package mx.gob.sedesol.gestorweb.beans.planesprogramas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;

import org.apache.log4j.Logger;
import org.primefaces.model.DualListModel;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.CompetenciaEspecificaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.MallaCurricularDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelEjeCompetenciaDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.service.admin.CatalogosGeneralesService;
import mx.gob.sedesol.basegestor.service.impl.planesyprogramas.FECServiceFacade;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.beans.administracion.BitacoraBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.dto.NodoeHijosDTO;
import mx.gob.sedesol.gestorweb.commons.utils.BitacoraUtil;

@ViewScoped
@ManagedBean
public class EjeCompetenciaBean extends BaseBean {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(EjeCompetenciaBean.class);

	@ManagedProperty(value = "#{catalogosGeneralesService}")
	private CatalogosGeneralesService catalogosGeneralesService;

	@ManagedProperty(value = "#{fecServiceFacade}")
	private FECServiceFacade fecServiceFacade;

	@ManagedProperty("#{bitacoraBean}")
	private BitacoraBean bitacoraBean;

	private List<CatalogoComunDTO> tiposCompetencias;
	private List<CatalogoComunDTO> ejes;
	private List<RelEjeCompetenciaDTO> competencias;

	private Integer idTipoCompetencia;
	private Integer idEjeCapacitacion;
	private Integer eje;

	private NodoeHijosDTO estPlanSedesol;
	private RelEjeCompetenciaDTO selected;

	private DualListModel<CompetenciaEspecificaDTO> dualCompetencias;
	List<CompetenciaEspecificaDTO> competenciasSource;
	List<CompetenciaEspecificaDTO> competenciasTarget;
	List<RelEjeCompetenciaDTO> relEjeTodos;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void iniciaRecursos() {

		ejes = new ArrayList<>();
		eje = 0;
		competencias = new ArrayList<>();

		tiposCompetencias = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_TPO_PLAN);

		this.generaEstructuraCatTpoCompetenciaPlan();

		competenciasSource = new ArrayList<CompetenciaEspecificaDTO>();
		competenciasTarget = new ArrayList<CompetenciaEspecificaDTO>();
		setDualCompetencias(new DualListModel<CompetenciaEspecificaDTO>(competenciasSource, competenciasTarget));
		idEjeCapacitacion = 0;
		idTipoCompetencia = 0;
	}

	@SuppressWarnings("unchecked")
	public void modificado() {
		competenciasSource = new ArrayList<CompetenciaEspecificaDTO>();
		competenciasTarget = new ArrayList<CompetenciaEspecificaDTO>();
		dualCompetencias = new DualListModel<CompetenciaEspecificaDTO>(competenciasSource, competenciasTarget);
		relEjeTodos = new ArrayList<RelEjeCompetenciaDTO>();

		competenciasSource = fecServiceFacade.getCompetenciaEspecificaService().findAll();
		relEjeTodos = fecServiceFacade.getEjeCompetenciaService().obtenerCompetenciasEspecificasPorEje(eje);

		for (int i = 0; i < relEjeTodos.size(); i++) {
			competenciasTarget.add(relEjeTodos.get(i).getCatCompetenciaEspecifica());
			for (int y = 0; y < competenciasSource.size(); y++) {
				if (competenciasTarget.get(i).getId().equals(competenciasSource.get(y).getId())) {
					competenciasSource.remove(competenciasSource.get(y));
				}
			}
		}
		setDualCompetencias(new DualListModel<CompetenciaEspecificaDTO>(competenciasSource, competenciasTarget));
	}

	public void guardarCompetencias() {
		for (int i = 0; i < relEjeTodos.size(); i++) {
			relEjeTodos.get(i).setUsuarioModifico(getUsuarioEnSession().getIdPersona());
			fecServiceFacade.getEjeCompetenciaService().eliminar(relEjeTodos.get(i));
		}
		for (int i = 0; i < dualCompetencias.getTarget().size(); i++) {
			CompetenciaEspecificaDTO ce = dualCompetencias.getTarget().get(i);
			RelEjeCompetenciaDTO ec = new RelEjeCompetenciaDTO();
			ec.setCatCompetenciaEspecifica(ce);
			ec.setIdMallaCurricular(eje);
			ec.setIdCompetenciaEspecifica(ce.getId());
			ec.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
			ec.setFechaRegistro(new Date());
			fecServiceFacade.getEjeCompetenciaService().guardar(ec);
		}
		competencias = fecServiceFacade.getEjeCompetenciaService().obtenerCompetenciasEspecificasPorEje(eje);

		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "EDI_COMP_EJE", "", requestActual(), TipoServicioEnum.LOCAL);
		agregarMsgInfo("Modificación exitosa.", null);
	}

	public void removeCompetencia() {
		fecServiceFacade.getEjeCompetenciaService().eliminar(selected);
		iniciaRecursos();
	}

	public void onChangeTpoCompetencia(ValueChangeEvent e) {

		ejes = new ArrayList<>();
		competencias = new ArrayList<>();

		if (ObjectUtils.isNotNull(e.getNewValue())) {

			Integer tCompetencia = (Integer) e.getNewValue();
			if (!tCompetencia.equals(0)) {
				for (NodoeHijosDTO nh : estPlanSedesol.getNodosHijos()) {
					if (nh.getIdNodo().equals(tCompetencia)) {
						for (NodoeHijosDTO nint : nh.getNodosHijos()) {
							CatalogoComunDTO cc = new CatalogoComunDTO();
							cc.setId(nint.getIdNodo());
							cc.setNombre(nint.getNombre());
							ejes.add(cc);
						}
					}
				}
			} else {
				competencias = new ArrayList<>();
				ejes = new ArrayList<>();
			}

		}
	}

	public void onChangeEje(ValueChangeEvent idEje) {
		competencias = new ArrayList<>();
		if (ObjectUtils.isNotNull(idEje.getNewValue())) {
			eje = (Integer) idEje.getNewValue();
			if (!eje.equals(0)) {
				competencias = fecServiceFacade.getEjeCompetenciaService().obtenerCompetenciasEspecificasPorEje(eje);
				if (!competencias.isEmpty()) {
					bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CON_COM_EJE", "", requestActual(),
							TipoServicioEnum.LOCAL);
				}
			}else{
				competencias = new ArrayList<>();
			}

		}

	}

	private void generaEstructuraCatTpoCompetenciaPlan() {

		List<NodoeHijosDTO> planes = new ArrayList<>();
		List<MallaCurricularDTO> mallas = new ArrayList<>();

		// RN: Solo se presentara el plan de sedesol por el momento
		MallaCurricularDTO mallaSedesol = getFecServiceFacade().getMallaCurricularService()
				.obtenerMallaCurricularPorId(1);
		mallas.add(mallaSedesol);

		for (MallaCurricularDTO m : mallas) {
			NodoeHijosDTO nodog = new NodoeHijosDTO();
			nodog.setNombre(m.getNombre());
			nodog.setIdNodo(m.getId());
			nodog.setIdPadre(m.getMallaCurricularPadre() != null ? m.getMallaCurricularPadre().getId() : null);
			nodog.setIdObjCurr(m.getObjetoCurricular().getId());
			nodog.setNivel(0);

			if (!m.getLstHijosMallaCurr().isEmpty()) {
				this.generaCatxNivel(m.getLstHijosMallaCurr(), nodog, nodog.getNivel());
			}

			planes.add(nodog);
		}

		tiposCompetencias = new ArrayList<>();

		// Genera el Catalogo Tipo de Competencia
		estPlanSedesol = planes.get(ConstantesGestorWeb.INDICE_INICIAL);
		for (NodoeHijosDTO nh : estPlanSedesol.getNodosHijos()) {
			CatalogoComunDTO cc = new CatalogoComunDTO();
			cc.setId(nh.getIdNodo());
			cc.setNombre(nh.getNombre());
			tiposCompetencias.add(cc);
		}
	}

	// private void generaCatxNivel(List<MallaCurricularDTO> hijos,
	// NodoeHijosDTO nodoGral, int nivel) {
	//
	// for (MallaCurricularDTO mint : hijos) {
	//
	// NodoeHijosDTO hijo = new NodoeHijosDTO();
	// hijo.setIdNodo(mint.getId());
	// hijo.setIdPadre(nodoGral.getIdNodo());
	// hijo.setIdObjCurr(mint.getObjetoCurricular().getId());
	// hijo.setNivel(nivel + 1);
	// hijo.setNombre(mint.getNombre());
	// nodoGral.getNodosHijos().add(hijo);
	//
	// if (!ObjectUtils.isNullOrEmpty(mint.getLstHijosMallaCurr()))
	// this.generaCatxNivel(mint.getLstHijosMallaCurr(), hijo, hijo.getNivel());
	// ;
	// }
	// }
	public List<RelEjeCompetenciaDTO> getCompetencias() {
		return competencias;
	}

	public void setCompetencias(List<RelEjeCompetenciaDTO> competencias) {
		this.competencias = competencias;
	}

	public RelEjeCompetenciaDTO getSelected() {
		return selected;
	}

	public void setSelected(RelEjeCompetenciaDTO selected) {
		this.selected = selected;
	}

	public CatalogosGeneralesService getCatalogosGeneralesService() {
		return catalogosGeneralesService;
	}

	public void setCatalogosGeneralesService(CatalogosGeneralesService catalogosGeneralesService) {
		this.catalogosGeneralesService = catalogosGeneralesService;
	}

	public FECServiceFacade getFecServiceFacade() {
		return fecServiceFacade;
	}

	public void setFecServiceFacade(FECServiceFacade fecServiceFacade) {
		this.fecServiceFacade = fecServiceFacade;
	}

	public List<CatalogoComunDTO> getTiposCompetencias() {
		return tiposCompetencias;
	}

	public void setTiposCompetencias(List<CatalogoComunDTO> tiposCompetencias) {
		this.tiposCompetencias = tiposCompetencias;
	}

	public List<CatalogoComunDTO> getEjes() {
		return ejes;
	}

	public void setEjes(List<CatalogoComunDTO> ejes) {
		this.ejes = ejes;
	}

	public Integer getIdTipoCompetencia() {
		return idTipoCompetencia;
	}

	public void setIdTipoCompetencia(Integer idTipoCompetencia) {
		this.idTipoCompetencia = idTipoCompetencia;
	}

	public Integer getIdEjeCapacitacion() {
		return idEjeCapacitacion;
	}

	public void setIdEjeCapacitacion(Integer idEjeCapacitacion) {
		this.idEjeCapacitacion = idEjeCapacitacion;
	}

	public NodoeHijosDTO getEstPlanSedesol() {
		return estPlanSedesol;
	}

	public void setEstPlanSedesol(NodoeHijosDTO estPlanSedesol) {
		this.estPlanSedesol = estPlanSedesol;
	}

	public DualListModel<CompetenciaEspecificaDTO> getDualCompetencias() {
		return dualCompetencias;
	}

	public void setDualCompetencias(DualListModel<CompetenciaEspecificaDTO> dualCompetencias) {
		this.dualCompetencias = dualCompetencias;
	}

	public BitacoraBean getBitacoraBean() {
		return bitacoraBean;
	}

	public void setBitacoraBean(BitacoraBean bitacoraBean) {
		this.bitacoraBean = bitacoraBean;
	}

}
