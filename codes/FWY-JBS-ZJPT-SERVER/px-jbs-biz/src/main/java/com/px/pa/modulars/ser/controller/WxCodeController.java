package com.px.pa.modulars.ser.controller;

import com.pig4cloud.pig.common.core.util.R;
import com.px.pa.modulars.code.entity.CodeProduct;
import com.px.pa.modulars.code.service.CodeProductService;
import com.px.pa.modulars.info.service.ProductInfoService;
import com.px.pa.modulars.ser.service.WxCodeTempService;
import com.px.pa.vo.param.CodeToTempParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.open.bean.WxOpenMaCodeTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 *
 */
@RestController
@RequestMapping("/wx/code")
@Api(value = "code", tags = "代码管理")
public class WxCodeController {

	@Autowired
	private WxCodeTempService codeTempService;

	@Autowired
	private ProductInfoService productInfoService;
	@Autowired
	private CodeProductService codeProductService;

	/**
	 * 查询草稿列表
	 *
	 * @return
	 */
	@ApiOperation("查询草稿列表")
	@GetMapping("/read/temp/draft")
	public R queryTemplateDraftList() {
		List<WxOpenMaCodeTemplate> codes = this.codeTempService.queryDraftList();
		codes.sort(Comparator.comparing(WxOpenMaCodeTemplate::getDraftId).reversed());
		return R.ok(codes);
	}

	@ApiOperation("将草稿设置为模板")
	@PostMapping("/update/addToTemp")
	public R addToTemp(@RequestBody CodeToTempParam param) {
		boolean flag = this.codeTempService.addToTemp(param.getDraftId());
		if (flag) {
			//如果上传成功，绑定产品与代码关系
			this.codeProductService.bind(param.getDraftId(), param.getProductId());
		}
		return R.ok(flag);
	}


	@ApiOperation("查询模板列表")
	@GetMapping("/read/temp/list")
	public R queryTemplateList() {
		List<WxOpenMaCodeTemplate> codes = this.codeTempService.queryTempList();
		return R.ok(codes);
	}

	@ApiOperation("删除模板")
	@DeleteMapping("/remove/{tempId}")
	public R removeTemp(@PathVariable("tempId") Long tempId) {
		codeProductService.lambdaUpdate().eq(CodeProduct::getTemplateId, tempId).remove();
		boolean flag = this.codeTempService.delTemp(tempId);
		return R.ok(flag);
	}

}
