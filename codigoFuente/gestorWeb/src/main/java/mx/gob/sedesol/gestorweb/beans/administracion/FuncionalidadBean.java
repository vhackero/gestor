package mx.gob.sedesol.gestorweb.beans.administracion;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import mx.gob.sedesol.basegestor.commons.dto.admin.FuncionalidadDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.TextoSistemaDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.service.admin.FuncionalidadService;
import mx.gob.sedesol.basegestor.service.admin.TextoSistemaService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.utils.BitacoraUtil;
import mx.gob.sedesol.gestorweb.commons.utils.ObjectUtils;
import mx.gob.sedesol.gestorweb.sistema.SistemaBean;

@SessionScoped
@ManagedBean(name = "funcionalidadBean")
public class FuncionalidadBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(FuncionalidadBean.class);

	@ManagedProperty(value = "#{funcionalidadService}")
	private transient FuncionalidadService funcionalidadService;

	@ManagedProperty(value = "#{textoSistemaService}")
	private transient TextoSistemaService textoSistemaService;

	@ManagedProperty(value = "#{sistema}")
	private SistemaBean sistema;

	@ManagedProperty("#{bitacoraBean}")
	private BitacoraBean bitacoraBean;

	private transient TreeNode root;

	private List<FuncionalidadDTO> funcionalidades;

	private FuncionalidadDTO funcionalidad = new FuncionalidadDTO();
	private FuncionalidadDTO funcionalidadModificar;
	private FuncionalidadDTO funcionalidadPadre;

	private String ruta;
	private boolean nueva;

	private List<TextoSistemaDTO> textosSistema;
	private TextoSistemaDTO textoSistema = new TextoSistemaDTO();
	private TextoSistemaDTO textoSistemaModificar;

	private final transient ModelMapper modelMapper = new ModelMapper();

	@PostConstruct
	public void init() {
		funcionalidadPadre = null;
		iniciarArbol();
	}

	private void iniciarArbol() {
		funcionalidades = funcionalidadService.findAll();

		Map<Long, TreeNode> mapaFuncionalidades = new HashMap<>();

		root = new DefaultTreeNode(new FuncionalidadDTO(), null);

		for (FuncionalidadDTO funcion : funcionalidades) {
			TreeNode funcionTree;
			if (ObjectUtils.isNull(funcion.getFuncionalidadPadre())) {
				funcionTree = new DefaultTreeNode(funcion, root);
			} else {
				TreeNode funcionTreePadre = mapaFuncionalidades
						.get(funcion.getFuncionalidadPadre().getIdFuncionalidad());
				funcionTree = new DefaultTreeNode(funcion, funcionTreePadre);
			}
			mapaFuncionalidades.put(funcion.getIdFuncionalidad(), funcionTree);
		}
	}

	public void nuevaFuncionalidad() {
		funcionalidad = new FuncionalidadDTO(getUsuarioEnSession().getIdPersona());
		nueva = true;
		establecerRuta(funcionalidadPadre);
	}

	public void cargaFuncionalidad() {
		funcionalidad = new FuncionalidadDTO();
		modelMapper.map(funcionalidadModificar, funcionalidad);
		funcionalidad.setFechaActualizacion(new Date());
		funcionalidad.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
		nueva = false;
		establecerRuta(funcionalidadModificar.getFuncionalidadPadre());
	}

	private void establecerRuta(FuncionalidadDTO funcionalidadDTO) {
		if (funcionalidadDTO == null) {
			ruta = "";
		} else {
			ruta = obtenerRuta(funcionalidadDTO.getIdFuncionalidad());
		}
	}

	public String obtenerRuta(Long idFuncionalidad) {
		for (FuncionalidadDTO dto : funcionalidades) {
			if (dto.getIdFuncionalidad() == idFuncionalidad) {
				if (ObjectUtils.isNull(dto.getFuncionalidadPadre())) {
					return dto.getDescripcion();
				} else {
					return obtenerRuta(dto.getFuncionalidadPadre().getIdFuncionalidad()) + " / " + dto.getDescripcion();
				}
			}
		}
		return "";
	}

	public String guardarFuncionalidad() {
		ResultadoDTO<FuncionalidadDTO> resultado;
		if (nueva) {
			funcionalidad.setFuncionalidadPadre(funcionalidadPadre);
			resultado = funcionalidadService.guardar(funcionalidad);
		} else {
			resultado = funcionalidadService.actualizar(funcionalidad);
		}
		if (resultado.getResultado() == ResultadoTransaccionEnum.EXITOSO) {
			if (nueva) {
				bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CRE_FUN",
						String.valueOf(resultado.getDto().getIdFuncionalidad()), requestActual(),
						TipoServicioEnum.LOCAL);
			} else {
				bitacoraBean.guardarBitacora(idPersonaEnSesion(), "EDI_FUN",
						String.valueOf(resultado.getDto().getIdFuncionalidad()), requestActual(),
						TipoServicioEnum.LOCAL);
			}

			init();
			RequestContext.getCurrentInstance().execute("PF('amodal').hide();");
			agregarMsgInfo(resultado.getMensajes().get(0), null, sistema);
		} else {
			agregarMsgError(resultado.getMensajes(), null, sistema);
		}
		return "";
	}

	public String cargarTextosSistema() {
		textosSistema = textoSistemaService.obtenerTextosPorFuncionalidad(funcionalidadModificar.getIdFuncionalidad());
		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "VER_TEX_SIS_FUN", "", requestActual(),
				TipoServicioEnum.LOCAL);

		return ConstantesGestorWeb.NAVEGA_TEXTOS_SISTEMA;
	}

	public void nuevoTextoSistema() {
		textoSistema = new TextoSistemaDTO(getUsuarioEnSession().getIdPersona());
		textoSistema.setFuncionalidad(funcionalidadModificar);

		nueva = true;
	}

	public void cargarTextoSistema() {
		textoSistema = new TextoSistemaDTO();
		modelMapper.map(textoSistemaModificar, textoSistema);
		textoSistema.setFechaActualizacion(new Date());
		textoSistema.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
		textoSistema.setFuncionalidad(funcionalidadModificar);

		nueva = false;
	}

	public void guardarTextoSistema() {
		ResultadoDTO<TextoSistemaDTO> resultado;
		if (nueva) {
			resultado = textoSistemaService.guardar(textoSistema);
		} else {
			resultado = textoSistemaService.actualizar(textoSistema);
		}
		if (resultado.getResultado() == ResultadoTransaccionEnum.EXITOSO) {
			if (nueva) {
				bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CRE_TEX_SIS_FUN", "", requestActual(),
						TipoServicioEnum.LOCAL);
			} else {
				bitacoraBean.guardarBitacora(idPersonaEnSesion(), "EDI_TEX_SIS_FUN", "", requestActual(),
						TipoServicioEnum.LOCAL);
			}
			sistema.establecerTexto(textoSistema.getClave(), textoSistema.getValor());
			cargarListaDeTextos();
			RequestContext.getCurrentInstance().execute("PF('amodal').hide();");
			agregarMsgInfo(resultado.getMensajes().get(0), null, sistema);
		} else {
			agregarMsgError(resultado.getMensajes(), null, sistema);
		}
	}

	public void cargarListaDeTextos() {
		textosSistema = textoSistemaService.obtenerTextosPorFuncionalidad(funcionalidadModificar.getIdFuncionalidad());
	}

	/* INICIO DE GETS Y SETS */
	public FuncionalidadService getFuncionalidadService() {
		return funcionalidadService;
	}

	public void setFuncionalidadService(FuncionalidadService funcionalidadService) {
		this.funcionalidadService = funcionalidadService;
	}

	public List<FuncionalidadDTO> getFuncionalidades() {
		return funcionalidades;
	}

	public void setFuncionalidades(List<FuncionalidadDTO> funcionalidades) {
		this.funcionalidades = funcionalidades;
	}

	public FuncionalidadDTO getFuncionalidad() {
		return funcionalidad;
	}

	public void setFuncionalidad(FuncionalidadDTO funcionalidad) {
		this.funcionalidad = funcionalidad;
	}

	public FuncionalidadDTO getFuncionalidadPadre() {
		return funcionalidadPadre;
	}

	public void setFuncionalidadPadre(FuncionalidadDTO funcionalidadPadre) {
		this.funcionalidadPadre = funcionalidadPadre;
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public FuncionalidadDTO getFuncionalidadModificar() {
		return funcionalidadModificar;
	}

	public void setFuncionalidadModificar(FuncionalidadDTO funcionalidadModificar) {
		this.funcionalidadModificar = funcionalidadModificar;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
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

	public TextoSistemaService getTextoSistemaService() {
		return textoSistemaService;
	}

	public void setTextoSistemaService(TextoSistemaService textoSistemaService) {
		this.textoSistemaService = textoSistemaService;
	}

	public boolean isNueva() {
		return nueva;
	}

	public void setNueva(boolean nueva) {
		this.nueva = nueva;
	}

	public SistemaBean getSistema() {
		return sistema;
	}

	public void setSistema(SistemaBean sistema) {
		this.sistema = sistema;
	}

	public BitacoraBean getBitacoraBean() {
		return bitacoraBean;
	}

	public void setBitacoraBean(BitacoraBean bitacoraBean) {
		this.bitacoraBean = bitacoraBean;
	}
}
