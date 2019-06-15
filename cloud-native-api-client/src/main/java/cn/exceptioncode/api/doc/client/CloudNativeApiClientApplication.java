package cn.exceptioncode.api.doc.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 *
 * @author zhangkai
 */
@SpringBootApplication
@EnableFeignClients
public class CloudNativeApiClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudNativeApiClientApplication.class, args);
	}

}
