package cn.exceptioncode.cloudnativeapiclient;

import cn.exceptioncode.api.doc.client.CloudNativeApiClientApplication;
import cn.exceptioncode.api.doc.client.feign.YapiClient;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CloudNativeApiClientApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CloudNativeApiClientApplicationTests {

	@Autowired
	YapiClient yapiClient;

	@Test
	public void contextLoads() {
		Map<String,String> map = new HashMap<>(2);
		map.put("email","425485346@qq.com");
		map.put("password","ymfe.org");
		Response response = yapiClient.userLogin(map);
		Collection<String> list = response.headers().get("set-cookie");
		String cookie = "";
		Iterator<String> iterator = list.iterator();
		boolean isFirst = true;
		while (iterator.hasNext()){
			if(!isFirst){
				cookie+=";";
			}
			cookie+=iterator.next();
			isFirst = false;
		}
		log.warn("Cookie:{}",cookie);

	}

}
