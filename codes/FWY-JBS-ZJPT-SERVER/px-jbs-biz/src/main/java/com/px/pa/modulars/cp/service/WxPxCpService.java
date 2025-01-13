package com.px.pa.modulars.cp.service;

import com.px.pa.modulars.cp.entity.CpInfo;
import lombok.val;
import me.chanjar.weixin.cp.api.WxCpExternalContactService;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.config.impl.WxCpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 企业号操作服务
 */
@Service
public class WxPxCpService {
	@Autowired
	private InfoService infoService;
	//服务Map
	private static Map<Integer, WxCpService> cpServiceMap;

	/**
	 * 获取企业服务ID
	 *
	 * @param infoId
	 * @return
	 */
	public WxCpService getCpService(Integer infoId) {
		WxCpService service = cpServiceMap.get(infoId);
		if (service == null) {
			service = new WxCpServiceImpl();
			CpInfo info = this.infoService.getById(infoId);
			val configStorage = new WxCpDefaultConfigImpl();
			configStorage.setCorpId(info.getCpid());
//		configStorage.setAgentId(a.getAgentId());
			configStorage.setCorpSecret(info.getSecret());
//		configStorage.setToken(a.getToken());
//		configStorage.setAesKey(a.getAesKey());

			service.setWxCpConfigStorage(configStorage);
		}
		return service;
	}

	/**
	 * 获取用户外部联系人信息
	 *
	 * @param infoId
	 * @return
	 */
	public WxCpExternalContactService getCpExternalContactService(Integer infoId) {
		WxCpService cpService = this.getCpService(infoId);
		return cpService.getExternalContactService();
	}

}
