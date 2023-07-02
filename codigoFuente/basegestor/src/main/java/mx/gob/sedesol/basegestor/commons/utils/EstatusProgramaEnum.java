package mx.gob.sedesol.basegestor.commons.utils;

public enum EstatusProgramaEnum {
	
	BORRADOR(1,"Borrador"),
	FINAL(2,"Final"),
	CANCELADO(3,"Cancelado"),
	BLOQUEADO(4,"Bloqueado"),
	ELIMINADO(5,"Eliminado");

	private Integer id;
	private String etiqueta;
	
	
	private EstatusProgramaEnum(Integer id, String etiqueta){
		this.id = id;
		this.etiqueta = etiqueta;
	}
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @return the etiqueta
	 */
	public String getEtiqueta() {
		return etiqueta;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public EstatusProgramaEnum obtieneEstatusProgramaById(Integer id){
		
		for(EstatusProgramaEnum enm : values()){
			if(enm.getId().equals(id))
				return enm;
		}
		return null;
	}
	
}
