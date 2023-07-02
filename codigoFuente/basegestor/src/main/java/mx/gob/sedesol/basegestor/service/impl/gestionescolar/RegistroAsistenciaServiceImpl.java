package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AsistenciaAuxDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AsistenciaXML;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.CatAsistenciaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.DiasCapAuxDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.GrupoParticipanteXML;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.GrupoXML;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ParticipanteXML;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelAsistenciaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelDiasEventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelGrupoParticipanteDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.GrupoDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAsistenciaEnum;
import mx.gob.sedesol.basegestor.service.ServiceException;
import mx.gob.sedesol.basegestor.service.gestionescolar.CatAsistenciaService;
import mx.gob.sedesol.basegestor.service.gestionescolar.EventoCapacitacionService;
import mx.gob.sedesol.basegestor.service.gestionescolar.GrupoParticipanteService;
import mx.gob.sedesol.basegestor.service.gestionescolar.GrupoService;
import mx.gob.sedesol.basegestor.service.gestionescolar.RegistroAsistenciaService;
import mx.gob.sedesol.basegestor.service.gestionescolar.RelAsistenciaService;
import mx.gob.sedesol.basegestor.service.gestionescolar.RelDiasEventoCapacitacionService;

@Service("registroAsistenciaService")
public class RegistroAsistenciaServiceImpl implements RegistroAsistenciaService {

    private static final Logger logger = Logger.getLogger(RegistroAsistenciaServiceImpl.class);

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private GrupoParticipanteService grupoParticipanteService;

    @Autowired
    private RelAsistenciaService relAsistenciaService;

    @Autowired
    private RelDiasEventoCapacitacionService relDiasEventoCapacitacionService;

    @Autowired
    private CatAsistenciaService catAsistenciaService;

    private List<CatAsistenciaDTO> catAsistencia;

    @Autowired
    private EventoCapacitacionService eventoCapacitacionService;

//    @Override
//    public List<CatAsistenciaDTO> getCatAsistencia(){
//    	
//    	if (catAsistencia == null){
//    		catAsistencia = catAsistenciaService.getCatAsistencia();
//
//    	}
//        
//    	return  catAsistencia;
//    }
    /**
     *
     * @param idEvento
     * @return
     */
    public List<CatAsistenciaDTO> getCatAsistencia(Integer idEvento) {

        EventoCapacitacionDTO evtoCap = eventoCapacitacionService.getEvento(idEvento);
        catAsistencia = catAsistenciaService.getCatAsistencia();

        if (!ObjectUtils.isNullOrEmpty(catAsistencia) && ObjectUtils.isNotNull(evtoCap)) {
            for (CatAsistenciaDTO asist : catAsistencia) {

                TipoAsistenciaEnum tpoAsitEnm = TipoAsistenciaEnum.getTipoAsistenciaEnum(asist.getNombre());
                switch (tpoAsitEnm) {

                    case ASISTENCIA:
                        asist.setValor(evtoCap.getValorAsistencia());
                        break;

                    case RETARDO:
                        asist.setValor(evtoCap.getValorRetardo());
                        break;

                    case FALTA_JUSTIFICADA:
                        asist.setValor(evtoCap.getValorFaltaJustificada());
                        break;

                    case FALTA:
                        asist.setValor(evtoCap.getValorFalta());
                        break;

                    case INCONCLUSO:
                        asist.setValor(evtoCap.getValorInconcluso());
                        break;

                }
            }
        }
        return catAsistencia;
    }

    /**
     * Inicializa el arreglo de asistencias del participante
     */
    @Override
    public void resetAsistenciaPartipante(List<RelGrupoParticipanteDTO> lista) {

        List<AsistenciaAuxDTO> asistencias;
        for (RelGrupoParticipanteDTO participante : lista) {

            asistencias = participante.getAsistencias();
            asistencias.clear();
        }
    }

    /**
     * Carga el arreglo de asistencias de cada participante
     */
    @Override
    public void crearArregloAsistenciasXparticipante(List<RelDiasEventoCapacitacionDTO> diasEvento,
             List<RelGrupoParticipanteDTO> participantes, List<RelAsistenciaDTO> asistencias) {

        List<DiasCapAuxDTO> cabezeraDiasEvento = new ArrayList<>();

        for (RelDiasEventoCapacitacionDTO diaEvento : diasEvento) {
            DiasCapAuxDTO diasCapAuxDTO = new DiasCapAuxDTO();

            diasCapAuxDTO.setId(diaEvento.getId());
            diasCapAuxDTO.setFechaEventoCapacitacion(diaEvento.getFechaEventoCapacitacion());
            diasCapAuxDTO.setFechaEventoFormat(diaEvento.getFechaEventoFormat());

            cabezeraDiasEvento.add(diasCapAuxDTO);
        }

        for (RelGrupoParticipanteDTO participante : participantes) {
            List<RelAsistenciaDTO> asistenciaXparti = getAsistenciaByIdParticipante(participante.getId(), asistencias);
            //TODO: ADECUACION PONDERACION ASISTENCIAS
            List<AsistenciaAuxDTO> lstAsistXparticipante = createArregloAsistenciasXparticipante(participante.getGrupo().getIdEventoTemp(), participante.getId(), cabezeraDiasEvento, asistenciaXparti);
            participante.setAsistencias(lstAsistXparticipante);
        }

        calcularPorcentajeAsistencia(participantes);

    }

    public void calcularPorcentajeAsistencia(List<RelGrupoParticipanteDTO> grupoParticipantes) {

        int sumValorAsistencia;
        int countAsistencia;
        BigDecimal bgSumValor;
        BigDecimal bgCountAsis;

        for (RelGrupoParticipanteDTO participante : grupoParticipantes) {

            countAsistencia = 0;
            sumValorAsistencia = 0;

            for (AsistenciaAuxDTO asistencia : participante.getAsistencias()) {

                //si se tiene un cero es que no esta registrado en la base de datos
                if (asistencia.getIdtipoAsistencia() > 0) {
                    sumValorAsistencia += asistencia.getValor();
                    ++countAsistencia;
                }

            }//fin for asistencias

            bgSumValor = new BigDecimal(sumValorAsistencia);
            bgCountAsis = new BigDecimal(countAsistencia);

            BigDecimal bg5 = BigDecimal.ZERO;

            if (countAsistencia > 0) {
                bg5 = bgSumValor.divide(bgCountAsis, 0, RoundingMode.CEILING);
            }

            participante.setPorcentajeAsistencia(bg5);
        }//fin for grupo

    }

    /**
     * Se obtiene la asistecnia por cada participante de la lista ASISTENCIAS
     * (en donde se engloban todas las asistencias de las personas del grupo)
     */
    private List<RelAsistenciaDTO> getAsistenciaByIdParticipante(Integer idGrupoParticipante, List<RelAsistenciaDTO> asistencias) {
        List<RelAsistenciaDTO> lista = new ArrayList<>();

        for (RelAsistenciaDTO relAsistenciaDTO : asistencias) {
            if (idGrupoParticipante == relAsistenciaDTO.getIdGrupoParticipante()) {
                lista.add(relAsistenciaDTO);
            }
        }

        return lista;

    }

    /**
     *
     * Se crea un arreglo de asitencias por participante
     */
    private List<AsistenciaAuxDTO> createArregloAsistenciasXparticipante(int idEventoCap, int idGrupoParticipante, List<DiasCapAuxDTO> cabezeraDiasEvento, List<RelAsistenciaDTO> asistencias) {
        List<AsistenciaAuxDTO> lstAsistenciaAux = new ArrayList<>();

        for (int x = 0; x < cabezeraDiasEvento.size(); x++) {
            RelAsistenciaDTO relAsistenciaDTO = getAsistenciaDTOByIdDiaCapacitacion(cabezeraDiasEvento.get(x).getId(), asistencias);
            AsistenciaAuxDTO asistenciaAux = new AsistenciaAuxDTO();

            if (relAsistenciaDTO != null) {

                asistenciaAux.setIdDiaCapacitacion(cabezeraDiasEvento.get(x).getId());
                asistenciaAux.setFechaEventoCapacitacion(cabezeraDiasEvento.get(x).getFechaEventoCapacitacion());
                asistenciaAux.setFechaEventoFormat(cabezeraDiasEvento.get(x).getFechaEventoFormat());
                asistenciaAux.setIdtipoAsistencia(relAsistenciaDTO.getIdTpoAsistencia());
                asistenciaAux.setTipoAsistencia(getTipoAsistencia(relAsistenciaDTO.getIdTpoAsistencia()));
                asistenciaAux.setIdGrupoParticipante(idGrupoParticipante);
                asistenciaAux.setId(relAsistenciaDTO.getId());
                asistenciaAux.setValor(getValorAsistencia(relAsistenciaDTO.getIdTpoAsistencia()));

            } else {
                asistenciaAux.setIdDiaCapacitacion(cabezeraDiasEvento.get(x).getId());
                asistenciaAux.setFechaEventoCapacitacion(cabezeraDiasEvento.get(x).getFechaEventoCapacitacion());
                asistenciaAux.setFechaEventoFormat(cabezeraDiasEvento.get(x).getFechaEventoFormat());
                asistenciaAux.setIdtipoAsistencia(0);
                asistenciaAux.setTipoAsistencia("");
                asistenciaAux.setIdGrupoParticipante(idGrupoParticipante);
                asistenciaAux.setId(0);

            }
            lstAsistenciaAux.add(asistenciaAux);
        }

        return lstAsistenciaAux;

    }

    @Override
    public String getTipoAsistencia(int idTipoAsistencia) {

        String tipoAsistencia = "";

        for (CatAsistenciaDTO catAsistenciaDTO : catAsistencia) {
            if (catAsistenciaDTO.getId() == idTipoAsistencia) {
                tipoAsistencia = catAsistenciaDTO.getNombre();
                break;
            }
        }

        return tipoAsistencia;

    }

    private int getValorAsistencia(int idTipoAsistencia) {

        int valor = 0;

        for (CatAsistenciaDTO catAsistenciaDTO : catAsistencia) {
            if (catAsistenciaDTO.getId() == idTipoAsistencia) {
                valor = catAsistenciaDTO.getValor();
                break;
            }
        }

        return valor;

    }

    /**
     *
     * Se busca el tipo de asistencia que tiene en un determiando
     * DIA_DE_CAPACITACION
     */
    private RelAsistenciaDTO getAsistenciaDTOByIdDiaCapacitacion(int idDiaEvento, List<RelAsistenciaDTO> asistencias) {
        RelAsistenciaDTO relAsistenciaDTO = null;

        for (RelAsistenciaDTO asistencia : asistencias) {

            if (asistencia.getIdDiasEventoCap() == idDiaEvento) {
                relAsistenciaDTO = asistencia;
                break;
            }

        }

        return relAsistenciaDTO;

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public ResultadoDTO guardarRelAsistencia(long usuarioSesion, List<RelGrupoParticipanteDTO> grupoParticipantes) throws ServiceException {
        ResultadoDTO resultado = new ResultadoDTO();

        logger.info("************************guardarRelAsistencia****************************");

        try {
            for (RelGrupoParticipanteDTO grupoParticipante : grupoParticipantes) {

                guardarRelAsistenciaParticipante(usuarioSesion, grupoParticipante);
            }
            resultado.setMensaje("Asistencias guardadas exitosamente");
            resultado.setResultado(ResultadoTransaccionEnum.EXITOSO);

        } catch (ServiceException e) {
            resultado.setMensaje("Ha ocurrido un error " + e.toString());
            resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
            throw e;
        }

        return resultado;
    }

    private void guardarRelAsistenciaParticipante(long usuarioSesion, RelGrupoParticipanteDTO grupoParticipante) throws ServiceException {
        try {

            for (AsistenciaAuxDTO asistenciaAuxDTO : grupoParticipante.getAsistencias()) {

                asistenciaAuxDTO.setFechaActualizacion(new Date());
                asistenciaAuxDTO.setFechaRegistro(new Date());
                asistenciaAuxDTO.setUsuarioModifico(usuarioSesion);

                //solo si se selecciono un tipo de asistencia se guarda
                if (asistenciaAuxDTO.getIdtipoAsistencia() > 0) {
                    relAsistenciaService.save(asistenciaAuxDTO);
                }
            }
        } catch (Exception e) {
            logger.error(e.toString());
            throw new ServiceException(e.toString(), e);
        }
    }

    @Override
    public ResultadoDTO<RelDiasEventoCapacitacionDTO> agregarDiaEvento(RelDiasEventoCapacitacionDTO eventoDiaDTO) {

        ResultadoDTO resultado = new ResultadoDTO();

        try {

            if (!relDiasEventoCapacitacionService.existeDiaEvento(eventoDiaDTO.getFechaEventoCapacitacion(), eventoDiaDTO.getGrupo().getIdGrupo())) {

                relDiasEventoCapacitacionService.save(eventoDiaDTO);

                resultado.setMensaje("Dia agregado correctamente");
                resultado.setResultado(ResultadoTransaccionEnum.EXITOSO);

            } else {
                resultado.setMensaje("Ya existe un dia registrado con la fecha proporcionada");
                resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            resultado.setMensaje("Error al guardar");
            resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
            throw e;
        }

        return resultado;

    }

    @Override
    public String getXML(int idEvento) {

        getCatAsistencia(idEvento);
        GrupoParticipanteXML grupoParticipanteXML = consultarGruposXML(idEvento);
        return convertDTOtoXML(grupoParticipanteXML);

    }

    private String convertDTOtoXML(GrupoParticipanteXML grupoParticipanteXML) {

        int secuencia;

        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("<RelAsistencias>\n");
        sBuilder.append("	<grupos>\n");

        for (GrupoXML grupoXML : grupoParticipanteXML.getGrupos()) {
            sBuilder.append("	<grupo>\n");
            sBuilder.append("		<descripcion>").append(grupoXML.getDescripcion()).append("</descripcion>\n");
            sBuilder.append("		<idGrupo>").append(grupoXML.getIdGrupo()).append("</idGrupo>\n");
            sBuilder.append("		<participantes>\n");
            secuencia = 0;
            for (ParticipanteXML participanteXML : grupoXML.getParticipantes()) {

                sBuilder.append("			<participante>\n");
                sBuilder.append("				<idGrupo>").append(grupoXML.getIdGrupo()).append("</idGrupo>\n");
                sBuilder.append("				<secuencia>").append(++secuencia).append("</secuencia>\n");
                sBuilder.append("				<nombre>").append(participanteXML.getNombre()).append("</nombre>\n");
                sBuilder.append("				<apellidoPaterno>").append(participanteXML.getApellidoPaterno()).append("</apellidoPaterno>\n");
                sBuilder.append("				<apellidoMaterno>").append(participanteXML.getApellidoMaterno()).append("</apellidoMaterno>\n");

                for (AsistenciaXML asistenciaXML : participanteXML.getAsistencia()) {
                    sBuilder.append("				<dia").append(asistenciaXML.getSecuencia()).append(">").append(asistenciaXML.getDia()).append("</dia").append(asistenciaXML.getSecuencia()).append(">\n");
                    sBuilder.append("				<tipoAsistencia").append(asistenciaXML.getSecuencia()).append(">").append(asistenciaXML.getTipoAsistencia()).append("</tipoAsistencia").append(asistenciaXML.getSecuencia()).append(">\n");
                }
                sBuilder.append("				<porcentaje>").append(participanteXML.getPorcentaje()).append("</porcentaje>\n");
                sBuilder.append("			</participante>\n");
            }
            sBuilder.append("	</participantes>\n");
            sBuilder.append("	</grupo>\n");
        }

        sBuilder.append("	</grupos>\n");
        sBuilder.append("</RelAsistencias>\n");

        logger.info("***XML " + sBuilder.toString());

        return sBuilder.toString();
    }

    private GrupoParticipanteXML consultarGruposXML(int idEvento) {
        List<GrupoDTO> grupos = grupoService.getGruposByEvento(idEvento);
        List<GrupoXML> gruposXML = new ArrayList<>();
        GrupoParticipanteXML grupoParticipanteXML = new GrupoParticipanteXML();

        if (!ObjectUtils.isNullOrEmpty(grupos)) {

            for (GrupoDTO grupo : grupos) {
                GrupoXML grupoXML = new GrupoXML();
                grupoXML.setIdGrupo(grupo.getIdGrupo());
                grupoXML.setDescripcion(grupo.getNombre());

                List<RelGrupoParticipanteDTO> grupoParticipantes = getGrupoParticipante(grupo.getIdEventoTemp(), grupo.getIdGrupo());
                List<ParticipanteXML> lstParticipanteXML = getParticipantesXML(grupo.getIdGrupo(), grupoParticipantes);

                grupoXML.setParticipantes(lstParticipanteXML);
                gruposXML.add(grupoXML);
            }

            grupoParticipanteXML.setGrupos(gruposXML);
        }

        return grupoParticipanteXML;

    }

    /*
	 * Mapear la asistencai de cada participante
     */
    private List<ParticipanteXML> getParticipantesXML(int idGrupo, List<RelGrupoParticipanteDTO> grupoParticipantes) {
        List<ParticipanteXML> lstParticipanteXML = new ArrayList<>();
        int numReg = 0;

        for (RelGrupoParticipanteDTO grupoParticipanteDTO : grupoParticipantes) {
            ParticipanteXML participanteXML = new ParticipanteXML();
            participanteXML.setIdGrupo(idGrupo);
            participanteXML.setNombre(grupoParticipanteDTO.getPersona().getNombre());
            participanteXML.setApellidoPaterno(grupoParticipanteDTO.getPersona().getApellidoPaterno());
            participanteXML.setApellidoMaterno(grupoParticipanteDTO.getPersona().getApellidoMaterno());
            participanteXML.setPorcentaje(grupoParticipanteDTO.getPorcentajeAsistencia());
            participanteXML.setSecuencia(++numReg);

            List<AsistenciaXML> asistenciasXML = new ArrayList<>();

            for (int x = 0; x < grupoParticipanteDTO.getAsistencias().size(); x++) {
                AsistenciaXML asistenciaXML = mapearAsistencia(x + 1, grupoParticipanteDTO.getAsistencias().get(x));
                asistenciasXML.add(asistenciaXML);
            }

            participanteXML.setAsistencia(asistenciasXML);
            lstParticipanteXML.add(participanteXML);
        }

        return lstParticipanteXML;

    }

    private AsistenciaXML mapearAsistencia(int indice, AsistenciaAuxDTO asistenciaAuxDTO) {

        AsistenciaXML asistenciaXML = new AsistenciaXML();
        asistenciaXML.setSecuencia(indice < 9 ? "0" + indice : "" + Integer.toString(indice));
        asistenciaXML.setDia(asistenciaAuxDTO.getFechaEventoFormat());
        asistenciaXML.setTipoAsistencia(asistenciaAuxDTO.getTipoAsistencia());

        return asistenciaXML;

    }

    public List<RelGrupoParticipanteDTO> getGrupoParticipante(int idEvento, int idGrupo) {
        List<Integer> lisIdGrupoParticipante = new ArrayList<>();
        List<RelGrupoParticipanteDTO> grupoParticipantes;
        List<RelAsistenciaDTO> asistencias = null;
        List<RelDiasEventoCapacitacionDTO> diasEvento;

        if (ObjectUtils.isNull(catAsistencia)) {
            getCatAsistencia(idEvento);
        }

        grupoParticipantes = grupoParticipanteService.getParticipantesByGrupo(idGrupo);

        if (!ObjectUtils.isNullOrEmpty(grupoParticipantes)) {

            /**
             * se genera una lista del ID_GRUPO_PARTICPANTE para generar un
             * SELECT IN(lista) de la tabla de asistencias
             */
            for (RelGrupoParticipanteDTO relGrupoParticipanteDTO : grupoParticipantes) {
                lisIdGrupoParticipante.add(relGrupoParticipanteDTO.getId());
            }

            /**
             * Obtienen las asistencias de cada participante
             */
            if (!ObjectUtils.isNullOrEmpty(lisIdGrupoParticipante)) {
                asistencias = relAsistenciaService.getAsistenciaByIdGrupoParticipante(lisIdGrupoParticipante);
            }

            diasEvento = relDiasEventoCapacitacionService.getDiasEventoByGrupo(idGrupo);

            /**
             * Carga el arreglo de asistencias de cada participante
             */
            this.crearArregloAsistenciasXparticipante(diasEvento, grupoParticipantes, asistencias);

        }

        return grupoParticipantes;

    }

    @Override
    public CatalogoComunDTO getEjeCapacitacion(int idEjeCapacitacion, List<CatalogoComunDTO> ejesCapacitacion) {

        CatalogoComunDTO ejeCapacitacion = null;

        for (CatalogoComunDTO catalogoComunDTO : ejesCapacitacion) {

            if (catalogoComunDTO.getId() == idEjeCapacitacion) {
                ejeCapacitacion = catalogoComunDTO;
            }
        }

        return ejeCapacitacion;
    }

    public GrupoService getGrupoService() {
        return grupoService;
    }

    public void setGrupoService(GrupoService grupoService) {
        this.grupoService = grupoService;
    }

    public RelDiasEventoCapacitacionService getRelDiasEventoCapacitacionService() {
        return relDiasEventoCapacitacionService;
    }

    public void setRelDiasEventoCapacitacionService(RelDiasEventoCapacitacionService relDiasEventoCapacitacionService) {
        this.relDiasEventoCapacitacionService = relDiasEventoCapacitacionService;
    }

    public GrupoParticipanteService getGrupoParticipanteService() {
        return grupoParticipanteService;
    }

    public void setGrupoParticipanteService(GrupoParticipanteService grupoParticipanteService) {
        this.grupoParticipanteService = grupoParticipanteService;
    }

    public RelAsistenciaService getRelAsistenciaService() {
        return relAsistenciaService;
    }

    public void setRelAsistenciaService(RelAsistenciaService relAsistenciaService) {
        this.relAsistenciaService = relAsistenciaService;
    }
}
