package cn.exceptioncode.gateway.filter;

import com.iotechina.base.common.starter.enums.DefaultStatusEnum;
import com.iotechina.base.gateway.server.config.GatewayConfig;
import com.iotechina.base.gateway.server.service.DistributedLockService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.setResponseStatus;

/**
 * @author zhangkai
 */
@Slf4j
@ConfigurationProperties("spring.cloud.gateway.filter.request-sub-limiter")
public class RequestSubLimiterGatewayFilterFactory extends AbstractGatewayFilterFactory<RequestSubLimiterGatewayFilterFactory.Config> {

    private Integer expireTime = 1;

    private final GatewayConfig.ReqSerNumKeyResolver reqSerNumKeyResolver;

    private final DistributedLockService distributedLockService;


    public Integer getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Integer expireTime) {
        this.expireTime = expireTime;
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


            return resolver.resolve(exchange).flatMap(keyResolver ->

                 distributedLockService.tryGetDistributedLock(keyResolver.get(GatewayConfig.APPID_HEADER),
                        keyResolver.get(GatewayConfig.REQUEST_SERIAL_HEADER),
                        expireTime).flatMap(baseResponse -> {
                    if (baseResponse.getCode() == DefaultStatusEnum.SUCCESS.code()) {
                        return chain.filter(exchange)
                        .then(Mono.fromRunnable(()->
                                {
//                                 请求处理完成 释放锁
                                log.info("请求处理完成 释放锁");
                               try{
                                   System.out.println(new OkHttpClient().newCall(new Request.Builder()
                                        .url("http://localhost:8080/gateway/releaseDistributedLock?appId=pay_service&reqSeriNum=20190527190423233")
                                        .delete().build()).execute().body().string());
                               }catch (IOException e){
                                   e.printStackTrace();
                               }

                            distributedLockService.releaseDistributedLock(keyResolver.get(GatewayConfig.APPID_HEADER),
                                    keyResolver.get(GatewayConfig.REQUEST_SERIAL_HEADER));
                        }
                        ));
                    } else {
                        setResponseStatus(exchange, config.getStatusCode());
                        DataBuffer buffer = exchange.getResponse().bufferFactory()
                                .wrap(baseResponse.getMessage().getBytes());
                        return exchange.getResponse().writeWith(Flux.just(buffer));
                    }
                })

            );


//            return chain.filter(exchange.mutate().request(request).build());
        };
    }

    private <T> T getOrDefault(T configValue, T defaultValue) {
        return (configValue != null) ? configValue : defaultValue;
    }

    public static class Config {

        //Put the configuration properties for your filter here

        private GatewayConfig.ReqSerNumKeyResolver keyResolver;

        private RateLimiter rateLimiter;

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

        public RateLimiter getRateLimiter() {
            return rateLimiter;
        }

        public RequestSubLimiterGatewayFilterFactory.Config setRateLimiter(RateLimiter rateLimiter) {
            this.rateLimiter = rateLimiter;
            return this;
        }

    }

}