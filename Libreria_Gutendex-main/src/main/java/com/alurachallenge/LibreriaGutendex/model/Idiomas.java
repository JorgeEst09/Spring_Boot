package com.alurachallenge.LibreriaGutendex.model;

/**
 * La enumeración Idiomas representa los idiomas disponibles con sus códigos y descripciones.
 */
public enum Idiomas {
    EN("en", "Inglés"),
    ES("es", "Español"),
    FR("fr", "Francés"),
    PT("pt", "Portugués");

    private final String codigo;
    private final String descripcion;

    /**
     * Constructor para la enumeración Idiomas.
     *
     * @param codigo      El código del idioma.
     * @param descripcion La descripción del idioma.
     */
    Idiomas(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el código del idioma.
     *
     * @return El código del idioma.
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Obtiene la descripción del idioma.
     *
     * @return La descripción del idioma.
     */
    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return descripcion + " [" + codigo + "]";
    }
}
