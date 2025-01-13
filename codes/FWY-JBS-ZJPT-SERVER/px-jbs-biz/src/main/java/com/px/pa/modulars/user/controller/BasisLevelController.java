
package com.px.pa.modulars.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.constant.Constants;
import com.pig4cloud.pig.common.core.util.R;
import com.px.pa.modulars.shop.entity.CoreShop;
import com.px.pa.modulars.shop.service.CoreShopService;
import com.px.pa.modulars.user.entity.BasisLevel;
import com.px.pa.modulars.user.service.BasisLevelService;
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
 * 等级
 *
 *
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/basisLevel/basislevel")
@Api(value = "basislevel", tags = "等级管理")
public class BasisLevelController extends BaseController<BasisLevel,BasisLevelService> {

    private final  BasisLevelService basisLevelService;
    private final CoreShopService shopService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param basisLevel 等级
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('basisLevel_basislevel_get')")
    public R getBasisLevelPage(Page<Integer> page, BasisLevel basisLevel) {
        basisLevel.setCoreShop(getSid());
        return super.query(basisLevel, page);
    }

    @ApiOperation(value = "列表查询", notes = "列表查询")
    @GetMapping("/list")
    public R getBasisLevelList() {
        return R.ok(
                basisLevelService.lambdaQuery()
                        .eq(BasisLevel::getEnable, Constants.ENABLE_TRUE)
                        .eq(BasisLevel::getCoreShop,getSid())
                        .list()
        );
    }

    public Integer getSid(){
        return shopService.lambdaQuery().eq(CoreShop::getUid, SecurityUtils.getUser().getId()).one().getId();
    }


    /**
     * 通过id查询等级
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('basisLevel_basislevel_get')")
    public R getById(@PathVariable("id") Integer id) {
        return super.get(id);
    }

    /**
     * 新增等级
     * @param basisLevel 等级
     * @return R
     */
    @ApiOperation(value = "新增等级", notes = "新增等级")
    @PostMapping
    @SysLog("新增等级")
    @PreAuthorize("@pms.hasPermission('basisLevel_basislevel_add')")
    public R save(@Validated @RequestBody BasisLevel basisLevel) {
        return super.update(basisLevel, SecurityUtils.getUser().getId());
    }

    /**
     * 修改等级
     * @param basisLevel 等级
     * @return R
     */
    @ApiOperation(value = "修改等级", notes = "修改等级")
    @PutMapping
    @SysLog("修改等级")
    @PreAuthorize("@pms.hasPermission('basisLevel_basislevel_edit')")
    public R updateById(@Validated @RequestBody BasisLevel basisLevel) {
        return super.update(basisLevel, SecurityUtils.getUser().getId());
    }

    /**
     * 通过id删除等级
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除等级", notes = "通过id删除等级")
    @DeleteMapping("/{id}")
    @SysLog("通过id删除等级")
    @PreAuthorize("@pms.hasPermission('basisLevel_basislevel_del')")
    public R removeById(@PathVariable Integer id) {
        BasisLevel basisLevel=super.service.queryById(id);
        if (basisLevel ==null){
            return R.failed("ID错误");
        }
        return super.del(basisLevel, SecurityUtils.getUser().getId());
    }

}
