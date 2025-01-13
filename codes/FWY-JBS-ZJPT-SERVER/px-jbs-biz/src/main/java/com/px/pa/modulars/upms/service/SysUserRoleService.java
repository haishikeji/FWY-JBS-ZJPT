

package com.px.pa.modulars.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.px.pa.modulars.upms.entity.SysUserRole;

import java.util.List;

/**
 * <p>
 * 用户角色表 服务类
 * </p>
 *
 *
 * 
 */
public interface SysUserRoleService extends IService<SysUserRole> {

	/**
	 * 根据用户Id删除该用户的角色关系
	 * @param userId 用户ID
	 * @return boolean
	 *
	 *
	 */
	Boolean removeRoleByUserId(Integer userId);

	public List<SysUserRole> getList();
}
