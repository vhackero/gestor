package mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.DetEstUniDidacticaDTO;


public class UnidadOaAvaDTO implements Serializable{

	/**
	 * Serialization
	 */
	private static final long serialVersionUID = 1L;


	
	private Integer id;

	private Date fechaActualizacion;

	private Date fechaRegistro;

	private Boolean funcionalidad;

	private Byte porcentajeAvanceOa;
	
	private Boolean  validacionDesarrolloOa;

	private Boolean validacionGuionInst ;

	private Boolean validacionScorm ;
			
	private BigInteger usuarioModifico;

	private DetEstUniDidacticaDTO detEstUnidadDidactica;
	
	private AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizaje;
	
	private FichaDescriptivaOaDTO fichaDescriptivaObjetoAprendizaje;
	
	private List<ReponsableProduccionOaDTO> reponsableProduccionOas;

	private List<CargaArchivosOaDTO> cargaArchivosOas;

	private List<RecursosOaDTO> recursosOas;
	
	
	/**Estas variables no se persisten solo son para la vista**/
	private Boolean isGuionIConfirmable = Boolean.FALSE; 

	private Boolean isDesarrolloOaConfirmable = Boolean.FALSE;
	
	private Boolean isScormConfirmable = Boolean.FALSE;
	
	private Boolean isFuncionalidadConfirmable = Boolean.FALSE;
	
	private ReponsableProduccionOaDTO expertoEnContenido = new ReponsableProduccionOaDTO();
	
	private ReponsableProduccionOaDTO disenadorInstruccional = new ReponsableProduccionOaDTO();;
	
	private ReponsableProduccionOaDTO disenadorElearning = new ReponsableProduccionOaDTO();;
	
	private ReponsableProduccionOaDTO desarrolladorElearning = new ReponsableProduccionOaDTO();;
        
	
	
	public UnidadOaAvaDTO() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Date getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Boolean getFuncionalidad() {
		return this.funcionalidad;
	}

	public void setFuncionalidad(Boolean funcionalidad) {
		this.funcionalidad = funcionalidad;
	}

	


	public Byte getPorcentajeAvanceOa() {
		return this.porcentajeAvanceOa;
	}

	public void setPorcentajeAvanceOa(Byte porcentajeAvanceOa) {
		this.porcentajeAvanceOa = porcentajeAvanceOa;
	}

	public BigInteger getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(BigInteger usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	

	public DetEstUniDidacticaDTO getDetEstUnidadDidactica() {
		return detEstUnidadDidactica;
	}

	public void setDetEstUnidadDidactica(DetEstUniDidacticaDTO detEstUnidadDidactica) {
		this.detEstUnidadDidactica = detEstUnidadDidactica;
	}

	public AmbienteVirtualAprendizajeDTO getAmbienteVirtualAprendizaje() {
		return ambienteVirtualAprendizaje;
	}

	public void setAmbienteVirtualAprendizaje(AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizaje) {
		this.ambienteVirtualAprendizaje = ambienteVirtualAprendizaje;
	}

	public FichaDescriptivaOaDTO getFichaDescriptivaObjetoAprendizaje() {
		return fichaDescriptivaObjetoAprendizaje;
	}

	public void setFichaDescriptivaObjetoAprendizaje(FichaDescriptivaOaDTO fichaDescriptivaObjetoAprendizaje) {
		this.fichaDescriptivaObjetoAprendizaje = fichaDescriptivaObjetoAprendizaje;
	}

	public List<ReponsableProduccionOaDTO> getReponsableProduccionOas() {
		return reponsableProduccionOas;
	}

	public void setReponsableProduccionOas(List<ReponsableProduccionOaDTO> reponsableProduccionOas) {
		this.reponsableProduccionOas = reponsableProduccionOas;
	}

	public List<CargaArchivosOaDTO> getCargaArchivosOas() {
		return cargaArchivosOas;
	}

	public void setCargaArchivosOas(List<CargaArchivosOaDTO> cargaArchivosOas) {
		this.cargaArchivosOas = cargaArchivosOas;
	}

	public List<RecursosOaDTO> getRecursosOas() {
		return recursosOas;
	}

	public void setRecursosOas(List<RecursosOaDTO> recursosOas) {
		this.recursosOas = recursosOas;
	}
	

	public Boolean getValidacionDesarrolloOa() {
		return validacionDesarrolloOa;
	}

	public void setValidacionDesarrolloOa(Boolean validacionDesarrolloOa) {
		this.validacionDesarrolloOa = validacionDesarrolloOa;
	}

	

	public Boolean getValidacionGuionInst() {
		return validacionGuionInst;
	}

	public void setValidacionGuionInst(Boolean validacionGuionInst) {
		this.validacionGuionInst = validacionGuionInst;
	}

	public Boolean getValidacionScorm() {
		return validacionScorm;
	}

	public void setValidacionScorm(Boolean validacionScorm) {
		this.validacionScorm = validacionScorm;
	}
	
	

	public Boolean getIsGuionIConfirmable() {
		return isGuionIConfirmable;
	}

	public void setIsGuionIConfirmable(Boolean isGuionIConfirmable) {
		this.isGuionIConfirmable = isGuionIConfirmable;
	}

	public Boolean getIsDesarrolloOaConfirmable() {
		return isDesarrolloOaConfirmable;
	}

	public void setIsDesarrolloOaConfirmable(Boolean isDesarrolloOaConfirmable) {
		this.isDesarrolloOaConfirmable = isDesarrolloOaConfirmable;
	}

	public Boolean getIsScormConfirmable() {
		return isScormConfirmable;
	}

	public void setIsScormConfirmable(Boolean isScormConfirmable) {
		this.isScormConfirmable = isScormConfirmable;
	}
	
	public Boolean getIsFuncionalidadConfirmable() {
		return isFuncionalidadConfirmable;
	}

	public void setIsFuncionalidadConfirmable(Boolean isFuncionalidadConfirmable) {
		this.isFuncionalidadConfirmable = isFuncionalidadConfirmable;
	}

	public ReponsableProduccionOaDTO getExpertoEnContenido() {
		return expertoEnContenido;
	}

	public void setExpertoEnContenido(ReponsableProduccionOaDTO expertoEnContenido) {
		this.expertoEnContenido = expertoEnContenido;
	}

	public ReponsableProduccionOaDTO getDisenadorInstruccional() {
		return disenadorInstruccional;
	}

	public void setDisenadorInstruccional(ReponsableProduccionOaDTO disenadorInstruccional) {
		this.disenadorInstruccional = disenadorInstruccional;
	}

	public ReponsableProduccionOaDTO getDisenadorElearning() {
		return disenadorElearning;
	}

	public void setDisenadorElearning(ReponsableProduccionOaDTO disenadorElearning) {
		this.disenadorElearning = disenadorElearning;
	}

	public ReponsableProduccionOaDTO getDesarrolladorElearning() {
		return desarrolladorElearning;
	}

	public void setDesarrolladorElearning(ReponsableProduccionOaDTO desarrolladorElearning) {
		this.desarrolladorElearning = desarrolladorElearning;
	}


}
