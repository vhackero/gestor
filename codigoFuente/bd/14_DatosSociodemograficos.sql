-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 28-12-2023 a las 20:19:13
-- Versión del servidor: 10.4.13-MariaDB
-- Versión de PHP: 7.4.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `des_sisi_gestor`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cat_discapacidades`
--

CREATE TABLE `cat_discapacidades` (
  `id_discapacidad` int(11) NOT NULL,
  `discapacidad` varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  `descripcion` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `fecha_registro` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `fecha_modificacion` timestamp NOT NULL DEFAULT current_timestamp(),
  `usuario_modifico` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `cat_discapacidades`
--

INSERT INTO `cat_discapacidades` (`id_discapacidad`, `discapacidad`, `descripcion`, `fecha_registro`, `fecha_modificacion`, `usuario_modifico`) VALUES
(1, 'Discapacidad motriz', 'Discapacidad motriz', '2023-12-29 01:09:17', '2023-12-27 01:42:20', 2),
(2, 'Discapacidad sensorial y de la comunicación', 'Discapacidad sensorial y de la comunicación', '2023-12-27 04:20:08', '2023-12-27 04:20:08', 2),
(3, 'Discapacidad mental', 'Discapacidad mental', '2023-12-27 04:20:08', '2023-12-27 04:20:08', 2),
(4, 'Discapacidades múltiples u otras', 'Discapacidades múltiples u otras', '2023-12-29 01:01:21', '2023-12-29 01:01:21', 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cat_lenguajes_indigena`
--

CREATE TABLE `cat_lenguajes_indigena` (
  `id_lenguaje` int(11) NOT NULL,
  `lenguaje` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `descripcion` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `fecha_registro` timestamp NOT NULL DEFAULT current_timestamp(),
  `fecha_modificacion` timestamp NOT NULL DEFAULT current_timestamp(),
  `usuario_modifico` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cat_tipo_discapacidades`
--

CREATE TABLE `cat_tipo_discapacidades` (
  `id_tipo_discapacidad` int(11) NOT NULL,
  `id_discapacidad` int(11) NOT NULL,
  `tipo_discapacidad` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `descripcion` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `fecha_registro` timestamp NOT NULL DEFAULT current_timestamp(),
  `fecha_modificacion` timestamp NOT NULL DEFAULT current_timestamp(),
  `usuario_modifico` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `cat_tipo_discapacidades`
--

INSERT INTO `cat_tipo_discapacidades` (`id_tipo_discapacidad`, `id_discapacidad`, `tipo_discapacidad`, `descripcion`, `fecha_registro`, `fecha_modificacion`, `usuario_modifico`) VALUES
(1, 1, 'De las extremidades inferiores', 'De las extremidades inferiores', '2023-12-27 01:43:24', '2023-12-27 01:43:24', 2),
(2, 1, 'Del tronco, cuello y cabeza', 'Del tronco, cuello y cabeza', '2023-12-27 01:43:24', '2023-12-27 01:43:24', 2),
(3, 1, 'De las extremidades superiores', 'De las extremidades superiores', '2023-12-27 01:44:36', '2023-12-27 01:44:36', 2),
(4, 2, 'Para ver', 'Para ver', '2023-12-27 04:23:55', '2023-12-27 04:23:55', 2),
(5, 2, 'Ceguera', 'Ceguera', '2023-12-29 01:02:31', '2023-12-29 01:02:31', 2),
(6, 2, 'Para Oir', 'Para Oir', '2023-12-29 01:02:31', '2023-12-29 01:02:31', 2),
(7, 2, 'Sordera', 'Sordera', '2023-12-29 01:02:31', '2023-12-29 01:02:31', 2),
(8, 2, 'Mudez', 'Mudez', '2023-12-29 01:02:31', '2023-12-29 01:02:31', 2),
(9, 2, 'Para comunicarse o comprender el lenguaje ', 'Para comunicarse o comprender el lenguaje ', '2023-12-29 01:06:43', '2023-12-29 01:06:43', 2),
(10, 3, 'Intelectual ', 'Intelectual ', '2023-12-29 01:07:38', '2023-12-29 01:07:38', 2),
(11, 3, 'Conductuales y otras mentales ', 'Conductuales y otras mentales ', '2023-12-29 01:07:38', '2023-12-29 01:07:38', 2),
(12, 4, 'Múltiples u otras', 'Múltiples u otras', '2023-12-29 01:08:06', '2023-12-29 01:08:06', 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tbl_datos_sociodemograficos_persona`
--

CREATE TABLE `tbl_datos_sociodemograficos_persona` (
  `id_dato_sociodemografico_persona` bigint(20) NOT NULL,
  `id_persona` bigint(20) NOT NULL,
  `lengua_indigena` tinyint(1) NOT NULL,
  `id_lenguaje` int(11) DEFAULT NULL,
  `tiene_discapacidad` tinyint(1) DEFAULT NULL,
  `id_tipo_discapacidad` int(11) DEFAULT NULL,
  `fecha_registro` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `fecha_modificacion` timestamp NOT NULL DEFAULT current_timestamp(),
  `usuario_modifico` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cat_discapacidades`
--
ALTER TABLE `cat_discapacidades`
  ADD PRIMARY KEY (`id_discapacidad`);

--
-- Indices de la tabla `cat_lenguajes_indigena`
--
ALTER TABLE `cat_lenguajes_indigena`
  ADD PRIMARY KEY (`id_lenguaje`);

--
-- Indices de la tabla `cat_tipo_discapacidades`
--
ALTER TABLE `cat_tipo_discapacidades`
  ADD PRIMARY KEY (`id_tipo_discapacidad`),
  ADD KEY `id_discapacidad` (`id_discapacidad`);

--
-- Indices de la tabla `tbl_datos_sociodemograficos_persona`
--
ALTER TABLE `tbl_datos_sociodemograficos_persona`
  ADD PRIMARY KEY (`id_dato_sociodemografico_persona`),
  ADD KEY `id_persona_datos_sociodemograficos` (`id_persona`) USING BTREE,
  ADD KEY `id_tipo_discapacidad_persona` (`id_tipo_discapacidad`) USING BTREE,
  ADD KEY `fk_datos_sociodemograficos_lenguaje` (`id_lenguaje`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cat_discapacidades`
--
ALTER TABLE `cat_discapacidades`
  MODIFY `id_discapacidad` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `cat_lenguajes_indigena`
--
ALTER TABLE `cat_lenguajes_indigena`
  MODIFY `id_lenguaje` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `cat_tipo_discapacidades`
--
ALTER TABLE `cat_tipo_discapacidades`
  MODIFY `id_tipo_discapacidad` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `tbl_datos_sociodemograficos_persona`
--
ALTER TABLE `tbl_datos_sociodemograficos_persona`
  MODIFY `id_dato_sociodemografico_persona` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `cat_lenguajes_indigena`
--
ALTER TABLE `cat_lenguajes_indigena`
  ADD CONSTRAINT `cat_lenguajes_indigena_ibfk_1` FOREIGN KEY (`id_lenguaje`) REFERENCES `tbl_datos_sociodemograficos_persona` (`id_lenguaje`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `cat_tipo_discapacidades`
--
ALTER TABLE `cat_tipo_discapacidades`
  ADD CONSTRAINT `cat_tipo_discapacidades_ibfk_1` FOREIGN KEY (`id_discapacidad`) REFERENCES `cat_discapacidades` (`id_discapacidad`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `tbl_datos_sociodemograficos_persona`
--
ALTER TABLE `tbl_datos_sociodemograficos_persona`
  ADD CONSTRAINT `fk_datos_sociodemograficos_lenguaje` FOREIGN KEY (`id_lenguaje`) REFERENCES `cat_lenguajes_indigena` (`id_lenguaje`),
  ADD CONSTRAINT `fk_datos_sociodemograficos_lenguaje_indigena` FOREIGN KEY (`id_lenguaje`) REFERENCES `cat_lenguajes_indigena` (`id_lenguaje`),
  ADD CONSTRAINT `tbl_datos_sociodemograficos_persona_ibfk_1` FOREIGN KEY (`id_tipo_discapacidad`) REFERENCES `cat_tipo_discapacidades` (`id_tipo_discapacidad`) ON UPDATE CASCADE,
  ADD CONSTRAINT `tbl_datos_sociodemograficos_persona_ibfk_3` FOREIGN KEY (`id_persona`) REFERENCES `tbl_persona` (`id_persona`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
