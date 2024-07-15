package com.alurachallenge.LibreriaGutendex.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * La clase Datos representa una estructura de datos para recibir una lista de libros.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Datos(
        @JsonAlias("results") List<DatosLibros> libros) {

    /**
     * Obtiene la lista de libros.
     *
     * @return Una lista de DatosLibros.
     */
    public List<DatosLibros> getLibros() {
        return libros;
    }
}
