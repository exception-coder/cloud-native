package cn.exceptioncode.common;

import cn.exceptioncode.common.security.CodecUtil;
import cn.exceptioncode.common.security.EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.security.KeyPair;

@Slf4j
/**
 * @author zhangkai
 */
public class UtilTest {


    @Test
    public void test() throws Exception {
        String message = "zhangkai2019张凯";
        KeyPair keyPair = EncryptUtil.buildKeyPairByRSA2();

        log.info("私钥：{}",EncryptUtil.getBase64RSA2PrivateKey(keyPair.getPrivate()));
        log.info("公钥：{}",EncryptUtil.getBase64RSA2PublicKey(keyPair.getPublic()));

        // 公钥加密
        byte[] bytes = EncryptUtil.blockEncryptByRSA2(keyPair.getPublic(), message);
        log.info("加密数据BASE64编码后明文：{}",CodecUtil.BASE64Encoder(bytes));
        // 进行BASE64编码后再进行URL编码
        message = CodecUtil.URLEncoderUTF8(CodecUtil.BASE64Encoder(bytes));
        log.info("URL编码后明文：{}",message);
        log.info("BASE64解码后明文：{}",CodecUtil.URLDecoderUTF8(message));
        bytes = CodecUtil.BASE64Decoder(CodecUtil.URLDecoderUTF8(message));
        // 使用私钥解码
        bytes = EncryptUtil.blockDecryptByRSA2(keyPair.getPrivate(), bytes);
        System.out.println(new String(bytes, "utf-8"));
    }
}
