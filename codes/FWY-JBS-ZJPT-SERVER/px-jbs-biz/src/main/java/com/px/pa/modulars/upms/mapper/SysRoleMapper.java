

package com.px.pa.modulars.upms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.px.pa.modulars.upms.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 *
 * 
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 通过用户ID，查询角色信息
     *
     * @param userId
     * @return
     */
    List<SysRole> listRolesByUserId(Integer userId);


    List<SysRole> listByAdmin();
}
