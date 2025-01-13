

package com.px.pa.modulars.info.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.px.pa.modulars.info.entity.AuditLog;
import com.px.pa.modulars.info.service.AuditLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

// import com.pig4cloud.pig.common.log.annotation.SysLog;


/**
 * 审批记录
 *
 *
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auditlog" )
@Api(value = "auditlog", tags = "审批记录管理")
public class AuditLogController {

    private final AuditLogService auditLogService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param auditLog 审批记录
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('info_auditlog_get')" )
    public R getAuditLogPage(Page page, AuditLog auditLog) {
        return R.ok(auditLogService.page(page, Wrappers.query(auditLog)));
    }


    /**
     * 通过id查询审批记录
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('info_auditlog_get')" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(auditLogService.getById(id));
    }

    /**
     * 新增审批记录
     * @param auditLog 审批记录
     * @return R
     */
    @ApiOperation(value = "新增审批记录", notes = "新增审批记录")
    // @SysLog("新增审批记录" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('info_auditlog_add')" )
    public R save(@RequestBody AuditLog auditLog) {
        return R.ok(auditLogService.save(auditLog));
    }

    /**
     * 修改审批记录
     * @param auditLog 审批记录
     * @return R
     */
    @ApiOperation(value = "修改审批记录", notes = "修改审批记录")
    // @SysLog("修改审批记录" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('info_auditlog_edit')" )
    public R updateById(@RequestBody AuditLog auditLog) {
        return R.ok(auditLogService.updateById(auditLog));
    }

    /**
     * 通过id删除审批记录
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除审批记录", notes = "通过id删除审批记录")
    // @SysLog("通过id删除审批记录" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('info_auditlog_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(auditLogService.removeById(id));
    }

}
