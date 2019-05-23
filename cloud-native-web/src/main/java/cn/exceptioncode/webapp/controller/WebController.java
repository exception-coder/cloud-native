package cn.exceptioncode.webapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class WebController {
    @GetMapping("/index")
    public Mono<String> index(){
        return Mono.just("index");
    }
}
