package mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CasoAcademicoOperativoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.MensajeInstitucionalContextualDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.PatronConocimientoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TipoCasoAcademicoDTO;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.RelPatronCasoAcademicoV2;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2.CatAccionOperativaV2Repo;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2.CatCriterioOperativoV2Repo;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2.CatMensajeInstitucionalContextualV2Repo;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2.RelPatronAccionOperativaV2Repo;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2.RelPatronCasoAcademicoV2Repo;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2.RelPatronCriterioOperativoV2Repo;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2.RelPatronMensajeContextualV2Repo;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.BaseConocimientoAcademicoService;

@Service("baseConocimientoAcademicoService")
public class BaseConocimientoAcademicoServiceImpl implements BaseConocimientoAcademicoService {

    @Autowired
    private RelPatronCasoAcademicoV2Repo patronRepo;

    @Autowired
    private RelPatronCriterioOperativoV2Repo patronCriterioRepo;

    @Autowired
    private RelPatronAccionOperativaV2Repo patronAccionRepo;

    @Autowired
    private RelPatronMensajeContextualV2Repo patronMensajeRepo;

    @Autowired
    private CatCriterioOperativoV2Repo criterioRepo;

    @Autowired
    private CatAccionOperativaV2Repo accionRepo;

    @Autowired
    private CatMensajeInstitucionalContextualV2Repo mensajeRepo;

    @Override
    @Transactional(readOnly = true)
    public List<PatronConocimientoDTO> obtenerPatronesActivos() throws InscripcionException {
        List<PatronConocimientoDTO> resultado = new ArrayList<PatronConocimientoDTO>();
        for (RelPatronCasoAcademicoV2 patron : patronRepo.findAll()) {
            if (Boolean.TRUE.equals(patron.getActivo())) {
                resultado.add(construirPatron(patron));
            }
        }
        return resultado;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatronConocimientoDTO> obtenerPatronesPorTipoCaso(TipoCasoAcademicoDTO tipoCaso)
            throws InscripcionException {
        List<PatronConocimientoDTO> resultado = new ArrayList<PatronConocimientoDTO>();
        if (tipoCaso == null || tipoCaso.getId() == null) {
            return resultado;
        }
        for (RelPatronCasoAcademicoV2 patron : patronRepo.findByIdTipoCaso(tipoCaso.getId())) {
            resultado.add(construirPatron(patron));
        }
        return resultado;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MensajeInstitucionalContextualDTO> obtenerMensajesPorPerfilYPeriodo(String perfil, String periodoOperativo)
            throws InscripcionException {
        List<MensajeInstitucionalContextualDTO> resultado = new ArrayList<MensajeInstitucionalContextualDTO>();
        mensajeRepo.findByPerfilAndPeriodoOperativo(perfil, periodoOperativo)
                .forEach(item -> resultado.add(AsistenteCurricularV2Mapper.toMensajeDto(item)));
        return resultado;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatronConocimientoDTO> obtenerPatronesRelevantes(CasoAcademicoOperativoDTO caso, String perfil,
            String periodoOperativo) throws InscripcionException {
        List<PatronConocimientoDTO> candidatos = new ArrayList<PatronConocimientoDTO>();
        if (caso != null && caso.getTipoCaso() != null) {
            candidatos.addAll(obtenerPatronesPorTipoCaso(caso.getTipoCaso()));
        }
        for (PatronConocimientoDTO patron : obtenerPatronesActivos()) {
            if (!contienePatron(candidatos, patron)) {
                candidatos.add(patron);
            }
        }
        List<PatronPuntaje> ponderados = new ArrayList<PatronPuntaje>();
        for (PatronConocimientoDTO patron : candidatos) {
            double puntaje = puntuarPatron(caso, patron, perfil, periodoOperativo);
            if (puntaje > 0D) {
                ponderados.add(new PatronPuntaje(patron, puntaje));
            }
        }
        Collections.sort(ponderados, Comparator.comparingDouble(PatronPuntaje::getPuntaje).reversed());
        List<PatronConocimientoDTO> resultado = new ArrayList<PatronConocimientoDTO>();
        for (PatronPuntaje item : ponderados) {
            resultado.add(item.getPatron());
            if (resultado.size() >= 4) {
                break;
            }
        }
        return resultado;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MensajeInstitucionalContextualDTO> obtenerMensajesContextuales(CasoAcademicoOperativoDTO caso,
            String perfil, String periodoOperativo) throws InscripcionException {
        Map<String, MensajePuntaje> mensajes = new LinkedHashMap<String, MensajePuntaje>();
        for (MensajeInstitucionalContextualDTO mensaje : obtenerMensajesPorPerfilYPeriodo(perfil, periodoOperativo)) {
            double puntaje = puntuarMensaje(caso, mensaje, false);
            registrarMensaje(mensajes, mensaje, puntaje);
        }
        for (PatronConocimientoDTO patron : obtenerPatronesRelevantes(caso, perfil, periodoOperativo)) {
            if (patron.getMensajes() == null) {
                continue;
            }
            for (MensajeInstitucionalContextualDTO mensaje : patron.getMensajes()) {
                if (mensaje == null) {
                    continue;
                }
                if (!coincide(mensaje.getPerfil(), perfil) || !coincide(mensaje.getPeriodoOperativo(), periodoOperativo)) {
                    continue;
                }
                double puntaje = puntuarMensaje(caso, mensaje, true);
                registrarMensaje(mensajes, mensaje, puntaje);
            }
        }
        List<MensajePuntaje> ordenados = new ArrayList<MensajePuntaje>(mensajes.values());
        Collections.sort(ordenados, Comparator.comparingDouble(MensajePuntaje::getPuntaje).reversed());
        List<MensajeInstitucionalContextualDTO> resultado = new ArrayList<MensajeInstitucionalContextualDTO>();
        for (MensajePuntaje item : ordenados) {
            resultado.add(item.getMensaje());
            if (resultado.size() >= 4) {
                break;
            }
        }
        return resultado;
    }

    private PatronConocimientoDTO construirPatron(RelPatronCasoAcademicoV2 patron) {
        PatronConocimientoDTO dto = AsistenteCurricularV2Mapper.toPatronDto(patron);
        patronCriterioRepo.findByIdPatronCaso(patron.getId())
                .forEach(item -> dto.getCriterios()
                        .add(AsistenteCurricularV2Mapper.toCriterioDto(criterioRepo.findOne(item.getIdCriterioOperativo()))));
        patronAccionRepo.findByIdPatronCaso(patron.getId())
                .forEach(item -> dto.getAcciones()
                        .add(AsistenteCurricularV2Mapper.toAccionDto(accionRepo.findOne(item.getIdAccionOperativa()))));
        patronMensajeRepo.findByIdPatronCasoAndPerfil(patron.getId(), "GESTOR")
                .forEach(item -> dto.getMensajes()
                        .add(AsistenteCurricularV2Mapper.toMensajeDto(mensajeRepo.findOne(item.getIdMensajeContextual()))));
        patronMensajeRepo.findByIdPatronCasoAndPerfil(patron.getId(), "ESTUDIANTE")
                .forEach(item -> dto.getMensajes()
                        .add(AsistenteCurricularV2Mapper.toMensajeDto(mensajeRepo.findOne(item.getIdMensajeContextual()))));
        return dto;
    }

    private boolean contienePatron(List<PatronConocimientoDTO> lista, PatronConocimientoDTO patron) {
        if (lista == null || patron == null) {
            return false;
        }
        for (PatronConocimientoDTO existente : lista) {
            if (existente != null && existente.getId() != null && existente.getId().equals(patron.getId())) {
                return true;
            }
        }
        return false;
    }

    private double puntuarPatron(CasoAcademicoOperativoDTO caso, PatronConocimientoDTO patron, String perfil,
            String periodoOperativo) {
        if (caso == null || patron == null) {
            return 0D;
        }
        double puntaje = patron.getConfianzaBase() != null ? patron.getConfianzaBase().doubleValue() : 0.10D;
        String tipoClave = caso.getTipoCaso() != null ? caso.getTipoCaso().getClave() : null;
        String motivoClave = caso.getMotivoRestriccion() != null ? caso.getMotivoRestriccion().getClave() : null;
        String nombre = textoPlano(patron.getNombrePatron());
        String descripcion = textoPlano(patron.getDescripcion());
        String restriccion = caso.getDiagnostico() != null ? textoPlano(caso.getDiagnostico().getRestriccionDominante())
                : "";
        String riesgoActual = caso.getDiagnostico() != null ? textoPlano(caso.getDiagnostico().getRiesgoActual()) : "";
        int totalNoAcreditadas = valor(caso.getDiagnostico() != null ? caso.getDiagnostico().getTotalNoAcreditadas() : null);
        int totalBloqueadas = valor(caso.getDiagnostico() != null ? caso.getDiagnostico().getTotalBloqueadas() : null);
        boolean senalAcademicaFuerte = totalNoAcreditadas > 0 || totalBloqueadas > 0 || contiene(riesgoActual, "ALTO")
                || contiene(restriccion, "AVANCE_ANUAL");

        if (patron.getTipoCaso() != null && patron.getTipoCaso().getClave() != null
                && patron.getTipoCaso().getClave().equalsIgnoreCase(tipoClave)) {
            puntaje += 0.60D;
        }
        if (contiene(nombre, "rezago") && (contiene(tipoClave, "REZAGO") || contiene(tipoClave, "UNIDADES_NO_ACREDITADAS"))) {
            puntaje += 0.45D;
        }
        if ((contiene(nombre, "no acreditad") || contiene(descripcion, "no acreditad"))
                && totalNoAcreditadas > 0) {
            puntaje += 0.50D;
        }
        if ((contiene(nombre, "alto riesgo") || contiene(descripcion, "alto riesgo")) && totalNoAcreditadas >= 5) {
            puntaje += 0.80D;
        }
        if ((contiene(nombre, "primer anio incompleto") || contiene(nombre, "segundo anio"))
                && contiene(tipoClave, "REINSCRIPCION_NO_APROBADA")) {
            puntaje += 0.65D;
        }
        if ((contiene(nombre, "continuidad del mismo anio") || contiene(descripcion, "mismo anio"))
                && ("AVANCE_ANUAL".equalsIgnoreCase(restriccion) || contiene(motivoClave, "MISMO_ANIO"))) {
            puntaje += 0.55D;
        }
        if ((contiene(nombre, "optativas") || contiene(descripcion, "optativas"))
                && contiene(motivoClave, "SOLO_OPTATIVAS")) {
            puntaje += 0.55D;
        }
        if ((contiene(nombre, "omision documentada") || contiene(descripcion, "omision documentada"))
                && contiene(motivoClave, "OMISION_DOCUMENTADA")) {
            puntaje += 0.60D;
        }
        if ((contiene(nombre, "dictamen") || contiene(descripcion, "informacion"))
                && !senalAcademicaFuerte
                && (containsFalse(caso.getExpedienteCompleto()) || contiene(tipoClave, "PENDIENTE_INFORMACION")
                        || contiene(motivoClave, "SIN_DICTAMEN") || contiene(motivoClave, "INFORMACION_INSUFICIENTE"))) {
            puntaje += 0.65D;
        }
        if ((contiene(nombre, "cierre anual") || contiene(descripcion, "cierre anual"))
                && contiene(motivoClave, "CIERRE_ANUAL")) {
            puntaje += 0.50D;
        }
        if ((contiene(nombre, "seriacion") || contiene(descripcion, "seriacion"))
                && valor(caso.getDiagnostico() != null ? caso.getDiagnostico().getTotalBloqueadas() : null) > 0) {
            puntaje += 0.45D;
        }
        if ("GESTOR".equalsIgnoreCase(perfil) && contiene(nombre, "revaloracion")) {
            puntaje += 0.10D;
        }
        if ("INSCRIPCION".equalsIgnoreCase(periodoOperativo) && contiene(nombre, "inscrip")) {
            puntaje += 0.05D;
        }
        return puntaje;
    }

    private void registrarMensaje(Map<String, MensajePuntaje> mensajes, MensajeInstitucionalContextualDTO mensaje,
            double puntaje) {
        if (mensaje == null || mensaje.getClave() == null) {
            return;
        }
        MensajePuntaje actual = mensajes.get(mensaje.getClave());
        if (actual == null || puntaje > actual.getPuntaje()) {
            mensajes.put(mensaje.getClave(), new MensajePuntaje(mensaje, puntaje));
        }
    }

    private double puntuarMensaje(CasoAcademicoOperativoDTO caso, MensajeInstitucionalContextualDTO mensaje,
            boolean desdePatron) {
        if (caso == null || mensaje == null) {
            return 0D;
        }
        double puntaje = desdePatron ? 1.00D : 0.40D;
        String clave = textoPlano(mensaje.getClave());
        String titulo = textoPlano(mensaje.getTitulo());
        String texto = textoPlano(mensaje.getMensaje());
        String tipoClave = caso.getTipoCaso() != null ? caso.getTipoCaso().getClave() : null;
        String motivoClave = caso.getMotivoRestriccion() != null ? caso.getMotivoRestriccion().getClave() : null;
        int totalNoAcreditadas = valor(caso.getDiagnostico() != null ? caso.getDiagnostico().getTotalNoAcreditadas() : null);
        int totalBloqueadas = valor(caso.getDiagnostico() != null ? caso.getDiagnostico().getTotalBloqueadas() : null);
        String riesgoActual = caso.getDiagnostico() != null ? textoPlano(caso.getDiagnostico().getRiesgoActual()) : "";
        String restriccion = caso.getDiagnostico() != null ? textoPlano(caso.getDiagnostico().getRestriccionDominante()) : "";
        boolean senalAcademicaFuerte = totalNoAcreditadas > 0 || totalBloqueadas > 0 || contiene(riesgoActual, "ALTO")
                || contiene(restriccion, "AVANCE_ANUAL");

        if ((contiene(clave, "rezago") || contiene(titulo, "rezago"))
                && (contiene(tipoClave, "REZAGO") || contiene(tipoClave, "UNIDADES_NO_ACREDITADAS"))) {
            puntaje += 0.65D;
        }
        if ((contiene(clave, "no_acreditadas") || contiene(texto, "no acreditadas")) && totalNoAcreditadas > 0) {
            puntaje += 0.70D;
        }
        if ((contiene(clave, "alto_riesgo") || contiene(texto, "riesgo alto")) && totalNoAcreditadas >= 5) {
            puntaje += 0.85D;
        }
        if ((contiene(clave, "solo_optativas") || contiene(texto, "optativas")) && contiene(motivoClave, "SOLO_OPTATIVAS")) {
            puntaje += 0.60D;
        }
        if ((contiene(clave, "mismo_anio") || contiene(texto, "mismo anio")) && contiene(motivoClave, "MISMO_ANIO")) {
            puntaje += 0.55D;
        }
        if ((contiene(clave, "omision_doc") || contiene(texto, "omision")) && contiene(motivoClave, "OMISION_DOCUMENTADA")) {
            puntaje += 0.55D;
        }
        if ((contiene(clave, "sin_dictamen") || contiene(texto, "informacion insuficiente"))
                && !senalAcademicaFuerte
                && (containsFalse(caso.getExpedienteCompleto()) || contiene(tipoClave, "PENDIENTE_INFORMACION"))) {
            puntaje += 0.75D;
        }
        if ((contiene(clave, "cierre_anual") || contiene(texto, "cierre anual"))
                && contiene(motivoClave, "CIERRE_ANUAL")) {
            puntaje += 0.50D;
        }
        if (totalBloqueadas > 0 && (contiene(texto, "seriacion") || contiene(clave, "seriacion"))) {
            puntaje += 0.45D;
        }
        return puntaje;
    }

    private boolean coincide(String valor, String esperado) {
        return valor != null && esperado != null && valor.equalsIgnoreCase(esperado);
    }

    private boolean contiene(String valor, String patron) {
        return valor != null && patron != null && valor.toUpperCase().contains(patron.toUpperCase());
    }

    private String textoPlano(String valor) {
        return valor != null ? valor : "";
    }

    private int valor(Integer numero) {
        return numero != null ? numero.intValue() : 0;
    }

    private boolean containsFalse(Boolean valor) {
        return Boolean.FALSE.equals(valor);
    }

    private static class PatronPuntaje {
        private final PatronConocimientoDTO patron;
        private final double puntaje;

        private PatronPuntaje(PatronConocimientoDTO patron, double puntaje) {
            this.patron = patron;
            this.puntaje = puntaje;
        }

        public PatronConocimientoDTO getPatron() {
            return patron;
        }

        public double getPuntaje() {
            return puntaje;
        }
    }

    private static class MensajePuntaje {
        private final MensajeInstitucionalContextualDTO mensaje;
        private final double puntaje;

        private MensajePuntaje(MensajeInstitucionalContextualDTO mensaje, double puntaje) {
            this.mensaje = mensaje;
            this.puntaje = puntaje;
        }

        public MensajeInstitucionalContextualDTO getMensaje() {
            return mensaje;
        }

        public double getPuntaje() {
            return puntaje;
        }
    }
}
