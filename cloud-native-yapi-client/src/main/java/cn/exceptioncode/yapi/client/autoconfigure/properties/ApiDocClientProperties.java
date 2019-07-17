package cn.exceptioncode.yapi.client.autoconfigure.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 *
 * @author zhangkai
 *
 */
@ConfigurationProperties(prefix = "cn.exceptioncode.yapi.client")
@Data
public class ApiDocClientProperties {

    /**
     *
     * 扫描的controller包
     *
     */
    private String controllerBasePackage;

    /**
     *
     * yapi-server 配置
     *
     */
    private String apiServer;

    /**
     *
     * yapi-server token
     *
     */
    private String apiToken;

    /**
     *
     *
     * yapi分组id
     */
    private String apiCatid;

    /**
     *
     * yapi-server 用户名
     */
    private String apiUserName;


    /**
     *
     *
     * yapi-server 密码
     *
     */
    private String apiUserPassword;

    private Boolean enable = false;
}
