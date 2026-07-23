# Propuesta Técnica SIGIE 2.0

## Asistente Curricular, Malla Curricular y Expediente

Fecha: 16 de julio de 2026

## 1. Objetivo

Definir la base técnica para evolucionar el ecosistema de:

- Expediente académico
- Malla curricular
- Asistente curricular

hacia una versión 2.0 centrada en:

- diagnóstico académico-operativo
- clasificación de casos reales
- base de conocimiento reutilizable
- soporte resolutivo para gestor académico
- orientación contextual para estudiante
- trazabilidad institucional de decisiones

La propuesta está alineada a:

- [Propuesta Funcional_Asistente_Curricular_SIGIE_v2_150726.docx](/home/abelinux/gestor/repo/gestor/Propuesta%20Funcional_Asistente_Curricular_SIGIE_v2_150726.docx)
- [casos_avance_anual_2026_2.xlsx](/home/abelinux/gestor/repo/gestor/casos_avance_anual_2026_2.xlsx)

## 2. Decisión de arquitectura

La evolución 2.0 no debe resolverse agregando más lógica a los beans actuales.

La estrategia correcta es introducir un núcleo nuevo de dominio:

- `Caso académico-operativo`
- `Diagnóstico académico-operativo`
- `Base de conocimiento`
- `Bitácora de atención`

Las vistas existentes deben convertirse en consumidoras de este núcleo.

## 3. Componentes nuevos

### 3.1 Núcleo de caso

Responsabilidades:

- clasificar el caso
- determinar expediente mínimo
- generar diagnóstico
- detectar causa dominante
- proponer viabilidad técnica
- sugerir mensaje institucional
- preparar trazabilidad y escalamiento

### 3.2 Base de conocimiento

Responsabilidades:

- modelar patrones derivados del Excel
- asociar criterios operativos
- sugerir mensajes
- sugerir acciones
- buscar casos similares

### 3.3 Capa de presentación

Consumidores principales:

- expediente
- malla
- asistente

Con variantes por:

- estudiante / gestor
- inscripción / cursamiento

## 4. Modelo de datos propuesto

## 4.1 Catálogos de conocimiento

### `cat_tipo_caso_academico`

```sql
CREATE TABLE cat_tipo_caso_academico (
    id_tipo_caso BIGINT NOT NULL AUTO_INCREMENT,
    clave VARCHAR(80) NOT NULL,
    nombre VARCHAR(200) NOT NULL,
    descripcion VARCHAR(1000) NULL,
    activo BIT NOT NULL DEFAULT b'1',
    fecha_registro DATETIME NOT NULL,
    fecha_actualizacion DATETIME NULL,
    usuario_modifico BIGINT NULL,
    PRIMARY KEY (id_tipo_caso),
    UNIQUE KEY uk_cat_tipo_caso_academico_clave (clave)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

Ejemplos de `clave`:

- `AVANCE_ANUAL`
- `ACLARACION_AVANCE`
- `CASO_REZAGO`
- `UNIDAD_DIDACTICA_PENDIENTE`
- `REINSCRIPCION_UNIDAD_NO_APROBADA`
- `OFERTA_NO_DISPONIBLE`
- `SERIACION`
- `OMISION_REGISTRO`
- `PENDIENTE_INFORMACION`
- `NO_OPERABLE`

### `cat_motivo_restriccion`

```sql
CREATE TABLE cat_motivo_restriccion (
    id_motivo_restriccion BIGINT NOT NULL AUTO_INCREMENT,
    clave VARCHAR(80) NOT NULL,
    nombre VARCHAR(200) NOT NULL,
    descripcion VARCHAR(1000) NULL,
    prioridad INT NOT NULL DEFAULT 0,
    activo BIT NOT NULL DEFAULT b'1',
    fecha_registro DATETIME NOT NULL,
    fecha_actualizacion DATETIME NULL,
    usuario_modifico BIGINT NULL,
    PRIMARY KEY (id_motivo_restriccion),
    UNIQUE KEY uk_cat_motivo_restriccion_clave (clave)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

Ejemplos:

- `BLOQUEO_AVANCE_ANUAL`
- `CARGA_LIMITADA`
- `ACUMULACION_PENDIENTES`
- `SERIACION_ACTIVA`
- `OFERTA_NO_VIGENTE`
- `OMISION_REGISTRO`
- `NO_ACREDITACION_PREVIA`
- `BAJA_TEMPORAL`
- `DATOS_INSUFICIENTES`

### `cat_criterio_operativo`

```sql
CREATE TABLE cat_criterio_operativo (
    id_criterio_operativo BIGINT NOT NULL AUTO_INCREMENT,
    clave VARCHAR(80) NOT NULL,
    nombre VARCHAR(250) NOT NULL,
    descripcion VARCHAR(2000) NOT NULL,
    prioridad INT NOT NULL DEFAULT 0,
    activo BIT NOT NULL DEFAULT b'1',
    fecha_registro DATETIME NOT NULL,
    fecha_actualizacion DATETIME NULL,
    usuario_modifico BIGINT NULL,
    PRIMARY KEY (id_criterio_operativo),
    UNIQUE KEY uk_cat_criterio_operativo_clave (clave)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

### `cat_accion_operativa`

```sql
CREATE TABLE cat_accion_operativa (
    id_accion_operativa BIGINT NOT NULL AUTO_INCREMENT,
    clave VARCHAR(80) NOT NULL,
    nombre VARCHAR(200) NOT NULL,
    descripcion VARCHAR(1000) NULL,
    requiere_intervencion_humana BIT NOT NULL DEFAULT b'0',
    activo BIT NOT NULL DEFAULT b'1',
    fecha_registro DATETIME NOT NULL,
    fecha_actualizacion DATETIME NULL,
    usuario_modifico BIGINT NULL,
    PRIMARY KEY (id_accion_operativa),
    UNIQUE KEY uk_cat_accion_operativa_clave (clave)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

### `cat_viabilidad_tecnica`

```sql
CREATE TABLE cat_viabilidad_tecnica (
    id_viabilidad_tecnica BIGINT NOT NULL AUTO_INCREMENT,
    clave VARCHAR(80) NOT NULL,
    nombre VARCHAR(200) NOT NULL,
    descripcion VARCHAR(1000) NULL,
    activo BIT NOT NULL DEFAULT b'1',
    fecha_registro DATETIME NOT NULL,
    fecha_actualizacion DATETIME NULL,
    usuario_modifico BIGINT NULL,
    PRIMARY KEY (id_viabilidad_tecnica),
    UNIQUE KEY uk_cat_viabilidad_tecnica_clave (clave)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

Valores sugeridos:

- `VIABLE`
- `VIABLE_CON_AJUSTE`
- `NO_VIABLE`
- `PENDIENTE_INFORMACION`
- `REQUIERE_REVALORACION`

### `cat_mensaje_institucional_contextual`

```sql
CREATE TABLE cat_mensaje_institucional_contextual (
    id_mensaje_contextual BIGINT NOT NULL AUTO_INCREMENT,
    clave VARCHAR(100) NOT NULL,
    perfil VARCHAR(30) NOT NULL,
    periodo_operativo VARCHAR(30) NOT NULL,
    tipo_mensaje VARCHAR(50) NOT NULL,
    titulo VARCHAR(250) NOT NULL,
    mensaje VARCHAR(4000) NOT NULL,
    activo BIT NOT NULL DEFAULT b'1',
    fecha_registro DATETIME NOT NULL,
    fecha_actualizacion DATETIME NULL,
    usuario_modifico BIGINT NULL,
    PRIMARY KEY (id_mensaje_contextual),
    UNIQUE KEY uk_cat_mensaje_contextual_clave (clave)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

## 4.2 Base de conocimiento

### `rel_patron_caso_academico`

```sql
CREATE TABLE rel_patron_caso_academico (
    id_patron_caso BIGINT NOT NULL AUTO_INCREMENT,
    id_tipo_caso BIGINT NOT NULL,
    nombre_patron VARCHAR(250) NOT NULL,
    descripcion VARCHAR(3000) NOT NULL,
    confianza_base DECIMAL(5,2) NOT NULL DEFAULT 0,
    activo BIT NOT NULL DEFAULT b'1',
    fecha_registro DATETIME NOT NULL,
    fecha_actualizacion DATETIME NULL,
    usuario_modifico BIGINT NULL,
    PRIMARY KEY (id_patron_caso),
    CONSTRAINT fk_patron_tipo_caso
        FOREIGN KEY (id_tipo_caso) REFERENCES cat_tipo_caso_academico (id_tipo_caso)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

### `rel_patron_criterio_operativo`

```sql
CREATE TABLE rel_patron_criterio_operativo (
    id_rel_patron_criterio BIGINT NOT NULL AUTO_INCREMENT,
    id_patron_caso BIGINT NOT NULL,
    id_criterio_operativo BIGINT NOT NULL,
    PRIMARY KEY (id_rel_patron_criterio),
    UNIQUE KEY uk_patron_criterio (id_patron_caso, id_criterio_operativo),
    CONSTRAINT fk_patron_criterio_patron
        FOREIGN KEY (id_patron_caso) REFERENCES rel_patron_caso_academico (id_patron_caso),
    CONSTRAINT fk_patron_criterio_criterio
        FOREIGN KEY (id_criterio_operativo) REFERENCES cat_criterio_operativo (id_criterio_operativo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

### `rel_patron_accion_operativa`

```sql
CREATE TABLE rel_patron_accion_operativa (
    id_rel_patron_accion BIGINT NOT NULL AUTO_INCREMENT,
    id_patron_caso BIGINT NOT NULL,
    id_accion_operativa BIGINT NOT NULL,
    prioridad INT NOT NULL DEFAULT 0,
    PRIMARY KEY (id_rel_patron_accion),
    UNIQUE KEY uk_patron_accion (id_patron_caso, id_accion_operativa),
    CONSTRAINT fk_patron_accion_patron
        FOREIGN KEY (id_patron_caso) REFERENCES rel_patron_caso_academico (id_patron_caso),
    CONSTRAINT fk_patron_accion_accion
        FOREIGN KEY (id_accion_operativa) REFERENCES cat_accion_operativa (id_accion_operativa)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

### `rel_patron_mensaje_contextual`

```sql
CREATE TABLE rel_patron_mensaje_contextual (
    id_rel_patron_mensaje BIGINT NOT NULL AUTO_INCREMENT,
    id_patron_caso BIGINT NOT NULL,
    id_mensaje_contextual BIGINT NOT NULL,
    perfil VARCHAR(30) NOT NULL,
    PRIMARY KEY (id_rel_patron_mensaje),
    UNIQUE KEY uk_patron_mensaje (id_patron_caso, id_mensaje_contextual, perfil),
    CONSTRAINT fk_patron_mensaje_patron
        FOREIGN KEY (id_patron_caso) REFERENCES rel_patron_caso_academico (id_patron_caso),
    CONSTRAINT fk_patron_mensaje_mensaje
        FOREIGN KEY (id_mensaje_contextual) REFERENCES cat_mensaje_institucional_contextual (id_mensaje_contextual)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

## 4.3 Caso académico-operativo

### `tbl_caso_academico_operativo`

```sql
CREATE TABLE tbl_caso_academico_operativo (
    id_caso BIGINT NOT NULL AUTO_INCREMENT,
    folio_externo VARCHAR(120) NULL,
    id_persona BIGINT NOT NULL,
    id_plan BIGINT NULL,
    id_periodo BIGINT NULL,
    perfil_origen VARCHAR(30) NOT NULL,
    origen_caso VARCHAR(50) NOT NULL,
    id_tipo_caso BIGINT NULL,
    id_viabilidad_tecnica BIGINT NULL,
    motivo_principal VARCHAR(500) NULL,
    descripcion_solicitud VARCHAR(4000) NULL,
    confianza_clasificacion DECIMAL(5,2) NULL,
    requiere_intervencion_humana BIT NOT NULL DEFAULT b'0',
    expediente_completo BIT NOT NULL DEFAULT b'0',
    estatus_caso VARCHAR(50) NOT NULL,
    fecha_registro DATETIME NOT NULL,
    fecha_actualizacion DATETIME NULL,
    usuario_modifico BIGINT NULL,
    PRIMARY KEY (id_caso),
    KEY idx_caso_persona (id_persona),
    KEY idx_caso_plan (id_plan),
    KEY idx_caso_tipo (id_tipo_caso),
    KEY idx_caso_viabilidad (id_viabilidad_tecnica),
    CONSTRAINT fk_caso_tipo
        FOREIGN KEY (id_tipo_caso) REFERENCES cat_tipo_caso_academico (id_tipo_caso),
    CONSTRAINT fk_caso_viabilidad
        FOREIGN KEY (id_viabilidad_tecnica) REFERENCES cat_viabilidad_tecnica (id_viabilidad_tecnica)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

### `tbl_caso_ud_relacionada`

```sql
CREATE TABLE tbl_caso_ud_relacionada (
    id_caso_ud BIGINT NOT NULL AUTO_INCREMENT,
    id_caso BIGINT NOT NULL,
    id_programa BIGINT NULL,
    clave_ud VARCHAR(80) NULL,
    nombre_ud VARCHAR(300) NOT NULL,
    tipo_ud VARCHAR(80) NULL,
    semestre INT NULL,
    bloque INT NULL,
    estatus_detectado VARCHAR(100) NULL,
    es_critica BIT NOT NULL DEFAULT b'0',
    motivo_restriccion VARCHAR(500) NULL,
    PRIMARY KEY (id_caso_ud),
    KEY idx_caso_ud_caso (id_caso),
    CONSTRAINT fk_caso_ud_caso
        FOREIGN KEY (id_caso) REFERENCES tbl_caso_academico_operativo (id_caso)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

### `tbl_caso_diagnostico`

```sql
CREATE TABLE tbl_caso_diagnostico (
    id_caso_diagnostico BIGINT NOT NULL AUTO_INCREMENT,
    id_caso BIGINT NOT NULL,
    situacion_academica VARCHAR(100) NULL,
    subtipo_situacion VARCHAR(100) NULL,
    riesgo_actual VARCHAR(50) NULL,
    riesgo_siguiente_periodo VARCHAR(50) NULL,
    cierre_anual VARCHAR(100) NULL,
    carga_viable INT NULL,
    seriacion_activa BIT NOT NULL DEFAULT b'0',
    oferta_vigente BIT NOT NULL DEFAULT b'0',
    omision_probable BIT NOT NULL DEFAULT b'0',
    resumen_motor VARCHAR(4000) NULL,
    PRIMARY KEY (id_caso_diagnostico),
    UNIQUE KEY uk_caso_diagnostico (id_caso),
    CONSTRAINT fk_caso_diagnostico_caso
        FOREIGN KEY (id_caso) REFERENCES tbl_caso_academico_operativo (id_caso)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

### `tbl_caso_dictamen`

```sql
CREATE TABLE tbl_caso_dictamen (
    id_caso_dictamen BIGINT NOT NULL AUTO_INCREMENT,
    id_caso BIGINT NOT NULL,
    id_accion_operativa BIGINT NULL,
    id_viabilidad_tecnica BIGINT NULL,
    dictamen VARCHAR(4000) NULL,
    mensaje_estudiante VARCHAR(4000) NULL,
    mensaje_gestor VARCHAR(4000) NULL,
    requiere_escalamiento BIT NOT NULL DEFAULT b'0',
    folio_escalamiento VARCHAR(120) NULL,
    usuario_dictamino BIGINT NULL,
    fecha_dictamen DATETIME NULL,
    PRIMARY KEY (id_caso_dictamen),
    UNIQUE KEY uk_caso_dictamen (id_caso),
    CONSTRAINT fk_caso_dictamen_caso
        FOREIGN KEY (id_caso) REFERENCES tbl_caso_academico_operativo (id_caso),
    CONSTRAINT fk_caso_dictamen_accion
        FOREIGN KEY (id_accion_operativa) REFERENCES cat_accion_operativa (id_accion_operativa),
    CONSTRAINT fk_caso_dictamen_viabilidad
        FOREIGN KEY (id_viabilidad_tecnica) REFERENCES cat_viabilidad_tecnica (id_viabilidad_tecnica)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

## 4.4 Trazabilidad y conocimiento histórico

### `tbl_caso_bitacora`

```sql
CREATE TABLE tbl_caso_bitacora (
    id_caso_bitacora BIGINT NOT NULL AUTO_INCREMENT,
    id_caso BIGINT NOT NULL,
    evento VARCHAR(120) NOT NULL,
    detalle VARCHAR(4000) NULL,
    usuario BIGINT NULL,
    fecha_registro DATETIME NOT NULL,
    PRIMARY KEY (id_caso_bitacora),
    KEY idx_caso_bitacora_caso (id_caso),
    CONSTRAINT fk_caso_bitacora_caso
        FOREIGN KEY (id_caso) REFERENCES tbl_caso_academico_operativo (id_caso)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

### `tbl_caso_evidencia`

```sql
CREATE TABLE tbl_caso_evidencia (
    id_caso_evidencia BIGINT NOT NULL AUTO_INCREMENT,
    id_caso BIGINT NOT NULL,
    tipo_evidencia VARCHAR(80) NOT NULL,
    ruta_archivo VARCHAR(500) NULL,
    descripcion VARCHAR(2000) NULL,
    usuario_registro BIGINT NULL,
    fecha_registro DATETIME NOT NULL,
    PRIMARY KEY (id_caso_evidencia),
    KEY idx_caso_evidencia_caso (id_caso),
    CONSTRAINT fk_caso_evidencia_caso
        FOREIGN KEY (id_caso) REFERENCES tbl_caso_academico_operativo (id_caso)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

### `tbl_caso_similar`

```sql
CREATE TABLE tbl_caso_similar (
    id_caso_similar BIGINT NOT NULL AUTO_INCREMENT,
    id_caso_origen BIGINT NOT NULL,
    id_caso_relacionado BIGINT NOT NULL,
    puntaje_similitud DECIMAL(5,2) NOT NULL DEFAULT 0,
    motivo_relacion VARCHAR(1000) NULL,
    PRIMARY KEY (id_caso_similar),
    UNIQUE KEY uk_caso_similar (id_caso_origen, id_caso_relacionado),
    CONSTRAINT fk_caso_similar_origen
        FOREIGN KEY (id_caso_origen) REFERENCES tbl_caso_academico_operativo (id_caso),
    CONSTRAINT fk_caso_similar_relacionado
        FOREIGN KEY (id_caso_relacionado) REFERENCES tbl_caso_academico_operativo (id_caso)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

## 5. DTOs propuestos

Paquete recomendado:

- `mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2`

## 5.1 DTOs núcleo

```java
package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CasoAcademicoOperativoDTO implements Serializable {
    private Long idCaso;
    private String folioExterno;
    private Long idPersona;
    private Long idPlan;
    private Long idPeriodo;
    private String perfilOrigen;
    private String origenCaso;
    private TipoCasoAcademicoDTO tipoCaso;
    private ViabilidadTecnicaDTO viabilidadTecnica;
    private String motivoPrincipal;
    private String descripcionSolicitud;
    private Double confianzaClasificacion;
    private Boolean requiereIntervencionHumana;
    private Boolean expedienteCompleto;
    private String estatusCaso;
    private Date fechaRegistro;
    private Date fechaActualizacion;
    private DiagnosticoAcademicoOperativoDTO diagnostico;
    private DictamenCasoDTO dictamen;
    private List<CasoUdRelacionadaDTO> udsRelacionadas;
    private List<BitacoraCasoDTO> bitacora;
}
```

```java
public class DiagnosticoAcademicoOperativoDTO implements Serializable {
    private Long idCaso;
    private String situacionAcademica;
    private String subtipoSituacion;
    private String riesgoActual;
    private String riesgoSiguientePeriodo;
    private String cierreAnual;
    private Integer cargaViable;
    private Boolean seriacionActiva;
    private Boolean ofertaVigente;
    private Boolean omisionProbable;
    private String resumenMotor;
    private List<MotivoRestriccionDTO> motivos;
}
```

```java
public class DictamenCasoDTO implements Serializable {
    private Long idCaso;
    private AccionOperativaDTO accionOperativa;
    private ViabilidadTecnicaDTO viabilidadTecnica;
    private String dictamen;
    private String mensajeEstudiante;
    private String mensajeGestor;
    private Boolean requiereEscalamiento;
    private String folioEscalamiento;
    private Long usuarioDictamino;
}
```

```java
public class CasoUdRelacionadaDTO implements Serializable {
    private Long idCasoUd;
    private Long idCaso;
    private Long idPrograma;
    private String claveUd;
    private String nombreUd;
    private String tipoUd;
    private Integer semestre;
    private Integer bloque;
    private String estatusDetectado;
    private Boolean critica;
    private String motivoRestriccion;
}
```

## 5.2 DTOs de clasificación y expediente

```java
public class ClasificacionCasoDTO implements Serializable {
    private TipoCasoAcademicoDTO tipoCaso;
    private ViabilidadTecnicaDTO viabilidadTecnica;
    private String motivoPrincipal;
    private Double confianza;
    private Boolean requiereIntervencionHumana;
    private List<String> advertencias;
}
```

```java
public class ExpedienteMinimoCasoDTO implements Serializable {
    private Long idPersona;
    private String matricula;
    private String periodo;
    private Boolean completo;
    private Integer totalCampos;
    private Integer camposCompletos;
    private List<CampoExpedienteCasoDTO> campos;
    private List<String> faltantesCriticos;
}
```

```java
public class CampoExpedienteCasoDTO implements Serializable {
    private String clave;
    private String nombre;
    private Boolean presente;
    private Boolean critico;
    private String valorResumen;
}
```

## 5.3 DTOs de conocimiento

```java
public class PatronConocimientoDTO implements Serializable {
    private Long idPatronCaso;
    private TipoCasoAcademicoDTO tipoCaso;
    private String nombrePatron;
    private String descripcion;
    private Double confianzaBase;
    private List<CriterioOperativoDTO> criterios;
    private List<AccionOperativaDTO> acciones;
    private List<MensajeInstitucionalContextualDTO> mensajes;
}
```

```java
public class CasoSimilarDTO implements Serializable {
    private Long idCaso;
    private String folioExterno;
    private String matricula;
    private String programaEducativo;
    private TipoCasoAcademicoDTO tipoCaso;
    private ViabilidadTecnicaDTO viabilidadTecnica;
    private String resolucion;
    private Double puntajeSimilitud;
}
```

## 5.4 DTOs catálogo

```java
public class TipoCasoAcademicoDTO implements Serializable {
    private Long idTipoCaso;
    private String clave;
    private String nombre;
    private String descripcion;
}
```

```java
public class MotivoRestriccionDTO implements Serializable {
    private Long idMotivoRestriccion;
    private String clave;
    private String nombre;
    private String descripcion;
    private Integer prioridad;
}
```

```java
public class CriterioOperativoDTO implements Serializable {
    private Long idCriterioOperativo;
    private String clave;
    private String nombre;
    private String descripcion;
    private Integer prioridad;
}
```

```java
public class AccionOperativaDTO implements Serializable {
    private Long idAccionOperativa;
    private String clave;
    private String nombre;
    private String descripcion;
    private Boolean requiereIntervencionHumana;
}
```

```java
public class ViabilidadTecnicaDTO implements Serializable {
    private Long idViabilidadTecnica;
    private String clave;
    private String nombre;
    private String descripcion;
}
```

```java
public class MensajeInstitucionalContextualDTO implements Serializable {
    private Long idMensajeContextual;
    private String clave;
    private String perfil;
    private String periodoOperativo;
    private String tipoMensaje;
    private String titulo;
    private String mensaje;
}
```

## 5.5 DTOs de bitácora y evidencias

```java
public class BitacoraCasoDTO implements Serializable {
    private Long idCasoBitacora;
    private Long idCaso;
    private String evento;
    private String detalle;
    private Long usuario;
    private Date fechaRegistro;
}
```

```java
public class EvidenciaCasoDTO implements Serializable {
    private Long idCasoEvidencia;
    private Long idCaso;
    private String tipoEvidencia;
    private String rutaArchivo;
    private String descripcion;
    private Long usuarioRegistro;
    private Date fechaRegistro;
}
```

## 5.6 DTOs de fachada para vistas

```java
public class FichaIntegralCasoDTO implements Serializable {
    private CasoAcademicoOperativoDTO caso;
    private ExpedienteMinimoCasoDTO expediente;
    private DiagnosticoAcademicoOperativoDTO diagnostico;
    private List<CasoSimilarDTO> casosSimilares;
    private List<String> mensajesGestor;
    private List<String> mensajesEstudiante;
}
```

```java
public class ContextoAsistenteCurricularV2DTO implements Serializable {
    private Long idPersona;
    private String perfil;
    private String periodoOperativo;
    private DiagnosticoAcademicoOperativoDTO diagnostico;
    private List<CasoUdRelacionadaDTO> udsPrioritarias;
    private List<String> orientaciones;
    private String mensajePrincipal;
    private String siguientePaso;
}
```

## 6. Firmas de servicios Java

Paquete recomendado:

- `mx.gob.sedesol.basegestor.service.gestionescolar.v2`

Implementaciones recomendadas:

- `mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2`

## 6.1 Servicios núcleo

```java
package mx.gob.sedesol.basegestor.service.gestionescolar.v2;

import java.util.List;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.*;

public interface CasoAcademicoOperativoService {
    CasoAcademicoOperativoDTO crearCaso(CasoAcademicoOperativoDTO caso);
    CasoAcademicoOperativoDTO obtenerCasoPorId(Long idCaso);
    List<CasoAcademicoOperativoDTO> buscarCasosPorPersona(Long idPersona);
    CasoAcademicoOperativoDTO actualizarEstatus(Long idCaso, String estatusCaso, Long usuarioModifico);
    CasoAcademicoOperativoDTO cerrarCaso(Long idCaso, Long usuarioModifico);
}
```

```java
public interface ClasificadorCasoService {
    ClasificacionCasoDTO clasificarCaso(Long idPersona, String periodoOperativo, String descripcionSolicitud, List<String> udsSolicitadas);
    ClasificacionCasoDTO reclasificarCaso(Long idCaso);
}
```

```java
public interface ValidadorExpedienteCasoService {
    ExpedienteMinimoCasoDTO validarExpedienteMinimo(Long idPersona, String periodoOperativo, String descripcionSolicitud, List<String> udsSolicitadas);
    List<String> obtenerFaltantesCriticos(Long idCaso);
}
```

```java
public interface DiagnosticoAcademicoOperativoService {
    DiagnosticoAcademicoOperativoDTO generarDiagnostico(Long idPersona, String periodoOperativo);
    DiagnosticoAcademicoOperativoDTO regenerarDiagnosticoCaso(Long idCaso);
    List<CasoUdRelacionadaDTO> obtenerUdsCriticas(Long idPersona, String periodoOperativo);
}
```

```java
public interface BaseConocimientoAcademicoService {
    List<PatronConocimientoDTO> buscarPatronesAplicables(ClasificacionCasoDTO clasificacion, DiagnosticoAcademicoOperativoDTO diagnostico);
    List<MensajeInstitucionalContextualDTO> obtenerMensajesContextuales(String perfil, String periodoOperativo, String tipoCaso, String viabilidad);
}
```

```java
public interface RecomendadorResolucionOperativaService {
    DictamenCasoDTO generarDictamenPreliminar(CasoAcademicoOperativoDTO caso, DiagnosticoAcademicoOperativoDTO diagnostico);
    List<AccionOperativaDTO> sugerirAcciones(CasoAcademicoOperativoDTO caso);
}
```

```java
public interface CasosSimilaresService {
    List<CasoSimilarDTO> buscarCasosSimilares(Long idPersona, String tipoCaso, List<String> clavesUd);
    void recalcularRelacionesCasoSimilar(Long idCaso);
}
```

```java
public interface BitacoraCasoService {
    BitacoraCasoDTO registrarEvento(Long idCaso, String evento, String detalle, Long usuario);
    List<BitacoraCasoDTO> obtenerBitacora(Long idCaso);
}
```

```java
public interface EvidenciaCasoService {
    EvidenciaCasoDTO registrarEvidencia(EvidenciaCasoDTO evidencia);
    List<EvidenciaCasoDTO> obtenerEvidencias(Long idCaso);
}
```

## 6.2 Fachadas para UI

```java
public interface AsistenteCurricularV2Facade {
    ContextoAsistenteCurricularV2DTO obtenerContextoEstudiante(Long idPersona, String periodoOperativo);
    FichaIntegralCasoDTO obtenerContextoGestor(Long idPersona, String periodoOperativo, Long idCaso);
}
```

```java
public interface MallaCurricularV2Facade {
    DiagnosticoAcademicoOperativoDTO obtenerDiagnosticoMalla(Long idPersona, String periodoOperativo);
    CasoUdRelacionadaDTO obtenerDetalleUd(Long idPersona, Long idPrograma, String periodoOperativo);
}
```

```java
public interface ExpedienteCurricularV2Facade {
    FichaIntegralCasoDTO construirFichaIntegral(Long idPersona, String periodoOperativo, Long idCaso);
    List<CasoSimilarDTO> obtenerCasosSimilares(Long idPersona, Long idCaso);
}
```

## 7. Repositorios propuestos

Paquete recomendado:

- `mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2`

Repositorios mínimos:

- `CasoAcademicoOperativoRepo`
- `CasoDiagnosticoRepo`
- `CasoDictamenRepo`
- `CasoUdRelacionadaRepo`
- `CasoBitacoraRepo`
- `CasoEvidenciaRepo`
- `CasoSimilarRepo`
- `TipoCasoAcademicoRepo`
- `MotivoRestriccionRepo`
- `CriterioOperativoRepo`
- `AccionOperativaRepo`
- `ViabilidadTecnicaRepo`
- `MensajeInstitucionalContextualRepo`
- `PatronCasoAcademicoRepo`
- `PatronCriterioOperativoRepo`
- `PatronAccionOperativaRepo`
- `PatronMensajeContextualRepo`

## 8. Mapa de integración con beans actuales

## 8.1 Beans que deben consumir V2

### [ConstanciasBean.java](/home/abelinux/gestor/repo/gestor/codigoFuente/gestorWeb/src/main/java/mx/gob/sedesol/gestorweb/beans/gestionaprendizaje/alumnoview/ConstanciasBean.java)

Debe consumir:

- `ExpedienteCurricularV2Facade`
- `DiagnosticoAcademicoOperativoService`

Uso:

- encabezado contextual enriquecido
- ficha integral resumida
- mensajes institucionales
- acceso a casos similares

### [MallaCurricularAlumnoBean.java](/home/abelinux/gestor/repo/gestor/codigoFuente/gestorWeb/src/main/java/mx/gob/sedesol/gestorweb/beans/gestionaprendizaje/alumnoview/MallaCurricularAlumnoBean.java)

Debe consumir:

- `MallaCurricularV2Facade`
- `DiagnosticoAcademicoOperativoService`

Uso:

- explicar motivo de bloqueo por UD
- diferenciar avance anual vs otras restricciones
- enriquecer panel contextual
- exponer riesgo, causa y acción sugerida por UD

### [TablaCurricularAsistidaBean.java](/home/abelinux/gestor/repo/gestor/codigoFuente/gestorWeb/src/main/java/mx/gob/sedesol/gestorweb/beans/gestionaprendizaje/alumnoview/TablaCurricularAsistidaBean.java)

Debe consumir:

- `AsistenteCurricularV2Facade`
- `BaseConocimientoAcademicoService`
- `RecomendadorResolucionOperativaService`

Uso:

- situación académica 2.0
- UD prioritarias con causa operativa
- validación con explicación real
- orientación contextual sin textos estáticos
- mensajes diferenciados por estudiante/gestor y cursamiento/inscripción

### [ExpedienteAlumnoBean.java](/home/abelinux/gestor/repo/gestor/codigoFuente/gestorWeb/src/main/java/mx/gob/sedesol/gestorweb/beans/gestionaprendizaje/ExpedienteAlumnoBean.java)

Debe consumir:

- `CasoAcademicoOperativoService`
- `ExpedienteCurricularV2Facade`

Uso:

- apertura de ficha integral del estudiante consultado
- navegación desde gestor a caso, malla y asistente con contexto unificado

### [ExpedienteAcademicoBean.java](/home/abelinux/gestor/repo/gestor/codigoFuente/gestorWeb/src/main/java/mx/gob/sedesol/gestorweb/beans/gestionaprendizaje/ExpedienteAcademicoBean.java)

Debe consumir:

- `CasoAcademicoOperativoService`
- `ClasificadorCasoService`

Uso:

- búsqueda por matrícula
- detección de caso abierto
- apertura de hub/ficha integral

### [TrayectoriaAcademicaContextoBean.java](/home/abelinux/gestor/repo/gestor/codigoFuente/gestorWeb/src/main/java/mx/gob/sedesol/gestorweb/beans/gestionaprendizaje/TrayectoriaAcademicaContextoBean.java)

Debe ampliarse para contener:

- `idCasoAcademicoOperativo`
- `tipoCaso`
- `periodoOperativo`
- `clasificacionPreliminar`
- `origenCaso`

Uso:

- transportar contexto entre expediente, malla y asistente

## 8.2 Beans nuevos recomendados

### `CasoAcademicoOperativoBean`

Paquete sugerido:

- `mx.gob.sedesol.gestorweb.beans.gestionaprendizaje.v2`

Responsabilidad:

- bandeja de casos
- apertura de ficha integral
- cambio de estatus
- dictamen

### `FichaIntegralCasoBean`

Responsabilidad:

- vista unificada de análisis gestor
- expediente mínimo
- diagnóstico
- casos similares
- bitácora

### `SeguimientoPreventivoCasoBean`

Responsabilidad:

- vistas de cursamiento para gestor
- alertas preventivas
- prevención futura

## 9. Roadmap por sprints

## Sprint 1

Objetivo:

- crear BD base y catálogos
- DTOs y servicios vacíos
- scripts semilla

Entregables:

- DDL completo
- DTOs núcleo
- interfaces de servicios
- catálogos iniciales

## Sprint 2

Objetivo:

- clasificación y expediente mínimo

Entregables:

- `ClasificadorCasoService`
- `ValidadorExpedienteCasoService`
- estados del caso
- apertura básica de caso por matrícula

## Sprint 3

Objetivo:

- diagnóstico enriquecido
- motivos de bloqueo
- avance anual vs otras restricciones
- omisión vs no acreditación

Entregables:

- `DiagnosticoAcademicoOperativoService`
- detalle por UD
- primeras reglas integradas

## Sprint 4

Objetivo:

- cargar base de conocimiento v1 desde el Excel
- mensajes y patrones

Entregables:

- catálogos poblados
- `BaseConocimientoAcademicoService`
- `CasosSimilaresService`

codigoFuente/bd/26_asistente_curricular_v2_base_conocimiento_excel_20260718.sql


## Sprint 5

Objetivo:

- experiencia gestor v2

Entregables:

- bandeja de casos
- ficha integral
- dictamen y acción operativa
- bitácora y evidencias

## Sprint 6

Objetivo:

- experiencia estudiante v2

Entregables:

- orientación contextual enriquecida
- mensajes preventivos
- paneles sin duplicidad
- prevención de tickets innecesarios

## 10. Recomendaciones finales

- No sustituir de inmediato las vistas actuales.
- Montar V2 por fachada y feature flag.
- Cargar el Excel a catálogo estructurado lo antes posible.
- Priorizar gestor antes que estudiante.
- No resolver V2 con más texto fijo en JSF.
- Centralizar toda decisión en servicios nuevos.

## 11. Primer backlog técnico sugerido

1. Crear paquete `commons.dto.gestionescolar.v2`.
2. Crear paquete `service.gestionescolar.v2`.
3. Crear paquete `service.impl.gestionescolar.v2`.
4. Crear paquete `model.repositories.gestionescolar.v2`.
5. Implementar DDL y catálogos base.
6. Diseñar importador inicial del Excel a tablas catálogo/patrones.
7. Integrar `TrayectoriaAcademicaContextoBean` con `idCaso`.
8. Crear `FichaIntegralCasoBean`.
9. Conectar `MallaCurricularAlumnoBean` a detalle V2 por UD.
10. Conectar `TablaCurricularAsistidaBean` a orientación V2 basada en conocimiento.
