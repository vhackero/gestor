package mx.gob.sedesol.gestorweb.beans.administracion;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.ArchivoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PlantillaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.DateUtils;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoDocumentoEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.basegestor.service.admin.PlantillaService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.dto.ReporteConfig;
import mx.gob.sedesol.gestorweb.commons.utils.BitacoraUtil;
import mx.gob.sedesol.gestorweb.commons.utils.GestorArchivos;
import mx.gob.sedesol.gestorweb.commons.utils.ReporteUtil;
import mx.gob.sedesol.gestorweb.sistema.SistemaBean;

@SessionScoped
@ManagedBean(name = "plantillasDocumentosBean")
public class PlantillasDocumentosBean extends BaseBean {

	private static final long serialVersionUID = -5438902299195042046L;

	private static final Logger logger = Logger.getLogger(PlantillasDocumentosBean.class);

	@ManagedProperty(value = "#{plantillaService}")
	private transient PlantillaService plantillaService;

	@ManagedProperty(value = "#{parametroSistemaService}")
	private transient ParametroSistemaService parametroSistemaService;

	@ManagedProperty(value = "#{sistema}")
	private SistemaBean sistema;

	@ManagedProperty("#{bitacoraBean}")
	private BitacoraBean bitacoraBean;

	private List<PlantillaDTO> plantillas = new ArrayList<>();
	private PlantillaDTO plantilla;
	private PlantillaDTO plantillaModificar;

	private Long idPlantilla;

	private StreamedContent imagenEncabezado;

	private StreamedContent plantillaPDF;

	private Integer tipoDocumento;

	private boolean nueva;

	private StringBuilder rutaImagenes;
	private StringBuilder rutaUndertow;


	@PostConstruct
	public void init() {
		rutaImagenes = new StringBuilder();
		rutaImagenes.append(
				parametroSistemaService.obtenerParametroConRutaCompleta(ConstantesGestor.PARAMETRO_RUTA_RECURSOS));
		rutaImagenes.append(
				parametroSistemaService.obtenerParametroRuta(ConstantesGestor.PARAMETRO_RUTA_IMAGENES_DOCUMENTOS));

		rutaUndertow = new StringBuilder();
		rutaUndertow.append(FacesContext.getCurrentInstance().getExternalContext().getRequestScheme());
		rutaUndertow.append("://");
		rutaUndertow.append(FacesContext.getCurrentInstance().getExternalContext().getRequestServerName());
		rutaUndertow.append(":");
		rutaUndertow.append(FacesContext.getCurrentInstance().getExternalContext().getRequestServerPort());
		rutaUndertow.append(parametroSistemaService.obtenerParametroRuta(ConstantesGestor.PARAMETRO_RUTA_UNDERTOW));
		rutaUndertow.append(
				parametroSistemaService.obtenerParametroRuta(ConstantesGestor.PARAMETRO_RUTA_IMAGENES_DOCUMENTOS));
	}

	public TipoDocumentoEnum[] getTiposDocumento() {
		return TipoDocumentoEnum.values();
	}

	public void cargarPlantillas(ValueChangeEvent e) {
		if (ObjectUtils.isNull(e.getNewValue())) {
			plantillas = new ArrayList<>();
		} else {
			tipoDocumento = Integer.parseInt(e.getNewValue().toString());
			plantillas = plantillaService.obtenerPlantillasPorTipoDocumento(tipoDocumento);
			for (PlantillaDTO plantillaDTO : plantillas) {
				if (plantillaDTO.getActivo()) {
					idPlantilla = plantillaDTO.getIdPlantilla();
				}
			}
		}
	}

	public String cargarPlantillas() {
		plantillas = plantillaService.obtenerPlantillasPorTipoDocumento(tipoDocumento);
		for (PlantillaDTO plantillaDTO : plantillas) {
			if (plantillaDTO.getActivo()) {
				idPlantilla = plantillaDTO.getIdPlantilla();
			}
		}
		return ConstantesGestorWeb.NAVEGA_PLANTILLAS_DOCS;
	}

	public void seleccionarPlantilla(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			for (PlantillaDTO plantillaDTO : plantillas) {
				plantillaDTO.setFechaActualizacion(new Date());
				plantillaDTO.setUsuarioCreo(getUsuarioEnSession().getIdPersona());
				if (plantillaDTO.getIdPlantilla().equals(e.getNewValue())) {
					idPlantilla = (Long) e.getNewValue();
					plantillaDTO.setActivo(true);
					agregarMsgInfo("Se activó la plantilla", null);

				} else {
					plantillaDTO.setActivo(false);
				}
				ResultadoDTO<PlantillaDTO> resultado = plantillaService.actualizar(plantillaDTO);
				if (resultado.getResultado() == ResultadoTransaccionEnum.EXITOSO) {
					bitacoraBean.guardarBitacora(idPersonaEnSesion(), "ACT_PLA_DOC",
							String.valueOf(resultado.getDto().getIdPlantilla()), requestActual(),
							TipoServicioEnum.LOCAL);
				} else {
					agregarMsgInfo("Ocurrió un error al activar la plantilla", null);
				}
			}
		}
	}

	public void mostrarGrowlCambioPlantilla() {
		agregarMsgInfo("Se ha activado la plantilla", null);
	}

	public String nuevaPlantilla() {
		plantilla = new PlantillaDTO(getUsuarioEnSession().getIdPersona());
		plantilla.setTipoDocumento(tipoDocumento);
		nueva = true;
		return ConstantesGestorWeb.NAVEGA_PLANTILLA_DOCS;
	}

	public String cargarPlantilla() {
		plantilla = plantillaModificar;
		plantilla.setFechaActualizacion(new Date());
		plantilla.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
		nueva = false;
		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "VER_PLA_DOC", String.valueOf(plantilla.getIdPlantilla()),
				requestActual(), TipoServicioEnum.LOCAL);

		return ConstantesGestorWeb.NAVEGA_PLANTILLA_DOCS;
	}

	public List<StreamedContent> cargaImagenesEncabezado() {
		List<StreamedContent> lista = new ArrayList<>();
		List<ArchivoDTO> archivos = GestorArchivos.obtenerArchivos(rutaImagenes.toString());

		for (ArchivoDTO archivo : archivos) {
			InputStream inputStream = new ByteArrayInputStream(archivo.getDatos());
			StreamedContent sc = new DefaultStreamedContent(inputStream, archivo.getTipo(), archivo.getNombre());
			lista.add(sc);
		}

		return lista;
	}

	public void seleccionaEncabezado() {
		if (ObjectUtils.isNotNull(imagenEncabezado)) {
			plantilla.setImagenFondo(imagenEncabezado.getName());
			agregarMsgInfo("La imagen de fondo se ha seleccionado.", null);
		}
	}

	public StreamedContent getEncabezado() {
		if (!(ObjectUtils.isNull(plantilla) || ObjectUtils.isNullOrEmpty(plantilla.getImagenFondo()))) {
			ArchivoDTO archivo = GestorArchivos.obtenerArchivo(rutaImagenes + plantilla.getImagenFondo());
			if (ObjectUtils.isNotNull(archivo)) {
				InputStream inputStream = new ByteArrayInputStream(archivo.getDatos());
				StreamedContent sc = new DefaultStreamedContent(inputStream, archivo.getTipo(), archivo.getNombre());
				return sc;
			}
		}
		return new DefaultStreamedContent(new ByteArrayInputStream(new byte[0]), "image/png");
	}

	public void verPlantilla() {

		if (ObjectUtils.isNotNull(plantilla)) {
			if (!ObjectUtils.isNullOrEmpty(plantilla.getImagenFondo())) {
				plantillaPDF = null;
				try {

					TipoDocumentoEnum tipoDocumentoEnum = TipoDocumentoEnum.getTipoDocumentoEnum(tipoDocumento);

					ReporteConfig reporteConfig = new ReporteConfig();
					reporteConfig.setDatos(null);
					reporteConfig.setNombreReporte("Constancia");
					reporteConfig.setPathJasper(obtenerPlantillaJasper(tipoDocumentoEnum));
					reporteConfig.setTipoReporte(ReporteUtil.REPORTE_PDF);

					String lugar = parametroSistemaService.obtenerParametro(ConstantesGestor.CIUDAD_CONSTANCIA);
					String nombreDirectorGeneral = parametroSistemaService
							.obtenerParametro(ConstantesGestor.NOMBRE_DIRECTOR_GENERAL);

					String parrafo1 = insertaVariablesEnParrafo(plantilla.getParrafo1(), lugar);
					String parrafo2 = insertaVariablesEnParrafo(plantilla.getParrafo2(), lugar);
					String parrafo3 = insertaVariablesEnParrafo(plantilla.getParrafo3(), lugar);

					HashMap<String, Object> params = new HashMap<>();
					params.put("P_IMAGEN", rutaUndertow.toString() + plantilla.getImagenFondo());
					params.put("P_NOMBRE_ACREDITADO", "José López López");
					params.put("P_NOMBRE_PROGRAMA", "CUIS");
					params.put("P_PARRAFO1", parrafo1);
					params.put("P_PARRAFO2", parrafo2);
					params.put("P_PARRAFO3", parrafo3);
					params.put("P_DIRECTOR_GRAL", nombreDirectorGeneral);
					//params.put("P_IMG_HEAD", getEncabezado().getStream());
					reporteConfig.setParametros(params);
					plantillaPDF = ReporteUtil.getStreamedContentOfBytes(ReporteUtil.generar(reporteConfig),
							"application/pdf", "Constancia");
					RequestContext.getCurrentInstance().execute("PF('visorPlantilla').show()");
					RequestContext.getCurrentInstance().update("visorPdf");
					RequestContext.getCurrentInstance().scrollTo("visorPdf");
				} catch (Exception e) {
					logger.info("Ocurrio una excepcion al intentar visualizar la plantilla: " + e);
					agregarMsgInfo("Ocurrió un error al intentar visualizar la plantilla.", null);
				}
			} else {
				logger.info("Se debe seleccionar una imagen de fondo.");
				agregarMsgInfo("Debe seleccionar una imagen de fondo", null);
			}

		} else {
			logger.info("La plantilla es nula");
			agregarMsgInfo("Ocurrió un error al intentar visualizar la plantilla.", null);
		}
	}

	private String insertaVariablesEnParrafo(String parrafo, String lugar) {
		String parrafoSalida = parrafo;
		parrafoSalida = parrafoSalida.replace("$_calificacion_", "100");
		parrafoSalida = parrafoSalida.replace("$_modalidad_", "En línea");
		parrafoSalida = parrafoSalida.replace("$_lugar_", lugar);
		parrafoSalida = parrafoSalida.replace("$_fecha_", "14 de junio del 2018");
		parrafoSalida = parrafoSalida.replace("$_num_horas_", "10");
		return parrafoSalida;
	}

	private String obtenerPlantillaJasper(TipoDocumentoEnum tipoDocumento) {
		switch (tipoDocumento) {
		case CONSTANCIA_ACREDITACION:
			return "/resources/jasperReport/gestionAprendizaje/constanciaAcreditacion.jasper";
		case CONSTANCIA_PARTICIPACION:
			return "/resources/jasperReport/gestionAprendizaje/constanciaParticipacion.jasper";
		default:
			return "/resources/jasperReport/PlantillaGeneralSISI.jasper";
		}
	}

	public String guardarPlantilla() {
		String rutaRetorno;
		ResultadoDTO<PlantillaDTO> resultado;
		if (nueva) {
			if (plantillas.isEmpty()) {
				plantilla.setActivo(true);
			}
			resultado = plantillaService.guardar(plantilla);
		} else {
			resultado = plantillaService.actualizar(plantilla);
		}
		if (resultado.esCorrecto()) {
			plantillas = plantillaService.obtenerPlantillasPorTipoDocumento(tipoDocumento);

			if (nueva) {
				bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CRE_PLA_DOC",
						String.valueOf(resultado.getDto().getIdPlantilla()), requestActual(), TipoServicioEnum.LOCAL);
				agregarFlashMessage("Creación de plantilla exitosa.", null, FacesMessage.SEVERITY_INFO);
			} else {
				bitacoraBean.guardarBitacora(idPersonaEnSesion(), "EDI_PLA_DOC",
						String.valueOf(resultado.getDto().getIdPlantilla()), requestActual(), TipoServicioEnum.LOCAL);
				agregarFlashMessage("Actualización de plantilla exitosa", null, FacesMessage.SEVERITY_INFO);
			}

			rutaRetorno = ConstantesGestorWeb.NAVEGA_PLANTILLAS_DOCS;
		} else {
			if (ObjectUtils.isNotNull(resultado.getMensajes())) {
				agregarMsgError(resultado.getMensajes(), null, sistema);
			}
			rutaRetorno = ConstantesGestorWeb.NAVEGA_PLANTILLA_DOCS;
		}
		return rutaRetorno;
	}

	/* INICIO DE GETS Y SETS */
	public PlantillaService getPlantillaService() {
		return plantillaService;
	}

	public List<PlantillaDTO> getPlantillas() {
		return plantillas;
	}

	public PlantillaDTO getPlantilla() {
		return plantilla;
	}

	public PlantillaDTO getPlantillaModificar() {
		return plantillaModificar;
	}

	public Integer getTipoDocumento() {
		return tipoDocumento;
	}

	public void setPlantillaService(PlantillaService plantillaService) {
		this.plantillaService = plantillaService;
	}

	public void setPlantillas(List<PlantillaDTO> plantillas) {
		this.plantillas = plantillas;
	}

	public void setPlantilla(PlantillaDTO plantilla) {
		this.plantilla = plantilla;
	}

	public void setPlantillaModificar(PlantillaDTO plantillaModificar) {
		this.plantillaModificar = plantillaModificar;
	}

	public void setTipoDocumento(Integer tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public ParametroSistemaService getParametroSistemaService() {
		return parametroSistemaService;
	}

	public void setParametroSistemaService(ParametroSistemaService parametroSistemaService) {
		this.parametroSistemaService = parametroSistemaService;
	}

	public StreamedContent getImagenEncabezado() {
		return imagenEncabezado;
	}

	public void setImagenEncabezado(StreamedContent imagenEncabezado) {
		logger.info("___" + imagenEncabezado);
		this.imagenEncabezado = imagenEncabezado;
		this.plantilla.setImagenFondo(imagenEncabezado.getName());
	}

	public StreamedContent getPlantillaPDF() {
		return plantillaPDF;
	}

	public void setPlantillaPDF(StreamedContent plantillaPDF) {
		this.plantillaPDF = plantillaPDF;
	}

	public SistemaBean getSistema() {
		return sistema;
	}

	public void setSistema(SistemaBean sistema) {
		this.sistema = sistema;
	}

	public Long getIdPlantilla() {
		return idPlantilla;
	}

	public void setIdPlantilla(Long idPlantilla) {
		this.idPlantilla = idPlantilla;
	}

	public BitacoraBean getBitacoraBean() {
		return bitacoraBean;
	}

	public void setBitacoraBean(BitacoraBean bitacoraBean) {
		this.bitacoraBean = bitacoraBean;
	}

}
