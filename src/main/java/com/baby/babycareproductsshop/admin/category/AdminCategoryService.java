package com.baby.babycareproductsshop.admin.category;

import com.baby.babycareproductsshop.admin.category.model.CategoryVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCategoryService {
    private final AdminMainCategoryRepository mainRepository;
    private final AdminMiddleCategoryRepository middleRepository;

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
