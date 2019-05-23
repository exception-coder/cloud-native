package cn.exceptioncode.webapp.controller;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class WebController {


    @GetMapping("/foo/index")
    public Mono<String> index() {
        return Mono.just("index");
    }


    @GetMapping("/proxied_route")
    public Mono<String> proxiedRoute(ServerHttpRequest httpRequest) {
        String ipAddress = httpRequest.getRemoteAddress().getAddress().getHostAddress();
        System.out.println(ipAddress);
        return Mono.just("proxied_route");
    }


}
