package mx.gob.sedesol.basegestor.mongo.service.impl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.admin.DestinatarioDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.InformacionMensajeDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.MensajeDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.MensajeResumenDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.NotificacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.mongo.model.Destinatario;
import mx.gob.sedesol.basegestor.mongo.model.Mensaje;
import mx.gob.sedesol.basegestor.mongo.model.Notificacion;
import mx.gob.sedesol.basegestor.mongo.repositories.NotificacionRepo;
import mx.gob.sedesol.basegestor.mongo.service.NotificacionService;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.admin.PersonaService;

@Service("notificacionService")
@EnableAsync
public class NotificacionServiceImpl extends ComunValidacionService<NotificacionDTO> implements NotificacionService {

    @Autowired
    private NotificacionRepo notificacionRepo;

    @Autowired
    private PersonaService personaService;

    @Autowired
    private MongoTemplate mongoTemplate;

    private final ModelMapper modelMapper = new ModelMapper();

    private static final Type tipoListaNotificacion = new TypeToken<List<NotificacionDTO>>() {
    }.getType();

    private static final Type tipoListaDestinatario = new TypeToken<List<Destinatario>>() {
    }.getType();

    private final Sort sort = new Sort(Sort.Direction.DESC, "mensajes.fecha");

    private final PageRequest page = new PageRequest(0, 3, sort);

    @Override
    public ResultadoDTO<NotificacionDTO> guardar(NotificacionDTO dto) {
        ResultadoDTO<NotificacionDTO> resultado = validarNotificacion(dto);
        if (ObjectUtils.isNotNull(resultado) && resultado.esCorrecto()) {
            dto.setEnviado(true);
            dto.getDestinatarios().add(new DestinatarioDTO(dto.getUsuarioModifico(), false));

            Notificacion notificacion = modelMapper.map(dto, Notificacion.class);
            notificacionRepo.save(notificacion);

            //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), notificacion.getId());
            resultado.agregaMensaje(MensajesSistemaEnum.NOTIFICACIONES_ENVIAR_EXITO.getId());
        }
        return resultado;
    }

    @Override
    public ResultadoDTO<NotificacionDTO> guardarBorrador(NotificacionDTO dto) {
        ResultadoDTO<NotificacionDTO> resultado = new ResultadoDTO<>();

        Notificacion notificacion = modelMapper.map(dto, Notificacion.class);
        notificacionRepo.save(notificacion);

        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO(), notificacion.getId());
        resultado.agregaMensaje(MensajesSistemaEnum.NOTIFICACIONES_GUARDAR_BORRADOR_EXITO.getId());

        return resultado;
    }

    @Override
    public ResultadoDTO<NotificacionDTO> actualizarEstatusNuevo(String idNotificacion, long idPersona) {
        ResultadoDTO<NotificacionDTO> resultado = new ResultadoDTO<>();

        Query query = new Query();
        Update update = new Update();

        query.addCriteria(Criteria.where("_id").is(new ObjectId(idNotificacion)));
        query.addCriteria(Criteria.where("destinatarios.idPersona").is(idPersona));

        update.set("destinatarios.$.nuevo", false);

        mongoTemplate.updateFirst(query, update, Notificacion.class);

        resultado.agregaMensaje(MensajesSistemaEnum.NOTIFICACIONES_ACTUALIZAR_NUEVO_EXITO.getId());
        
        return resultado;
    }

    @Override
    public ResultadoDTO<NotificacionDTO> actualizarActivo(String idNotificacion, long idPersona) {
        ResultadoDTO<NotificacionDTO> resultado = new ResultadoDTO<>();

        Query query = new Query();
        Update update = new Update();

        query.addCriteria(Criteria.where("_id").is(new ObjectId(idNotificacion)));
        query.addCriteria(Criteria.where("destinatarios.idPersona").is(idPersona));

        update.set("destinatarios.$.activo", false);

        mongoTemplate.updateFirst(query, update, Notificacion.class);

        resultado.agregaMensaje(MensajesSistemaEnum.NOTIFICACIONES_ACTUALIZAR_ACTIVO_EXITO.getId());

        return resultado;
    }

    @Override
    public ResultadoDTO<NotificacionDTO> agregarMensaje(NotificacionDTO notificacion, MensajeDTO mensajeDTO) {
        ResultadoDTO<NotificacionDTO> resultado = validarMensaje(mensajeDTO);
        if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
            Mensaje mensaje = modelMapper.map(mensajeDTO, Mensaje.class);

            Query query = new Query();
            Update update = new Update();

            List<Destinatario> destinatarios = modelMapper.map(notificacion.getDestinatarios(), tipoListaDestinatario);

            query.addCriteria(Criteria.where("_id").is(new ObjectId(notificacion.getId())));

            update.push("mensajes", mensaje);
            update.set("destinatarios", destinatarios);
            mongoTemplate.updateFirst(query, update, Notificacion.class);
            //GUSTAVO --guardarBitacora(notificacion.getBitacoraDTO(), notificacion.getId());
            resultado.agregaMensaje(MensajesSistemaEnum.NOTIFICACIONES_RESPUESTA_EXITO.getId());
        }
        return resultado;
    }

    @Override
    public NotificacionDTO obtenerNotificacion(String id, Map<Long, PersonaDTO> mapa, long idPersona) {
        NotificacionDTO dto = null;

        Notificacion notificacion = notificacionRepo.findOne(id);
        if (ObjectUtils.isNotNull(notificacion)) {
            dto = modelMapper.map(notificacion, NotificacionDTO.class);
            procesarPersonasNotificacion(dto, mapa, idPersona);
        }
        return dto;
    }

    @Override
    public List<NotificacionDTO> obtenerNotificacionesPorRemitente(Long idPersona, boolean enviado, Map<Long, PersonaDTO> mapa) {
        List<NotificacionDTO> notificaciones = new ArrayList<>();

        List<Notificacion> entidades = notificacionRepo.findByRemitente(idPersona, enviado);

        if (ObjectUtils.isNotNull(entidades)) {
            notificaciones = modelMapper.map(entidades, tipoListaNotificacion);
        }
        procesarPersonasNotificaciones(notificaciones, mapa, idPersona);

        return notificaciones;
    }

    @Override
    public List<NotificacionDTO> obtenerNotificacionesPorDestinatario(Long idPersona, boolean activo, Map<Long, PersonaDTO> mapa) {
        List<NotificacionDTO> notificaciones = new ArrayList<>();

        List<Notificacion> entidades = notificacionRepo.findByDestinatario(idPersona, activo, sort);

        if (ObjectUtils.isNotNull(entidades)) {
            notificaciones = modelMapper.map(entidades, tipoListaNotificacion);
        }
        procesarPersonasNotificaciones(notificaciones, mapa, idPersona);

        return notificaciones;
    }

    @Override
    public InformacionMensajeDTO obtenerNumeroMensajesNuevos(long idPersona) {
        InformacionMensajeDTO info = new InformacionMensajeDTO();
        List<Notificacion> entidades = notificacionRepo.findByDestinatarioNuevo(idPersona);
        info.setNumeroMensajes(entidades.size());
        return info;
    }

    @Override
    public List<MensajeResumenDTO> obtenerPrimerosMensajes(Long idPersona) {
        List<MensajeResumenDTO> mensajes = new ArrayList<>();

        List<Notificacion> entidades = notificacionRepo.findByDestinatario(idPersona, true, page);

        if (ObjectUtils.isNotNull(entidades)) {
            procesarMensajes(mensajes, entidades, idPersona);
        }

        return mensajes;
    }

    /**
     * Valida que una notificacion contenga los datos necesarios
     *
     * @param dto
     * @return
     */
    private ResultadoDTO<NotificacionDTO> validarNotificacion(NotificacionDTO dto) {
        ResultadoDTO<NotificacionDTO> resultado = new ResultadoDTO<>();

        if (ObjectUtils.isNotNull(dto)) {
            resultado = new ResultadoDTO<>();
            if (ObjectUtils.isNullOrEmpty(dto.getTitulo())) {
                resultado.setMensajeError(MensajesSistemaEnum.NOTIFICACIONES_TITULO_REQ);
            }
            if (ObjectUtils.isNullOrEmpty(dto.getMensajes()) || ObjectUtils.isNullOrEmpty(dto.getMensajes().get(0).getContenido())) {
                resultado.setMensajeError(MensajesSistemaEnum.NOTIFICACIONES_MENSAJE_REQ);
            }
            if (ObjectUtils.isNullOrEmpty(dto.getDestinatarios())) {
                resultado.setMensajeError(MensajesSistemaEnum.NOTIFICACIONES_DESTINATARIO_REQ);
            }
        } else {
            resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
        }
        return resultado;
    }

    /**
     * Valida que un mensaje tenga contenido
     *
     * @param mensaje
     * @return
     */
    private ResultadoDTO<NotificacionDTO> validarMensaje(MensajeDTO mensaje) {
        ResultadoDTO<NotificacionDTO> resultado = new ResultadoDTO<>();

        if (ObjectUtils.isNull(mensaje) || ObjectUtils.isNullOrEmpty(mensaje.getContenido())) {
            resultado.setMensajeError(MensajesSistemaEnum.NOTIFICACIONES_MENSAJE_REQ);
        }

        return resultado;
    }

    /**
     * llena los datos de un mensaje
     *
     * @param mensajes
     * @param entidades
     * @param idPersona
     */
    private void procesarMensajes(List<MensajeResumenDTO> mensajes, List<Notificacion> entidades, long idPersona) {
        for (Notificacion notificacion : entidades) {
            MensajeResumenDTO mensaje = new MensajeResumenDTO();
            mensaje.setTitulo(notificacion.getTitulo());
            Collections.reverse(notificacion.getMensajes());
            mensaje.setId(notificacion.getId());
            mensaje.setFecha(notificacion.getMensajes().get(0).getFecha());
            mensaje.setMensaje(notificacion.getMensajes().get(0).getContenido());
            mensaje.setRemitente(obtenerNombreRemitente(notificacion.getMensajes().get(0).getRemitente()));

            for (Destinatario destinatario : notificacion.getDestinatarios()) {
                if (destinatario.getIdPersona() == idPersona && destinatario.isNuevo()) {
                    mensaje.setNuevo(true);
                    break;
                }
            }
            mensajes.add(mensaje);
        }
    }

    /**
     * obtiene de la base el nombre completo de una persona
     *
     * @param idPersona
     * @return
     */
    private String obtenerNombreRemitente(long idPersona) {
        PersonaDTO persona = personaService.buscarPorId(idPersona);
        if (ObjectUtils.isNotNull(persona)) {
            return persona.getNombreCompleto();
        } else {
            return "";
        }
    }

    /**
     * obtiene el nombre completo de una persona, si existe en el mapa lo toma
     * de ahi, si no de la base
     *
     * @param idPersona
     * @param mapa
     * @return
     */
    private String obtenerNombreRemitente(long idPersona, Map<Long, PersonaDTO> mapa) {
        PersonaDTO persona = mapa.get(idPersona);
        if (ObjectUtils.isNull(persona)) {
            return obtenerNombreRemitente(idPersona);
        } else {
            return persona.getNombreCompleto();
        }
    }

    /**
     * recorre las notificaciones y llama a su procesamiento
     *
     * @param notificaciones
     * @param mapa
     * @param idPersona
     */
    private void procesarPersonasNotificaciones(List<NotificacionDTO> notificaciones, Map<Long, PersonaDTO> mapa, long idPersona) {
        for (NotificacionDTO notificacion : notificaciones) {
            procesarPersonasNotificacion(notificacion, mapa, idPersona);
        }
    }

    /**
     * Llena los nombres de todos los participantes de las notificaciones los
     * datos los obtiene del mapa, tambien valida si es una notificacion nueva
     * para el usuario
     *
     * @param notificacion
     * @param mapa
     * @param idPersona
     */
    private void procesarPersonasNotificacion(NotificacionDTO notificacion, Map<Long, PersonaDTO> mapa, long idPersona) {
        Collections.reverse(notificacion.getMensajes());
        for (DestinatarioDTO destinatario : notificacion.getDestinatarios()) {
            destinatario.setNombreCompleto(obtenerNombreRemitente(destinatario.getIdPersona(), mapa));
            if (destinatario.getIdPersona() == idPersona) {
                notificacion.setNuevo(destinatario.isNuevo());
            }
        }
        for (MensajeDTO mensaje : notificacion.getMensajes()) {
            mensaje.setNombreCompleto(obtenerNombreRemitente(mensaje.getRemitente(), mapa));
        }
    }

    @Override
    public void validarPersistencia(NotificacionDTO dto, ResultadoDTO<NotificacionDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarActualizacion(NotificacionDTO dto, ResultadoDTO<NotificacionDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarEliminacion(NotificacionDTO dto, ResultadoDTO<NotificacionDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
