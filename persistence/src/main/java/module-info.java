module com.invermo.persistence {
    requires java.sql;
    requires lombok;
    exports com.invermo.persistence.facade;
    exports com.invermo.persistence.entity;
    exports com.invermo.persistence.enumeration;
}