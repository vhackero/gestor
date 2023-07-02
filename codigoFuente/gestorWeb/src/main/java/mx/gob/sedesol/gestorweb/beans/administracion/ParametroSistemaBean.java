package mx.gob.sedesol.gestorweb.beans.administracion;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;

import org.modelmapper.ModelMapper;
import org.primefaces.context.RequestContext;

import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroSistemaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.utils.BitacoraUtil;
import mx.gob.sedesol.gestorweb.sistema.SistemaBean;

@SessionScoped
@ManagedBean(name = "parametroSistemaBean")
public class ParametroSistemaBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{parametroSistemaService}")
	private transient ParametroSistemaService parametroSistemaService;

	@ManagedProperty(value = "#{sistema}")
	private SistemaBean sistema;

	@ManagedProperty("#{bitacoraBean}")
	private BitacoraBean bitacoraBean;

	private List<ParametroSistemaDTO> parametros;
	private ParametroSistemaDTO parametro = new ParametroSistemaDTO();
	private ParametroSistemaDTO parametroModificar;

	private boolean nuevo;

	private transient ModelMapper modelMapper = new ModelMapper();

	@PostConstruct
	public void init() {
		parametros = parametroSistemaService.findAll();
	}

	public void nuevoParametro() {
		parametro = new ParametroSistemaDTO(getUsuarioEnSession().getIdPersona());
		setNuevo(true);
	}

	public void cargarParametro() {
		parametro = new ParametroSistemaDTO();
		modelMapper.map(parametroModificar, parametro);
		parametro.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
		parametro.setFechaActualizacion(new Date());

		setNuevo(false);
	}

	public boolean buscarClave(ParametroSistemaDTO p1, List<ParametroSistemaDTO> parametros) {
		for (ParametroSistemaDTO parametroDTO : parametros) {
			if (parametroDTO.getClave().equals(p1.getClave())) {
				return false;
			}
		}
		return true;
	}

	public void guardarParametro() {
		ResultadoDTO<ParametroSistemaDTO> resultado;
		if (isNuevo()) {
			if (buscarClave(parametro, parametros)) {
				resultado = parametroSistemaService.guardar(parametro);
				if (resultado.getResultado() == ResultadoTransaccionEnum.EXITOSO) {
					init();
					bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CRE_PAR_SIS", "", requestActual(),
							TipoServicioEnum.LOCAL);

					agregarMsgInfo(resultado.getMensajes().get(0), null, sistema);
					RequestContext.getCurrentInstance().execute("PF('amodal').hide()");
				} else {
					agregarMsgError(resultado.getMensajes(), null, sistema);
				}
			} else {
				agregarMsgInfo("La clave del parámetro ya existe", null, sistema);
			}

		} else {
			resultado = parametroSistemaService.actualizar(parametro);
			if (resultado.getResultado() == ResultadoTransaccionEnum.EXITOSO) {
				init();
				bitacoraBean.guardarBitacora(idPersonaEnSesion(), "EDI_PAR_SIS", "", requestActual(),
						TipoServicioEnum.LOCAL);
				agregarMsgInfo(resultado.getMensajes().get(0), null, sistema);
				RequestContext.getCurrentInstance().execute("PF('amodal').hide()");
			} else {
				agregarMsgError(resultado.getMensajes(), null, sistema);
			}
		}

	}

	/* INICIO DE GETS Y SETS */
	public ParametroSistemaService getParametroSistemaService() {
		return parametroSistemaService;
	}

	public void setParametroSistemaService(ParametroSistemaService parametroSistemaService) {
		this.parametroSistemaService = parametroSistemaService;
	}

	public SistemaBean getSistema() {
		return sistema;
	}

	public void setSistema(SistemaBean sistema) {
		this.sistema = sistema;
	}

	public List<ParametroSistemaDTO> getParametros() {
		return parametros;
	}

	public void setParametros(List<ParametroSistemaDTO> parametros) {
		this.parametros = parametros;
	}

	public ParametroSistemaDTO getParametro() {
		return parametro;
	}

	public void setParametro(ParametroSistemaDTO parametro) {
		this.parametro = parametro;
	}

	public ParametroSistemaDTO getParametroModificar() {
		return parametroModificar;
	}

	public void setParametroModificar(ParametroSistemaDTO parametroModificar) {
		this.parametroModificar = parametroModificar;
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
