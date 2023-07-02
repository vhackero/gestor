package mx.gob.sedesol.gestorweb.beans.administracion;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;

@SessionScoped
@ManagedBean
public class CreadorPlantillasBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(CreadorPlantillasBean.class);

	@ManagedProperty(value = "#{parametroSistemaService}")
	private transient ParametroSistemaService parametroSistemaService;

	private StringBuilder rutaImagenes;

	@ManagedProperty("#{bitacoraBean}")
	private BitacoraBean bitacoraBean;

	@PostConstruct
	public void init() {

		logger.info(FacesContext.getCurrentInstance().getExternalContext().getRequestScheme() + "://"
				+ FacesContext.getCurrentInstance().getExternalContext().getRequestServerName() + ":"
				+ FacesContext.getCurrentInstance().getExternalContext().getRequestServerPort() + "/recursos/");

		rutaImagenes = new StringBuilder();
		rutaImagenes.append(
				parametroSistemaService.obtenerParametroConRutaCompleta(ConstantesGestor.PARAMETRO_RUTA_RECURSOS));
		rutaImagenes.append(
				parametroSistemaService.obtenerParametroRuta(ConstantesGestor.PARAMETRO_RUTA_IMAGENES_DOCUMENTOS));
	}

	/**
	 * METODO QUE CARGA Y GUARDA UNA IMAGEN DEL TIPO HEADER O FOOTER
	 */
	public void cargaImagenPlantilla(FileUploadEvent evento) {

		String nombreArchivo = UUID.randomUUID().toString();

		if (ObjectUtils.isNotNull(evento)) {

			String path = rutaImagenes.toString() + nombreArchivo;

			try {
				File archivoRuta = new File(path);
				if (!archivoRuta.exists()) {
					archivoRuta.mkdirs();
				}

				BufferedImage image = ImageIO.read(evento.getFile().getInputstream());
				ImageIO.write(image, "png", new File(path));
				bitacoraBean.guardarBitacora(idPersonaEnSesion(), "SUB_IMG_DOC", "", requestActual(),
						TipoServicioEnum.LOCAL);
				agregarMsgInfo("Se guardó correctamente la imagen.", null);

			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	public List<StreamedContent> cargaImagenesEncabezado() {
		List<StreamedContent> lstEncabezados = new ArrayList<>();
		File dirEncabezados = new File(rutaImagenes.toString());
		if (dirEncabezados.exists() && dirEncabezados.isDirectory()) {
			File[] encabezados = dirEncabezados.listFiles();

			try {
				for (File enc : encabezados) {
					InputStream input = new FileInputStream(enc);
					StreamedContent sc = new DefaultStreamedContent(input,
							getFacesContext().getExternalContext().getMimeType(enc.getName()), enc.getName());
					lstEncabezados.add(sc);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

		return lstEncabezados;
	}

	public ParametroSistemaService getParametroSistemaService() {
		return parametroSistemaService;
	}

	public void setParametroSistemaService(ParametroSistemaService parametroSistemaService) {
		this.parametroSistemaService = parametroSistemaService;
	}

	public BitacoraBean getBitacoraBean() {
		return bitacoraBean;
	}

	public void setBitacoraBean(BitacoraBean bitacoraBean) {
		this.bitacoraBean = bitacoraBean;
	}

}
