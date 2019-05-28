package cn.exceptioncode.gateway.controller;

import cn.exceptioncode.common.dto.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RequestMapping("/gateway")
@RestController
public class GatewayController {



    @GetMapping("/busy")
    public Mono<BaseResponse> busy() {
        return Mono.just(BaseResponse.error("系统繁忙~"));
    }


}
