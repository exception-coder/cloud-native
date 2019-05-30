package cn.exceptioncode.gateway.filter.decrypt;

import cn.exceptioncode.common.security.CodecUtil;
import cn.exceptioncode.common.security.EncryptUtil;
import lombok.Data;
import org.springframework.web.server.ServerWebExchange;

import java.security.PrivateKey;

/**
 * @author zhangkai
 */
@Data
public class DecryptKeyResolver {

    private final String dataKey;

    private final String signKey;


    public DecryptKeyResolver(String dataKey, String signKey) {
        this.dataKey = dataKey;
        this.signKey = signKey;
    }

    /**
     * 获取解密后请求数据
     *
     * @param exchange
     * @return
     */
    public String decryptData(ServerWebExchange exchange, PrivateKey privateKey) throws Exception {
        String data = exchange.getRequest().getQueryParams().getFirst(this.dataKey);
        // url解码后再进行base64解码得到加密字节数组
        byte[] bytes = CodecUtil.BASE64Decoder(CodecUtil.URLDecoderUTF8(data));
        // 使用私钥解码
        bytes = EncryptUtil.blockDecryptByRSA2(privateKey, bytes);
        System.out.println(new String(bytes, "utf-8"));
        return new String(bytes, "utf-8");
    }

}
