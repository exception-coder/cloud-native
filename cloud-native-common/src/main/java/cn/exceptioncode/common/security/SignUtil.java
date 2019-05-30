package cn.exceptioncode.common.security;

import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/***
 *
 * 数据签名工具类
 *
 * @author zhangkai
 */
public class SignUtil {

    private SignUtil() {

    }


    public static byte[] signByHmacSHA256(String message, String secret) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac sha256HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        sha256HMAC.init(secretKey);
        byte[] bytes = sha256HMAC.doFinal(message.getBytes());
        return bytes;
    }

    /**
     *
     * md5签名
     *
     * @param str
     * @return
     */
    public static byte[] signByMD5(String str) {
        return DigestUtils.md5(str);
    }




}
