package com.invermo.business.mapper;

import com.invermo.business.domain.Category;
import com.invermo.persistence.entity.CategoryEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryMapper {

    public static List<Category> mapToCategories(final List<CategoryEntity> categoryEntities) {
        if (categoryEntities == null) {
            return List.of();
        }
        return categoryEntities.stream()
                .map(CategoryMapper::mapToCategory)
                .toList();
    }

    public static Category mapToCategory(final CategoryEntity category) {
        return Category.builder()
                .id(category.categoryId())
                .name(category.name())
                .build();
    }
}
