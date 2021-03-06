package cn.exceptioncode.gateway.filter;

import cn.exceptioncode.common.dto.BaseResponse;
import cn.exceptioncode.common.security.CodecUtil;
import cn.exceptioncode.common.security.EncryptUtil;
import cn.exceptioncode.common.security.SignUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.util.Map;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.setResponseStatus;

/**
 * @author zhangkai
 */
@Slf4j
@ConfigurationProperties("spring.cloud.gateway.filter.decrypt-verify-sign")
public class DecryptVerifySignGatewayFilterFactory extends AbstractGatewayFilterFactory<DecryptVerifySignGatewayFilterFactory.Config> {

    /**
     * debug 使用公钥
     */
    private final static String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAojydj+dKyvC7FUejUtoiy/GxSUxhMutDoxCEwcMlB+0VxGy1siUw1T66tUWK8jCsPS9V5enDcoP5O7Rg+69bAZmJJgbvbqnNku7L44izl0MWMhRdpVBws8Lp7qN9jci/wBBuJyLGbEKarzeBtnmOt9DHF77W1eLw7jQRCPTIdNFb8pawKaQiw5cPfQ2qhtWC53LI81vwMwzaprUj3yQXOn6mN5wu/oKcV2vTFd6I8rfpQTqs/wAXpBFF7T+Cymv1yPXV2jhxRtyYhB9lSve6ZpOacMXLzG1Gmrf+Ba8sazs3eRTQW4Hs3m5TgcXbLUCqHMaFtURjpUrD9oNjTkdByQIDAQAB";

    /**
     * debug 使用私钥
     */
    private final static String PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCiPJ2P50rK8LsVR6NS2iLL8bFJTGEy60OjEITBwyUH7RXEbLWyJTDVPrq1RYryMKw9L1Xl6cNyg/k7tGD7r1sBmYkmBu9uqc2S7svjiLOXQxYyFF2lUHCzwunuo32NyL/AEG4nIsZsQpqvN4G2eY630McXvtbV4vDuNBEI9Mh00VvylrAppCLDlw99DaqG1YLncsjzW/AzDNqmtSPfJBc6fqY3nC7+gpxXa9MV3ojyt+lBOqz/ABekEUXtP4LKa/XI9dXaOHFG3JiEH2VK97pmk5pwxcvMbUaat/4FryxrOzd5FNBbgezeblOBxdstQKocxoW1RGOlSsP2g2NOR0HJAgMBAAECggEABDaayO/yy05xLV5M6Yvst2sxdXvcUlw1x3hYWkfdIFWR6SYBmfzJReB5LNf/gcat0nD2RCBMCR9QS7KliiPFs0bvfuNTOIaEZjp5ZRFZyKXfX7GTtQVfx+tZ8+6fBh2ejThgA/gtOwaHyeEhJ0IE6NUtNnqoTam1JBqKDrd8tTvn35H5NiXqCKXmdpXv2NsZRya5glfprdnfL9cS1ZTUCaEKHV2qyAU+4g6EhWP3Yt8ZIFkalIo6hVfU+CE2847h58AMYSkCExMdjkzAOCzim3d0QOWKsmwW36zvEMabc4WRP6XlbUcAP3/iY+SQf+m8lsMiMSf8HocZ/L7hHPVNGQKBgQD5pZpM33OiaDX2I2TdlwtvT8+wngjUM2/OLFMa7LLkbUaaX0rjZiA+GtvdryxrXLjn9M/0k2wxs8LCvyDSjXThc/uSoIQQosgJjpWMo9DNH5pAC0nvr02AIzXgXTdZIvY4kGFsmUX4JX6ftd1DccF57KmaA+ZGHRsXybpSsIeTKwKBgQCmXY3eTtVTJiTEcfdACFCGKwoth/MkwgMsG5Wsy/zWYVzJAFwaTq29GcHO6ODtVAsJzglqQotQy42jX7paFi8c9ey0wSq3Q/BdaU8bmNjlZycex+IzW5Oypd0nU/x0M/YQeYZnyzHMo0HX7cIHRAjjtDc/4Sv8qkvfVngqNecU2wKBgQCrs7Wrl6h9lDVBBFj0e1WXCoqrTBaAdJw6bov+IbxslagQtufX/T7B2FFOaJQhh5V5nAyHh/nLOXsD3NbjWf2Arvfhglsy4fK5eALtxyHuYwUBdYI1wxmHvy/oD9Tojr5QayVGvyi7onYzvK7hG1VZ2WdREcuIf65Jrypigx3MHwKBgQCNts+seRa6t5lGRZZKU0rUn0ESO+Zgf2vYXL8I7NK5g0JYbhgGyNKybIhtSm6fe6HTflEC1SB7eIl0i4zgRFlpoAs8hfdvGU5dI+GhLnhEIbRvQfFrsiyEV0mlwKMiEUkgC4T0UWBgYwkpeHx5V/kCjncp0RN5LU9oNe4ydqwbVQKBgQDFeBEwo065F8/cAvDI3bXsogzcJzVb5wPB8c3aWnEFMpCozx6cRLRU9dyWHYMKvzF3b7UzQF4a4xmxE15IVsask51njSM6LgljxPf0TYzWYuG/EDZ2RCYxUGZCn9C5rJU6YMRQ97CUgaA8ZuStyMPgaBoqy8+qB2AvTPWypLcKVg==";

    private String dataKey = "data";

    private String signKey = "sign";

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public String getSignKey() {
        return signKey;
    }

    public void setSignKey(String signKey) {
        this.signKey = signKey;
    }


    public DecryptVerifySignGatewayFilterFactory() {
        super(Config.class);
    }

    /**
     * 成功解密 并且 验签 通过后放行
     *
     * @param config
     * @return
     */
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            try {
                // 获取加密请求数据 data已经进行url解码
                String data = exchange.getRequest().getQueryParams().getFirst(dataKey);
                log.info("请求密文：{}", data);
                byte[] bytes = CodecUtil.BASE64Decoder(data);
                // 使用私钥解码
                bytes = EncryptUtil.blockDecryptByRSA2(EncryptUtil.getPrivateKeyByRSA2(PRIVATE_KEY), bytes);
                String clearText = new String(bytes, "utf-8");
                log.info("解密后请求明文：{}", clearText);

                Map<String, String> map = JSON.parseObject(clearText, Map.class);
                // 获取待签串
                String pendingSignStr = CodecUtil.pendingSignStrEncoder(map);
                // 获取请求签名
                String sign = map.get(signKey);
                log.info("请求签名：{}", sign);
                // 验证签名
                boolean verify = SignUtil.verifySign4MD5ByBase64(sign, pendingSignStr);
                if (verify) {
                    URI uri = exchange.getRequest().getURI();
                    String rawQuery = uri.getRawQuery();
                    log.info("rawQuery：{}",rawQuery);
                    StringBuilder newRawQuery = new StringBuilder();
                    String paramSeparator = "&";
                    String[] paramStr = rawQuery.split(paramSeparator);
                    boolean isFirst = true;
                    for (String s : paramStr) {
                        if(!isFirst){
                            newRawQuery.append('&');
                        }
                        if(s.contains("data=")){
                           newRawQuery.append("data=").append(clearText);
                        }else {
                            newRawQuery.append(s);
                        }
                        isFirst = false;
                    }
                    uri = UriComponentsBuilder.fromUri(uri)
                            .replaceQuery(newRawQuery.toString())
                            .build(false)
                            .toUri();
                    ServerHttpRequest httpRequest = exchange.getRequest().mutate().uri(uri).build();
                    return chain.filter(exchange.mutate().request(httpRequest).build());
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("验签解密失败，错误名称：{}，错误信息：{}", e.toString(), e.getMessage());
            }

            setResponseStatus(exchange, HttpStatus.BAD_REQUEST);
            DataBuffer buffer = exchange.getResponse().bufferFactory()
                    .wrap(JSON.toJSONString(BaseResponse.error("验签解密失败")).getBytes());
            return exchange.getResponse().writeWith(Flux.just(buffer));

        };
    }


    public static class Config {


    }
}
