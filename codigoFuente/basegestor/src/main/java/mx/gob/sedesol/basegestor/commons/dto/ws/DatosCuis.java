package mx.gob.sedesol.basegestor.commons.dto.ws;

import java.io.Serializable;
import java.util.List;

public class DatosCuis implements Serializable {

	private static final long serialVersionUID = 7571907376344521275L;

	private String tipo_usuario;
	private String usuario;
	private String curp;
	private String nombre;
	private String primer_apellido;
	private String segundo_apellido;
	private String fecha_nacimiento;
	private String institucion_en_que_labora;
	private String correo_usuario;
	private String telefono_usuario;
	private String celular_usuario;
	private String sede_usuario_labora;
	private String municipio_usuario_labora;
	private String orden_gob_usuario_labora;
	private String puesto_usuario_labora;
	private String modalidad_programa;
	private List<Evento> eventos;
	private String fecha_acreditacion;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo_usuario() {
		return tipo_usuario;
	}

	public void setTipo_usuario(String tipo_usuario) {
		this.tipo_usuario = tipo_usuario;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	public void setFecha_nacimiento(String fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}

	public String getInstitucion_en_que_labora() {
		return institucion_en_que_labora;
	}

	public void setInstitucion_en_que_labora(String institucion_en_que_labora) {
		this.institucion_en_que_labora = institucion_en_que_labora;
	}

	public String getCorreo_usuario() {
		return correo_usuario;
	}

	public void setCorreo_usuario(String correo_usuario) {
		this.correo_usuario = correo_usuario;
	}

	public String getTelefono_usuario() {
		return telefono_usuario;
	}

	public void setTelefono_usuario(String telefono_usuario) {
		this.telefono_usuario = telefono_usuario;
	}

	public String getCelular_usuario() {
		return celular_usuario;
	}

	public void setCelular_usuario(String celular_usuario) {
		this.celular_usuario = celular_usuario;
	}

	public String getSede_usuario_labora() {
		return sede_usuario_labora;
	}

	public void setSede_usuario_labora(String sede_usuario_labora) {
		this.sede_usuario_labora = sede_usuario_labora;
	}

	public String getMunicipio_usuario_labora() {
		return municipio_usuario_labora;
	}

	public void setMunicipio_usuario_labora(String municipio_usuario_labora) {
		this.municipio_usuario_labora = municipio_usuario_labora;
	}

	public String getOrden_gob_usuario_labora() {
		return orden_gob_usuario_labora;
	}

	public void setOrden_gob_usuario_labora(String orden_gob_usuario_labora) {
		this.orden_gob_usuario_labora = orden_gob_usuario_labora;
	}

	public String getPuesto_usuario_labora() {
		return puesto_usuario_labora;
	}

	public void setPuesto_usuario_labora(String puesto_usuario_labora) {
		this.puesto_usuario_labora = puesto_usuario_labora;
	}

	public String getModalidad_programa() {
		return modalidad_programa;
	}

	public void setModalidad_programa(String modalidad_programa) {
		this.modalidad_programa = modalidad_programa;
	}

	public String getPrimer_apellido() {
		return primer_apellido;
	}

	public void setPrimer_apellido(String primer_apellido) {
		this.primer_apellido = primer_apellido;
	}

	public String getSegundo_apellido() {
		return segundo_apellido;
	}

	public void setSegundo_apellido(String segundo_apellido) {
		this.segundo_apellido = segundo_apellido;
	}

	public List<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	public String getFecha_acreditacion() {
		return fecha_acreditacion;
	}

	public void setFecha_acreditacion(String fecha_acreditacion) {
		this.fecha_acreditacion = fecha_acreditacion;
	}

}
