

package com.px.pa.modulars.shop.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.px.pa.modulars.drama.entity.CoreDrama;
import com.px.pa.modulars.drama.vo.DramaVo;
import com.px.pa.modulars.shop.entity.CoreShopDrama;
import com.px.pa.modulars.shop.vo.ShopDramaVo;
import com.px.pa.modulars.shop.vo.ShopVo;
import org.apache.ibatis.annotations.Mapper;
import com.px.basic.alone.core.base.BaseMapperImpl;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 店铺剧本列表
 *
 *
 *
 */
@Mapper
public interface CoreShopDramaMapper extends BaseMapperImpl<CoreShopDrama> {

    @Select("select a.*,b.spec,f.name specName,b.name dramaName,d.name shopName,e.name diffName,b.image,b.detail,b.male,b.female,b.duration from core_shop_drama a " +
            "left join core_drama b on a.did=b.id " +
            "left join core_drama_type c on b.id=c.did " +
            "left join core_shop d on a.sid=d.id " +
            "left join core_difficulty e on b.difficulty=e.id " +
            "left join core_spec f on b.spec=f.id " +
            "${ew.customSqlSegment}")
    Page<ShopDramaVo> getShopDramaPage(Page page, @Param(Constants.WRAPPER) QueryWrapper query);
    @Select("select a.id,b.name from core_shop_drama a " +
            "left join core_drama b on a.did=b.id " +
            "${ew.customSqlSegment}")
    List<CoreDrama> getShopDramaList(@Param(Constants.WRAPPER) QueryWrapper query);

    @Select("select b.id,b.name,group_concat(d.name) types,e.name specName from core_drama b " +
            "left join core_drama_type c on b.id=c.did " +
            "left join core_type d on c.tid=d.id " +
            "left join core_spec e on b.spec=e.id " +
            "${ew.customSqlSegment}")
    List<DramaVo> getShopDramaUnAdd(@Param(Constants.WRAPPER) QueryWrapper query);

    @Select("select b.id,b.name,e.name specName,b.image from core_drama b " +
            "left join core_drama_type c on b.id=c.did " +
            "left join core_type d on c.tid=d.id " +
            "left join core_spec e on b.spec=e.id " +
            "${ew.customSqlSegment}")
    Page<DramaVo> getShopDramaUnAddPage(Page page,@Param(Constants.WRAPPER) QueryWrapper query);

    @Select("select a.*,b.name dramaName,c.name diffName,b.detail,b.male,b.female,b.duration,b.image from core_shop_drama a " +
            "left join core_drama b on a.did=b.id " +
            "left join core_difficulty c on b.difficulty=c.id where a.id=#{id} ")
    ShopDramaVo getDramaDetail(@Param("id")Integer id);

    @Select("select b.name dramaName,b.image,a.id,GROUP_CONCAT(c.tid) detail from core_shop_drama a " +
            "left join core_drama b on a.did=b.id " +
            "left join core_drama_type c on a.did=c.did " +
            "${ew.customSqlSegment}")
    List<ShopDramaVo> getShopDramaByType(Page page,@Param(Constants.WRAPPER) QueryWrapper query);
}
