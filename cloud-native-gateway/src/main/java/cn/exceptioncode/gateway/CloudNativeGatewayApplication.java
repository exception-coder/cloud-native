package cn.exceptioncode.gateway;

import cn.exceptioncode.gateway.filter.DecryptVerifySignGatewayFilterFactory;
import cn.exceptioncode.gateway.filter.RequestSubLimiterGatewayFilterFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


/**
 *
 * @author zhangkai
 */
@EnableDiscoveryClient
@EnableConfigurationProperties({RequestSubLimiterGatewayFilterFactory.class, DecryptVerifySignGatewayFilterFactory.class})
@SpringBootApplication
public class CloudNativeGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudNativeGatewayApplication.class, args);
    }

}
