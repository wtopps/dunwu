package io.github.dunwu.quickstart.filesystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.dunwu.data.entity.BaseEntity;
import io.github.dunwu.quickstart.filesystem.constant.FileStoreTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 文件信息表数据实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-12
 */
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "FileInfo", description = "文件信息表")
public class File extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "实际文件名")
    private String fileName;

    @ApiModelProperty(value = "命名空间。一般对应业务系统")
    private String namespace;

    @ApiModelProperty(value = "标签。供业务系统使用")
    private String tag;

    @TableField(condition = "%s LIKE CONCAT('%%',#{%s},'%%')")
    @ApiModelProperty(value = "源文件名")
    private String originName;

    @ApiModelProperty(value = "文件大小", example = "0")
    private Long size;

    @ApiModelProperty(value = "文件扩展名")
    private String extension;

    @ApiModelProperty(value = "文件实际类型")
    private String contentType;

    @ApiModelProperty(value = "文件存储服务类型")
    private FileStoreTypeEnum storeType;

    @ApiModelProperty(value = "文件存储路径")
    private String storeUrl;

    @ApiModelProperty(value = "文件访问路径")
    private String accessUrl;

    @ApiModelProperty(value = "上传时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

}
