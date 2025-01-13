

package com.px.pa.modulars.user.mapper;

import com.px.apis.user.vo.UserTimeVo;
import com.px.pa.modulars.user.entity.BasisShopUserTime;
import org.apache.ibatis.annotations.Mapper;
import com.px.basic.alone.core.base.BaseMapperImpl;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户空闲时间
 *
 *
 *
 */
@Mapper
public interface BasisShopUserTimeMapper extends BaseMapperImpl<BasisShopUserTime> {

    @Select("select day,group_concat(type) type from basis_shop_user_time where uid=#{uid} group by day")
    List<UserTimeVo> getUserTimes(Integer uid);
}
