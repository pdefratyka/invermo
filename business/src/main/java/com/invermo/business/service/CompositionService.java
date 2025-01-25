package com.invermo.business.service;

import com.invermo.business.domain.Category;
import com.invermo.business.domain.SinglePortfolioAsset;
import com.invermo.business.enumeration.AssetType;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class CompositionService {

    private final PortfolioService portfolioService;
    private final CategoryService categoryService;

    public Map<String, BigDecimal> getComposition(final Long userId, final long parentCategoryId) {
        final List<Category> childCategories = categoryService.getChildCategoriesByParentId(parentCategoryId);
        final Map<Category, BigDecimal> categories = childCategories.stream()
                .collect(Collectors.toMap(category -> category, category -> BigDecimal.ZERO));
        final Category otherCategory = Category.builder()
                .name("Other")
                .build();
        categories.put(otherCategory, BigDecimal.ZERO);
        final List<SinglePortfolioAsset> portfolioAssets = portfolioService.getPortfolioAssets(userId).stream()
                .filter(positionWithAsset -> !Set.of(AssetType.CURRENCY, AssetType.CRYPTOCURRENCY).contains(AssetType.fromName(positionWithAsset.getAssetType())))
                .toList();
        for (SinglePortfolioAsset asset : portfolioAssets) {
            categoryService.getCategoriesByAssetId(asset.getAssetId())
                    .stream()
                    .filter(categories::containsKey)
                    .findFirst()
                    .ifPresentOrElse(category -> categories.put(category, categories.get(category).add(asset.getValue())),
                            () -> categories.put(otherCategory, categories.get(otherCategory).add(asset.getValue())));
        }
        return categories.entrySet().stream()
                .collect(Collectors.toMap(entry -> entry.getKey().getName(), Map.Entry::getValue));
    }
}
