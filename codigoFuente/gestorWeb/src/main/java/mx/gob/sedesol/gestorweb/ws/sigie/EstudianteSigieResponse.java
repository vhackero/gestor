package mx.gob.sedesol.gestorweb.ws.sigie;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "matricula", "nombre", "primer_apellido", "segundo_apellido", "curp", "programa",
        "avance", "situacion_escolar", "estatus", "correo_institucional", "fecha_hora_consulta",
        "folio_consulta" })
public class EstudianteSigieResponse {
    private String matricula;
    private String nombre;
    @JsonProperty("primer_apellido") private String primerApellido;
    @JsonProperty("segundo_apellido") private String segundoApellido;
    private String curp;
    private Programa programa;
    private Avance avance;
    @JsonProperty("situacion_escolar") private Catalogo situacionEscolar;
    private Catalogo estatus;
    @JsonProperty("correo_institucional") private String correoInstitucional;
    @JsonProperty("fecha_hora_consulta") private String fechaHoraConsulta;
    @JsonProperty("folio_consulta") private String folioConsulta;

    @JsonPropertyOrder({ "clave", "nombre", "nivel", "plan_estudios" })
    public static class Programa {
        private String clave;
        private String nombre;
        private String nivel;
        @JsonProperty("plan_estudios") private String planEstudios;
        public String getClave() { return clave; }
        public void setClave(String clave) { this.clave = clave; }
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public String getNivel() { return nivel; }
        public void setNivel(String nivel) { this.nivel = nivel; }
        public String getPlanEstudios() { return planEstudios; }
        public void setPlanEstudios(String planEstudios) { this.planEstudios = planEstudios; }
    }
    @JsonPropertyOrder({ "creditos_totales", "creditos_cubiertos", "porcentaje_cubierto" })
    public static class Avance {
        @JsonProperty("creditos_totales") private BigDecimal creditosTotales;
        @JsonProperty("creditos_cubiertos") private BigDecimal creditosCubiertos;
        @JsonProperty("porcentaje_cubierto") private BigDecimal porcentajeCubierto;
        public BigDecimal getCreditosTotales() { return creditosTotales; }
        public void setCreditosTotales(BigDecimal creditosTotales) { this.creditosTotales = creditosTotales; }
        public BigDecimal getCreditosCubiertos() { return creditosCubiertos; }
        public void setCreditosCubiertos(BigDecimal creditosCubiertos) { this.creditosCubiertos = creditosCubiertos; }
        public BigDecimal getPorcentajeCubierto() { return porcentajeCubierto; }
        public void setPorcentajeCubierto(BigDecimal porcentajeCubierto) { this.porcentajeCubierto = porcentajeCubierto; }
    }
    @JsonPropertyOrder({ "clave", "descripcion" })
    public static class Catalogo {
        private final String clave;
        private final String descripcion;
        public Catalogo(String clave, String descripcion) { this.clave = clave; this.descripcion = descripcion; }
        public String getClave() { return clave; }
        public String getDescripcion() { return descripcion; }
    }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getPrimerApellido() { return primerApellido; }
    public void setPrimerApellido(String primerApellido) { this.primerApellido = primerApellido; }
    public String getSegundoApellido() { return segundoApellido; }
    public void setSegundoApellido(String segundoApellido) { this.segundoApellido = segundoApellido; }
    public String getCurp() { return curp; }
    public void setCurp(String curp) { this.curp = curp; }
    public Programa getPrograma() { return programa; }
    public void setPrograma(Programa programa) { this.programa = programa; }
    public Avance getAvance() { return avance; }
    public void setAvance(Avance avance) { this.avance = avance; }
    public Catalogo getSituacionEscolar() { return situacionEscolar; }
    public void setSituacionEscolar(Catalogo situacionEscolar) { this.situacionEscolar = situacionEscolar; }
    public Catalogo getEstatus() { return estatus; }
    public void setEstatus(Catalogo estatus) { this.estatus = estatus; }
    public String getCorreoInstitucional() { return correoInstitucional; }
    public void setCorreoInstitucional(String correoInstitucional) { this.correoInstitucional = correoInstitucional; }
    public String getFechaHoraConsulta() { return fechaHoraConsulta; }
    public void setFechaHoraConsulta(String fechaHoraConsulta) { this.fechaHoraConsulta = fechaHoraConsulta; }
    public String getFolioConsulta() { return folioConsulta; }
    public void setFolioConsulta(String folioConsulta) { this.folioConsulta = folioConsulta; }
}
