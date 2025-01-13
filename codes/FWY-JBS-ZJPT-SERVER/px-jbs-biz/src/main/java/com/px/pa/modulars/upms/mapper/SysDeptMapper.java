
package com.px.pa.modulars.upms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.px.pa.modulars.upms.entity.SysDept;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 部门管理 Mapper 接口
 * </p>
 *
 * 
 * 
 */
@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {

	/**
	 * 关联dept——relation
	 * @return 数据列表
	 */
	List<SysDept> listDepts();

}
