

package com.px.pa.modulars.shop.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.px.apis.drama.vo.ShopCarVo;
import com.px.pa.modulars.shop.entity.CoreCar;
import com.px.pa.modulars.shop.vo.CarVo;
import com.px.pa.modulars.shop.vo.DmDramaVo;
import org.apache.ibatis.annotations.Mapper;
import com.px.basic.alone.core.base.BaseMapperImpl;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 组局信息
 *
 * 
 * 
 */
@Mapper
public interface CoreCarMapper extends BaseMapperImpl<CoreCar> {

    @Select("select a.*,c.name dramaName,d.name dmName,f.name userName from core_car a " +
            "left join core_shop_drama b on a.sdid=b.id " +
            "left join core_drama c on b.did=c.id " +
            "left join core_dm d on a.dmid=d.id " +
            "left join basis_shop_user e on a.uid=e.id " +
            "left join basis_users f on e.user_id=f.id " +
            "${ew.customSqlSegment}")
    Page<CarVo> getCoreCarPage(Page page, @Param(Constants.WRAPPER) QueryWrapper query);


    @Select("select a.*,b.did,b.score,c.name dramaName,c.image,c.male,c.female,c.detail,c.duration,count(distinct cu.id) curr,d.name diffName,a.play_time playTime from core_car a " +
            "left join core_shop_drama b on a.sdid=b.id " +
            "left join core_car_user cu on a.id=cu.cid " +
            "left join core_drama c on b.did=c.id " +
            "left join core_difficulty d on c.difficulty=d.id " +
            "where a.id=#{id}  group by a.id" )
    ShopCarVo getCarDetail(@Param("id") Integer id);


    @Select("select a.*,c.name dramaName,c.image,c.detail,count(distinct b.id) curr,sd.did,c.duration,s.name specName from core_car a " +
            "left join core_car_user b on a.id=b.cid " +
            "left join core_shop_drama sd on a.sdid=sd.id " +
            "left join core_drama c on sd.did=c.id " +
            "left join core_drama_type tc on c.id=tc.did " +
            "left join core_spec s on c.spec=s.id " +
            "${ew.customSqlSegment}")
    Page<ShopCarVo> getShopCarPage(Page page, @Param(Constants.WRAPPER) QueryWrapper query);

    @Select("select b.id,a.state as payState,b.type,b.state,b.num,b.play_time,b.create_time,b.price,c.name dramaName,c.image,c.detail,count(distinct a.id) curr,sd.did,c.duration from core_car_user a " +
            "left join core_car b on a.cid=b.id " +
            "left join core_shop_drama sd on b.sdid=sd.id " +
            "left join core_drama c on sd.did=c.id " +
            "${ew.customSqlSegment}")
    Page<ShopCarVo> getMyCarsPage(Page page, @Param(Constants.WRAPPER) QueryWrapper query);

    @Select("select a.*,c.name dramaName,c.image,c.detail,count(distinct b.id) curr,sd.did,c.duration,a.id as cid from core_car a " +
            "left join core_car_user b on a.id=b.cid " +
            "left join core_shop_drama sd on a.sdid=sd.id " +
            "left join core_drama c on sd.did=c.id " +
            "${ew.customSqlSegment}")
    Page<ShopCarVo> getDmCars(Page page, @Param(Constants.WRAPPER) QueryWrapper query);

    @Select("select count(*) from core_car a " +
            "${ew.customSqlSegment}" )
    Integer countDm(@Param(Constants.WRAPPER) QueryWrapper query);

    /**
     * 今日组局数
     * @return
     */
    Integer getcorecarSuccess(@Param("spid") Integer spid);
}
