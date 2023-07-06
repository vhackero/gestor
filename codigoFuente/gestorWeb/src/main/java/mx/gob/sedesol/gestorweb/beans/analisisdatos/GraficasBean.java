package mx.gob.sedesol.gestorweb.beans.analisisdatos;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import mx.gob.sedesol.basegestor.commons.utils.EstatusProgramaEnum;
import mx.gob.sedesol.basegestor.service.planesyprogramas.FichaDescProgramaService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;

@ViewScoped
@ManagedBean
public class GraficasBean extends BaseBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(GraficasBean.class);
	
	@ManagedProperty(value="#{fichaDescProgramaService}")
	private FichaDescProgramaService fichaDescProgramaService;
	ObjectMapper jsonMapper;
	

	public GraficasBean(){
		jsonMapper = new ObjectMapper();
	}
	
	/**
	 * 
	 * @return
	 */
	public void totalProgramasEstatusBorrador(){
		
		String jsonPrgsFinal  = StringUtils.EMPTY;
		String jsonPrgsBloq  = StringUtils.EMPTY;
		String jsonPrgsTotal  = StringUtils.EMPTY;
		String jsonPrgsEstrategico  = StringUtils.EMPTY;
		String jsonPrgsGestion  = StringUtils.EMPTY;
		String jsonPrgsInst = StringUtils.EMPTY;
		String jsonPrgsReg = StringUtils.EMPTY;
		String jsonPrgsProd = StringUtils.EMPTY;
		String jsonPrgsEstructura = StringUtils.EMPTY;
		String jsonPrgsContexto = StringUtils.EMPTY;
		String jsonPrgsLinea = StringUtils.EMPTY;
		String jsonPrgsPresencial = StringUtils.EMPTY;
		String jsonPrgsAct = StringUtils.EMPTY;
		String jsonPrgsInd = StringUtils.EMPTY;
		String jsonPrgsForm = StringUtils.EMPTY;
		String jsonPrgsBasico = StringUtils.EMPTY;
		String jsonPrgsIntermedio = StringUtils.EMPTY;
		String jsonPrgsAvanzado = StringUtils.EMPTY;
		String jsonPrgsCurso = StringUtils.EMPTY;
		String jsonPrgsTaller = StringUtils.EMPTY;
		String jsonPrgsSeminario = StringUtils.EMPTY;

		try {
			int totalProgramas = fichaDescProgramaService.totalProgramas();
			int estrategicos = fichaDescProgramaService.totalProgramasByTipo(ConstantesGestorWeb.COMPETENCIA_ESTRATEGICO);
			int gestion = fichaDescProgramaService.totalProgramasByTipo(ConstantesGestorWeb.COMPETENCIA_GESTION);
			int totFin = fichaDescProgramaService.totalProgramasActivosByEstatus(EstatusProgramaEnum.FINAL.getId());
			int totBloq = fichaDescProgramaService.totalProgramasActivosByEstatus(EstatusProgramaEnum.BLOQUEADO.getId());
			int institucional = fichaDescProgramaService.totalProgramasByTipo(ConstantesGestorWeb.COMPETENCIA_INSTITUCIONAL);
			int regulatorio =  fichaDescProgramaService.totalProgramasByTipo(ConstantesGestorWeb.COMPETENCIA_REGULATORIO);
			int producto =  fichaDescProgramaService.totalProgramasByTipo(ConstantesGestorWeb.COMPETENCIA_PRODUCTO);
			int estructura = fichaDescProgramaService.totalProgramasByTipo(ConstantesGestorWeb.COMPETENCIA_ESTRUCTURA);
			int contexto = fichaDescProgramaService.totalProgramasByTipo(ConstantesGestorWeb.COMPETENCIA_CONTEXTO_HUMANO);
			int linea = fichaDescProgramaService.totalModalidadPrograma(ConstantesGestorWeb.MODALIDAD_EN_LINEA);
			int presencial = fichaDescProgramaService.totalModalidadPrograma(ConstantesGestorWeb.MODALIDAD_PRESENCIAL);
			int actualizacion = fichaDescProgramaService.totalNivelEnsenanzaById(ConstantesGestorWeb.NIVEL_ACTUALIZACION);
			int induccion = fichaDescProgramaService.totalNivelEnsenanzaById(ConstantesGestorWeb.NIVEL_INDUCCION);
			int formacion = fichaDescProgramaService.totalNivelEnsenanzaById(ConstantesGestorWeb.NIVEL_FORMACION);
			int basico = fichaDescProgramaService.totalNivelConocimientoById(ConstantesGestorWeb.CONOCIMIENTO_BASICO);
			int intermedio = fichaDescProgramaService.totalNivelConocimientoById(ConstantesGestorWeb.CONOCIMIENTO_INTERMEDIO);
			int avanzado = fichaDescProgramaService.totalNivelConocimientoById(ConstantesGestorWeb.CONOCIMIENTO_AVANZADO);
			int curso = fichaDescProgramaService.totalTipoEventoById(ConstantesGestorWeb.CURSO);
			int taller = fichaDescProgramaService.totalTipoEventoById(ConstantesGestorWeb.TALLER);
			int seminario =fichaDescProgramaService.totalTipoEventoById(ConstantesGestorWeb.SEMINARIO);
			
			RequestContext reqCtx = RequestContext.getCurrentInstance();
			
			jsonPrgsBasico = jsonMapper.writeValueAsString(basico);
			reqCtx.addCallbackParam("progsBasico",jsonPrgsBasico);
			
			jsonPrgsCurso = jsonMapper.writeValueAsString(curso);
			reqCtx.addCallbackParam("progsCurso",jsonPrgsCurso);
			
			jsonPrgsTaller = jsonMapper.writeValueAsString(taller);
			reqCtx.addCallbackParam("progsTaller",jsonPrgsTaller);
			
			jsonPrgsSeminario = jsonMapper.writeValueAsString(seminario);
			reqCtx.addCallbackParam("progsSeminario",jsonPrgsSeminario);
			
			jsonPrgsIntermedio = jsonMapper.writeValueAsString(intermedio);
			reqCtx.addCallbackParam("progsIntermedio",jsonPrgsIntermedio);
			
			jsonPrgsAvanzado = jsonMapper.writeValueAsString(avanzado);
			reqCtx.addCallbackParam("progsAvanzado",jsonPrgsAvanzado);
			
			jsonPrgsTotal = jsonMapper.writeValueAsString(totalProgramas);
			reqCtx.addCallbackParam("progsTotal",jsonPrgsTotal);
			
			jsonPrgsAct = jsonMapper.writeValueAsString(actualizacion);
			reqCtx.addCallbackParam("progsAct",jsonPrgsAct);
			
			jsonPrgsInd = jsonMapper.writeValueAsString(induccion);
			reqCtx.addCallbackParam("progsInd",jsonPrgsInd);
			
			jsonPrgsForm = jsonMapper.writeValueAsString(formacion);
			reqCtx.addCallbackParam("progsForm",jsonPrgsForm);
			
			jsonPrgsLinea = jsonMapper.writeValueAsString(linea);
			reqCtx.addCallbackParam("progsLinea",jsonPrgsLinea);
			
			jsonPrgsPresencial = jsonMapper.writeValueAsString(presencial);
			reqCtx.addCallbackParam("progsPresencial",jsonPrgsPresencial);
			
			jsonPrgsEstructura = jsonMapper.writeValueAsString(estructura);
			reqCtx.addCallbackParam("progsEstr",jsonPrgsEstructura);
			
			jsonPrgsContexto = jsonMapper.writeValueAsString(contexto);
			reqCtx.addCallbackParam("progsContext",jsonPrgsContexto);
			
			jsonPrgsEstrategico = jsonMapper.writeValueAsString(estrategicos);
			reqCtx.addCallbackParam("progsEst",jsonPrgsEstrategico);
			
			jsonPrgsGestion = jsonMapper.writeValueAsString(gestion);
			reqCtx.addCallbackParam("progsGest",jsonPrgsGestion);
			
			jsonPrgsFinal = jsonMapper.writeValueAsString(totFin);
			reqCtx.addCallbackParam("progsFinal",jsonPrgsFinal);
			
			jsonPrgsBloq = jsonMapper.writeValueAsString(totBloq);
			reqCtx.addCallbackParam("progsBloq",jsonPrgsBloq);
			
			jsonPrgsInst = jsonMapper.writeValueAsString(institucional);
			reqCtx.addCallbackParam("progsInst",jsonPrgsInst);
			
			jsonPrgsReg = jsonMapper.writeValueAsString(regulatorio);
			reqCtx.addCallbackParam("progsReg",jsonPrgsReg);
			
			jsonPrgsProd = jsonMapper.writeValueAsString(producto);
			reqCtx.addCallbackParam("progsProd",jsonPrgsProd);
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		//return jsonCad;
	}

	/**
	 * @return the fichaDescProgramaService
	 */
	public FichaDescProgramaService getFichaDescProgramaService() {
		return fichaDescProgramaService;
	}

	/**
	 * @param fichaDescProgramaService the fichaDescProgramaService to set
	 */
	public void setFichaDescProgramaService(FichaDescProgramaService fichaDescProgramaService) {
		this.fichaDescProgramaService = fichaDescProgramaService;
	}
}
