package mx.gob.sedesol.gestorweb.beans.planesprogramas;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.primefaces.context.RequestContext;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.CompetenciaEspecificaDTO;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.service.planesyprogramas.CompetenciaEspecificaService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.beans.administracion.BitacoraBean;
import mx.gob.sedesol.gestorweb.commons.utils.ObjectUtils;

@ManagedBean
@ViewScoped
public class CompetenciaEspecificaBean extends BaseBean {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CompetenciaEspecificaBean.class);

	@ManagedProperty(value = "#{competenciaEspecificaService}")
	private CompetenciaEspecificaService competenciaEspecificaService;

	@ManagedProperty("#{bitacoraBean}")
	private BitacoraBean bitacoraBean;

	ModelMapper modelMapper = new ModelMapper();

	private CompetenciaEspecificaDTO competencia = new CompetenciaEspecificaDTO();
	private CompetenciaEspecificaDTO competenciaModificar;
	private List<CompetenciaEspecificaDTO> listaCompetencias;
	private boolean nuevo;

	@PostConstruct
	public void init() {
		competencia = new CompetenciaEspecificaDTO();
		competenciaModificar = new CompetenciaEspecificaDTO();
		setListaCompetencias(getCompetenciaEspecificaService().findAll());
	}

	public String administrarCompetencias() {

		init();
		return "ADMINISTRAR_COMPETENCIAS";
	}

	public void nuevaCompetencia() {
		competencia = new CompetenciaEspecificaDTO(getUsuarioEnSession().getIdPersona());
		nuevo = true;
	}

	public void modificaCompetencia() {
		competencia = new CompetenciaEspecificaDTO();
		modelMapper.map(competenciaModificar, competencia);

		competencia.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
		competencia.setFechaActualizacion(new Date());

		nuevo = false;
	}

	public void guardarCompetencia() {
		if (nuevo) {
			if (getCompetenciaEspecificaService().estaVacio(competencia.getId(), competencia.getNombre())) {
				ResultadoDTO<CompetenciaEspecificaDTO> ret = competenciaEspecificaService.guardar(competencia);
				if (!ObjectUtils.isNullOrEmpty(ret)) {
					if (!ObjectUtils.isNullOrEmpty(competencia.getNombre())) {
						listaCompetencias.add(0, ret.getDto());
						bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CRE_COM",
								String.valueOf(ret.getDto().getId()), requestActual(), TipoServicioEnum.LOCAL);
						agregarMsgInfo("Competencia agregada correctamente", null);
						RequestContext.getCurrentInstance().execute("PF('modCompWid').hide()");
					} else {
						agregarMsgError("LLenar campo nombre", null);
					}
				}
			} else {
				agregarMsgError("Ya existe competencia", null);
			}
		} else {
			if (!ObjectUtils.isNullOrEmpty(competencia.getNombre())) {
				CompetenciaEspecificaDTO ret = competenciaEspecificaService.actualizar(competencia).getDto();
				if (!ObjectUtils.isNullOrEmpty(ret)) {
					bitacoraBean.guardarBitacora(idPersonaEnSesion(), "EDI_COM", String.valueOf(ret.getId()),
							requestActual(), TipoServicioEnum.LOCAL);
					listaCompetencias.remove(competenciaModificar);
					listaCompetencias.add(0, ret);
					agregarMsgInfo("Competencia modificada", null);
					RequestContext.getCurrentInstance().execute("PF('modCompWid').hide()");
				}
			} else {
				agregarMsgError("LLenar campo nombre", null);
			}
		}

	}

	public CompetenciaEspecificaDTO getCompetencia() {
		return competencia;
	}

	public void setCompetencia(CompetenciaEspecificaDTO competencia) {
		this.competencia = competencia;
	}

	public List<CompetenciaEspecificaDTO> getListaCompetencias() {
		return listaCompetencias;
	}

	public void setListaCompetencias(List<CompetenciaEspecificaDTO> listaCompetencias) {
		this.listaCompetencias = listaCompetencias;
	}

	public CompetenciaEspecificaDTO getCompetenciaModificar() {
		return competenciaModificar;
	}

	public void setCompetenciaModificar(CompetenciaEspecificaDTO competenciaModificar) {
		this.competenciaModificar = competenciaModificar;
	}

	public CompetenciaEspecificaService getCompetenciaEspecificaService() {
		return competenciaEspecificaService;
	}

	public void setCompetenciaEspecificaService(CompetenciaEspecificaService competenciaEspecificaService) {
		this.competenciaEspecificaService = competenciaEspecificaService;
	}

	public BitacoraBean getBitacoraBean() {
		return bitacoraBean;
	}

	public void setBitacoraBean(BitacoraBean bitacoraBean) {
		this.bitacoraBean = bitacoraBean;
	}

}
