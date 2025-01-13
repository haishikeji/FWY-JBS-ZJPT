package com.px.pa.modulars.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.common.core.constant.Constants;
import com.pig4cloud.pig.common.core.util.UUIDUtil;
import com.px.apis.constant.CarStatusEnum;
import com.px.apis.constant.OrderTypeEnum;
import com.px.apis.constant.PayStatusEnum;
import com.px.apis.constant.PayTypeEnum;
import com.px.pa.modulars.shop.entity.BasisOrder;
import com.px.pa.modulars.shop.entity.CoreCar;
import com.px.pa.modulars.shop.entity.CoreCarUser;
import com.px.pa.modulars.shop.entity.CoreShop;
import com.px.pa.modulars.shop.mapper.CoreCarUserMapper;
import com.px.pa.modulars.shop.service.BasisOrderService;
import com.px.pa.modulars.shop.service.CoreCarService;
import com.px.pa.modulars.shop.service.CoreCarUserService;
import com.px.basic.alone.core.base.BaseServiceImpl;
import com.px.pa.modulars.shop.vo.CarUserVo;
import com.px.pa.modulars.user.entity.BasisShopUser;
import com.px.pa.modulars.user.service.BasisShopUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 组局详情
 *
 *
 *
 */
@Service
public class CoreCarUserServiceImpl extends BaseServiceImpl<CoreCarUser,CoreCarUserMapper> implements CoreCarUserService {

    @Autowired
    public CoreCarService carService;
    @Autowired
    public BasisShopUserService basisShopUserService;
    @Autowired
    public BasisOrderService basisOrderService;

    @Override
    public List<CarUserVo> getCoreCarUserList(Integer cid,Integer state) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("a.enable", Constants.ENABLE_TRUE);
        queryWrapper.eq("a.cid",cid);
        queryWrapper.eq(state!=null,"a.state",state);
        queryWrapper.orderByDesc("a.id");
        return super.baseMapper.getCoreCarUserList(queryWrapper);
    }

    @Override
    public String balancePay(Integer id, Integer uid) {
        BasisShopUser basisShopUser = basisShopUserService.getById(uid);
        BigDecimal price = carService.getById(id).getPrice();
        if (basisShopUser.getWallet().compareTo(price) == -1) {
            return "余额不足无法付款";
        }
        String ordersn = UUIDUtil.getOrderIdByUUId().toString();
        CoreCarUser cu = super.lambdaQuery()
                .eq(CoreCarUser::getCid, id)
                .eq(CoreCarUser::getUid, uid)
                .eq(CoreCarUser::getState, 0)
                .eq(CoreCarUser::getEnable, Constants.ENABLE_TRUE)
                .one();
        if (cu != null) {
            cu.setRemark(ordersn);
            cu.setState(1);
            super.update(cu);
            //记录订单记录
            CoreCar coreCar = carService.getById(cu.getCid());
            BasisOrder basisOrder = new BasisOrder();
            basisOrder.setCreateTime(LocalDateTime.now());
            basisOrder.setUpdateTime(LocalDateTime.now());
            basisOrder.setShopUser(cu.getUid());
            basisOrder.setGoodId(cu.getCid());
            if (coreCar != null) {
                basisOrder.setPricing(coreCar.getPrice());
                basisOrder.setRealPay(coreCar.getPrice());
            }
            basisOrder.setStatus(PayStatusEnum.PAY_STATUS_ENUM_TWO.getValue());
            basisOrder.setPayType(PayTypeEnum.PAY_TYPE_ENUM_ONE.getValue());
            basisOrder.setOrderType(OrderTypeEnum.ORDER_TYPE_ENUM_ONE.getValue());
            basisOrder.setNumber(ordersn);
            CoreCar car = carService.getById(cu.getCid());
            if (car != null) {
                basisOrder.setShopId(car.getSid());
            }
            basisOrder.setAvailableBalance(basisShopUser.getWallet().subtract(price));
            basisOrderService.save(basisOrder);
            //查询组局付款状态  改变组局状态
            int count = super.lambdaQuery()
                    .eq(CoreCarUser::getCid, cu.getCid())
                    .eq(CoreCarUser::getState, 0)
                    .eq(CoreCarUser::getEnable, Constants.ENABLE_TRUE)
                    .count();
            if (count == 0) {
                carService.lambdaUpdate()
                        .eq(CoreCar::getId, cu.getCid())
                        .set(CoreCar::getState, CarStatusEnum.OVER.getValue())
                        .set(CoreCar::getUpdateTime, LocalDateTime.now())
                        .update();
            }
            //账户余额
            basisShopUser.setWallet(basisShopUser.getWallet().subtract(price));
            basisShopUserService.updateById(basisShopUser);
            return "";
        } else {
            return "未找到待支付订单";
        }
    }
}
