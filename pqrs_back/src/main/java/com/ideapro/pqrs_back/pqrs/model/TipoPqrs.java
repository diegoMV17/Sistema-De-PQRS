/**
 * pqrs/model/TipoPqrs.java
 */
package com.ideapro.pqrs_back.pqrs.model;

public enum TipoPqrs {
    PETICION("Petición"),
    QUEJA("Queja"),
    RECLAMO("Reclamo"),
    SUGERENCIA("Sugerencia");

    private final String displayName;

    TipoPqrs(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
