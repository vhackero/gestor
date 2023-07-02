package mx.gob.sedesol.basegestor.commons.dto.planesyprogramas;

import java.io.Serializable;
import java.util.Date;
import mx.gob.sedesol.basegestor.commons.dto.admin.BitacoraDTO;


public class RelEjeCompetenciaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Date fechaRegistro;
	private Integer idCompetenciaEspecifica;
	private Integer idMallaCurricular;
	private Long usuarioModifico;
	private MallaCurricularDTO mallaCurricularDTO;
	private CompetenciaEspecificaDTO catCompetenciaEspecifica;
	private BitacoraDTO bitacoraDTO;
        
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public Integer getIdCompetenciaEspecifica() {
		return idCompetenciaEspecifica;
	}
	public void setIdCompetenciaEspecifica(Integer idCompetenciaEspecifica) {
		this.idCompetenciaEspecifica = idCompetenciaEspecifica;
	}
	public Integer getIdMallaCurricular() {
		return idMallaCurricular;
	}
	public void setIdMallaCurricular(Integer idMallaCurricular) {
		this.idMallaCurricular = idMallaCurricular;
	}
	public Long getUsuarioModifico() {
		return usuarioModifico;
	}
	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}
	public MallaCurricularDTO getMallaCurricularDTO() {
		return mallaCurricularDTO;
	}
	public void setMallaCurricularDTO(MallaCurricularDTO mallaCurricularDTO) {
		this.mallaCurricularDTO = mallaCurricularDTO;
	}
	public CompetenciaEspecificaDTO getCatCompetenciaEspecifica() {
		return catCompetenciaEspecifica;
	}
	public void setCatCompetenciaEspecifica(CompetenciaEspecificaDTO catCompetenciaEspecifica) {
		this.catCompetenciaEspecifica = catCompetenciaEspecifica;
	}

    /**
     * @return the bitacoraDTO
     */
    public BitacoraDTO getBitacoraDTO() {
        return bitacoraDTO;
    }

    /**
     * @param bitacoraDTO the bitacoraDTO to set
     */
    public void setBitacoraDTO(BitacoraDTO bitacoraDTO) {
        this.bitacoraDTO = bitacoraDTO;
    }
	
}
