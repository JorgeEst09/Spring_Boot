package com.alurachallenge.LibreriaGutendex.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * La clase DatosLibros representa una estructura de datos para recibir información de un libro.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibros(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DatosAutor> autor,
        @JsonAlias("languages") List<String> idioma,
        @JsonAlias("download_count") Double numeroDeDescargas) {
}
