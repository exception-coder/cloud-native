package cn.exceptioncode.webapp.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationListener;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;


/**
 *
 * nacos config 配置管理测试
 *
 * @author zhangkai
 */
@RestController
@RequestMapping("/config")
@RefreshScope
public class ConfigController {

    /**
     *
     * curl -X POST "http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=cloud-service.properties&group=DEFAULT_GROUP&content=useLocalCache=true"
     *
     *
     */

    @Value("${useLocalCache:false}")
    private boolean useLocalCache;

    @RequestMapping("/get")
    public boolean get() {
        return useLocalCache;
    }

}