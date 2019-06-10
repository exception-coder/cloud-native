package cn.exceptioncode.api.doc.client.autoconfigure;


import cn.exceptioncode.api.doc.client.autoconfigure.properties.ApiDocClientProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;


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
    public void applicationRunListener(ContextStartedEvent ces){
      log.info("Application Run {}",ces.getApplicationContext().getStartupDate());
    }

    public String getControllerBasePackage() {
        return this.apiDocClientProperties.getControllerBasePackage();
    }
}
