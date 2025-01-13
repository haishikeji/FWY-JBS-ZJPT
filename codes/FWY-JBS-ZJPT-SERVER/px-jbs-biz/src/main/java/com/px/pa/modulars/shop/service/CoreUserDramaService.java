package com.px.pa.modulars.shop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.px.apis.user.param.UserDramaParam;
import com.px.basic.alone.core.base.BaseService;
import com.px.pa.modulars.shop.entity.CoreCar;
import com.px.pa.modulars.shop.entity.CoreUserDrama;
import com.px.pa.modulars.shop.vo.ShopDramaVo;
import com.px.pa.modulars.user.entity.BasisUsersType;

import java.util.List;

/**
 * 用户剧本关联记录
 *
 *
 *
 */
public interface CoreUserDramaService extends IService<CoreUserDrama>, BaseService<CoreUserDrama> {

    boolean saveUserDrama(UserDramaParam param);
    Page<ShopDramaVo> getMyFollowPage(UserDramaParam param);

    Integer getCount(Integer id,Integer type,Integer uid);

    /**
     * 查出剧本想玩的人
     * @param sdid 剧本id
     * @param carId  组局id
     *
     */
    List<Integer> getUserList(Integer sdid,Integer carId);


    /**
     * 创建开局  通知想玩的人
     */
    void noteWantPaly(List<Integer> userList, CoreCar coreCar);

    /**
     *
     * 解析订阅消息信息
     */
     String getTemplate(String code,String appId);

     List<BasisUsersType> getTypesByFollow(Integer uid);
}
