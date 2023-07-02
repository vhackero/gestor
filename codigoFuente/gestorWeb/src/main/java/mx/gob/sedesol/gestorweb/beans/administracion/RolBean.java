package mx.gob.sedesol.gestorweb.beans.administracion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;

import org.modelmapper.ModelMapper;
import org.primefaces.context.RequestContext;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.TreeNode;

import mx.gob.sedesol.basegestor.commons.dto.admin.BitacoraDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.RolDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.RolFuncionalidadDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.mongo.service.BitacoraService;
import mx.gob.sedesol.basegestor.service.admin.PersonaService;
import mx.gob.sedesol.basegestor.service.admin.RolFuncionalidadService;
import mx.gob.sedesol.basegestor.service.admin.RoleService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.utils.BitacoraUtil;
import mx.gob.sedesol.gestorweb.sistema.SistemaBean;

@SessionScoped
@ManagedBean(name = "rolBean")
public class RolBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{roleService}")
	private transient RoleService roleService;

	@ManagedProperty(value = "#{rolFuncionalidadService}")
	private transient RolFuncionalidadService rolFuncionalidadService;

	@ManagedProperty(value = "#{sistema}")
	private SistemaBean sistema;

	@ManagedProperty("#{personaService}")
	private PersonaService personaService;

	@ManagedProperty("#{bitacoraService}")
	private BitacoraService bitacoraService;

	@ManagedProperty("#{bitacoraBean}")
	private BitacoraBean bitacoraBean;

	private transient ModelMapper modelMapper = new ModelMapper();

	private List<RolDTO> roles;
	private RolDTO rol = new RolDTO();
	private RolDTO rolModificar;

	private boolean nuevo;

	private transient TreeNode permisos;
	private transient TreeNode[] permisosSeleccionados;

	public String init() {
		roles = roleService.findAll();

		if (!roles.isEmpty()) {
			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CON_ROL", "", requestActual(), TipoServicioEnum.LOCAL);
		}

		return ConstantesGestorWeb.NAVEGA_ROLES;
	}

	public void nuevoRol() {
		rol = new RolDTO(getUsuarioEnSession().getIdPersona());
		nuevo = true;
	}

	public void cargarRol() {
		rol = new RolDTO();
		modelMapper.map(rolModificar, rol);

		rol.setUsuarioModifica(getUsuarioEnSession().getIdPersona());
		rol.setFechaActualizacion(new Date());

		nuevo = false;
	}

	public void guardarRol() {
		if (nuevo) {
			almacenarRol();
		} else {
			actualizarRol();
		}
	}

	private void almacenarRol() {

		ResultadoDTO<RolDTO> resultado = roleService.guardar(rol);
		if (resultado.getResultado() == ResultadoTransaccionEnum.EXITOSO) {

			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CRE_ROL", String.valueOf(resultado.getDto().getIdRol()),
					requestActual(), TipoServicioEnum.LOCAL);

			roles.add(0, resultado.getDto());
			RequestContext.getCurrentInstance().execute("PF('mRol').hide();");
			if (ObjectUtils.isNotNull(resultado.getMensajes())) {
				agregarMsgInfo(resultado.getMensajes().get(0), null, sistema);
			}
		} else {
			if (ObjectUtils.isNotNull(resultado.getMensajes())) {
				agregarMsgError(resultado.getMensajes(), null, sistema);
			}
		}
	}

	private void actualizarRol() {

		ResultadoDTO<RolDTO> resultado = roleService.actualizar(rol);
		if (resultado.getResultado() == ResultadoTransaccionEnum.EXITOSO) {

			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "EDI_ROL", String.valueOf(resultado.getDto().getIdRol()),
					requestActual(), TipoServicioEnum.LOCAL);

			roles.remove(rolModificar);
			roles.add(0, resultado.getDto());

			RequestContext.getCurrentInstance().execute("PF('mRol').hide();");
			if (ObjectUtils.isNotNull(resultado.getMensajes())) {
				agregarMsgInfo(resultado.getMensajes().get(0), null, sistema);
			}
		} else {
			if (ObjectUtils.isNotNull(resultado.getMensajes())) {
				agregarMsgError(resultado.getMensajes(), null, sistema);
			}
		}
	}

	public String cargarPermisos() {
		List<RolFuncionalidadDTO> funcionalidades = rolFuncionalidadService
				.obtenerArbolFuncionalidadesRol(rolModificar.getIdRol());
		permisos = new CheckboxTreeNode(new RolFuncionalidadDTO(), null);

		agregarPermisos(permisos, funcionalidades);

		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "VER_PER_ROL", "", requestActual(), TipoServicioEnum.LOCAL);

		return ConstantesGestorWeb.NAVEGA_ROLES_PERMISOS;
	}

	public String guardarPermisos() {
		List<RolFuncionalidadDTO> funcionalidades = new ArrayList<>();
		recorrerArbol(permisos.getChildren(), funcionalidades);

		rolFuncionalidadService.guardarArbolFuncionalidadesRol(funcionalidades, rolModificar.getIdRol(),
				getUsuarioEnSession().getIdPersona());

		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "EDI_PER_ROL", String.valueOf(rolModificar.getIdRol()),
				requestActual(), TipoServicioEnum.LOCAL);

		agregarFlashMessage("Permisos guardados correctamente.", null, FacesMessage.SEVERITY_INFO);
		return init();
	}

	private void recorrerArbol(List<TreeNode> permisos, List<RolFuncionalidadDTO> funcionalidades) {
		for (TreeNode nodo : permisos) {
			RolFuncionalidadDTO funcionalidad = (RolFuncionalidadDTO) nodo.getData();
			funcionalidad.setSeleccionado(nodo.isSelected());
			funcionalidades.add(funcionalidad);
			recorrerNodo(nodo, funcionalidad);
		}
	}

	private void recorrerNodo(TreeNode padre, RolFuncionalidadDTO funcionalidadPadre) {
		funcionalidadPadre.setFuncionalidades(new ArrayList<RolFuncionalidadDTO>());
		for (TreeNode nodo : padre.getChildren()) {
			RolFuncionalidadDTO funcionalidad = (RolFuncionalidadDTO) nodo.getData();
			funcionalidad.setSeleccionado(nodo.isSelected());
			funcionalidadPadre.getFuncionalidades().add(funcionalidad);
			recorrerNodo(nodo, funcionalidad);
		}
	}

	private void agregarPermisos(TreeNode arbol, List<RolFuncionalidadDTO> funcionalidades) {
		for (RolFuncionalidadDTO dto : funcionalidades) {
			TreeNode nodo = new CheckboxTreeNode(dto, arbol);
			nodo.setSelected(dto.isSeleccionado());
			if (!dto.getFuncionalidades().isEmpty()) {
				agregarPermisos(nodo, dto.getFuncionalidades());
			}
		}
	}

	/* INICIO DE GETS Y SETS */
	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public List<RolDTO> getRoles() {
		return roles;
	}

	public void setRoles(List<RolDTO> roles) {
		this.roles = roles;
	}

	public RolDTO getRol() {
		return rol;
	}

	public void setRol(RolDTO rol) {
		this.rol = rol;
	}

	public RolDTO getRolModificar() {
		return rolModificar;
	}

	public void setRolModificar(RolDTO rolModificar) {
		this.rolModificar = rolModificar;
	}

	public boolean isNuevo() {
		return nuevo;
	}

	public void setNuevo(boolean nuevo) {
		this.nuevo = nuevo;
	}

	public RolFuncionalidadService getRolFuncionalidadService() {
		return rolFuncionalidadService;
	}

	public void setRolFuncionalidadService(RolFuncionalidadService rolFuncionalidadService) {
		this.rolFuncionalidadService = rolFuncionalidadService;
	}

	public TreeNode getPermisos() {
		return permisos;
	}

	public void setPermisos(TreeNode permisos) {
		this.permisos = permisos;
	}

	public TreeNode[] getPermisosSeleccionados() {
		return permisosSeleccionados;
	}

	public void setPermisosSeleccionados(TreeNode[] permisosSeleccionados) {
		this.permisosSeleccionados = permisosSeleccionados;
	}

	public SistemaBean getSistema() {
		return sistema;
	}

	public void setSistema(SistemaBean sistema) {
		this.sistema = sistema;
	}

	public PersonaService getPersonaService() {
		return personaService;
	}

	public void setPersonaService(PersonaService personaService) {
		this.personaService = personaService;
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
