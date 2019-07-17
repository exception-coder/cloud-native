package cn.exceptioncode.yapi.client.autoconfigure;

import cn.exceptioncode.yapi.client.autoconfigure.properties.ApiDocClientProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author zhangkai
 */
@Configuration
@EnableConfigurationProperties(ApiDocClientProperties.class)
@ConditionalOnWebApplication
        //(type = ConditionalOnWebApplication.Type.REACTIVE)
// TODO: 19-6-28
@ConditionalOnProperty(prefix = "cn.exceptioncode.api.code.doc",name = "enable",matchIfMissing = true)
public class ApiDocClientAutoConfiguration {

    @Autowired
    ApiDocClientProperties apiDocClientProperties;

    @Qualifier("apiClientRestTemplate")
    @Autowired
    RestTemplate apiClientRestTemplate;


    @Bean(name = "apiDocClientService")
    @ConditionalOnMissingBean(ApiDocClientService.class)
    public ApiDocClientService apiDocClientService(){
        ApiDocClientService apiDocClientService = new ApiDocClientService(apiDocClientProperties, apiClientRestTemplate);
        return apiDocClientService;
    }


    @Bean
    RestTemplate apiClientRestTemplate(){
        return new RestTemplate();
    }

}
