

package com.px.pa.modulars.drama.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.px.basic.alone.core.base.BaseModel;

import javax.validation.constraints.*;

/**
 * 剧本分类
 *
 *
 *
 */
@Data
@TableName("core_type")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "剧本分类")
public class CoreType extends BaseModel<CoreType> {
    private static final long serialVersionUID = 1L;

    /**
     * 分类名称
     */
    @Size(max = 255, message = "分类名称超出长度255的限制")
    @ApiModelProperty(value = "分类名称")
    private String name;
    /**
     * 分类描述
     */
    @Size(max = 255, message = "分类描述超出长度255的限制")
    @ApiModelProperty(value = "分类描述")
    private String detail;
}
