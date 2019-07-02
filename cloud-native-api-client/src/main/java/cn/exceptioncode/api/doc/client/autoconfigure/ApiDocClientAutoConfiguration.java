package cn.exceptioncode.api.doc.client.autoconfigure;

import cn.exceptioncode.api.doc.client.autoconfigure.properties.ApiDocClientProperties;
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
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
// TODO: 19-6-28
/**
 *
 * 不够优雅 用户习惯于pom文件添加依赖后即代表用户默许开启组件 不习惯于显性配置属性开启
 * 使用依赖配置对象 `ApiDocClientProperties` 申明关闭组件
 *
 */
//@ConditionalOnProperty(prefix = "cn.exceptioncode.api.code.doc",name = "enable")
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
