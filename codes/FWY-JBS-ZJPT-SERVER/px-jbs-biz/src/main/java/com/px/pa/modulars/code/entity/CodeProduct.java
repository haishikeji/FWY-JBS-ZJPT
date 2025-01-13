

package com.px.pa.modulars.code.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 代码与产品绑定
 *
 *
 *
 */
@Data
@TableName("wxa_code_product")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "代码与产品绑定")
public class CodeProduct extends Model<CodeProduct> {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	@ApiModelProperty(value = "")
	private Integer id;
	/**
	 * 产品id
	 */
	@ApiModelProperty(value = "产品id")
	private Integer productId;
	/**
	 * 模板id
	 */
	@ApiModelProperty(value = "模板id")
	private Long templateId;
	/**
	 * 版本号
	 */
	@ApiModelProperty(value = "版本号")
	private String userVersion;
	/**
	 * 版本备注信息
	 */
	@ApiModelProperty(value = "版本备注信息")
	private String userVersionDesc;
	@ApiModelProperty("产品名称")
	@TableField(exist = false)
	private String productName;
}
