package com.iotechina.base.gateway.server.filter;

import com.iotechina.base.gateway.server.config.GatewayConfig;
import com.iotechina.base.gateway.server.service.DistributedLockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ReleaseDistributedLockFilter implements GlobalFilter, Ordered {

    @Autowired
    DistributedLockService distributedLockService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String appId = exchange.getRequest().getHeaders().getFirst(GatewayConfig.APPID_HEADER);
        String reqSeriNum = exchange.getRequest().getHeaders().getFirst(GatewayConfig.REQUEST_SERIAL_HEADER);
        return distributedLockService.releaseDistributedLock(appId,reqSeriNum).flatMap(baseResponse ->
             chain.filter(exchange)
        );
//        return chain.filter(exchange).then(Mono.fromRunnable(() ->
//                distributedLockService.releaseDistributedLock(appId, reqSeriNum)));
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE - 1000;
    }
}
