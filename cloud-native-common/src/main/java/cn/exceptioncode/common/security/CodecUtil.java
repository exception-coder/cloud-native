package cn.exceptioncode.common.security;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 *
 * 数据编码工具类
 *
 * @author zhangkai
 */
public class CodecUtil {

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
}
