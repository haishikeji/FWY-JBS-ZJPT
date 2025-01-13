

package com.px.pa.modulars.info.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 管理日志
 *
 *
 *
 */
@Data
@TableName("wxa_event")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "管理日志")
public class MpEvent extends Model<MpEvent> {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	@ApiModelProperty(value = "")
	private Integer id;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;
	/**
	 * 创建人
	 */
	@ApiModelProperty(value = "创建人")
	private Integer createBy;
	@ApiModelProperty(value = "小程序ID")
	private Integer appletId;
	/**
	 * 创建人
	 */
	@ApiModelProperty(value = "创建人")
	private String createName;
	/**
	 * 操作
	 */
	@ApiModelProperty(value = "操作")
	private String event;
	/**
	 * 信息
	 */
	@ApiModelProperty(value = "信息")
	private String info;
}
