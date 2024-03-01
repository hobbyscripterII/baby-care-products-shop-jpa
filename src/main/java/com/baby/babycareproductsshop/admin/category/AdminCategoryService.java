package com.baby.babycareproductsshop.admin.category;

import com.baby.babycareproductsshop.admin.category.model.CategoryVo;
import com.baby.babycareproductsshop.admin.category.model.MiddleCategoryInsDto;
import com.baby.babycareproductsshop.common.Const;
import com.baby.babycareproductsshop.common.ResVo;
import com.baby.babycareproductsshop.common.Utils;
import com.baby.babycareproductsshop.entity.product.ProductMainCategoryEntity;
import com.baby.babycareproductsshop.entity.product.ProductMiddleCategoryEntity;
import com.baby.babycareproductsshop.exception.AuthErrorCode;
import com.baby.babycareproductsshop.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCategoryService {
    private final AdminMainCategoryRepository mainRepository;
    private final AdminMiddleCategoryRepository middleRepository;

    @Transactional
    public ResVo insMainCategory(String mainCategory) {
        try {
            ProductMainCategoryEntity mainCategoryEntity = new ProductMainCategoryEntity();
            mainCategoryEntity.setMainCategory(mainCategory);
            mainRepository.save(mainCategoryEntity);
            return new ResVo(Const.SUCCESS);
        } catch (Exception e) {
            throw new RestApiException(AuthErrorCode.CATEGORY_INSERT_FAIL);
        }
    }

    public ResVo delMainCategory(long imain) {
        try {
            ProductMainCategoryEntity mainCategoryEntity = mainRepository.getReferenceById(imain);
            if (Utils.isNotNull(mainCategoryEntity)) {
                mainRepository.delete(mainCategoryEntity);
                return new ResVo(Const.SUCCESS);
            } else {
                throw new RestApiException(AuthErrorCode.CATEGORY_DELETE_FAIL);
            }
        } catch (Exception e) {
            throw new RestApiException(AuthErrorCode.CATEGORY_DELETE_FAIL);
        }
    }

    @Transactional
    public ResVo insMiddleCategory(MiddleCategoryInsDto dto) {
        try {
            ProductMiddleCategoryEntity middleCategoryEntity = new ProductMiddleCategoryEntity();
//            middleCategoryEntity.getProductMainCategory().setImain(dto.getImain()); // 수정 필요
            middleCategoryEntity.setImiddle(dto.getImain());
            middleCategoryEntity.setMiddleCategory(dto.getMiddleCategory());
            middleRepository.save(middleCategoryEntity);
            return new ResVo(Const.SUCCESS);
        } catch (Exception e) {
            throw new RestApiException(AuthErrorCode.CATEGORY_INSERT_FAIL);
        }
    }

    public ResVo delMiddleCategory(long candidateKey) {
        try {
            ProductMiddleCategoryEntity middleCategoryEntity = middleRepository.getReferenceById(candidateKey);
            if (Utils.isNotNull(middleCategoryEntity)) {
                middleRepository.delete(middleCategoryEntity);
                return new ResVo(Const.SUCCESS);
            } else {
                throw new RestApiException(AuthErrorCode.CATEGORY_DELETE_FAIL);
            }
        } catch (Exception e) {
            throw new RestApiException(AuthErrorCode.CATEGORY_DELETE_FAIL);
        }
    }

    public List<CategoryVo> categoryList() {
        return mainRepository.findAll()
                .stream()
                .map(item -> {
                    List<CategoryVo.MiddleCategoryVo> middleCategory = middleRepository.getByProductMainCategory_Imain(item.getImain())
                            .stream()
                            .map(middle -> CategoryVo.MiddleCategoryVo
                                    .builder()
                                    .imiddle(middle.getImiddle())
                                    .middleCategory(middle.getMiddleCategory())
                                    .build())
                            .toList();

                    return CategoryVo
                            .builder()
                            .imain(item.getImain())
                            .mainCategory(item.getMainCategory())
                            .middleCategoryList(middleCategory)
                            .build();
                })
                .toList();
    }
}
