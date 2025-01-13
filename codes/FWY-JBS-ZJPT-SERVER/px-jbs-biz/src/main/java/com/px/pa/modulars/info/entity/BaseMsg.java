

package com.px.pa.modulars.info.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 小程序订阅消息配置
 *
 *
 * 
 */
@Data
@TableName("wxa_base_msg")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "小程序订阅消息配置")
public class BaseMsg extends Model<BaseMsg> {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	@ApiModelProperty(value = "")
	private Integer id;
	/**
	 * 模板标识
	 */
	@ApiModelProperty(value = "模板标识")
	private String code;
	/**
	 * 模板名称
	 */
	@ApiModelProperty(value = "模板名称")
	private String title;
	/**
	 * 模版id
	 */
	@ApiModelProperty(value = "模版id")
	private Integer tid;
	/**
	 * 模版类型，2 为一次性订阅，3 为长期订阅
	 */
	@ApiModelProperty(value = "模版类型，2 为一次性订阅，3 为长期订阅")
	private Integer type;
	/**
	 * 模版所属类目 id
	 */
	@ApiModelProperty(value = "模版所属类目 id")
	private Integer categoryId;
	/**
	 * 所属产品ID
	 */
	@ApiModelProperty(value = "所属产品ID")
	private Integer productId;
	/**
	 * 选中关键字列表
	 */
	@ApiModelProperty(value = "选中关键字列表")
	private String ext;
	/**
	 * 所有关键字列表
	 */
	@ApiModelProperty(value = "所有关键字列表")
	private String keyword;
	/**
	 * 发送模板id列表
	 */
	@ApiModelProperty(value = "发送模板id列表")
	private String priTmplId;

}
