package com.exceptioncode.openapi;

import com.exceptioncode.openapi.tencent.wkweixin.GetTokenService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CloudNativeOpenApiApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CloudNativeOpenApiApplicationTests {

    @Autowired
    private GetTokenService getTokenService;

    @Test
    public void contextLoads() {
        GetTokenService.AccessTokenResponseDTO accessTokenResponseDTO = getTokenService.getToken("001","001");
        System.out.println(accessTokenResponseDTO);
    }

}
