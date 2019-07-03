package cn.exceptioncode.webapp.controller;

import cn.exceptioncode.yapi.doc.client.autoconfigure.ApiDocClientService;
import cn.exceptioncode.common.annotations.ParamDesc;
import cn.exceptioncode.common.dto.BaseResponse;
import cn.exceptioncode.common.dto.DogDTO;
import cn.exceptioncode.webapp.service.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;


/**
 * @author zhangkai
 */
@RestController
public class WebController {

    @Autowired(required = false)
    ApiDocClientService apiDocClientService;

    @Autowired
    WebService webService;


    @PutMapping(value = "/test/javadoc/{foo}", name = "api-client调试使用接口")
    public
    BaseResponse<DogDTO>
//    Mono<BaseResponse<DogDTO>>
    javadoc(@RequestParam(name = "userName", required = false) String userName,
            @ParamDesc(example = "foo_example", desc = "foo_desc") @PathVariable(name = "foo") String foo,
            @ParamDesc(example = "bar_example", desc = "bar_desc") @RequestParam(name = "bar", required = false) String bar,
            String code,
            @RequestBody DogDTO dogDTO) {
        return BaseResponse.success(new DogDTO(foo, 1, bar));
//        return Mono.just(BaseResponse.success(new DogDTO()));
    }


    @GetMapping("/exception")
    public void exception() throws Exception {
        webService.service();
    }

    @GetMapping(value = "/busy", produces = MediaType.TEXT_PLAIN_VALUE)
    public String busy() {
        return "busy";
    }


    @GetMapping("/proxied_route")
    public String proxiedRoute(ServerHttpRequest httpRequest) {
        String ipAddress = httpRequest.getRemoteAddress().getAddress().getHostAddress();
        System.out.println(ipAddress);
        return "proxied_route";

    }


}
