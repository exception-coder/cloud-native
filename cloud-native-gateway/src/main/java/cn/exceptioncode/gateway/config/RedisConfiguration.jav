package cn.exceptioncode.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.PreDestroy;

@Configuration
/**
 *
 * @author zhangkai
 */
public class RedisConfiguration {

    @Autowired
    RedisConnectionFactory factory;


    @Bean
    public LettuceConnectionFactory redisConnectionFactory(@Value("${spring.redis.host}")
                                                                   String redisHostName, @Value("${spring.redis.password}") String redisPassword) {

        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisHostName);
        redisStandaloneConfiguration.setPassword(redisPassword);
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }


    /**
     * Configures a {@link ReactiveRedisTemplate} with {@link String} keys and {@link GenericJackson2JsonRedisSerializer}.
     */
    @Bean(name = "reactiveJsonObjectRedisTemplate")
    public ReactiveRedisTemplate<String, Object> reactiveJsonObjectRedisTemplate(
            ReactiveRedisConnectionFactory connectionFactory) {

        RedisSerializationContext.RedisSerializationContextBuilder<String, Object> builder = RedisSerializationContext
                .newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, Object> serializationContext = builder
                .value(new GenericJackson2JsonRedisSerializer("_type")).build();

        return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
    }

    /**
     * Clear database before shut down.
     */
    public @PreDestroy
    void flushTestDb() {
        factory.getConnection().flushDb();
    }


    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}