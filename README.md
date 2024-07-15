Proyecto CHALLENGE LIBRERIA-GUTENDEX
Este proyecto es parte del programa Oracle Next Education y Alura Latam. El objetivo es experimentar directamente el rol de un desarrollador backend en el día a día, creando una aplicación con conexión a una base de datos relacional. Aquí se aplican conceptos avanzados de Java y Spring, como el consumo de APIs externas y la persistencia de datos.

Requisitos
Para ejecutar este proyecto necesitas:

Java 11 o superior
Spring Framework
PostgreSQL u otra base de datos configurada
Funcionalidad
El programa ejecuta las siguientes funcionalidades:

Buscar libro por título: Realiza una búsqueda en una API externa y guarda el libro encontrado en la base de datos.
Mostrar libros registrados: Lista todos los libros almacenados en la base de datos.
Mostrar autores registrados: Lista todos los autores almacenados en la base de datos.
Mostrar autores vivos en un determinado año: Lista los autores que estuvieron vivos en un año específico.
Listar libros por idioma: Lista los libros registrados según su idioma.
Repositorios
Los repositorios utilizados son:

LibroRepository: Interfaz para las operaciones CRUD sobre los libros.
AutorRepository: Interfaz para las operaciones CRUD sobre los autores y consultas personalizadas.
Tecnologías Utilizadas
Spring Framework: Para la gestión de dependencias, inyección de dependencias, y configuración de la aplicación.
PostgreSQL: Como base de datos relacional para almacenar los datos de libros y autores.
Java Streams y Lambdas: Para el manejo eficiente de colecciones de datos.
JPQL (Java Persistence Query Language): Para consultas avanzadas a la base de datos.
Jackson para Deserialización: Para convertir datos JSON de las APIs externas en objetos Java.
IntelliJ IDEA y Visual Studio Code: Entornos de desarrollo utilizados para escribir y depurar el código.
ChatGPT: Utilizado para asistencia y consultas técnicas en el desarrollo del proyecto.
