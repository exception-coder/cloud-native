package cn.exceptioncode.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.function.Consumer;


@Configuration
/**
 *
 * 如果您想保留Spring Boot WebFlux的功能，并且想要添加额外的 WebFlux configuration ，则可以添加自己的 @Configuration 类 WebFluxConfigurer 但 不
 * @EnableWebFlux 。
 * 如果你想完全控制Spring WebFlux，你可以添加自己的 @Configuration 注释 @EnableWebFlux 。
 */
//@EnableWebFlux
/**
 *
 * @author zhangkai
 */
public class WebFluxConfig implements WebFluxConfigurer {

    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        configurer.defaultCodecs().enableLoggingRequestDetails(true);
    }


    @Bean
    public WebClient webClient(){
        Consumer<ClientCodecConfigurer> consumer = clientCodecConfigurer -> clientCodecConfigurer.defaultCodecs().enableLoggingRequestDetails(true);
        WebClient webClient = WebClient.builder().exchangeStrategies(ExchangeStrategies.builder().codecs(consumer).build()).build();
        return webClient;
    }


}
