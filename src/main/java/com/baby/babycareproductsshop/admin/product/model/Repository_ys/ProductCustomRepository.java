package com.baby.babycareproductsshop.admin.product.model.Repository_ys;

import com.baby.babycareproductsshop.entity.product.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCustomRepository extends JpaRepository<ProductEntity,Long>, ProductRepository{

}
