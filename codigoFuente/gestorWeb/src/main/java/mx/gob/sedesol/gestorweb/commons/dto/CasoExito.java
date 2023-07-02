package mx.gob.sedesol.gestorweb.commons.dto;

public class CasoExito {
	
	private Integer id;
	private String href;
	private String target;
	private String titulo;
	private String subtitulo;
	private String url;
	
	public CasoExito(){
		
	}
	
	public CasoExito(Integer id, String href, String target, String titulo, String subtitulo, String url){
		this.id = id;
		this.href = href;
		this.target = target;
		this.titulo = titulo;
		this.subtitulo = subtitulo;
		this.url = url;
	}
	
	public CasoExito(Integer id, String url){
		this.id = id;
		this.url = url;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getSubtitulo() {
		return subtitulo;
	}
	public void setSubtitulo(String subtitulo) {
		this.subtitulo = subtitulo;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

}
