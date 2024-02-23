package com.baby.babycareproductsshop.admin.user;

import com.baby.babycareproductsshop.admin.user.model.AdminSelAllUserDto;
import com.baby.babycareproductsshop.admin.user.model.AdminSelUserSignupDto;
import com.baby.babycareproductsshop.entity.user.UserEntity;

import java.util.List;

public interface AdminUserQdslRepository {
    List<UserEntity> selUserAll(AdminSelAllUserDto dto);
    List<UserEntity> selUserSignupStatistics(AdminSelUserSignupDto dto);
}
