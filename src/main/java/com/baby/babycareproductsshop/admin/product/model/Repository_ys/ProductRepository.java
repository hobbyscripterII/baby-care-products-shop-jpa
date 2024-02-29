package com.baby.babycareproductsshop.admin.product.model.Repository_ys;


import com.baby.babycareproductsshop.admin.product.model.ProductManagementSelVo;
import com.baby.babycareproductsshop.entity.product.ProductEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long>, ProductQdslRepository  {

    List<ProductManagementSelVo> findAllByRcFl(int rcFl);

    List<ProductManagementSelVo> findAllByNewFl(int newFl);

    List<ProductManagementSelVo> findAllByPopFl(int popFl);

}
