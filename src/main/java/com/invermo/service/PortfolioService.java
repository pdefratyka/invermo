package com.invermo.service;

import com.invermo.gui.portfolio.dto.SinglePortfolioAsset;

import java.util.List;

public interface PortfolioService {
    List<SinglePortfolioAsset> getPortfolioAssets();
}
