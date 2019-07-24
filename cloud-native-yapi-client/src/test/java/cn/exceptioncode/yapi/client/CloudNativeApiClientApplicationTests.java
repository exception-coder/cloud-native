package cn.exceptioncode.yapi.client;

import cn.exceptioncode.yapi.client.dto.ApiDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CloudNativeApiClientApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CloudNativeApiClientApplicationTests {

//	@Autowired
//	YapiClient yapiClient;
//
//	@Autowired
//	YapiService yapiService;

	@Test
	public void contextLoads() {
		ApiDTO apiDTO = new ApiDTO();
		apiDTO.setTitle("开发调试测试接口");
		apiDTO.setPath("/api/group/list");
		apiDTO.setDesc("接口描述。。。。 开发调试数据");
		apiDTO.setMethod("GET");
		apiDTO.setStatus("undone");
		List<Map<String,String>> reqParams = new ArrayList<>(3);
		apiDTO.setReq_params(reqParams);
		apiDTO.setRes_body("hello world");
		apiDTO.setRes_body_type("raw");
		// 暂时不知道有什么用
		apiDTO.setSwitch_notice(false);
		apiDTO.setMessage("message");
//		System.out.println(yapiClient.interfaceSave(apiDTO,yapiService.loginCookie()));
	}


	@Test
    public void test(){
        System.out.println( isInPackage("cn.exceptioncode.*.client",this.getClass().getPackage().getName()));
        System.out.println( isInPackage("cn.exceptioncode.**.client",this.getClass().getPackage().getName()));
        System.out.println( isInPackage("cn.exceptioncode.yapii.**",this.getClass().getPackage().getName()));

    }

    private boolean isInPackage(String basePackage,String classPackage){
        String[] strArr1 = basePackage.split("\\.");
        String[] strArr2 = classPackage.split("\\.");
        boolean flag = true;
        for (int i = 0; i < strArr1.length; i++) {
            if("**".equals(strArr1[i])){
                break;
            }
            if(!"*".equals(strArr1[i])){
                if(!strArr1[i].equals(strArr2[i])){
                    flag = false;
                    break;
                }
            }

        }
        return flag;
    }


}
