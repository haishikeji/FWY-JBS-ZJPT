package com.px.pa.modulars.dm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.px.apis.shop.param.DmParam;
import com.px.apis.shop.vo.DmVo;
import com.px.basic.alone.core.base.BaseService;
import com.px.pa.modulars.dm.entity.CoreDm;

import java.util.List;

/**
 * 剧本主持人
 *
 *
 *
 */
public interface CoreDmService extends IService<CoreDm>, BaseService<CoreDm> {

    Page<CoreDm> getDmPage(DmParam dm);
    DmVo getDmDetail(Integer id);
    int getMaxNum(Integer sid);
}
