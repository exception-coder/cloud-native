package cn.exceptioncode.webapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 *
 * @author zhangkai
 */
@EnableDiscoveryClient
@SpringBootApplication
public class CloudNativeWebApplication {

    @Autowired
    private Environment env;

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    public static void main(String[] args) {
        SpringApplication.run(CloudNativeWebApplication.class, args);
    }


    /**
     *
     * http://localhost:8081/test/echo/2018
     *
     */
    @RestController
    public class TestController {

        private final RestTemplate restTemplate;

        @Autowired
        public TestController(RestTemplate restTemplate) {this.restTemplate = restTemplate;}

        @RequestMapping(value = "/test/echo/{str}", method = RequestMethod.GET,
        produces = MediaType.TEXT_XML_VALUE)
        public String echo(@PathVariable String str) {
            return restTemplate.getForObject("http://cloud-service/echo/" + str, String.class);
        }
    }

    @RestController
    class EchoController {
        @RequestMapping(value = "/echo/{string}", method = RequestMethod.GET)
        public String echo(@PathVariable String string) throws UnknownHostException {
            return Inet4Address.getLocalHost().getHostName() +":"+env.getProperty("server.port")+" Hello Nacos Discovery " + string;
        }
    }
}
