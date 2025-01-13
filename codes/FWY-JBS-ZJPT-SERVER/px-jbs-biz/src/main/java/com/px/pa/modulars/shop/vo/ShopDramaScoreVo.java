package com.px.pa.modulars.shop.vo;

import com.px.pa.modulars.shop.entity.CoreShopDramaScore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class ShopDramaScoreVo extends CoreShopDramaScore implements Serializable {

    private String userName;

    private String dramaName;

}
