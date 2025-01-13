
package com.px.pa.modulars.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.px.basic.alone.core.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户推送消息
 *
 *
 *
 */
@Data
@TableName("basis_users_push")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "用户推送消息")
public class BasisUsersPush extends BaseModel<BasisUsersPush> {

    /**
     * 用户id
     */
    @ApiModelProperty(value="用户id")
    private Integer uid;

    /**
     * 店铺id
     */
    @ApiModelProperty(value="店铺id")
    private Integer sid;

    /**
     * 剧本id
     */
    @ApiModelProperty(value="剧本id")
    private Integer did;

    /**
     * 店铺剧本id
     */
    @ApiModelProperty(value="店铺剧本id")
    private Integer sdid;

    /**
     * 拼车id
     */
    @ApiModelProperty(value="拼车id")
    private Integer cid;

}
