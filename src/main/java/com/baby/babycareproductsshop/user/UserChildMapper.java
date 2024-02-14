package com.baby.babycareproductsshop.user;

import com.baby.babycareproductsshop.user.model.UserChildDto;
import com.baby.babycareproductsshop.user.model.UserChildVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserChildMapper {
    int insUserChildren(UserChildDto list);
    List<UserChildVo> selUserChildren(int iuser);
    int delUserChildren(int iuser);
}
