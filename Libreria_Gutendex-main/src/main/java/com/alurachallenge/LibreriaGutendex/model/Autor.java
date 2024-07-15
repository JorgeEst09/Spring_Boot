package com.alurachallenge.LibreriaGutendex.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * La clase Autor representa un autor en la base de datos.
 */
@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer fechaDeNacimiento;
    private Integer fechaDeMuerte;

    @ManyToMany(mappedBy = "autores", fetch = FetchType.EAGER)
    private List<Libro> libros;

    /**
     * Constructor por defecto.
     */
    public Autor() {
    }

    /**
     * Constructor que inicializa un autor con datos específicos.
     *
     * @param datosAutor Los datos del autor.
     */
    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.fechaDeNacimiento = datosAutor.fechaDeNacimiento();
        this.fechaDeMuerte = datosAutor.fechaDeMuerte();
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Integer fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public Integer getFechaDeMuerte() {
        return fechaDeMuerte;
    }

    public void setFechaDeMuerte(Integer fechaDeMuerte) {
        this.fechaDeMuerte = fechaDeMuerte;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        String listaLibros = (libros != null && !libros.isEmpty()) ? libros.stream()
                .map(Libro::getTitulo)
                .collect(Collectors.joining(", ")) : "Ninguno";
        return "----- Autor -----\n" +
                "Nombre: " + nombre + "\n" +
                "Año de Nacimiento: " + fechaDeNacimiento + "\n" +
                "Año de Fallecimiento: " + fechaDeMuerte + "\n" +
                "Libros: " + listaLibros;
    }
}
