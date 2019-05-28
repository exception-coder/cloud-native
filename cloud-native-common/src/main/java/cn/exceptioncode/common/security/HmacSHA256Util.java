package cn.exceptioncode.common.security;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class HmacSHA256Util {
    public static byte[] sign(String message, String secret) throws NoSuchAlgorithmException,InvalidKeyException {
            Mac sha256HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256HMAC.init(secretKey);
            byte[] bytes = sha256HMAC.doFinal(message.getBytes());
            return bytes;
    }
}
