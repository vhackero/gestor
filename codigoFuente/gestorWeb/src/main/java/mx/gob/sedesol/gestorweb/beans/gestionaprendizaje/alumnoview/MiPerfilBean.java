package mx.gob.sedesol.gestorweb.beans.gestionaprendizaje.alumnoview;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.UploadedFile;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.AsentamientoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.CapturaPersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.EntidadFederativaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.MunicipioDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PaisDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.RolDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.OrdenGobiernoEnum;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.service.impl.admin.PersonaServiceFacade;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.beans.administracion.BitacoraBean;
import mx.gob.sedesol.gestorweb.commons.utils.GestorArchivos;
import mx.gob.sedesol.gestorweb.sistema.SistemaBean;

@ManagedBean
@ViewScoped
public class MiPerfilBean extends BaseBean {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(MiPerfilBean.class);
	@ManagedProperty(value = "#{personaServiceFacade}")
	private PersonaServiceFacade personaServiceFacade;

	@ManagedProperty("#{sistema}")
	private SistemaBean textosSistema;

	@ManagedProperty("#{bitacoraBean}")
	private BitacoraBean bitacoraBean;

	private Long idUsuarioEnSesion;
	private CapturaPersonaDTO personaEnSesion;
	private List<PersonaDTO> personas = new ArrayList<>();
	private DualListModel<RolDTO> listaRoles;
	private PersonaDTO persona;
	private List<PaisDTO> listaPaises;
	private List<EntidadFederativaDTO> listaEntidades;
	private List<MunicipioDTO> listaMunicipios;
	private List<AsentamientoDTO> listaAsentamientos;
	private String rutaFotografias;
	private String rutaUndertow;
	private String nombreFotoComun;
	private String nombreParaComparar;
	private transient UploadedFile archivoCargar;

	@PostConstruct
	public void init() {

		rutaFotografias = personaServiceFacade.obtenerRutaAlmacenamientoFotosUsuario();
		rutaUndertow = personaServiceFacade.obtenerRutaUndertow();
		nombreFotoComun = personaServiceFacade.obtenerNombreImagenComun();
		idUsuarioEnSesion = getUsuarioEnSession().getIdPersona();
		personaEnSesion = new CapturaPersonaDTO();
		persona = personaServiceFacade.obtenerPersonaPorId(idUsuarioEnSesion);
		personaEnSesion = personaServiceFacade.obtenerDatosPersona(persona, idUsuarioEnSesion);

		if (ObjectUtils.isNullOrEmpty(personaEnSesion.getPersona().getRutaFoto())) {
			personaEnSesion.getPersona().setRutaCompletaFoto(rutaUndertow + nombreFotoComun);
		} else if (!(new File(rutaFotografias + personaEnSesion.getPersona().getRutaFoto()).exists())) {
			personaEnSesion.getPersona().setRutaCompletaFoto(rutaUndertow + nombreFotoComun);
		} else {
			personaEnSesion.getPersona().setRutaCompletaFoto(rutaUndertow + personaEnSesion.getPersona().getRutaFoto());
			nombreParaComparar = rutaFotografias + personaEnSesion.getPersona().getRutaFoto();
		}

		listaPaises = personaServiceFacade.obtenerPaises();
		listaEntidades = personaServiceFacade.obtenerEntidadesPorPais(ConstantesGestor.ID_PAIS_MEXICO);

		if (ObjectUtils.isNotNull(personaEnSesion.getDomicilioPersona().getAsentamiento())) {
			if (ObjectUtils.isNotNull(personaEnSesion.getDomicilioPersona().getAsentamiento())) {
				if (ObjectUtils.isNotNull(personaEnSesion.getDomicilioPersona().getAsentamiento().getMunicipio())) {
					if (ObjectUtils.isNotNull(personaEnSesion.getDomicilioPersona().getAsentamiento().getMunicipio()
							.getEntidadFederativa())) {
						listaMunicipios = personaServiceFacade
								.obtenerMunicipiosPorEntidad(personaEnSesion.getDomicilioPersona().getAsentamiento()
										.getMunicipio().getEntidadFederativa().getIdEntidadFederativa());
						listaAsentamientos = personaServiceFacade.obtenerAsentamientosPorMunicipio(personaEnSesion
								.getDomicilioPersona().getAsentamiento().getMunicipio().getIdMunicipio());
					}
				}
			}
		}
		listaRoles = new DualListModel<>(personaServiceFacade.obtenerTodosRoles(), new ArrayList<>());
		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "VER_PER",
				String.valueOf(personaEnSesion.getPersona().getIdPersona()), requestActual(), TipoServicioEnum.LOCAL);
	}

	public void fileUploadListener(FileUploadEvent e) {

		UploadedFile archivoCargar = e.getFile();
		if (ObjectUtils.isNotNull(archivoCargar) && !ObjectUtils.isNullOrEmpty(archivoCargar.getFileName())) {

			personaEnSesion.getPersona().setRutaFoto(UUID.randomUUID().toString());
			String rutaArchivo = rutaFotografias + personaEnSesion.getPersona().getRutaFoto();

			if (GestorArchivos.crearCarpeta(rutaFotografias).getResultado().getValor() && GestorArchivos
					.almacenarArchivo(rutaArchivo, archivoCargar.getContents()).getResultado().getValor()) {
				personaEnSesion.getPersona()
						.setRutaCompletaFoto(rutaUndertow + personaEnSesion.getPersona().getRutaFoto());
				bitacoraBean.guardarBitacora(idPersonaEnSesion(), "EDI_PER",
						String.valueOf(personaEnSesion.getPersona().getIdPersona()), requestActual(),
						TipoServicioEnum.LOCAL);
			}
		}
	}

	public void validarCodigoPostal() {
		AsentamientoDTO asentamiento = personaServiceFacade.obtenerAsentamientoPorCodigoPostal(
				personaEnSesion.getDomicilioPersona().getAsentamiento().getCodigoPostal());
		if (ObjectUtils.isNotNull(asentamiento)) {
			personaEnSesion.getDomicilioPersona().setAsentamiento(new AsentamientoDTO());
			personaEnSesion.getDomicilioPersona().getAsentamiento().setIdAsentamiento(asentamiento.getIdAsentamiento());
			personaEnSesion.getDomicilioPersona().setIdMunicipio(asentamiento.getMunicipio().getIdMunicipio());
			personaEnSesion.getDomicilioPersona().setIdEntidadFederativa(
					asentamiento.getMunicipio().getEntidadFederativa().getIdEntidadFederativa());
			personaEnSesion.getDomicilioPersona()
					.setIdPais(asentamiento.getMunicipio().getEntidadFederativa().getPais().getIdPais());
			listaEntidades = personaServiceFacade
					.obtenerEntidadesPorPais(personaEnSesion.getDomicilioPersona().getIdPais());
			listaMunicipios = personaServiceFacade
					.obtenerMunicipiosPorEntidad(personaEnSesion.getDomicilioPersona().getIdEntidadFederativa());
			listaAsentamientos = personaServiceFacade
					.obtenerAsentamientosPorMunicipio(personaEnSesion.getDomicilioPersona().getIdMunicipio());
		}
	}

	public void actualizarPersona() {

		ResultadoDTO<PersonaDTO> resultado = personaServiceFacade.actualizarPersona(personaEnSesion);
		if (resultado.getResultado() == ResultadoTransaccionEnum.EXITOSO) {
			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "EDI_PER",
					String.valueOf(personaEnSesion.getPersona().getIdPersona()), requestActual(),
					TipoServicioEnum.LOCAL);
			personas.remove(persona);
			personas.add(0, resultado.getDto());
			if (ObjectUtils.isNotNull(resultado.getMensajes())) {
				agregarMsgInfo(resultado.getMensajes().get(0), null, textosSistema);
			}

		} else {
			if (ObjectUtils.isNotNull(resultado.getMensajes())) {
				agregarMsgError(resultado.getMensajes(), null, textosSistema);
			}
		}
	}

	public String obtenerNombreOrdenPorId(String idOrden) {
		String nombreOrden = "";
		if (ObjectUtils.isNotNull(idOrden)) {
			for (OrdenGobiernoEnum ordenEnum : OrdenGobiernoEnum.values()) {
				if (idOrden.equals(ordenEnum.getValor())) {
					nombreOrden = ordenEnum.getDescripcion();
				}
			}
		} else {
			return nombreOrden;
		}
		return nombreOrden;
	}

	public void onChangePais(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			String valor = (String) e.getNewValue();
			listaEntidades = personaServiceFacade.obtenerEntidadesPorPais(valor);
		}
	}

	public void onChangeEntidad(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {

			Integer valor = (Integer) e.getNewValue();
			listaMunicipios = personaServiceFacade.obtenerMunicipiosPorEntidad(valor);
		}
	}

	public void onChangeMunicipio(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {

			String valor = (String) e.getNewValue();
			listaAsentamientos = personaServiceFacade.obtenerAsentamientosPorMunicipio(valor);
		}
	}

	public PersonaServiceFacade getPersonaServiceFacade() {
		return personaServiceFacade;
	}

	public void setPersonaServiceFacade(PersonaServiceFacade personaServiceFacade) {
		this.personaServiceFacade = personaServiceFacade;
	}

	public PersonaDTO getPersona() {
		return persona;
	}

	public void setPersona(PersonaDTO persona) {
		this.persona = persona;
	}

	public List<PaisDTO> getListaPaises() {
		return listaPaises;
	}

	public void setListaPaises(List<PaisDTO> listaPaises) {
		this.listaPaises = listaPaises;
	}

	public List<EntidadFederativaDTO> getListaEntidades() {
		return listaEntidades;
	}

	public void setListaEntidades(List<EntidadFederativaDTO> listaEntidades) {
		this.listaEntidades = listaEntidades;
	}

	public Long getIdUsuarioEnSesion() {
		return idUsuarioEnSesion;
	}

	public void setIdUsuarioEnSesion(Long idUsuarioEnSesion) {
		this.idUsuarioEnSesion = idUsuarioEnSesion;
	}

	public List<MunicipioDTO> getListaMunicipios() {
		return listaMunicipios;
	}

	public void setListaMunicipios(List<MunicipioDTO> listaMunicipios) {
		this.listaMunicipios = listaMunicipios;
	}

	public List<AsentamientoDTO> getListaAsentamientos() {
		return listaAsentamientos;
	}

	public void setListaAsentamientos(List<AsentamientoDTO> listaAsentamientos) {
		this.listaAsentamientos = listaAsentamientos;
	}

	public CapturaPersonaDTO getPersonaEnSesion() {
		return personaEnSesion;
	}

	public void setPersonaEnSesion(CapturaPersonaDTO personaEnSesion) {
		this.personaEnSesion = personaEnSesion;
	}

	public UploadedFile getArchivoCargar() {
		return archivoCargar;
	}

	public void setArchivoCargar(UploadedFile archivoCargar) {
		this.archivoCargar = archivoCargar;
	}

	public SistemaBean getTextosSistema() {
		return textosSistema;
	}

	public void setTextosSistema(SistemaBean textosSistema) {
		this.textosSistema = textosSistema;
	}

	public DualListModel<RolDTO> getListaRoles() {
		return listaRoles;
	}

	public void setListaRoles(DualListModel<RolDTO> listaRoles) {
		this.listaRoles = listaRoles;
	}

	public String getNombreParaComparar() {
		return nombreParaComparar;
	}

	public void setNombreParaComparar(String nombreParaComparar) {
		this.nombreParaComparar = nombreParaComparar;
	}

	public BitacoraBean getBitacoraBean() {
		return bitacoraBean;
	}

	public void setBitacoraBean(BitacoraBean bitacoraBean) {
		this.bitacoraBean = bitacoraBean;
	}

}
