

package com.px.pa.modulars.user.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.px.pa.modulars.user.entity.BasisUsersPush;
import com.px.pa.modulars.user.service.BasisUsersPushService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 用户推送消息
 *
 *
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/basisuserspush" )
@Api(value = "basisuserspush", tags = "用户推送消息管理")
public class BasisUsersPushController {

    private final  BasisUsersPushService basisUsersPushService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param basisUsersPush 用户推送消息
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('user_basisuserspush_get')" )
    public R getBasisUsersPushPage(Page page, BasisUsersPush basisUsersPush) {
        return R.ok(basisUsersPushService.page(page, Wrappers.query(basisUsersPush)));
    }


    /**
     * 通过id查询用户推送消息
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('user_basisuserspush_get')" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(basisUsersPushService.getById(id));
    }

    /**
     * 新增用户推送消息
     * @param basisUsersPush 用户推送消息
     * @return R
     */
    @ApiOperation(value = "新增用户推送消息", notes = "新增用户推送消息")
    @PostMapping
    @PreAuthorize("@pms.hasPermission('user_basisuserspush_add')" )
    public R save(@RequestBody BasisUsersPush basisUsersPush) {
        return R.ok(basisUsersPushService.save(basisUsersPush));
    }

    /**
     * 修改用户推送消息
     * @param basisUsersPush 用户推送消息
     * @return R
     */
    @ApiOperation(value = "修改用户推送消息", notes = "修改用户推送消息")
    @PutMapping
    @PreAuthorize("@pms.hasPermission('user_basisuserspush_edit')" )
    public R updateById(@RequestBody BasisUsersPush basisUsersPush) {
        return R.ok(basisUsersPushService.updateById(basisUsersPush));
    }

    /**
     * 通过id删除用户推送消息
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除用户推送消息", notes = "通过id删除用户推送消息")
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('user_basisuserspush_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(basisUsersPushService.removeById(id));
    }

}
