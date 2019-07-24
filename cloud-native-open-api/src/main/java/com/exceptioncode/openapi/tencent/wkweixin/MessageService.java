package com.exceptioncode.openapi.tencent.wkweixin;


import feign.Param;
import feign.RequestLine;
import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;


/**
 *
 * 企业微信API 消息推送
 *
 */
@Service
@FeignClient(name = "wexin-api-service-gettoken", url = "https://qyapi.weixin.qq.com")
public interface MessageService {



    @Data
    class BaseMsgRequestDTO{
        /**
         *
         * 成员ID列表（消息接收者，多个接收者用‘|’分隔，最多支持1000个）。特殊情况：指定为@all，则向该企业应用的全部成员发送
         */
        private String touser;
        /**
         *
         * 部门ID列表，多个接收者用‘|’分隔，最多支持100个。当touser为@all时忽略本参数
         */
        private String toparty;
        /**
         *
         * 标签ID列表，多个接收者用‘|’分隔，最多支持100个。当touser为@all时忽略本参数
         */
        private String totag;
        /**
         *
         * 消息类型，此时固定为：text
         */
        private String msgtype;
        /**
         *
         * 企业应用的id，整型。企业内部开发，可在应用的设置页面查看；第三方服务商，可通过接口 获取企业授权信息 获取该参数值
         */
        private int agentid;
    }

    /**
     *
     * 文本消息请求体
     *
     */
    @Data
    class TextMsgRequestDTO extends BaseMsgRequestDTO{

        private TextDTO text;
        /**
         *
         * 表示是否是保密消息，0表示否，1表示是，默认0
         */
        private int safe;


        @Data
        static class TextDTO {

            /**
             *
             * 消息内容，最长不超过2048个字节，超过将截断
             */
            private String content;

        }
    }

    /**
     *
     * 发送应用消息
     *
     * @param access_token
     * @return
     */
    @RequestLine("POST /cgi-bin/message/send")
    UserService.UserSimpleListResponseDTO send(@Param("access_token") final String access_token,TextMsgRequestDTO textMsgRequestDTO);
}
