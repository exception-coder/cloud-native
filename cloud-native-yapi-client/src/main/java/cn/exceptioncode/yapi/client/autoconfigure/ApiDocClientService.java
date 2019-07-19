package cn.exceptioncode.yapi.client.autoconfigure;


import cn.exceptioncode.common.annotation.ParamDesc;
import cn.exceptioncode.yapi.client.autoconfigure.properties.ApiDocClientProperties;
import cn.exceptioncode.yapi.client.dto.ApiDTO;
import cn.exceptioncode.yapi.client.dto.ParamDTO;
import cn.exceptioncode.yapi.client.util.YapiClassUtils;
import cn.exceptioncode.yapi.client.util.YapiUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;


/**
 * @author zhangkai
 */
@Slf4j
public class ApiDocClientService {


    private static String PROPERTIES_KEY = "properties";

    private ApiDocClientProperties apiDocClientProperties;

    private RestTemplate restTemplate;

    public ApiDocClientService(ApiDocClientProperties apiDocClientProperties,
                               RestTemplate restTemplate) {
        this.apiDocClientProperties = apiDocClientProperties;
        this.restTemplate = restTemplate;
    }

    /**
     * @param event
     */
    @EventListener
    public void applicationRunListener(ApplicationStartedEvent event) {
        Boolean enable = apiDocClientProperties.getEnable();
        if (enable == true) {
            // 从 `ApplicationContext` 获取所有包含 `Controller` 注解的类
            Map<String, Object> controllers = event.getApplicationContext().getBeansWithAnnotation(Controller.class);
            // `Controller` 对象集合
            List<Object> list = Lists.newArrayList();
            controllers.forEach((controllerKey, controller) -> {
                        // 集合中存在 `Controller` 则表示已经处理 不再进行处理
                        if (!list.contains(controller)) {
                            list.add(controller);

                            RequestMapping requestMapping = controller.getClass().getAnnotation(RequestMapping.class);
                            // url 请求路径前缀
                            String pathPrefix = "";
                            if (requestMapping != null) {
                                // TODO: 19-7-1 路径可为路径数组 暂时只获取第一个路径
                                String[] pathValues = requestMapping.value();
                                if (pathValues != null && pathValues.length > 0) {
                                    pathPrefix = pathValues[0];
                                }
                            }

                            Method[] methods = ReflectionUtils.getAllDeclaredMethods(controller.getClass());

                            for (Method method : methods) {
                                ApiDTO apiDTO = new ApiDTO();
                                RequestMapping methodRequestMapping = YapiUtils.getRequestMapping(pathPrefix, apiDTO, method);

                                // 只解析 `RequestMapping`及其派生注解申明的方法
                                if (methodRequestMapping != null) {
                                    apiDTO.setCatid(this.apiDocClientProperties.getApiCatid());
                                    apiDTO.setToken(this.apiDocClientProperties.getApiToken());

                                    /**
                                     *
                                     * 绑定 yapi 请求参数
                                     */

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
                                                    this.log("请求体");
                                                    // 参数中应当只有一个 @RequestBody 注解，待发掘多个的使用场景
                                                    apiDTO.setReq_body_is_json_schema(true);
                                                    Class clazz = parameter.getType();
                                                    Map<String, Object> yapiJsonProperties = yapiJsonProperties(clazz, null, null);
                                                    Object object = yapiJsonProperties.get(StringUtils.uncapitalize(clazz.getSimpleName()));
                                                    String reqBodyOther = JSON.toJSONString(object, SerializerFeature.WRITE_MAP_NULL_FEATURES, SerializerFeature.QuoteFieldNames);
                                                    apiDTO.setReq_body_other(reqBodyOther);
                                                    break;
                                                case "RequestParam":
                                                    this.log("URL请求参数");
                                                    RequestParam requestParam = (RequestParam) paramAnnotation;
                                                    paramName = requestParam.name();
                                                    paramDTO = new ParamDTO(StringUtils.isEmpty(paramName)==true?parameter.getName():paramName, parameter.getType().getSimpleName(),
                                                            paramDesc == null ? null : paramDesc.example(), paramDesc == null ? null : paramDesc.desc(),
                                                            requestParam.required() == true ? "1" : "0");
                                                    reqQuery.add(paramDTO);
                                                    break;
                                                case "RequestHeader":
                                                    this.log("请求头");
                                                    RequestHeader requestHeader = (RequestHeader) paramAnnotation;
                                                    paramName = requestHeader.name();
                                                    paramDTO = new ParamDTO(StringUtils.isEmpty(paramName)==true?parameter.getName():paramName, parameter.getType().getSimpleName(),
                                                            paramDesc == null ? null : paramDesc.example(), paramDesc == null ? null : paramDesc.desc(),
                                                            requestHeader.required() == true ? "1" : "0");
                                                    reqHeaders.add(paramDTO);
                                                    break;
                                                case "PathVariable":
                                                    this.log("请求路径");
                                                    PathVariable pathVariable = (PathVariable) paramAnnotation;
                                                    paramName = pathVariable.name();
                                                    paramDTO = new ParamDTO(StringUtils.isEmpty(paramName)==true?parameter.getName():paramName, parameter.getType().getSimpleName(),
                                                            paramDesc == null ? null : paramDesc.example(), paramDesc == null ? null : paramDesc.desc(),
                                                            pathVariable.required() == true ? "1" : "0");
                                                    reqParams.add(paramDTO);
                                                    break;
                                                default:
                                                    this.log("classSimpleName:" + paramAnnotation + ",unknown");

                                            }
                                        }

                                        // TODO: 2019/6/23  annotation 为 null 获取存在多个 annotation
                                    }
                                    // 赋值请求参数
                                    apiDTO.setReq_query(reqQuery);
                                    apiDTO.setReq_params(reqParams);
                                    apiDTO.setReq_headers(reqHeaders);


                                    /**
                                     *
                                     *
                                     * yapi响应参数绑定
                                     *
                                     */
                                    Map<Class, Class> classMap = YapiClassUtils.getResponseReturnType(method);
                                    Set<Class> set = classMap.keySet();
                                    Class clazz = null;
                                    Class genericClazz = null;
                                    for (Class aClass : set) {
                                        clazz = aClass;
                                        genericClazz = classMap.get(aClass);
                                    }
                                    // 赋值响应参数
                                    if (!clazz.isPrimitive()) {
                                        // 返回类型不是基本数据类型
                                        Map<String, Object> jsonProperties = yapiJsonProperties(clazz, null, genericClazz);
                                        Object jsonPropertiesObj = jsonProperties.get(StringUtils.uncapitalize(clazz.getSimpleName()));
                                        String resBody = JSON.toJSONString(jsonPropertiesObj, SerializerFeature.WRITE_MAP_NULL_FEATURES, SerializerFeature.QuoteFieldNames);
                                        apiDTO.setRes_body_type("json");
                                        log.warn("赋值响应参数res_body:{}", resBody);
                                        apiDTO.setRes_body(resBody);
                                    } else {
                                        // 返回类型是基本数据类型
                                        apiDTO.setRes_body_type("text");
                                        apiDTO.setRes_body_type(clazz.getName());
                                    }
                                    String result = saveApi(apiDTO);
                                    log.warn("保存api成功，请求参数：{}，响应信息：{}", JSON.toJSONString(apiDTO, SerializerFeature.WRITE_MAP_NULL_FEATURES, SerializerFeature.QuoteFieldNames), result);
                                }


                            }
                        }
                        log.warn("alias:{},controller:{}", controllerKey, controller);
                    }

            );
        }
    }


    /**
     * @param apiDTO
     * @return
     */
    private String saveApi(ApiDTO apiDTO) {
        final String YAPI_URL = "http://" + this.apiDocClientProperties.getApiServer() + "/api";
        final String LOGIN_URL = YAPI_URL + "/user/login";
        final String SAVE_API_URL = YAPI_URL + "/interface/save";
        Map<String, String> map = new HashMap<>(2);
        map.put("email", apiDocClientProperties.getApiUserName());
        map.put("password", apiDocClientProperties.getApiUserPassword());
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(LOGIN_URL, map, Map.class);

        List<String> headers = responseEntity.getHeaders().get("set-cookie");

        String cookie = "";
        Iterator<String> iterator = headers.iterator();
        boolean isFirst = true;
        while (iterator.hasNext()) {
            if (!isFirst) {
                cookie += ";";

            }
            cookie += iterator.next();
            isFirst = false;
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", cookie);
        HttpEntity<ApiDTO> httpEntity = new HttpEntity<>(apiDTO, httpHeaders);
        return restTemplate.postForEntity(SAVE_API_URL, httpEntity, String.class).getBody();
    }

    private void log(String reqSource) {
        log.warn("请求参数来源：{}", reqSource);
    }


    /**
     * @param object        Class 或 Field
     * @param propertiesMap 对象子节点
     * @return
     */
    private static Map<String, Object> yapiJsonProperties(Object object, Map<String, Object> propertiesMap, Class genericSuperclass) {
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
