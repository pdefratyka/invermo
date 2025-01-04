package com.invermo.application.service.impl;

import com.invermo.application.facade.InnerApplicationFacade;
import com.invermo.business.domain.Category;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CategoryService {

    private final InnerApplicationFacade innerApplicationFacade;

    public List<Category> getAllMainCategories() {
        return innerApplicationFacade.getAllMainCategories();
    }

    public List<Category> getChildCategoriesByParentId(long parentId) {
        return innerApplicationFacade.getChildCategoriesByParentId(parentId);
    }

    public List<Category> getCategoriesByAssetId(long assetId) {
        return innerApplicationFacade.getCategoriesByAssetId(assetId);
    }

    public void saveAssetCategory(long assetId, long categoryId) {
        innerApplicationFacade.saveAssetCategory(assetId, categoryId);
    }
}
