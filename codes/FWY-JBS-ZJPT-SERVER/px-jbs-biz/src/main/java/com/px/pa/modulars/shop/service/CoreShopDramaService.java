package com.px.pa.modulars.shop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.px.apis.drama.param.DramaParam;
import com.px.apis.shop.param.ShopDramaParam;
import com.px.basic.alone.core.base.BaseService;
import com.px.pa.modulars.drama.entity.CoreDrama;
import com.px.pa.modulars.drama.vo.DramaVo;
import com.px.pa.modulars.shop.entity.CoreShopDrama;
import com.px.pa.modulars.shop.vo.ShopDramaVo;

import java.util.List;

/**
 * 店铺剧本列表
 *
 *
 *
 */
public interface CoreShopDramaService extends IService<CoreShopDrama>, BaseService<CoreShopDrama> {

    Page<ShopDramaVo> getShopDramaPage(Page page, ShopDramaVo coreShopDrama);
    List<CoreDrama> getShopDramaList(Integer sid);
    List<DramaVo> getShopDramaUnAdd(Integer sid, String key);
    Page<ShopDramaVo> getShopDramaPage(DramaParam drama);
    ShopDramaVo getDramaDetail(Integer id,Integer uid);
    Page<ShopDramaVo> getShopRelated(Integer id,Integer page);
    List<ShopDramaVo> getRecommentList(Integer sid);
    List<Integer> getTypeIds(Integer id);
    Page<DramaVo> getShopDramas(DramaParam param);
    boolean addDramas(ShopDramaParam param);
    List<ShopDramaVo> getShopDramaByType(Page page,Integer sid,List<Integer> tid,Integer sdid);
}
