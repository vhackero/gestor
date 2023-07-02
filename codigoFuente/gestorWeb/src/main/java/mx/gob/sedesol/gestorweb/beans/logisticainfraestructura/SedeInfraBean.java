package mx.gob.sedesol.gestorweb.beans.logisticainfraestructura;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.DualListModel;
import org.primefaces.model.TreeNode;

import mx.gob.sedesol.basegestor.commons.dto.NodoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.OrgGubernamentalDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.AreaSedeDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.SedeDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.CatAreaInfraestructura;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.CatUbicacionTerritorial;
import mx.gob.sedesol.basegestor.service.admin.OrgGubernamentalService;
import mx.gob.sedesol.basegestor.service.impl.logisticainfraestructura.LogisticaInfraServiceFacade;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.beans.administracion.BitacoraBean;

@ViewScoped
@ManagedBean
public class SedeInfraBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(SedeInfraBean.class);

	private SedeDTO sede;
	private transient TreeNode arbolOrgGubernamental;
	private transient TreeNode arbolOrgGubernamental2;
	private transient TreeNode nodoOrgGubernamentalSel;
	private transient TreeNode nodoOrgGubernamentalSelEdit;
	private List<CatalogoComunDTO> catUbicacionTerr;
	private List<SedeDTO> sedesRegistradas;

	@ManagedProperty(value = "#{logisticaInfraServiceFacade}")
	private transient LogisticaInfraServiceFacade logisticaInfraServiceFacade;

	@ManagedProperty("#{bitacoraBean}")
	private BitacoraBean bitacoraBean;

	@ManagedProperty("#{orgGubernamentalService}")
	private OrgGubernamentalService orgGubernamentalService;

	private SedeDTO sedeSeleccionada;
	private SedeDTO sedeEditar;

	private DualListModel<CatalogoComunDTO> listaAreas;
	private DualListModel<CatalogoComunDTO> listaAreasAEliminar;
	private boolean editarSede;
	private boolean hayTransferencia;

	private boolean mostrarTree = false;

	boolean skipRecursionOrgGub = false;

	@PostConstruct
	public void inicializador() {
		sede = new SedeDTO();
		sede.setCatUbicacionTerritorial(new CatalogoComunDTO());
		sedeSeleccionada = new SedeDTO();
		sedeSeleccionada.setCatUbicacionTerritorial(new CatalogoComunDTO());

		this.generaArbolOrgGubernamental();
		listaAreas = new DualListModel<>();
		listaAreasAEliminar = new DualListModel<CatalogoComunDTO>();
		sedeEditar = new SedeDTO();
		sedeEditar.setCatUbicacionTerritorial(new CatalogoComunDTO());
		catUbicacionTerr = logisticaInfraServiceFacade.getCatUbicacionTerrService()
				.findAll(CatUbicacionTerritorial.class);
		sedesRegistradas = logisticaInfraServiceFacade.getSedeService().findAll();
		setEditarSede(Boolean.FALSE);

		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CON_CAT_CON_SED", "", requestActual(),
				TipoServicioEnum.LOCAL);

	}

	public void generaArbolOrgGubernamental() {
		List<OrgGubernamentalDTO> orgGubs = orgGubernamentalService.obtenerOrgGubsPadres();
		arbolOrgGubernamental = new DefaultTreeNode();
		this.generaHijosOrgGubAreas(arbolOrgGubernamental, orgGubs);
	}

	public void onNodeSelectDependencia(NodeSelectEvent event) {
		NodoDTO n = (NodoDTO) event.getTreeNode().getData();
		sede.setOrganismoGubernamental(
				logisticaInfraServiceFacade.getOrgGubernamentalService().obtenerOrgGubernamentalPorId(n.getId()));

	}

	public void onNodeSelectDependenciaEdit(NodeSelectEvent event) {
		NodoDTO n = (NodoDTO) event.getTreeNode().getData();
		sedeEditar.setOrganismoGubernamental(
				logisticaInfraServiceFacade.getOrgGubernamentalService().obtenerOrgGubernamentalPorId(n.getId()));

	}

	public void handleTransfer() {
		if (listaAreas.getTarget().isEmpty()) {
			hayTransferencia = false;
		} else {
			hayTransferencia = true;
		}
	}

	public void handleTransferDel() {
		if (listaAreasAEliminar.getTarget().isEmpty()) {
			hayTransferencia = false;
		} else {
			hayTransferencia = true;
		}
	}

	/**
	 *
	 */
	public void guardarSede() {
		if (ObjectUtils.isNotNull(sede)) {
			ResultadoDTO<SedeDTO> resDto;
			sede.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
			sede.setFechaRegistro(new Date());
			resDto = logisticaInfraServiceFacade.getSedeService().guardar(sede);
			if (ObjectUtils.isNotNull(resDto) && resDto.getResultado().getValor()) {
				sedesRegistradas = logisticaInfraServiceFacade.getSedeService().findAll();
				bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CRE_SED",
						String.valueOf(resDto.getDto().getIdSede()), requestActual(), TipoServicioEnum.LOCAL);
				agregarMsgInfo("Se guardo correctamente la informacion", null);
			} else {
				agregarMsgError("Ocurrio un error al intentar guardar los datos", null);
			}
		} else {
			logger.info("Sede viene nulo");
		}
	}

	public void verSede() {
		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "VER_SED",
				String.valueOf(sede.getIdSede()), requestActual(), TipoServicioEnum.LOCAL);
		RequestContext.getCurrentInstance().execute("PF('mdlEditarSede').show()");
	}

	/**
	 *
	 */
	public void consultarAreas() {
		if (ObjectUtils.isNotNull(sedeSeleccionada)) {

			List<CatalogoComunDTO> areasAsigSede = new ArrayList<>();

			List<AreaSedeDTO> areasSede = logisticaInfraServiceFacade.getAreaSedeService()
					.consultaAreasSedePorIdSede(sedeSeleccionada.getIdSede());

			List<CatalogoComunDTO> areas = logisticaInfraServiceFacade.getCatAreaInfraService()
					.findAll(CatAreaInfraestructura.class);

			if (!ObjectUtils.isNullOrEmpty(areasSede)) {
				for (AreaSedeDTO as : areasSede) {
					areasAsigSede.add(as.getCatArea());
					areas.removeIf(a -> a.getId().equals(as.getCatArea().getId()));
				}
			}
			listaAreas = new DualListModel<CatalogoComunDTO>(areas, new ArrayList<CatalogoComunDTO>());
			listaAreasAEliminar = new DualListModel<CatalogoComunDTO>(areasAsigSede, new ArrayList<CatalogoComunDTO>());
			handleTransfer();
		}
	}

	/**
	 *
	 */
	public void guardaAreasSede() {
		if (ObjectUtils.isNotNull(sedeSeleccionada)) {
			if (logisticaInfraServiceFacade.asignaAreasASede(sedeSeleccionada, listaAreas.getTarget(),
					getUsuarioEnSession().getIdPersona())) {
				bitacoraBean.guardarBitacora(idPersonaEnSesion(), "ASI_ARE_SED", "",
						requestActual(), TipoServicioEnum.LOCAL);
				agregarMsgInfo("Se guardaron correctamente los datos", null);
			} else {
				agregarMsgError("Ocurrio un error", null);
			}
		}
	}

	/**
	 *
	 */
	public void eliminaAreasSede() {
		if (ObjectUtils.isNotNull(sedeSeleccionada)) {
			if (logisticaInfraServiceFacade.eliminacionLogicaAreasASede(sedeSeleccionada,
					listaAreasAEliminar.getTarget(), getUsuarioEnSession().getIdPersona())) {

				bitacoraBean.guardarBitacora(idPersonaEnSesion(), "ELI_ARE_SED", "",
						requestActual(), TipoServicioEnum.LOCAL);
				agregarMsgInfo("Se guardaron correctamente los datos", null);
			} else {
				agregarMsgError("Ocurrio un error", null);
			}
		}
	}

	public void reiniciarObjetoEditarSede() {
		sedeEditar = new SedeDTO();
		sedeEditar.setCatUbicacionTerritorial(new CatalogoComunDTO());
	}

	public void cerrarTreeTable() {
		mostrarTree = false;
		logger.info("valor de mostrarTree: " + mostrarTree);
		reiniciarObjetoEditarSede();
	}

	public void mostrarTreeTable() {
		if (mostrarTree == true) {
			mostrarTree = false;
		} else {
			mostrarTree = true;
			cargarDependencia();
		}
	}

	public void cargarDependencia() {
		nodoOrgGubernamentalSel = null;
		nodoOrgGubernamentalSelEdit = null;
		skipRecursionOrgGub = false;
		List<OrgGubernamentalDTO> orgGubs = orgGubernamentalService.obtenerOrgGubsPadres();
		arbolOrgGubernamental2 = new DefaultTreeNode();
		this.generaHijosOrgGubAreas(arbolOrgGubernamental2, orgGubs);
		obtieneValorOrgGubSel(arbolOrgGubernamental2.getChildren(), sedeEditar.getOrganismoGubernamental().getId());
	}

	public void guardarSedeAlEditar() {
		if (ObjectUtils.isNotNull(sedeEditar)) {
			ResultadoDTO<SedeDTO> resDto;
			sedeEditar.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
			sedeEditar.setFechaActualizacion(new Date());
			resDto = logisticaInfraServiceFacade.getSedeService().actualizar(sedeEditar);
			if (ObjectUtils.isNotNull(resDto) && resDto.getResultado().getValor()) {
				bitacoraBean.guardarBitacora(idPersonaEnSesion(), "EDI_SED",
						String.valueOf(resDto.getDto().getIdSede()), requestActual(), TipoServicioEnum.LOCAL);
				sedesRegistradas = logisticaInfraServiceFacade.getSedeService().findAll();
				agregarMsgInfo("Se guardó correctamente la información", null);

			} else {
				agregarMsgError("Ocurrió un error al intentar guardar los datos", null);
			}
		} else {
			logger.info("SedeEditar viene nulo");
		}
		RequestContext.getCurrentInstance().execute("PF('mdlEditarSede').hide();");
		cerrarTreeTable();
	}

	/**
	 *
	 * @param arbol
	 * @param idData
	 * @return
	 */
	private void obtieneValorOrgGubSel(List<TreeNode> arbol, Integer idData) {

		for (TreeNode node : arbol) {

			if (skipRecursionOrgGub) {
				break;
			}

			NodoDTO n = (NodoDTO) node.getData();
			if (n.getId().equals(idData)) {
				nodoOrgGubernamentalSelEdit = node;
				skipRecursionOrgGub = true;
				break;
			}
			this.obtieneValorOrgGubSel(node.getChildren(), idData);
		}
	}

	/**
	 * @return the sede
	 */
	public SedeDTO getSede() {
		return sede;
	}

	/**
	 * @param sede
	 *            the sede to set
	 */
	public void setSede(SedeDTO sede) {
		this.sede = sede;
	}

	/**
	 * @return the arbolOrgGubernamental
	 */
	public TreeNode getArbolOrgGubernamental() {
		return arbolOrgGubernamental;
	}

	/**
	 * @param arbolOrgGubernamental
	 *            the arbolOrgGubernamental to set
	 */
	public void setArbolOrgGubernamental(TreeNode arbolOrgGubernamental) {
		this.arbolOrgGubernamental = arbolOrgGubernamental;
	}

	/**
	 * @return the nodoOrgGubernamentalSel
	 */
	public TreeNode getNodoOrgGubernamentalSel() {
		return nodoOrgGubernamentalSel;
	}

	/**
	 * @param nodoOrgGubernamentalSel
	 *            the nodoOrgGubernamentalSel to set
	 */
	public void setNodoOrgGubernamentalSel(TreeNode nodoOrgGubernamentalSel) {
		this.nodoOrgGubernamentalSel = nodoOrgGubernamentalSel;
	}

	/**
	 * @return the catUbicacionTerr
	 */
	public List<CatalogoComunDTO> getCatUbicacionTerr() {
		return catUbicacionTerr;
	}

	/**
	 * @param catUbicacionTerr
	 *            the catUbicacionTerr to set
	 */
	public void setCatUbicacionTerr(List<CatalogoComunDTO> catUbicacionTerr) {
		this.catUbicacionTerr = catUbicacionTerr;
	}

	/**
	 * @return the logisticaInfraServiceFacade
	 */
	public LogisticaInfraServiceFacade getLogisticaInfraServiceFacade() {
		return logisticaInfraServiceFacade;
	}

	/**
	 * @param logisticaInfraServiceFacade
	 *            the logisticaInfraServiceFacade to set
	 */
	public void setLogisticaInfraServiceFacade(LogisticaInfraServiceFacade logisticaInfraServiceFacade) {
		this.logisticaInfraServiceFacade = logisticaInfraServiceFacade;
	}

	/**
	 * @return the sedesRegistradas
	 */
	public List<SedeDTO> getSedesRegistradas() {
		return sedesRegistradas;
	}

	/**
	 * @param sedesRegistradas
	 *            the sedesRegistradas to set
	 */
	public void setSedesRegistradas(List<SedeDTO> sedesRegistradas) {
		this.sedesRegistradas = sedesRegistradas;
	}

	/**
	 * @return the listaAreas
	 */
	public DualListModel<CatalogoComunDTO> getListaAreas() {
		return listaAreas;
	}

	/**
	 * @param listaAreas
	 *            the listaAreas to set
	 */
	public void setListaAreas(DualListModel<CatalogoComunDTO> listaAreas) {
		this.listaAreas = listaAreas;
	}

	/**
	 * @return the sedeSeleccionada
	 */
	public SedeDTO getSedeSeleccionada() {
		return sedeSeleccionada;
	}

	/**
	 * @param sedeSeleccionada
	 *            the sedeSeleccionada to set
	 */
	public void setSedeSeleccionada(SedeDTO sedeSeleccionada) {
		this.sedeSeleccionada = sedeSeleccionada;
	}

	/**
	 * @return the editarSede
	 */
	public boolean isEditarSede() {
		return editarSede;
	}

	/**
	 * @param editarSede
	 *            the editarSede to set
	 */
	public void setEditarSede(boolean editarSede) {
		this.editarSede = editarSede;
	}

	/**
	 * @return the listaAreasAEliminar
	 */
	public DualListModel<CatalogoComunDTO> getListaAreasAEliminar() {
		return listaAreasAEliminar;
	}

	/**
	 * @param listaAreasAEliminar
	 *            the listaAreasAEliminar to set
	 */
	public void setListaAreasAEliminar(DualListModel<CatalogoComunDTO> listaAreasAEliminar) {
		this.listaAreasAEliminar = listaAreasAEliminar;
	}

	public boolean isHayTransferencia() {
		return hayTransferencia;
	}

	public void setHayTransferencia(boolean hayTransferencia) {
		this.hayTransferencia = hayTransferencia;
	}

	public SedeDTO getSedeEditar() {
		return sedeEditar;
	}

	public void setSedeEditar(SedeDTO sedeEditar) {
		this.sedeEditar = sedeEditar;
	}

	public TreeNode getNodoOrgGubernamentalSelEdit() {
		return nodoOrgGubernamentalSelEdit;
	}

	public void setNodoOrgGubernamentalSelEdit(TreeNode nodoOrgGubernamentalSelEdit) {
		this.nodoOrgGubernamentalSelEdit = nodoOrgGubernamentalSelEdit;
	}

	public TreeNode getArbolOrgGubernamental2() {
		return arbolOrgGubernamental2;
	}

	public void setArbolOrgGubernamental2(TreeNode arbolOrgGubernamental2) {
		this.arbolOrgGubernamental2 = arbolOrgGubernamental2;
	}

	public boolean isMostrarTree() {
		return mostrarTree;
	}

	public void setMostrarTree(boolean mostrarTree) {
		this.mostrarTree = mostrarTree;
	}

	public BitacoraBean getBitacoraBean() {
		return bitacoraBean;
	}

	public void setBitacoraBean(BitacoraBean bitacoraBean) {
		this.bitacoraBean = bitacoraBean;
	}

	public OrgGubernamentalService getOrgGubernamentalService() {
		return orgGubernamentalService;
	}

	public void setOrgGubernamentalService(OrgGubernamentalService orgGubernamentalService) {
		this.orgGubernamentalService = orgGubernamentalService;
	}

}
