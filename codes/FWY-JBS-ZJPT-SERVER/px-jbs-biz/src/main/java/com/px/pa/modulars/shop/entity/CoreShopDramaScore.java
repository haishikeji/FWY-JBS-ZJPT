

package com.px.pa.modulars.shop.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.px.basic.alone.core.base.BaseModel;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 剧本评分记录
 *
 *
 * 
 */
@Data
@TableName("core_shop_drama_score")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "剧本评分记录")
public class CoreShopDramaScore extends BaseModel<CoreShopDramaScore> {
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Integer uid;
    /**
     * 店铺剧本id
     */
    @ApiModelProperty(value = "店铺剧本id")
    private Integer sdid;
    /**
     * 评分
     */
    @ApiModelProperty(value = "评分")
    private Integer score;
    /**
     * DMid
     */
    @ApiModelProperty(value = "DMid")
    private Integer dmid;
    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Integer sid;
}
