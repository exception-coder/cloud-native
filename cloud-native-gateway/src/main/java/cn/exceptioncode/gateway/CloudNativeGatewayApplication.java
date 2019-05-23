package cn.exceptioncode.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.factory.RequestRateLimiterGatewayFilterFactory;

@SpringBootApplication
public class CloudNativeGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudNativeGatewayApplication.class, args);
    }

}
