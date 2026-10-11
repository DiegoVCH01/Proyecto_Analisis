-- ==========================================================
-- BASE DE DATOS
-- Sistema Web de Gestión de Reclutamiento y Selección
-- de Talento TI
-- ==========================================================

DROP DATABASE IF EXISTS gestion_talento_ti;

CREATE DATABASE gestion_talento_ti
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE gestion_talento_ti;


-- ==========================================================
-- 1. USUARIO
-- Almacena los datos generales de acceso.
-- Puede representar a un CANDIDATO o RECLUTADOR.
-- ==========================================================

CREATE TABLE usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,

    nombres VARCHAR(80) NOT NULL,
    apellidos VARCHAR(80) NOT NULL,
    correo VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,

    rol VARCHAR(20) NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVO',

    fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_usuario_rol
        CHECK (rol IN ('CANDIDATO', 'RECLUTADOR')),

    CONSTRAINT chk_usuario_estado
        CHECK (estado IN ('ACTIVO', 'INACTIVO'))
);


-- ==========================================================
-- 2. PERFIL CANDIDATO
-- Datos profesionales que únicamente pertenecen
-- a usuarios con rol CANDIDATO.
-- Relación 1 a 1 con usuario.
-- ==========================================================

CREATE TABLE perfil_candidato (
    id_usuario INT PRIMARY KEY,

    telefono VARCHAR(20),
    linkedin_url VARCHAR(255),
    github_url VARCHAR(255),

    resumen_profesional TEXT,

    anios_experiencia INT NOT NULL DEFAULT 0,

    CONSTRAINT chk_perfil_experiencia
        CHECK (anios_experiencia >= 0),

    CONSTRAINT fk_perfil_usuario
        FOREIGN KEY (id_usuario)
        REFERENCES usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);


-- ==========================================================
-- 3. VACANTE
-- Vacantes registradas y gestionadas por los reclutadores.
-- ==========================================================

CREATE TABLE vacante (
    id_vacante INT AUTO_INCREMENT PRIMARY KEY,

    id_reclutador INT NOT NULL,

    titulo VARCHAR(150) NOT NULL,
    descripcion TEXT NOT NULL,

    area VARCHAR(100),
    ubicacion VARCHAR(150),

    modalidad VARCHAR(20) NOT NULL,

    estado VARCHAR(20) NOT NULL DEFAULT 'BORRADOR',

    fecha_publicacion DATE,
    fecha_cierre DATE,

    fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_vacante_modalidad
        CHECK (
            modalidad IN (
                'PRESENCIAL',
                'REMOTO',
                'HIBRIDO'
            )
        ),

    CONSTRAINT chk_vacante_estado
        CHECK (
            estado IN (
                'BORRADOR',
                'PUBLICADA',
                'CERRADA',
                'CANCELADA'
            )
        ),

    CONSTRAINT chk_vacante_fechas
        CHECK (
            fecha_cierre IS NULL
            OR fecha_publicacion IS NULL
            OR fecha_cierre >= fecha_publicacion
        ),

    CONSTRAINT fk_vacante_reclutador
        FOREIGN KEY (id_reclutador)
        REFERENCES usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);


-- ==========================================================
-- 4. CRITERIO VACANTE
-- Requisitos utilizados durante la preselección.
-- Una vacante puede poseer varios criterios.
-- ==========================================================

CREATE TABLE criterio_vacante (
    id_criterio INT AUTO_INCREMENT PRIMARY KEY,

    id_vacante INT NOT NULL,

    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(300),

    obligatorio BOOLEAN NOT NULL DEFAULT TRUE,

    peso DECIMAL(5,2),

    CONSTRAINT chk_criterio_peso
        CHECK (
            peso IS NULL
            OR (peso >= 0 AND peso <= 100)
        ),

    CONSTRAINT fk_criterio_vacante
        FOREIGN KEY (id_vacante)
        REFERENCES vacante(id_vacante)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);


-- ==========================================================
-- 5. CURRICULUM VITAE
-- Un candidato puede registrar más de un CV.
-- ==========================================================

CREATE TABLE curriculum_vitae (
    id_curriculum INT AUTO_INCREMENT PRIMARY KEY,

    id_candidato INT NOT NULL,

    nombre_archivo VARCHAR(200) NOT NULL,
    archivo_url VARCHAR(500) NOT NULL,

    fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVO',

    CONSTRAINT chk_curriculum_estado
        CHECK (
            estado IN (
                'ACTIVO',
                'INACTIVO'
            )
        ),

    CONSTRAINT fk_curriculum_candidato
        FOREIGN KEY (id_candidato)
        REFERENCES usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);


-- ==========================================================
-- 6. ESTADO POSTULACION
-- Catálogo de estados posibles.
-- Se separa para no repetir textos y facilitar trazabilidad.
-- ==========================================================

CREATE TABLE estado_postulacion (
    id_estado INT AUTO_INCREMENT PRIMARY KEY,

    nombre VARCHAR(50) NOT NULL UNIQUE,

    descripcion VARCHAR(250)
);


-- ==========================================================
-- 7. POSTULACION
-- Relaciona al candidato con la vacante.
-- El candidato selecciona qué CV utilizar.
-- ==========================================================

CREATE TABLE postulacion (
    id_postulacion INT AUTO_INCREMENT PRIMARY KEY,

    id_candidato INT NOT NULL,
    id_vacante INT NOT NULL,
    id_curriculum INT NOT NULL,
    id_estado INT NOT NULL,

    fecha_postulacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    fecha_actualizacion DATETIME
        NOT NULL
        DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT uq_postulacion_candidato_vacante
        UNIQUE (id_candidato, id_vacante),

    CONSTRAINT fk_postulacion_candidato
        FOREIGN KEY (id_candidato)
        REFERENCES usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT fk_postulacion_vacante
        FOREIGN KEY (id_vacante)
        REFERENCES vacante(id_vacante)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT fk_postulacion_curriculum
        FOREIGN KEY (id_curriculum)
        REFERENCES curriculum_vitae(id_curriculum)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT fk_postulacion_estado
        FOREIGN KEY (id_estado)
        REFERENCES estado_postulacion(id_estado)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);


-- ==========================================================
-- 8. HISTORIAL ESTADO POSTULACION
-- Mantiene la trazabilidad del proceso.
-- Ejemplo:
-- RECIBIDA -> EN REVISION -> PRESELECCIONADO -> ENTREVISTA
-- ==========================================================

CREATE TABLE historial_estado_postulacion (
    id_historial INT AUTO_INCREMENT PRIMARY KEY,

    id_postulacion INT NOT NULL,
    id_estado INT NOT NULL,

    id_usuario_cambio INT,

    fecha_cambio DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    observacion VARCHAR(500),

    CONSTRAINT fk_historial_postulacion
        FOREIGN KEY (id_postulacion)
        REFERENCES postulacion(id_postulacion)
        ON UPDATE CASCADE
        ON DELETE CASCADE,

    CONSTRAINT fk_historial_estado
        FOREIGN KEY (id_estado)
        REFERENCES estado_postulacion(id_estado)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT fk_historial_usuario
        FOREIGN KEY (id_usuario_cambio)
        REFERENCES usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE SET NULL
);


-- ==========================================================
-- 9. EVALUACION PRESELECCION
-- Resultado general de la revisión inicial
-- de una postulación.
-- ==========================================================

CREATE TABLE evaluacion_preseleccion (
    id_evaluacion INT AUTO_INCREMENT PRIMARY KEY,

    id_postulacion INT NOT NULL,
    id_reclutador INT NOT NULL,

    fecha_evaluacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    puntaje_total DECIMAL(5,2),

    resultado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE',

    observaciones TEXT,

    CONSTRAINT uq_evaluacion_postulacion
        UNIQUE (id_postulacion),

    CONSTRAINT chk_evaluacion_puntaje
        CHECK (
            puntaje_total IS NULL
            OR (puntaje_total >= 0 AND puntaje_total <= 100)
        ),

    CONSTRAINT chk_evaluacion_resultado
        CHECK (
            resultado IN (
                'PENDIENTE',
                'PRESELECCIONADO',
                'DESCARTADO'
            )
        ),

    CONSTRAINT fk_evaluacion_postulacion
        FOREIGN KEY (id_postulacion)
        REFERENCES postulacion(id_postulacion)
        ON UPDATE CASCADE
        ON DELETE CASCADE,

    CONSTRAINT fk_evaluacion_reclutador
        FOREIGN KEY (id_reclutador)
        REFERENCES usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);


-- ==========================================================
-- 10. DETALLE EVALUACION
-- Detalla cómo fue evaluado cada criterio de la vacante.
-- ==========================================================

CREATE TABLE detalle_evaluacion (
    id_detalle INT AUTO_INCREMENT PRIMARY KEY,

    id_evaluacion INT NOT NULL,
    id_criterio INT NOT NULL,

    cumple BOOLEAN NOT NULL,

    puntaje DECIMAL(5,2),

    observacion VARCHAR(500),

    CONSTRAINT uq_detalle_evaluacion_criterio
        UNIQUE (id_evaluacion, id_criterio),

    CONSTRAINT chk_detalle_puntaje
        CHECK (
            puntaje IS NULL
            OR (puntaje >= 0 AND puntaje <= 100)
        ),

    CONSTRAINT fk_detalle_evaluacion
        FOREIGN KEY (id_evaluacion)
        REFERENCES evaluacion_preseleccion(id_evaluacion)
        ON UPDATE CASCADE
        ON DELETE CASCADE,

    CONSTRAINT fk_detalle_criterio
        FOREIGN KEY (id_criterio)
        REFERENCES criterio_vacante(id_criterio)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);


-- ==========================================================
-- 11. ENTREVISTA
-- Una postulación puede tener una o varias entrevistas.
-- ==========================================================

CREATE TABLE entrevista (
    id_entrevista INT AUTO_INCREMENT PRIMARY KEY,

    id_postulacion INT NOT NULL,
    id_reclutador INT NOT NULL,

    tipo VARCHAR(30) NOT NULL DEFAULT 'GENERAL',

    fecha_hora DATETIME NOT NULL,

    modalidad VARCHAR(20) NOT NULL,

    enlace_reunion VARCHAR(500),

    estado VARCHAR(20) NOT NULL DEFAULT 'PROGRAMADA',

    observaciones TEXT,

    CONSTRAINT chk_entrevista_tipo
        CHECK (
            tipo IN (
                'GENERAL',
                'RRHH',
                'TECNICA',
                'FINAL'
            )
        ),

    CONSTRAINT chk_entrevista_modalidad
        CHECK (
            modalidad IN (
                'VIRTUAL',
                'PRESENCIAL'
            )
        ),

    CONSTRAINT chk_entrevista_estado
        CHECK (
            estado IN (
                'PROGRAMADA',
                'REALIZADA',
                'REPROGRAMADA',
                'CANCELADA'
            )
        ),

    CONSTRAINT fk_entrevista_postulacion
        FOREIGN KEY (id_postulacion)
        REFERENCES postulacion(id_postulacion)
        ON UPDATE CASCADE
        ON DELETE CASCADE,

    CONSTRAINT fk_entrevista_reclutador
        FOREIGN KEY (id_reclutador)
        REFERENCES usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);


-- ==========================================================
-- 12. RESULTADO SELECCION
-- Representa la decisión final sobre la postulación.
-- Se separa de entrevista porque puede existir más de
-- una entrevista antes de tomar una decisión.
-- ==========================================================

CREATE TABLE resultado_seleccion (
    id_resultado INT AUTO_INCREMENT PRIMARY KEY,

    id_postulacion INT NOT NULL,
    id_reclutador INT NOT NULL,

    resultado VARCHAR(30) NOT NULL,

    fecha_resultado DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    observaciones TEXT,

    CONSTRAINT uq_resultado_postulacion
        UNIQUE (id_postulacion),

    CONSTRAINT chk_resultado_seleccion
        CHECK (
            resultado IN (
                'SELECCIONADO',
                'NO_SELECCIONADO',
                'PENDIENTE'
            )
        ),

    CONSTRAINT fk_resultado_postulacion
        FOREIGN KEY (id_postulacion)
        REFERENCES postulacion(id_postulacion)
        ON UPDATE CASCADE
        ON DELETE CASCADE,

    CONSTRAINT fk_resultado_reclutador
        FOREIGN KEY (id_reclutador)
        REFERENCES usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);


-- ==========================================================
-- 13. NOTIFICACION
-- Historial de comunicaciones al candidato.
-- ==========================================================

CREATE TABLE notificacion (
    id_notificacion INT AUTO_INCREMENT PRIMARY KEY,

    id_usuario INT NOT NULL,

    id_postulacion INT,

    tipo VARCHAR(30) NOT NULL,

    asunto VARCHAR(150) NOT NULL,

    mensaje TEXT NOT NULL,

    canal VARCHAR(20) NOT NULL DEFAULT 'SISTEMA',

    estado_envio VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE',

    fecha_envio DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_notificacion_tipo
        CHECK (
            tipo IN (
                'POSTULACION',
                'PRESELECCION',
                'ENTREVISTA',
                'RESULTADO'
            )
        ),

    CONSTRAINT chk_notificacion_canal
        CHECK (
            canal IN (
                'SISTEMA',
                'CORREO'
            )
        ),

    CONSTRAINT chk_notificacion_estado
        CHECK (
            estado_envio IN (
                'PENDIENTE',
                'ENVIADA',
                'ERROR'
            )
        ),

    CONSTRAINT fk_notificacion_usuario
        FOREIGN KEY (id_usuario)
        REFERENCES usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT fk_notificacion_postulacion
        FOREIGN KEY (id_postulacion)
        REFERENCES postulacion(id_postulacion)
        ON UPDATE CASCADE
        ON DELETE SET NULL
);


-- ==========================================================
-- DATOS INICIALES DEL CATÁLOGO DE ESTADOS
-- ==========================================================

INSERT INTO estado_postulacion (nombre, descripcion)
VALUES
('RECIBIDA',
 'La postulación fue registrada correctamente.'),

('EN_REVISION',
 'La postulación se encuentra en proceso de revisión.'),

('PRESELECCIONADO',
 'El candidato cumple los criterios iniciales.'),

('DESCARTADO',
 'El candidato no continúa en el proceso.'),

('EN_ENTREVISTA',
 'El candidato se encuentra en etapa de entrevista.'),

('SELECCIONADO',
 'El candidato fue seleccionado para la vacante.'),

('NO_SELECCIONADO',
 'El candidato finalizó el proceso sin ser seleccionado.');


-- ==========================================================
-- ÍNDICES
-- ==========================================================

CREATE INDEX idx_usuario_correo
ON usuario(correo);

CREATE INDEX idx_vacante_estado
ON vacante(estado);

CREATE INDEX idx_vacante_reclutador
ON vacante(id_reclutador);

CREATE INDEX idx_postulacion_candidato
ON postulacion(id_candidato);

CREATE INDEX idx_postulacion_vacante
ON postulacion(id_vacante);

CREATE INDEX idx_postulacion_estado
ON postulacion(id_estado);

CREATE INDEX idx_historial_postulacion
ON historial_estado_postulacion(id_postulacion);

CREATE INDEX idx_entrevista_postulacion
ON entrevista(id_postulacion);

CREATE INDEX idx_entrevista_fecha
ON entrevista(fecha_hora);

CREATE INDEX idx_notificacion_usuario
ON notificacion(id_usuario);