package mx.gob.sedesol.gestorweb.beans.administracion;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.BitacoraDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.LoteCargaUsuarioDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaCargaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaSigeDTO;
// import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaSigeDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoCargaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.commons.utils.UtilidadesCargaMasiva;
import mx.gob.sedesol.basegestor.mongo.service.BitacoraService;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.basegestor.service.admin.LoteCargaUsuarioService;
import mx.gob.sedesol.basegestor.service.admin.PersonaService;
import mx.gob.sedesol.basegestor.service.admin.PersonaSigeService;
import mx.gob.sedesol.basegestor.service.impl.admin.PersonaServiceImpl;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.utils.BitacoraUtil;
import mx.gob.sedesol.gestorweb.commons.utils.GestorArchivos;
import mx.gob.sedesol.gestorweb.sistema.SistemaBean;

@SessionScoped
@ManagedBean(name = "cargaMasivaUsuariosBean")
public class CargaMasivaUsuariosBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(name = "personaService", value = "#{personaService}")
	private transient PersonaService personaService;

	@ManagedProperty(value = "#{parametroSistemaService}")
	private transient ParametroSistemaService parametroSistemaService;

	@ManagedProperty(name = "loteCargaUsuarioService", value = "#{loteCargaUsuarioService}")
	private transient LoteCargaUsuarioService loteCargaUsuarioService;

	@ManagedProperty(name = "sistema", value = "#{sistema}")
	private SistemaBean sistema;
	@ManagedProperty("#{bitacoraService}")
	private BitacoraService bitacoraService;

	@ManagedProperty("#{bitacoraBean}")
	private BitacoraBean bitacoraBean;

	private transient BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	private static final Logger logger = Logger.getLogger(PersonaServiceImpl.class);
	private transient UploadedFile archivo;
	private ResultadoCargaDTO resultado;
	private List<LoteCargaUsuarioDTO> lotes;
	private LoteCargaUsuarioDTO lote;
	private LoteCargaUsuarioDTO filtrosLote = new LoteCargaUsuarioDTO();

	private String rutaCargaMasiva;

	private boolean mostrarResultados;

	public String inicioCargaUsuarios() {
		rutaCargaMasiva = parametroSistemaService
				.obtenerParametroConRutaCompleta(ConstantesGestor.PARAMETRO_RUTA_CARGA_MASIVA);
		resultado = new ResultadoCargaDTO();
		lotes = loteCargaUsuarioService.buscarPorCriterios(filtrosLote);
		lote = new LoteCargaUsuarioDTO(getUsuarioEnSession().getIdPersona());
		mostrarResultados = false;
		return ConstantesGestorWeb.NAVEGA_CARGA_USUARIOS;
	}

	public void fileUploadListener(FileUploadEvent e) {
		archivo = e.getFile();
		validarUsuarios();
	}

	public String validarUsuarios() {

		if (archivo != null) {

			String rutaArchivo = rutaCargaMasiva + UUID.randomUUID().toString();
			lote.setNombreArchivo(archivo.getFileName());

			if (GestorArchivos.crearCarpeta(rutaCargaMasiva).getResultado().getValor()
					&& GestorArchivos.almacenarArchivo(rutaArchivo, archivo.getContents()).getResultado().getValor()) {
				resultado = personaService.procesarArchivo(rutaArchivo, getUsuarioEnSession().getIdPersona());
				if (resultado.getRegistros().isEmpty()) {
					mostrarResultados = false;
				} else {
					mostrarResultados = true;
					UtilidadesCargaMasiva.crearDocumento(resultado, rutaCargaMasiva);
					lote.setRutaArchivo(resultado.getRutaArchivo());
				}
			}
		}
		return ConstantesGestorWeb.NAVEGA_CARGA_USUARIOS;
	}

	public StreamedContent getArchivoResultado() throws IOException {
		File archivoResultado = new File(resultado.getRutaCompleta());
		if (archivoResultado.canRead()) {
			return new DefaultStreamedContent(new FileInputStream(archivoResultado),
					ConstantesGestor.ARCHIVO_TIPO_EXCEL, ConstantesGestor.NOMBRE_RESULTADO_CARGA);
		} else {
			return null;
		}
	}

	public String guardarLote() {
		List<PersonaCargaDTO> personasToInsert = new ArrayList<PersonaCargaDTO>();
		for (PersonaCargaDTO personaCarga : this.resultado.getRegistros()) {
			personaCarga.setHash(personaCarga.getContrasenia());
			personasToInsert.add(personaCarga);
			personaCarga.setContraseniaEncriptada(encoder.encode(personaCarga.getContrasenia()));
		}
		
		this.resultado.setRegistros(personasToInsert);
		
		ResultadoDTO<LoteCargaUsuarioDTO> resultadoGuardar = personaService.guardar(lote, this.resultado,
				rutaCargaMasiva);
		if (resultadoGuardar.getResultado() == ResultadoTransaccionEnum.EXITOSO) {
			agregarMsgInfo(resultadoGuardar.getMensajes().get(0), null, sistema);
			inicioCargaUsuarios();
			mostrarResultados = false;
		} else {
			agregarMsgError(resultadoGuardar.getMensajes(), null, sistema);
			mostrarResultados = true;
		}
		return ConstantesGestorWeb.NAVEGA_CARGA_USUARIOS;
	}

	public StreamedContent getArchivoLote() throws IOException {
		String ruta = parametroSistemaService
				.obtenerParametroConRutaCompleta(ConstantesGestor.PARAMETRO_RUTA_CARGA_MASIVA);
		String rutaArchivo = ruta + lote.getRutaArchivo();
		File archivoLote = new File(rutaArchivo);
		if (archivoLote.canRead()) {
			return new DefaultStreamedContent(new FileInputStream(archivoLote), ConstantesGestor.ARCHIVO_TIPO_EXCEL,
					ConstantesGestor.NOMBRE_RESULTADO_CARGA);
		} else {
			return null;
		}
	}

	public StreamedContent getPlantilla() throws IOException {
		byte[] plantilla = UtilidadesCargaMasiva.crearPlantilla();

		InputStream inputStream = new ByteArrayInputStream(plantilla);

		if (ObjectUtils.isNotNull(plantilla)) {
			return new DefaultStreamedContent(inputStream, ConstantesGestor.ARCHIVO_TIPO_EXCEL,
					ConstantesGestor.NOMBRE_PLANTILLA_CARGA);
		} else {
			return null;
		}
	}

	/* INICIO DE GETS Y SETS */
	public PersonaService getPersonaService() {
		return personaService;
	}

	public void setPersonaService(PersonaService personaService) {
		this.personaService = personaService;
	}

	public UploadedFile getArchivo() {
		return archivo;
	}

	public void setArchivo(UploadedFile archivo) {
		this.archivo = archivo;
	}

	public ParametroSistemaService getParametroSistemaService() {
		return parametroSistemaService;
	}

	public void setParametroSistemaService(ParametroSistemaService parametroSistemaService) {
		this.parametroSistemaService = parametroSistemaService;
	}

	public ResultadoCargaDTO getResultado() {
		return resultado;
	}

	public void setResultado(ResultadoCargaDTO resultado) {
		this.resultado = resultado;
	}

	public List<LoteCargaUsuarioDTO> getLotes() {
		return lotes;
	}

	public void setLotes(List<LoteCargaUsuarioDTO> lotes) {
		this.lotes = lotes;
	}

	public LoteCargaUsuarioDTO getLote() {
		return lote;
	}

	public void setLote(LoteCargaUsuarioDTO lote) {
		this.lote = lote;
	}

	public boolean isMostrarResultados() {
		return mostrarResultados;
	}

	public void setMostrarResultados(boolean mostrarResultados) {
		this.mostrarResultados = mostrarResultados;
	}

	public LoteCargaUsuarioService getLoteCargaUsuarioService() {
		return loteCargaUsuarioService;
	}

	public void setLoteCargaUsuarioService(LoteCargaUsuarioService loteCargaUsuarioService) {
		this.loteCargaUsuarioService = loteCargaUsuarioService;
	}

	public SistemaBean getSistema() {
		return sistema;
	}

	public void setSistema(SistemaBean sistema) {
		this.sistema = sistema;
	}

	public LoteCargaUsuarioDTO getFiltrosLote() {
		return filtrosLote;
	}

	public void setFiltrosLote(LoteCargaUsuarioDTO filtrosLote) {
		this.filtrosLote = filtrosLote;
	}

	public BitacoraService getBitacoraService() {
		return bitacoraService;
	}

	public void setBitacoraService(BitacoraService bitacoraService) {
		this.bitacoraService = bitacoraService;
	}

	public BitacoraBean getBitacoraBean() {
		return bitacoraBean;
	}

	public void setBitacoraBean(BitacoraBean bitacoraBean) {
		this.bitacoraBean = bitacoraBean;
	}
	
}
