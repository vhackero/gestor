UPDATE tbl_textos_sistema
SET valor = REPLACE(valor, 'evento de capacitación', 'evento académico')
WHERE valor LIKE '%evento de capacitación%';
UPDATE tbl_textos_sistema
SET valor = REPLACE(valor, 'Utiliza la FEC de referencia', 'Utiliza tu planificación de referencia')
WHERE valor LIKE '%Utiliza la FEC de referencia%';
UPDATE tbl_textos_sistema
SET valor = 'Nivel de estudios'
WHERE valor LIKE 'Nivel de enseñanza';
UPDATE tbl_textos_sistema
SET valor = 'Nuevo evento de capacitacion'
WHERE valor LIKE 'Nuevo evento académico';