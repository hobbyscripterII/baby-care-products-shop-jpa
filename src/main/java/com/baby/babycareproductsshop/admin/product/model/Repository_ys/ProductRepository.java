package com.baby.babycareproductsshop.admin.product.model.Repository_ys;


import com.baby.babycareproductsshop.admin.product.model.Product2141234Vo;
import com.baby.babycareproductsshop.entity.product.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long>, ProductQdslRepository  {

    List<Product2141234Vo> findAllByRcFl(Long rcFl);

    List<Product2141234Vo> findAllByNewFl(Long newFl);

    List<Product2141234Vo> findAllByPopFl(Long popFl);

    /*int updateByIproduct (Long iproduct);*/

}
