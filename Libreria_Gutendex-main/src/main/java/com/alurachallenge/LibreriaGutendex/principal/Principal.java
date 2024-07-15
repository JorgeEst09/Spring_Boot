package com.alurachallenge.LibreriaGutendex.principal;

import com.alurachallenge.LibreriaGutendex.model.*;
import com.alurachallenge.LibreriaGutendex.repository.AutorRepository;
import com.alurachallenge.LibreriaGutendex.repository.LibroRepository;
import com.alurachallenge.LibreriaGutendex.service.ConsumoAPI;
import com.alurachallenge.LibreriaGutendex.service.ConvierteDatos;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Clase principal de la aplicación que maneja la interacción con el usuario.
 */
@Component
public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";

    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner entrada = new Scanner(System.in);

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    /**
     * Muestra el menú principal y gestiona las opciones seleccionadas por el usuario.
     */
    public void muestraMenu() {
        int option = -1;
        while (option != 0) {
            String menu = """
                    1 - Buscar libro por título
                    2 - Mostrar libros registrados
                    3 - Mostrar autores registrados
                    4 - Mostrar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    0 - Salir
                    """;
            System.out.println(menu);
            option = entrada.nextInt();
            entrada.nextLine();

            switch (option) {
                case 1 -> libroPorTitulo();
                case 2 -> librosRegistrados();
                case 3 -> librosPorAutor();
                case 4 -> autorPorAnio();
                case 5 -> libroPorIdioma();
                case 0 -> System.out.println("Cerrando aplicación");
                default -> System.out.println("Opción inválida");
            }
        }
    }

    /**
     * Busca un libro por su título utilizando la API.
     *
     * @param tituloLibro El título del libro a buscar.
     * @return Los datos del libro encontrado.
     * @throws IOException Si ocurre un error durante la búsqueda.
     */
    private DatosLibros buscarLibroPorTitulo(String tituloLibro) throws IOException {
        String json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
        Datos datosBusqueda = conversor.obtenerDatos(json, Datos.class);

        return datosBusqueda.libros().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Maneja la búsqueda de un libro por su título y muestra los resultados.
     */
    private void libroPorTitulo() {
        System.out.println("Ingrese el nombre del Libro que desea Buscar:");
        String tituloLibro = entrada.nextLine();

        try {
            DatosLibros libro = buscarLibroPorTitulo(tituloLibro);
            if (libro != null) {
                guardarLibroEnBaseDeDatos(libro);
                System.out.println("Libro Encontrado:");
                System.out.println("----- LIBRO -----");
                System.out.println("Título: " + libro.titulo());
                System.out.println("Autor(es): " + libro.autor().stream().map(DatosAutor::nombre).collect(Collectors.joining(", ")));
                System.out.println("Idiomas: " + String.join(", ", libro.idioma()));
                System.out.println("Número de Descargas: " + libro.numeroDeDescargas());
                System.out.println("---------------");
            } else {
                System.out.println("Libro no Encontrado. Seleccione otra Opción.");
            }
        } catch (IOException e) {
            System.err.println("Error al buscar el libro: " + e.getMessage());
        }

        System.out.println("\n");
    }

    /**
     * Guarda un libro en la base de datos si no existe previamente.
     *
     * @param datosLibros Los datos del libro a guardar.
     */
    public void guardarLibroEnBaseDeDatos(DatosLibros datosLibros) {
        // Verificar si el libro ya existe en la base de datos
        Libro libroExistente = libroRepository.findByTituloIgnoreCase(datosLibros.titulo());
        if (libroExistente != null) {
            System.out.println("No se puede registrar el libro, ya existe.");
        } else {
            // Buscar y/o guardar autores
            List<Autor> autores = datosLibros.autor().stream()
                    .map(datosAutor -> autorRepository.findByNombre(datosAutor.nombre())
                            .orElseGet(() -> autorRepository.save(new Autor(datosAutor))))
                    .collect(Collectors.toList());

            // Crear el nuevo libro
            Libro nuevoLibro = new Libro(datosLibros, autores);
            libroRepository.save(nuevoLibro);
            System.out.println("Libro guardado exitosamente: " + nuevoLibro);
        }
    }

    /**
     * Muestra todos los libros registrados en la base de datos.
     */
    private void librosRegistrados() {
        List<Libro> libros = libroRepository.encontrarTodoConAutores();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            System.out.println("----- LIBROS REGISTRADOS -----");
            for (Libro libro : libros) {
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Autor(es): " + libro.getAutores().stream().map(Autor::getNombre).collect(Collectors.joining(", ")));
                System.out.println("Idiomas: " + libro.getLenguajes());
                System.out.println("Número de Descargas: " + libro.getNumeroDescargas());
                System.out.println("-----------------------------");
            }
        }
        System.out.println("\n");
    }

    /**
     * Muestra todos los autores registrados en la base de datos junto con sus libros.
     */
    @Transactional
    public void librosPorAutor() {
        List<Autor> autores = autorRepository.libroAutor();
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
        } else {
            System.out.println("----- AUTORES REGISTRADOS -----");
            for (Autor autor : autores) {
                System.out.println(autor.toString());
                System.out.println("-----------------------------");
            }
        }
        System.out.println("\n");
    }

    /**
     * Muestra los autores que vivieron en un año específico.
     */
    @Transactional
    public void autorPorAnio() {
        System.out.println("Ingrese un año para buscar autores:");
        int anio = entrada.nextInt();
        entrada.nextLine(); // Consumir el salto de línea

        List<Autor> autores = autorRepository.autoresAnio(anio);
        if (autores.isEmpty()) {
            System.out.println("No hay autores que vivieron en el año " + anio + ".");
        } else {
            System.out.println("----- AUTORES QUE VIVIERON EN EL AÑO " + anio + " -----");
            for (Autor autor : autores) {
                System.out.println(autor.toString());
                System.out.println("-----------------------------");
            }
        }
        System.out.println("\n");
    }

    /**
     * Muestra el menú para seleccionar libros por idioma.
     */
    private void libroPorIdioma() {
        System.out.println("Selecciona el lenguaje/idioma que deseas buscar:");
        while (true) {
            String opciones = """
                1. en - Inglés
                2. es - Español
                3. fr - Francés
                4. pt - Portugués
                0. Volver a las opciones anteriores
                """;
            System.out.println(opciones);
            int opcion;
            while (true) {
                if (entrada.hasNextInt()) {
                    opcion = entrada.nextInt();
                    entrada.nextLine(); // Consumir el salto de línea
                    break;
                } else {
                    System.out.println("Formato inválido, ingrese un número que esté disponible en el menú");
                    entrada.nextLine(); // Limpiar el buffer
                }
            }
            switch (opcion) {
                case 0 -> {
                    return; // Salir del menú
                }
                case 1 -> buscarLibrosPorIdioma(Idiomas.EN);
                case 2 -> buscarLibrosPorIdioma(Idiomas.ES);
                case 3 -> buscarLibrosPorIdioma(Idiomas.FR);
                case 4 -> buscarLibrosPorIdioma(Idiomas.PT);
                default -> System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        }
    }

    /**
     * Busca libros por idioma y muestra los resultados.
     *
     * @param idioma El idioma en el que buscar los libros.
     */
    @Transactional
    public void buscarLibrosPorIdioma(Idiomas idioma) {
        String codigoIdioma = idioma.getCodigo();
        List<Libro> libros = libroRepository.findByLenguajes(codigoIdioma);

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma seleccionado.");
        } else {
            System.out.println("Libros encontrados en " + idioma.getDescripcion() + ":");
            for (Libro libro : libros) {
                // Cargar explícitamente la colección de autores
                Hibernate.initialize(libro.getAutores());
                System.out.println(libro);
            }
        }
    }
}
