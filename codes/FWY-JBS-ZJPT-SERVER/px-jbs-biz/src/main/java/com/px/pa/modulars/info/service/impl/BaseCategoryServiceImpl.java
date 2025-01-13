
package com.px.pa.modulars.info.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.px.pa.modulars.info.entity.Applets;
import com.px.pa.modulars.info.entity.BaseCategory;
import com.px.pa.modulars.info.mapper.BaseCategoryMapper;
import com.px.pa.modulars.info.service.AppletsService;
import com.px.pa.modulars.info.service.BaseCategoryService;
import com.px.pa.modulars.info.service.ProductInfoService;
import com.px.pa.modulars.ser.service.WxPxOpenService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.api.WxOpenMaService;
import me.chanjar.weixin.open.bean.ma.WxFastMaCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 小程序类目配置
 *
 *
 *
 */
@Service
public class BaseCategoryServiceImpl extends ServiceImpl<BaseCategoryMapper, BaseCategory> implements BaseCategoryService {

    @Autowired
    private WxPxOpenService openService;
    @Autowired
    private AppletsService appletsService;

    @Override
    public void addCategory(BaseCategory baseDomains) {
        List<String> appids=appletsService.lambdaQuery()
                .eq(Applets::getProductId,baseDomains.getProductId())
                .list()
                .stream().map(Applets::getAppid).collect(Collectors.toList());
        for (String appid:appids){
            List<WxFastMaCategory> list=new ArrayList<>();
            WxOpenMaService s=openService.getOpenMaService(appid);
            WxFastMaCategory c=new WxFastMaCategory();
            c.setFirst(Integer.parseInt(baseDomains.getFirstId()));
            c.setSecond(Integer.parseInt(baseDomains.getSecondId()));
            list.add(c);
            try {
                s.getBasicService().addCategory(list);
            } catch (WxErrorException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delCategory(BaseCategory baseDomains) {
        List<String> appids=appletsService.lambdaQuery()
                .eq(Applets::getProductId,baseDomains.getProductId())
                .list()
                .stream().map(Applets::getAppid).collect(Collectors.toList());
        for (String appid:appids){
            WxOpenMaService s=openService.getOpenMaService(appid);
            try {
                s.getBasicService().deleteCategory(Integer.valueOf(baseDomains.getFirstId()),Integer.valueOf(baseDomains.getSecondId()));
            } catch (WxErrorException e) {
                e.printStackTrace();
            }
        }
    }
}
