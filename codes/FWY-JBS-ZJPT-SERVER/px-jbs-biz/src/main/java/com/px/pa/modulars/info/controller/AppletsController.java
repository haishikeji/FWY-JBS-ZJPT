

package com.px.pa.modulars.info.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.core.vo.form.FormTreeVo;
import com.pig4cloud.pig.common.mybatis.util.OrderItemUtil;
import com.px.basic.alone.security.annotation.Inner;
import com.px.fastfile.service.FastfileService;
import com.px.pa.modulars.code.entity.CodeTask;
import com.px.pa.modulars.code.service.CodeTaskService;
import com.px.pa.modulars.info.entity.Applets;
import com.px.pa.modulars.info.service.AppletsService;
import com.px.pa.modulars.shop.service.CoreShopService;
import com.px.pa.modulars.user.entity.CoreWx;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

// import com.pig4cloud.pig.common.log.annotation.SysLog;


/**
 * 小程序信息
 *
 * 
 * 
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/info/applets")
@Api(value = "applets", tags = "小程序信息管理")
public class AppletsController {
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    private AppletsService appletsService;
    @Autowired
    private CodeTaskService codeTaskService;
    @Autowired
    private FastfileService fastfileService;
    @Autowired
    private CoreShopService shopService;

    /**
     * 分页查询
     *
     * @param page    分页对象
     * @param applets 小程序信息
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('info_applets_get')")
    public R getAppletsPage(Page page, Applets applets) {
        //排除已经授权通过的小程序
        QueryWrapper<Applets> wrappers = Wrappers.query(applets);
        wrappers.notIn("status", Applets.STATUS_SUCCESS);
        page.addOrder(OrderItemUtil.desc("id"));
        return R.ok(appletsService.page(page, wrappers));
    }

    /**
     * 查询可更新的小程序信息
     *
     * @param task 任务信息
     * @return
     */
    @ApiOperation(value = "查询可更新的小程序信息", notes = "查询可更新的小程序信息")
    @PutMapping("/read/renewable")
    public R getRenewable(@RequestBody CodeTask task) {
        task = this.codeTaskService.getById(task.getId());
        List<Applets> list = this.appletsService.queryRenewable(task.getProductId());
        return R.ok(list);
    }

    /**
     * 分页查询
     *
     * @param page    分页对象
     * @param applets 小程序信息
     * @return
     */
    @ApiOperation(value = "分页查询管理", notes = "分页查询")
    @GetMapping("/managePage")
    @PreAuthorize("@pms.hasPermission('info_applets_get')")
    public R getAppletsManagePage(Page page, Applets applets) {
        //只查询已经通过的
//		applets.setStatus(Applets.STATUS_SUCCESS);
        page.addOrder(OrderItemUtil.desc("id"));
        return R.ok(appletsService.page(page, Wrappers.query(applets)));
    }


    /**
     * 通过id查询小程序信息
     *
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('info_applets_get')")
    public R getById(@PathVariable("id") Integer id) {
        return R.ok(appletsService.getById(id));
    }

    /**
     * 新增小程序信息
     *
     * @param applets 小程序信息
     * @return R
     */
    @ApiOperation(value = "新增小程序信息", notes = "新增小程序信息")
    // @SysLog("新增小程序信息")
    @PostMapping
    @PreAuthorize("@pms.hasPermission('info_applets_add')")
    public R save(@RequestBody Applets applets) {
        applets.setStatus(Applets.STATUS_NONE);
        //TODO 第三方的手机号，需要可以配置
        applets.setComponentPhone("15136201795");
        boolean flag = appletsService.save(applets);
        if (flag) {

        }
        return R.ok(flag);
    }

    /**
     * 修改小程序信息
     *
     * @param applets 小程序信息
     * @return R
     */
    @ApiOperation(value = "修改小程序信息", notes = "修改小程序信息")
    // @SysLog("修改小程序信息")
    @PutMapping
    @PreAuthorize("@pms.hasPermission('info_applets_edit')")
    public R updateById(@RequestBody Applets applets) {
        return R.ok(appletsService.updateById(applets));
    }

    /**
     * 通过id删除小程序信息
     *
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除小程序信息", notes = "通过id删除小程序信息")
    // @SysLog("通过id删除小程序信息")
    @DeleteMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('info_applets_del')")
    public R removeById(@PathVariable Integer id) {
        return R.ok(appletsService.removeById(id));
    }

    @ApiOperation(value = "创建小程序", notes = "通过ID创建小程序")
    // @SysLog("通过ID创建小程序")
    @GetMapping("/createMp/{id}")
    @PreAuthorize("@pms.hasPermission('info_applets_create_mp')")
    public R createWxMp(@PathVariable Integer id) {
        Applets applet = this.appletsService.getById(id);
        if (applet == null) {
            //TODO 需要统一异常处理
            //TODO 异常处理需要国际化
            return R.failed("参数错误");
        }
        boolean flag = this.appletsService.createWxMp(applet);
        return R.ok(flag);
    }


    @ApiOperation("初始化小程序配置信息")
    @GetMapping("/update/initMpSetting/{appId}")
//	@PreAuthorize("@pms.hasPermission('info_applets_create_mp')")
    public R initMpSetting(@PathVariable("appId") String appId) {
        //TODO 待实现
        return R.ok();
    }

    @ApiOperation("获取小程序信息树，按照小程序分类展示")
    @GetMapping("/read/treeByCate")
    public R queryTreeByCate() {
        List<FormTreeVo> trees = this.appletsService.queryTreeByCate();
        return R.ok(trees);
    }


    @ApiOperation("获取小程序信息树，按照小程序分类展示")
    @PostMapping("/uploadPortrait")
    @Inner(false)
    public R uploadPortrait(@RequestParam MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        String key = "px-jbs";
        Map<String, Object> fileResult = this.fastfileService.uploadFile(key, file);
        System.out.println("------------------------------------");
        System.out.println(JSONUtil.toJsonStr(fileResult));
        Integer shopid=getShopId();
        System.out.println("SHOPID:::"+shopid);
        String appid=shopService.getById(shopid).getAppid();
        System.out.println("APPID:::"+appid);
        try{
            String msg=this.appletsService.uploadPortrait(appid, fileResult.get("kpath").toString());
            if(StrUtil.isEmpty(msg)){
                return R.ok();
            }else{
                return R.failed(msg);
            }
        }catch (Exception e){
            return R.failed(e.getMessage());
        }
    }

    @ApiOperation("获取小程序信息树，按照小程序分类展示")
    @GetMapping("/uploadPortrait/{appid}/{imgid}")
    @Inner(false)
    public R uploadPortrait(@PathVariable("appid") String appid, @PathVariable("imgid") String imgid) {
        String key = "px-jbs";
        try {
            String msg=this.appletsService.uploadPortrait(appid, "http://res.jb.hnpinxun.cn/" + imgid + ".jpg");
            if(StrUtil.isEmpty(msg)){
                return R.ok();
            }else{
                return R.failed(msg);
            }
        }catch (Exception e){
            return R.failed(e.getMessage());
        }
    }

    public Integer getShopId() {
        String shopId = this.request.getHeader("SHOPID");
        if (StrUtil.isNotEmpty(shopId)) {
            return Integer.valueOf(shopId);
        } else {
            return 0;
        }
    }
}
