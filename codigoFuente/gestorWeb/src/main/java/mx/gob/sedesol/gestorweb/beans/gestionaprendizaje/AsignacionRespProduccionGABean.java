package mx.gob.sedesol.gestorweb.beans.gestionaprendizaje;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.log4j.Logger;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;
import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.NotificacionSistemaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.VariableMensajeOperacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.AmbienteVirtualAprendizajeDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.ReponsableProduccionOaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.UnidadOaAvaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.PersonaResponsabilidadesDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.mongo.service.NotificacionSistemaService;
import mx.gob.sedesol.basegestor.service.gestion.aprendizaje.ReponsableProduccionOaService;
import mx.gob.sedesol.basegestor.service.gestionescolar.PersonaResponsabilidadesService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.beans.administracion.BitacoraBean;
import mx.gob.sedesol.gestorweb.beans.administracion.CorreoNotificacionBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.dto.UsuarioSessionDTO;
import mx.gob.sedesol.gestorweb.sistema.SistemaBean;

@ManagedBean
@ViewScoped
public class AsignacionRespProduccionGABean extends BaseBean {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(AsignacionRespProduccionGABean.class);

	@ManagedProperty("#{correoNotificacionBean}")
	private CorreoNotificacionBean correoNotificacionBean;
	
	@ManagedProperty("#{bitacoraBean}")
	private BitacoraBean bitacoraBean;

	/**
	 * Inyeccion de reponsableProduccionOaService
	 */
	@ManagedProperty(value = "#{reponsableProduccionOaService}")
	private ReponsableProduccionOaService reponsableProduccionOaService;

	/**
	 * Inyeccion de personaResponsabilidadesService
	 */
	@ManagedProperty(value = "#{personaResponsabilidadesService}")
	private PersonaResponsabilidadesService personaResponsabilidadesService;

	/**
	 * Inyeccion del servicio de notificaciones
	 */
	@ManagedProperty(value = "#{notificacionSistemaService}")
	private NotificacionSistemaService notificacionSistemaService;

	/**
	 * Inyeccion del servicio de sistema
	 */
	@ManagedProperty(value = "#{sistema}")
	private SistemaBean sistema;

	private UnidadOaAvaDTO objetoAprendizajeSeleccionado;

	private List<CatalogoComunDTO> responsabilidades;

	private List<PersonaResponsabilidadesDTO> respProdExpContenidoList;

	private List<PersonaResponsabilidadesDTO> respProdDisInstList;

	private List<PersonaResponsabilidadesDTO> respProdDisElearningList;

	private List<PersonaResponsabilidadesDTO> respProdDesaElearningList;

	private List<Integer> idTipoResponsabilidades;

	private List<PersonaResponsabilidadesDTO> personaResponsabilidadesDTOs;

	private UsuarioSessionDTO usuarioSessionDTO;

	private String numeroUnidad;

	private AmbienteVirtualAprendizajeDTO ava;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {

		logger.info("Se esta creando el Bean de AsignacionRespProduccionGABean");
		usuarioSessionDTO = this.getUsuarioEnSession();

		objetoAprendizajeSeleccionado = new UnidadOaAvaDTO();

		/**
		 * Busca los tipo de responsabilidad*
		 */
		responsabilidades = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_TIPO_RESPONSABILIDAD);

		idTipoResponsabilidades = this.obtenerResponsabilidades(responsabilidades);

		/**
		 * Obtiene a los responsables de produccion*
		 */
		personaResponsabilidadesDTOs = personaResponsabilidadesService
				.obtienePersonasPorResponsabilidadList(idTipoResponsabilidades);

		/**
		 * Clasifica a los responsables de produccion*
		 */
		this.obtenerResponsablesContruccion(personaResponsabilidadesDTOs);

		logger.info("Termino la carga del bean AsignacionRespProduccion");

	}

	private void obtenerResponsablesContruccion(List<PersonaResponsabilidadesDTO> respProdDesasarrollo) {

		respProdExpContenidoList = new ArrayList<PersonaResponsabilidadesDTO>();
		respProdDisInstList = new ArrayList<PersonaResponsabilidadesDTO>();
		respProdDisElearningList = new ArrayList<PersonaResponsabilidadesDTO>();
		respProdDesaElearningList = new ArrayList<PersonaResponsabilidadesDTO>();

		for (PersonaResponsabilidadesDTO personaResponsabilidadesDTO : respProdDesasarrollo) {
			String tipoResponsabilidad = personaResponsabilidadesDTO.getCatTipoResponsabilidadEc().getNombre();

			switch (tipoResponsabilidad) {
			case "Experto en contenido":
				respProdExpContenidoList.add(personaResponsabilidadesDTO);
				break;
			case "Diseñador instruccional":
				respProdDisInstList.add(personaResponsabilidadesDTO);
				break;
			case "Diseñador elearning":
				respProdDisElearningList.add(personaResponsabilidadesDTO);
				break;
			case "Desarrollador elearning":
				respProdDesaElearningList.add(personaResponsabilidadesDTO);
				break;

			default:
				logger.info("ReponsableProduccionOaService:" + "obtenerResponsablesContruccions:Tipo de "
						+ "responsabilidad no valido El tipo de responsabilidad es " + tipoResponsabilidad);
				break;
			}

		}

	}

	/**
	 * Metodo que obtiene el responsable seleccionado en la vista por el ID si
	 * lo encuntra valida que si el objeto PersonaResponsabilidade del objeto
	 * reponsableProduccionOaDTO es nulo o si es id persona del objeto
	 * personaResponsabilidades y el id persona del objeto
	 * reponsableProduccionOaDTO son diferentes realiza la nueva asignacion
	 *
	 * @param reponsableProduccionOaDTO
	 * @param personaResponsabilidadesDTOs
	 * @return
	 */
	private PersonaResponsabilidadesDTO obtenerResponsableSeleccionado(
			ReponsableProduccionOaDTO reponsableProduccionOaDTO,
			List<PersonaResponsabilidadesDTO> personaResponsabilidadesDTOs) {

		PersonaResponsabilidadesDTO personaResponsabilidadesDTO = null;

		for (PersonaResponsabilidadesDTO personaResponsabilidades : personaResponsabilidadesDTOs) {
			if (personaResponsabilidades.getId().equals(reponsableProduccionOaDTO.getIdResponsableProduccion())) {
				if (ObjectUtils.isNull(reponsableProduccionOaDTO.getPersonaResponsabilidade())
						|| !personaResponsabilidades.getTblPersona().getIdPersona().equals(reponsableProduccionOaDTO
								.getPersonaResponsabilidade().getTblPersona().getIdPersona())) {
					personaResponsabilidadesDTO = personaResponsabilidades;
					break;
				}
			}
		}

		return personaResponsabilidadesDTO;
	}

	private void persisteExpertoEnContenido() {
		PersonaResponsabilidadesDTO personaResponsabilidadeTemp = null;
		Boolean enivarMensajeDeDesasignacion = Boolean.FALSE;
		ReponsableProduccionOaDTO expertoContenidoClonado;

		/**
		 * Actualiza Experto en contenido*
		 */
		personaResponsabilidadeTemp = this.obtenerResponsableSeleccionado(
				objetoAprendizajeSeleccionado.getExpertoEnContenido(), personaResponsabilidadesDTOs);

		/**
		 * si el responsable de produccion es nulo ya no realiza ninguna
		 * operacion para actualizar o guardar al experto en contenido
		 */
		if (ObjectUtils.isNotNull(personaResponsabilidadeTemp)) {

			expertoContenidoClonado = SerializationUtils.clone(objetoAprendizajeSeleccionado.getExpertoEnContenido());

			objetoAprendizajeSeleccionado.getExpertoEnContenido()
					.setPersonaResponsabilidade(personaResponsabilidadeTemp);

			ResultadoDTO<ReponsableProduccionOaDTO> resultadoExpEncont;

			if (ObjectUtils
					.isNull(objetoAprendizajeSeleccionado.getExpertoEnContenido().getIdRelResponsableProduccionOa())) {

				this.agreagarValoresRequeridosResponsableProd(objetoAprendizajeSeleccionado.getExpertoEnContenido(),
						objetoAprendizajeSeleccionado);
				resultadoExpEncont = reponsableProduccionOaService
						.guardar(objetoAprendizajeSeleccionado.getExpertoEnContenido());
				objetoAprendizajeSeleccionado.getExpertoEnContenido()
						.setIdRelResponsableProduccionOa(resultadoExpEncont.getDto().getIdRelResponsableProduccionOa());

			} else {
				this.agreagarValoresRequeridosResponsableProd(objetoAprendizajeSeleccionado.getExpertoEnContenido(),
						objetoAprendizajeSeleccionado);
				resultadoExpEncont = reponsableProduccionOaService
						.actualizar(objetoAprendizajeSeleccionado.getExpertoEnContenido());
			}

			this.validaMensajeResultadoTransaccion(resultadoExpEncont.getMensajes(), resultadoExpEncont.getResultado());

			logger.info("El ExpertoEnContenido resultado de la transaccion : " + resultadoExpEncont.getResultado());
			if (resultadoExpEncont.esCorrecto()) {

				logger.info("Resultado de la transaccion Exitoso Se procede al envio de mensajes");

				enivarMensajeDeDesasignacion = this.validaEnvioMensajeDesasignacion(
						objetoAprendizajeSeleccionado.getExpertoEnContenido(), expertoContenidoClonado);

				this.enviarMensajeAsignacionDesasignacionResp(enivarMensajeDeDesasignacion,
						objetoAprendizajeSeleccionado.getExpertoEnContenido(), expertoContenidoClonado);
				
				//TODO Enviar notificacion y correo a experto en contenido
				if(ObjectUtils.isNotNull(objetoAprendizajeSeleccionado.getAmbienteVirtualAprendizaje())){
					
					if(ObjectUtils.isNotNull(objetoAprendizajeSeleccionado.getAmbienteVirtualAprendizaje().getEventoCapacitacion())){
						
						String claveNotificacion = ConstantesGestorWeb.CLAVE_NOTIFICACION_AL_AGREGAR_COLABORADORES;
						String claveCorreo = ConstantesGestorWeb.CLAVE_CORREO_AL_AGREGAR_COLABORADORES;
						EventoCapacitacionDTO evento = objetoAprendizajeSeleccionado.getAmbienteVirtualAprendizaje().getEventoCapacitacion();
						Long idColaborador = objetoAprendizajeSeleccionado.getExpertoEnContenido().getPersonaResponsabilidade().getTblPersona().getIdPersona();
						String nombreTema = objetoAprendizajeSeleccionado.getDetEstUnidadDidactica().getTituloUa();
						correoNotificacionBean.notificarColaboradorProduccion(claveNotificacion, claveCorreo, idColaborador,nombreTema, evento );
					}
					
				}
				

			}
		}
	}

	private void persisteDisenadorInstruccional() {
		PersonaResponsabilidadesDTO personaResponsabilidadeTemp = null;
		Boolean enivarMensajeDeDesasignacion = Boolean.FALSE;
		ReponsableProduccionOaDTO disenadorInstruccionalClonado;

		/**
		 * Actualiza Diseñador instruccional*
		 */
		personaResponsabilidadeTemp = this.obtenerResponsableSeleccionado(
				objetoAprendizajeSeleccionado.getDisenadorInstruccional(), personaResponsabilidadesDTOs);

		/**
		 * si el responsable de produccion es nulo ya no realiza ninguna
		 * operacion para actualizar o guardar al dienador elearning
		 */
		if (ObjectUtils.isNotNull(personaResponsabilidadeTemp)) {

			disenadorInstruccionalClonado = SerializationUtils
					.clone(objetoAprendizajeSeleccionado.getDisenadorInstruccional());

			objetoAprendizajeSeleccionado.getDisenadorInstruccional()
					.setPersonaResponsabilidade(personaResponsabilidadeTemp);

			ResultadoDTO<ReponsableProduccionOaDTO> resultadoDisenadorInst;

			if (ObjectUtils.isNull(
					objetoAprendizajeSeleccionado.getDisenadorInstruccional().getIdRelResponsableProduccionOa())) {

				this.agreagarValoresRequeridosResponsableProd(objetoAprendizajeSeleccionado.getDisenadorInstruccional(),
						objetoAprendizajeSeleccionado);
				
				resultadoDisenadorInst = reponsableProduccionOaService
						.guardar(objetoAprendizajeSeleccionado.getDisenadorInstruccional());
				objetoAprendizajeSeleccionado.getDisenadorInstruccional().setIdRelResponsableProduccionOa(
						resultadoDisenadorInst.getDto().getIdRelResponsableProduccionOa());
			} else {
				this.agreagarValoresRequeridosResponsableProd(objetoAprendizajeSeleccionado.getDisenadorInstruccional(),
						objetoAprendizajeSeleccionado);
			
				resultadoDisenadorInst = reponsableProduccionOaService
						.actualizar(objetoAprendizajeSeleccionado.getDisenadorInstruccional());
			}

			this.validaMensajeResultadoTransaccion(resultadoDisenadorInst.getMensajes(),
					resultadoDisenadorInst.getResultado());

			logger.info("El Diseñador instruccional resultado de la transaccion : "
					+ resultadoDisenadorInst.getResultado());

			if (resultadoDisenadorInst.esCorrecto()) {

				logger.info("Resultado de la transaccion Exitoso Se procede al envio de mensajes");

				enivarMensajeDeDesasignacion = this.validaEnvioMensajeDesasignacion(
						objetoAprendizajeSeleccionado.getDisenadorInstruccional(), disenadorInstruccionalClonado);

				this.enviarMensajeAsignacionDesasignacionResp(enivarMensajeDeDesasignacion,
						objetoAprendizajeSeleccionado.getDisenadorInstruccional(), disenadorInstruccionalClonado);
				
				
				//TODO Enviar notificacion y correo a disenador instruccional
				if(ObjectUtils.isNotNull(objetoAprendizajeSeleccionado.getAmbienteVirtualAprendizaje())){
					
					if(ObjectUtils.isNotNull(objetoAprendizajeSeleccionado.getAmbienteVirtualAprendizaje().getEventoCapacitacion())){
						
						String claveNotificacion = ConstantesGestorWeb.CLAVE_NOTIFICACION_AL_AGREGAR_COLABORADORES;
						String claveCorreo = ConstantesGestorWeb.CLAVE_CORREO_AL_AGREGAR_COLABORADORES;
						EventoCapacitacionDTO evento = objetoAprendizajeSeleccionado.getAmbienteVirtualAprendizaje().getEventoCapacitacion();
						Long idColaborador = objetoAprendizajeSeleccionado.getDisenadorInstruccional().getPersonaResponsabilidade().getTblPersona().getIdPersona();
						String nombreTema = objetoAprendizajeSeleccionado.getDetEstUnidadDidactica().getTituloUa();
						correoNotificacionBean.notificarColaboradorProduccion(claveNotificacion, claveCorreo, idColaborador,nombreTema, evento );
					}
					
				}
			}
		}

	}

	private void persisteDisenadorElearning() {
		PersonaResponsabilidadesDTO personaResponsabilidadeTemp = null;
		Boolean enivarMensajeDeDesasignacion = Boolean.FALSE;
		ReponsableProduccionOaDTO disenadorElearningClonado;

		/**
		 * Actualiza DisenadorElearning*
		 */
		personaResponsabilidadeTemp = this.obtenerResponsableSeleccionado(
				objetoAprendizajeSeleccionado.getDisenadorElearning(), personaResponsabilidadesDTOs);

		if (ObjectUtils.isNotNull(personaResponsabilidadeTemp)) {

			disenadorElearningClonado = SerializationUtils.clone(objetoAprendizajeSeleccionado.getDisenadorElearning());

			objetoAprendizajeSeleccionado.getDisenadorElearning()
					.setPersonaResponsabilidade(personaResponsabilidadeTemp);

			ResultadoDTO<ReponsableProduccionOaDTO> resultadoDisenadorElean;

			if (ObjectUtils
					.isNull(objetoAprendizajeSeleccionado.getDisenadorElearning().getIdRelResponsableProduccionOa())) {

				this.agreagarValoresRequeridosResponsableProd(objetoAprendizajeSeleccionado.getDisenadorElearning(),
						objetoAprendizajeSeleccionado);

				resultadoDisenadorElean = reponsableProduccionOaService
						.guardar(objetoAprendizajeSeleccionado.getDisenadorElearning());
				objetoAprendizajeSeleccionado.getDisenadorElearning().setIdRelResponsableProduccionOa(
						resultadoDisenadorElean.getDto().getIdRelResponsableProduccionOa());

			} else {
				this.agreagarValoresRequeridosResponsableProd(objetoAprendizajeSeleccionado.getDisenadorElearning(),
						objetoAprendizajeSeleccionado);

				resultadoDisenadorElean = reponsableProduccionOaService
						.actualizar(objetoAprendizajeSeleccionado.getDisenadorElearning());

			}

			this.validaMensajeResultadoTransaccion(resultadoDisenadorElean.getMensajes(),
					resultadoDisenadorElean.getResultado());
			logger.info(
					"El Disenador Elearning resultado de la trasaccion : " + resultadoDisenadorElean.getResultado());

			if (resultadoDisenadorElean.esCorrecto()) {
				logger.info("Resultado de la transaccion Exitoso Se procede al envio de mensajes");

				enivarMensajeDeDesasignacion = this.validaEnvioMensajeDesasignacion(
						objetoAprendizajeSeleccionado.getDisenadorElearning(), disenadorElearningClonado);

				this.enviarMensajeAsignacionDesasignacionResp(enivarMensajeDeDesasignacion,
						objetoAprendizajeSeleccionado.getDisenadorElearning(), disenadorElearningClonado);
				
				//TODO Enviar notificacion y correo a disenador elearning
				if(ObjectUtils.isNotNull(objetoAprendizajeSeleccionado.getAmbienteVirtualAprendizaje())){
					
					if(ObjectUtils.isNotNull(objetoAprendizajeSeleccionado.getAmbienteVirtualAprendizaje().getEventoCapacitacion())){
						
						String claveNotificacion = ConstantesGestorWeb.CLAVE_NOTIFICACION_AL_AGREGAR_COLABORADORES;
						String claveCorreo = ConstantesGestorWeb.CLAVE_CORREO_AL_AGREGAR_COLABORADORES;
						EventoCapacitacionDTO evento = objetoAprendizajeSeleccionado.getAmbienteVirtualAprendizaje().getEventoCapacitacion();
						Long idColaborador = objetoAprendizajeSeleccionado.getDisenadorElearning().getPersonaResponsabilidade().getTblPersona().getIdPersona();
						String nombreTema = objetoAprendizajeSeleccionado.getDetEstUnidadDidactica().getTituloUa();
						correoNotificacionBean.notificarColaboradorProduccion(claveNotificacion, claveCorreo, idColaborador,nombreTema, evento );
					}
					
				}
			}

		}
	}

	private void persisteDesarrolladorElearning() {
		PersonaResponsabilidadesDTO personaResponsabilidadeTemp = null;
		Boolean enivarMensajeDeDesasignacion = Boolean.FALSE;
		ReponsableProduccionOaDTO desarrolladorElearningClonado;

		/**
		 * Actualiza DisarrolladorElearning*
		 */
		personaResponsabilidadeTemp = this.obtenerResponsableSeleccionado(
				objetoAprendizajeSeleccionado.getDesarrolladorElearning(), personaResponsabilidadesDTOs);

		if (ObjectUtils.isNotNull(personaResponsabilidadeTemp)) {

			desarrolladorElearningClonado = SerializationUtils
					.clone(objetoAprendizajeSeleccionado.getDesarrolladorElearning());

			objetoAprendizajeSeleccionado.getDesarrolladorElearning()
					.setPersonaResponsabilidade(personaResponsabilidadeTemp);

			ResultadoDTO<ReponsableProduccionOaDTO> resultadoDesarrolloElearning;

			if (ObjectUtils.isNull(
					objetoAprendizajeSeleccionado.getDesarrolladorElearning().getIdRelResponsableProduccionOa())) {

				this.agreagarValoresRequeridosResponsableProd(objetoAprendizajeSeleccionado.getDesarrolladorElearning(),
						objetoAprendizajeSeleccionado);

				resultadoDesarrolloElearning = reponsableProduccionOaService
						.guardar(objetoAprendizajeSeleccionado.getDesarrolladorElearning());
				objetoAprendizajeSeleccionado.getDesarrolladorElearning().setIdRelResponsableProduccionOa(
						resultadoDesarrolloElearning.getDto().getIdRelResponsableProduccionOa());

			} else {

				this.agreagarValoresRequeridosResponsableProd(objetoAprendizajeSeleccionado.getDesarrolladorElearning(),
						objetoAprendizajeSeleccionado);

				resultadoDesarrolloElearning = reponsableProduccionOaService
						.actualizar(objetoAprendizajeSeleccionado.getDesarrolladorElearning());
			}

			this.validaMensajeResultadoTransaccion(resultadoDesarrolloElearning.getMensajes(),
					resultadoDesarrolloElearning.getResultado());
			logger.info("El DisenadorElearning resultado de la transaccion : "
					+ resultadoDesarrolloElearning.getResultado());

			if (resultadoDesarrolloElearning.esCorrecto()) {
				logger.info("Resultado de la transaccion Exitoso Se procede al envio de mensajes");

				enivarMensajeDeDesasignacion = this.validaEnvioMensajeDesasignacion(
						objetoAprendizajeSeleccionado.getDesarrolladorElearning(), desarrolladorElearningClonado);

				this.enviarMensajeAsignacionDesasignacionResp(enivarMensajeDeDesasignacion,
						objetoAprendizajeSeleccionado.getDesarrolladorElearning(), desarrolladorElearningClonado);
				
				//TODO Enviar notificacion y correo a desarrollador elearning
				if(ObjectUtils.isNotNull(objetoAprendizajeSeleccionado.getAmbienteVirtualAprendizaje())){
					if(ObjectUtils.isNotNull(objetoAprendizajeSeleccionado.getAmbienteVirtualAprendizaje().getEventoCapacitacion())){
						String claveNotificacion = ConstantesGestorWeb.CLAVE_NOTIFICACION_AL_AGREGAR_COLABORADORES;
						String claveCorreo = ConstantesGestorWeb.CLAVE_CORREO_AL_AGREGAR_COLABORADORES;
						EventoCapacitacionDTO evento = objetoAprendizajeSeleccionado.getAmbienteVirtualAprendizaje().getEventoCapacitacion();
						Long idColaborador = objetoAprendizajeSeleccionado.getDesarrolladorElearning().getPersonaResponsabilidade().getTblPersona().getIdPersona();
						String nombreTema = objetoAprendizajeSeleccionado.getDetEstUnidadDidactica().getTituloUa();
						correoNotificacionBean.notificarColaboradorProduccion(claveNotificacion, claveCorreo, idColaborador,nombreTema, evento );
					}
				}
				
			}

		}
	}

	public void guardarResponsablesEnUnidadOaAva() {

		logger.info("AsignacionRespProduccionGABean:guardarResponsablesEnUnidadOaAva:"
				+ "Va a guardar/actualizar los responsables de produccion" + "en la unidad : id: "
				+ objetoAprendizajeSeleccionado.getId() + " nombre"
				+ objetoAprendizajeSeleccionado.getDetEstUnidadDidactica().getTituloUa());

		this.persisteExpertoEnContenido();
		this.persisteDisenadorInstruccional();
		this.persisteDisenadorElearning();
		this.persisteDesarrolladorElearning();
		

		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "ASI_COL_PRO_OA",
				String.valueOf(objetoAprendizajeSeleccionado.getId()), requestActual(), TipoServicioEnum.LOCAL);

		logger.info("Se actualizaron correctamente los responsables de produccion");
	}

	private void enviarMensajeAsignacionDesasignacionResp(Boolean enviarMensajeDesasignacion,
			ReponsableProduccionOaDTO colaboradorNuevo, ReponsableProduccionOaDTO colaboradorActual) {

		String nombrePersona = null;
		String responsabilidad = null;
		Long idPersona = null;
		List<VariableMensajeOperacionDTO> variables = null;

		if (ObjectUtils.isNotNull(colaboradorNuevo.getPersonaResponsabilidade())) {
			responsabilidad = colaboradorNuevo.getPersonaResponsabilidade().getCatTipoResponsabilidadEc().getNombre();
			nombrePersona = colaboradorNuevo.getPersonaResponsabilidade().getTblPersona().getNombreCompleto();
			idPersona = colaboradorNuevo.getPersonaResponsabilidade().getTblPersona().getIdPersona();

			variables = this.armaMensajeGenericoDeTipoResponsabilidad(nombrePersona, responsabilidad,
					ava.getEventoCapacitacion().getNombreEc(),
					objetoAprendizajeSeleccionado.getDetEstUnidadDidactica().getNombreUnidad());
			/**
			 * Envia mensaje de asignacion
			 */
			this.enviarMensajeAsignacionResp(idPersona, variables);

			responsabilidad = null;
			nombrePersona = null;
			idPersona = null;
			variables = null;
		}

		/**
		 * Envia mensaje de desasignacion
		 */
		if (enviarMensajeDesasignacion) {

			if (ObjectUtils.isNotNull(colaboradorActual.getPersonaResponsabilidade())) {
				responsabilidad = colaboradorActual.getPersonaResponsabilidade().getCatTipoResponsabilidadEc()
						.getNombre();
				nombrePersona = colaboradorActual.getPersonaResponsabilidade().getTblPersona().getNombre();
				idPersona = colaboradorActual.getPersonaResponsabilidade().getTblPersona().getIdPersona();

				variables = this.armaMensajeGenericoDeTipoResponsabilidad(nombrePersona, responsabilidad,
						ava.getEventoCapacitacion().getNombreEc(),
						objetoAprendizajeSeleccionado.getDetEstUnidadDidactica().getNombreUnidad());
				this.enviarMensajeDesasignacionResp(idPersona, variables);
			}
		}

	}

	private List<VariableMensajeOperacionDTO> armaMensajeGenericoDeTipoResponsabilidad(String nombrePersona,
			String responsabilidad, String nombreEventoCap, String nombreUnidadOA) {
		List<VariableMensajeOperacionDTO> variables = new ArrayList<>();

		variables.add(new VariableMensajeOperacionDTO("${nombre_persona}", nombrePersona));
		variables.add(new VariableMensajeOperacionDTO("${responsablidad}", responsabilidad));
		variables.add(new VariableMensajeOperacionDTO("${nombre_evento_cap}", nombreEventoCap));
		variables.add(new VariableMensajeOperacionDTO("${nombre_unidad_oa}", nombreUnidadOA));

		return variables;
	}

	/**
	 * Metodo que envia un mensaje de asignacion a un colaborador de produccion
	 *
	 * @param idPersona
	 * @param variables
	 */
	private void enviarMensajeAsignacionResp(Long idPersona, List<VariableMensajeOperacionDTO> variables) {
		this.enviarMensaje(ConstantesBitacora.ASIGNA_COLABORADOR_PROD_MOD_SEG_AVA, idPersona, variables);
	}

	/**
	 * Metodo que envia un mensaje de desasignacion a un colaborador de
	 * produccin
	 *
	 * @param idPersona
	 * @param variables
	 */
	private void enviarMensajeDesasignacionResp(Long idPersona, List<VariableMensajeOperacionDTO> variables) {
		this.enviarMensaje(ConstantesBitacora.RELEVA_COLABORADOR_PROD_MOD_SEG_AVA, idPersona, variables);
	}

	/**
	 * Metodo que envia un mensaje a la persona seleccionada
	 *
	 * @param operacion
	 * @param idPersona
	 * @param variables
	 */
	private void enviarMensaje(String operacion, Long idPersona, List<VariableMensajeOperacionDTO> variables) {
		ResultadoDTO<NotificacionSistemaDTO> resultado = notificacionSistemaService.enviarNotificacion(operacion,
				idPersona, variables);
		/*logger.info("El resultado del envio del mensaje a la persona " + "con el id " + idPersona + " fue "
				+ resultado.getResultado());*/
	}

	private Boolean validaEnvioMensajeDesasignacion(ReponsableProduccionOaDTO colaboradorNuevo,
			ReponsableProduccionOaDTO colaboradorActual) {
		Boolean enviaraMensajeDeDesasignacion = Boolean.FALSE;

		Long idPersonaColaboradorNuevo = null;
		Long idPersonaColaboradorActual = null;

		if (ObjectUtils.isNotNull(colaboradorNuevo.getPersonaResponsabilidade())
				&& ObjectUtils.isNotNull(colaboradorActual.getPersonaResponsabilidade())) {

			idPersonaColaboradorNuevo = colaboradorNuevo.getPersonaResponsabilidade().getTblPersona().getIdPersona();

			idPersonaColaboradorActual = colaboradorActual.getPersonaResponsabilidade().getTblPersona().getIdPersona();

			if (!idPersonaColaboradorNuevo.equals(idPersonaColaboradorActual)) {
				enviaraMensajeDeDesasignacion = Boolean.TRUE;
			}

		}
		return enviaraMensajeDeDesasignacion;
	}

	/**
	 * Metodo que agrega los campos requeridos para realizar la insercion o
	 * actualizacion
	 *
	 * @param reponsableProduccionOaDTO
	 * @param unidadOaAva
	 */
	private void agreagarValoresRequeridosResponsableProd(ReponsableProduccionOaDTO reponsableProduccionOaDTO,
			UnidadOaAvaDTO unidadOaAva) {

		if (ObjectUtils.isNull(reponsableProduccionOaDTO.getIdRelResponsableProduccionOa())) {
			reponsableProduccionOaDTO.setFechaRegistro(new Date());
			reponsableProduccionOaDTO.setUnidadOaAva(unidadOaAva);
		} else {
			reponsableProduccionOaDTO.setFechaActualizacion(new Date());
		}

		reponsableProduccionOaDTO.setUsuarioModifico(BigInteger.valueOf(usuarioSessionDTO.getIdPersona()));

	}

	private List<Integer> obtenerResponsabilidades(List<CatalogoComunDTO> responsabilidades) {

		List<Integer> idTiposResponsabilidad = new ArrayList<Integer>();

		for (CatalogoComunDTO catalogoComunDTO : responsabilidades) {

			String tipoResponsabilidad = catalogoComunDTO.getNombre();

			switch (tipoResponsabilidad) {
			case "Experto en contenido":
				idTiposResponsabilidad.add(catalogoComunDTO.getId());
				break;
			case "Diseñador instruccional":
				idTiposResponsabilidad.add(catalogoComunDTO.getId());
				break;
			case "Diseñador elearning":
				idTiposResponsabilidad.add(catalogoComunDTO.getId());
				break;
			case "Desarrollador elearning":
				idTiposResponsabilidad.add(catalogoComunDTO.getId());
				break;

			default:
				logger.info("ReponsableProduccionOaService:" + "obtenerResponsabilidades:Tipo de "
						+ "responsabilidad no valido El tipo de responsabilidad es " + tipoResponsabilidad);
				break;
			}

		}

		return idTiposResponsabilidad;
	}

	public void asignarUnidad(UnidadOaAvaDTO objetoAprendizajeSeleccionado, String numeroUnidad) {
		this.numeroUnidad = numeroUnidad;
		this.objetoAprendizajeSeleccionado = objetoAprendizajeSeleccionado;
		// this.nombreDelEventoDeCapacitacion =
		// ava.getEventoCapacitacion().getNombreEc();
		logger.info("El id de la unidad oa ava  seleccionada es " + objetoAprendizajeSeleccionado.getId());
		logger.info("El titulo de la unidad oa ava  seleccionada es "
				+ objetoAprendizajeSeleccionado.getDetEstUnidadDidactica().getTituloUa());

	}

	private void validaMensajeResultadoTransaccion(List<String> mensajes,
			ResultadoTransaccionEnum resultadoTransaccionEnum) {

		if (resultadoTransaccionEnum == ResultadoTransaccionEnum.EXITOSO) {
			if (ObjectUtils.isNotNull(mensajes)) {
				agregarMsgInfo(mensajes.get(0), null, sistema);
			}
		} else {
			if (ObjectUtils.isNotNull(mensajes)) {
				agregarMsgError(mensajes, null, sistema);
			}
		}
	}

	protected HttpSession getSession() {
		return getRequest().getSession();
	}

	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	/**
	 * Setter y getter*
	 */
	public ReponsableProduccionOaService getReponsableProduccionOaService() {
		return reponsableProduccionOaService;
	}

	public void setReponsableProduccionOaService(ReponsableProduccionOaService reponsableProduccionOaService) {
		this.reponsableProduccionOaService = reponsableProduccionOaService;
	}

	public UnidadOaAvaDTO getObjetoAprendizajeSeleccionado() {
		return objetoAprendizajeSeleccionado;
	}

	public void setObjetoAprendizajeSeleccionado(UnidadOaAvaDTO objetoAprendizajeSeleccionado) {
		this.objetoAprendizajeSeleccionado = objetoAprendizajeSeleccionado;
	}

	public List<CatalogoComunDTO> getResponsabilidades() {
		return responsabilidades;
	}

	public void setResponsabilidades(List<CatalogoComunDTO> responsabilidades) {
		this.responsabilidades = responsabilidades;
	}

	public List<PersonaResponsabilidadesDTO> getRespProdExpContenidoList() {
		return respProdExpContenidoList;
	}

	public void setRespProdExpContenidoList(List<PersonaResponsabilidadesDTO> respProdExpContenidoList) {
		this.respProdExpContenidoList = respProdExpContenidoList;
	}

	public List<PersonaResponsabilidadesDTO> getRespProdDisInstList() {
		return respProdDisInstList;
	}

	public void setRespProdDisInstList(List<PersonaResponsabilidadesDTO> respProdDisInstList) {
		this.respProdDisInstList = respProdDisInstList;
	}

	public List<PersonaResponsabilidadesDTO> getRespProdDisElearningList() {
		return respProdDisElearningList;
	}

	public void setRespProdDisElearningList(List<PersonaResponsabilidadesDTO> respProdDisElearningList) {
		this.respProdDisElearningList = respProdDisElearningList;
	}

	public List<PersonaResponsabilidadesDTO> getRespProdDesaElearningList() {
		return respProdDesaElearningList;
	}

	public void setRespProdDesaElearningList(List<PersonaResponsabilidadesDTO> respProdDesaElearningList) {
		this.respProdDesaElearningList = respProdDesaElearningList;
	}

	public PersonaResponsabilidadesService getPersonaResponsabilidadesService() {
		return personaResponsabilidadesService;
	}

	public void setPersonaResponsabilidadesService(PersonaResponsabilidadesService personaResponsabilidadesService) {
		this.personaResponsabilidadesService = personaResponsabilidadesService;
	}

	public String getNumeroUnidad() {
		return numeroUnidad;
	}

	public void setNumeroUnidad(String numeroUnidad) {
		this.numeroUnidad = numeroUnidad;
	}

	public NotificacionSistemaService getNotificacionSistemaService() {
		return notificacionSistemaService;
	}

	public void setNotificacionSistemaService(NotificacionSistemaService notificacionSistemaService) {
		this.notificacionSistemaService = notificacionSistemaService;
	}

	public SistemaBean getSistema() {
		return sistema;
	}

	public void setSistema(SistemaBean sistema) {
		this.sistema = sistema;
	}

	public AmbienteVirtualAprendizajeDTO getAva() {
		return ava;
	}

	public void setAva(AmbienteVirtualAprendizajeDTO ava) {
		this.ava = ava;
	}

	public BitacoraBean getBitacoraBean() {
		return bitacoraBean;
	}

	public void setBitacoraBean(BitacoraBean bitacoraBean) {
		this.bitacoraBean = bitacoraBean;
	}

	public CorreoNotificacionBean getCorreoNotificacionBean() {
		return correoNotificacionBean;
	}

	public void setCorreoNotificacionBean(CorreoNotificacionBean correoNotificacionBean) {
		this.correoNotificacionBean = correoNotificacionBean;
	}

}
