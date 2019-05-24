package cn.exceptioncode.gateway.filter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;

@ConfigurationProperties("spring.cloud.gateway.filter.request-sub-limiter")
public class RequestSubLimiterGatewayFilterFactory extends AbstractGatewayFilterFactory<RequestSubLimiterGatewayFilterFactory.Config> {

	private Long expireTime = 1L;


	public Long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}

	public RequestSubLimiterGatewayFilterFactory() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		// grab configuration from Config object
		return (exchange, chain) -> {
            //If you want to build a "pre" filter you need to manipulate the
            //request before calling chain.filter
            ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
            //use builder to manipulate the request

			return chain.filter(exchange);
//            return chain.filter(exchange.mutate().request(request).build());
		};
	}

	public static class Config {

        //Put the configuration properties for your filter here
		private Long expireTime;

		public RequestSubLimiterGatewayFilterFactory.Config setExpireTime(Long expireTime) {
			this.expireTime = expireTime;
			return this;
		}
	}

}