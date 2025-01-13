

package com.px.pa.modulars.upms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.px.pa.modulars.upms.entity.SysUserRole;
import com.px.pa.modulars.upms.mapper.SysUserRoleMapper;
import com.px.pa.modulars.upms.service.SysUserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 *
 * 
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    /**
     * 根据用户Id删除该用户的角色关系
     *
     * @param userId 用户ID
     * @return boolean
     *
     * 
     */
    @Override
    public Boolean removeRoleByUserId(Integer userId) {
        return baseMapper.deleteByUserId(userId);
    }

    @Override
    public List<SysUserRole> getList() {
        return super.list();
    }

}
