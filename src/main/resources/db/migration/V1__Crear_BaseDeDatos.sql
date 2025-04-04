-- Crear tabla Persona
CREATE TABLE Persona (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    identificacion BIGINT NOT NULL UNIQUE,
    pnombre VARCHAR(255) NOT NULL,
    snombre VARCHAR(255),
    papellido VARCHAR(255) NOT NULL,
    sapellido VARCHAR(255),
    email VARCHAR(255) NOT NULL UNIQUE,
    fechanacimiento DATE NOT NULL,
    edad INT,
    edadclinica VARCHAR(255)
);

-- Crear tabla Usuario
CREATE TABLE Usuario (
    id BIGINT NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    apikey VARCHAR(255),
    PRIMARY KEY (id, username),
    CONSTRAINT fk_usuario_persona FOREIGN KEY (id) REFERENCES Persona(id) ON DELETE CASCADE
);

-- Trigger para calcular edad antes de insertar
CREATE TRIGGER before_insert_persona
    BEFORE INSERT ON Persona
    FOR EACH ROW
BEGIN
    SET NEW.edad = TIMESTAMPDIFF(YEAR, NEW.fechanacimiento, CURDATE());
    SET NEW.edadclinica = CONCAT(
            TIMESTAMPDIFF(YEAR, NEW.fechanacimiento, CURDATE()), ' años ',
            TIMESTAMPDIFF(MONTH, NEW.fechanacimiento, CURDATE()) % 12, ' meses ',
            DATEDIFF(CURDATE(), DATE_ADD(NEW.fechanacimiento, INTERVAL TIMESTAMPDIFF(YEAR, NEW.fechanacimiento, CURDATE()) YEAR)) % 30, ' días'
                          );
END;

-- Trigger para actualizar edad antes de un UPDATE
CREATE TRIGGER before_update_persona
    BEFORE UPDATE ON Persona
    FOR EACH ROW
BEGIN
    SET NEW.edad = TIMESTAMPDIFF(YEAR, NEW.fechanacimiento, CURDATE());
    SET NEW.edadclinica = CONCAT(
            TIMESTAMPDIFF(YEAR, NEW.fechanacimiento, CURDATE()), ' años ',
            TIMESTAMPDIFF(MONTH, NEW.fechanacimiento, CURDATE()) % 12, ' meses ',
            DATEDIFF(CURDATE(), DATE_ADD(NEW.fechanacimiento, INTERVAL TIMESTAMPDIFF(YEAR, NEW.fechanacimiento, CURDATE()) YEAR)) % 30, ' días'
                          );
END;

