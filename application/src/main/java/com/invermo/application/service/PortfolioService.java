package com.invermo.application.service;

import com.invermo.application.gui.portfolio.dto.SinglePortfolioAsset;

import java.util.List;

public interface PortfolioService {
    List<SinglePortfolioAsset> getPortfolioAssets();
}
