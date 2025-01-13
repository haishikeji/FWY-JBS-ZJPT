

package com.px.pa.modulars.drama.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.px.pa.modulars.drama.entity.CoreDrama;
import com.px.pa.modulars.drama.vo.DramaVo;
import org.apache.ibatis.annotations.Mapper;
import com.px.basic.alone.core.base.BaseMapperImpl;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 剧本信息
 *
 *
 * 
 */
@Mapper
public interface CoreDramaMapper extends BaseMapperImpl<CoreDrama> {

    @Select("select a.*,GROUP_CONCAT(tid) types from core_drama a " +
            "left join core_drama_type b on a.id=b.did " +
            "${ew.customSqlSegment}")
    Page<DramaVo> getDramaVoPage(Page page, @Param(Constants.WRAPPER) QueryWrapper query);
}
