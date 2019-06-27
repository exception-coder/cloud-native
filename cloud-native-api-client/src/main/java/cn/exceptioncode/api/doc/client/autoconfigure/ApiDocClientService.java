package cn.exceptioncode.api.doc.client.autoconfigure;


import cn.exceptioncode.api.doc.client.autoconfigure.properties.ApiDocClientProperties;
import cn.exceptioncode.api.doc.client.dto.ApiDTO;
import cn.exceptioncode.api.doc.client.dto.ParamDTO;
import cn.exceptioncode.common.annotations.ParamDesc;
import cn.exceptioncode.common.dto.BaseResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


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

        Map<String, Object> controllers = event.getApplicationContext().getBeansWithAnnotation(Controller.class);
        ObjectMapper objectMapper = event.getApplicationContext().getBean(ObjectMapper.class);
        List<Object> list = Lists.newArrayList();
        controllers.forEach((s, o) -> {
                    if (!list.contains(o)) {
                        list.add(o);
                        RequestMapping requestMapping = o.getClass().getAnnotation(RequestMapping.class);
                        String pathPrefix = "";
                        if (requestMapping != null) {
                            String[] pathValues = requestMapping.value();
                            if (pathValues != null && pathValues.length > 0) {
                                pathPrefix = pathValues[0];
                            }
                        }
                        Method[] methods = ReflectionUtils.getAllDeclaredMethods(o.getClass());
                        for (Method method : methods) {
                            RequestMapping requestMappingAnn = method.getAnnotation(RequestMapping.class);
                            // 接口路径
                            String path = null;
                            // 接口名称
                            String apiName = "";
                            if (requestMappingAnn == null) {
                                List<Class> classArrayList = Lists.newArrayList(GetMapping.class, PutMapping.class, DeleteMapping.class, PatchMapping.class);
                                // 尝试获取 GetMapping、PostMapping、PutMapping、DeleteMapping、PatchMapping
                                for (Class aClass : classArrayList) {
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
                                        requestMappingAnn = AnnotationUtils.findAnnotation(annotation.getClass(), RequestMapping.class);
                                        break;
                                    }
                                }
                            }

                            // 只解析 @RequestMapping 申明的方法
                            if (requestMappingAnn != null) {
                                ApiDTO apiDTO = new ApiDTO();
                                apiDTO.setCatid(this.apiDocClientProperties.getApiCatid());
                                apiDTO.setToken(this.apiDocClientProperties.getApiToken());
                                // 获取接口路径、接口名称
                                String[] pathValue = path == null ? requestMappingAnn.value() : new String[]{path};
                                apiName = StringUtils.isEmpty(apiName) ? requestMappingAnn.name() : apiName;
                                if (pathValue != null && pathValue.length > 0) {
                                    // v0.1 仅获取一个请求路径
                                    // TODO: 2019/6/21 多请求路径映射处理 
                                    apiDTO.setPath(pathPrefix + pathValue[0]);
                                    if (StringUtils.isEmpty(apiName)) {
                                        apiDTO.setTitle(apiDTO.getPath());
                                    } else {
                                        apiDTO.setTitle(apiName);
                                    }
                                }
                                // 获取请求方法
                                RequestMethod[] requestMethods = requestMappingAnn.method();
                                if (requestMethods != null && requestMethods.length == 1) {
                                    apiDTO.setMethod(requestMethods[0].name());
                                } else {
                                    // 存在多个请求method 或者获取不到 直接使用 GET method
                                    apiDTO.setMethod(RequestMethod.GET.name());
                                }
                                // 获取请求参数
                                Parameter[] parameters = method.getParameters();
                                ParamDTO paramDTO;
                                List<ParamDTO> reqQuery = Lists.newArrayList();
                                List<ParamDTO> reqHeaders = Lists.newArrayList();
                                List<ParamDTO> reqParams = Lists.newArrayList();
                                // 遍历 controller 绑定的参数
                                for (Parameter parameter : parameters) {
                                    Annotation annotation = null;
                                    // 获取 RequestParam、RequestHeader、PathVariable、RequestBody 注解信息
                                    for (Class<? extends Annotation> aClass : Lists.newArrayList(RequestParam.class, RequestHeader.class,
                                            PathVariable.class, RequestBody.class)) {
                                        annotation = parameter.getAnnotation(aClass);
                                        if (annotation != null) {
                                            // 一但找到对应注解 不再继续获取
                                            break;
                                        }

                                    }
                                    // 获取 ParamDesc 注解信息
                                    ParamDesc paramDesc = parameter.getAnnotation(ParamDesc.class);

                                    String paramAnnotationName = "";
                                    if (annotation != null) {
                                        // 获取动态代理类类名 
                                        try {
                                            // TODO: 2019/6/20 强行获取 待优化
                                            Field filed = Proxy.getInvocationHandler(annotation).getClass().getDeclaredField("type");
                                            filed.setAccessible(true);
                                            Object object = filed.get(Proxy.getInvocationHandler(annotation));
                                            if (object instanceof Class) {
                                                Class clazz = (Class) object;
                                                paramAnnotationName = clazz.getSimpleName();
                                            }
                                        } catch (NoSuchFieldException | IllegalAccessException e) {
                                            log.error("获取字段type失败，失败信息：{}", e.getMessage());
                                        }

                                        log.info("请求参数注解简称：{}", paramAnnotationName);
                                        switch (paramAnnotationName) {
                                            case "RequestBody":
                                                this.log("请求体");
                                                // 参数中应当只有一个 @RequestBody 注解，待发掘多个的使用场景
                                                apiDTO.setRes_body(JSON.toJSONString(parameter));
                                                break;
                                            case "RequestParam":
                                                this.log("URL请求参数");
                                                RequestParam requestParam = (RequestParam) annotation;
                                                paramDTO = new ParamDTO(requestParam.name(), parameter.getType().getSimpleName(),
                                                        paramDesc == null ? null : paramDesc.example(), paramDesc == null ? null : paramDesc.desc(),
                                                        requestParam.required() == true ? "1" : "0");
                                                reqQuery.add(paramDTO);
                                                break;
                                            case "RequestHeader":
                                                this.log("请求头");
                                                RequestHeader requestHeader = (RequestHeader) annotation;
                                                paramDTO = new ParamDTO(requestHeader.name(), parameter.getType().getSimpleName(),
                                                        paramDesc == null ? null : paramDesc.example(), paramDesc == null ? null : paramDesc.desc(),
                                                        requestHeader.required() == true ? "1" : "0");
                                                reqHeaders.add(paramDTO);
                                                break;
                                            case "PathVariable":
                                                this.log("请求路径");
                                                PathVariable pathVariable = (PathVariable) annotation;
                                                paramDTO = new ParamDTO(pathVariable.name(), parameter.getType().getSimpleName(),
                                                        paramDesc == null ? null : paramDesc.example(), paramDesc == null ? null : paramDesc.desc(),
                                                        pathVariable.required() == true ? "1" : "0");
                                                reqParams.add(paramDTO);
                                                break;
                                            default:
                                                this.log("classSimpleName:" + paramAnnotationName + ",unknown");

                                        }
                                    }

                                    // TODO: 2019/6/23  annotations 为 null 获取存在多个 annotations
                                }
                                // 赋值请求参数
                                apiDTO.setReq_query(reqQuery);
                                apiDTO.setReq_params(reqParams);
                                apiDTO.setReq_headers(reqHeaders);
                                // 获取响应参数
                                Class clazz = method.getReturnType();
                                /**
                                 *
                                 * 基本数据类型没有 package
                                 *
                                 */
                                Package monoPackage = Mono.class.getPackage();
                                Package fluxPackage = Flux.class.getPackage();
                                Package clazzPackage = clazz.getPackage();
                                boolean isReactor = monoPackage.equals(clazzPackage)
                                        || fluxPackage.equals(clazzPackage);
                                if (!clazz.isPrimitive() && isReactor) {
                                    // 返回类型是 Flux 或 Mono 序列
                                    log.info("Reactor");
                                    Type genericReturnType = method.getGenericReturnType();
                                    if (genericReturnType instanceof ParameterizedTypeImpl) {
                                        ParameterizedTypeImpl type = (ParameterizedTypeImpl) genericReturnType;
                                        Type[] actualTypeArguments = type.getActualTypeArguments();
                                        if (actualTypeArguments != null && actualTypeArguments.length > 0) {
                                            Type actualTypeArgument = actualTypeArguments[0];
                                            try {
                                                String jsonStr = JSON.toJSONString(Class.forName(actualTypeArgument.getTypeName()).newInstance(), SerializerFeature.WRITE_MAP_NULL_FEATURES, SerializerFeature.QuoteFieldNames);
                                                apiDTO.setRes_body(jsonStr);
                                            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                                                log.error("赋值响应类型异常，ApiDTO：{}，异常信息：{}", JSON.toJSONString(apiDTO), e.getMessage());
                                            }
                                            log.warn("响应数据类型：{}", actualTypeArgument);
                                        }
                                        // TODO: 2019/6/18 可能出现多个泛型类型、也可能还是Flux或者Mono序列类型
                                    }
                                } else {

                                    if (!clazz.isPrimitive()) {
                                        // 返回类型不是基本数据类型
                                        apiDTO.setRes_body_type("json");
                                        Map<String,Object> JsonProperties = yapiJsonProperties(clazz, null);
                                        apiDTO.setRes_body(JSON.toJSONString(JsonProperties.get(clazz.getSimpleName()), SerializerFeature.WRITE_MAP_NULL_FEATURES, SerializerFeature.QuoteFieldNames));
                                    } else {
                                        // 返回类型是基本数据类型
                                        apiDTO.setRes_body_type("text");
                                        apiDTO.setRes_body_type(clazz.getName());
                                    }


                                }

                                try {
                                    log.warn("请求体：{}", objectMapper.writeValueAsString(apiDTO));
                                } catch (JsonProcessingException e) {
                                    log.error(e.getMessage());
                                }
                                String
                                        result = saveApi(apiDTO);


                                log.warn("保存api成功，请求参数：{}，响应信息：{}", JSON.toJSONString(apiDTO, SerializerFeature.WRITE_MAP_NULL_FEATURES, SerializerFeature.QuoteFieldNames), result);
                            }


                        }
                    }
                    log.warn("alias:{},controller:{}", s, o);
                }

        );

    }


    private String saveApi(ApiDTO apiDTO) {
        final String YAPI_URL = "http://" + this.apiDocClientProperties.getApiServer() + "/api";
        final String LOGIN_URL = YAPI_URL + "/user/login";
        final String SAVE_API_URL = YAPI_URL + "/interface/save";
        Map<String, String> map = new HashMap<>(2);
        map.put("email", "425485346@qq.com");
        map.put("password", "ymfe.org");
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


    public String getControllerBasePackage() {
        return this.apiDocClientProperties.getControllerBasePackage();
    }

    @SneakyThrows
    public static void main(String[] args) {
        Map<String, Object> stringObjectMap = yapiJsonProperties(BaseResponse.class, null);
        System.out.println(JSON.toJSONString(stringObjectMap));
    }


    private static Map<String, Object> jsonProperties(String name, ParamDesc paramDesc, Map<String, Object> properties) {
        Map<String, Object> jsonMap = new HashMap<>(10);
        jsonMap.put("name", name);
        jsonMap.put("type", "object");
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
        return jsonMap;
    }


    /**
     * @param object        Class 或 Field
     * @param propertiesMap 对象子节点
     * @return
     */
    private static Map<String, Object> yapiJsonProperties(Object object, Map<String, Object> propertiesMap) {
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
            }
            if (propertiesMap == null) {
                propertiesMap = jsonProperties(propertiesName, getParamDesc(clazz), null);
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
                    } catch (NoSuchMethodException e) {

                    }
                }

            }
            if (fields.isEmpty()) {
                // 解析的类属性没有对应的有效属性则直接将属性直接绑定到根节点下
                if (field != null) {
                    propertiesMap.put(propertiesName, jsonProperties(propertiesName, getParamDesc(field), null));
                } else {
                    propertiesMap.put(propertiesName, jsonProperties(propertiesName, getParamDesc(clazz), null));
                }
            } else {
                Map<String,Object> map = jsonProperties(propertiesName, getParamDesc(clazz), null);
                propertiesMap.put(propertiesName,map);
                Map<String,Object> mapp = new HashMap<>(10);
                map.put(PROPERTIES_KEY, mapp);
                for (Field field1 : fields) {
                    // 递归构建 properties 节点
                    yapiJsonProperties(field1, mapp);
                }
            }
            return propertiesMap;
        }
        return propertiesMap;

    }

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
