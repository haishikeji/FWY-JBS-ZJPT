

package com.px.pa.modulars.shop.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.px.pa.modulars.shop.entity.CoreUserDrama;
import com.px.pa.modulars.shop.vo.ShopDramaVo;
import com.px.pa.modulars.user.entity.BasisUsersType;
import org.apache.ibatis.annotations.Mapper;
import com.px.basic.alone.core.base.BaseMapperImpl;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户剧本关联记录
 *
 *
 *
 */
@Mapper
public interface CoreUserDramaMapper extends BaseMapperImpl<CoreUserDrama> {

    @Select("select b.id,b.price,c.name dramaName,c.image,c.male,c.female,c.duration,e.name diffName,b.did,f.name specName from core_user_drama a " +
            "left join core_shop_drama b on a.sdid=b.id " +
            "left join core_drama c on b.did=c.id " +
            "left join core_difficulty e on c.difficulty=e.id " +
            "left join core_spec f on c.spec=f.id " +
            "${ew.customSqlSegment}")
    Page<ShopDramaVo> getMyFollowPage(Page page, @Param(Constants.WRAPPER) QueryWrapper query);

    @Select("select c.tid,count(*) num from core_user_drama a " +
            "left join core_shop_drama b on a.sdid=b.id " +
            "left join core_drama_type c on b.did=c.did " +
            "where a.suid in (select id from basis_shop_user where user_id=#{uid}) and c.tid is not null " +
            "and a.type=0 and a.enable=1 group by c.tid order by count(*) desc")
    List<BasisUsersType> getTypesByFollow(Integer uid);
}
