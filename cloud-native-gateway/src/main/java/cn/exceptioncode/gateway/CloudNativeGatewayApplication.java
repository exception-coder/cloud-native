package cn.exceptioncode.gateway;

import cn.exceptioncode.gateway.filter.RequestSubLimiterGatewayFilterFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.factory.RequestRateLimiterGatewayFilterFactory;


/**
 *
 * @author zhangkai
 */
@EnableConfigurationProperties(RequestSubLimiterGatewayFilterFactory.class)
@SpringBootApplication
public class CloudNativeGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudNativeGatewayApplication.class, args);
    }

}
