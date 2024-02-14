package com.baby.babycareproductsshop.user;

import com.baby.babycareproductsshop.user.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserAddressMapper {
    int insUserAddress(UserInsAddressDto dto);
    List<UserSelAddressVo> selUserAddress(int iuser);
    int updUserAddress(UserUpdAddressDto dto);
    int delUserAddress(UserDelAddressDto dto);
}
