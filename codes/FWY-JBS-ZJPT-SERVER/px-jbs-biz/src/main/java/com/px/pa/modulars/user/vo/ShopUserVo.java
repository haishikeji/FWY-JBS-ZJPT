package com.px.pa.modulars.user.vo;

import com.px.apis.user.vo.UserTimeVo;
import com.px.pa.modulars.user.entity.BasisShopUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ShopUserVo extends BasisShopUser implements Serializable {

    private String name;
    private String avatar;
    private Integer sex;
    private String phone;
    private Integer monthCount;
    private List<UserTimeVo> times;
    private Integer pageNo;
    private Integer pageSize;
    private Integer isDm;
    private String keyWord;
    private Integer couponNum;
    private Date birthday;
}
