package mx.gob.sedesol.gestorweb.beans.administracion;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.badges.BadgeDTO;
import mx.gob.sedesol.basegestor.commons.dto.badges.ClasificacionBadgeDTO;
import mx.gob.sedesol.basegestor.commons.utils.ClasificacionBadgeEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.basegestor.service.badges.BadgeService;
import mx.gob.sedesol.basegestor.service.badges.ClasificacionBadgeService;
import mx.gob.sedesol.basegestor.service.impl.admin.PersonaServiceFacade;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.utils.BitacoraUtil;
import mx.gob.sedesol.gestorweb.commons.utils.GestorArchivos;
import mx.gob.sedesol.gestorweb.sistema.SistemaBean;

@SessionScoped
@ManagedBean(name = "badgetBean")
public class BadgetBean extends BaseBean {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(BadgetBean.class);

	@ManagedProperty(value = "#{badgeService}")
	private transient BadgeService badgeService;

	@ManagedProperty(value = "#{clasificacionBadgeService}")
	private transient ClasificacionBadgeService clasificacionBadgeService;

	@ManagedProperty("#{personaServiceFacade}")
	private transient PersonaServiceFacade personaServiceFacade;

	private List<ClasificacionBadgeDTO> clasificaciones;
	@ManagedProperty(value = "#{sistema}")
	private SistemaBean sistema;

	@ManagedProperty("#{bitacoraBean}")
	private BitacoraBean bitacoraBean;

	private transient ModelMapper modelMapper = new ModelMapper();

	private List<BadgeDTO> badgets;
	private BadgeDTO badget;
	private BadgeDTO badgetModificar;
	private boolean nuevo;
	private String rutaBadges;
	private String nombreFotoComun;
	private String rutaUndertow;
	private String rutaUndertowBadges;

	private transient UploadedFile archivoCargar;

	@ManagedProperty(value = "#{parametroSistemaService}")
	private transient ParametroSistemaService parametroSistemaService;

	public BadgetBean() {
		badget = new BadgeDTO();
	}

	@PostConstruct
	public void cargar() {
		badgets = new ArrayList<>();
		clasificaciones = clasificacionBadgeService.findAll();
		badgets = badgeService.findAll();
	}

	
	public String obtenerClasificacion(Integer idClasificacion){
		for (ClasificacionBadgeDTO clasificacion : clasificaciones) {
			if(clasificacion.getIdClasificacionBadge().equals(idClasificacion)){
				return clasificacion.getNombre();
			}
		}
		return "";
	}
	
	public String init() {
		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "VER_CAT_INS", "", requestActual(), TipoServicioEnum.LOCAL);
		rutaBadges = personaServiceFacade.obtenerRutaAlmacenamientoBadge();
		nombreFotoComun = personaServiceFacade.obtenerNombreImagenComun();
		rutaUndertow = personaServiceFacade.obtenerRutaUndertow();
		rutaUndertowBadges = personaServiceFacade.obtenerRutaUndertowBadges();
		clasificaciones = clasificacionBadgeService.findAll();
		badgets = badgeService.findAll();
		mostrarImagen();
		logger.info("Navegando a badges");
		return ConstantesGestorWeb.NAVEGA_BADGES;
	}

	public void mostrarImagen() {
		for (BadgeDTO badge : badgets) {
			String rutaAbsolutaBadge = rutaBadges.concat(badge.getRutaImagen());
			File archivo = new File(rutaAbsolutaBadge);
			if (archivo.exists()) {
				badge.setRutaCompleta(rutaUndertowBadges + badge.getRutaImagen());
			} else {
				badge.setRutaCompleta(rutaUndertow + nombreFotoComun);
			}
		}
	}

	public void nuevoBadget() {
		badget = new BadgeDTO();
		badget.setRutaCompleta(rutaUndertow + nombreFotoComun);
		badget.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
		nuevo = true;
	}

	public void cargarBadget() {
		badget = new BadgeDTO();
		modelMapper.map(getBadgetModificar(), badget);
		badget.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
		badget.setFechaActualizacion(new Date());
		nuevo = false;
	}

	public void guardarBadget() {
		badget.setIdEstatus(ConstantesGestorWeb.ID_ESTATUS_DEFAULT);
		if (nuevo) {
			badget.setFechaRegistro(new Date());
			almacenarBadget();
		} else {
			badget.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
			actualizarBadget();
		}
	}

	@Transactional(rollbackFor = Exception.class)
	private void actualizarBadget() {

		ResultadoDTO<BadgeDTO> resultado = badgeService.actualizar(badget);
		if (resultado.getResultado() == ResultadoTransaccionEnum.EXITOSO) {
			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "EDI_INS",
					String.valueOf(resultado.getDto().getIdBadge()), requestActual(), TipoServicioEnum.LOCAL);
			badgets.remove(badgetModificar);
			badgets.add(0, resultado.getDto());
			mostrarImagen();
			RequestContext.getCurrentInstance().execute("PF('mBadget').hide();");
			if (ObjectUtils.isNotNull(resultado.getMensajes())) {
				agregarMsgInfo(resultado.getMensajes().get(0), null, sistema);
			}
		} else if (ObjectUtils.isNotNull(resultado.getMensajes())) {
			agregarMsgError(resultado.getMensajes(), null, sistema);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	private void almacenarBadget() {

		ResultadoDTO<BadgeDTO> resultado = badgeService.guardar(badget);
		if (resultado.getResultado() == ResultadoTransaccionEnum.EXITOSO) {
			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CRE_INS",
					String.valueOf(resultado.getDto().getIdBadge()), requestActual(), TipoServicioEnum.LOCAL);
			badgets.add(0, resultado.getDto());
			mostrarImagen();
			RequestContext.getCurrentInstance().execute("PF('mBadget').hide();");
			if (ObjectUtils.isNotNull(resultado.getMensajes())) {
				agregarMsgInfo(resultado.getMensajes().get(0), null, sistema);
			}
		} else if (ObjectUtils.isNotNull(resultado.getMensajes())) {
			agregarMsgError(resultado.getMensajes(), null, sistema);
		}
	}

	public void fileUploadListener(FileUploadEvent e) {
		UploadedFile archivoCargar = e.getFile();
		if (ObjectUtils.isNotNull(archivoCargar) && !ObjectUtils.isNullOrEmpty(archivoCargar.getFileName())) {
			badget.setRutaImagen(UUID.randomUUID().toString());
			String rutaArchivo = rutaBadges.concat(badget.getRutaImagen());
			if (GestorArchivos.crearCarpeta(rutaBadges).getResultado().getValor() && GestorArchivos
					.almacenarArchivo(rutaArchivo, archivoCargar.getContents()).getResultado().getValor()) {
				badget.setRutaCompleta(rutaUndertowBadges.concat(badget.getRutaImagen()));
			}
		}
	}

	public StreamedContent getImagenInsignia(String ruta) throws Exception {
		StreamedContent img = null;
		if (ruta != null | !"".equals(ruta.trim())) {
			String rutaaux = ruta;
			byte[] f = Files.readAllBytes(new File(rutaaux).toPath());
			img = new DefaultStreamedContent(new ByteArrayInputStream(f));
			// img = new DefaultStreamedContent(new
			// ByteArrayInputStream(f.getAbsolutePath().getBytes()) ,
			// "image/png");
		}
		return img;
	}

	public BadgeService getBadgeService() {
		return badgeService;
	}

	public void setBadgeService(BadgeService badgeService) {
		this.badgeService = badgeService;
	}

	public ClasificacionBadgeService getClasificacionBadgeService() {
		return clasificacionBadgeService;
	}

	public void setClasificacionBadgeService(ClasificacionBadgeService clasificacionBadgeService) {
		this.clasificacionBadgeService = clasificacionBadgeService;
	}

	public List<ClasificacionBadgeDTO> getClasificaciones() {
		return clasificaciones;
	}

	public void setClasificaciones(List<ClasificacionBadgeDTO> clasificaciones) {
		this.clasificaciones = clasificaciones;
	}

	public SistemaBean getSistema() {
		return sistema;
	}

	public void setSistema(SistemaBean sistema) {
		this.sistema = sistema;
	}

	public ModelMapper getModelMapper() {
		return modelMapper;
	}

	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public List<BadgeDTO> getBadgets() {
		return badgets;
	}

	public void setBadgets(List<BadgeDTO> badgets) {
		this.badgets = badgets;
	}

	public BadgeDTO getBadget() {
		return badget;
	}

	public void setBadget(BadgeDTO badget) {
		this.badget = badget;
	}

	public BadgeDTO getBadgetModificar() {
		return badgetModificar;
	}

	public void setBadgetModificar(BadgeDTO badgetModificar) {
		this.badgetModificar = badgetModificar;
	}

	public boolean isNuevo() {
		return nuevo;
	}

	public void setNuevo(boolean nuevo) {
		this.nuevo = nuevo;
	}

	public UploadedFile getArchivoCargar() {
		return archivoCargar;
	}

	public void setArchivoCargar(UploadedFile archivoCargar) {
		this.archivoCargar = archivoCargar;
	}

	public ParametroSistemaService getParametroSistemaService() {
		return parametroSistemaService;
	}

	public void setParametroSistemaService(ParametroSistemaService parametroSistemaService) {
		this.parametroSistemaService = parametroSistemaService;
	}

	public PersonaServiceFacade getPersonaServiceFacade() {
		return personaServiceFacade;
	}

	public void setPersonaServiceFacade(PersonaServiceFacade personaServiceFacade) {
		this.personaServiceFacade = personaServiceFacade;
	}

	public BitacoraBean getBitacoraBean() {
		return bitacoraBean;
	}

	public void setBitacoraBean(BitacoraBean bitacoraBean) {
		this.bitacoraBean = bitacoraBean;
	}

}
