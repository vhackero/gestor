-- Textos para el modulo de altas de usuarios

-- Texto panel
INSERT INTO des_sisi_gestor.tbl_textos_sistema
            (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
    SELECT 'gw.gestionescolar.altasbajas.nuevaBaja.etiquetaPanel.importarDatosFuenteExt',
            'Importar datos de fuente externa',
            f.id_funcionalidad,
            NOW(),
            NOW(),
            2
    FROM des_sisi_gestor.tbl_funcionalidades f
    WHERE f.clave = 'ALT_BAJ_USR';

-- Fuente externa
INSERT INTO des_sisi_gestor.tbl_textos_sistema
            (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
     SELECT 'gw.gestionescolar.altasbajas.etiqueta.fuenteExterna',
            'Fuente externa',
            f.id_funcionalidad,
            NOW(),
            NOW(),
            2
     FROM des_sisi_gestor.tbl_funcionalidades f
     WHERE f.clave = 'ALT_BAJ_USR';

-- Grupo
INSERT INTO des_sisi_gestor.tbl_textos_sistema
            (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
       SELECT 'gw.gestionescolar.altasbajas.etiqueta.grupo',
              'Grupo',
               f.id_funcionalidad,
               NOW(),
               NOW(),
               2
       FROM des_sisi_gestor.tbl_funcionalidades f
       WHERE f.clave = 'ALT_BAJ_USR';

-- Botón Importar
INSERT INTO des_sisi_gestor.tbl_textos_sistema
            (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
       SELECT 'gw.gestionescolar.altasbajas.btn.importar',
              'Importar',
              f.id_funcionalidad,
              NOW(),
              NOW(),
              2
       FROM des_sisi_gestor.tbl_funcionalidades f
       WHERE f.clave = 'ALT_BAJ_USR';