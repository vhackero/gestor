package mx.gob.sedesol.gestorweb.beans.administracion;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
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

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.ArchivoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroSistemaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.TemaDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoTemaEnum;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.basegestor.service.admin.TemaService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.utils.BitacoraUtil;
import mx.gob.sedesol.gestorweb.commons.utils.GestorArchivos;
import mx.gob.sedesol.gestorweb.sistema.SistemaBean;

@SessionScoped
@ManagedBean(name = "temaBean")
public class TemaBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(TemaBean.class);

	@ManagedProperty(value = "#{temaService}")
	private transient TemaService temaService;

	@ManagedProperty(value = "#{sistema}")
	private SistemaBean sistema;

	@ManagedProperty(value = "#{parametroSistemaService}")
	private transient ParametroSistemaService parametroSistemaService;

	@ManagedProperty("#{bitacoraBean}")
	private BitacoraBean bitacoraBean;

	private transient ModelMapper modelMapper = new ModelMapper();

	private int tipoTema;

	private List<TemaDTO> temas;
	private TemaDTO tema = new TemaDTO();
	private TemaDTO temaModificar;
	private List<ArchivoDTO> archivos;
	private ArchivoDTO archivo;
	private ArchivoDTO archivoNuevo;

	private transient StreamedContent imagen;
	private transient StreamedContent imagenNueva;

	private transient UploadedFile archivoCargar;

	private boolean nuevo;

	@PostConstruct
	public void init() {
		archivoCargar = null;
		tipoTema = TipoTemaEnum.PRIVADO.getValor();
		actualizarListaTemas();
	}

	public void actualizarListaTemas() {
		temas = temaService.buscarPorTipo(tipoTema);
	}

	public void nuevoTema() {
		tema = new TemaDTO(tipoTema, getUsuarioEnSession().getIdPersona());
		setNuevo(true);
	}

	public void cargarTema() {
		tema = new TemaDTO();
		modelMapper.map(temaModificar, tema);

		tema.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
		tema.setFechaActualizacion(new Date());

		setNuevo(false);
	}

	public boolean buscarNombre(TemaDTO t, List<TemaDTO> temas) {
		for (TemaDTO tema : temas) {
			if (tema.getNombre().equals(t.getNombre())) {
				return false;
			}
		}
		return true;
	}

	public void guardarTema() {
		ResultadoDTO<TemaDTO> resultado;

		if (isNuevo()) {

			if (buscarNombre(tema, temas)) {
				resultado = temaService.guardar(tema);
				if (resultado.getResultado() == ResultadoTransaccionEnum.EXITOSO) {

					bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CRE_TEM",
							String.valueOf(resultado.getDto().getIdTema()), requestActual(), TipoServicioEnum.LOCAL);

					actualizarListaTemas();
					RequestContext.getCurrentInstance().execute("PF('mTema').hide();");
					agregarMsgInfo(resultado.getMensajes().get(0), null, sistema);
				} else {
					agregarMsgError(resultado.getMensajes(), null, sistema);
				}
			} else {
				agregarMsgInfo("El nombre del tema ya existe.", null, sistema);
			}

		} else {
			tema.setFechaActualizacion(new Date());
			resultado = temaService.actualizar(tema);
			if (resultado.getResultado() == ResultadoTransaccionEnum.EXITOSO) {
				bitacoraBean.guardarBitacora(idPersonaEnSesion(), "EDI_TEM",
						String.valueOf(resultado.getDto().getIdTema()), requestActual(), TipoServicioEnum.LOCAL);

				actualizarListaTemas();
				RequestContext.getCurrentInstance().execute("PF('mTema').hide();");
				agregarMsgInfo(resultado.getMensajes().get(0), null, sistema);
			} else {
				agregarMsgError(resultado.getMensajes(), null, sistema);
			}
		}

		setNuevo(false);
	}

	public void cargarArchivo() {
		if (ObjectUtils.isNotNull(archivoCargar) && !ObjectUtils.isNullOrEmpty(archivoCargar.getFileName())) {

			StringBuilder rutaAnterior = new StringBuilder(
					parametroSistemaService.obtenerParametroConRutaCompleta(ConstantesGestor.PARAMETRO_RUTA_RECURSOS));
			rutaAnterior.append(obtenerRutaTipo(tipoTema));
			rutaAnterior.append("/");
			rutaAnterior.append(tema.getRuta());
			rutaAnterior.append("/");

			tema.setRuta(UUID.randomUUID().toString());

			StringBuilder rutaAlmacenamiento = new StringBuilder(
					parametroSistemaService.obtenerParametroConRutaCompleta(ConstantesGestor.PARAMETRO_RUTA_RECURSOS));
			rutaAlmacenamiento.append(obtenerRutaTipo(tipoTema));
			rutaAlmacenamiento.append("/");
			rutaAlmacenamiento.append(tema.getRuta());
			rutaAlmacenamiento.append("/");

			String rutaArchivo = rutaAlmacenamiento.toString() + archivoCargar.getFileName();

			if (GestorArchivos.reemplazarCarpeta(rutaAlmacenamiento.toString()).getResultado().getValor()
					&& GestorArchivos.almacenarArchivo(rutaArchivo, archivoCargar.getContents()).getResultado()
							.getValor()) {
				GestorArchivos.unzipFunction(rutaArchivo, rutaAlmacenamiento.toString());
				GestorArchivos.eliminarArchivo(rutaArchivo);
				GestorArchivos.eliminarArchivo(rutaAnterior.toString());

				tema.setFechaActualizacion(new Date());
				ResultadoDTO<TemaDTO> resultado = temaService.actualizar(tema);
				if (resultado.getResultado() == ResultadoTransaccionEnum.EXITOSO) {
					bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CAR_TEM",
							String.valueOf(resultado.getDto().getIdTema()), requestActual(), TipoServicioEnum.LOCAL);

					agregarMsgInfo(MensajesSistemaEnum.TEMAS_CARGAR_EXITO.getId(), null, sistema);
				} else {
					agregarMsgInfo("Ocurrio un error al actualizar el tema", null);
				}

			} else {
				agregarMsgError(MensajesSistemaEnum.TEMAS_CARGAR_ERROR.getId(), null, sistema);
			}
		} else {
			agregarMsgError(MensajesSistemaEnum.TEMAS_CARGAR_ERROR.getId(), null, sistema);
		}
		archivoCargar = null;
		tema = null;
		actualizarListaTemas();
	}

	/**
	 * Elimina la instancia del objeto UploadedFile si se cargo algun archivo
	 */
	public void eliminarArchivo() {
		archivoCargar = null;
	}

	public String activarTema() {

		if (temaService.validarTema(tema)) {
			tema.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
			ResultadoDTO<TemaDTO> resultado = temaService.activarTema(tema);
			if (resultado.getResultado().getValor()) {
				actualizarListaTemas();
				sistema.establecerTema(tema.getRuta(), TipoTemaEnum.getTipoTemaEnum(tema.getTipoTema()));
				bitacoraBean.guardarBitacora(idPersonaEnSesion(), "ACT_TEM", String.valueOf(tema.getIdTema()),
						requestActual(), TipoServicioEnum.LOCAL);
				agregarMsgInfo(MensajesSistemaEnum.TEMAS_ACTIVAR_EXITO.getId(), null, sistema);
			} else {
				agregarMsgError(MensajesSistemaEnum.TEMAS_ACTIVAR_ERROR.getId(), null, sistema);
			}
		} else {
			agregarMsgError(MensajesSistemaEnum.TEMAS_INVALIDO.getId(), null, sistema);
		}
		tema = null;
		return null;
	}

	private String obtenerRutaTipo(int tipoTema) {
		switch (TipoTemaEnum.getTipoTemaEnum(tipoTema)) {
		case PUBLICO:
			return ConstantesGestor.RUTA_RECURSOS_PUBLICO;
		case PRIVADO:
			return ConstantesGestor.RUTA_RECURSOS_PRIVADO;
		default:
			return "_";
		}
	}

	public String cargarArchivosTema() {
		StringBuilder rutaAlmacenamiento = new StringBuilder(
				parametroSistemaService.obtenerParametroConRutaCompleta(ConstantesGestor.PARAMETRO_RUTA_RECURSOS));
		rutaAlmacenamiento.append(obtenerRutaTipo(tipoTema));
		rutaAlmacenamiento.append("/");
		rutaAlmacenamiento.append(tema.getRuta());
		rutaAlmacenamiento.append("/");
		rutaAlmacenamiento.append(ConstantesGestor.RUTA_IMAGENES);

		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "VER_ARC_TEM", String.valueOf(tema.getIdTema()),
				requestActual(), TipoServicioEnum.LOCAL);

		archivos = GestorArchivos.obtenerArchivos(rutaAlmacenamiento.toString());
		return ConstantesGestorWeb.NAVEGA_ARCHIVOS_TEMA;
	}

	public String cargarArchivoTema() {
		InputStream stream = new ByteArrayInputStream(archivo.getDatos());
		imagen = new DefaultStreamedContent(stream);
		return ConstantesGestorWeb.NAVEGA_ARCHIVO_TEMA;
	}

	public void fileUploadListener(FileUploadEvent e) {
		archivoNuevo = new ArchivoDTO();
		archivoNuevo.setDatos(e.getFile().getContents());
		InputStream stream = new ByteArrayInputStream(archivoNuevo.getDatos());
		imagenNueva = new DefaultStreamedContent(stream);
	}

	public String cancelarEditarArchivo() {
		return ConstantesGestorWeb.NAVEGA_ARCHIVOS_TEMA;
	}

	public String guardarArchivo() {
		StringBuilder rutaAlmacenamiento = new StringBuilder(
				parametroSistemaService.obtenerParametroConRutaCompleta(ConstantesGestor.PARAMETRO_RUTA_RECURSOS));
		rutaAlmacenamiento.append(obtenerRutaTipo(tipoTema));
		rutaAlmacenamiento.append(File.separator);
		rutaAlmacenamiento.append(tema.getRuta());
		rutaAlmacenamiento.append(File.separator);

		String rutaArchivo = rutaAlmacenamiento.toString() + ConstantesGestor.RUTA_IMAGENES + File.separator
				+ archivo.getNombre();
		if (GestorArchivos.almacenarArchivo(rutaArchivo, archivoNuevo.getDatos()).getResultado().getValor()) {
			StringBuilder rutaNueva = new StringBuilder(
					parametroSistemaService.obtenerParametroConRutaCompleta(ConstantesGestor.PARAMETRO_RUTA_RECURSOS));
			tema.setRuta(UUID.randomUUID().toString());
			rutaNueva.append(obtenerRutaTipo(tipoTema));
			rutaNueva.append(File.separator);
			rutaNueva.append(tema.getRuta());
			rutaNueva.append(File.separator);

			File dir = new File(rutaAlmacenamiento.toString());
			File newName = new File(rutaNueva.toString());
			if (dir.isDirectory() && dir.renameTo(newName)) {
				temaService.actualizar(tema);
			}
		}

		return cargarArchivosTema();
	}

	/* INICIO DE GETS Y SETS */
	public TipoTemaEnum[] getTiposTemas() {
		return TipoTemaEnum.values();
	}

	public TemaService getTemaService() {
		return temaService;
	}

	public void setTemaService(TemaService temaService) {
		this.temaService = temaService;
	}

	public int getTipoTema() {
		return tipoTema;
	}

	public void setTipoTema(int tipoTema) {
		this.tipoTema = tipoTema;
	}

	public List<TemaDTO> getTemas() {
		return temas;
	}

	public void setTemas(List<TemaDTO> temas) {
		this.temas = temas;
	}

	public TemaDTO getTema() {
		return tema;
	}

	public void setTema(TemaDTO tema) {
		this.tema = tema;
	}

	public TemaDTO getTemaModificar() {
		return temaModificar;
	}

	public void setTemaModificar(TemaDTO temaModificar) {
		this.temaModificar = temaModificar;
	}

	public SistemaBean getSistema() {
		return sistema;
	}

	public void setSistema(SistemaBean sistema) {
		this.sistema = sistema;
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

	public List<ArchivoDTO> getArchivos() {
		return archivos;
	}

	public void setArchivos(List<ArchivoDTO> archivos) {
		this.archivos = archivos;
	}

	public ArchivoDTO getArchivo() {
		return archivo;
	}

	public void setArchivo(ArchivoDTO archivo) {
		this.archivo = archivo;
	}

	public StreamedContent getImagen() {
		return imagen;
	}

	public void setImagen(StreamedContent imagen) {
		this.imagen = imagen;
	}

	public StreamedContent getImagenNueva() {
		return imagenNueva;
	}

	public void setImagenNueva(StreamedContent imagenNueva) {
		this.imagenNueva = imagenNueva;
	}

	public boolean isNuevo() {
		return nuevo;
	}

	public void setNuevo(boolean nuevo) {
		this.nuevo = nuevo;
	}

	public BitacoraBean getBitacoraBean() {
		return bitacoraBean;
	}

	public void setBitacoraBean(BitacoraBean bitacoraBean) {
		this.bitacoraBean = bitacoraBean;
	}

}
