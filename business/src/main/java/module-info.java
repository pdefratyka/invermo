module com.invermo.business {
    requires java.sql;
    requires lombok;
    requires com.invermo.persistence;
    requires com.google.api.services.sheets;
    requires com.google.api.client;
    requires com.google.api.client.json.gson;
    requires google.api.client;
    requires com.google.api.client.extensions.jetty.auth;
    requires com.google.api.client.auth;
    requires com.google.api.client.extensions.java6.auth;
    requires org.slf4j;
    requires jdk.unsupported;
    requires jdk.httpserver;
    requires org.apache.commons.lang3;
    exports com.invermo.business.facade;
    exports com.invermo.business.domain;
    exports com.invermo.business.enumeration;
}