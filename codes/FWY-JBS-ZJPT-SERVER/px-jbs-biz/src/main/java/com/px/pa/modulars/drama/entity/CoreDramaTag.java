

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
 * 剧本标签关联
 *
 *
 *
 */
@Data
@TableName("core_drama_tag")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "剧本标签关联")
public class CoreDramaTag extends BaseModel<CoreDramaTag> {
private static final long serialVersionUID = 1L;

                    /**
     * 剧本id
     */
                        @Size(max=11,message = "剧本id超出长度11的限制")
                @ApiModelProperty(value="剧本id")
        private Integer did;
                /**
     * 标签id
     */
                        @Size(max=11,message = "标签id超出长度11的限制")
                @ApiModelProperty(value="标签id")
        private Integer tid;
        }
