

package com.px.pa.modulars.code.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.mybatis.util.OrderItemUtil;
import com.px.pa.modulars.code.entity.CodePage;
import com.px.pa.modulars.code.service.CodePageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

//// import com.pig4cloud.pig.common.log.annotation.SysLog;


/**
 * 页面配置信息
 *
 *
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/codepage" )
@Api(value = "codepage", tags = "页面配置信息管理")
public class CodePageController {

    private final CodePageService codePageService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param codePage 页面配置信息
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('code_codepage_get')" )
    public R getCodePagePage(Page page, CodePage codePage) {
        page.addOrder(OrderItemUtil.desc("id"));
        return R.ok(codePageService.page(page, Wrappers.query(codePage)));
    }


    /**
     * 通过id查询页面配置信息
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('code_codepage_get')" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(codePageService.getById(id));
    }

    /**
     * 新增页面配置信息
     * @param codePage 页面配置信息
     * @return R
     */
    @ApiOperation(value = "新增页面配置信息", notes = "新增页面配置信息")
//    // @SysLog("新增页面配置信息" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('code_codepage_add')" )
    public R save(@RequestBody CodePage codePage) {
        return R.ok(codePageService.save(codePage));
    }

    /**
     * 修改页面配置信息
     * @param codePage 页面配置信息
     * @return R
     */
    @ApiOperation(value = "修改页面配置信息", notes = "修改页面配置信息")
//    // @SysLog("修改页面配置信息" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('code_codepage_edit')" )
    public R updateById(@RequestBody CodePage codePage) {
        return R.ok(codePageService.updateById(codePage));
    }

    /**
     * 通过id删除页面配置信息
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除页面配置信息", notes = "通过id删除页面配置信息")
//    // @SysLog("通过id删除页面配置信息" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('code_codepage_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(codePageService.removeById(id));
    }

}
