package com.invermo.persistence.facade;

public class PersistenceFacadeFactory {
    public static OuterPersistenceFacade createOuterPersistenceFacade() {
        return OuterPersistenceFacade.getInstance();
    }
}
