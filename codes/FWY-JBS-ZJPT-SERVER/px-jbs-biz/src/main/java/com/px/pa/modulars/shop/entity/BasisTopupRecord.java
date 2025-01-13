

package com.px.pa.modulars.shop.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.px.basic.alone.core.base.BaseModel;

import java.math.BigDecimal;
import javax.validation.constraints.*;

/**
 * 用户在某个店铺充值记录表
 *
 *
 *
 */
@Data
@TableName("basis_topup_record")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "用户在某个店铺充值记录表")
public class BasisTopupRecord extends BaseModel<BasisTopupRecord> {
    private static final long serialVersionUID = 1L;

    /**
     * 商铺会员id
     */
    @Size(max = 11, message = "商铺会员id超出长度11的限制")
    @ApiModelProperty(value = "商铺会员id")
    private Integer shopUser;
    /**
     * 充值内容
     */
    @Size(max = 500, message = "充值内容超出长度500的限制")
    @ApiModelProperty(value = "充值内容")
    private String content;
    /**
     * 可用余额
     */
    @ApiModelProperty(value = "可用余额")
    private BigDecimal availableBalance;
    /**
     * 充值余额
     */
    @ApiModelProperty(value = "充值余额")
    private BigDecimal money;
}
