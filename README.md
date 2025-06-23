Microservicio de Reseñas y Calificaciones
Este repositorio contiene el código fuente de un microservicio RESTful desarrollado con Spring Boot, diseñado para gestionar reseñas y calificaciones de productos. Permite a los usuarios crear, consultar, actualizar y eliminar reseñas, así como obtener estadísticas de puntuación para productos.

🚀 Tecnologías Utilizadas
Java 17: Lenguaje de programación.
Spring Boot 3.1.5: Framework para el desarrollo rápido de aplicaciones Java.
Spring Data JPA: Para la persistencia de datos y la interacción con la base de datos.
MySQL: Base de datos relacional para almacenar las reseñas.
Maven: Herramienta de gestión de dependencias y construcción de proyectos.
Lombok: Para reducir el código boilerplate (constructores, getters, setters, etc.).
Springdoc OpenAPI (Swagger UI): Para la generación automática de documentación de la API y una interfaz interactiva para probar los endpoints.
Jakarta Validation (Bean Validation): Para la validación de datos de entrada en los modelos.
✨ Características
Gestión de Reseñas (CRUD):
Crear nuevas reseñas.
Consultar reseñas por ID o por ID de producto.
Actualizar reseñas existentes.
Eliminar reseñas.
Estadísticas de Puntuación:
Obtener la puntuación promedio de un producto.
Contar el número total de reseñas para un producto.
Validación de Datos: Validación robusta de los datos de entrada para asegurar la integridad de la información.
Documentación Interactiva de la API: Acceso a una interfaz Swagger UI para explorar y probar todos los endpoints del microservicio.
