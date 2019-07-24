package com.exceptioncode.openapi.tencent.wkweixin;

import feign.Param;
import feign.RequestLine;
import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;

/**
 *
 *
 * 企业微信API 获取access_token
 *
 */
@Service
@FeignClient(name = "wexin-api-service-gettoken", url = "https://qyapi.weixin.qq.com")
public interface GetTokenService {

    @Data
    class AccessTokenResponseDTO {
        /**
         *
         * 出错返回码，为0表示成功，非0表示调用失败
         */
        private int errcode;

        /**
         *
         * 返回码提示语
         */
        private String errmsg;

        /**
         *
         * 获取到的凭证，最长为512字节
         */
        private String access_token;

        /**
         *
         * 凭证的有效时间（秒）
         */
        private String expires_in;
    }

    /**
     *
     * 获取access_token
     *
     * @param corpid 企业ID，获取方式参考：
     *      @see <a href="https://work.weixin.qq.com/api/doc#14953/corpid">术语说明-corpid </a>
     * @param corpsecret 应用的凭证密钥，获取方式参考：术语说明-secret
     *      @see <a href="https://work.weixin.qq.com/api/doc#14953/secret">术语说明-secret </a>
     * @return
     */
    @RequestLine("GET /cgi-bin/gettoken")
    AccessTokenResponseDTO getToken(@Param("corpid") final String corpid, @Param("corpsecret") final String corpsecret);

}
