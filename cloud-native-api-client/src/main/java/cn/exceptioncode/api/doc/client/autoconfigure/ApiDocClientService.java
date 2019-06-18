package cn.exceptioncode.api.doc.client.autoconfigure;


import cn.exceptioncode.api.doc.client.autoconfigure.properties.ApiDocClientProperties;
import com.google.common.collect.Lists;
import com.google.common.reflect.Reflection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.beans.BeanInfo;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
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
                        Method[] methods = ReflectionUtils.getAllDeclaredMethods(o.getClass());
                        for (Method method : methods) {
                            Annotation annotation =  method.getAnnotation(RequestMapping.class);
                            if(annotation instanceof  RequestMapping){
                               RequestMethod[]  requestMethods = ((RequestMapping) annotation).method();
                               if(requestMethods.length>1){

                               }else {

                               }
                            }
                        }
                    }
                    log.warn("alias:{},controller:{}", s, o);
                }

        );

    }

    public String getControllerBasePackage() {
        return this.apiDocClientProperties.getControllerBasePackage();
    }
}
