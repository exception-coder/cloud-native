package cn.exceptioncode.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.support.ipresolver.RemoteAddressResolver;
import org.springframework.cloud.gateway.support.ipresolver.XForwardedRemoteAddressResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;


/**
 * @author zhangkai
 */
@Slf4j
@Configuration
public class GatewayConfig {

    public final static String APPID_HEADER = "X-Request-AppId";


    public final static String REQUEST_SERIAL_HEADER = "X-Request-Serial";


    RemoteAddressResolver resolver = XForwardedRemoteAddressResolver.maxTrustedIndex(1);


    @Bean
    KeyResolver userKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("user"));
    }


    @Bean
    ReqSerNumKeyResolver reqSerNumKeyResolver() {
        return new ReqSerNumKeyResolver();
    }

    public class ReqSerNumKeyResolver {
        public Mono<Map<String,String>> resolve(ServerWebExchange exchange) {
            String appId = exchange.getRequest().getHeaders().getFirst(APPID_HEADER);
            String reqSerial = exchange.getRequest().getHeaders().getFirst(REQUEST_SERIAL_HEADER);
            if(StringUtils.isAnyEmpty(appId,reqSerial)){
                return Mono.empty();
            }
            Map<String,String> map = new HashMap<>();
            map.put(APPID_HEADER,appId);
            map.put(REQUEST_SERIAL_HEADER,reqSerial);
            return Mono.just(map);
        }
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("proxied-route", r -> r.path("").and().path("/route/**").and()
                        .remoteAddr(resolver, "127.0.0.1")
                        .uri("http://127.0.0.1:8081"))
                .build();
//        return builder.routes()
//                .route("path_route", r -> r.path("/get")
//                        .uri("http://httpbin.org"))
//                .route("host_route", r -> r.host("*.myhost.org")
//                        .uri("http://httpbin.org"))
//                .route("rewrite_route", r -> r.host("*.rewrite.org")
//                        .filters(f -> f.rewritePath("/foo/(?<segment>.*)", "/${segment}"))
//                        .uri("http://httpbin.org"))
//                .route("hystrix_route", r -> r.host("*.hystrix.org")
//                        .filters(f -> f.hystrix(c -> c.setName("slowcmd")))
//                        .uri("http://httpbin.org"))
//                .route("hystrix_fallback_route", r -> r.host("*.hystrixfallback.org")
//                        .filters(f -> f.hystrix(c -> c.setName("slowcmd").setFallbackUri("forward:/hystrixfallback")))
//                        .uri("http://httpbin.org"))
//                .route("limit_route", r -> r
//                        .host("*.limited.org").and().path("/anything/**")
//                        .filters(f -> f.requestRateLimiter(c -> c.setRateLimiter(redisRateLimiter())))
//                        .uri("http://httpbin.org"))
//                .build();
    }


}
