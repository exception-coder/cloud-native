package cn.exceptioncode.yapi.doc.client.autoconfigure.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 *
 * @author zhangkai
 *
 */
@ConfigurationProperties(prefix = "cn.exceptioncode.yapi.code.doc")
@Data
public class ApiDocClientProperties {

    // TODO: 19-6-28 暂时不处理 不想把结构搞得太复杂 约束配置太多  全量扫系统中的Controller 一切权限约束由网关及用户中心抽象处理
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

    private Boolean enable = true;
}
