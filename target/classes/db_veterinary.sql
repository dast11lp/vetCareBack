-- =============================================
-- VETERINARY - Script completo
-- Borrar y recrear todo limpio
-- =============================================

DROP TABLE IF EXISTS cita CASCADE;
DROP TABLE IF EXISTS mascota CASCADE;
DROP TABLE IF EXISTS specialist CASCADE;
DROP TABLE IF EXISTS service_type CASCADE;
DROP TABLE IF EXISTS specialist_type CASCADE;
DROP TABLE IF EXISTS propietario CASCADE;
DROP TABLE IF EXISTS users_authorities CASCADE;
DROP TABLE IF EXISTS authorities CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- Base de usuarios
CREATE TABLE users (
    id_users     SERIAL PRIMARY KEY,
    email_users  TEXT UNIQUE,
    password_users TEXT,
    nombre_users TEXT,
    apellido_users TEXT,
    direccion_users TEXT,
    telefono_users TEXT
);

CREATE TABLE authorities (
    id_auth   SERIAL PRIMARY KEY,
    authority TEXT
);

CREATE TABLE users_authorities (
    id_users_auth SERIAL PRIMARY KEY,
    id_users INT,
    id_auth  INT,
    FOREIGN KEY (id_users) REFERENCES users(id_users),
    FOREIGN KEY (id_auth)  REFERENCES authorities(id_auth)
);

CREATE TABLE propietario (
    id_users INT PRIMARY KEY,
    FOREIGN KEY (id_users) REFERENCES users(id_users)
);

-- Tipos de especialista
CREATE TABLE specialist_type (
    id_spec_type       SERIAL PRIMARY KEY,
    nombre_spec_type   TEXT,
    descripcion_spec_type TEXT
);

-- Especialistas (reemplaza veterinarian)
CREATE TABLE specialist (
    id_users     INT PRIMARY KEY,
    id_spec_type INT,
    FOREIGN KEY (id_users)     REFERENCES users(id_users),
    FOREIGN KEY (id_spec_type) REFERENCES specialist_type(id_spec_type)
);

-- Tipos de servicio
CREATE TABLE service_type (
    id_serv_type          SERIAL PRIMARY KEY,
    nombre_serv_type      TEXT,
    descripcion_serv_type TEXT,
    duracion_min_serv_type INT,
    precio_base_serv_type  DOUBLE PRECISION,
    id_spec_type           INT,
    FOREIGN KEY (id_spec_type) REFERENCES specialist_type(id_spec_type)
);

-- Mascotas
CREATE TABLE mascota (
    id_mas    SERIAL PRIMARY KEY,
    nombre_mas TEXT,
    altura_mas DOUBLE PRECISION,
    peso_mas   DOUBLE PRECISION,
    especie_mas TEXT,
    raza_mas   TEXT,
    id_pro     INT,
    FOREIGN KEY (id_pro) REFERENCES users(id_users)
);

-- Citas
CREATE TABLE cita (
    id_cit           SERIAL PRIMARY KEY,
    fecha_cit        DATE,
    hora_cit         TIME,
    consultorio_cit  INT,
    monto_cit        DOUBLE PRECISION,
    procedimiento_cit TEXT,
    descripcion_cit  TEXT,
    receta_cit       TEXT,
    estado_cit       TEXT DEFAULT 'DISPONIBLE',
    id_specialist    INT,
    id_serv_type     INT,
    id_mas           INT,
    FOREIGN KEY (id_specialist) REFERENCES specialist(id_users),
    FOREIGN KEY (id_serv_type)  REFERENCES service_type(id_serv_type),
    FOREIGN KEY (id_mas)        REFERENCES mascota(id_mas)
);

-- =============================================
-- DATOS DE PRUEBA
-- =============================================

-- Tipos de especialista
INSERT INTO specialist_type (nombre_spec_type, descripcion_spec_type) VALUES
('Veterinario',          'Médico veterinario general'),
('Veterinario Cirujano', 'Especialista en cirugías'),
('Odontólogo Veterinario','Especialista en salud dental animal'),
('Groomer',              'Especialista en estética y peluquería animal');

-- Tipos de servicio
INSERT INTO service_type (nombre_serv_type, descripcion_serv_type, duracion_min_serv_type, precio_base_serv_type, id_spec_type) VALUES
('Consulta General',   'Revisión médica básica',                         30,  50000, 1),
('Vacunación',         'Aplicación de vacunas con registro',              20,  35000, 1),
('Desparasitación',    'Tratamiento interno y/o externo',                 20,  30000, 1),
('Odontología',        'Limpieza dental y revisión bucal',                60,  90000, 3),
('Cirugía',            'Procedimientos quirúrgicos con seguimiento',     120, 200000, 2),
('Laboratorio',        'Toma de muestras y análisis diagnóstico',         40,  70000, 1),
('Peluquería Básica',  'Baño, secado y cepillado',                        60,  45000, 4),
('Peluquería Completa','Baño, corte, limpieza de oídos y uñas',           90,  70000, 4);

-- Usuarios base (contraseña: 1234 — en prod van encriptadas con BCrypt)
INSERT INTO users (email_users, password_users, nombre_users, apellido_users, direccion_users, telefono_users) VALUES
('john.vet@vetcare.com',     'password', 'John',      'Smith',   'Calle 10 #5-20', '3001234567'),
('emily.vet@vetcare.com',    'password', 'Emily',     'Jones',   'Carrera 7 #12-5','3007654321'),
('carlos.cir@vetcare.com',   'password', 'Carlos',    'Ruiz',    'Av 19 #8-10',    '3009876543'),
('lucia.odon@vetcare.com',   'password', 'Lucía',     'Mora',    'Calle 80 #3-15', '3001112233'),
('pedro.groom@vetcare.com',  'password', 'Pedro',     'Arias',   'Cra 15 #22-11',  '3004445566'),
('owner1@mail.com',          'password', 'Valentina', 'Torres',  'Calle 45 #9-30', '3118889900'),
('owner2@mail.com',          'password', 'Miguel',    'Herrera', 'Cra 50 #18-7',   '3129990011');

-- Especialistas
INSERT INTO specialist (id_users, id_spec_type) VALUES
(1, 1), -- John: Veterinario
(2, 1), -- Emily: Veterinario
(3, 2), -- Carlos: Cirujano
(4, 3), -- Lucía: Odontólogo
(5, 4); -- Pedro: Groomer

-- Propietarios
INSERT INTO propietario (id_users) VALUES (6), (7);

-- Authorities
INSERT INTO authorities (authority) VALUES ('ROLE_USER'), ('ROLE_ADMIN'), ('ROLE_SPECIALIST');

-- Mascotas
INSERT INTO mascota (nombre_mas, altura_mas, peso_mas, especie_mas, raza_mas, id_pro) VALUES
('Mochi',  0.30, 5.2,  'Perro', 'Shih Tzu',   6),
('Luna',   0.45, 12.0, 'Perro', 'Labrador',   6),
('Miso',   0.25, 3.8,  'Gato',  'Siamés',     7);

-- Citas disponibles de prueba
INSERT INTO cita (fecha_cit, hora_cit, consultorio_cit, monto_cit, estado_cit, id_specialist, id_serv_type) VALUES
('2026-06-05', '09:00:00', 1, 50000, 'DISPONIBLE', 1, 1), -- Consulta General con John
('2026-06-05', '10:00:00', 1, 50000, 'DISPONIBLE', 2, 1), -- Consulta General con Emily
('2026-06-06', '09:00:00', 2, 35000, 'DISPONIBLE', 1, 2), -- Vacunación con John
('2026-06-07', '11:00:00', 3, 90000, 'DISPONIBLE', 4, 4), -- Odontología con Lucía
('2026-06-08', '14:00:00', 1, 45000, 'DISPONIBLE', 5, 7), -- Peluquería Básica con Pedro
('2026-06-09', '10:00:00', 2, 70000, 'DISPONIBLE', 5, 8), -- Peluquería Completa con Pedro
('2026-06-10', '09:00:00', 4, 70000, 'DISPONIBLE', 1, 6), -- Laboratorio con John
('2026-06-11', '15:00:00', 5,200000, 'DISPONIBLE', 3, 5); -- Cirugía con Carlos
