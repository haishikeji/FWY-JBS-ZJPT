

package com.px.pa.modulars.code.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.common.core.util.R;
import com.px.pa.modulars.code.entity.CodeTaskExample;

import java.util.List;

/**
 * 任务执行记录
 *
 *
 * 
 */
public interface CodeTaskExampleService extends IService<CodeTaskExample> {
	/**
	 * 使用APPID查询代码审批任务信息
	 * @param appId
	 * @return
	 */
	public CodeTaskExample readByAppId(String appId);

	/**
	 * 查询某个任务的某种状态的执行记录
	 * @param taskId 任务ID
	 * @param status 状态值
	 * @return
	 */
	public List<CodeTaskExample> queryByAuditStatus(Integer taskId, Integer status);

	/**
	 * 提交审核
	 * @param taskExample
	 */
	public R submitAudit(CodeTaskExample taskExample);

	/**
	 * 撤回申请
	 * @param taskExample
	 */
	public R undocodeAudit(CodeTaskExample taskExample);

	/**
	 * 发布任务
	 * @param taskExample
	 */
	public boolean release(CodeTaskExample taskExample);

	/**
	 * 查询某个小程序的所有发布任务信息
	 * @param appId
	 * @return
	 */
	public List<CodeTaskExample> queryByAppId(String appId);
}
