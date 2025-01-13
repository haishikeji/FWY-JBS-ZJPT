package com.px.pa.modulars.upms.service.impl;

import com.pig4cloud.pig.common.core.constant.Constants;
import com.px.basic.alone.core.base.BaseModel;
import com.px.basic.alone.core.base.BaseServiceImpl;
import com.px.pa.modulars.upms.entity.SysProhibiteWord;
import com.px.pa.modulars.upms.mapper.SysProhibiteWordMapper;
import com.px.pa.modulars.upms.service.SysProhibiteWordService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 关键词过滤
 *
 *
 *
 */
@Service
public class SysProhibiteWordServiceImpl extends BaseServiceImpl<SysProhibiteWord, SysProhibiteWordMapper> implements SysProhibiteWordService {

    @Override
    public List<String> queryByKey(String s) {
        List<SysProhibiteWord> words = super.lambdaQuery().eq(SysProhibiteWord::getSkey, s).eq(BaseModel::getEnable, Constants.ENABLE_TRUE).list();
        List<String> wordStrs = new ArrayList<>();
        for (SysProhibiteWord word : words) {
            wordStrs.add(word.getWord());
        }
        return wordStrs;
    }
}
