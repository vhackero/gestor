package mx.gob.sedesol.basegestor.mongo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;
import mx.gob.sedesol.basegestor.commons.dto.admin.NotificacionSistemaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.VariableMensajeOperacionDTO;
import mx.gob.sedesol.basegestor.commons.utils.ConexionMongo;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoNotificacionEnum;
import mx.gob.sedesol.basegestor.model.entities.admin.RelMensajeOperacion;
import mx.gob.sedesol.basegestor.model.repositories.admin.MensajeOperacionRepo;
import mx.gob.sedesol.basegestor.mongo.service.NotificacionSistemaService;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.basegestor.service.admin.PersonaRolesService;

@Service("notificacionSistemaService")
public class NotificacionSistemaServiceImpl implements NotificacionSistemaService {

    private static final Logger LOG = Logger.getLogger(NotificacionServiceImpl.class);

    @Autowired
    private MensajeOperacionRepo mensajeOperacionRepo;

    @Autowired
    private PersonaRolesService personaRolesService;

    @Autowired
    private ParametroSistemaService parametroSistemaService;

    @Override
    public ResultadoDTO<NotificacionSistemaDTO> enviarNotificacion(String claveOperacion, long idUsuarioDestino,
            List<VariableMensajeOperacionDTO> variables) {
        ResultadoDTO<NotificacionSistemaDTO> resultadoDTO = new ResultadoDTO<>();

        List<RelMensajeOperacion> plantillas = mensajeOperacionRepo.obtenerMensajesPorOperacion(claveOperacion, true);

        if (plantillas.isEmpty()) {
            resultadoDTO.setMensajeError(MensajesSistemaEnum.NOTIFICACIONES_SIS_PLANTILLA_ERROR);
        } else {
            resultadoDTO = almacenarNotificacionSistema(procesarPlantilla(plantillas.get(0), variables), idUsuarioDestino);
        }
        return resultadoDTO;
    }

    @Override
    public ResultadoDTO<NotificacionSistemaDTO> enviarNotificacionARolesEspecificos(String claveOperacion, List<Integer> roles,
            List<VariableMensajeOperacionDTO> variables) {

        ResultadoDTO<NotificacionSistemaDTO> resultadoDTO = new ResultadoDTO<>();
        List<RelMensajeOperacion> plantillas = mensajeOperacionRepo.obtenerMensajesPorOperacion(claveOperacion, true);

        if (plantillas.isEmpty()) {
            resultadoDTO.setMensajeError(MensajesSistemaEnum.NOTIFICACIONES_SIS_PLANTILLA_ERROR);
        } else {
            for (Integer idRol : roles) {
                List<PersonaDTO> personas = personaRolesService.obtenerPersonasPorRol(idRol);
                for (PersonaDTO persona : personas) {
                    enviarNotificacion(claveOperacion, persona.getIdPersona(), llenarVaribles(persona, variables));
                }
            }
        }

        return resultadoDTO;
    }

    private List<VariableMensajeOperacionDTO> llenarVaribles(PersonaDTO persona, List<VariableMensajeOperacionDTO> variables) {
        List<VariableMensajeOperacionDTO> variablesNuevas = new ArrayList<>();
        variablesNuevas.addAll(variables);
        variablesNuevas.add(new VariableMensajeOperacionDTO("${nombre_usuario}", persona.getNombreCompleto()));
        return variablesNuevas;
    }

    @Override
    public List<NotificacionSistemaDTO> obtenerNotificaciones(long idUsuario, TipoNotificacionEnum tipo,
            Boolean activo) {
        return obtenerNotificaciones(idUsuario, tipo, activo, false);
    }

    @Override
    public List<NotificacionSistemaDTO> obtenerPrimerasNotificaciones(long idUsuario, TipoNotificacionEnum tipo,
            Boolean activo) {
        return obtenerNotificaciones(idUsuario, tipo, activo, true);
    }

    private List<NotificacionSistemaDTO> obtenerNotificaciones(long idUsuario, TipoNotificacionEnum tipo,
            Boolean activo, boolean limitar) {
        List<NotificacionSistemaDTO> notificaciones = new ArrayList<>();
        BasicDBObject obj = new BasicDBObject();

        if (ObjectUtils.isNotNull(tipo)) {
            obj.put("tipo", tipo.getValor());
        }
        if (ObjectUtils.isNotNull(activo)) {
            obj.put("activo", activo);
        }
        procesarConsultaNotificaciones(notificaciones, obj, idUsuario, limitar);
        return notificaciones;
    }

    @Override
    public ResultadoDTO<NotificacionSistemaDTO> actualizarFechaVisualizacion(long idUsuario, String idMensaje) {
        ResultadoDTO<NotificacionSistemaDTO> resultadoDTO = new ResultadoDTO<>();
        try {
            Document notificacionDocument = new Document()
                    .append("fecha_visualizacion", new Date());

            Document updateDocument = new Document()
                    .append("$set", notificacionDocument);

            ObjectId id = new ObjectId(idMensaje);
            Document actual = new Document()
                    .append("_id", id);

            actualizarNotificacionMongo(updateDocument, actual, idUsuario);
            resultadoDTO.agregaMensaje(MensajesSistemaEnum.NOTIFICACIONES_SIS_EDITAR_FECHA_EXITO.getId());
        } catch (MongoException e) {
            LOG.error(e.getMessage(), e);
            resultadoDTO.setMensajeError(MensajesSistemaEnum.NOTIFICACIONES_SIS_EDITAR_FECHA_ERROR);
        }

        return resultadoDTO;
    }

    @Override
    public ResultadoDTO<NotificacionSistemaDTO> actualizarEstatus(long idUsuario, String idMensaje) {
        ResultadoDTO<NotificacionSistemaDTO> resultadoDTO = new ResultadoDTO<>();
        try {
            Document notificacionDocument = new Document()
                    .append("activo", false);

            Document updateDocument = new Document()
                    .append("$set", notificacionDocument);

            ObjectId id = new ObjectId(idMensaje);
            Document actual = new Document()
                    .append("_id", id);

            actualizarNotificacionMongo(updateDocument, actual, idUsuario);
            resultadoDTO.agregaMensaje(MensajesSistemaEnum.NOTIFICACIONES_SIS_EDITAR_ESTATUS_EXITO.getId());
        } catch (MongoException e) {
            LOG.error(e.getMessage(), e);
            resultadoDTO.setMensajeError(MensajesSistemaEnum.NOTIFICACIONES_SIS_EDITAR_ESTATUS_ERROR);
        }

        return resultadoDTO;
    }

    @Override
    public long obtenerNumeroNotificacionesNuevas(long idUsuario, TipoNotificacionEnum tipo, Boolean activo) {
        BasicDBObject obj = new BasicDBObject();

        obj.put("fecha_visualizacion", null);

        if (ObjectUtils.isNotNull(tipo)) {
            obj.put("tipo", tipo.getValor());
        }
        if (ObjectUtils.isNotNull(activo)) {
            obj.put("activo", activo);
        }

        String collection = ConstantesBitacora.COLECCION_NOTIFICACIONES_SISTEMA_MONGO + idUsuario;
        if (ConexionMongo.obtenerBaseDeDatos(parametroSistemaService) != null) {
            long numeroMensajes = ConexionMongo.obtenerBaseDeDatos(parametroSistemaService).getCollection(collection).count(obj);
            ConexionMongo.cerrarBaseDeDatos();

            return numeroMensajes;
        }
        return 0;
    }

    private ResultadoDTO<NotificacionSistemaDTO> almacenarNotificacionSistema(NotificacionSistemaDTO notificacion, long idUsuario) {
        ResultadoDTO<NotificacionSistemaDTO> resultadoDTO = new ResultadoDTO<>();

        try {

            Document notificacionDocument = new Document();
            notificacionDocument.put("titulo", notificacion.getTitulo());
            notificacionDocument.put("tipo", notificacion.getTipo());
            notificacionDocument.put("mensaje", notificacion.getMensaje());
            notificacionDocument.put("activo", true);
            notificacionDocument.put("fecha_envio", notificacion.getFechaEnvio());
            notificacionDocument.put("fecha_visualizacion", null);

            MongoCollection<Document> collection = ConexionMongo.obtenerBaseDeDatos(parametroSistemaService)
                    .getCollection(ConstantesBitacora.COLECCION_NOTIFICACIONES_SISTEMA_MONGO + idUsuario);

            collection.insertOne(notificacionDocument);
            resultadoDTO.agregaMensaje(MensajesSistemaEnum.NOTIFICACIONES_SIS_ENVIAR_EXITO.getId());
            ConexionMongo.cerrarBaseDeDatos();
        } catch (MongoException | NullPointerException e) {
            LOG.error(e.getMessage(), e);
            resultadoDTO.setMensajeError(MensajesSistemaEnum.NOTIFICACIONES_SIS_ENVIAR_ERROR);
        }
        return resultadoDTO;
    }

    private NotificacionSistemaDTO procesarPlantilla(RelMensajeOperacion plantilla, List<VariableMensajeOperacionDTO> variables) {
        NotificacionSistemaDTO notificacion = new NotificacionSistemaDTO();
        notificacion.setTitulo(remplazarVariables(plantilla.getTitulo(), variables));
        notificacion.setMensaje(remplazarVariables(plantilla.getMensaje(), variables));
        notificacion.setTipo(plantilla.getTipo());
        notificacion.setFechaEnvio(new Date());
        return notificacion;
    }

    private String remplazarVariables(String plantilla, List<VariableMensajeOperacionDTO> variables) {
        String mensaje = plantilla;
        for (VariableMensajeOperacionDTO variable : variables) {
            mensaje = mensaje.replace(variable.getClave(), variable.getValor());
        }
        return mensaje;
    }

    private void procesarConsultaNotificaciones(List<NotificacionSistemaDTO> notificaciones, BasicDBObject obj,
            long idUsuario, boolean limitar) {
        String collection = ConstantesBitacora.COLECCION_NOTIFICACIONES_SISTEMA_MONGO + idUsuario;
        FindIterable<Document> findIterable;
        if (ConexionMongo.obtenerBaseDeDatos(parametroSistemaService) != null) {
            if (obj.isEmpty()) {
                findIterable = ConexionMongo.obtenerBaseDeDatos(parametroSistemaService)
                        .getCollection(collection)
                        .find();
            } else {
                findIterable = ConexionMongo.obtenerBaseDeDatos(parametroSistemaService)
                        .getCollection(collection)
                        .find(obj);
            }
            if (limitar) {
                findIterable.limit(3);
            }
            BasicDBObject ordenamiento = new BasicDBObject();
            ordenamiento.put("fecha_envio", -1);
            findIterable.sort(ordenamiento);
            MongoCursor<Document> cursor = findIterable.iterator();
            while (cursor.hasNext()) {
                Document documento = cursor.next();
                NotificacionSistemaDTO notificacion = new NotificacionSistemaDTO();
                notificacion.setIdNotificacion(documento.get("_id").toString());
                notificacion.setTitulo(documento.get("titulo").toString());
                notificacion.setTipo(Integer.parseInt(documento.get("tipo").toString()));
                notificacion.setMensaje(documento.get("mensaje").toString());
                notificacion.setActivo(Boolean.parseBoolean(documento.get("activo").toString()));
                notificacion.setFechaEnvio(new DateTime(documento.get("fecha_envio")).toDate());
                if (ObjectUtils.isNull(documento.get("fecha_visualizacion"))) {
                    notificacion.setFechaVisualizacion(null);
                } else {
                    notificacion.setFechaVisualizacion(new DateTime(documento.get("fecha_visualizacion")).toDate());
                }
                notificaciones.add(notificacion);
            }
            ConexionMongo.cerrarBaseDeDatos();
        }
    }

    private void actualizarNotificacionMongo(Document datos, Document filtro, long idUsuario) throws MongoException {
        if (ConexionMongo.obtenerBaseDeDatos(parametroSistemaService) != null) {
            MongoCollection<Document> collection = ConexionMongo.obtenerBaseDeDatos(parametroSistemaService)
                    .getCollection(ConstantesBitacora.COLECCION_NOTIFICACIONES_SISTEMA_MONGO + idUsuario);
            collection.updateOne(filtro, datos);
            ConexionMongo.cerrarBaseDeDatos();
        }
    }
}
