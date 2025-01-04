package com.invermo.persistence.repository.category;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CategoryDatabaseQueries {

    static String getMainCategories() {
        return "SELECT c.category_id as category_id, " +
                "c.name as name " +
                "FROM invermo.category c " +
                "WHERE c.parent_id IS NULL " +
                "ORDER BY c.category_id DESC ";
    }

    static String getChildCategoriesByParentId(final long parentId) {
        return "SELECT c.category_id as category_id, " +
                "c.name as name " +
                "FROM invermo.category c " +
                "WHERE c.parent_id=" + parentId + " " +
                "ORDER BY c.category_id DESC ";
    }

    static String getCategoriesByAssetId(final long assetId) {
        return "SELECT c.category_id as category_id, " +
                "c.name as name " +
                "FROM invermo.category c " +
                "LEFT JOIN invermo.asset_category ac ON c.category_id=ac.category_id " +
                "WHERE ac.asset_id=" + assetId;
    }

    static String saveCategoryAsset(long assetId, long categoryId) {
        return "INSERT INTO asset_category (asset_id, category_id)\n" +
                "VALUES ('" +
                assetId + "','" +
                categoryId + "')";
    }
}
