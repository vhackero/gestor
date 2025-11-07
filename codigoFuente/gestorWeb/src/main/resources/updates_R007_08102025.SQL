UPDATE tbl_funcionalidades
SET id_funcionalidad_padre = (
    SELECT id_funcionalidad
    FROM tbl_funcionalidades
    WHERE clave = 'GES_ESC'
)
WHERE clave = 'MIS_INSCRIPCIONES';