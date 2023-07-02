package mx.gob.sedesol.gestorweb.beans.logisticainfraestructura;

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

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.RecursosInfraestructuraDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.CatTipoRecurso;
import mx.gob.sedesol.basegestor.service.impl.logisticainfraestructura.LogisticaInfraServiceFacade;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.beans.administracion.BitacoraBean;
import mx.gob.sedesol.gestorweb.commons.utils.BitacoraUtil;

@ManagedBean
@ViewScoped
public class RecursosBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(RecursosBean.class);

	@ManagedProperty(value = "#{logisticaInfraServiceFacade}")
	private LogisticaInfraServiceFacade logisticaInfraServiceFacade;

	@ManagedProperty("#{bitacoraBean}")
	private BitacoraBean bitacoraBean;

	private List<CatalogoComunDTO> tiposRecursos;
	private CatalogoComunDTO tipoRecurso;
	private RecursosInfraestructuraDTO recursoNuevo;
	private List<RecursosInfraestructuraDTO> tblRecursos;
	private RecursosInfraestructuraDTO recursoElim;
	private CatalogoComunDTO catalogoVacio;

	public RecursosBean() {
		catalogoVacio = null;
		recursoNuevo = new RecursosInfraestructuraDTO();
		tipoRecurso = new CatalogoComunDTO();
		tblRecursos = new ArrayList<>();
		tiposRecursos = new ArrayList<>();
		recursoElim = new RecursosInfraestructuraDTO();
	}

	@PostConstruct
	public void init() {
		tiposRecursos = logisticaInfraServiceFacade.getCatTipoRecursoService().findAll(CatTipoRecurso.class);
	}

	public void agregarRecurso() {
		recursoNuevo.setActivo(1);
		recursoNuevo.setFechaRegistro(new Date());
		recursoNuevo.setCatTipoRecurso(tipoRecurso);
		recursoNuevo.setOrden(1);
		recursoNuevo.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
		tblRecursos.add(0,
				logisticaInfraServiceFacade.getRecursosInfraestructuraService().guardar(recursoNuevo).getDto());
		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CRE_REC", String.valueOf(tblRecursos.get(0).getIdRecurso()),
				requestActual(), TipoServicioEnum.LOCAL);
		agregarMsgInfo("Se agregó el recurso exitosamente", null);
		recursoNuevo = new RecursosInfraestructuraDTO();
	}

	public void eliminarRecurso() {
		Integer idRecursoEliminado = recursoElim.getIdRecurso();
		tblRecursos.remove(recursoElim);
		ResultadoDTO<RecursosInfraestructuraDTO> resultado = logisticaInfraServiceFacade
				.getRecursosInfraestructuraService().eliminar(recursoElim);
		if (resultado.getResultado() == ResultadoTransaccionEnum.EXITOSO) {
			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "ELI_REC", String.valueOf(idRecursoEliminado),
					requestActual(), TipoServicioEnum.LOCAL);
			agregarMsgInfo("Se eliminó el recurso exitosamente", null);
		} else {
			agregarMsgInfo("Ocurrió un error al intentar eliminar el recurso", null);
		}

		recursoElim = new RecursosInfraestructuraDTO();
	}

	public void actualizaTipoRecursos(ValueChangeEvent e) {
		CatalogoComunDTO tipo = (CatalogoComunDTO) e.getNewValue();
		tblRecursos = logisticaInfraServiceFacade.getRecursosInfraestructuraService()
				.obtieneRecursosPorTipoDeRecurso(tipo.getId());
		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CON_TIP_REC",
				String.valueOf(tipo.getId()), requestActual(), TipoServicioEnum.LOCAL);
	}

	public LogisticaInfraServiceFacade getLogisticaInfraServiceFacade() {
		return logisticaInfraServiceFacade;
	}

	public void setLogisticaInfraServiceFacade(LogisticaInfraServiceFacade logisticaInfraServiceFacade) {
		this.logisticaInfraServiceFacade = logisticaInfraServiceFacade;
	}

	public List<RecursosInfraestructuraDTO> getTblRecursos() {
		return tblRecursos;
	}

	public void setTblRecursos(List<RecursosInfraestructuraDTO> tblRecursos) {
		this.tblRecursos = tblRecursos;
	}

	public List<CatalogoComunDTO> getTiposRecursos() {
		return tiposRecursos;
	}

	public void setTiposRecursos(List<CatalogoComunDTO> tiposRecursos) {
		this.tiposRecursos = tiposRecursos;
	}

	public CatalogoComunDTO getTipoRecurso() {
		return tipoRecurso;
	}

	public void setTipoRecurso(CatalogoComunDTO tipoRecurso) {
		this.tipoRecurso = tipoRecurso;
	}

	public RecursosInfraestructuraDTO getRecursoElim() {
		return recursoElim;
	}

	public void setRecursoElim(RecursosInfraestructuraDTO recursoElim) {
		this.recursoElim = recursoElim;
	}

	public RecursosInfraestructuraDTO getRecursoNuevo() {
		return recursoNuevo;
	}

	public void setRecursoNuevo(RecursosInfraestructuraDTO recursoNuevo) {
		this.recursoNuevo = recursoNuevo;
	}

	public CatalogoComunDTO getCatalogoVacio() {
		return catalogoVacio;
	}

	public void setCatalogoVacio(CatalogoComunDTO catalogoVacio) {
		this.catalogoVacio = catalogoVacio;
	}

	public BitacoraBean getBitacoraBean() {
		return bitacoraBean;
	}

	public void setBitacoraBean(BitacoraBean bitacoraBean) {
		this.bitacoraBean = bitacoraBean;
	}

}
