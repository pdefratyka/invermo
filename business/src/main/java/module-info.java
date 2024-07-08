module com.invermo.business {
    requires java.sql;
    requires lombok;
    requires com.invermo.persistence;
    exports com.invermo.business.facade;
    exports com.invermo.business.domain;
    exports com.invermo.business.enumeration;
}