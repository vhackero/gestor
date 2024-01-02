-- Jorge Polo - 311223 
-- Ajustes necesarios para desarrollo de la funcionalidad de matriculación masiva
--
-- Database: des_sisi_gestor
-- ------------------------------------------------------

-- Modificar el tipo de dato de la columna tbl_inscripciones para que sea compatible con id_persona en tbl_persona
ALTER TABLE tbl_inscripciones
    MODIFY COLUMN IdpersonaSIGIE bigint;

-- Agregar claves foráneas en tbl_inscripciones 
    ALTER TABLE tbl_inscripciones
    ADD CONSTRAINT fk_IdpersonaSIGIE
    FOREIGN KEY (IdpersonaSIGIE) REFERENCES tbl_persona(id_persona),
    ADD CONSTRAINT fk_idplan
    FOREIGN KEY (idplan) REFERENCES tbl_planes(id_plan),
    ADD CONSTRAINT fk_idprograma
    FOREIGN KEY (idprograma) REFERENCES tbl_ficha_descriptiva_programa(id_programa),
    ADD CONSTRAINT fk_idevento
    FOREIGN KEY (idevento) REFERENCES tbl_eventos(id_evento);

-- Homologar la ruta taxonomica de los nombres de grupo de la tabla tbl_inscripcion_resumen con los de la columna groupbase en tbl_persona
UPDATE tbl_inscripcion_resumen
SET grupo = REPLACE(grupo, '24-01', '2401')
WHERE grupo LIKE '%24-01%';

-- Para encontrar todas las filas en la tabla tbl_inscripcion_resumen donde el segundo carácter después del tercer guion en la columna "grupo" no coincida con el valor "B" seguido del valor de la columna "bloque".
-- SELECT grupo, bloque
-- FROM tbl_inscripcion_resumen
-- WHERE SUBSTRING_INDEX(SUBSTRING_INDEX(grupo, '-', 4), '-', -1) != CONCAT('B', bloque);

-- Para actualizar las filas donde para que el segundo carácter después del tercer guion sea "B" seguido del valor de la columna "bloque". 
UPDATE tbl_inscripcion_resumen
SET grupo = CONCAT(
        SUBSTRING_INDEX(grupo, '-', 3),
        '-',
        CONCAT('B', bloque),
        '-',
        SUBSTRING_INDEX(grupo, '-', -1)
            )
WHERE SUBSTRING_INDEX(SUBSTRING_INDEX(grupo, '-', 4), '-', -1) != CONCAT('B', bloque);


