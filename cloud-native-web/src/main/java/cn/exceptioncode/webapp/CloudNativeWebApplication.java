package cn.exceptioncode.webapp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.List;

/**
 * @author zhangkai
 */
@Slf4j
@EnableDiscoveryClient
@SpringBootApplication
public class CloudNativeWebApplication{

    @Autowired
    private Environment env;

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }



    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(CloudNativeWebApplication.class);
        springApplication.addListeners(new StartingEvent());
        springApplication.run();
    }


    /**
     * http://localhost:8081/test/echo/2018
     */
    @RestController
    public class TestController {

        private final RestTemplate restTemplate;

        @Autowired
        public TestController(RestTemplate restTemplate) {
            this.restTemplate = restTemplate;
        }

        @RequestMapping(value = "/test/echo/{str}", method = RequestMethod.GET,
                produces = MediaType.TEXT_HTML_VALUE)
        public String echo(@PathVariable String str, @RequestParam("data") String data, ServerHttpRequest httpRequest) {
            log.info("请求参数data：{}", data);
            List<String> datas = httpRequest.getQueryParams().get("data");
            log.info("请求参数datas：{}", datas);
            String text = restTemplate.getForObject("http://cloud-service/echo/" + str, String.class);
            return text;
        }
    }

    @RestController
    class EchoController {
        @RequestMapping(value = "/echo/{string}", method = RequestMethod.GET,
                produces = MediaType.TEXT_HTML_VALUE)
        public String echo(@PathVariable String string) throws UnknownHostException {
            return Inet4Address.getLocalHost().getHostName() + ":" + env.getProperty("server.port") + " Hello Nacos Discovery " + string;
        }
    }






}
