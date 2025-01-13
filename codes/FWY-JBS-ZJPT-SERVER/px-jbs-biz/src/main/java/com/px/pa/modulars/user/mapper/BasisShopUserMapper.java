

package com.px.pa.modulars.user.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.px.apis.drama.vo.ShopCarVo;
import com.px.pa.modulars.user.entity.BasisShopUser;
import com.px.pa.modulars.user.vo.ShopUserVo;
import org.apache.ibatis.annotations.Mapper;
import com.px.basic.alone.core.base.BaseMapperImpl;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 商铺会员关联表
 *
 * 
 * 
 */
@Mapper
public interface BasisShopUserMapper extends BaseMapperImpl<BasisShopUser> {

    @Select("select a.*,b.name,b.avatar,b.sex,b.phone from basis_shop_user a " +
            "left join basis_users b on a.user_id=b.id " +
            "${ew.customSqlSegment}")
    Page<ShopUserVo> getBasisShopUserPage(Page page, @Param(Constants.WRAPPER) QueryWrapper query);

    Page<ShopUserVo> getUserPage(@Param("page") Page page,@Param("keyWord") String keyWord,@Param("spid") Integer spid);
}

