package com.invermo.persistence.repository.category;

import com.invermo.persistence.entity.CategoryEntity;
import com.invermo.persistence.repository.AbstractRepository;
import com.invermo.persistence.tables.CategoryTable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository extends AbstractRepository {

    public List<CategoryEntity> findAllMainCategories() {
        final String query = CategoryDatabaseQueries.getMainCategories();
        return executeQuery(query, this::extractAssetsFromResultSet);
    }

    public List<CategoryEntity> findChildCategoriesByParentId(final long parentId) {
        final String query = CategoryDatabaseQueries.getChildCategoriesByParentId(parentId);
        return executeQuery(query, this::extractAssetsFromResultSet);
    }

    public List<CategoryEntity> findCategoriesByAssetId(final long assetId) {
        final String query = CategoryDatabaseQueries.getCategoriesByAssetId(assetId);
        return executeQuery(query, this::extractAssetsFromResultSet);
    }

    public void saveCategory(long assetId, long categoryId) {
        final String query = CategoryDatabaseQueries.saveCategoryAsset(assetId, categoryId);
        execute(query);
    }

    private List<CategoryEntity> extractAssetsFromResultSet(final ResultSet resultSet) {
        try {
            final List<CategoryEntity> categories = new ArrayList<>();
            while (resultSet.next()) {
                final CategoryEntity asset = buildAssetFromResultSet(resultSet);
                categories.add(asset);
            }
            return categories;
        } catch (SQLException ex) {
            throw new RuntimeException("Error during processing ResultSet", ex);
        }
    }

    private CategoryEntity buildAssetFromResultSet(final ResultSet resultSet) throws SQLException {
        final long categoryId = resultSet.getLong(CategoryTable.CATEGORY_ID);
        final String name = resultSet.getString(CategoryTable.NAME);
        return new CategoryEntity(categoryId, name);
    }
}
