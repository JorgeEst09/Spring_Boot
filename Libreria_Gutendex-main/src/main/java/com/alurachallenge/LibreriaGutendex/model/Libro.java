package com.alurachallenge.LibreriaGutendex.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * La clase Libro representa un libro en la base de datos.
 */
@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "libro_autor",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Autor> autores;

    @Column(name = "lenguajes")
    private String lenguajes;

    private Double numeroDescargas;

    /**
     * Constructor por defecto.
     */
    public Libro() {
    }

    /**
     * Constructor que inicializa un libro con datos específicos.
     *
     * @param datosLibros Los datos del libro.
     * @param autores     La lista de autores del libro.
     */
    public Libro(DatosLibros datosLibros, List<Autor> autores) {
        this.titulo = datosLibros.titulo();
        this.autores = autores;
        this.lenguajes = String.join(",", datosLibros.idioma());
        this.numeroDescargas = datosLibros.numeroDeDescargas();
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public String getLenguajes() {
        return lenguajes;
    }

    public void setLenguajes(String lenguajes) {
        this.lenguajes = lenguajes;
    }

    public Double getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Double numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    @Override
    public String toString() {
        String autoresNombre = autores.stream()
                .map(Autor::getNombre)
                .collect(Collectors.joining(", "));

        return "----- Libro -----\n" +
                "ID: " + id + "\n" +
                "Título: " + titulo + "\n" +
                "Autores: " + autoresNombre + "\n" +
                "Lenguajes: " + lenguajes + "\n" +
                "Número de Descargas: " + numeroDescargas;
    }
}
