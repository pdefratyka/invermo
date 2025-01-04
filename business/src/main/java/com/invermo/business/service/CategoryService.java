package com.invermo.business.service;

import com.invermo.business.domain.Category;
import com.invermo.business.service.persistence.CategoryPersistenceService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CategoryService {

    private final CategoryPersistenceService categoryPersistenceService;

    public List<Category> getAllMainCategories() {
        return categoryPersistenceService.getAllMainCategories();
    }

    public List<Category> getChildCategoriesByParentId(long parentId) {
        return categoryPersistenceService.getChildCategoriesByParentId(parentId);
    }

    public List<Category> getCategoriesByAssetId(long assetId) {
        return categoryPersistenceService.getCategoriesByAssetId(assetId);
    }

    public void saveAssetCategory(long assetId, long categoryId) {
        categoryPersistenceService.saveAssetCategory(assetId, categoryId);
    }
}
