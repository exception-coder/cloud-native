package cn.exceptioncode.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class GatewayController {

    @GetMapping("/header_route")
    public Mono<String> header_route(){
        return Mono.just("header_route");
    }
}
