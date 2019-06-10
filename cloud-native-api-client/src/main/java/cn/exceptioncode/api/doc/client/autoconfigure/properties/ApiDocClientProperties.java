package cn.exceptioncode.api.doc.client.autoconfigure.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 *
 * @author zhangkai
 *
 */
@ConfigurationProperties(prefix = "cn.exceptioncode.api.code.doc")
@Data
public class ApiDocClientProperties {

    private String controllerBasePackage;
}
