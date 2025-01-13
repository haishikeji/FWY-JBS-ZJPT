

package com.px.pa.modulars.info.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.px.pa.modulars.info.entity.AuditType;
import com.px.pa.modulars.info.service.AuditTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

// import com.pig4cloud.pig.common.log.annotation.SysLog;


/**
 * 审批类型
 *
 *
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/audittype")
@Api(value = "audittype", tags = "审批类型管理")
public class AuditTypeController {

	private final AuditTypeService auditTypeService;

	/**
	 * 分页查询
	 *
	 * @param page      分页对象
	 * @param auditType 审批类型
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	@PreAuthorize("@pms.hasPermission('info_audittype_get')")
	public R getAuditTypePage(Page page, AuditType auditType) {
		return R.ok(auditTypeService.page(page, Wrappers.query(auditType)));
	}


	/**
	 * 通过id查询审批类型
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('info_audittype_get')")
	public R getById(@PathVariable("id") Integer id) {
		return R.ok(auditTypeService.getById(id));
	}

	/**
	 * 新增审批类型
	 *
	 * @param auditType 审批类型
	 * @return R
	 */
	@ApiOperation(value = "新增审批类型", notes = "新增审批类型")
	// @SysLog("新增审批类型")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('info_audittype_add')")
	public R save(@RequestBody AuditType auditType) {
		AuditType at = this.auditTypeService.readByCode(auditType.getCode());
		if (at != null) {
			return R.failed("Code已存在，请修改后重新提交");
		}
		return R.ok(auditTypeService.save(auditType));
	}

	/**
	 * 修改审批类型
	 *
	 * @param auditType 审批类型
	 * @return R
	 */
	@ApiOperation(value = "修改审批类型", notes = "修改审批类型")
	// @SysLog("修改审批类型")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('info_audittype_edit')")
	public R updateById(@RequestBody AuditType auditType) {
		return R.ok(auditTypeService.updateById(auditType));
	}

	/**
	 * 通过id删除审批类型
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除审批类型", notes = "通过id删除审批类型")
	// @SysLog("通过id删除审批类型")
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('info_audittype_del')")
	public R removeById(@PathVariable Integer id) {
		return R.ok(auditTypeService.removeById(id));
	}

}
