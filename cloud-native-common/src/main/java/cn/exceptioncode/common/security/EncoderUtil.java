package cn.exceptioncode.common.security;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class EncoderUtil {

    private static final BASE64Encoder base64Encoder = new BASE64Encoder();


    public static String BASE64Encoder(byte[] bytes){
        return base64Encoder.encode(bytes);
    }

    public static String URLEncoderUTF8(String str) throws UnsupportedEncodingException {
       return URLEncoder.encode(str,"utf-8");
    }
}
