package mx.gob.sedesol.gestorweb.commons.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.event.ValueChangeEvent;

import org.primefaces.model.DefaultScheduleEvent;

import mx.gob.sedesol.basegestor.commons.dto.NodoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.BitacoraDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.AreaSedeDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.PersonalizacionAreaDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;

public class SolicitudReservAreaDTO implements Serializable {

	private static final long serialVersionUID = 2566711555761555108L;

	private Integer idSede;
	private Integer idArea;
	private Integer idAreaSede;
	private DefaultScheduleEvent eventoSchedule;

	private List<String> horasA;
	private String horaInicial;
	private List<String> horasB;
	private String horaFinal;

	private List<NodoDTO> catUbicacionSede;
	private List<CatalogoComunDTO> catAreas;
	
	private PersonalizacionAreaDTO perArea;
	
	private List<AreaSedeDTO> areasSede;
	private Map<Integer, List<AreaSedeDTO>> mapaAreasSede;
	private Boolean personalizacionBoolean; 
        private BitacoraDTO bitacoraDTO;
        
	public SolicitudReservAreaDTO() {
	}

	public void voidSetHorasB(ValueChangeEvent h) {
		horasB = new ArrayList<>();
		Integer index = 0;
		for (int i = 0; i < horasA.size(); i++) {
			if (horasA.get(i).equals(h.getNewValue())) {
				index = horasA.indexOf(horasA.get(i));
			}
		}
		for (int z = 0; z < horasA.size(); z++) {
			if (z > index) {
				horasB.add(horasA.get(z));
			}
		}
	}
	
	public void establecerAreas(ValueChangeEvent e) {
		if (ObjectUtils.isNull(e.getNewValue())) {
			areasSede = new ArrayList<>();
		} else {
			if (mapaAreasSede.containsKey((Integer) e.getNewValue())) {
				areasSede = mapaAreasSede.get((Integer) e.getNewValue());
			}
		}
	}

	/**
	 * @return the idSede
	 */
	public Integer getIdSede() {
		return idSede;
	}

	/**
	 * @param idSede
	 *            the idSede to set
	 */
	public void setIdSede(Integer idSede) {
		this.idSede = idSede;
	}

	/**
	 * @return the idArea
	 */
	public Integer getIdArea() {
		return idArea;
	}

	/**
	 * @param idArea
	 *            the idArea to set
	 */
	public void setIdArea(Integer idArea) {
		this.idArea = idArea;
	}

	/**
	 * @return the catUbicacionSede
	 */
	public List<NodoDTO> getCatUbicacionSede() {
		return catUbicacionSede;
	}

	/**
	 * @param catUbicacionSede
	 *            the catUbicacionSede to set
	 */
	public void setCatUbicacionSede(List<NodoDTO> catUbicacionSede) {
		this.catUbicacionSede = catUbicacionSede;
	}

	/**
	 * @return the catAreas
	 */
	public List<CatalogoComunDTO> getCatAreas() {
		return catAreas;
	}

	/**
	 * @param catAreas
	 *            the catAreas to set
	 */
	public void setCatAreas(List<CatalogoComunDTO> catAreas) {
		this.catAreas = catAreas;
	}

	/**
	 * @return the idAreaSede
	 */
	public Integer getIdAreaSede() {
		return idAreaSede;
	}

	/**
	 * @param idAreaSede
	 *            the idAreaSede to set
	 */
	public void setIdAreaSede(Integer idAreaSede) {
		this.idAreaSede = idAreaSede;
	}

	/**
	 * @return the horaInicial
	 */
	public String getHoraInicial() {
		return horaInicial;
	}

	/**
	 * @param horaInicial
	 *            the horaInicial to set
	 */
	public void setHoraInicial(String horaInicial) {
		this.horaInicial = horaInicial;
	}

	/**
	 * @return the horaFinal
	 */
	public String getHoraFinal() {
		return horaFinal;
	}

	/**
	 * @param horaFinal
	 *            the horaFinal to set
	 */
	public void setHoraFinal(String horaFinal) {
		this.horaFinal = horaFinal;
	}

	/**
	 * @return the horasB
	 */
	public List<String> getHorasB() {
		return horasB;
	}

	/**
	 * @param horasB
	 *            the horasB to set
	 */
	public void setHorasB(List<String> horasB) {
		this.horasB = horasB;
	}
	
	public void llenaHorasA(){
		horasA = new ArrayList<>();

		horasA.add("00:00");
		horasA.add("00:30");
		horasA.add("01:00");
		horasA.add("01:30");
		horasA.add("02:00");
		horasA.add("02:30");
		horasA.add("03:00");
		horasA.add("03:30");
		horasA.add("04:00");
		horasA.add("04:30");
		horasA.add("05:00");
		horasA.add("05:30");
		horasA.add("06:00");
		horasA.add("06:30");
		horasA.add("07:00");
		horasA.add("07:30");
		horasA.add("08:00");
		horasA.add("08:30");
		horasA.add("09:00");
		horasA.add("09:30");
		horasA.add("10:00");
		horasA.add("10:30");
		horasA.add("11:00");
		horasA.add("11:30");
		horasA.add("12:00");
		horasA.add("12:30");
		horasA.add("13:00");
		horasA.add("13:30");
		horasA.add("14:00");
		horasA.add("14:30");
		horasA.add("15:00");
		horasA.add("15:30");
		horasA.add("16:00");
		horasA.add("16:30");
		horasA.add("17:00");
		horasA.add("17:30");
		horasA.add("18:00");
		horasA.add("18:30");
		horasA.add("19:00");
		horasA.add("19:30");
		horasA.add("20:00");
		horasA.add("20:30");
		horasA.add("21:00");
		horasA.add("21:30");
		horasA.add("22:00");
		horasA.add("22:30");
		horasA.add("23:00");
		horasA.add("23:30");
		
	}

	/**
	 * @return the horasA
	 */
	public List<String> getHorasA() {
		return horasA;
	}

	/**
	 * @param horasA
	 *            the horasA to set
	 */
	public void setHorasA(List<String> horasA) {
		this.horasA = horasA;
	}

	/**
	 * @return the perArea
	 */
	public PersonalizacionAreaDTO getPerArea() {
		return perArea;
	}

	/**
	 * @param perArea the perArea to set
	 */
	public void setPerArea(PersonalizacionAreaDTO perArea) {
		this.perArea = perArea;
	}

	public List<AreaSedeDTO> getAreasSede() {
		return areasSede;
	}

	public void setAreasSede(List<AreaSedeDTO> areasSede) {
		this.areasSede = areasSede;
	}

	public Map<Integer, List<AreaSedeDTO>> getMapaAreasSede() {
		return mapaAreasSede;
	}

	public void setMapaAreasSede(Map<Integer, List<AreaSedeDTO>> mapaAreasSede) {
		this.mapaAreasSede = mapaAreasSede;
	}

	/**
	 * @return the personalizacionBoolean
	 */
	public Boolean getPersonalizacionBoolean() {
		return personalizacionBoolean;
	}

	/**
	 * @param personalizacionBoolean the personalizacionBoolean to set
	 */
	public void setPersonalizacionBoolean(Boolean personalizacionBoolean) {
		this.personalizacionBoolean = personalizacionBoolean;
	}

	public DefaultScheduleEvent getEventoSchedule() {
		return eventoSchedule;
	}

	public void setEventoSchedule(DefaultScheduleEvent eventoSchedule) {
		this.eventoSchedule = eventoSchedule;
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
