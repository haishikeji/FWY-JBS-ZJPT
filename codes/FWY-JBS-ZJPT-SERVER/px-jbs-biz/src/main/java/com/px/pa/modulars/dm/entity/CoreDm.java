

package com.px.pa.modulars.dm.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.px.pa.modulars.shop.entity.CoreDmDrama;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.px.basic.alone.core.base.BaseModel;

import java.math.BigDecimal;
import java.util.List;
import javax.validation.constraints.*;

/**
 * 剧本主持人
 *
 *
 *
 */
@Data
@TableName("core_dm")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "剧本主持人")
public class CoreDm extends BaseModel<CoreDm> {
    private static final long serialVersionUID = 1L;

    /**
     * DM名称
     */
    @Size(max = 255, message = "DM名称超出长度255的限制")
    @ApiModelProperty(value = "DM名称")
    private String name;
    /**
     * 图片
     */
    @ApiModelProperty(value = "图片")
    private String image;
    /**
     * 性别：1-男，2-女
     */
    @ApiModelProperty(value = "性别：1-男，2-女")
    private Integer sex;
    /**
     * 星座
     */
    @Size(max = 255, message = "星座超出长度255的限制")
    @ApiModelProperty(value = "星座")
    private String sign;
    /**
     * 简介
     */
    @Size(max = 255, message = "简介超出长度255的限制")
    @ApiModelProperty(value = "简介")
    private String detail;
    /**
     * 评分
     */
    @ApiModelProperty(value = "评分")
    private BigDecimal score;
    /**
     * 热度
     */
    @ApiModelProperty(value = "热度")
    private Integer hot;
    /**
     * 开本场次
     */
    @ApiModelProperty(value = "开本场次")
    private Integer num;
    /**
     * 虚拟开本场次
     */
    @ApiModelProperty(value = "虚拟开本场次")
    private Integer invented;
    /**
     * 所属店铺用户id
     */
    @ApiModelProperty(value = "所属店铺用户id")
    private Integer suid;
    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Integer sid;
    /**
     * 店员编号
     */
    @Size(max = 255, message = "店员编号超出长度255的限制")
    @ApiModelProperty(value = "店员编号")
    private String number;
    /**
     * 店员手机号
     */
    @Size(max = 255, message = "店员手机号超出长度255的限制")
    @ApiModelProperty(value = "店员手机号")
    private String phone;

    @TableField(exist = false)
    private List<CoreDmDrama> coreDmDramaList;

    @TableField(exist = false)
    private Integer pageNo;
    @TableField(exist = false)
    private Integer pageSize;
    @TableField(exist = false)
    private Integer scoreNum;
    /**
     * 是否绑定(1.未绑定2.已绑定)
     */
    @TableField(exist = false)
    private Integer isBand;

}
