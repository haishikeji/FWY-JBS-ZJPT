package com.px.pa.modulars.dm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.BeanUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.constant.Constants;
import com.pig4cloud.pig.common.core.support.page.PageUtil;
import com.px.apis.shop.param.DmParam;
import com.px.apis.shop.vo.DmVo;
import com.px.pa.modulars.dm.entity.CoreDm;
import com.px.pa.modulars.dm.mapper.CoreDmMapper;
import com.px.pa.modulars.dm.service.CoreDmService;
import com.px.basic.alone.core.base.BaseServiceImpl;
import com.px.pa.modulars.drama.service.CoreDramaTypeService;
import com.px.pa.modulars.drama.service.CoreTypeService;
import com.px.pa.modulars.shop.service.CoreDmDramaService;
import com.px.pa.modulars.shop.service.CoreDmScoreService;
import com.px.pa.modulars.shop.service.CoreShopDramaScoreService;
import com.px.pa.modulars.shop.service.CoreShopDramaService;
import com.px.pa.modulars.shop.vo.ShopDramaVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * 剧本主持人
 *
 *
 *
 */
@Service
public class CoreDmServiceImpl extends BaseServiceImpl<CoreDm,CoreDmMapper> implements CoreDmService {

    @Autowired
    private CoreDmDramaService dmDramaService;
    @Autowired
    private CoreDramaTypeService typeService;
    @Autowired
    private CoreDmScoreService coreDmScoreService;
    @Autowired
    private CoreShopDramaScoreService coreShopDramaScoreService;
    @Autowired
    protected HttpServletRequest request;

    @Override
    public Page<CoreDm> getDmPage(DmParam dm) {
        Page p= PageUtil.getPage(BeanUtil.beanToMap(dm));
        QueryWrapper<CoreDm> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.sid", dm.getSid());
        queryWrapper.eq("a.enable",Constants.ENABLE_TRUE);
        queryWrapper.eq(dm.getSex()!=null,"a.sex",dm.getSex());
        if(dm.getNumber()==null&&dm.getRate()==null){
            queryWrapper.orderByDesc("a.hot","a.id");
        }
        if(dm.getNumber()!=null){
            if(dm.getNumber()==1){
                queryWrapper.orderByDesc("a.num+a.invented");
            }else if(dm.getNumber()==-1){
                queryWrapper.orderByAsc("a.num+a.invented");
            }
        }
        if(dm.getRate()!=null){
            if(dm.getRate()==1){
                queryWrapper.orderByDesc("a.score");
            }else if(dm.getRate()==-1){
                queryWrapper.orderByAsc("a.score");
            }
        }
        return super.baseMapper.getDmPage(p,queryWrapper);
    }

    @Override
    public DmVo getDmDetail(Integer id) {
        CoreDm dm=this.getById(id);
        DmVo vo=new DmVo();
        BeanUtil.copyProperties(dm,vo, true);
        QueryWrapper<ShopDramaVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.dmid",id);
        queryWrapper.eq("a.enable",Constants.ENABLE_TRUE);
        queryWrapper.orderByDesc("b.score");
        List<ShopDramaVo> list=dmDramaService.getDmDramaList(queryWrapper);
        list.forEach(record->{
            record.setTypeNames(typeService.getTypeNames(record.getDid()));
            Integer avg1 =   coreShopDramaScoreService.avg(getShopId(),record.getId());
            record.setScore(new BigDecimal(avg1));
        });
        vo.setDramas(list);
        Integer avg =   coreDmScoreService.average(dm.getSid(),id);
        vo.setScore(new BigDecimal(avg.intValue()));
        return vo;
    }

    @Override
    public int getMaxNum(Integer sid) {
        return super.baseMapper.getMaxNum(sid);
    }

    public Integer getShopId() {
        String shopId = this.request.getHeader("SHOPID");
        if(StrUtil.isNotEmpty(shopId)){
            return Integer.valueOf(shopId);
        }else{
            return 0;
        }
    }

}
