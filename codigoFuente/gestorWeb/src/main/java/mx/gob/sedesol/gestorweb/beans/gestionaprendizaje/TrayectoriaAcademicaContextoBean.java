package mx.gob.sedesol.gestorweb.beans.gestionaprendizaje;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.commons.lang3.StringUtils;

import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.gestorweb.commons.dto.UsuarioSessionDTO;

@ManagedBean
@SessionScoped
public class TrayectoriaAcademicaContextoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idPersonaObjetivo;
    private String matriculaObjetivo;
    private String nombreObjetivo;
    private boolean vistaGestor;
    private String origenContextoAsistente;
    private String claveUdContexto;
    private String nombreUdContexto;
    private String estadoUdContexto;
    private String tipoUdContexto;
    private String motivoUdContexto;

    public void configurarAlumnoSesion(UsuarioSessionDTO usuarioSession) {
        if (usuarioSession == null) {
            limpiar();
            return;
        }
        this.idPersonaObjetivo = usuarioSession.getIdPersona();
        this.matriculaObjetivo = usuarioSession.getUsuario();
        this.nombreObjetivo = usuarioSession.getUsuario();
        this.vistaGestor = false;
        limpiarContextoAsistente();
    }

    public void configurarAlumnoSesion(PersonaDTO persona, UsuarioSessionDTO usuarioSession) {
        if (usuarioSession == null && persona == null) {
            limpiar();
            return;
        }
        this.idPersonaObjetivo = persona != null && persona.getIdPersona() != null
                ? persona.getIdPersona()
                : (usuarioSession != null ? usuarioSession.getIdPersona() : null);
        this.matriculaObjetivo = persona != null && StringUtils.isNotBlank(persona.getUsuario())
                ? persona.getUsuario().trim()
                : (usuarioSession != null ? usuarioSession.getUsuario() : null);
        this.nombreObjetivo = persona != null ? construirNombre(persona)
                : (usuarioSession != null ? usuarioSession.getUsuario() : null);
        this.vistaGestor = false;
        limpiarContextoAsistente();
    }

    public void configurarConsultaGestor(PersonaDTO persona) {
        if (persona == null || persona.getIdPersona() == null) {
            limpiar();
            return;
        }
        this.idPersonaObjetivo = persona.getIdPersona();
        this.matriculaObjetivo = persona.getUsuario();
        this.nombreObjetivo = construirNombre(persona);
        this.vistaGestor = true;
        limpiarContextoAsistente();
    }

    public void limpiar() {
        this.idPersonaObjetivo = null;
        this.matriculaObjetivo = null;
        this.nombreObjetivo = null;
        this.vistaGestor = false;
        limpiarContextoAsistente();
    }

    public void configurarContextoAsistenteDesdeHistorial() {
        this.origenContextoAsistente = "HISTORIAL";
        this.claveUdContexto = null;
        this.nombreUdContexto = null;
        this.estadoUdContexto = null;
        this.tipoUdContexto = null;
        this.motivoUdContexto = "Consulta de impacto en inscripción a partir del historial académico.";
    }

    public void configurarContextoAsistenteDesdeMalla(String clave, String nombre, String estado, String tipo, String motivo) {
        this.origenContextoAsistente = "MALLA";
        this.claveUdContexto = StringUtils.trimToNull(clave);
        this.nombreUdContexto = StringUtils.trimToNull(nombre);
        this.estadoUdContexto = StringUtils.trimToNull(estado);
        this.tipoUdContexto = StringUtils.trimToNull(tipo);
        this.motivoUdContexto = StringUtils.trimToNull(motivo);
    }

    public void limpiarContextoAsistente() {
        this.origenContextoAsistente = null;
        this.claveUdContexto = null;
        this.nombreUdContexto = null;
        this.estadoUdContexto = null;
        this.tipoUdContexto = null;
        this.motivoUdContexto = null;
    }

    public boolean tieneContextoAsistente() {
        return StringUtils.isNotBlank(origenContextoAsistente)
                || StringUtils.isNotBlank(claveUdContexto)
                || StringUtils.isNotBlank(nombreUdContexto)
                || StringUtils.isNotBlank(motivoUdContexto);
    }

    public boolean tienePersonaObjetivo() {
        return idPersonaObjetivo != null;
    }

    public Long resolverIdPersonaObjetivo(Long idPersonaSesion) {
        return idPersonaObjetivo != null ? idPersonaObjetivo : idPersonaSesion;
    }

    public String resolverMatriculaObjetivo(String matriculaSesion) {
        return StringUtils.defaultIfBlank(matriculaObjetivo, matriculaSesion);
    }

    public String resolverNombreObjetivo(String nombreSesion) {
        return StringUtils.defaultIfBlank(nombreObjetivo, nombreSesion);
    }

    private String construirNombre(PersonaDTO persona) {
        StringBuilder nombre = new StringBuilder();
        if (StringUtils.isNotBlank(persona.getNombre())) {
            nombre.append(persona.getNombre().trim());
        }
        if (StringUtils.isNotBlank(persona.getApellidoPaterno())) {
            if (nombre.length() > 0) {
                nombre.append(' ');
            }
            nombre.append(persona.getApellidoPaterno().trim());
        }
        if (StringUtils.isNotBlank(persona.getApellidoMaterno())) {
            if (nombre.length() > 0) {
                nombre.append(' ');
            }
            nombre.append(persona.getApellidoMaterno().trim());
        }
        if (nombre.length() == 0 && StringUtils.isNotBlank(persona.getUsuario())) {
            return persona.getUsuario().trim();
        }
        return nombre.toString();
    }

    public Long getIdPersonaObjetivo() {
        return idPersonaObjetivo;
    }

    public void setIdPersonaObjetivo(Long idPersonaObjetivo) {
        this.idPersonaObjetivo = idPersonaObjetivo;
    }

    public String getMatriculaObjetivo() {
        return matriculaObjetivo;
    }

    public void setMatriculaObjetivo(String matriculaObjetivo) {
        this.matriculaObjetivo = matriculaObjetivo;
    }

    public String getNombreObjetivo() {
        return nombreObjetivo;
    }

    public void setNombreObjetivo(String nombreObjetivo) {
        this.nombreObjetivo = nombreObjetivo;
    }

    public boolean isVistaGestor() {
        return vistaGestor;
    }

    public void setVistaGestor(boolean vistaGestor) {
        this.vistaGestor = vistaGestor;
    }

    public String getOrigenContextoAsistente() {
        return origenContextoAsistente;
    }

    public void setOrigenContextoAsistente(String origenContextoAsistente) {
        this.origenContextoAsistente = origenContextoAsistente;
    }

    public String getClaveUdContexto() {
        return claveUdContexto;
    }

    public void setClaveUdContexto(String claveUdContexto) {
        this.claveUdContexto = claveUdContexto;
    }

    public String getNombreUdContexto() {
        return nombreUdContexto;
    }

    public void setNombreUdContexto(String nombreUdContexto) {
        this.nombreUdContexto = nombreUdContexto;
    }

    public String getEstadoUdContexto() {
        return estadoUdContexto;
    }

    public void setEstadoUdContexto(String estadoUdContexto) {
        this.estadoUdContexto = estadoUdContexto;
    }

    public String getTipoUdContexto() {
        return tipoUdContexto;
    }

    public void setTipoUdContexto(String tipoUdContexto) {
        this.tipoUdContexto = tipoUdContexto;
    }

    public String getMotivoUdContexto() {
        return motivoUdContexto;
    }

    public void setMotivoUdContexto(String motivoUdContexto) {
        this.motivoUdContexto = motivoUdContexto;
    }
}
