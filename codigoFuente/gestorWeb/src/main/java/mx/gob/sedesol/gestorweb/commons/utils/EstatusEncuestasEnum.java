package mx.gob.sedesol.gestorweb.commons.utils;

public enum EstatusEncuestasEnum {

	BORRADOR(1,"Borrador"),
	COMENTARIOS(2,"Con Comentarios"),
	REVISION(3,"En Revisión"),
	PUBLICAR(4,"A Publicar");

	private Integer id;
	private String etiqueta;
	
	
	private EstatusEncuestasEnum(Integer id, String etiqueta){
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
	public EstatusEncuestasEnum obtieneEstatusEncuestaById(Integer id){
		
		for(EstatusEncuestasEnum enm : values()){
			if(enm.getId().equals(id))
				return enm;
		}
		return null;
	}
	
}
