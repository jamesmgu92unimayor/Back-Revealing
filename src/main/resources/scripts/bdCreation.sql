CREATE DATABASE revealing_popayan;

-- Crear la extensi�n para poder utilizar UUIDs
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Crear la tabla de usuarios
CREATE TABLE usuarios (
                          id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                          nombre VARCHAR(100) NOT NULL,
                          email VARCHAR(100) UNIQUE NOT NULL,
                          contrasena VARCHAR(255) NOT NULL,
                          tipo_usuario VARCHAR(20) NOT NULL -- Puede ser 'cliente', 'restaurante' o 'admin'
);

-- Crear la tabla de clientes
CREATE TABLE clientes (
                          id_usuario UUID PRIMARY KEY,
                          FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
);

-- Crear la tabla de restaurantes
CREATE TABLE restaurantes (
                              id_usuario UUID PRIMARY KEY,
                              FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
);

-- Crear la tabla de administradores
CREATE TABLE administradores (
                                 id_usuario UUID PRIMARY KEY,
                                 FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
);

-- Tabla para las rese�as de los restaurantes
CREATE TABLE resenas (
                         id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                         id_restaurante UUID,
                         id_cliente UUID,
                         puntuacion INT,
                         comentario TEXT,
                         fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         FOREIGN KEY (id_restaurante) REFERENCES restaurantes(id_usuario),
                         FOREIGN KEY (id_cliente) REFERENCES clientes(id_usuario)
);

-- Crear la tabla de categorias para restaurante
CREATE TABLE categorias_restaurantes (
                                         id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                         nombre VARCHAR(100) NOT NULL unique -- Ejemplo: "Casual", "Familiar", "comidas rapidas".
);

-- Crear la tabla de categorias para platos
CREATE TABLE categorias_platos (
                                   id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                   nombre VARCHAR(100) NOT NULL unique -- Ejemplo: "Entrada", "plato fuerte", "desayuno".
);

-- Crear la tabla de tipos de cocina
CREATE TABLE tipos_cocina (
                              id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                              nombre VARCHAR(100) NOT NULL unique -- Ejemplo: "Colombiana", "Mexicana", etc".
);

-- Crear la tabla de restricciones diet�ticas
CREATE TABLE restricciones_dieteticas (
                                          id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                          nombre VARCHAR(100) NOT NULL UNIQUE -- Ejemplo: "Colesterol", "Diabetes", etc.
);

-- Tabla para los platos de los restaurantes
CREATE TABLE platos (
                        id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                        id_restaurante UUID,
                        nombre VARCHAR(100) NOT NULL,
                        descripcion TEXT,
                        precio DECIMAL(10, 2),
                        FOREIGN KEY (id_restaurante) REFERENCES restaurantes(id_usuario)
);

CREATE TABLE cliente_restriccion_dietetica (
                                               id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                               id_cliente UUID,
                                               id_restriccion_dietetica UUID,
                                               FOREIGN KEY (id_cliente) REFERENCES clientes(id_usuario),
                                               FOREIGN KEY (id_restriccion_dietetica) REFERENCES restricciones_dieteticas(id)
);

CREATE TABLE cliente_tipo_cocina (
                                     id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                     id_cliente UUID,
                                     id_tipo_cocina UUID,
                                     FOREIGN KEY (id_cliente) REFERENCES clientes(id_usuario),
                                     FOREIGN KEY (id_tipo_cocina) REFERENCES tipos_cocina(id)
);

CREATE TABLE cliente_categoria_restaurante (
                                               id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                               id_cliente UUID,
                                               id_categoria_restaurante UUID,
                                               FOREIGN KEY (id_cliente) REFERENCES clientes(id_usuario),
                                               FOREIGN KEY (id_categoria_restaurante) REFERENCES categorias_restaurantes(id)
);

CREATE TABLE restaurante_tipo_cocina (
                                         id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                         id_restaurante UUID,
                                         id_tipo_cocina UUID,
                                         FOREIGN KEY (id_restaurante) REFERENCES restaurantes(id_usuario),
                                         FOREIGN KEY (id_tipo_cocina) REFERENCES tipos_cocina(id)
);

-- Modificar la tabla de restaurantes para incluir la categor�a de restaurante
ALTER TABLE restaurantes
    ADD COLUMN id_categoria_restaurante UUID,
ADD FOREIGN KEY (id_categoria_restaurante) REFERENCES categorias_restaurantes(id);

-- Modificar la tabla de platos para incluir la categoría de plato
ALTER TABLE platos
    ADD COLUMN id_categoria_plato UUID,
ADD FOREIGN KEY (id_categoria_plato) REFERENCES categorias_platos(id);

-- Agragar datos complementarios de cliente
ALTER TABLE clientes
    ADD COLUMN nombre VARCHAR(100),
ADD COLUMN apellido VARCHAR(100),
ADD COLUMN celular VARCHAR(100);

-- Agragar datos complementarios de restaurante
ALTER TABLE restaurantes
    ADD COLUMN direccion VARCHAR(100),
ADD COLUMN celular VARCHAR(100);

-- Usuario Admin ---
INSERT INTO public.usuarios
(id, nombre, email, contrasena, tipo_usuario)
VALUES('f2320a65-5750-47e7-ae71-d4abd4d33b6c'::uuid, 'super admin', 'superadminrevealing@yopmail.com', '$2a$10$C2tAXhiADUiY6rs.K3FLwOK2xCwKmxRU1noize7CNSnFj8pu2RY0e', 'ADMIN');

INSERT INTO public.administradores
(id_usuario)
VALUES('f2320a65-5750-47e7-ae71-d4abd4d33b6c'::uuid);

CREATE TABLE imagenes (
                          id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                          nombre VARCHAR(255) NOT NULL,
                          datos LONGBLOB NOT NULL
);

ALTER TABLE restaurantes
    ADD COLUMN id_imagen UUID,
ADD FOREIGN KEY (id_imagen) REFERENCES imagenes(id);

ALTER TABLE clientes
    ADD COLUMN id_imagen UUID,
ADD FOREIGN KEY (id_imagen) REFERENCES imagenes(id);

ALTER TABLE platos
    ADD COLUMN id_imagen UUID,
ADD FOREIGN KEY (id_imagen) REFERENCES imagenes(id);


