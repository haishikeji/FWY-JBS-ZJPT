package com.px.pa.modulars.drama.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.px.basic.alone.core.base.BaseService;
import com.px.pa.modulars.drama.entity.CoreDrama;
import com.px.pa.modulars.drama.vo.DramaVo;

/**
 * 剧本信息
 *
 *
 *
 */
public interface CoreDramaService extends IService<CoreDrama>, BaseService<CoreDrama> {

    Page<DramaVo> getDramaPage(Page page,DramaVo coreDrama);
    Integer save(DramaVo drama,Integer uid);
}
