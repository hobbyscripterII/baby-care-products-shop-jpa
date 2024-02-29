package com.baby.babycareproductsshop.admin.category;

import com.baby.babycareproductsshop.admin.category.model.CategoryVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/category")
public class AdminCategoryController {
    private final AdminCategoryService service;

    @GetMapping
    public List<CategoryVo> orderCategoryList() {
        return service.categoryList();
    }
}
