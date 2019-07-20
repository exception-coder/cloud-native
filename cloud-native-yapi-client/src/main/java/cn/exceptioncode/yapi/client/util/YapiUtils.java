package cn.exceptioncode.yapi.client.util;

import cn.exceptioncode.yapi.client.dto.ApiDTO;
import cn.exceptioncode.common.annotation.ParamDesc;
import cn.exceptioncode.yapi.client.dto.ParamDTO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangkai
 */
@Slf4j
public class YapiUtils {

    // `RequestMapping` 派生注解集合
    private static final List<Class> REQUESTMAPPING_DERIVED = Lists.newArrayList(GetMapping.class, PutMapping.class, DeleteMapping.class, PatchMapping.class, PostMapping.class);
    private static String PROPERTIES_KEY = "properties";


    /**
     * 获取类对应的 yapi 元素类型
     *
     * @param clazz
     * @return
     */
    public static String getPropertiesType(Class clazz) {
        if (clazz.isPrimitive()) {
            System.out.println(clazz);
            clazz.getClassLoader();
        } else {
            if (ClassUtils.isAssignable(clazz, Number.class)) {
                if (ClassUtils.isAssignable(clazz, Integer.class)) {
                    return "integer";
                }
                return "number";
            }
            if (ClassUtils.isAssignable(clazz, Boolean.class)) {
                return "boolean";
            }
            if (ClassUtils.isAssignable(clazz, String.class)) {
                return "string";
            }
            if (ClassUtils.isAssignable(clazz, Collection.class) || clazz.isArray()) {
                return "array";
            }

        }

        return "object";
    }


    /**
     * 构建 yapi 元素描述对象
     *
     * @param name       键
     * @param clazz      类类型
     * @param paramDesc  对象或字段描述注解
     * @param properties yapi json元素描述对象 properties 对应的 map
     * @param required   必填属性集合
     * @return
     */
    public static Map<String, Object> jsonProperties(String name, Class clazz, ParamDesc paramDesc, Map<String, Object> properties, List required) {
        Map<String, Object> jsonMap = new HashMap<>(10);
        jsonMap.put("name", name);
        jsonMap.put("type", YapiUtils.getPropertiesType(clazz));
        if (paramDesc != null) {
            jsonMap.put("example", paramDesc.example());
            jsonMap.put("description", paramDesc.desc());
        } else {
            jsonMap.put("example", name);
            jsonMap.put("description", name);
        }
        if (properties == null) {
            properties = new HashMap<>(1);
        }
        jsonMap.put("properties", properties);
        if (required == null) {
            jsonMap.put("required", Lists.newArrayList(10));
        } else {
            jsonMap.put("required", required);
        }

        return jsonMap;
    }


    /**
     * 赋值请求方法及请求路径
     *
     * @param pathPrefix
     * @param apiDTO
     * @param method
     * @return
     */
    public static RequestMapping getRequestMapping(String pathPrefix, ApiDTO apiDTO, Method method) {
        if (pathPrefix == null) {
            pathPrefix = "";
        }
        // 接口路径
        String path = null;
        // 接口名称
        String apiName = null;
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        if (requestMapping == null) {
            // 尝试从 `RequestMapping` 派生注解中获取 `RequestMapping` 注解
            for (Class aClass : REQUESTMAPPING_DERIVED) {
                Annotation annotation = method.getAnnotation(aClass);
                if (annotation != null) {
                    Object pathObject = AnnotationUtils.getValue(annotation);
                    if (pathObject != null) {
                        if (pathObject instanceof String[]) {
                            path = ((String[]) pathObject)[0];
                        }
                    }
                    pathObject = AnnotationUtils.getValue(annotation, "name");
                    if (pathObject != null) {
                        if (pathObject instanceof String) {
                            apiName = ((String) pathObject);
                        }
                    }
                    // 从`RequestMapping`派生注解上获取 `RequestMapping`注解
                    requestMapping = AnnotationUtils.findAnnotation(annotation.getClass(), RequestMapping.class);
                    break;
                }
            }
        }
        if(requestMapping!=null){
            // 获取接口路径
            String[] pathValue = path == null ? requestMapping.value() : new String[]{path};
            // 获取接口名称
            apiName = apiName == null ? requestMapping.name() : apiName;
            if (pathValue != null && pathValue.length > 0) {
                // TODO: 2019/6/21 path为数组 可能存在多个请求路径
                apiDTO.setPath(pathPrefix + pathValue[0]);
                if (StringUtils.isEmpty(apiName)) {
                    apiDTO.setTitle(apiDTO.getPath());
                } else {
                    apiDTO.setTitle(apiName);
                }
            }
            // 获取请求方法
            RequestMethod[] requestMethods = requestMapping.method();
            if (requestMethods != null && requestMethods.length == 1) {
                apiDTO.setMethod(requestMethods[0].name());
            } else {
                // 存在多个请求method 或者获取不到 直接使用 GET method
                apiDTO.setMethod(RequestMethod.GET.name());
            }
            return requestMapping;
        }else {
            return null;
        }

    }


    public static void bindingParameter(Method method,ApiDTO apiDTO){
        Parameter[] parameters = method.getParameters();
        ParamDTO paramDTO;
        List<ParamDTO> reqQuery = Lists.newArrayList();
        List<ParamDTO> reqHeaders = Lists.newArrayList();
        List<ParamDTO> reqParams = Lists.newArrayList();

        // 遍历 `Controller` 对象绑定的参数
        for (Parameter parameter : parameters) {
            Annotation paramAnnotation = null;
            // 获取 RequestParam、RequestHeader、PathVariable、RequestBody 注解信息
            for (Class<? extends Annotation> aClass : Lists.newArrayList(RequestParam.class, RequestHeader.class,
                    PathVariable.class, RequestBody.class)) {
                paramAnnotation = parameter.getAnnotation(aClass);
                if (paramAnnotation != null) {
                    // 一但找到对应注解 不再继续获取
                    break;
                }

            }
            // 获取 ParamDesc 注解信息
            ParamDesc paramDesc = parameter.getAnnotation(ParamDesc.class);

            String paramAnnotationSimpleName = "";
            if (paramAnnotation != null) {
                // 获取动态代理类类名
                try {
                    // TODO: 2019/6/20 强行获取 待优化
                    Field filed = Proxy.getInvocationHandler(paramAnnotation).getClass().getDeclaredField("type");
                    filed.setAccessible(true);
                    Object object = filed.get(Proxy.getInvocationHandler(paramAnnotation));
                    if (object instanceof Class) {
                        Class clazz = (Class) object;
                        paramAnnotationSimpleName = clazz.getSimpleName();
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    log.error("获取字段type失败，失败信息：{}", e.getMessage());
                }

                String paramName;
                switch (paramAnnotationSimpleName) {
                    case "RequestBody":
                        // 参数中应当只有一个 @RequestBody 注解，待发掘多个的使用场景
                        apiDTO.setReq_body_is_json_schema(true);
                        Class clazz = parameter.getType();
                        Map<String, Object> yapiJsonPropertiesMap = yapiJsonProperties(clazz, null, null);
                        Object object = yapiJsonPropertiesMap.get(StringUtils.uncapitalize(clazz.getSimpleName()));
                        String reqBodyOther = JSON.toJSONString(object, SerializerFeature.WRITE_MAP_NULL_FEATURES, SerializerFeature.QuoteFieldNames);
                        apiDTO.setReq_body_other(reqBodyOther);
                        break;
                    case "RequestParam":
                        RequestParam requestParam = (RequestParam) paramAnnotation;
                        paramName = requestParam.name();
                        paramDTO = new ParamDTO(StringUtils.isEmpty(paramName)==true?parameter.getName():paramName, parameter.getType().getSimpleName(),
                                paramDesc == null ? null : paramDesc.example(), paramDesc == null ? null : paramDesc.desc(),
                                requestParam.required() == true ? "1" : "0");
                        reqQuery.add(paramDTO);
                        break;
                    case "RequestHeader":
                        RequestHeader requestHeader = (RequestHeader) paramAnnotation;
                        paramName = requestHeader.name();
                        paramDTO = new ParamDTO(StringUtils.isEmpty(paramName)==true?parameter.getName():paramName, parameter.getType().getSimpleName(),
                                paramDesc == null ? null : paramDesc.example(), paramDesc == null ? null : paramDesc.desc(),
                                requestHeader.required() == true ? "1" : "0");
                        reqHeaders.add(paramDTO);
                        break;
                    case "PathVariable":
                        PathVariable pathVariable = (PathVariable) paramAnnotation;
                        paramName = pathVariable.name();
                        paramDTO = new ParamDTO(StringUtils.isEmpty(paramName)==true?parameter.getName():paramName, parameter.getType().getSimpleName(),
                                paramDesc == null ? null : paramDesc.example(), paramDesc == null ? null : paramDesc.desc(),
                                pathVariable.required() == true ? "1" : "0");
                        reqParams.add(paramDTO);
                        break;
                    default:
                        log.info(paramAnnotationSimpleName);
                }
            }

            // TODO: 2019/6/23  annotation 为 null 获取存在多个 annotation
        }
        // 赋值请求参数
        apiDTO.setReq_query(reqQuery);
        apiDTO.setReq_params(reqParams);
        apiDTO.setReq_headers(reqHeaders);

    }




    /**
     * @param object        Class 或 Field
     * @param propertiesMap 对象子节点
     * @return
     */
    public static Map<String, Object> yapiJsonProperties(Object object, Map<String, Object> propertiesMap, Class genericSuperclass) {
        List<Field> fields = Lists.newArrayList();
        Class clazz = null;
        Field field = null;
        if (object instanceof Class) {
            clazz = (Class) object;
        }
        if (object instanceof Field) {
            field = (Field) object;
            try {
                clazz = ClassUtils.getClass(field.getType().getTypeName());
            } catch (ClassNotFoundException e) {

            }
        }
        if (clazz != null) {
            Field[] classFields = clazz.getDeclaredFields();
            String propertiesName;
            if (field != null) {
                propertiesName = field.getName();
            } else {
                propertiesName = clazz.getSimpleName();
                propertiesName = StringUtils.uncapitalize(propertiesName);
            }

            /**
             *
             * 对象必填节点集合
             */
            List<String> required = Lists.newArrayList();

            if (propertiesMap == null) {
                propertiesMap = YapiUtils.jsonProperties(propertiesName, clazz, getParamDesc(clazz), null, required);
            }

            for (Field field1 : classFields) {
                // 判断是否私有属性
                if (field1.getModifiers() == 2) {
                    String fieldName = field1.getName();
                    char[] cs = fieldName.toCharArray();
                    cs[0] -= 32;
                    try {
                        Method method = clazz.getMethod("get" + String.valueOf(cs));
                        // 判断是否有对应的getter方法则判断为DTO
                        if (method != null) {
                            fields.add(field1);
                        }
                        ParamDesc paramDesc = getParamDesc(field);
                        if (paramDesc != null && paramDesc.required()) {
                            required.add(field1.getName());
                        }
                    } catch (NoSuchMethodException e) {

                    }
                }

            }
            if (fields.isEmpty()) {
                // 解析的类属性没有对应的有效属性则直接将属性直接绑定到根节点下
                if (field != null) {
                    Class typeClass = Object.class;
                    try {
                        Class classType = field.getType();
                        // 判断字段类型是否基本类型
                        if (classType.isPrimitive()) {
                            // 若是基本数据类型 则获取其对应包装类型
                            typeClass = YapiClassUtils.getWrapperByPrimitive(classType);
                        } else {
                            typeClass = Class.forName(field.getGenericType().getTypeName());
                        }
                    } catch (ClassNotFoundException e) {

                    }
                    propertiesMap.put(propertiesName, YapiUtils.jsonProperties(propertiesName, typeClass, getParamDesc(field), null, required));
                } else {
                    propertiesMap.put(propertiesName, YapiUtils.jsonProperties(propertiesName, clazz, getParamDesc(clazz), null, required));
                }
            } else {
                Map<String, Object> map = YapiUtils.jsonProperties(propertiesName, clazz, getParamDesc(clazz), null, required);
                propertiesMap.put(propertiesName, map);
                Map<String, Object> mapp = new HashMap<>(10);
                // 将 object 属性字段申明到 properties 中
                map.put(PROPERTIES_KEY, mapp);
                map.put("required", required);
                for (Field field1 : fields) {
                    ParamDesc paramDesc = getParamDesc(field1);
                    if (paramDesc != null && paramDesc.required()) {
                        required.add(field1.getName());
                    }
                    // 递归构建 properties 节点，子节点泛型不再捕获替换
                    if (genericSuperclass != null) {
                        Type genericType = field1.getGenericType();
                        String typeName = genericType.getTypeName();
                        if ("T".equals(typeName)) {
                            if (genericSuperclass != null) {
                                // 将泛型 T 转换为具体申明的类型
                                yapiJsonProperties(genericSuperclass, mapp, genericSuperclass);
                            }
                        } else {
                            yapiJsonProperties(field1, mapp, null);
                        }
                    } else {
                        yapiJsonProperties(field1, mapp, null);
                    }

                }
            }
            return propertiesMap;
        }
        return propertiesMap;

    }


    /**
     * 从 Class 或 Field 上获取 ParamDesc 注解
     *
     * @param object
     * @return
     */
    private static ParamDesc getParamDesc(Object object) {
        Annotation annotation;
        if (object instanceof Field) {
            Field field = (Field) object;
            ParamDesc paramDesc = field.getAnnotation(ParamDesc.class);
            if (paramDesc != null) {
                return paramDesc;
            }
        }
        if (object instanceof Class) {
            Class clazz = (Class) object;
            annotation = clazz.getAnnotation(ParamDesc.class);
            if (annotation != null && annotation instanceof ParamDesc) {
                return (ParamDesc) annotation;
            }
        }

        return null;
    }

}
