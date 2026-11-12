-- P1-10. Ejecutar antes de desplegar. No reclasifica historial ni altera calificaciones.
CREATE TABLE IF NOT EXISTS rel_inscripcion_optativa (
 id_inscripcion INT(11) NOT NULL,
 caracter VARCHAR(10) NOT NULL,
 id_adeudo_origen INT(11) NULL,
 id_inscripcion_anterior INT(11) NULL,
 id_proceso INT(11) NOT NULL,
 optativas_requeridas_acreditadas INT(11) NOT NULL,
 fecha_registro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 PRIMARY KEY (id_inscripcion),
 UNIQUE KEY uq_optativa_siguiente (id_inscripcion_anterior),
 KEY ix_optativa_origen (id_adeudo_origen),
 CONSTRAINT fk_optativa_inscripcion FOREIGN KEY (id_inscripcion) REFERENCES tbl_inscripciones(id),
 CONSTRAINT fk_optativa_origen FOREIGN KEY (id_adeudo_origen) REFERENCES rel_inscripcion_optativa(id_inscripcion),
 CONSTRAINT fk_optativa_anterior FOREIGN KEY (id_inscripcion_anterior) REFERENCES rel_inscripcion_optativa(id_inscripcion)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
