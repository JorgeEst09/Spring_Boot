package com.alurachallenge.LibreriaGutendex.repository;

import com.alurachallenge.LibreriaGutendex.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para manejar las operaciones CRUD de la entidad Autor.
 */
public interface AutorRepository extends JpaRepository<Autor, Long> {

    /**
     * Encuentra un autor por su nombre.
     *
     * @param nombre El nombre del autor.
     * @return Un Optional que contiene el autor si es encontrado.
     */
    Optional<Autor> findByNombre(String nombre);

    /**
     * Obtiene todos los autores con sus libros.
     *
     * @return Una lista de autores con sus libros.
     */
    @Query("SELECT a FROM Autor a LEFT JOIN FETCH a.libros")
    List<Autor> libroAutor();

    /**
     * Encuentra autores que estaban vivos en un año específico.
     *
     * @param year El año para buscar autores.
     * @return Una lista de autores que estaban vivos en el año especificado.
     */
    @Query("SELECT a FROM Autor a LEFT JOIN FETCH a.libros WHERE a.fechaDeNacimiento <= :year AND (a.fechaDeMuerte = 0 OR a.fechaDeMuerte >= :year)")
    List<Autor> autoresAnio(@Param("year") int year);
}
