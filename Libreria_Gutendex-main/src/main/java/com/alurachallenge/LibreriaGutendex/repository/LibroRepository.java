package com.alurachallenge.LibreriaGutendex.repository;

import com.alurachallenge.LibreriaGutendex.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para manejar las operaciones CRUD de la entidad Libro.
 */
@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    /**
     * Encuentra un libro por su título, ignorando mayúsculas y minúsculas.
     *
     * @param titulo El título del libro.
     * @return El libro encontrado o null si no existe.
     */
    Libro findByTituloIgnoreCase(String titulo);

    /**
     * Encuentra libros por el nombre de un autor.
     *
     * @param nombre El nombre del autor.
     * @return Una lista de libros escritos por el autor especificado.
     */
    @Query("SELECT l FROM Libro l JOIN FETCH l.autores a WHERE a.nombre = :nombre")
    List<Libro> encontrarPorAutor(String nombre);

    /**
     * Obtiene todos los libros con sus autores.
     *
     * @return Una lista de libros con sus autores.
     */
    @Query("SELECT l FROM Libro l JOIN FETCH l.autores")
    List<Libro> encontrarTodoConAutores();

    /**
     * Encuentra libros por el código de idioma.
     *
     * @param codigoIdioma El código del idioma.
     * @return Una lista de libros escritos en el idioma especificado.
     */
    @Query("SELECT l FROM Libro l LEFT JOIN FETCH l.autores WHERE l.lenguajes = :codigoIdioma")
    List<Libro> findByLenguajes(String codigoIdioma);
}
