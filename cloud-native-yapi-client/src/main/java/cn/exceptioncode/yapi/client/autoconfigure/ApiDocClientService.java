package cn.exceptioncode.yapi.client.autoconfigure;


import cn.exceptioncode.yapi.client.autoconfigure.properties.ApiDocClientProperties;
import cn.exceptioncode.yapi.client.dto.ApiDTO;
import cn.exceptioncode.yapi.client.util.YapiClassUtils;
import cn.exceptioncode.yapi.client.util.YapiUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Method;
import java.util.*;


/**
 * @author zhangkai
 */
@Slf4j
public class ApiDocClientService {


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

                        // 判断当前`controller`是否存在`Controller`集合中 存在则表示已经处理 不再进行处理
                        // 判断 `controller` 是否在申明的包下 不在则不进行处理
                        if (!list.contains(controller) &&
                                controllerIsInPackage(apiDocClientProperties, controller)) {
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
                                // 获取方法上的 `RequestMapping` 并赋值请求方法及请求路径
                                RequestMapping methodRequestMapping = YapiUtils.getRequestMappingAndBindingMethodPath(pathPrefix, apiDTO, method);

                                // 只解析 `RequestMapping`及其派生注解申明的方法
                                if (methodRequestMapping != null) {
                                    apiDTO.setCatid(this.apiDocClientProperties.getApiCatid());
                                    apiDTO.setToken(this.apiDocClientProperties.getApiToken());

                                    /**
                                     *
                                     * 绑定 yapi 请求参数
                                     */
                                    YapiUtils.bindingParameter(method, apiDTO);


                                    /**
                                     *
                                     *
                                     * 绑定 yapi 响应参数
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
                                        Map<String, Object> jsonProperties = YapiUtils.yapiJsonProperties(clazz, null, genericClazz);
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

    private boolean isInPackage(String basePackage, Object object) {
        String classPackage = object.getClass().getPackage().getName();
        String[] strArr1 = basePackage.split(".");
        String[] strArr2 = classPackage.split(".");
        boolean flag = true;
        for (int i = 0; i < strArr1.length; i++) {
            if ("**".equals(strArr1[i])) {
                break;
            }
            if (!"*".equals(strArr1[i])) {
                if (!strArr1[i].equals(strArr2[i])) {
                    flag = false;
                    break;
                }
            }

        }
        return flag;
    }


    /**
     * 判断 `controller` 实例是否在申明的包下
     *
     * @param apiDocClientProperties
     * @param controller
     * @return
     */
    private boolean controllerIsInPackage(ApiDocClientProperties apiDocClientProperties, Object controller) {
        String basePackageStr = apiDocClientProperties.getControllerBasePackage();
        if (StringUtils.isEmpty(basePackageStr)) {
            return true;
        }
        String[] basePackageArr = basePackageStr.split(",");
        boolean flag = false;
        for (String basePackage : basePackageArr) {
            flag = isInPackage(basePackage, controller);
            if (flag) {
                break;
            }
        }
        return flag;
    }


}
