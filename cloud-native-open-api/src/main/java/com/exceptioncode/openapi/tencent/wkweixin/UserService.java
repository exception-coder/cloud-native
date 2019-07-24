package com.exceptioncode.openapi.tencent.wkweixin;


import feign.Param;
import feign.RequestLine;
import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * 企业微信API 通讯录管理
 */
@Service
@FeignClient(name = "wexin-api-service-gettoken", url = "https://qyapi.weixin.qq.com")
public interface UserService {

    @Data
    class UserSimpleListResponseDTO {

        /**
         *
         * 返回码
         */
        private String errcode;

        /**
         *
         * 对返回码的文本描述内容
         */
        private String errmsg;


        /**
         *
         * 成员列表
         */
        private List<User> userlist;

        @Data
        class User {
            /**
             *
             *  成员UserID。对应管理端的帐号
             */
            private String userid;

            /**
             *
             *  成员名称
             */
            private String name;

            /**
             *
             *  成员所属部门列表。列表项为部门ID，32位整型
             */
            private String[] department;
        }
    }


    /**
     *
     * 获取部门成员
     *
     * @param access_token 调用接口凭证
     * @param department_id 获取的部门id
     * @param fetch_child 1/0：是否递归获取子部门下面的成员
     *
     * @return
     */
    @RequestLine("GET /cgi-bin/user/simplelist")
    UserService.UserSimpleListResponseDTO simplelist(@Param("access_token") final String access_token, @Param("department_id") final String department_id,
                                                     @Param("fetch_child") final short fetch_child);

}
