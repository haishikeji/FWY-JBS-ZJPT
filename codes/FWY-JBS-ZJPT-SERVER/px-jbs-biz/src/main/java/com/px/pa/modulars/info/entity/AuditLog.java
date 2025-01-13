

package com.px.pa.modulars.info.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 审批记录
 *
 *
 *
 */
@Data
@TableName("applets_audit_log")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "审批记录")
public class AuditLog extends Model<AuditLog> {
private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    @ApiModelProperty(value="")
    private Integer id;
    /**
     * 审批类型
     */
    @ApiModelProperty(value="审批类型")
    private String type;
    /**
     * 审批码
     */
    @ApiModelProperty(value="审批码")
    private String auditId;
    @ApiModelProperty("相关产品")
    private Integer appletId;
    /**
     * 审核状态：1：审核中；2：审核通过；3：审核未通过
     */
    @ApiModelProperty(value="审核状态：1：审核中；2：审核通过；3：审核未通过")
    private Integer status;
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private LocalDateTime createTime;
    /**
     * 结束时间
     */
    @ApiModelProperty(value="结束时间")
    private LocalDateTime endTime;
    /**
     * 创建人
     */
    @ApiModelProperty(value="创建人")
    private Integer createBy;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
    /**
     * 最后更新时间
     */
    @ApiModelProperty(value="最后更新时间")
    private LocalDateTime updateTime;
    /**
     * 结果消息
     */
    @ApiModelProperty(value="结果消息")
    private String resultMsg;
    }
