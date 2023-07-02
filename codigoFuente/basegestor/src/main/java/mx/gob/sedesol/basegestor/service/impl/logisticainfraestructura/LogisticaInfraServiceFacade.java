package mx.gob.sedesol.basegestor.service.impl.logisticainfraestructura;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.NodoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.BitacoraDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.AreaSedeDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.ConfiguracionAreaDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.CritBusquedaAreasConfigDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.RelAreaDistribucionDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.RelAreaRecursoDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.RelSolicitudEventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.RelSolicitudEventoGeneralDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.SedeDTO;
import mx.gob.sedesol.basegestor.commons.utils.EstatusAreaEnum;
import mx.gob.sedesol.basegestor.commons.utils.EstatusReservacionEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatEstadoEventoCapacitacion;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.CatAreaInfraestructura;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.CatDistribucionArea;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.CatEstatusArea;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.CatEstatusReservacion;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.CatTipoRecurso;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.CatUbicacionTerritorial;
import mx.gob.sedesol.basegestor.service.admin.CatalogoComunService;
import mx.gob.sedesol.basegestor.service.admin.OrgGubernamentalService;
import mx.gob.sedesol.basegestor.service.gestionescolar.EventoCapacitacionService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.AreaDistribucionService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.AreaRecursoService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.AreaSedeService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.ConfiguracionAreaService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.EventoGeneralService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.PersonalizacionAreaService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.PersonalizacionRecursoService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.RecursosInfraestructuraService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.ReservacionEventoCapacitacionService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.ReservacionEventoGeneralService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.SedeService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.SolicitudEventoCapacitacionService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.SolicitudEventoGeneralService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.SolicitudReservacionService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.MallaCurricularService;

@Service("logisticaInfraServiceFacade")
public class LogisticaInfraServiceFacade implements Serializable {

    private static final long serialVersionUID = 7125311370102820918L;

    private Logger logger = Logger.getLogger(LogisticaInfraServiceFacade.class);

    @Autowired
    private SolicitudReservacionService solicitudReservacionService;

    @Autowired
    private ReservacionEventoGeneralService reservacionEventoGeneralService;

    @Autowired
    private SolicitudEventoGeneralService solicitudEventoGeneralService;

    @Autowired
    private EventoGeneralService eventoGeneralService;

    @Autowired
    private ReservacionEventoCapacitacionService reservacionEventoCapacitacionService;

    @Autowired
    private SolicitudEventoCapacitacionService solicitudEventoCapacitacionService;

    @Autowired
    private AreaDistribucionService areaDistribucionService;

    @Autowired
    private PersonalizacionRecursoService personalizacionRecursoService;

    @Autowired
    private PersonalizacionAreaService personalizacionAreaService;

    @Autowired
    private ConfiguracionAreaService configuracionAreaService;

    @Autowired
    private CatalogoComunService<CatUbicacionTerritorial, Integer> catUbicacionTerrService;

    @Autowired
    private CatalogoComunService<CatDistribucionArea, Integer> catDistribucionAreaService;

    @Autowired
    private CatalogoComunService<CatEstatusArea, Integer> catEstatusAreaService;

    @Autowired
    private AreaRecursoService areaRecursosService;

    @Autowired
    private RecursosInfraestructuraService recursosInfraestructuraService;

    @Autowired
    private CatalogoComunService<CatTipoRecurso, Integer> catTipoRecursoService;

    @Autowired
    private SedeService sedeService;

    @Autowired
    private OrgGubernamentalService orgGubernamentalService;

    @Autowired
    private CatalogoComunService<CatAreaInfraestructura, Integer> catAreaInfraService;

    @Autowired
    private CatalogoComunService<CatEstatusReservacion, Integer> catEstatusReservacionService;

    @Autowired
    private AreaSedeService areaSedeService;

    @Autowired
    private EventoCapacitacionService eventoCapacitacionService;

    @Autowired
    private CatalogoComunService<CatEstadoEventoCapacitacion, Integer> estadoEventoCapacitService;

    @Autowired
    private MallaCurricularService mallaCurricularService;

    @Autowired
    private CatalogoComunService<CatEstatusReservacion, Integer> estatusReservacionService;

    /**
     *
     * @param sede
     * @param areas
     */
    @Transactional(rollbackFor = Exception.class)
    //TODO: No debería existir lógica de negocio en un facade, deberían mover este código a un servicio
    public boolean asignaAreasASede(SedeDTO sede, List<CatalogoComunDTO> areas, Long idUsuModifico) {
        boolean bnd = Boolean.FALSE;
        Date fechaAct = new Date();
        try {
            if (ObjectUtils.isNotNull(sede) && !ObjectUtils.isNullOrEmpty(areas)) {

                for (CatalogoComunDTO a : areas) {

                    AreaSedeDTO consAS = areaSedeService.consultaAreaSedePorIdSedeyIdArea(sede.getIdSede(), a.getId());

                    if (ObjectUtils.isNotNull(consAS)) {
                        sede.getCatAreasSedes().forEach(as -> {
                            if (as.getCatArea().getId().equals(a.getId())) {
                                AreaSedeDTO asdto = as;
                                asdto.setCatEstatusArea(catEstatusAreaService.buscarPorId(EstatusAreaEnum.ACTIVO.getId(),
                                        CatEstatusArea.class));
                                asdto.setFechaActualizacion(fechaAct);
                                asdto.setUsuarioModifico(idUsuModifico);
                                areaSedeService.actualizar(asdto);
                            }
                        });

                    } else {
                        AreaSedeDTO as = new AreaSedeDTO();
                        as.setCatArea(a);
                        as.setCatSede(sede);
                        as.setFechaRegistro(fechaAct);
                        as.setUsuarioModifico(idUsuModifico);
                        as.setCatEstatusArea(catEstatusAreaService.buscarPorId(EstatusAreaEnum.ACTIVO.getId(),
                                CatEstatusArea.class));
                        sede.setCatAreasSedes(new ArrayList<>());
                        sede.getCatAreasSedes().add(as);
                        sede.setFechaActualizacion(fechaAct);
                        sedeService.actualizar(sede);
                    }
                }
                bnd = Boolean.TRUE;
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }

        return bnd;
    }

    public Map<String, Object> obtieneSedesInfo(Integer idOrgGubernamental, Integer idSede) {
        Map<String, Object> sedesInfo = new HashMap<>();

        List<SedeDTO> sedes = sedeService.consultaSedesPorOrgGubDepedencia(idOrgGubernamental);

        HashMap<Integer, String> sedesPorUbi = new HashMap<>();

        if (!ObjectUtils.isNullOrEmpty(sedes)) {
            for (SedeDTO sede : sedes) {
                sedesPorUbi.put(sede.getIdSede(),
                        sede.getCatUbicacionTerritorial().getNombre().concat("-").concat(sede.getNombre()));
            }

            List<NodoDTO> ubiSedes = new ArrayList<>();
            for (Map.Entry<Integer, String> entry : sedesPorUbi.entrySet()) {
                NodoDTO nodo = new NodoDTO(entry.getKey(), entry.getValue());
                ubiSedes.add(nodo);
            }

            sedesInfo.put("ubiSedes", ubiSedes);

            // Genera el Catalogo de areas asociadas a la sede
            CritBusquedaAreasConfigDTO criteriosConf = new CritBusquedaAreasConfigDTO();
            criteriosConf.setIdSede(idSede);

            List<CatalogoComunDTO> catAreasInfra = new ArrayList<>();
            List<ConfiguracionAreaDTO> areasConfiguradas = configuracionAreaService
                    .busquedaAreasConfiguradasCriterios(criteriosConf);

            for (ConfiguracionAreaDTO s : areasConfiguradas) {
                catAreasInfra.add(s.getCatAreasSede().getCatArea());
            }

            sedesInfo.put("catAreasInfra", catAreasInfra);
        }

        return sedesInfo;
    }

    /**
     *
     * @param sede
     * @param areas
     * @param idUsuModifico
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean eliminacionLogicaAreasASede(SedeDTO sede, List<CatalogoComunDTO> areas, Long idUsuModifico) {
        boolean bnd = Boolean.FALSE;
        Date fechaAct = new Date();
        try {
            if (ObjectUtils.isNotNull(sede) && !ObjectUtils.isNullOrEmpty(areas)) {
                for (CatalogoComunDTO a : areas) {
                    for (AreaSedeDTO as : sede.getCatAreasSedes()) {
                        if (a.getId().equals(as.getCatArea().getId())) {
                            AreaSedeDTO asdto = as;
                            asdto.setCatEstatusArea(catEstatusAreaService.buscarPorId(EstatusAreaEnum.INACTIVO.getId(),
                                    CatEstatusArea.class));
                            asdto.setFechaActualizacion(fechaAct);
                            asdto.setUsuarioModifico(idUsuModifico);
                            areaSedeService.actualizar(asdto);
                        }
                    }

                }
                bnd = Boolean.TRUE;
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }

        return bnd;
    }

    /**
     *
     * @param configArea
     * @param recursosEliminar
     * @param nuevaConfig
     * @param nvasDistribuciones
     * @param nuevosRecursos
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<ConfiguracionAreaDTO> guardaConfiguracionArea(ConfiguracionAreaDTO configArea,
            List<RelAreaRecursoDTO> recursosEliminar, boolean nuevaConfig, List<String> nvasDistribuciones,
            List<RelAreaRecursoDTO> nuevosRecursos) {

        ResultadoDTO<ConfiguracionAreaDTO> resTx = null;
        Date fechaActual = new Date();

        try {
            // Se eliminan recursos asociados
            if (ObjectUtils.isNullOrEmpty(configArea.getRelAreaRecursos())) {
                for (RelAreaRecursoDTO r : recursosEliminar) {
                   areaRecursosService.eliminar(r);
                }
            }

            // Se eliminar Distribuciones
            if (!ObjectUtils.isNullOrEmpty(configArea.getRelAreaDistribucion())) {
                for (RelAreaDistribucionDTO var : configArea.getRelAreaDistribucion()) {
                    boolean flag = false;
                    for (String d : nvasDistribuciones) {
                        if (var.getIdDistribucion().equals(Integer.valueOf(d))) {
                            flag = true;
                        }
                    }
                    if (flag == false) {
                      areaDistribucionService.eliminar(var);
                    }
                }
            }

            if (nuevaConfig) {

                ResultadoDTO<ConfiguracionAreaDTO> res = configuracionAreaService.guardar(configArea);
                if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {
                    ConfiguracionAreaDTO configAreaGuardada = res.getDto();
                    configAreaGuardada.setFechaActualizacion(fechaActual);
                    this.asociaRecursosDistribucionesConfigArea(configAreaGuardada, nvasDistribuciones, nuevosRecursos);
                    resTx = configuracionAreaService.actualizar(configAreaGuardada);
                }

            } else {

                configArea.setFechaActualizacion(fechaActual);
                this.asociaRecursosDistribucionesConfigArea(configArea, nvasDistribuciones, nuevosRecursos);
                resTx = configuracionAreaService.actualizar(configArea);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
        return resTx;
    }

    /**
     *
     * @param configArea
     * @param nvasDistribuciones
     * @param nuevosRecursos
     */
    private void asociaRecursosDistribucionesConfigArea(ConfiguracionAreaDTO configArea,
            List<String> nvasDistribuciones, List<RelAreaRecursoDTO> nuevosRecursos) {

        if (!ObjectUtils.isNullOrEmpty(nvasDistribuciones)) {

            List<RelAreaDistribucionDTO> distribuciones = new ArrayList<>();

            for (String d : nvasDistribuciones) {
                RelAreaDistribucionDTO ad = new RelAreaDistribucionDTO();
                ad.setActivo(1);
                ad.setFechaRegistro(configArea.getFechaRegistro());
                ad.setIdAreaConfig(configArea.getIdConfigArea());
                ad.setIdDistribucion(Integer.valueOf(d));
                ad.setUsuarioRegistro(configArea.getUsuarioModifico());
                distribuciones.add(ad);
            }
            configArea.setRelAreaDistribucion(distribuciones);
        }

        // Nuevos Recursos
        if (!ObjectUtils.isNullOrEmpty(nuevosRecursos)) {

            for (RelAreaRecursoDTO rec : nuevosRecursos) {
                rec.setFechaRegistro(configArea.getFechaRegistro());
                rec.setUsuarioRegistro(configArea.getUsuarioModifico());
                rec.setIdAreaConfig(configArea.getIdConfigArea());
            }
            configArea.setRelAreaRecursos(nuevosRecursos);
        }
    }

    public List<RelSolicitudEventoCapacitacionDTO> asignarSolicitudesCapAprobadas(List<RelSolicitudEventoCapacitacionDTO> solicitudes) {
        for (RelSolicitudEventoCapacitacionDTO r : solicitudes) {
            if (r.getTblReservacionEventoCapacitacion().getCatEstatusReservacion()
                    .getId() == EstatusReservacionEnum.APROBADO.getValor()) {
                r.setAprobado(true);
            }
        }
        return solicitudes;
    }

    public List<RelSolicitudEventoGeneralDTO> asignarSolicitudesGenAprobadas(List<RelSolicitudEventoGeneralDTO> solicitudes) {
        for (RelSolicitudEventoGeneralDTO r : solicitudes) {
            if (r.getTblReservacionEventoGeneral().getCatEstatusReservacion()
                    .getId() == EstatusReservacionEnum.APROBADO.getValor()) {
                r.setAprobado(true);
            }
        }
        return solicitudes;
    }

    public boolean validarHorasSalas(List<Integer> solicitudesReserv) {

        Boolean aprobado = true;

        return true;
    }

    // INICIA GETTERS Y SETTERS
    /**
     * @return the configuracionAreaService
     */
    public ConfiguracionAreaService getConfiguracionAreaService() {
        return configuracionAreaService;
    }

    /**
     * @return the catUbicacionTerrService
     */
    public CatalogoComunService<CatUbicacionTerritorial, Integer> getCatUbicacionTerrService() {
        return catUbicacionTerrService;
    }

    /**
     * @return the sedeService
     */
    public SedeService getSedeService() {
        return sedeService;
    }

    public CatalogoComunService<CatDistribucionArea, Integer> getCatDistribucionAreaService() {
        return catDistribucionAreaService;
    }

    public void setCatDistribucionAreaService(
            CatalogoComunService<CatDistribucionArea, Integer> catDistribucionAreaService) {
        this.catDistribucionAreaService = catDistribucionAreaService;
    }

    public CatalogoComunService<CatEstatusArea, Integer> getCatEstatusAreaService() {
        return catEstatusAreaService;
    }

    public void setCatEstatusAreaService(CatalogoComunService<CatEstatusArea, Integer> catEstatusAreaService) {
        this.catEstatusAreaService = catEstatusAreaService;
    }

    /**
     * @return the orgGubernamentalService
     */
    public OrgGubernamentalService getOrgGubernamentalService() {
        return orgGubernamentalService;
    }

    /**
     * @return the catAreaInfraService
     */
    public CatalogoComunService<CatAreaInfraestructura, Integer> getCatAreaInfraService() {
        return catAreaInfraService;
    }

    /**
     * @return the areaSedeService
     */
    public AreaSedeService getAreaSedeService() {
        return areaSedeService;
    }

    public CatalogoComunService<CatTipoRecurso, Integer> getCatTipoRecursoService() {
        return catTipoRecursoService;
    }

    public void setCatTipoRecursoService(CatalogoComunService<CatTipoRecurso, Integer> catTipoRecursoService) {
        this.catTipoRecursoService = catTipoRecursoService;
    }

    public RecursosInfraestructuraService getRecursosInfraestructuraService() {
        return recursosInfraestructuraService;
    }

    public void setRecursosInfraestructuraService(RecursosInfraestructuraService recursosInfraestructuraService) {
        this.recursosInfraestructuraService = recursosInfraestructuraService;
    }

    /**
     * @return the mallaCurricularService
     */
    public MallaCurricularService getMallaCurricularService() {
        return mallaCurricularService;
    }

    /**
     * @return the eventoCapacitacionService
     */
    public EventoCapacitacionService getEventoCapacitacionService() {
        return eventoCapacitacionService;
    }

    /**
     * @return the estadoEventoCapacitService
     */
    public CatalogoComunService<CatEstadoEventoCapacitacion, Integer> getEstadoEventoCapacitService() {
        return estadoEventoCapacitService;
    }

    public AreaRecursoService getAreaRecursosService() {
        return areaRecursosService;
    }

    public void setAreaRecursosService(AreaRecursoService areaRecursosService) {
        this.areaRecursosService = areaRecursosService;
    }

    public AreaDistribucionService getAreaDistribucionService() {
        return areaDistribucionService;
    }

    /**
     * @return the estatusReservacionService
     */
    public CatalogoComunService<CatEstatusReservacion, Integer> getEstatusReservacionService() {
        return estatusReservacionService;
    }

    public PersonalizacionAreaService getPersonalizacionAreaService() {
        return personalizacionAreaService;
    }

    public void setPersonalizacionAreaService(PersonalizacionAreaService personalizacionAreaService) {
        this.personalizacionAreaService = personalizacionAreaService;
    }

    /**
     * @return the personalizacionRecursoService
     */
    public PersonalizacionRecursoService getPersonalizacionRecursoService() {
        return personalizacionRecursoService;
    }

    /**
     * @param personalizacionRecursoService the personalizacionRecursoService to
     * set
     */
    public void setPersonalizacionRecursoService(PersonalizacionRecursoService personalizacionRecursoService) {
        this.personalizacionRecursoService = personalizacionRecursoService;
    }

    /**
     * @return the solicitudReservacionService
     */
    public SolicitudReservacionService getSolicitudReservacionService() {
        return solicitudReservacionService;
    }

    /**
     * @param solicitudReservacionService the solicitudReservacionService to set
     */
    public void setSolicitudReservacionService(SolicitudReservacionService solicitudReservacionService) {
        this.solicitudReservacionService = solicitudReservacionService;
    }

    /**
     * @return the reservacionEventoCapacitacionService
     */
    public ReservacionEventoCapacitacionService getReservacionEventoCapacitacionService() {
        return reservacionEventoCapacitacionService;
    }

    /**
     * @param reservacionEventoCapacitacionService the
     * reservacionEventoCapacitacionService to set
     */
    public void setReservacionEventoCapacitacionService(
            ReservacionEventoCapacitacionService reservacionEventoCapacitacionService) {
        this.reservacionEventoCapacitacionService = reservacionEventoCapacitacionService;
    }

    /**
     * @return the solicitudEventoCapacitacionService
     */
    public SolicitudEventoCapacitacionService getSolicitudEventoCapacitacionService() {
        return solicitudEventoCapacitacionService;
    }

    /**
     * @param solicitudEventoCapacitacionService the
     * solicitudEventoCapacitacionService to set
     */
    public void setSolicitudEventoCapacitacionService(
            SolicitudEventoCapacitacionService solicitudEventoCapacitacionService) {
        this.solicitudEventoCapacitacionService = solicitudEventoCapacitacionService;
    }

    /**
     * @return the eventoGeneralService
     */
    public EventoGeneralService getEventoGeneralService() {
        return eventoGeneralService;
    }

    /**
     * @param eventoGeneralService the eventoGeneralService to set
     */
    public void setEventoGeneralService(EventoGeneralService eventoGeneralService) {
        this.eventoGeneralService = eventoGeneralService;
    }

    /**
     * @return the reservacionEventoGeneralService
     */
    public ReservacionEventoGeneralService getReservacionEventoGeneralService() {
        return reservacionEventoGeneralService;
    }

    /**
     * @param reservacionEventoGeneralService the
     * reservacionEventoGeneralService to set
     */
    public void setReservacionEventoGeneralService(ReservacionEventoGeneralService reservacionEventoGeneralService) {
        this.reservacionEventoGeneralService = reservacionEventoGeneralService;
    }

    /**
     * @return the solicitudEventoGeneralService
     */
    public SolicitudEventoGeneralService getSolicitudEventoGeneralService() {
        return solicitudEventoGeneralService;
    }

    /**
     * @param solicitudEventoGeneralService the solicitudEventoGeneralService to
     * set
     */
    public void setSolicitudEventoGeneralService(SolicitudEventoGeneralService solicitudEventoGeneralService) {
        this.solicitudEventoGeneralService = solicitudEventoGeneralService;
    }

    /**
     * @return the catEstatusReservacionService
     */
    public CatalogoComunService<CatEstatusReservacion, Integer> getCatEstatusReservacionService() {
        return catEstatusReservacionService;
    }

    /**
     * @param catEstatusReservacionService the catEstatusReservacionService to
     * set
     */
    public void setCatEstatusReservacionService(
            CatalogoComunService<CatEstatusReservacion, Integer> catEstatusReservacionService) {
        this.catEstatusReservacionService = catEstatusReservacionService;
    }

}
