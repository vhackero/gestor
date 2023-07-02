package mx.gob.sedesol.gestorweb.beans.gestionaprendizaje;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.AmbienteVirtualAprendizajeDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.PersonaResponsabilidadesDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ReponsableProduccionEcDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoResponsabilidadEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.service.gestion.aprendizaje.AmbienteVirtualApService;
import mx.gob.sedesol.basegestor.service.gestionescolar.PersonaResponsabilidadesService;
import mx.gob.sedesol.basegestor.service.gestionescolar.RelReponsableProduccionEcService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.beans.administracion.BitacoraBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.dto.UsuarioSessionDTO;
import mx.gob.sedesol.gestorweb.sistema.SistemaBean;

@ManagedBean
@ViewScoped
public class AsignacionResponsableProduccionFoaBean extends BaseBean {

	private static final Logger logger = Logger.getLogger(AsignacionResponsableProduccionFoaBean.class);
	/**
	 *
	 */
	private static final long serialVersionUID = -781318043651244838L;

	/**
	 * Inyeccion de personaResponsabilidadesService
	 */
	@ManagedProperty(value = "#{personaResponsabilidadesService}")
	private PersonaResponsabilidadesService personaResponsabilidadesService;

	/**
	 * Inyeccion del ambientevirtuaApService
	 */
	@ManagedProperty(value = "#{ambienteVirtualApService}")
	private AmbienteVirtualApService ambienteVirtualApService;

	/**
	 * Inyeccion del servicio de sistema
	 */
	@ManagedProperty(value = "#{sistema}")
	private SistemaBean sistema;

	/**
	 * Inyeccion de RelReponsableProduccionEcService
	 */
	@ManagedProperty(value = "#{relReponsableProduccionEcService}")
	private RelReponsableProduccionEcService relReponsableProduccionEcService;

	@ManagedProperty("#{bitacoraBean}")
	private BitacoraBean bitacoraBean;

	private List<CatalogoComunDTO> responsabilidades;

	private List<Integer> idTipoResponsabilidades;

	private List<PersonaResponsabilidadesDTO> personaResponsablesList;

	private PersonaResponsabilidadesDTO respProdSeleccionado;

	private AmbienteVirtualAprendizajeDTO avaSeleccionado;

	private UsuarioSessionDTO usuarioSessionDTO;

	private Boolean btnBorrarRespEsActivo;

	private Boolean elementosAsignacionRespEsActivo;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {

		usuarioSessionDTO = this.getUsuarioEnSession();

		/**
		 * Busca los tipo de responsabilidad*
		 */
		responsabilidades = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_TIPO_RESPONSABILIDAD);

		idTipoResponsabilidades = this.obtenerTpoResponsableRespProduccion(responsabilidades);

		personaResponsablesList = personaResponsabilidadesService
				.obtienePersonasPorResponsabilidadList(idTipoResponsabilidades);

		respProdSeleccionado = new PersonaResponsabilidadesDTO();
		respProdSeleccionado.setTblPersona(new PersonaDTO());

		elementosAsignacionRespEsActivo = Boolean.TRUE;

	}

	private List<Integer> obtenerTpoResponsableRespProduccion(List<CatalogoComunDTO> responsabilidades) {

		List<Integer> idTiposResponsabilidad = new ArrayList<Integer>();

		for (CatalogoComunDTO catalogoComunDTO : responsabilidades) {

			if (TipoResponsabilidadEnum.RESPONSABLE_DE_PRODUCCION.getId().equals(catalogoComunDTO.getId())) {
				idTiposResponsabilidad.add(catalogoComunDTO.getId());
			}
		}

		return idTiposResponsabilidad;
	}

	public void asignaResponsableProdAva(AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizajeDTO) {

		this.avaSeleccionado = ambienteVirtualAprendizajeDTO;
		this.respProdSeleccionado = new PersonaResponsabilidadesDTO();

		if (ObjectUtils.isNotNull(avaSeleccionado.getEventoCapacitacion().getResponsableProduccion())) {

			this.respProdSeleccionado = avaSeleccionado.getEventoCapacitacion().getResponsableProduccion()
					.getPersonaResponsabilidad();

			btnBorrarRespEsActivo = Boolean.TRUE;
			elementosAsignacionRespEsActivo = Boolean.FALSE;

		}
		logger.info("Se asignaron correctamente los responsables de prod" + (elementosAsignacionRespEsActivo));
	}

	public void guardarResponsableProduccion() {
		ResultadoDTO<ReponsableProduccionEcDTO> resultado;
		respProdSeleccionado = obtenerResponsableProduccionDTO(respProdSeleccionado.getId(), personaResponsablesList);

		if (ObjectUtils.isNotNull(respProdSeleccionado.getId())) {
			resultado = this.actualizarResponsableProduccion(respProdSeleccionado);
			if (resultado.getResultado().equals(ResultadoTransaccionEnum.EXITOSO)) {
				bitacoraBean.guardarBitacora(idPersonaEnSesion(), "ASI_RES_PRO", "", requestActual(),
						TipoServicioEnum.LOCAL);
				btnBorrarRespEsActivo = Boolean.TRUE;
				elementosAsignacionRespEsActivo = Boolean.FALSE;
				avaSeleccionado.getEventoCapacitacion().setResponsableProduccion(resultado.getDto());
			}

		} else {

			agregarMsgError("Se debe seleccionar primero el responsable de produccion", null, sistema);
		}
	}

	public void borrarResponsableProduccionSeleccionado() {
		this.borrarResponsableProduccion(respProdSeleccionado, avaSeleccionado.getEventoCapacitacion());
		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "ELI_RES_PRO", String.valueOf(respProdSeleccionado.getId()),
				requestActual(), TipoServicioEnum.LOCAL);
	}

	public ResultadoDTO<ReponsableProduccionEcDTO> borrarResponsableProduccion(
			PersonaResponsabilidadesDTO personaResponsabilidadesDTO, EventoCapacitacionDTO eventoCapacitacionDTO) {

		ResultadoDTO<ReponsableProduccionEcDTO> resultado;

		ReponsableProduccionEcDTO responsableProdEc = new ReponsableProduccionEcDTO();

		responsableProdEc.setPersonaResponsabilidad(personaResponsabilidadesDTO);
		responsableProdEc.setEventoCapacitacion(eventoCapacitacionDTO);
		responsableProdEc.setUsuarioModifico(BigInteger.valueOf(usuarioSessionDTO.getIdPersona()));
		// GUSTAVO --BitacoraUtil.llenarBitacora(responsableProdEc,
		// getUsuarioEnSession(),
		// ConstantesBitacora.RESPONSABLE_PRODUCCION_EC_ELIMINAR, request);
		resultado = relReponsableProduccionEcService.eliminar(responsableProdEc);

		this.validaMensajeResultadoTransaccion(resultado.getMensajes(), resultado.getResultado());

		if (resultado.esCorrecto()) {
			respProdSeleccionado = new PersonaResponsabilidadesDTO();
			responsableProdEc = null;
			avaSeleccionado.getEventoCapacitacion().setResponsableProduccion(null);
			btnBorrarRespEsActivo = Boolean.FALSE;
			elementosAsignacionRespEsActivo = Boolean.TRUE;

		}
		return resultado;
	}

	private ResultadoDTO<ReponsableProduccionEcDTO> actualizarResponsableProduccion(
			PersonaResponsabilidadesDTO personaResponsabilidadesDTO) {

		ResultadoDTO<ReponsableProduccionEcDTO> resultado = null;
		ReponsableProduccionEcDTO responsableProdEc = new ReponsableProduccionEcDTO();

		responsableProdEc.setEsResponsablePrincipal(Boolean.TRUE);
		responsableProdEc.setPersonaResponsabilidad(personaResponsabilidadesDTO);
		responsableProdEc.setEventoCapacitacion(avaSeleccionado.getEventoCapacitacion());
		responsableProdEc.setFechaActualizacion(new Date());
		responsableProdEc.setUsuarioModifico(BigInteger.valueOf(usuarioSessionDTO.getIdPersona()));

		logger.info("Guarda responsable evento capacitacion");
		responsableProdEc.setFechaRegistro(new Date());
		// GUSTAVO --BitacoraUtil.llenarBitacora(responsableProdEc,
		// getUsuarioEnSession(),
		// ConstantesBitacora.RESPONSABLE_PRODUCCION_EC_AGREGAR, request);
		resultado = relReponsableProduccionEcService.guardar(responsableProdEc);
		this.validaMensajeResultadoTransaccion(resultado.getMensajes(), resultado.getResultado());

		return resultado;
	}

	private PersonaResponsabilidadesDTO obtenerResponsableProduccionDTO(Integer idResponsableProduccion,
			List<PersonaResponsabilidadesDTO> personaResponsablesList) {

		PersonaResponsabilidadesDTO personaResponsabilidadesDTOResultante = new PersonaResponsabilidadesDTO();

		for (PersonaResponsabilidadesDTO personaResponsabilidadesDTO : personaResponsablesList) {
			if (personaResponsabilidadesDTO.getId().equals(idResponsableProduccion)) {
				personaResponsabilidadesDTOResultante.setId(personaResponsabilidadesDTO.getId());
				personaResponsabilidadesDTOResultante.setTblPersona(personaResponsabilidadesDTO.getTblPersona());
				break;
			}
		}

		return personaResponsabilidadesDTOResultante;
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

	/**
	 * Getters y setters
	 *
	 * @return
	 */
	public PersonaResponsabilidadesService getPersonaResponsabilidadesService() {
		return personaResponsabilidadesService;
	}

	public void setPersonaResponsabilidadesService(PersonaResponsabilidadesService personaResponsabilidadesService) {
		this.personaResponsabilidadesService = personaResponsabilidadesService;
	}

	public List<CatalogoComunDTO> getResponsabilidades() {
		return responsabilidades;
	}

	public void setResponsabilidades(List<CatalogoComunDTO> responsabilidades) {
		this.responsabilidades = responsabilidades;
	}

	public List<Integer> getIdTipoResponsabilidades() {
		return idTipoResponsabilidades;
	}

	public void setIdTipoResponsabilidades(List<Integer> idTipoResponsabilidades) {
		this.idTipoResponsabilidades = idTipoResponsabilidades;
	}

	public List<PersonaResponsabilidadesDTO> getPersonaResponsablesList() {
		return personaResponsablesList;
	}

	public void setPersonaResponsablesList(List<PersonaResponsabilidadesDTO> personaResponsablesList) {
		this.personaResponsablesList = personaResponsablesList;
	}

	public PersonaResponsabilidadesDTO getRespProdSeleccionado() {
		return respProdSeleccionado;
	}

	public void setRespProdSeleccionado(PersonaResponsabilidadesDTO respProdSeleccionado) {
		this.respProdSeleccionado = respProdSeleccionado;
	}

	public AmbienteVirtualApService getAmbienteVirtualApService() {
		return ambienteVirtualApService;
	}

	public void setAmbienteVirtualApService(AmbienteVirtualApService ambienteVirtualApService) {
		this.ambienteVirtualApService = ambienteVirtualApService;
	}

	public AmbienteVirtualAprendizajeDTO getAvaSeleccionado() {
		return avaSeleccionado;
	}

	public void setAvaSeleccionado(AmbienteVirtualAprendizajeDTO avaSeleccionado) {
		this.avaSeleccionado = avaSeleccionado;
	}

	public Boolean getBtnBorrarRespEsActivo() {
		return btnBorrarRespEsActivo;
	}

	public void setBtnBorrarRespEsActivo(Boolean btnBorrarRespEsActivo) {
		this.btnBorrarRespEsActivo = btnBorrarRespEsActivo;
	}

	public SistemaBean getSistema() {
		return sistema;
	}

	public void setSistema(SistemaBean sistema) {
		this.sistema = sistema;
	}

	public RelReponsableProduccionEcService getRelReponsableProduccionEcService() {
		return relReponsableProduccionEcService;
	}

	public void setRelReponsableProduccionEcService(RelReponsableProduccionEcService relReponsableProduccionEcService) {
		this.relReponsableProduccionEcService = relReponsableProduccionEcService;
	}

	public Boolean getElementosAsignacionRespEsActivo() {
		return elementosAsignacionRespEsActivo;
	}

	public void setElementosAsignacionRespEsActivo(Boolean elementosAsignacionRespEsActivo) {
		this.elementosAsignacionRespEsActivo = elementosAsignacionRespEsActivo;
	}

	public BitacoraBean getBitacoraBean() {
		return bitacoraBean;
	}

	public void setBitacoraBean(BitacoraBean bitacoraBean) {
		this.bitacoraBean = bitacoraBean;
	}

}
