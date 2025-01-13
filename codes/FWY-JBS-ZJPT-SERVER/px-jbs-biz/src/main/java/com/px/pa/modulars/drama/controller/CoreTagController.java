
package com.px.pa.modulars.drama.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.constant.Constants;
import com.pig4cloud.pig.common.core.util.R;
import com.px.pa.modulars.drama.entity.CoreSpec;
import com.px.pa.modulars.drama.entity.CoreTag;
import com.px.pa.modulars.drama.service.CoreTagService;
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
 * 剧本标签
 *
 *
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/drama/coretag")
@Api(value = "coretag", tags = "剧本标签管理")
public class CoreTagController extends BaseController<CoreTag,CoreTagService> {

    private final  CoreTagService coreTagService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param coreTag 剧本标签
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('drama_coretag_get')")
    public R getCoreTagPage(Page page, CoreTag coreTag) {
        return R.ok(coreTagService.lambdaQuery()
                .like(StrUtil.isNotEmpty(coreTag.getName()),CoreTag::getName,coreTag.getName())
                .eq(CoreTag::getEnable, Constants.ENABLE_TRUE)
                .orderByDesc(CoreTag::getId)
                .page(page)
        );
    }

    @ApiOperation(value = "列表查询", notes = "列表查询")
    @GetMapping("/list")
    public R getCoreSpecList() {
        return R.ok(coreTagService.lambdaQuery().eq(CoreTag::getEnable, 1).list());
    }

    /**
     * 通过id查询剧本标签
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('drama_coretag_get')")
    public R getById(@PathVariable("id") Integer id) {
        return super.get(id);
    }

    /**
     * 新增剧本标签
     * @param coreTag 剧本标签
     * @return R
     */
    @ApiOperation(value = "新增剧本标签", notes = "新增剧本标签")
    @PostMapping
    @SysLog("新增剧本标签")
    @PreAuthorize("@pms.hasPermission('drama_coretag_add')")
    public R save(@Validated @RequestBody CoreTag coreTag) {
        return super.update(coreTag, SecurityUtils.getUser().getId());
    }

    /**
     * 修改剧本标签
     * @param coreTag 剧本标签
     * @return R
     */
    @ApiOperation(value = "修改剧本标签", notes = "修改剧本标签")
    @PutMapping
    @SysLog("修改剧本标签")
    @PreAuthorize("@pms.hasPermission('drama_coretag_edit')")
    public R updateById(@Validated @RequestBody CoreTag coreTag) {
        return super.update(coreTag, SecurityUtils.getUser().getId());
    }

    /**
     * 通过id删除剧本标签
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除剧本标签", notes = "通过id删除剧本标签")
    @DeleteMapping("/{id}")
    @SysLog("通过id删除剧本标签")
    @PreAuthorize("@pms.hasPermission('drama_coretag_del')")
    public R removeById(@PathVariable Integer id) {
        CoreTag coreTag=super.service.queryById(id);
        if (coreTag ==null){
            return R.failed("ID错误");
        }
        coreTag.setEnable(Constants.ENABLE_FALSE);
        return super.update(coreTag, SecurityUtils.getUser().getId());
    }

}
