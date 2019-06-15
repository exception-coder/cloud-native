package cn.exceptioncode.api.doc.client.feign;

import cn.exceptioncode.api.doc.client.dto.AddApiDTO;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @author zhangkai
 */
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
    String interfaceAdd(@RequestBody AddApiDTO addApiDTO);


}
