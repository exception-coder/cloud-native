package cn.exceptioncode.gateway.service;


import cn.exceptioncode.common.dto.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Collections;

@Service
/**
 *
 * @author zhangkai
 */
public class DistributedLockService {

    private static final Long SUCCESS_FLAG = 1L;

//    @Autowired
//    @Qualifier("reactiveJsonObjectRedisTemplate")
//    ReactiveRedisTemplate<String, Object> reactiveJsonObjectRedisTemplate;

    @Autowired
    ReactiveRedisTemplate<String,String>  redisTemplate;


    /**
     * 获取分布式锁
     *
     * @param lockKey   锁
     * @param requestId 请求标识
     * @return 锁是否获取成功
     */
    public Mono<BaseResponse> tryGetDistributedLock(String lockKey, String requestId, Integer expireTime) {


        // 如果键不存在 则设置
        return redisTemplate.opsForValue()
                .setIfAbsent(lockKey, requestId, Duration.ofSeconds(expireTime))
                .map(el -> {
                    if(true==el){
                        return BaseResponse.success("获取锁成功");
                    }else {
                        return BaseResponse.error("获取锁失败");
                    }
                });

    }


    /**
     * 释放分布式锁
     *
     * @param lockKey   锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public Mono<BaseResponse> releaseDistributedLock(String lockKey, String requestId) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        return redisTemplate.execute(RedisScript.of(script, Long.class)
                , Collections.singletonList(lockKey), Collections.singletonList(requestId)).onErrorResume(result ->
                BaseResponse.error("释放锁失败")
        ).map((el) -> {
            if (SUCCESS_FLAG.equals(el)) {
                return BaseResponse.success("释放锁成功");
            } else {
                return BaseResponse.error("释放锁失败");
            }
        }).last();

    }


}
