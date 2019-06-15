package cn.exceptioncode.cloudnativeapiclient;

import cn.exceptioncode.api.doc.client.CloudNativeApiClientApplication;
import cn.exceptioncode.api.doc.client.service.YapiService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CloudNativeApiClientApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CloudNativeApiClientApplicationTests {


	@Autowired
	YapiService yapiService;

	@Test
	public void contextLoads() {
		System.out.println(yapiService.loginCookie());
	}

}
