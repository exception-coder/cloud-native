package cn.exceptioncode.common;

import cn.exceptioncode.common.security.CodecUtil;
import cn.exceptioncode.common.security.EncryptUtil;
import org.junit.Test;

import java.security.KeyPair;

/**
 * @author zhangkai
 */
public class UtilTest {


    @Test
    public void test() throws Exception {
        String message = "zhangkai2019张凯";
        KeyPair keyPair = EncryptUtil.buildKeyPairByRSA2();
        System.out.println(EncryptUtil.getBase64RSA2PrivateKey(keyPair.getPrivate()));
        System.out.println(EncryptUtil.getBase64RSA2PublicKey(keyPair.getPublic()));
        // 公钥加密
        byte[] bytes = EncryptUtil.blockEncryptByRSA2(keyPair.getPublic(), message);
        // 进行BAS564编码后再进行URL编码
        message = CodecUtil.URLEncoderUTF8(CodecUtil.BASE64Encoder(bytes));
        bytes = CodecUtil.BASE64Decoder(CodecUtil.URLDecoderUTF8(message));
        // 使用私钥解码
        bytes = EncryptUtil.blockDecryptByRSA2(keyPair.getPrivate(), bytes);
        System.out.println(new String(bytes, "utf-8"));
    }
}
