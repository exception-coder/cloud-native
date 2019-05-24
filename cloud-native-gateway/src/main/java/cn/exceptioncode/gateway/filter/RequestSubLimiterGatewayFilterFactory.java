package cn.exceptioncode.gateway.filter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.http.server.reactive.ServerHttpRequest;

@ConfigurationProperties("spring.cloud.gateway.filter.request-sub-limiter")
public class RequestSubLimiterGatewayFilterFactory extends AbstractGatewayFilterFactory<RequestSubLimiterGatewayFilterFactory.Config> {

	private Long expireTime = 1L;

	private final KeyResolver defaultKeyResolver;

	private final RateLimiter subLimitRateLimiter;


	public Long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}

	public RateLimiter getSubLimitRateLimiter() {
		return subLimitRateLimiter;
	}

	public RequestSubLimiterGatewayFilterFactory(RateLimiter subLimitRateLimiter,
												 KeyResolver defaultKeyResolver) {
		super(Config.class);
		this.subLimitRateLimiter = subLimitRateLimiter;
		this.defaultKeyResolver = defaultKeyResolver;
	}

	@Override
	public GatewayFilter apply(Config config) {
		KeyResolver resolver = getOrDefault(config.keyResolver, defaultKeyResolver);
		RateLimiter<Object> limiter = getOrDefault(config.rateLimiter,
				subLimitRateLimiter);
		// grab configuration from Config object
		return (exchange, chain) -> {
            //If you want to build a "pre" filter you need to manipulate the
            //request before calling chain.filter
            ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
            //use builder to manipulate the request

			return resolver.resolve(exchange).flatMap(defaultKeyResolver ->{
// TODO: 19-5-24
				return chain.filter(exchange);
			});

//            return chain.filter(exchange.mutate().request(request).build());
		};
	}

	private <T> T getOrDefault(T configValue, T defaultValue) {
		return (configValue != null) ? configValue : defaultValue;
	}

	public static class Config {

		//Put the configuration properties for your filter here

		private KeyResolver keyResolver;

		private RateLimiter rateLimiter;


		public KeyResolver getKeyResolver() {
			return keyResolver;
		}

		public RequestSubLimiterGatewayFilterFactory.Config setKeyResolver(KeyResolver keyResolver) {
			this.keyResolver = keyResolver;
			return this;
		}

		public RateLimiter getRateLimiter() {
			return rateLimiter;
		}

		public RequestSubLimiterGatewayFilterFactory.Config setRateLimiter(RateLimiter rateLimiter) {
			this.rateLimiter = rateLimiter;
			return this;
		}

	}

}