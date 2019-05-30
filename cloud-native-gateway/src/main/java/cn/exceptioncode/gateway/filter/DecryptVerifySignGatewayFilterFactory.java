package cn.exceptioncode.gateway.filter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

/**
 * @author zhangkai
 */
@ConfigurationProperties("spring.cloud.gateway.filter.decrypt-verify-sign")
public class DecryptVerifySignGatewayFilterFactory extends AbstractGatewayFilterFactory<DecryptVerifySignGatewayFilterFactory.Config> {


    private String dataKey;

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    //    private final DecryptKeyResolver decryptKeyResolver;
//
//    public DecryptVerifySignGatewayFilterFactory(DecryptKeyResolver decryptKeyResolver) {
//        this.decryptKeyResolver = decryptKeyResolver;
//    }

    @Override
    public GatewayFilter apply(Config config) {
//        String dataKey = decryptKeyResolver.getDataKey();
//        String signKey = decryptKeyResolver.getSignKey();
        return (exchange, chain) ->  chain.filter(exchange);
    }

    private <T> T getOrDefault(T configValue, T defaultValue) {
        return (configValue != null) ? configValue : defaultValue;
    }

    public static class  Config{

    }
}
