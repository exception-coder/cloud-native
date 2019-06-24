package cn.exceptioncode.mybatis;

import cn.exceptioncode.mybatis.domain.City;
import cn.exceptioncode.mybatis.mapper.CityMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author zhangkai
 */
@SpringBootApplication
public class CloudNativeMybatisApplication{


    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(CloudNativeMybatisApplication.class);
        springApplication.run();
    }

    private final CityMapper cityMapper;

    public CloudNativeMybatisApplication(CityMapper cityMapper) {
        this.cityMapper = cityMapper;
    }

    @Bean
    CommandLineRunner mybatisCommandLineRunner() {
        return args -> {
            City city = new City();
            city.setName("San Francisco");
            city.setState("CA");
            city.setCountry("US");
            cityMapper.insert(city);
            System.out.println(this.cityMapper.findById(city.getId()));
        };
    }

}
