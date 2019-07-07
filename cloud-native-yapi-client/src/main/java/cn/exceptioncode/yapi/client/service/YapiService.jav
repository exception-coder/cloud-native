package cn.exceptioncode.api.doc.client.service;

import cn.exceptioncode.api.doc.client.feign.YapiClient;
import feign.Response;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author zhangkai
 */
@Service
public class YapiService {

    private final YapiClient yapiClient;

    public YapiService(YapiClient yapiClient) {
        this.yapiClient = yapiClient;
    }

    /**
     *
     * 获取 token 相关信息
     *
     * @return
     */
    public String loginCookie() {
        Map<String, String> map = new HashMap<>(2);
        map.put("email", "425485346@qq.com");
        map.put("password", "ymfe.org");
        Response response = yapiClient.userLogin(map);
        Collection<String> list = response.headers().get("set-cookie");
        String cookie = "";
//        Iterator<String> iterator = list.iterator();
//        boolean isFirst = true;
//        while (iterator.hasNext()) {
//            if (!isFirst) {
//                cookie += ";";
//            }
//            cookie += iterator.next();
//            isFirst = false;
//        }
        return cookie;
    }
}
