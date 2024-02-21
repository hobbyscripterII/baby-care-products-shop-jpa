package com.baby.babycareproductsshop.admin.user;

import com.baby.babycareproductsshop.admin.user.model.AdminSelAllUserVo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminUserQdslRepository {
    List<AdminSelAllUserVo> selUserAll(long unregisteredFl, Pageable pageable);
}
