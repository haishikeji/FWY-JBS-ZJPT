

package com.px.pa.modulars.dm.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.px.pa.modulars.dm.entity.CoreDm;
import com.px.pa.modulars.shop.vo.CarVo;
import org.apache.ibatis.annotations.Mapper;
import com.px.basic.alone.core.base.BaseMapperImpl;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 剧本主持人
 *
 *
 *
 */
@Mapper
public interface CoreDmMapper extends BaseMapperImpl<CoreDm> {

    @Select("select a.* from core_dm a " +
            "${ew.customSqlSegment}")
    Page<CoreDm> getDmPage(Page page, @Param(Constants.WRAPPER) QueryWrapper query);

    @Select("select max(convert(a.number, unsigned integer)) from core_dm a where a.sid=#{sid}")
    int getMaxNum(Integer sid);
}
