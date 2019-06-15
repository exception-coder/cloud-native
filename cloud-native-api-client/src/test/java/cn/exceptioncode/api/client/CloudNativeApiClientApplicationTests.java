package cn.exceptioncode.api.client;

import cn.exceptioncode.api.doc.client.CloudNativeApiClientApplication;
import cn.exceptioncode.api.doc.client.dto.AddApiDTO;
import cn.exceptioncode.api.doc.client.feign.YapiClient;
import cn.exceptioncode.api.doc.client.service.YapiService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CloudNativeApiClientApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CloudNativeApiClientApplicationTests {

	@Autowired
	YapiClient yapiClient;

	@Autowired
	YapiService yapiService;

	@Test
	public void contextLoads() {
		AddApiDTO addApiDTO = new AddApiDTO();
		addApiDTO.setTitle("开发调试测试接口");
		addApiDTO.setPath("/api/group/list");
		addApiDTO.setDesc("接口描述。。。。 开发调试数据");
		addApiDTO.setMethod("GET");
		addApiDTO.setStatus("undone");
		List<Map<String,String>> reqParams = new ArrayList<>(3);
		addApiDTO.setReq_params(reqParams);
		addApiDTO.setRes_body("hello world");
		addApiDTO.setRes_body_type("raw");
		// 暂时不知道有什么用
		addApiDTO.setSwitch_notice(false);
		addApiDTO.setMessage("message");
		System.out.println(yapiClient.interfaceSave(addApiDTO,yapiService.loginCookie()));
	}

}
