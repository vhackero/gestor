package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

public class ConfiguracionOportunidadesDTO {
    private boolean aplicarLimite = true;
    public boolean isAplicarLimite() { return aplicarLimite; }
    public void setAplicarLimite(boolean v) { aplicarLimite=v; }
    private boolean permiteContinuar;
    private int limite;
    private String mensajeUltima;
    private String mensajeAgotada;
    private int limiteAcumulado = 3;
    private boolean aplicarLimiteSustituciones = true;
    private int limiteSustituciones = 2;
    private String mensajeUltimaAcumulada = "La inscripción de {unidad} utilizará la última oportunidad del adeudo {adeudo}: {consumidas} consumidas de {limite}.";
    private String mensajeAgotadaAcumulada = "No puedes regularizar {adeudo}: agotaste sus {limite} oportunidades acumuladas.";
    private String mensajeSustituciones = "El adeudo {adeudo} alcanzó el límite de {max_sustituciones} sustituciones.";
    public int getLimiteAcumulado() { return limiteAcumulado; }
    public void setLimiteAcumulado(int v) { limiteAcumulado=v; }
    public boolean isAplicarLimiteSustituciones() { return aplicarLimiteSustituciones; }
    public void setAplicarLimiteSustituciones(boolean v) { aplicarLimiteSustituciones=v; }
    public int getLimiteSustituciones() { return limiteSustituciones; }
    public void setLimiteSustituciones(int v) { limiteSustituciones=v; }
    public String getMensajeUltimaAcumulada() { return mensajeUltimaAcumulada; }
    public void setMensajeUltimaAcumulada(String v) { mensajeUltimaAcumulada=v; }
    public String getMensajeAgotadaAcumulada() { return mensajeAgotadaAcumulada; }
    public void setMensajeAgotadaAcumulada(String v) { mensajeAgotadaAcumulada=v; }
    public String getMensajeSustituciones() { return mensajeSustituciones; }
    public void setMensajeSustituciones(String v) { mensajeSustituciones=v; }
    public boolean isPermiteContinuar() { return permiteContinuar; }
    public void setPermiteContinuar(boolean v) { permiteContinuar=v; }
    public int getLimite() { return limite; }
    public void setLimite(int v) { limite=v; }
    public String getMensajeUltima() { return mensajeUltima; }
    public void setMensajeUltima(String v) { mensajeUltima=v; }
    public String getMensajeAgotada() { return mensajeAgotada; }
    public void setMensajeAgotada(String v) { mensajeAgotada=v; }
    public void validar() {
        if(limite<1 || limite>3)throw new IllegalArgumentException("El límite de oportunidades debe estar entre 1 y 3.");
        if(limiteAcumulado<1 || limiteAcumulado>3)throw new IllegalArgumentException("El límite acumulado debe estar entre 1 y 3.");
        if(limiteSustituciones<0 || limiteSustituciones>2)throw new IllegalArgumentException("El límite de sustituciones debe estar entre 0 y 2.");
        for(String s:new String[]{mensajeUltima,mensajeAgotada}) {
            validarMensaje(s,"{unidad}");
        }
        for(String s:new String[]{mensajeUltimaAcumulada,mensajeAgotadaAcumulada,mensajeSustituciones})validarMensaje(s,"{adeudo}");
    }
    private void validarMensaje(String s,String requerida) {
        if(s==null || s.trim().isEmpty() || s.length()>1000 || !s.contains(requerida))throw new IllegalArgumentException("Cada mensaje requiere "+requerida+" y de 1 a 1000 caracteres.");
        String resto=s;
        String[] tokens="{unidad}".equals(requerida)?new String[]{"unidad","consumidas","disponibles","limite"}:new String[]{"unidad","adeudo","consumidas","disponibles","limite","sustituciones","max_sustituciones"};
        for(String token:tokens)resto=resto.replace("{"+token+"}","");
        if(resto.contains("{") || resto.contains("}"))throw new IllegalArgumentException("El mensaje contiene una variable no admitida.");
    }
    public String mensaje(String plantilla,OportunidadesUnidadDTO d) {
        return plantilla.replace("{unidad}",d.getNombre()).replace("{consumidas}",String.valueOf(d.getConsumidas()))
            .replace("{disponibles}",String.valueOf(d.getDisponibles())).replace("{limite}",String.valueOf(limite));
    }
    public String mensajeAdeudo(String plantilla,AdeudoOptativaDTO d,String unidad) {
        String nombre=java.util.Objects.toString(d.getNombrePrograma(),"UD "+d.getIdPrograma());
        return plantilla.replace("{unidad}",unidad==null?nombre:unidad).replace("{adeudo}",nombre)
            .replace("{consumidas}",String.valueOf(d.getIntentosReprobados())).replace("{disponibles}",String.valueOf(d.getOportunidadesDisponibles()))
            .replace("{limite}",String.valueOf(d.getLimiteAcumulado())).replace("{sustituciones}",String.valueOf(d.getSustituciones()))
            .replace("{max_sustituciones}",String.valueOf(d.getLimiteSustituciones()));
    }
}
