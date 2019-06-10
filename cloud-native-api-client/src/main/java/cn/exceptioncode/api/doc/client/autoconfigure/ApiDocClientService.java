package cn.exceptioncode.api.doc.client.autoconfigure;


import cn.exceptioncode.api.doc.client.autoconfigure.properties.ApiDocClientProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.util.unit.DataUnit;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.result.method.RequestMappingInfo;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;


/**
 *
 * @author zhangkai
 *
 */
@Slf4j
public class ApiDocClientService{

    private ApiDocClientProperties apiDocClientProperties;

    private String controllerBasePackage;


    public ApiDocClientService(ApiDocClientProperties apiDocClientProperties){
        this.apiDocClientProperties = apiDocClientProperties;
    }

    @EventListener
    public void applicationRunListener(ApplicationStartedEvent event){
        RequestMappingHandlerMapping mapping = event.getApplicationContext().getBean(RequestMappingHandlerMapping.class);
    }

    public String getControllerBasePackage() {
        return this.apiDocClientProperties.getControllerBasePackage();
    }
}
