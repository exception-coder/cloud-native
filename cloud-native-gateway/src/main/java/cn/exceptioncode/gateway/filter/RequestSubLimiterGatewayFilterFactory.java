package cn.exceptioncode.gateway.filter;

import cn.exceptioncode.common.enums.DefaultStatusEnum;
import cn.exceptioncode.gateway.config.GatewayConfig;
import cn.exceptioncode.gateway.service.DistributedLockService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.RequestRateLimiterGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.setResponseStatus;

/**
 * @author zhangkai
 */
@Slf4j
@ConfigurationProperties("spring.cloud.gateway.filter.request-sub-limiter")
public class RequestSubLimiterGatewayFilterFactory extends AbstractGatewayFilterFactory<RequestSubLimiterGatewayFilterFactory.Config> {

    /**
     * 防重复请求作用时间 默认1秒
     */
    private Integer expireTime = 1;

    /**
     * 是否强制防重复 默认强制 只有作用时间结束才允许继续发起同一请求
     */
    private Boolean enforce = true;
    private final GatewayConfig.ReqSerNumKeyResolver reqSerNumKeyResolver;

    private final DistributedLockService distributedLockService;


    public Integer getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Integer expireTime) {
        this.expireTime = expireTime;
    }

    public Boolean getEnforce() {
        return enforce;
    }

    public void setEnforce(Boolean enforce) {
        this.enforce = enforce;
    }

    public RequestSubLimiterGatewayFilterFactory(
            GatewayConfig.ReqSerNumKeyResolver reqSerNumKeyResolver,
            DistributedLockService distributedLockService) {
        super(Config.class);
        this.reqSerNumKeyResolver = reqSerNumKeyResolver;
        this.distributedLockService = distributedLockService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        GatewayConfig.ReqSerNumKeyResolver resolver = getOrDefault(config.keyResolver, reqSerNumKeyResolver);
        // grab configuration from Config object
        return (exchange, chain) -> {
            //If you want to build a "pre" filter you need to manipulate the
            //request before calling chain.filter
            ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
            //use builder to manipulate the request


            return resolver.resolve(exchange).flatMap(keyResolver -> {

                String appId = keyResolver.get(GatewayConfig.REQUEST_APPID_HEADER);
                String reqSerial = keyResolver.get(GatewayConfig.REQUEST_SERIAL_HEADER);
                if (StringUtils.isAnyEmpty(appId, reqSerial)) {
                    // 如果请求同中不包含  GatewayConfig.REQUEST_APPID_HEADER  GatewayConfig.REQUEST_SERIAL_HEADER 则直接方形
                    return chain.filter(exchange);
                }
                return distributedLockService.tryGetDistributedLock(appId, reqSerial,
                        expireTime).flatMap(baseResponse -> {
                    if (baseResponse.getCode() == DefaultStatusEnum.SUCCESS.code()) {
                        return chain.filter(exchange)
                                .then(Mono.fromRunnable(() ->
                                        {
                                            // 请求处理完成 释放锁
                                            if (!this.enforce) {
                                                log.info("请求处理完成 释放锁");
                                                distributedLockService.releaseDistributedLock(keyResolver.get(GatewayConfig.REQUEST_APPID_HEADER),
                                                        keyResolver.get(GatewayConfig.REQUEST_SERIAL_HEADER)).subscribe(System.out::println);

                                            }
                                        }
                                ));
                    } else {
                        setResponseStatus(exchange, config.getStatusCode());
                        DataBuffer buffer = exchange.getResponse().bufferFactory()
                                .wrap(JSON.toJSONString(baseResponse).getBytes());
                        return exchange.getResponse().writeWith(Flux.just(buffer));
                    }
                });

            });


//            return chain.filter(exchange.mutate().request(request).build());
        };
    }

    private <T> T getOrDefault(T configValue, T defaultValue) {
        return (configValue != null) ? configValue : defaultValue;
    }

    public static class Config {

        //Put the configuration properties for your filter here

        private GatewayConfig.ReqSerNumKeyResolver keyResolver;


        private HttpStatus statusCode = HttpStatus.TOO_MANY_REQUESTS;

        public HttpStatus getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(HttpStatus statusCode) {
            this.statusCode = statusCode;
        }

        public GatewayConfig.ReqSerNumKeyResolver getKeyResolver() {
            return keyResolver;
        }

        public RequestSubLimiterGatewayFilterFactory.Config setKeyResolver(GatewayConfig.ReqSerNumKeyResolver keyResolver) {
            this.keyResolver = keyResolver;
            return this;
        }


    }

}