package cn.exceptioncode.api.doc.client.feign;

import cn.exceptioncode.api.doc.client.dto.ApiDTO;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

/**
 * @author zhangkai
 */
@Service
@FeignClient(name = "yapi",url = "http://129.28.173.232:3000/api")
public interface YapiClient {

    /**
     *
     * 用户登录
     *
     * @param map
     * @return
     */
    @PostMapping("/user/login")
    Response userLogin(@RequestBody Map map);


    /**
     *
     * 新增接口
     *
     * @return
     */
    @PostMapping("/interface/add")
    String interfaceAdd(@RequestBody ApiDTO apiDTO, @RequestHeader(value = "Cookie") String cookie);


    /**
     *
     * 新增或更新接口
     *
     * @return
     */
    @PostMapping("/interface/save")
    String interfaceSave(@RequestBody ApiDTO apiDTO, @RequestHeader(value = "Cookie") String cookie);


}
