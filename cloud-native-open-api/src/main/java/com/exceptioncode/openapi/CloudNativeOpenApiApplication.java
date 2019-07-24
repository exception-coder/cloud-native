package com.exceptioncode.openapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 *
 * 开启 `FeignClient` 自动扫描
 */
@EnableFeignClients
@SpringBootApplication
public class CloudNativeOpenApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudNativeOpenApiApplication.class, args);
    }

}
