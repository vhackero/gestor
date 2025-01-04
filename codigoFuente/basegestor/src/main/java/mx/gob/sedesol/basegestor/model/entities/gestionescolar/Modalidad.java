package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

public class Modalidad {
    private String nombreNivelEnsenanza; // Nivel educativo (e.g., Primaria, Secundaria)
    private String nombrePlan;          // Nombre del plan (e.g., Bachillerato Técnico)
    private String nombrePrograma;      // Nombre del programa (e.g., Informática)

    // Constructor vacío
    public Modalidad() {}

    // Constructor con parámetros
    public Modalidad(String nombreNivelEnsenanza, String nombrePlan, String nombrePrograma) {
        this.nombreNivelEnsenanza = nombreNivelEnsenanza;
        this.nombrePlan = nombrePlan;
        this.nombrePrograma = nombrePrograma;
    }

    // Getters y Setters
    public String getNombreNivelEnsenanza() {
        return nombreNivelEnsenanza;
    }

    public void setNombreNivelEnsenanza(String nombreNivelEnsenanza) {
        this.nombreNivelEnsenanza = nombreNivelEnsenanza;
    }

    public String getNombrePlan() {
        return nombrePlan;
    }

    public void setNombrePlan(String nombrePlan) {
        this.nombrePlan = nombrePlan;
    }

    public String getNombrePrograma() {
        return nombrePrograma;
    }

    public void setNombrePrograma(String nombrePrograma) {
        this.nombrePrograma = nombrePrograma;
    }

    @Override
    public String toString() {
        return nombreNivelEnsenanza + " > " + nombrePlan + " > " + nombrePrograma;
    }
}
