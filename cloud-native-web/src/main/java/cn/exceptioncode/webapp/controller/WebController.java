package cn.exceptioncode.webapp.controller;

import cn.exceptioncode.api.doc.client.annotations.ParamDesc;
import cn.exceptioncode.api.doc.client.autoconfigure.ApiDocClientService;
import cn.exceptioncode.webapp.service.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


/**
 *
 * @author zhangkai
 */
@RestController
public class WebController {

    @Autowired(required = false)
    ApiDocClientService apiDocClientService;

    @Autowired
    WebService webService;


    @GetMapping(value = "/test/javadoc/{foo}",name = "debug测试接口foo_bar")
    public Mono<String> javadoc(@ParamDesc(desc = "用户名称",example = "泡泡熊")  @RequestParam(name = "userName",required = false) String userName,
                                @PathVariable(name = "foo") String foo,
                                @RequestParam(name = "bar") String bar){
        return Mono.just("hello world javadoc,userName:"+userName+",foo:"+foo+",bar:"+bar);
    }


    @GetMapping("/exception")
    public void exception() throws Exception {
        webService.service();
    }

    @GetMapping("/foo/index")
    public Mono<String> index() {
        return Mono.just(apiDocClientService.getControllerBasePackage());
    }

    @GetMapping(value = "/busy", produces = MediaType.TEXT_PLAIN_VALUE)
    public Mono<String> busy() {
        return Mono.just("busy");
    }


    @GetMapping("/proxied_route")
    public Mono<String> proxiedRoute(ServerHttpRequest httpRequest) {
        String ipAddress = httpRequest.getRemoteAddress().getAddress().getHostAddress();
        System.out.println(ipAddress);
        return Mono.just("proxied_route");
    }


}
