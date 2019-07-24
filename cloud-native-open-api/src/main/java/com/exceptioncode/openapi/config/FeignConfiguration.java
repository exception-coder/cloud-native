package com.exceptioncode.openapi.config;

import feign.Contract;
import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
/**
 *
 * @author zhangkai
 */
public class FeignConfiguration {

    @Bean
    public Logger.Level logger(){
        return Logger.Level.FULL;
    }

    @Bean
    public Contract useFeignAnnotations() {
        return new Contract.Default();
    }
}