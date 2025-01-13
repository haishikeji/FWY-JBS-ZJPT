package com.px.pa.modulars.shop.vo;

import com.px.pa.modulars.shop.entity.CoreDmScore;
import com.px.pa.modulars.shop.entity.CoreShop;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class DmScoreVo extends CoreDmScore implements Serializable {

    private String userName;

    private Integer sid;
}
