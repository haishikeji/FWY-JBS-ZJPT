package com.px.pa.vo.param;

import com.pig4cloud.pig.common.core.core.vo.BasePageParamVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 *
 */
@Data
@ApiModel("订阅消息查询")
@ToString
public class TemplateParam extends BasePageParamVo {
	@ApiModelProperty("产品id")
	private Integer id;
	@ApiModelProperty("模板id")
	private Integer tid;

}
