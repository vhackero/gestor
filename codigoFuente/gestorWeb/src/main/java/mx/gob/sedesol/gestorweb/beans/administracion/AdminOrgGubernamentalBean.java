package mx.gob.sedesol.gestorweb.beans.administracion;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;

import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.NotEmpty;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.NodoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.OrgGubernamentalDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.service.admin.OrgGubernamentalService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.utils.BitacoraUtil;

@ManagedBean
@ViewScoped
public class AdminOrgGubernamentalBean extends BaseBean {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(AdminOrgGubernamentalBean.class);

	private OrgGubernamentalDTO orgGub;
	private TreeNode orgGubSelect;
	private boolean muestraPnlRegistro;
	@NotEmpty(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String nombreElemOrgGub;

	@ManagedProperty(value = "#{orgGubernamentalService}")
	private OrgGubernamentalService orgGubernamentalService;

	@ManagedProperty("#{bitacoraBean}")
	private BitacoraBean bitacoraBean;

	private TreeNode rootView;

	public AdminOrgGubernamentalBean() {

		orgGub = new OrgGubernamentalDTO();
		rootView = new DefaultTreeNode(new NodoDTO(null, "RootView"), null);
	}

	@PostConstruct
	public void iniciarRecursos() {
		logger.info("Generando arbol de organismos gubernamentales");
		List<OrgGubernamentalDTO> orgsGubPadres = orgGubernamentalService.obtenerOrgGubsPadres();
		if (!ObjectUtils.isNullOrEmpty(orgsGubPadres)) {
			for (OrgGubernamentalDTO p : orgsGubPadres) {
				TreeNode objPadre = new DefaultTreeNode(new NodoDTO(p.getId(), p.getNombre(), p.getIdTipoOrganismo()),
						rootView);
				this.generaEstructuraOrgGubernamental(objPadre, p.getLstHijosOrgGub());
			}
		}
	}

	/**
	 * Metodo recursivo para generar la estructura del Arbol
	 *
	 * @param nodoPadre
	 * @param mallasHijo
	 */
	private void generaEstructuraOrgGubernamental(TreeNode nodoPadre, List<OrgGubernamentalDTO> mallasHijo) {
		if (!ObjectUtils.isNullOrEmpty(mallasHijo)) {
			for (OrgGubernamentalDTO hijo : mallasHijo) {
				TreeNode nodoHijo = new DefaultTreeNode(
						new NodoDTO(hijo.getId(), hijo.getNombre(), hijo.getIdTipoOrganismo()), nodoPadre);
				if (!ObjectUtils.isNullOrEmpty(hijo.getLstHijosOrgGub())) {
					generaEstructuraOrgGubernamental(nodoHijo, hijo.getLstHijosOrgGub());
				}
			}
		}
	}

	/**
	 *
	 */
	public void generaOrgGubPadre() {
		if (!ObjectUtils.isNullOrEmpty(orgGub.getNombre())) {

			orgGub.setActivo(ConstantesGestor.ACTIVO);
			orgGub.setFechaRegistro(getFechaActual());
			orgGub.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
			orgGub.setIdTipoOrganismo(ConstantesGestor.VERSION_UNO);
			ResultadoDTO<OrgGubernamentalDTO> resTx = orgGubernamentalService.guardar(orgGub);

			if (ObjectUtils.isNotNull(resTx) && resTx.getResultado().getValor()) {
				bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CRE_ORG_GUB", String.valueOf(resTx.getDto().getId()),
						requestActual(), TipoServicioEnum.LOCAL);
				new DefaultTreeNode(new NodoDTO(orgGub.getId(), orgGub.getNombre(), orgGub.getIdTipoOrganismo()),
						rootView);
				nombreElemOrgGub = new String();
				orgGub = new OrgGubernamentalDTO();
			}
		}
	}

	public void muestraApartadoRegistro() {
		setMuestraPnlRegistro(Boolean.TRUE);
	}

	/**
	 * Elimina Organismo Gubernamental
	 */
	public void eliminaOrgGubSel() {
		if (ObjectUtils.isNotNull(orgGubSelect)) {

			NodoDTO dataNodoSel = (NodoDTO) orgGubSelect.getData();
			OrgGubernamentalDTO objEliminar = orgGubernamentalService.buscarPorId(dataNodoSel.getId());

			Integer idEliminar = dataNodoSel.getId();
			ResultadoDTO<OrgGubernamentalDTO> rsTx = orgGubernamentalService.eliminar(objEliminar);

			if (ObjectUtils.isNotNull(rsTx) && rsTx.getResultado().getValor()) {
				bitacoraBean.guardarBitacora(idPersonaEnSesion(), "ELI_ORG_GUB", String.valueOf(idEliminar),
						requestActual(), TipoServicioEnum.LOCAL);
				TreeNode parent = orgGubSelect.getParent();
				if (ObjectUtils.isNotNull(parent) && !parent.getChildren().isEmpty()) {
					parent.getChildren().remove(orgGubSelect);
				}
			}
		}
	}

	/**
	 *
	 */
	public void guardarElementoOrgGub() {
		if (ObjectUtils.isNotNull(orgGubSelect) && !ObjectUtils.isNullOrEmpty(nombreElemOrgGub)) {
			this.generaElementoOrgGubHijo(nombreElemOrgGub, orgGubSelect);
			setMuestraPnlRegistro(Boolean.FALSE);
		}
	}

	/**
	 *
	 * @param nombreElem
	 * @param nodoPadre
	 */
	private void generaElementoOrgGubHijo(String nombreElem, TreeNode nodoPadre) {

		OrgGubernamentalDTO orgGubHijo = new OrgGubernamentalDTO();

		orgGubHijo.setActivo(ConstantesGestor.ACTIVO);
		orgGubHijo.setFechaRegistro(getFechaActual());
		orgGubHijo.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
		orgGubHijo.setNombre(nombreElem);

		if (ObjectUtils.isNotNull(nodoPadre)) {
			NodoDTO nodoDataPadre = (NodoDTO) nodoPadre.getData();
			orgGubHijo.setIdTipoOrganismo(nodoDataPadre.getTipoObjCurricular() + ConstantesGestor.VERSION_UNO);
			orgGubHijo.setOrgGubPadre(orgGubernamentalService.obtenerOrgGubernamentalPorId(nodoDataPadre.getId()));
		}
		ResultadoDTO<OrgGubernamentalDTO> resTxHijo = orgGubernamentalService.guardar(orgGubHijo);
		if (ObjectUtils.isNotNull(resTxHijo) && resTxHijo.getResultado().getValor()) {
			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CRE_ORG_GUB", String.valueOf(resTxHijo.getDto().getId()),
					requestActual(), TipoServicioEnum.LOCAL);
			orgGubHijo = resTxHijo.getDto();
			TreeNode nodoHijo = new DefaultTreeNode(
					new NodoDTO(orgGubHijo.getId(), orgGubHijo.getNombre(), orgGubHijo.getIdTipoOrganismo()),
					nodoPadre);
			nodoHijo.setExpanded(true);
			nombreElemOrgGub = new String();
		}
	}

	/**
	 *
	 * @param event
	 */
	public void onNodeSelect(NodeSelectEvent event) {
		orgGubSelect = event.getTreeNode();
	}

	/**
	 * @return the orgGub
	 */
	public OrgGubernamentalDTO getOrgGub() {
		return orgGub;
	}

	/**
	 * @param orgGub
	 *            the orgGub to set
	 */
	public void setOrgGub(OrgGubernamentalDTO orgGub) {
		this.orgGub = orgGub;
	}

	/**
	 * @return the orgGubernamentalService
	 */
	public OrgGubernamentalService getOrgGubernamentalService() {
		return orgGubernamentalService;
	}

	/**
	 * @param orgGubernamentalService
	 *            the orgGubernamentalService to set
	 */
	public void setOrgGubernamentalService(OrgGubernamentalService orgGubernamentalService) {
		this.orgGubernamentalService = orgGubernamentalService;
	}

	/**
	 * @return the orgGubSelect
	 */
	public TreeNode getOrgGubSelect() {
		return orgGubSelect;
	}

	/**
	 * @param orgGubSelect
	 *            the orgGubSelect to set
	 */
	public void setOrgGubSelect(TreeNode orgGubSelect) {
		this.orgGubSelect = orgGubSelect;
	}

	/**
	 * @return the muestraPnlRegistro
	 */
	public boolean isMuestraPnlRegistro() {
		return muestraPnlRegistro;
	}

	/**
	 * @param muestraPnlRegistro
	 *            the muestraPnlRegistro to set
	 */
	public void setMuestraPnlRegistro(boolean muestraPnlRegistro) {
		this.muestraPnlRegistro = muestraPnlRegistro;
	}

	/**
	 * @return the nombreElemOrgGub
	 */
	public String getNombreElemOrgGub() {
		return nombreElemOrgGub;
	}

	/**
	 * @param nombreElemOrgGub
	 *            the nombreElemOrgGub to set
	 */
	public void setNombreElemOrgGub(String nombreElemOrgGub) {
		this.nombreElemOrgGub = nombreElemOrgGub;
	}

	/**
	 * @return the rootView
	 */
	public TreeNode getRootView() {
		return rootView;
	}

	/**
	 * @param rootView
	 *            the rootView to set
	 */
	public void setRootView(TreeNode rootView) {
		this.rootView = rootView;
	}

	public BitacoraBean getBitacoraBean() {
		return bitacoraBean;
	}

	public void setBitacoraBean(BitacoraBean bitacoraBean) {
		this.bitacoraBean = bitacoraBean;
	}

}
