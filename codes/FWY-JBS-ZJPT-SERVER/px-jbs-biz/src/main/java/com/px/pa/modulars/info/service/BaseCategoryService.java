

package com.px.pa.modulars.info.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.px.pa.modulars.info.entity.BaseCategory;
import com.px.pa.modulars.info.entity.BaseDomains;

/**
 * 小程序类目配置
 *
 *
 * 
 */
public interface BaseCategoryService extends IService<BaseCategory> {
    void addCategory(BaseCategory baseDomains);
    void delCategory(BaseCategory baseDomains);
}
