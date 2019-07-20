package cn.exceptioncode.yapi.client.autoconfigure;

import cn.exceptioncode.yapi.client.autoconfigure.properties.ApiDocClientProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.*;
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
@ConditionalOnClass(RestTemplate.class)
@ConditionalOnWebApplication
        //(type = ConditionalOnWebApplication.Type.REACTIVE)
// TODO: 19-6-28
@ConditionalOnProperty(prefix = "cn.exceptioncode.api.code.doc",name = "enable",matchIfMissing = true)
public class ApiDocClientAutoConfiguration {

    @Autowired
    ApiDocClientProperties apiDocClientProperties;

    @Autowired
    RestTemplate restTemplate;


    @Bean(name = "apiDocClientService")
    @ConditionalOnMissingBean(ApiDocClientService.class)
    @ConditionalOnBean(RestTemplate.class)
    public ApiDocClientService apiDocClientService(){
        ApiDocClientService apiDocClientService = new ApiDocClientService(apiDocClientProperties, restTemplate);
        return apiDocClientService;
    }


    @Bean
    @ConditionalOnMissingBean(RestTemplate.class)
    RestTemplate apiClientRestTemplate(){
        return new RestTemplate();
    }

}
