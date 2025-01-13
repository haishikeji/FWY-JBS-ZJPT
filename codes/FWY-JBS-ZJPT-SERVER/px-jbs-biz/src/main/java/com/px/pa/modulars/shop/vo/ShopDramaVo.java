package com.px.pa.modulars.shop.vo;

import com.px.pa.modulars.drama.entity.CoreDramaRole;
import com.px.pa.modulars.shop.entity.CoreShopDrama;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ShopDramaVo extends CoreShopDrama implements Serializable {

    private String dramaName;

    private String shopName;

    private String specName;

    private String diffName;

    private String detail;
    private String duration;
    private String image;

    private List<Integer> typeIds;

    private Integer spec;
    private Integer male;
    private Integer female;

    private List<CoreDramaRole> roles;

    private List<String> typeNames;

    private Integer wants;
    private Integer play;
    private Integer follow;
    private Integer reserve;

    private List<ScoreVo> scores;

}
