

package com.px.pa.modulars.shop.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.px.basic.alone.core.base.BaseModel;

import java.math.BigDecimal;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDateTime;

/**
 * 店铺列表
 *
 *
 *
 */
@Data
@TableName("core_shop")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "店铺列表")
public class CoreShop extends BaseModel<CoreShop> {
    private static final long serialVersionUID = 1L;

    /**
     * 店铺名称
     */
    @Size(max = 255, message = "店铺名称超出长度255的限制")
    @ApiModelProperty(value = "店铺名称")
    private String name;
    /**
     * 详细地址
     */
    @Size(max = 255, message = "详细地址超出长度255的限制")
    @ApiModelProperty(value = "详细地址")
    private String addr;
    /**
     * 纬度
     */
    @Size(max = 255, message = "纬度超出长度255的限制")
    @ApiModelProperty(value = "纬度")
    private String lat;
    /**
     * 经度
     */
    @Size(max = 255, message = "经度超出长度255的限制")
    @ApiModelProperty(value = "经度")
    private String lng;
    /**
     * 联系人
     */
    @Size(max = 255, message = "联系人超出长度255的限制")
    @ApiModelProperty(value = "联系人")
    private String contact;
    /**
     * 联系电话
     */
    @Size(max = 255, message = "联系电话超出长度255的限制")
    @ApiModelProperty(value = "联系电话")
    private String phone;
    /**
     * 到期时间
     */
    @ApiModelProperty(value = "到期时间")
    private LocalDateTime expired;
    /**
     * 图片
     */
    @Size(max = 255, message = "图片超出长度255的限制")
    @ApiModelProperty(value = "图片")
    private String image;
    /**
     * 营业执照
     */
    @Size(max = 255, message = "营业执照超出长度255的限制")
    @ApiModelProperty(value = "营业执照")
    private String business;
    /**
     * 默认单价
     */
    @ApiModelProperty(value = "默认单价")
    private BigDecimal price;
    /**
     * 小程序APPID
     */
    @Size(max = 255, message = "小程序APPID超出长度255的限制")
    @ApiModelProperty(value = "小程序APPID")
    private String appid;
    /**
     *
     */
    @Size(max = 255, message = "超出长度255的限制")
    @ApiModelProperty(value = "")
    private String appsecret;
    /**
     * 支付商户号
     */
    @Size(max = 255, message = "支付商户号超出长度255的限制")
    @ApiModelProperty(value = "支付商户号")
    private String mchid;
    /**
     * 支付KEY
     */
    @Size(max = 255, message = "支付KEY超出长度255的限制")
    @ApiModelProperty(value = "支付KEY")
    private String mchKey;
    /**
     * 支付证书
     */
    @Size(max = 255, message = "支付证书超出长度255的限制")
    @ApiModelProperty(value = "支付证书,pem格式")
    private String payCert;
    /**
     * 支付证书密钥
     */
    @Size(max = 255, message = "支付证书密钥超出长度255的限制")
    @ApiModelProperty(value = "支付证书密钥")
    private String payKey;
    /**
     * 关联管理员id
     */
    @ApiModelProperty(value = "关联管理员id")
    private Integer uid;
    /**
     * 父店铺id
     */
    @ApiModelProperty(value = "父店铺id")
    private Integer pid;
    /**
     * 营业开始日期
     */
    @ApiModelProperty(value = "营业开始日期")
    private String sdate;
    /**
     * 营业结束日期
     */
    @ApiModelProperty(value = "营业结束日期")
    private String edate;
    /**
     * 营业开始时间
     */
    @ApiModelProperty(value = "营业开始时间")
    private Time stime;
    /**
     * 营业结束结束
     */
    @ApiModelProperty(value = "营业结束结束")
    private Time etime;
    /**
     * 分店个数
     */
    @ApiModelProperty(value = "分店个数")
    private Integer child;
    /**
     * 店铺轮播图
     */
    @ApiModelProperty(value = "店铺轮播图")
    private String figureImage;
    @ApiModelProperty(value = "省")
    private String province;
    @ApiModelProperty(value = "市")
    private String city;
    @ApiModelProperty(value = "区")
    private String district;
    @TableField(exist = false)
    private String token;
    @TableField(exist = false)
    private String weather;
    @TableField(exist = false)
    private Integer month;
    @ApiModelProperty(value = "微信号")
    private String wx;

    @TableField(exist = false)
    private Double distance;
    @TableField(exist = false)
    private Integer dramaNum;
    @TableField(exist = false)
    private Integer dmNum;

}
