package cn.exceptioncode.api.doc.client.autoconfigure;


import cn.exceptioncode.api.doc.client.autoconfigure.properties.ApiDocClientProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

import java.util.Map;


/**
 *
 * @author zhangkai
 *
 */
@Slf4j
public class ApiDocClientService{

    private ApiDocClientProperties apiDocClientProperties;


    public ApiDocClientService(ApiDocClientProperties apiDocClientProperties){
        this.apiDocClientProperties = apiDocClientProperties;
    }

    @EventListener
    public void applicationRunListener(ApplicationStartedEvent event){
        Map<String,Object> controllers = event.getApplicationContext().getBeansWithAnnotation(Controller.class);
        controllers.forEach((s, o) ->
            log.warn("alias:{},controller:{}",s,o)
        );

    }

    public String getControllerBasePackage() {
        return this.apiDocClientProperties.getControllerBasePackage();
    }
}
