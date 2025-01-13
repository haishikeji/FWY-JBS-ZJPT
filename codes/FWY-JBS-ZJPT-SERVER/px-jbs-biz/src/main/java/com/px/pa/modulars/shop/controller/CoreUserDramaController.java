
package com.px.pa.modulars.shop.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.px.basic.alone.security.annotation.Inner;
import com.px.pa.modulars.shop.entity.CoreUserDrama;
import com.px.pa.modulars.shop.service.CoreUserDramaService;
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
 * 用户剧本关联记录
 *
 *
 * 
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/shop/coreuserdrama")
@Api(value = "coreuserdrama", tags = "用户剧本关联记录管理")
public class CoreUserDramaController extends BaseController<CoreUserDrama,CoreUserDramaService> {

    private final  CoreUserDramaService coreUserDramaService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param coreUserDrama 用户剧本关联记录
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('shop_coreuserdrama_get')")
    public R getCoreUserDramaPage(Page<Integer> page, CoreUserDrama coreUserDrama) {
        return super.query(coreUserDrama, page);
    }


    /**
     * 通过id查询用户剧本关联记录
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('shop_coreuserdrama_get')")
    public R getById(@PathVariable("id") Integer id) {
        return super.get(id);
    }

    /**
     * 新增用户剧本关联记录
     * @param coreUserDrama 用户剧本关联记录
     * @return R
     */
    @ApiOperation(value = "新增用户剧本关联记录", notes = "新增用户剧本关联记录")
    @PostMapping
    @SysLog("新增用户剧本关联记录")
    @PreAuthorize("@pms.hasPermission('shop_coreuserdrama_add')")
    public R save(@Validated @RequestBody CoreUserDrama coreUserDrama) {
        return super.update(coreUserDrama, SecurityUtils.getUser().getId());
    }

    /**
     * 修改用户剧本关联记录
     * @param coreUserDrama 用户剧本关联记录
     * @return R
     */
    @ApiOperation(value = "修改用户剧本关联记录", notes = "修改用户剧本关联记录")
    @PutMapping
    @SysLog("修改用户剧本关联记录")
    @PreAuthorize("@pms.hasPermission('shop_coreuserdrama_edit')")
    public R updateById(@Validated @RequestBody CoreUserDrama coreUserDrama) {
        return super.update(coreUserDrama, SecurityUtils.getUser().getId());
    }

    /**
     * 通过id删除用户剧本关联记录
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除用户剧本关联记录", notes = "通过id删除用户剧本关联记录")
    @DeleteMapping("/{id}")
    @SysLog("通过id删除用户剧本关联记录")
    @PreAuthorize("@pms.hasPermission('shop_coreuserdrama_del')")
    public R removeById(@PathVariable Integer id) {
        CoreUserDrama coreUserDrama=super.service.queryById(id);
        if (coreUserDrama ==null){
            return R.failed("ID错误");
        }
        return super.del(coreUserDrama, SecurityUtils.getUser().getId());
    }


    @GetMapping("/template")
    @Inner(value = false)
    public R getTemplate(@RequestParam("code") String code,@RequestParam("appid") String appid){
     String template =   coreUserDramaService.getTemplate(code,appid);
     return R.ok(template);
    }
}
