package cn.exceptioncode.api.doc.client.util;

import cn.exceptioncode.api.doc.client.dto.ApiDTO;
import com.alibaba.fastjson.JSON;
import okhttp3.Response;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author zhangkai
 */
public class YapiClientUtil {

    private YapiClientUtil(){

    }

    private static final String YAPI_URL = "http://127.0.0.1:3000/api";
    private static final String LOGIN_URL = YAPI_URL + "/user/login";
    private static final String SAVE_API_URL = YAPI_URL + "/interface/save";

    public static String saveApi(ApiDTO apiDTO) throws IOException {
        Map<String, String> map = new HashMap<>(2);
        map.put("email", "425485346@qq.com");
        map.put("password", "ymfe.org");
        Response response = OkHttpUtil.getResWithPost(LOGIN_URL, JSON.toJSONString(map));
        map.clear();
        List<String> headers = response.headers("set-cookie");

        String cookie = "";
        Iterator<String> iterator = headers.iterator();
        boolean isFirst = true;
        while (iterator.hasNext()) {
            if (!isFirst) {
                cookie += ";";

            }
            cookie += iterator.next();
            isFirst = false;
        }
        map.put("Cookie", cookie);
        return OkHttpUtil.post(SAVE_API_URL, JSON.toJSONString(apiDTO), map);
    }


    @Test
    public void test() throws Exception {
        ApiDTO apiDTO = new ApiDTO();
        saveApi(apiDTO);
    }
}





