package cn.exceptioncode.gateway.controller;

import cn.exceptioncode.common.dto.BaseResponse;
import cn.exceptioncode.gateway.service.DistributedLockService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RequestMapping("/gateway")
@RestController
public class GatewayController {


    private final DistributedLockService distributedLockService;


    public GatewayController(DistributedLockService distributedLockService) {
        this.distributedLockService = distributedLockService;
    }

    @GetMapping("/busy")
    public Mono<BaseResponse> busy() {
        return Mono.just(BaseResponse.error("系统繁忙~"));
    }

    @DeleteMapping("/releaseDistributedLock")
    public Mono<BaseResponse> releaseDistributedLock(@RequestParam("appId") String appId,
                                             @RequestParam("reqSeriNum") String reqSeriNum) {
        return distributedLockService.releaseDistributedLock(appId,reqSeriNum);
    }

}
