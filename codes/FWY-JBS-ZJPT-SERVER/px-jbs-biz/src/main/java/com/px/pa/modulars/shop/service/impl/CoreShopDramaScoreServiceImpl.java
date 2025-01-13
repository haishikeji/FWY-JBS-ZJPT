package com.px.pa.modulars.shop.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.common.core.constant.Constants;
import com.px.apis.user.param.ScoreParam;
import com.px.pa.modulars.dm.entity.CoreDm;
import com.px.pa.modulars.dm.service.CoreDmService;
import com.px.pa.modulars.shop.entity.CoreShopDrama;
import com.px.pa.modulars.shop.entity.CoreShopDramaScore;
import com.px.pa.modulars.shop.mapper.CoreShopDramaScoreMapper;
import com.px.pa.modulars.shop.service.CoreShopDramaScoreService;
import com.px.basic.alone.core.base.BaseServiceImpl;
import com.px.pa.modulars.shop.service.CoreShopDramaService;
import com.px.pa.modulars.shop.vo.CoreShopDramaScoreVo;
import com.px.pa.modulars.shop.vo.ScoreVo;
import com.px.pa.modulars.shop.vo.ShopDramaScoreVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 剧本评分记录
 *
 * 
 * 
 */
@Service
public class CoreShopDramaScoreServiceImpl extends BaseServiceImpl<CoreShopDramaScore,CoreShopDramaScoreMapper> implements CoreShopDramaScoreService {

    @Autowired
    private CoreDmService dmService;
    @Autowired
    private CoreShopDramaService dramaService;

    @Override
    public Page<ShopDramaScoreVo> getCoreShopDramaScorePage(Page page, ShopDramaScoreVo coreShopDramaScore) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like(StrUtil.isNotEmpty(coreShopDramaScore.getDramaName()), "c.name", coreShopDramaScore.getDramaName());
        queryWrapper.like(StrUtil.isNotEmpty(coreShopDramaScore.getUserName()), "f.name", coreShopDramaScore.getUserName());
        queryWrapper.eq(coreShopDramaScore.getScore()!=null,"a.score",coreShopDramaScore.getScore());
        queryWrapper.eq(coreShopDramaScore.getDmid()!=null,"a.dmid",coreShopDramaScore.getDmid());
        queryWrapper.eq(coreShopDramaScore.getSid()!=null,"a.sid",coreShopDramaScore.getSid());
        queryWrapper.orderByDesc("a.id");
        return super.baseMapper.getCoreShopDramaScorePage(page,queryWrapper);
    }

    @Override
    public boolean updateScore(ScoreParam param) {
        int c=super.lambdaQuery()
                .eq(CoreShopDramaScore::getEnable, Constants.ENABLE_TRUE)
                .eq(CoreShopDramaScore::getUid,param.getUid())
                .eq(CoreShopDramaScore::getSdid,param.getId())
                .eq(CoreShopDramaScore::getDmid,param.getDmid())
                .count();
        if(c==0){
            CoreShopDramaScore score=new CoreShopDramaScore();
            score.setDmid(param.getDmid());
            score.setSdid(param.getId());
            score.setUid(param.getUid());
            score.setScore(param.getScore());
            score.setEnable(Constants.ENABLE_TRUE);
            score.setCreateTime(LocalDateTime.now());
            score.setSid(dmService.lambdaQuery().eq(CoreDm::getId,param.getDmid()).one().getSid());
            super.save(score);

            updateDramaScore(param.getId());
            return true;
        }
        return false;
    }

    @Override
    public void updateDramaScore(Integer id){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("enable",Constants.ENABLE_TRUE);
        queryWrapper.eq("sdid",id);
        BigDecimal avg=super.baseMapper.getAvgScore(queryWrapper).multiply(new BigDecimal(2));
        dramaService.lambdaUpdate()
                .eq(CoreShopDrama::getId,id)
                .set(CoreShopDrama::getScore,avg)
                .update();
    }

    @Override
    public List<ScoreVo> getScoreList(Integer id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("enable",Constants.ENABLE_TRUE);
        queryWrapper.eq("sdid", id);
        queryWrapper.groupBy("score");
        return super.baseMapper.getScoreList(queryWrapper);
    }

    @Override
    public List<CoreShopDramaScoreVo> getScore(Integer sdid) {
        return super.baseMapper.getScore(sdid);
    }

    @Override
    public Integer avg(Integer sid, Integer sdid) {
        return super.baseMapper.avg(sid,sdid);
    }
}
