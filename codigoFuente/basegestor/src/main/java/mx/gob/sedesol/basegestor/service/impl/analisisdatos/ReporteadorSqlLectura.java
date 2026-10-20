package mx.gob.sedesol.basegestor.service.impl.analisisdatos;

import java.util.regex.Pattern;

/** Validación y máscara léxica compartida por SQL y parámetros enlazados. */
final class ReporteadorSqlLectura {
    private static final Pattern INICIO = Pattern.compile("(?is)^(select|with)\\b.*");
    private static final Pattern ESCRITURA = Pattern.compile("(?i)\\b(insert|update|delete|drop|alter|truncate|create|replace|merge|call|grant|revoke|commit|rollback|set|do|load|handler|flush|lock|unlock|analyze|analyse|optimize|repair|rename|install|uninstall|start|begin|savepoint|release|into|outfile|dumpfile)\\b|:=|\\bfor\\s+update\\b|\\block\\s+in\\s+share\\s+mode\\b|\\b(load_file|sleep|benchmark|get_lock|release_lock|release_all_locks|sys_exec|sys_eval|last_insert_id)\\s*\\(");
    static void validar(String sql) {
        String texto = codigo(sql).trim();
        if (texto.endsWith(";")) texto = texto.substring(0, texto.length()-1).trim();
        // Los nombres de parámetros no son instrucciones SQL.
        texto = texto.replaceAll(":[A-Za-z][A-Za-z0-9_]*", "?");
        if (!INICIO.matcher(texto).matches() || texto.contains(";") || ESCRITURA.matcher(texto).find())
            throw new IllegalArgumentException("El reporteador sólo permite una consulta de lectura SELECT o WITH ... SELECT, sin instrucciones de modificación, escritura de archivos ni bloqueos.");
    }
    static String codigo(String sql) {
        if (sql == null) return "";
        char[] salida = sql.toCharArray();
        for (int i=0; i<salida.length;) {
            char c=sql.charAt(i);
            if (c=='\'' || c=='"' || c=='`') {
                char comilla=c; int inicio=i++; boolean cerrada=false;
                while(i<salida.length) {
                    char actual=sql.charAt(i++);
                    if(actual=='\\' && i<salida.length) { i++; continue; }
                    if(actual==comilla) {
                        if(i<salida.length && sql.charAt(i)==comilla) { i++; continue; }
                        cerrada=true; break;
                    }
                }
                if(!cerrada) throw new IllegalArgumentException("Cadena o identificador SQL sin cerrar.");
                // Identificadores citados se inspeccionan para detectar funciones peligrosas.
                if(comilla=='`') { salida[inicio]=' '; salida[i-1]=' '; }
                else for(int j=inicio;j<i;j++) salida[j]=' ';
            } else if(c=='#' || (c=='-' && i+2<salida.length && sql.charAt(i+1)=='-' && Character.isWhitespace(sql.charAt(i+2)))) {
                while(i<salida.length && sql.charAt(i)!='\n') salida[i++]=' ';
            } else if(c=='/' && i+1<salida.length && sql.charAt(i+1)=='*') {
                if(i+2<salida.length && (sql.charAt(i+2)=='!' || sql.charAt(i+2)=='+')
                    || i+3<salida.length && sql.charAt(i+2)=='M' && sql.charAt(i+3)=='!')
                    throw new IllegalArgumentException("No se permiten comentarios ejecutables ni directivas SQL.");
                int fin=sql.indexOf("*/",i+2);
                if(fin<0) throw new IllegalArgumentException("Comentario SQL sin cerrar.");
                while(i<fin+2) salida[i++]=' ';
            } else i++;
        }
        return new String(salida);
    }
    private ReporteadorSqlLectura() { }
}
