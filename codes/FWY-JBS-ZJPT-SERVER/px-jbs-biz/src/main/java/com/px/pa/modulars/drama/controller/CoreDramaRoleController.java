
package com.px.pa.modulars.drama.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.px.pa.modulars.drama.entity.CoreDramaRole;
import com.px.pa.modulars.drama.service.CoreDramaRoleService;
import com.px.basic.alone.core.base.BaseController;
import com.px.common.log.annotation.SysLog;
import com.px.basic.alone.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 剧本角色
 *
 * 
 * 
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/drama/coredramarole")
@Api(value = "coredramarole", tags = "剧本角色管理")
public class CoreDramaRoleController extends BaseController<CoreDramaRole,CoreDramaRoleService> {

    private final  CoreDramaRoleService coreDramaRoleService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param coreDramaRole 剧本角色
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('drama_coredramarole_get')")
    public R getCoreDramaRolePage(Page<Integer> page, CoreDramaRole coreDramaRole) {
        return super.query(coreDramaRole, page);
    }

    @ApiOperation(value = "列表查询", notes = "列表查询")
    @GetMapping("/list")
    @PreAuthorize("@pms.hasPermission('drama_coredramarole_get')")
    public R getCoreDramaRoleList(Integer id) {
        return R.ok(
                coreDramaRoleService.lambdaQuery().eq(CoreDramaRole::getDid,id).list()
        );
    }


    /**
     * 通过id查询剧本角色
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('drama_coredramarole_get')")
    public R getById(@PathVariable("id") Integer id) {
        return super.get(id);
    }

    /**
     * 新增剧本角色
     * @param coreDramaRole 剧本角色
     * @return R
     */
    @ApiOperation(value = "新增剧本角色", notes = "新增剧本角色")
    @PostMapping
    @SysLog("新增剧本角色")
    @PreAuthorize("@pms.hasPermission('drama_coredramarole_add')")
    public R save(@Validated @RequestBody CoreDramaRole coreDramaRole) {
        return super.update(coreDramaRole, SecurityUtils.getUser().getId());
    }

    /**
     * 修改剧本角色
     * @param coreDramaRole 剧本角色
     * @return R
     */
    @ApiOperation(value = "修改剧本角色", notes = "修改剧本角色")
    @PutMapping
    @SysLog("修改剧本角色")
    @PreAuthorize("@pms.hasPermission('drama_coredramarole_edit')")
    public R updateById(@Validated @RequestBody CoreDramaRole coreDramaRole) {
        return super.update(coreDramaRole, SecurityUtils.getUser().getId());
    }

    /**
     * 通过id删除剧本角色
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除剧本角色", notes = "通过id删除剧本角色")
    @DeleteMapping("/{id}")
    @SysLog("通过id删除剧本角色")
    @PreAuthorize("@pms.hasPermission('drama_coredramarole_del')")
    public R removeById(@PathVariable Integer id) {
        CoreDramaRole coreDramaRole=super.service.queryById(id);
        if (coreDramaRole ==null){
            return R.failed("ID错误");
        }
        return super.del(coreDramaRole, SecurityUtils.getUser().getId());
    }

}
