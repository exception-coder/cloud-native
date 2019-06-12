package cn.exceptioncode.gateway.filter;

import cn.exceptioncode.common.enums.DefaultStatusEnum;
import cn.exceptioncode.gateway.config.GatewayConfig;
import cn.exceptioncode.gateway.filter.reqsublimit.RequestSubLimiter;
import cn.exceptioncode.gateway.service.DistributedLockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.setResponseStatus;

/**
 * @author zhangkai
 */
@Slf4j
@ConfigurationProperties("spring.cloud.gateway.filter.request-sub-limiter")
public class RequestSubLimiterGatewayFilterFactory extends AbstractGatewayFilterFactory<RequestSubLimiterGatewayFilterFactory.Config> {


    private final GatewayConfig.ReqSerNumKeyResolver reqSerNumKeyResolver;

    private final DistributedLockService distributedLockService;

    private final RequestSubLimiter requestSubLimiter;



    public RequestSubLimiterGatewayFilterFactory(
            GatewayConfig.ReqSerNumKeyResolver reqSerNumKeyResolver,
            DistributedLockService distributedLockService,
            RequestSubLimiter requestSubLimiter) {
        super(Config.class);
        this.reqSerNumKeyResolver = reqSerNumKeyResolver;
        this.distributedLockService = distributedLockService;
        this.requestSubLimiter = requestSubLimiter;
    }

    @Override
    public GatewayFilter apply(Config config) {
        // grab configuration from Config object
        return (exchange, chain) -> {
            //If you want to build a "pre" filter you need to manipulate the
            //request before calling chain.filter
            ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
            //use builder to manipulate the request
            Map<String, RequestSubLimiter.Config> configMap = requestSubLimiter.getConfig();
            Route route = exchange
                    .getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
            RequestSubLimiter.Config subLimitConfig = configMap.get(route.getId());
            final Integer expireTime = subLimitConfig.getExpireTime().equals(null) ? requestSubLimiter.getExpireTime() : subLimitConfig.getExpireTime();
            final Boolean enforce = subLimitConfig.getEnforce().equals(null) ? requestSubLimiter.getEnforce() : subLimitConfig.getEnforce();
            return reqSerNumKeyResolver.resolve(exchange).flatMap(keyResolver ->
                    // 准备转发请求 获取锁
                    distributedLockService.tryGetDistributedLock(keyResolver.get(GatewayConfig.REQUEST_APPID_HEADER),
                            keyResolver.get(GatewayConfig.REQUEST_SERIAL_HEADER),
                            expireTime).flatMap(baseResponse -> {
                        if (baseResponse.getCode() == DefaultStatusEnum.SUCCESS.code()) {
                            return chain.filter(exchange)
                                    .then(Mono.fromRunnable(() ->
                                            {
                                                if (!enforce) {
                                                    // 请求处理完成 释放锁
                                                    log.info("请求处理完成 释放锁");
                                                    distributedLockService.releaseDistributedLock(keyResolver.get(GatewayConfig.REQUEST_APPID_HEADER),
                                                            keyResolver.get(GatewayConfig.REQUEST_SERIAL_HEADER)).subscribe(System.out::println);

                                                }
                                            }
                                    ));
                        } else {
                            setResponseStatus(exchange, HttpStatus.BAD_REQUEST);
                            DataBuffer buffer = exchange.getResponse().bufferFactory()
                                    .wrap(baseResponse.getMessage().getBytes());
                            return exchange.getResponse().writeWith(Flux.just(buffer));
                        }
                    })

            );


//            return chain.filter(exchange.mutate().request(request).build());
        };
    }


    public static class Config {

        //Put the configuration properties for your filter here

    }

}