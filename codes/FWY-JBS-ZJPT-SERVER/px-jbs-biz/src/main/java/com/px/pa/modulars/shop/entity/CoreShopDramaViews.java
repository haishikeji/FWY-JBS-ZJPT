
package com.px.pa.modulars.shop.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.px.basic.alone.core.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 剧本浏览历史
 *
 * 
 * 
 */
@Data
@TableName("core_shop_drama_views")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "剧本浏览历史")
public class CoreShopDramaViews extends BaseModel<CoreShopDramaViews> {

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
     * 用户id
     */
    @ApiModelProperty(value="用户id")
    private Integer uid;

    /**
     * 店铺用户id
     */
    @ApiModelProperty(value="店铺用户id")
    private Integer suid;

}
