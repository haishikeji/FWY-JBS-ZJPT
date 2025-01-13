

package com.px.pa.modulars.code.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.mybatis.util.OrderItemUtil;
import com.px.pa.modulars.code.entity.CodeTaskExample;
import com.px.pa.modulars.code.service.CodeTaskExampleService;
import com.px.pa.modulars.info.entity.Applets;
import com.px.pa.modulars.info.service.AppletsService;
import com.px.pa.modulars.ser.service.WxCodeManageService;
import com.px.pa.modulars.ser.service.WxInfoSettingService;
import com.px.pa.vo.param.CodeTaskExampleBatchParam;
import com.px.pa.vo.param.QrCodeParam;
import com.px.pa.vo.param.SubmitAuditParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.open.bean.result.WxFastMaBeenSetCategoryResult;
import me.chanjar.weixin.open.bean.result.WxOpenMaQueryAuditResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

// import com.pig4cloud.pig.common.log.annotation.SysLog;


/**
 * 任务执行记录
 *
 *
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/code/task/example")
@Api(value = "codetaskexample", tags = "任务执行记录管理")
public class CodeTaskExampleController {
	@Autowired
	private CodeTaskExampleService codeTaskExampleService;
	@Autowired
	private AppletsService appletsService;
	@Autowired
	private WxCodeManageService codeManageService;
	@Autowired
	private WxInfoSettingService infoSettingService;

	@ApiOperation(value = "分页查询某个任务的执行记录", notes = "分页查询某个任务的执行记录")
	@GetMapping("/page/byTask")
//	@PreAuthorize("@pms.hasPermission('mpopen_codetaskexample_get')")
	public R queryByTask(Page page, CodeTaskExample codeTaskExample) {
		if (codeTaskExample.getTaskId() == null) {
			return R.failed("请输入要查询的任务");
		}
		page.addOrder(OrderItemUtil.desc("id"));
		return R.ok(codeTaskExampleService.page(page, Wrappers.query(codeTaskExample)));
	}

	/**
	 * 分页查询
	 *
	 * @param page            分页对象
	 * @param codeTaskExample 任务执行记录
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
//	@PreAuthorize("@pms.hasPermission('mpopen_codetaskexample_get')")
	public R getCodeTaskExamplePage(Page page, CodeTaskExample codeTaskExample) {
		page.addOrder(OrderItemUtil.desc("id"));
		return R.ok(codeTaskExampleService.page(page, Wrappers.query(codeTaskExample)));
	}


	/**
	 * 通过id查询任务执行记录
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}")
//	@PreAuthorize("@pms.hasPermission('mpopen_codetaskexample_get')")
	public R getById(@PathVariable("id") Integer id) {
		return R.ok(codeTaskExampleService.getById(id));
	}

	/**
	 * 新增任务执行记录
	 *
	 * @param codeTaskExample 任务执行记录
	 * @return R
	 */
	@ApiOperation(value = "新增任务执行记录", notes = "新增任务执行记录")
	// @SysLog("新增任务执行记录")
	@PostMapping
//	@PreAuthorize("@pms.hasPermission('mpopen_codetaskexample_add')")
	public R save(@RequestBody CodeTaskExample codeTaskExample) {
		//默认是待提交审核
		codeTaskExample.setStatus(CodeTaskExample.STATUS_NONE);
		codeTaskExample.setCreateTime(LocalDateTime.now());
		codeTaskExample.setUseStatus(CodeTaskExample.USE_STATUS_NONE);
		return R.ok(codeTaskExampleService.save(codeTaskExample));
	}

	@ApiOperation(value = "批量新增任务执行记录", notes = "批量新增任务执行记录")
//	// @SysLog("批量新增任务执行记录")
	@PostMapping("/update/batch")
//	@PreAuthorize("@pms.hasPermission('mpopen_codetaskexample_add')")
	public R batchSave(@RequestBody CodeTaskExampleBatchParam param) {
//		List<String> failedAppids = new ArrayList<>();
		if (param.getAppIdList() != null) {
			param.getAppIdList().forEach(appId -> {
				Applets applets = this.appletsService.readByAppId(appId);
//				if (applets == null) {
//					failedAppids.add(appId);
//					return;
//				}
				//新增任务记录信息
				CodeTaskExample codeTaskExample = new CodeTaskExample();
				codeTaskExample.setStatus(CodeTaskExample.STATUS_NONE);
				codeTaskExample.setCreateTime(LocalDateTime.now());
				codeTaskExample.setAppid(appId);
				codeTaskExample.setTaskId(param.getTaskId());
				codeTaskExample.setAppName(applets.getAppName());
				codeTaskExample.setUseStatus(CodeTaskExample.USE_STATUS_NONE);
				this.codeTaskExampleService.save(codeTaskExample);
			});

		}
		return R.ok();
	}

	/**
	 * 修改任务执行记录
	 *
	 * @param codeTaskExample 任务执行记录
	 * @return R
	 */
	@ApiOperation(value = "修改任务执行记录", notes = "修改任务执行记录")
//	// @SysLog("修改任务执行记录")
	@PutMapping
//	@PreAuthorize("@pms.hasPermission('mpopen_codetaskexample_edit')")
	public R updateById(@RequestBody CodeTaskExample codeTaskExample) {
		return R.ok(codeTaskExampleService.updateById(codeTaskExample));
	}

	/**
	 * 通过id删除任务执行记录
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除任务执行记录", notes = "通过id删除任务执行记录")
//	// @SysLog("通过id删除任务执行记录")
	@DeleteMapping("/{id}")
//	@PreAuthorize("@pms.hasPermission('mpopen_codetaskexample_del')")
	public R removeById(@PathVariable Integer id) {
		return R.ok(codeTaskExampleService.removeById(id));
	}

	@ApiOperation(value = "提交审核", notes = "提交审核")
//	// @SysLog("提交审核")
	@PostMapping("/submitAudit")
	public R submitAudit(@RequestBody SubmitAuditParam param) {
		CodeTaskExample taskExample = this.codeTaskExampleService.getById(param.getId());
		taskExample.setPage(param.getPath());
		return this.codeTaskExampleService.submitAudit(taskExample);
	}

	/**
	 * 查询审批结果
	 *
	 * @return
	 */
	@ApiOperation(value = "获取审核结果", notes = "获取审核结果")
//	// @SysLog("获取审核结果")
	@GetMapping("/read/audit/{id}")
//	@PreAuthorize("@pms.hasPermission('mpopen_codetaskexample_del')")
	public R readAuditResult(@PathVariable("id") Integer id) {
		CodeTaskExample taskExample = this.codeTaskExampleService.getById(id);
		WxOpenMaQueryAuditResult result = this.codeManageService.readAuditResult(taskExample);
		if (result.isSuccess()) {
			//如果是已经审核通过
			if (result.getStatus().equals(Integer.valueOf(0))) {
				taskExample.setStatus(CodeTaskExample.STATUS_SUCCESS);
			} else if (result.getStatus().equals(Integer.valueOf(1))) {
				taskExample.setStatus(CodeTaskExample.STATUS_ERROR);
			}
			this.codeTaskExampleService.updateById(taskExample);
		}
		return R.ok(result);
	}

	@ApiOperation(value = "撤回审核", notes = "撤回审核")
//	// @SysLog("撤回审核")
	@GetMapping("/undocodeAudit/{id}")
	public R undocodeAudit(@PathVariable("id") Integer id) {
		CodeTaskExample taskExample = this.codeTaskExampleService.getById(id);
		R r=this.codeTaskExampleService.undocodeAudit(taskExample);
		return r;
	}

	@ApiOperation(value = "发布审核通过的小程序", notes = "发布审核通过的小程序")
//	// @SysLog("发布审核通过的小程序")
	@GetMapping("/release/{id}")
	public R release(@PathVariable("id") Integer id) {
		CodeTaskExample taskExample = this.codeTaskExampleService.getById(id);
		boolean flag=this.codeTaskExampleService.release(taskExample);
		return R.ok(flag);
	}

	@ApiOperation(value = "查询某个小程序的所有发布记录信息", notes = "查询某个小程序的所有发布记录信息")
	// @SysLog("查询某个小程序的所有发布记录信息")
	@GetMapping("/read/byAppId/{appId}")
	public R queryByAppId(@PathVariable("appId") String appId) {
		List<CodeTaskExample> tes = this.codeTaskExampleService.queryByAppId(appId);
		return R.ok(tes);
	}

	@ApiOperation(value = "获取小程序的体验版二维码", notes = "获取小程序的体验版二维码")
	// @SysLog("获取小程序的体验版二维码")
	@PutMapping("/read/qrcode")
	public R getTestQrCode2Base64(@RequestBody QrCodeParam param) {
		CodeTaskExample taskExample = this.codeTaskExampleService.getById(param.getId());
		String base64Code = this.codeManageService.getTestQrCode2Base64(taskExample.getAppid(), param.getPath());
		return R.ok(base64Code);
	}

	@ApiOperation(value = "获取小程序提交时候可以使用的类目", notes = "获取小程序提交时候可以使用的类目")
	// @SysLog("获取小程序提交时候可以使用的类目")
	@PutMapping("/read/categorys/{id}")
	public R getSubmitCategorys(@PathVariable("id") Integer id) {
		CodeTaskExample taskExample = this.codeTaskExampleService.getById(id);
		List<WxFastMaBeenSetCategoryResult.CategoriesBean> cates = this.infoSettingService.queryUseCategories(taskExample.getAppid());
		return R.ok(cates);
	}

}
