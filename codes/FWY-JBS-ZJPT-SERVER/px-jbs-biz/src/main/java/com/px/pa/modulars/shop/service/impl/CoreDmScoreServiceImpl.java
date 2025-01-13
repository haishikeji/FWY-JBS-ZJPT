package com.px.pa.modulars.shop.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.common.core.constant.Constants;
import com.px.apis.user.param.ScoreParam;
import com.px.pa.modulars.dm.entity.CoreDm;
import com.px.pa.modulars.dm.service.CoreDmService;
import com.px.pa.modulars.shop.entity.CoreDmScore;
import com.px.pa.modulars.shop.entity.CoreShopDrama;
import com.px.pa.modulars.shop.entity.CoreShopDramaScore;
import com.px.pa.modulars.shop.mapper.CoreDmScoreMapper;
import com.px.pa.modulars.shop.service.CoreDmScoreService;
import com.px.basic.alone.core.base.BaseServiceImpl;
import com.px.pa.modulars.shop.vo.DmScoreVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DM评分记录
 *
 *
 *
 */
@Service
public class CoreDmScoreServiceImpl extends BaseServiceImpl<CoreDmScore,CoreDmScoreMapper> implements CoreDmScoreService {

    @Autowired
    private CoreDmService dmService;

    @Override
    public Page<DmScoreVo> getCoreDmScorePage(Page page, DmScoreVo coreDmScore) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("a.enable", Constants.ENABLE_TRUE);
        queryWrapper.eq("a.sid",coreDmScore.getSid());
        queryWrapper.like(StrUtil.isNotEmpty(coreDmScore.getUserName()),"b.name",coreDmScore.getUserName());
        queryWrapper.eq(coreDmScore.getDmid()!=null,"a.dmid",coreDmScore.getDmid());
        queryWrapper.eq(coreDmScore.getScore()!=null,"a.score",coreDmScore.getScore());
        queryWrapper.orderByDesc("a.id");
        return super.baseMapper.getCoreDmScorePage(page,queryWrapper);
    }

    @Override
    public boolean updateScore(ScoreParam param) {
        int c=super.lambdaQuery()
                .eq(CoreDmScore::getEnable, Constants.ENABLE_TRUE)
                .eq(CoreDmScore::getUid,param.getUid())
                .eq(CoreDmScore::getDmid,param.getDmid())
                .count();
        if(c==0){
            CoreDmScore score=new CoreDmScore();
            score.setDmid(param.getDmid());
            score.setUid(param.getUid());
            score.setScore(param.getScore());
            score.setEnable(Constants.ENABLE_TRUE);
            score.setCreateTime(LocalDateTime.now());
            score.setSid(dmService.lambdaQuery().eq(CoreDm::getId,param.getDmid()).one().getSid());
            super.save(score);

            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("enable",Constants.ENABLE_TRUE);
            queryWrapper.eq("dmid",param.getId());
            BigDecimal avg=super.baseMapper.getAvgScore(queryWrapper).multiply(new BigDecimal(2));
            dmService.lambdaUpdate()
                    .eq(CoreDm::getId,param.getId())
                    .set(CoreDm::getScore,avg)
                    .update();
            return true;
        }
        return false;
    }

    @Override
    public Integer average(Integer sid, Integer dmid) {
        return super.baseMapper.average(sid,dmid);
    }
}
