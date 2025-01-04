package com.invermo.application.gui.composition.service;

import com.invermo.application.facade.InnerApplicationFacade;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@AllArgsConstructor
public class CompositionService {

    private final InnerApplicationFacade innerApplicationFacade;

    public Map<String, BigDecimal> getComposition(final Long userId, final long categoryId){
        return innerApplicationFacade.getComposition(userId, categoryId);
    }
}
