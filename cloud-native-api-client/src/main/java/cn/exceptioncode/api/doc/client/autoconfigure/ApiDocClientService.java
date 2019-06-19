package cn.exceptioncode.api.doc.client.autoconfigure;


import cn.exceptioncode.api.doc.client.autoconfigure.properties.ApiDocClientProperties;
import cn.exceptioncode.api.doc.client.dto.ApiDTO;
import cn.exceptioncode.api.doc.client.dto.ApiPropertiesDTO;
import cn.exceptioncode.api.doc.client.dto.ParamDTO;
import cn.exceptioncode.api.doc.client.dto.ResBodyDTO;
import cn.exceptioncode.api.doc.client.feign.YapiClient;
import cn.exceptioncode.api.doc.client.service.YapiService;
import cn.exceptioncode.api.doc.client.util.YapiClientUtil;
import cn.exceptioncode.common.dto.BaseResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author zhangkai
 */
@Slf4j
public class ApiDocClientService {


    private ApiDocClientProperties apiDocClientProperties;


    public ApiDocClientService(ApiDocClientProperties apiDocClientProperties) {
        this.apiDocClientProperties = apiDocClientProperties;
    }

    /**
     * @param event
     */
    @EventListener
    public void applicationRunListener(ApplicationStartedEvent event) {

        Map<String, Object> controllers = event.getApplicationContext().getBeansWithAnnotation(Controller.class);
        List<Object> list = Lists.newArrayList();
        controllers.forEach((s, o) -> {
                    if (!list.contains(o)) {
                        list.add(o);
                        Controller controllerAnn = o.getClass().getAnnotation(Controller.class);
                        String pathPrefix = "";
                        if (controllerAnn != null) {
                            pathPrefix = controllerAnn.value();
                        }
                        Method[] methods = ReflectionUtils.getAllDeclaredMethods(o.getClass());
                        for (Method method : methods) {
                            RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                            // 只解析 @RequestMapping 申明的方法
                            if (annotation != null) {
                                ApiDTO apiDTO = new ApiDTO();
                                // step1：获取请求路径
                                String[] pathValue = annotation.value();
                                if (pathValue != null) {
                                    // v0.1 仅获取一个请求路径 多个请求路径映射处理
                                    apiDTO.setPath(pathPrefix + pathValue[0]);
                                    apiDTO.setTitle("java debug");
                                    apiDTO.setStatus("undone");
                                }
                                // step2：获取请求方法
                                RequestMethod[] requestMethods = annotation.method();
                                if (requestMethods != null && requestMethods.length == 1) {
                                    apiDTO.setMethod(requestMethods[0].name());
                                } else {
                                    // 存在多个请求method 或者获取不到 直接使用 GET method
                                    apiDTO.setMethod(RequestMethod.GET.name());
                                }
                                // step3：获取请求参数
                                Parameter[] parameters = method.getParameters();
                                ParamDTO paramDTO;
                                List<ParamDTO> reqQuery = Lists.newArrayList();
                                List<ParamDTO> reqHeaders = Lists.newArrayList();
                                List<ParamDTO> reqParams = Lists.newArrayList();
                                // 遍历 controller 绑定的参数
                                for (Parameter parameter : parameters) {
                                    Annotation[] annotations = parameter.getAnnotations();
                                    if (annotations != null && annotations.length == 1) {
                                        switch (annotations[0].getClass().getSimpleName()) {
                                            case "RequestBody":
                                                this.log("请求体");
                                                // 参数中应当只有一个 @RequestBody 注解，待发掘多个的使用场景
                                                apiDTO.setRes_body(JSON.toJSONString(parameter));
                                                break;
                                            case "RequestParam":
                                                this.log("URL请求参数");
                                                RequestParam requestParam = (RequestParam) annotations[0];
                                                paramDTO = new ParamDTO(requestParam.name(), parameter.getType().getSimpleName(),
                                                        null, null,
                                                        requestParam.required() == true ? "1" : "0");
                                                reqQuery.add(paramDTO);
                                                break;
                                            case "RequestHeader":
                                                this.log("请求头");
                                                RequestHeader requestHeader = (RequestHeader) annotations[0];
                                                paramDTO = new ParamDTO(requestHeader.name(), parameter.getType().getSimpleName(),
                                                        null, null,
                                                        requestHeader.required() == true ? "1" : "0");
                                                reqHeaders.add(paramDTO);
                                                break;
                                            case "PathVariable":
                                                this.log("请求路径");
                                                PathVariable pathVariable = (PathVariable) annotations[0];
                                                paramDTO = new ParamDTO(pathVariable.name(), parameter.getType().getSimpleName(),
                                                        null, null,
                                                        pathVariable.required() == true ? "1" : "0");
                                                reqParams.add(paramDTO);
                                                break;
                                            default:
                                                this.log("classSimpleName:"+annotations[0].getClass().getSimpleName()+",unknown");

                                        }
                                    }
                                    // annotations 为 null 获取存在多个 annotation 暂不处理
                                }

                                // step4：获取响应参数
                                Class clazz = method.getReturnType();
                                if (Mono.class.getPackage().equals(clazz.getPackage())
                                        || Flux.class.getPackage().equals(clazz.getPackage())) {
                                    // TODO: 2019/6/18
                                } else {
                                    try {

                                        // 返回类型是json
                                        String jsonStr = JSON.toJSONString(clazz.newInstance(), SerializerFeature.WRITE_MAP_NULL_FEATURES);
                                        apiDTO.setRes_body(jsonStr);

                                        log.info("返回数据:{}", jsonStr);
                                        ApiPropertiesDTO apiPropertiesDTO = new ApiPropertiesDTO();
                                        apiPropertiesDTO.setType("object");
                                        Map<String, Object> map = JSON.parseObject(jsonStr, Map.class);
                                        apiPropertiesDTO.setProperties(map);


                                    } catch (InstantiationException | IllegalAccessException e) {
                                        log.error(e.getMessage());
                                        // 返回类型不是json
                                    }

                                }
                               try{
                                   String result = YapiClientUtil.saveApi(apiDTO);
                                   log.warn("保存api成功，响应信息：{}",result);
                               }catch (IOException e){
                                   log.error("保存api异常，异常信息：{}",e.getMessage());
                               }
                            }


                        }
                    }
                    log.warn("alias:{},controller:{}", s, o);
                }

        );

    }

    private void log(String reqSource) {
        log.warn("请求参数来源：{}", reqSource);
    }

    public Mono<String> method() {
        return Mono.just("data");
    }

    public String getControllerBasePackage() {
        return this.apiDocClientProperties.getControllerBasePackage();
    }


    public static void main(String[] args) {
        BaseResponse baseResponse = BaseResponse.success(new HashMap<>(1));
        String jsonStr = JSON.toJSONString(baseResponse, SerializerFeature.WRITE_MAP_NULL_FEATURES, SerializerFeature.PrettyFormat);
        System.out.println(jsonStr);
        Map<String, Object> map = JSON.parseObject(jsonStr, Map.class);
        map.forEach((s, obj) -> log.info("s:{},classSimpleName:{}", s, obj == null ? null : obj.getClass().getSimpleName()));
    }
}
