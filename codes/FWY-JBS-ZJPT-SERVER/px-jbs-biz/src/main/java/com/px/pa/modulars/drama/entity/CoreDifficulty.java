

package com.px.pa.modulars.drama.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.px.basic.alone.core.base.BaseModel;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 剧本困难度
 *
 *
 *
 */
@Data
@TableName("core_difficulty")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "剧本困难度")
public class CoreDifficulty extends BaseModel<CoreDifficulty> {
private static final long serialVersionUID = 1L;

                    /**
     * 困难度
     */
                        @Size(max=255,message = "困难度超出长度255的限制")
                @ApiModelProperty(value="困难度")
        private String name;
                /**
     * 困难度描述
     */
                        @Size(max=255,message = "困难度描述超出长度255的限制")
                @ApiModelProperty(value="困难度描述")
        private String detail;
                                                                }
