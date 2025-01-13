
package com.px.pa.modulars.user.controller;



import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.JsonObject;
import com.pig4cloud.pig.common.core.util.R;
import com.px.basic.alone.security.annotation.Inner;
import com.px.pa.modulars.shop.entity.CoreShop;
import com.px.pa.modulars.shop.service.CoreShopService;
import com.px.pa.modulars.user.entity.BasisShopUser;
import com.px.pa.modulars.user.service.BasisShopUserService;
import com.px.basic.alone.core.base.BaseController;
import com.px.common.log.annotation.SysLog;
import com.px.basic.alone.security.util.SecurityUtils;
import com.px.pa.modulars.user.vo.ShopUserVo;
import com.px.wx.mini.config.WxMaConfiguration;
import me.chanjar.weixin.common.enums.WxType;
import me.chanjar.weixin.common.error.WxError;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.util.json.GsonParser;
import me.chanjar.weixin.open.api.WxOpenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * 商铺会员关联表
 *
 *
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/basisShopUser/basisshopuser")
@Api(value = "basisshopuser", tags = "商铺会员关联表管理")
public class BasisShopUserController extends BaseController<BasisShopUser,BasisShopUserService> {

    private final  BasisShopUserService basisShopUserService;
    private final CoreShopService shopService;
    private final WxOpenService wxOpenService;
    private final WxMaService wxMaService;
    @Value("${wxgl.appid}")
    private String appid;
    @Value("${wxgl.secret}")
    private String secret;


    /**
     * 分页查询
     * @param page 分页对象
     * @param basisShopUser 商铺会员关联表
     * @return
     */
    ;@ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('basisShopUser_basisshopuser_get')")
    public R getBasisShopUserPage(Page page, ShopUserVo basisShopUser) {
        basisShopUser.setShopId(getSid());
        return R.ok(basisShopUserService.getBasisShopUserPage(page,basisShopUser));
    }

    public Integer getSid(){
        return shopService.lambdaQuery().eq(CoreShop::getUid, SecurityUtils.getUser().getId()).one().getId();
    }


    /**
     * 通过id查询商铺会员关联表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('basisShopUser_basisshopuser_get')")
    public R getById(@PathVariable("id") Integer id) {
        return super.get(id);
    }

    /**
     * 新增商铺会员关联表
     * @param basisShopUser 商铺会员关联表
     * @return R
     */
    @ApiOperation(value = "新增商铺会员关联表", notes = "新增商铺会员关联表")
    @PostMapping
    @SysLog("新增商铺会员关联表")
    @PreAuthorize("@pms.hasPermission('basisShopUser_basisshopuser_add')")
    public R save(@Validated @RequestBody BasisShopUser basisShopUser) {
        return super.update(basisShopUser, SecurityUtils.getUser().getId());
    }

    /**
     * 修改商铺会员关联表
     * @param basisShopUser 商铺会员关联表
     * @return R
     */
    @ApiOperation(value = "修改商铺会员关联表", notes = "修改商铺会员关联表")
    @PutMapping
    @SysLog("修改商铺会员关联表")
    @PreAuthorize("@pms.hasPermission('basisShopUser_basisshopuser_edit')")
    public R updateById(@Validated @RequestBody BasisShopUser basisShopUser) {
        return R.ok(
                basisShopUserService.lambdaUpdate()
                        .eq(BasisShopUser::getId,basisShopUser.getId())
                        .set(BasisShopUser::getDmid,basisShopUser.getDmid())
                        .set(BasisShopUser::getLevelId,basisShopUser.getLevelId())
                        .set(BasisShopUser::getManager,basisShopUser.getManager()==null?0:basisShopUser.getManager())
                        .update()
        );
    }

    /**
     * 通过id删除商铺会员关联表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除商铺会员关联表", notes = "通过id删除商铺会员关联表")
    @DeleteMapping("/{id}")
    @SysLog("通过id删除商铺会员关联表")
    @PreAuthorize("@pms.hasPermission('basisShopUser_basisshopuser_del')")
    public R removeById(@PathVariable Integer id) {
        BasisShopUser basisShopUser=super.service.queryById(id);
        if (basisShopUser ==null){
            return R.failed("ID错误");
        }
        return super.del(basisShopUser, SecurityUtils.getUser().getId());
    }


    @ApiOperation(value = "获取订阅模板", notes = "获取订阅模板")
    @GetMapping("/templat")
    @Inner(value = false)
    public R getTemplatList(){

      /*  String token = null;
        try {
             token=wxOpenService.getWxOpenComponentService().getAuthorizerAccessToken("wx2b92fa7da634089c",true);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }*/
        WxMaSubscribeMessage wxMaSubscribeMessage = new WxMaSubscribeMessage();
        wxMaSubscribeMessage.setToUser("ohkTC4q4qJVfpd0rFH3p8UOjWgJk");
        wxMaSubscribeMessage.setTemplateId("Tn7a32df0OcVO6k9XIxJracdIibhwfhDB45gl_u_Csw");
        wxMaSubscribeMessage.setPage("pages/pin/detail/index?id=29");
        List<WxMaSubscribeMessage.Data> dataList = new ArrayList<>();
        WxMaSubscribeMessage.Data data = new WxMaSubscribeMessage.Data();
        data.setName("thing1");
        data.setValue("111");
        dataList.add(data);
        WxMaSubscribeMessage.Data data1 = new WxMaSubscribeMessage.Data();
        data1.setName("time2");
        data1.setValue("2021年5月5日 20:20");
        dataList.add(data1);
        WxMaSubscribeMessage.Data data2 = new WxMaSubscribeMessage.Data();
        data2.setName("thing3");
        data2.setValue("111");
        dataList.add(data2);
        wxMaSubscribeMessage.setData(dataList);
        try {
            sendSubscribeMsg(wxMaSubscribeMessage);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return R.ok();

    }

    public void sendSubscribeMsg(WxMaSubscribeMessage subscribeMessage) throws WxErrorException {
        String responseContent = this.wxMaService.post("https://api.weixin.qq.com/cgi-bin/message/subscribe/send", subscribeMessage.toJson());
        JsonObject jsonObject = GsonParser.parse(responseContent);
        if (jsonObject.get("errcode").getAsInt() != 0) {
            throw new WxErrorException(WxError.fromJson(responseContent, WxType.MiniApp));
        }
    }

    @ApiOperation(value = "获取用户的opid", notes = "获取用户的opid")
    @GetMapping("/openid")
    @Inner(value = false)
    public R getOpenid(@RequestParam("code") String code){
        final WxMaService wxService = WxMaConfiguration.getMaService(appid, secret);
        WxMaJscode2SessionResult session = null;
        String wxOpenid = null;
        try {
            session = wxService.getUserService().getSessionInfo(code);
          wxOpenid = session.getOpenid();
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
       return R.ok(wxOpenid);
    }

}
