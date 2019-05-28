package cn.exceptioncode.common.security;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@Slf4j
public class JwtUtil {

    private String header;

    private String payload;

    private String signature;

    @Data
    public class Header {

        /**
         * 签名的算法（algorithm）默认是 HMAC SHA256（写成 HS256）
         */
        private String alg = "HS256";

        /**
         * 令牌（token）的类型 JWT 令牌统一写为JWT
         */
        private String typ = "JWT";
    }

    @Data
    public class Payload {

        /**
         * (issuer)：签发人
         */
        private String iss;

        /**
         * (expiration time)：过期时间
         */
        private String exp;

        /**
         * (subject)：主题
         */
        private String sub;

        /**
         * (audience)：受众
         */
        private String aud;

        /**
         * (Not Before)：生效时间
         */
        private String nbf;

        /**
         * (Issued At)：签发时间
         */
        private String iat;

        /**
         * (JWT ID)：编号
         */
        private String jti;
    }


    /**
     * 获取jwt
     * <p>
     * Header.Payload.Signature
     *
     * @return
     */
    public static String getJWT(Header header, Map<String, String> stringStringMap, String secret) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {

        String headerJson = JSON.toJSONString(header);
        log.info("headerJson：{}", headerJson);
        String payloadJson = JSON.toJSONString(stringStringMap);
        log.info("payloadJson：{}", payloadJson);
        String headerStr = EncoderUtil.BASE64Encoder(headerJson.getBytes("utf-8"));
        log.info("base64headerStr：{}", headerStr);
        String payloadStr = EncoderUtil.BASE64Encoder(payloadJson.getBytes("utf-8"));
        log.info("base64payloadStr：{}", payloadStr);
        headerStr = EncoderUtil.URLEncoderUTF8(headerStr);
        payloadStr = EncoderUtil.URLEncoderUTF8(payloadStr);
        byte[] signatureBytes = HmacSHA256Util.sign(headerJson + "." + payloadJson, secret);
        String signatureStr = EncoderUtil.BASE64Encoder(signatureBytes);
        signatureStr = EncoderUtil.URLEncoderUTF8(signatureStr);
        return headerStr + "." + payloadStr + "." + signatureStr;
    }


    @Test
    public void test() throws Exception{
        Payload payload = new Payload();
        Map map = JSON.parseObject(JSON.toJSONString(payload),Map.class);
        System.out.println(
                getJWT(new Header(),map,"test_secret")
                );

    }

}
