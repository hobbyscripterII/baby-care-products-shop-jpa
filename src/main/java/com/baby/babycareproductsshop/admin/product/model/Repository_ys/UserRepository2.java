package com.baby.babycareproductsshop.admin.product.model.Repository_ys;


import com.baby.babycareproductsshop.admin.product.model.ddddMapping;
import com.baby.babycareproductsshop.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserRepository2 extends JpaRepository<UserEntity,Long> {
        List<ddddMapping> findAllBy();






}
