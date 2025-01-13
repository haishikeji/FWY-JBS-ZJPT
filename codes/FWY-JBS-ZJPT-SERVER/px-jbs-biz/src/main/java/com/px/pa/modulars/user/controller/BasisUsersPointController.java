

package com.px.pa.modulars.user.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.px.pa.modulars.user.entity.BasisUsersPoint;
import com.px.pa.modulars.user.service.BasisUsersPointService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 用户位置信息
 *
 *
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/basisuserspoint" )
@Api(value = "basisuserspoint", tags = "用户位置信息管理")
public class BasisUsersPointController {

    private final  BasisUsersPointService basisUsersPointService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param basisUsersPoint 用户位置信息
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('user_basisuserspoint_get')" )
    public R getBasisUsersPointPage(Page page, BasisUsersPoint basisUsersPoint) {
        return R.ok(basisUsersPointService.page(page, Wrappers.query(basisUsersPoint)));
    }


    /**
     * 通过id查询用户位置信息
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('user_basisuserspoint_get')" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(basisUsersPointService.getById(id));
    }

    /**
     * 新增用户位置信息
     * @param basisUsersPoint 用户位置信息
     * @return R
     */
    @ApiOperation(value = "新增用户位置信息", notes = "新增用户位置信息")
    @PostMapping
    @PreAuthorize("@pms.hasPermission('user_basisuserspoint_add')" )
    public R save(@RequestBody BasisUsersPoint basisUsersPoint) {
        return R.ok(basisUsersPointService.save(basisUsersPoint));
    }

    /**
     * 修改用户位置信息
     * @param basisUsersPoint 用户位置信息
     * @return R
     */
    @ApiOperation(value = "修改用户位置信息", notes = "修改用户位置信息")
    @PutMapping
    @PreAuthorize("@pms.hasPermission('user_basisuserspoint_edit')" )
    public R updateById(@RequestBody BasisUsersPoint basisUsersPoint) {
        return R.ok(basisUsersPointService.updateById(basisUsersPoint));
    }

    /**
     * 通过id删除用户位置信息
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除用户位置信息", notes = "通过id删除用户位置信息")
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('user_basisuserspoint_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(basisUsersPointService.removeById(id));
    }

}
