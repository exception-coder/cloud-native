package cn.exceptioncode.api.doc.client.autoconfigure;

import cn.exceptioncode.api.doc.client.autoconfigure.properties.ApiDocClientProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author zhangkai
 */
@Configuration
@EnableConfigurationProperties(ApiDocClientProperties.class)
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "cn.exceptioncode.api.code.doc",name = "enable",havingValue = "true")
public class ApiDocClientAutoConfiguration {

    @Autowired
    ApiDocClientProperties apiDocClientProperties;


    @Bean(name = "apiDocClientService")
    @ConditionalOnMissingBean(ApiDocClientService.class)
    public ApiDocClientService apiDocClientService(){
        ApiDocClientService apiDocClientService = new ApiDocClientService(apiDocClientProperties);
        return apiDocClientService;
    }

}
