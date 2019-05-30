package cn.exceptioncode.common.security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 *
 * 数据加解密工具类
 *
 * @author zhangkai
 *
 */
public class EncryptUtil {

    private static final int KEY_SIZE = 2048;

    private static final int MAX_DECRYPT_BLOCK = 256;

    private static final int MAX_ENCRYPT_BLOCK = 245;

    private static final String KEY_ALGORITHM = "RSA";

    private EncryptUtil() {

    }


    /**
     * 构建RSA秘钥对
     *
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static KeyPair buildKeyPairByRSA2() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGenerator.initialize(KEY_SIZE);
        return keyPairGenerator.genKeyPair();
    }


    /**
     * 公钥分段加密
     *
     * @param publicKey
     * @param message
     * @return
     * @throws Exception
     */
    public static byte[] blockEncryptByRSA2(PublicKey publicKey, String message) throws Exception {
        byte[] data = message.getBytes("utf-8");
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }




    /**
     * 私钥分段解密
     *
     * @param privateKey
     * @param encrypted
     * @return
     * @throws Exception
     */
    public static byte[] blockDecryptByRSA2(PrivateKey privateKey, byte[] encrypted) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException {
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        int inputLen = encrypted.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encrypted, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encrypted, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }


    /**
     * 获取Base64编码后明文存储的公钥
     *
     * @param publicKey
     * @return
     */
    public static String getBase64RSA2PublicKey(PublicKey publicKey) {
        return new String(Base64.getEncoder().encode(publicKey.getEncoded()));
    }


    /**
     * 获取Base64编码后明文存储的私钥
     *
     * @param privateKey
     * @return
     */
    public static String getBase64RSA2PrivateKey(PrivateKey privateKey) {
        return new String(Base64.getEncoder().encode(privateKey.getEncoded()));
    }


    /**
     * 将base64编码后的公钥字符串转成PublicKey实例
     *
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKeyByRSA2(String publicKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(publicKey.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 将base64编码后的私钥字符串转成PrivateKey实例
     *
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKeyByRSA2(String privateKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(privateKey.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        return keyFactory.generatePrivate(keySpec);
    }


}

