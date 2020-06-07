package cn.exceptioncode.common;

import cn.exceptioncode.common.io.FileUtils;
import cn.exceptioncode.common.security.CodecUtil;
import cn.exceptioncode.common.security.EncryptUtil;
import cn.exceptioncode.common.security.SignUtil;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.security.KeyPair;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
/**
 * @author zhangkai
 */
public class UtilTest {


    @Test
    public void test() throws Exception {
        File path = new File("E:\\VPN");
        Collection<File> fileCollection = FileUtils.listFiles(path,new String[]{"ts"},false);
        fileCollection.forEach(file -> {
            String s = file.getAbsolutePath();
            int index = s.indexOf(".mp4");
            s = s.substring(0, index + ".mp4".length());
            file.renameTo(new File(s));
        });
        System.exit(0);
        String message = "zhangkai2019张凯";
        KeyPair keyPair = EncryptUtil.buildKeyPairByRSA2();

        log.info("私钥：{}", EncryptUtil.getBase64RSA2PrivateKey(keyPair.getPrivate()));
        log.info("公钥：{}", EncryptUtil.getBase64RSA2PublicKey(keyPair.getPublic()));

        // 公钥加密
        byte[] bytes = EncryptUtil.blockEncryptByRSA2(keyPair.getPublic(), message);
        log.info("加密数据BASE64编码后明文：{}", CodecUtil.BASE64Encoder(bytes));
        // 进行BASE64编码后再进行URL编码
        message = CodecUtil.URLEncoderUTF8(CodecUtil.BASE64Encoder(bytes));
        log.info("URL编码后明文：{}", message);
        log.info("BASE64解码后明文：{}", CodecUtil.URLDecoderUTF8(message));
        bytes = CodecUtil.BASE64Decoder(CodecUtil.URLDecoderUTF8(message));
        // 使用私钥解码
        bytes = EncryptUtil.blockDecryptByRSA2(keyPair.getPrivate(), bytes);
        System.out.println(new String(bytes, "utf-8"));
    }


    @Test
    public void pendingSignStrEncoderTest() throws Exception {
        A a = new A();
        a.setA("1");
        a.setB("b");
        Map<String, String> map = new HashMap<>(10);
        map.put("a", "a");
        map.put("a_", JSON.toJSONString(a));
        String pendingSignStr = CodecUtil.pendingSignStrEncoder(map);
        log.info("待签串：{}", CodecUtil.pendingSignStrEncoder(map));
        String sign = CodecUtil.BASE64Encoder(SignUtil.signByMD5(pendingSignStr));
        map.put("sign", sign);
        String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAojydj+dKyvC7FUejUtoiy/GxSUxhMutDoxCEwcMlB+0VxGy1siUw1T66tUWK8jCsPS9V5enDcoP5O7Rg+69bAZmJJgbvbqnNku7L44izl0MWMhRdpVBws8Lp7qN9jci/wBBuJyLGbEKarzeBtnmOt9DHF77W1eLw7jQRCPTIdNFb8pawKaQiw5cPfQ2qhtWC53LI81vwMwzaprUj3yQXOn6mN5wu/oKcV2vTFd6I8rfpQTqs/wAXpBFF7T+Cymv1yPXV2jhxRtyYhB9lSve6ZpOacMXLzG1Gmrf+Ba8sazs3eRTQW4Hs3m5TgcXbLUCqHMaFtURjpUrD9oNjTkdByQIDAQAB";
        String jsonStr = JSON.toJSONString(map);
        byte[] bytes = EncryptUtil.blockEncryptByRSA2(EncryptUtil.getPublicKeyByRSA2(publicKey), jsonStr);
        String ciphertext = CodecUtil.URLEncoderUTF8(CodecUtil.BASE64Encoder(bytes));
        log.info("请求密文：{}", ciphertext);
    }

    @Data
    class A<T> {
        private String a;

        private String b;

        private T data;
    }


}
