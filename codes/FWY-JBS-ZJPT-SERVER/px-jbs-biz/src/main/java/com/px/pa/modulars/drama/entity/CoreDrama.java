

package com.px.pa.modulars.drama.entity;

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
 * 剧本信息
 *
 *
 *
 */
@Data
@TableName("core_drama")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "剧本信息")
public class CoreDrama extends BaseModel<CoreDrama> {
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    @Size(max = 255, message = "名称超出长度255的限制")
    @ApiModelProperty(value = "名称")
    private String name;
    /**
     * 图片
     */
    @Size(max = 255, message = "图片超出长度255的限制")
    @ApiModelProperty(value = "图片")
    private String image;
    /**
     * 工作室
     */
    @ApiModelProperty(value = "工作室")
    private Integer studio;
    /**
     * 困难度
     */
    @ApiModelProperty(value = "困难度")
    private Integer difficulty;
    /**
     * 规格
     */
    @ApiModelProperty(value = "规格")
    private Integer spec;
    /**
     * 简介
     */
    @ApiModelProperty(value = "简介")
    private String detail;
    /**
     * 状态：0-未上架，1-上架，2-下架
     */
    @ApiModelProperty(value = "状态：0-未上架，1-上架，2-下架")
    private Integer status;
    /**
     * 游戏时长
     */
    @Size(max = 255, message = "游戏时长超出长度255的限制")
    @ApiModelProperty(value = "游戏时长")
    private String duration;
    /**
     * 男人数
     */
    @ApiModelProperty(value = "男人数")
    private Integer male;
    /**
     * 女人数
     */
    @ApiModelProperty(value = "女人数")
    private Integer female;
    /**
     * 使用人数
     */
    @ApiModelProperty(value = "使用人数")
    private Integer used;
    /**
     * 发行日期
     */
    @ApiModelProperty(value = "发行日期")
    private LocalDateTime pubDate;
    /**
     * 第三方ID
     */
    @Size(max = 255, message = "第三方ID超出长度255的限制")
    @ApiModelProperty(value = "第三方ID")
    private String thirdId;
}
