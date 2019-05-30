package cn.exceptioncode.common.security;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 *
 * @author zhangkai
 */
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
    public static String getJWT(Header header, Map<String, String> stringStringMap, String secret) throws UnsupportedEncodingException
            , NoSuchAlgorithmException, InvalidKeyException {

        String headerJson = JSON.toJSONString(header);
        log.info("headerJson：{}", headerJson);
        String payloadJson = JSON.toJSONString(stringStringMap);
        log.info("payloadJson：{}", payloadJson);
        String headerStr = CodecUtil.BASE64Encoder(headerJson.getBytes("utf-8"));
        log.info("base64headerStr：{}", headerStr);
        String payloadStr = CodecUtil.BASE64Encoder(payloadJson.getBytes("utf-8"));
        log.info("base64payloadStr：{}", payloadStr);
        headerStr = CodecUtil.URLEncoderUTF8(headerStr);
        payloadStr = CodecUtil.URLEncoderUTF8(payloadStr);
        byte[] signatureBytes = SignUtil.signByHmacSHA256(headerJson + "." + payloadJson, secret);
        String signatureStr = CodecUtil.BASE64Encoder(signatureBytes);
        signatureStr = CodecUtil.URLEncoderUTF8(signatureStr);
        return headerStr + "." + payloadStr + "." + signatureStr;
    }


//    @UtilTest
//    public void test() throws Exception{
//        Payload payload = new Payload();
//        Map map = JSON.parseObject(JSON.toJSONString(payload),Map.class);
//        System.out.println(
//                getJWT(new Header(),map,"test_secret")
//                );
//
//    }

}
