package com.px.pa.constant;

import com.pig4cloud.pig.common.core.constant.enums.BaseEnum;

/**
 * 小程序相关操作
 *
 *
 */

public enum WxEventEnum implements BaseEnum {
	settingModifyDomain(1, "设置服务器域名"),
	settingDomain(2, "设置业务域名"),
	settingHeaderImg(3, "设置小程序图标"),
	settingSignature(4, "设置小程序的简介信息"),
	settingName(5, "设置小程序的名称"),
	settingCategories(6, "设置小程序类目");

	private Integer value;
	private String desc;

	private WxEventEnum(Integer value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	@Override
	public Integer getValue() {
		return null;
	}

	@Override
	public String getDesc() {
		return null;
	}
}
