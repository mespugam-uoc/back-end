-- Esquema inicial de Biblioteca (Producto 3).
-- Docker lo ejecuta únicamente cuando se crea una base de datos nueva.

CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255),
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255),
    rol VARCHAR(255) NOT NULL DEFAULT 'USER',
    activo BIT(1) NOT NULL DEFAULT FALSE,
    fecha_registro DATE
);

CREATE TABLE IF NOT EXISTS libros (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255),
    autor VARCHAR(255),
    isbn VARCHAR(255) UNIQUE,
    disponible BIT(1) NOT NULL DEFAULT TRUE,
    creado_por_id BIGINT,
    FOREIGN KEY (creado_por_id) REFERENCES usuarios(id)
);

CREATE TABLE IF NOT EXISTS prestamos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    libro_id BIGINT NOT NULL,
    fecha_prestamo DATE,
    fecha_devolucion DATE,
    devuelto BIT(1) NOT NULL DEFAULT FALSE,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (libro_id) REFERENCES libros(id)
);
