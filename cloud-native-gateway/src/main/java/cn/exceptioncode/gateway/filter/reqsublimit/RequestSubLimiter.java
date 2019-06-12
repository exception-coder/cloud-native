package cn.exceptioncode.gateway.filter.reqsublimit;


import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.cloud.gateway.event.FilterArgsEvent;
import org.springframework.cloud.gateway.support.AbstractConfigurable;
import org.springframework.cloud.gateway.support.ConfigurationUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.validation.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 *
 * @author zhangkai
 */
@Data
public class RequestSubLimiter extends AbstractConfigurable<RequestSubLimiter.Config> implements ApplicationListener<FilterArgsEvent>, ApplicationContextAware {

    private Map<String, Config> config = new HashMap<>();

    private AtomicBoolean initialized = new AtomicBoolean(false);

    private Validator validator;

    public static final String CONFIGURATION_PROPERTY_NAME = "sub-limiter";


    @Override
    @SuppressWarnings("unchecked")
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        if (initialized.compareAndSet(false, true)) {
            if (context.getBeanNamesForType(Validator.class).length > 0) {
                this.setValidator(context.getBean(Validator.class));
            }
        }
    }

    @Override
    public void onApplicationEvent(FilterArgsEvent event) {
        Map<String, Object> args = event.getArgs();

        if (args.isEmpty() || !hasRelevantKey(args)) {
            return;
        }

        String routeId = event.getRouteId();
        Config routeConfig = newConfig();
        ConfigurationUtils.bind(routeConfig, args,
                CONFIGURATION_PROPERTY_NAME, CONFIGURATION_PROPERTY_NAME, validator);
        getConfig().put(routeId, routeConfig);
    }


    public Map<String, Config> getConfig() {
        return this.config;
    }

    private boolean hasRelevantKey(Map<String, Object> args) {
        return args.keySet().stream()
                .anyMatch(key -> key.startsWith(CONFIGURATION_PROPERTY_NAME + "."));
    }

    /**
     *
     * 防重复请求作用时间 默认1秒
     *
     */
    private Integer expireTime = 1;

    /**
     *
     * 是否强制防重复 默认强制 只有作用时间结束才允许继续发起同一请求
     *
     */
    private Boolean enforce = false;


    public RequestSubLimiter(Class<Config> configClass) {
        super(configClass);
    }


    public static class Config {
        private Integer expireTime;

        private Boolean enforce = true;

        public Integer getExpireTime() {
            return expireTime;
        }

        public RequestSubLimiter.Config setExpireTime(int expireTime) {
            this.expireTime = expireTime;
            return this;
        }

        public Boolean getEnforce() {
            return enforce;
        }

        public RequestSubLimiter.Config setEnforce(boolean enforce) {
            this.enforce = enforce;
            return this;
        }

        @Override
        public String toString() {
            return "Config{" +
                    "expireTime=" + expireTime +
                    ", enforce=" + enforce +
                    '}';
        }
    }
}
