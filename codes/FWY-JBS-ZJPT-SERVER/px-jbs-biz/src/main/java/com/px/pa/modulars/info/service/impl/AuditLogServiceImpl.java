
package com.px.pa.modulars.info.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.px.pa.modulars.info.entity.AuditLog;
import com.px.pa.modulars.info.mapper.AuditLogMapper;
import com.px.pa.modulars.info.service.AuditLogService;
import org.springframework.stereotype.Service;

/**
 * 审批记录
 *
 * 
 * 
 */
@Service
public class AuditLogServiceImpl extends ServiceImpl<AuditLogMapper, AuditLog> implements AuditLogService {

	@Override
	public void log(Integer appletId, String type, String auditId, Integer createBy, String remark) {

	}

	@Override
	public void notify(Integer appletId, String auditId, Integer status, String resultMsg) {

	}
}
