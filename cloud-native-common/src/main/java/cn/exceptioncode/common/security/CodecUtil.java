package cn.exceptioncode.common.security;

import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 *
 * 数据编码工具类
 *
 * @author zhangkai
 */
public class CodecUtil {

    private static final String SIGN_CODE = "sign";

    private static final BASE64Encoder base64Encoder = new BASE64Encoder();

    private static final BASE64Decoder base64Decoder = new BASE64Decoder();


    private CodecUtil(){

    }

    /**
     *
     * base64编码
     *
     * @param bytes
     * @return
     */
    public static String BASE64Encoder(byte[] bytes){
        /**
         *
         *
         *
         */
        return base64Encoder.encode(bytes);
    }

    public static byte[] BASE64Decoder(String data) throws IOException {
        return base64Decoder.decodeBuffer(data);
    }

    /**
     * url utf-8编码
     *
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String URLEncoderUTF8(String str) throws UnsupportedEncodingException {
       return URLEncoder.encode(str,"utf-8");
    }


    /**
     * url utf-8解码
     *
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String URLDecoderUTF8(String str) throws UnsupportedEncodingException {
        return URLDecoder.decode(str,"utf-8");
    }

    /**
     *
     * 将键值对转换为待签串
     *
     * @param mapParameter
     * @return
     */
    public static String pendingSignStrEncoder(Map<String, String> mapParameter) {
        List<String> parameterKey = new ArrayList<>();
        for (String key : mapParameter.keySet()) {
            if (!SIGN_CODE.equals(key)) {
                parameterKey.add(key);
            }
        }
        Collections.sort(parameterKey);
        StringBuffer pendingSignature = new StringBuffer();
        Integer index = 0;
        for (String key : parameterKey) {
            String  value = mapParameter.get(key);

            if (StringUtils.isNotEmpty(value)) {
                if (index != 0) {
                    pendingSignature.append("&");
                }
                index++;
                pendingSignature.append(key);
                pendingSignature.append("=");
                pendingSignature.append(value);
            }
        }

        return pendingSignature.toString();
    }


}
