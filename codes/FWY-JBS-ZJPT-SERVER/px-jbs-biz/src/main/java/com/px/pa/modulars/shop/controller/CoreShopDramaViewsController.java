

package com.px.pa.modulars.shop.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.px.pa.modulars.shop.entity.CoreShopDramaViews;
import com.px.pa.modulars.shop.service.CoreShopDramaViewsService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 剧本浏览历史
 *
 * 
 * 
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/coreshopdramaviews" )
@Api(value = "coreshopdramaviews", tags = "剧本浏览历史管理")
public class CoreShopDramaViewsController {

    private final  CoreShopDramaViewsService coreShopDramaViewsService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param coreShopDramaViews 剧本浏览历史
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('user_coreshopdramaviews_get')" )
    public R getCoreShopDramaViewsPage(Page page, CoreShopDramaViews coreShopDramaViews) {
        return R.ok(coreShopDramaViewsService.page(page, Wrappers.query(coreShopDramaViews)));
    }


    /**
     * 通过id查询剧本浏览历史
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('user_coreshopdramaviews_get')" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(coreShopDramaViewsService.getById(id));
    }

    /**
     * 新增剧本浏览历史
     * @param coreShopDramaViews 剧本浏览历史
     * @return R
     */
    @ApiOperation(value = "新增剧本浏览历史", notes = "新增剧本浏览历史")
    @PostMapping
    @PreAuthorize("@pms.hasPermission('user_coreshopdramaviews_add')" )
    public R save(@RequestBody CoreShopDramaViews coreShopDramaViews) {
        return R.ok(coreShopDramaViewsService.save(coreShopDramaViews));
    }

    /**
     * 修改剧本浏览历史
     * @param coreShopDramaViews 剧本浏览历史
     * @return R
     */
    @ApiOperation(value = "修改剧本浏览历史", notes = "修改剧本浏览历史")
    @PutMapping
    @PreAuthorize("@pms.hasPermission('user_coreshopdramaviews_edit')" )
    public R updateById(@RequestBody CoreShopDramaViews coreShopDramaViews) {
        return R.ok(coreShopDramaViewsService.updateById(coreShopDramaViews));
    }

    /**
     * 通过id删除剧本浏览历史
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除剧本浏览历史", notes = "通过id删除剧本浏览历史")
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('user_coreshopdramaviews_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(coreShopDramaViewsService.removeById(id));
    }

}
