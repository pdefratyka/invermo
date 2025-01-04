package com.invermo.business.service.persistence;

import com.invermo.business.domain.Category;
import com.invermo.business.facade.InnerBusinessFacade;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CategoryPersistenceService {

    private final InnerBusinessFacade innerBusinessFacade;

    public List<Category> getAllMainCategories() {
        return innerBusinessFacade.getAllMainCategories();
    }

    public List<Category> getChildCategoriesByParentId(final long parentId) {
        return innerBusinessFacade.getChildCategoriesByParentId(parentId);
    }

    public List<Category> getCategoriesByAssetId(final long assetId) {
        return innerBusinessFacade.getCategoriesByAssetId(assetId);
    }

    public void saveAssetCategory(long assetId, long categoryId) {
        innerBusinessFacade.saveAssetCategory(assetId, categoryId);
    }
}
