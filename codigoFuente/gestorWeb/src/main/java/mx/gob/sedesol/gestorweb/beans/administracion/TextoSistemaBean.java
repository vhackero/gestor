package mx.gob.sedesol.gestorweb.beans.administracion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.primefaces.context.RequestContext;

import mx.gob.sedesol.basegestor.commons.dto.admin.FuncionalidadDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.TextoSistemaDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.service.admin.FuncionalidadService;
import mx.gob.sedesol.basegestor.service.admin.TextoSistemaService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.sistema.SistemaBean;

@SessionScoped
@ManagedBean(name = "textoSistemaBean")
public class TextoSistemaBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(TextoSistemaBean.class);

	@ManagedProperty(value = "#{funcionalidadService}")
	private transient FuncionalidadService funcionalidadService;

	@ManagedProperty(value = "#{textoSistemaService}")
	private transient TextoSistemaService textoSistemaService;

	@ManagedProperty(name = "sistema", value = "#{sistema}")
	private SistemaBean sistema;

	@ManagedProperty("#{bitacoraBean}")
	private BitacoraBean bitacoraBean;

	private List<FuncionalidadDTO> modulos;
	private List<FuncionalidadDTO> componentes;

	private Long idModulo = 0L;
	private Long idComponente;

	private String valor;

	private List<TextoSistemaDTO> textosSistema;
	private TextoSistemaDTO textoSistema = new TextoSistemaDTO();
	private TextoSistemaDTO textoSistemaModificar;

	private transient ModelMapper modelMapper = new ModelMapper();

	@PostConstruct
	public void init() {
		modulos = funcionalidadService.obtenerModulos();
		componentes = new ArrayList<>();
	}

	public void cargarComponentes() {
		componentes = funcionalidadService.obtenerComponentes(idModulo);
		idComponente = 0L;
	}

	public void buscar() {
		TextoSistemaDTO criterios = new TextoSistemaDTO();
		criterios.setValor(valor);
		if (ObjectUtils.isNullOrCero(idComponente)) {
			// criterios.getBitacoraDTO().setFuncion(null);
		} else {
			criterios.setFuncionalidad(new FuncionalidadDTO());
			criterios.getFuncionalidad().setIdFuncionalidad(idComponente);
		}
		textosSistema = textoSistemaService.buscarPorCriterios(criterios);
		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "BUS_TEX_SIS", "", requestActual(), TipoServicioEnum.LOCAL);
	}

	public void cargarTextoSistema() {
		textoSistema = new TextoSistemaDTO();
		modelMapper.map(textoSistemaModificar, textoSistema);
		textoSistema.setFechaActualizacion(new Date());
		textoSistema.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
	}

	public void guardarTextoSistema() {
		ResultadoDTO<TextoSistemaDTO> resultado = textoSistemaService.actualizar(textoSistema);
		if (resultado.getResultado() == ResultadoTransaccionEnum.EXITOSO) {
			sistema.establecerTexto(textoSistema.getClave(), textoSistema.getValor());
			buscar();
			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "EDI_TEX_SIS", "", requestActual(),
					TipoServicioEnum.LOCAL);
			RequestContext.getCurrentInstance().execute("PF('amodal').hide();");
			agregarMsgInfo(resultado.getMensajes().get(0), null, sistema);
		} else {
			agregarMsgError(resultado.getMensajes(), null, sistema);
		}
	}

	public FuncionalidadService getFuncionalidadService() {
		return funcionalidadService;
	}

	public void setFuncionalidadService(FuncionalidadService funcionalidadService) {
		this.funcionalidadService = funcionalidadService;
	}

	public TextoSistemaService getTextoSistemaService() {
		return textoSistemaService;
	}

	public void setTextoSistemaService(TextoSistemaService textoSistemaService) {
		this.textoSistemaService = textoSistemaService;
	}

	public SistemaBean getSistema() {
		return sistema;
	}

	public void setSistema(SistemaBean sistema) {
		this.sistema = sistema;
	}

	public List<FuncionalidadDTO> getComponentes() {
		return componentes;
	}

	public void setComponentes(List<FuncionalidadDTO> componentes) {
		this.componentes = componentes;
	}

	public List<TextoSistemaDTO> getTextosSistema() {
		return textosSistema;
	}

	public void setTextosSistema(List<TextoSistemaDTO> textosSistema) {
		this.textosSistema = textosSistema;
	}

	public TextoSistemaDTO getTextoSistema() {
		return textoSistema;
	}

	public void setTextoSistema(TextoSistemaDTO textoSistema) {
		this.textoSistema = textoSistema;
	}

	public TextoSistemaDTO getTextoSistemaModificar() {
		return textoSistemaModificar;
	}

	public void setTextoSistemaModificar(TextoSistemaDTO textoSistemaModificar) {
		this.textoSistemaModificar = textoSistemaModificar;
	}

	public List<FuncionalidadDTO> getModulos() {
		return modulos;
	}

	public void setModulos(List<FuncionalidadDTO> modulos) {
		this.modulos = modulos;
	}

	public Long getIdModulo() {
		return idModulo;
	}

	public void setIdModulo(Long idModulo) {
		this.idModulo = idModulo;
	}

	public Long getIdComponente() {
		return idComponente;
	}

	public void setIdComponente(Long idComponente) {
		this.idComponente = idComponente;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public BitacoraBean getBitacoraBean() {
		return bitacoraBean;
	}

	public void setBitacoraBean(BitacoraBean bitacoraBean) {
		this.bitacoraBean = bitacoraBean;
	}
}
