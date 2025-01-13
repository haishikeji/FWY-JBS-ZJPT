package com.px.pa.modulars.shop.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.common.core.constant.Constants;
import com.pig4cloud.pig.common.core.support.page.PageUtil;
import com.px.apis.constant.CarStatusEnum;
import com.px.apis.constant.ShopDramaPeopleEnum;
import com.px.apis.constant.UserDramaEnum;
import com.px.apis.drama.vo.CarDetailVo;
import com.px.apis.drama.vo.ShopCarVo;
import com.px.apis.shop.param.ShopCarParam;
import com.px.apis.user.param.CarParam;
import com.px.pa.modulars.dm.service.CoreDmService;
import com.px.pa.modulars.drama.entity.CoreDramaType;
import com.px.pa.modulars.drama.service.CoreDramaRoleService;
import com.px.pa.modulars.drama.service.CoreDramaTypeService;
import com.px.pa.modulars.shop.entity.CoreCar;
import com.px.pa.modulars.shop.entity.CoreCarUser;
import com.px.pa.modulars.shop.mapper.CoreCarMapper;
import com.px.pa.modulars.shop.service.CoreCarService;
import com.px.basic.alone.core.base.BaseServiceImpl;
import com.px.pa.modulars.shop.service.CoreCarUserService;
import com.px.pa.modulars.shop.service.CoreShopDramaScoreService;
import com.px.pa.modulars.shop.service.CoreUserDramaService;
import com.px.pa.modulars.shop.vo.CarUserVo;
import com.px.pa.modulars.shop.vo.CarVo;
import com.px.pa.modulars.shop.vo.ShopDramaVo;
import com.px.pa.modulars.user.entity.BasisShopUser;
import com.px.pa.modulars.user.entity.BasisUsers;
import com.px.pa.modulars.user.entity.BasisUsersType;
import com.px.pa.modulars.user.service.BasisShopUserService;
import com.px.pa.modulars.user.service.BasisUsersService;
import com.px.pa.modulars.user.service.BasisUsersTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 组局信息
 *
 * 
 * 
 */
@Service
public class CoreCarServiceImpl extends BaseServiceImpl<CoreCar,CoreCarMapper> implements CoreCarService {

    @Autowired
    public CoreCarUserService carUserService;
    @Autowired
    public CoreDramaTypeService typeService;
    @Autowired
    public BasisShopUserService shopUserService;
    @Autowired
    public CoreUserDramaService userDramaService;
    @Autowired
    public CoreDramaRoleService roleService;
    @Autowired
    public CoreShopDramaScoreService scoreService;
    @Autowired
    public CoreDmService dmService;
    @Autowired
    public BasisUsersService usersService;
    @Autowired
    public BasisUsersTypeService usersTypeService;
    @Autowired
    private CoreShopDramaScoreService coreShopDramaScoreService;


    @Override
    public Page<CarVo> getCoreCarPage(Page page, CarVo coreCar) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("a.enable", Constants.ENABLE_TRUE);
        queryWrapper.eq("a.sid",coreCar.getSid());
        queryWrapper.eq(coreCar.getDmid()!=null,"a.dmid",coreCar.getDmid());
        queryWrapper.eq(coreCar.getSdid()!=null,"a.sdid",coreCar.getSdid());
//        queryWrapper.like(StrUtil.isNotEmpty(coreCar.getDramaName()), "c.name", coreCar.getDramaName());
//        queryWrapper.like(StrUtil.isNotEmpty(coreCar.getDmName()), "d.name", coreCar.getDmName());
        queryWrapper.like(StrUtil.isNotEmpty(coreCar.getUserName()), "f.name", coreCar.getUserName());
        queryWrapper.eq(coreCar.getState()!=null,"a.state",coreCar.getState());
        queryWrapper.eq(coreCar.getType()!=null,"a.type",coreCar.getType());
        queryWrapper.orderByDesc("a.id");
        return super.baseMapper.getCoreCarPage(page,queryWrapper);
    }

    @Override
    public Page<ShopCarVo> getShopCarPage(ShopCarParam param) {
        Page p= PageUtil.getPage(BeanUtil.beanToMap(param));
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("a.enable",Constants.ENABLE_TRUE);
        queryWrapper.eq("a.sid",param.getSid());
        queryWrapper.eq(param.getSpec()!=null, "c.spec", param.getSpec());
        queryWrapper.eq(param.getDiff()!=null, "c.difficulty", param.getDiff());
        queryWrapper.eq(StrUtil.isNotEmpty(param.getType()), "a.type", param.getType());
        queryWrapper.in("a.state",CarStatusEnum.WAITING.getValue(),CarStatusEnum.FINISH.getValue());
        queryWrapper.like(StrUtil.isNotEmpty(param.getName()),"c.name",param.getName());
        if(param.getNumber()!=null){
            if(param.getNumber().equals(ShopDramaPeopleEnum.MIN.getValue())){
                queryWrapper.le("a.num",ShopDramaPeopleEnum.MIN.getValue());
            }else if(param.getNumber().equals(ShopDramaPeopleEnum.MAX.getValue())){
                queryWrapper.ge("a.num",ShopDramaPeopleEnum.MIN.getValue());
            }else{
                queryWrapper.eq("a.num",param.getNumber());
            }
        }

        filterTypes(param.getTypes(),queryWrapper);

        if(param.getLast()!=null){
            queryWrapper.eq("a.state",CarStatusEnum.WAITING.getValue());
            queryWrapper.having("a.num-count(distinct b.id)={0}",param.getLast());
        }
        queryWrapper.groupBy("a.id");
        queryWrapper.orderByAsc("a.state");
        queryWrapper.orderByDesc("a.create_time");
        Page<ShopCarVo> result=super.baseMapper.getShopCarPage(p,queryWrapper);
        result.getRecords().forEach(record->{
            record.setUsers(carUserService.getCoreCarUserList(record.getId(),null));
            record.setTypeNames(typeService.getTypeNames(record.getDid()));
            record.setCurr(carUserService.getCoreCarUserList(record.getId(),null).size());
        });
        return result;
    }
    public void filterTypes(List<Integer> ids, QueryWrapper<ShopDramaVo> queryWrapper){
        if(ids!=null&&ids.size()>0){
            queryWrapper.in("tc.tid", ids);
            queryWrapper.having("count(tc.id)>={0}", ids.size());
        }
    }

    @Override
    public Page<ShopCarVo> getMyCarsPage(CarParam car) {
        Page p= PageUtil.getPage(BeanUtil.beanToMap(car));
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("a.enable",Constants.ENABLE_TRUE);
        queryWrapper.eq("b.enable",Constants.ENABLE_TRUE);
        queryWrapper.eq("a.uid",car.getUid());
        queryWrapper.eq(car.getState()!=null,"b.state",car.getState());
        queryWrapper.groupBy("a.cid");
        queryWrapper.orderByAsc("b.state");
        queryWrapper.orderByDesc("b.create_time");
        return super.baseMapper.getMyCarsPage(p,queryWrapper);
    }

    @Override
    public Page<ShopCarVo> getDmCars(CarParam car) {
        Page p= PageUtil.getPage(BeanUtil.beanToMap(car));
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("a.enable",Constants.ENABLE_TRUE);
        queryWrapper.eq(car.getState()!=null,"a.state",car.getState());
        queryWrapper.eq("a.dmid", shopUserService.lambdaQuery().eq(BasisShopUser::getId,car.getUid()).one().getDmid());
        queryWrapper.groupBy("a.id");
        queryWrapper.orderByAsc("a.state");
        queryWrapper.orderByDesc("a.create_time");
        return super.baseMapper.getDmCars(p,queryWrapper);
    }

    @Override
    public ShopCarVo getCarDetail(Integer id) {
        ShopCarVo car=super.baseMapper.getCarDetail(id);
        //剧本评分
        Integer score = coreShopDramaScoreService.avg(car.getSid(),car.getSdid());
        car.setScore(new BigDecimal(score));
        car.setTypeNames(typeService.getTypeNames(car.getDid()));
        car.setUsers(carUserService.getCoreCarUserList(id,null));
        car.setCurr(carUserService.getCoreCarUserList(id,null).size());
        car.setWants(userDramaService.getCount(car.getSdid(), UserDramaEnum.WANTS.getValue(), null));
        car.setPlayed(userDramaService.getCount(car.getSdid(), UserDramaEnum.PLAYED.getValue(), null));
        car.setRoles(roleService.getRoles(car.getDid()));
        car.setScores(scoreService.getScoreList(car.getSdid()));
        car.setDm(dmService.getById(car.getDmid()));
        car.setUser(usersService.getUserBySuid(car.getUid()));
        //结束时间计算
        Date enterEtime = Date.from(car.getPlayTime().atZone(ZoneId.systemDefault()).toInstant());
        Date date = new Date();
        if(enterEtime.compareTo(date) > 0){

            car.setCurrentTimmeMillis(enterEtime.getTime()/1000 - date.getTime()/1000);
        }else {
            car.setCurrentTimmeMillis(0L);
        }
        return car;
    }
    @Override
    public String joinCars(Integer id, Integer uid) {
        CoreCar c=super.getById(id);
        if(c!=null&&c.getState().equals(CarStatusEnum.WAITING.getValue())){
            int count= carUserService.lambdaQuery()
                    .eq(CoreCarUser::getEnable, Constants.ENABLE_TRUE)
                    .eq(CoreCarUser::getCid, id)
                    .eq(CoreCarUser::getUid, uid)
                    .count();
            if(count==0){
                count=carUserService.lambdaQuery()
                        .eq(CoreCarUser::getEnable, Constants.ENABLE_TRUE)
                        .eq(CoreCarUser::getCid, id)
                        .count();
                if((count+1)<=c.getNum()){
                    CoreCarUser cu=new CoreCarUser();
                    cu.setCid(id);
                    cu.setUid(uid);
                    cu.setEnable(Constants.ENABLE_TRUE);
                    cu.setCreateTime(LocalDateTime.now());
                    carUserService.save(cu);
                    return "";
                }
                return "该组局人数已满";
            }
            return "请勿重复加入组局";
        }
        return "未找到可以加入的组局";
    }

    @Override
    public Integer countDm(Integer id) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("dmid", id);
        queryWrapper.eq("enable", Constants.ENABLE_TRUE);
        queryWrapper.ne("state", CarStatusEnum.CLEAR.getValue());
        queryWrapper.apply("DATE_FORMAT(play_time,'%Y-%m')={0}", DateUtil.format(new Date(), "yyyy-MM"));
        return super.baseMapper.countDm(queryWrapper);
    }

    @Override
    public Integer getcorecarSuccess(Integer spid) {
        return super.mapper.getcorecarSuccess(spid);
    }

    @Override
    public void addTag(Integer cid) {
        List<CarUserVo> cu=carUserService.getCoreCarUserList(cid,Constants.ENABLE_TRUE);
        List<CoreDramaType> dt=typeService.getTypeByCar(cid);
        for (CarUserVo u:cu){
            if(u.getRuid()!=null){
                addType(u.getRuid(),dt);
            }
        }
    }
    public void addType(Integer uid,List<CoreDramaType> dt){
        for (CoreDramaType t:dt){
            if(t!=null&&t.getTid()!=null){
                BasisUsersType ut=usersTypeService.lambdaQuery()
                        .eq(BasisUsersType::getUid,uid)
                        .eq(BasisUsersType::getTid,t.getTid())
                        .one();
                if(ut==null){
                    ut=new BasisUsersType();
                    ut.setUid(uid);
                    ut.setTid(t.getTid());
                    ut.setEnable(Constants.ENABLE_TRUE);
                    ut.setNum(1);
                    usersTypeService.save(ut);
                }else{
                    ut.setNum(ut.getNum()+1);
                    usersTypeService.updateById(ut);
                }
            }
        }
    }

    @Override
    public void addTagByDrama(Integer sdid,Integer suid) {
        BasisUsers bu=usersService.getUserBySuid(suid);
        List<CoreDramaType> dt=typeService.getTypeByShopDrama(sdid);
        addType(bu.getId(),dt);
    }
}
