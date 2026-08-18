-- Alta idempotente del consumidor SISS para el servicio SIGIE.
--
-- Uso:
--   1. Cambiar @ip_permitida por la IP real del servidor SISS del ambiente.
--   2. Solamente durante la primera alta, proporcionar en la sesion un hash
--      BCrypt generado fuera del repositorio en @secret_hash_bcrypt.
--   3. En ejecuciones posteriores el hash almacenado se conserva y solamente
--      se actualiza la IP permitida.
--
-- Nunca versionar el secreto en texto claro ni su valor real en este archivo.

SET @ip_permitida = '<IP_SERVIDOR_SISS>';
SET @secret_hash_bcrypt = '<BCRYPT_HASH_GENERADO_FUERA_DE_GIT>';

INSERT INTO tbl_clientes_api_sigie (
    client_id,
    secret_hash,
    scope,
    ips_permitidas,
    activo,
    vigencia_inicio,
    vigencia_fin
)
VALUES (
    'siss',
    @secret_hash_bcrypt,
    'sigie:estudiantes:read',
    @ip_permitida,
    1,
    NOW(),
    NULL
)
ON DUPLICATE KEY UPDATE
    scope = VALUES(scope),
    ips_permitidas = VALUES(ips_permitidas),
    activo = 1,
    fecha_revocacion = NULL;

-- Verificacion sin mostrar el hash almacenado.
SELECT
    client_id,
    scope,
    ips_permitidas,
    activo,
    vigencia_inicio,
    vigencia_fin
FROM tbl_clientes_api_sigie
WHERE client_id = 'siss';
