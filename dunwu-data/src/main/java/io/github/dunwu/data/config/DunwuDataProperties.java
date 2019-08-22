package io.github.dunwu.data.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 数据层服务配置
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-16
 */
@Data
@ToString
@ConfigurationProperties(prefix = "dunwu.data")
public class DunwuDataProperties {
    private Boolean enabled = true;
    private Boolean blockAttackEnabled = true;
}