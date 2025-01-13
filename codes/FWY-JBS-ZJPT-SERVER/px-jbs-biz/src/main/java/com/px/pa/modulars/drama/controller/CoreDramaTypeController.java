
package com.px.pa.modulars.drama.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.px.pa.modulars.drama.entity.CoreDramaType;
import com.px.pa.modulars.drama.service.CoreDramaTypeService;
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
 * 剧本分类关联
 *
 *
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/drama/coredramatype")
@Api(value = "coredramatype", tags = "剧本分类关联管理")
public class CoreDramaTypeController extends BaseController<CoreDramaType,CoreDramaTypeService> {

    private final  CoreDramaTypeService coreDramaTypeService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param coreDramaType 剧本分类关联
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('drama_coredramatype_get')")
    public R getCoreDramaTypePage(Page<Integer> page, CoreDramaType coreDramaType) {
        return super.query(coreDramaType, page);
    }


    /**
     * 通过id查询剧本分类关联
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('drama_coredramatype_get')")
    public R getById(@PathVariable("id") Integer id) {
        return super.get(id);
    }

    /**
     * 新增剧本分类关联
     * @param coreDramaType 剧本分类关联
     * @return R
     */
    @ApiOperation(value = "新增剧本分类关联", notes = "新增剧本分类关联")
    @PostMapping
    @SysLog("新增剧本分类关联")
    @PreAuthorize("@pms.hasPermission('drama_coredramatype_add')")
    public R save(@Validated @RequestBody CoreDramaType coreDramaType) {
        return super.update(coreDramaType, SecurityUtils.getUser().getId());
    }

    /**
     * 修改剧本分类关联
     * @param coreDramaType 剧本分类关联
     * @return R
     */
    @ApiOperation(value = "修改剧本分类关联", notes = "修改剧本分类关联")
    @PutMapping
    @SysLog("修改剧本分类关联")
    @PreAuthorize("@pms.hasPermission('drama_coredramatype_edit')")
    public R updateById(@Validated @RequestBody CoreDramaType coreDramaType) {
        return super.update(coreDramaType, SecurityUtils.getUser().getId());
    }

    /**
     * 通过id删除剧本分类关联
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除剧本分类关联", notes = "通过id删除剧本分类关联")
    @DeleteMapping("/{id}")
    @SysLog("通过id删除剧本分类关联")
    @PreAuthorize("@pms.hasPermission('drama_coredramatype_del')")
    public R removeById(@PathVariable Integer id) {
        CoreDramaType coreDramaType=super.service.queryById(id);
        if (coreDramaType ==null){
            return R.failed("ID错误");
        }
        return super.del(coreDramaType, SecurityUtils.getUser().getId());
    }

}
