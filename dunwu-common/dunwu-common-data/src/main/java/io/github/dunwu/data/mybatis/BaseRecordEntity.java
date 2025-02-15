package io.github.dunwu.data.mybatis;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.github.dunwu.data.mybatis.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 基础记录数据库实体类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-27
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public abstract class BaseRecordEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "创建时间")
    protected LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    protected LocalDateTime updateTime;

    @TableLogic
    @TableField(select = false)
    @ApiModelProperty(value = "逻辑删除标记。不需要用户填值。", example = "0")
    protected Boolean deleted = false;

}
