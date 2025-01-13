

package com.px.pa.modulars.info.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.common.core.vo.form.FormTreeVo;
import com.px.pa.modulars.info.entity.Applets;

import java.io.File;
import java.util.List;

/**
 * 小程序信息
 *
 *
 * 
 */
public interface AppletsService extends IService<Applets> {

	/**
	 * 创建微信小程序
	 *
	 * @param applet 小程序信息
	 * @return
	 */
	public boolean createWxMp(Applets applet);

	/**
	 * 查询可以更新的微信小程序
	 *
	 * @param productId
	 * @return
	 */
	public List<Applets> queryRenewable(Integer productId);

	public Applets readByPrincipalName(String principalName);

	/**
	 * 添加成功后的处理
	 * @param applets
	 */
	public void saveSuccess(Applets applets);

	/**
	 * 同步某个小程序的信息
	 * @param appId
	 */
	public void syncInfo(String appId);

	/**
	 * 取消授权
	 * @param appId
	 */
	public void unauthorized(String appId);

	/**
	 * 通过小程序的原始ID查询小程序信息
	 * @param username
	 * @return
	 */
	public Applets readByUserName(String username);

	/**
	 * 使用AppID的获取小程序信息
	 * @param appId
	 * @return
	 */
	public Applets readByAppId(String appId);

	/**
	 * 根据分类查询小程序信息树
	 * @return
	 */
	public List<FormTreeVo> queryTreeByCate();

	/**
	 * 上传头像
	 * @param appid
	 * @param fileUrl 头像文件
	 * @return
	 */
	public String uploadPortrait(String appid,String fileUrl);
}
