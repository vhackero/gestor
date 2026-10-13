package mx.gob.sedesol.basegestor.model.repositories.admin;

import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaExternaCargaDTO;

@Repository
public class PersonaExternaCargaRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public Set<String> usuariosExistentes(Collection<String> usuarios) {
        Set<String> resultado = new HashSet<>();
        if (usuarios.isEmpty()) return resultado;
        List<String> nombres = new ArrayList<>(usuarios);
        for (int inicio=0; inicio<nombres.size(); inicio+=500) {
            List<?> filas = entityManager.createNativeQuery("SELECT LOWER(TRIM(nombre_usuario)) AS usuario "
                + "FROM tbl_persona_externos WHERE nombre_usuario_normalizado IN (:usuarios)")
                .setParameter("usuarios", nombres.subList(inicio,Math.min(inicio+500,nombres.size()))).getResultList();
            for (Object fila : filas) resultado.add(fila.toString());
        }
        return resultado;
    }

    public Set<Integer> planes() { return enteros("SELECT id_plan FROM tbl_planes"); }
    public Set<Integer> fuentes() { return enteros("SELECT id_fuente_externa FROM cat_fuentes_externas WHERE activo=1"); }
    public Set<Integer> convocatorias() { return enteros("SELECT convocatoria_id FROM tbl_convocatoria WHERE activo=1"); }
    public Set<String> relacionesPlanConvocatoria() {
        Set<String> resultado = new HashSet<>();
        List<?> filas = entityManager.createNativeQuery("SELECT DISTINCT CONCAT(id_plan, ':', id_convocatoria) "
            + "AS relacion FROM rel_convocatoria_planesyprogramas").getResultList();
        for (Object fila : filas) resultado.add(fila.toString());
        return resultado;
    }
    private Set<Integer> enteros(String sql) {
        Set<Integer> resultado = new HashSet<>();
        for (Object fila : entityManager.createNativeQuery(sql).getResultList()) resultado.add(((Number)fila).intValue());
        return resultado;
    }

    @SuppressWarnings("unchecked")
    public List<Object[]> catalogosPlantilla() {
        return entityManager.createNativeQuery("SELECT DISTINCT p.id_plan AS id_plan, p.nombre AS nombre_plan, "
            + "c.convocatoria_id AS id_convocatoria, c.nombre AS nombre_convocatoria, "
            + "f.id_fuente_externa AS id_fuente, f.nombre AS nombre_fuente "
            + "FROM rel_convocatoria_planesyprogramas r JOIN tbl_planes p ON p.id_plan=r.id_plan "
            + "JOIN tbl_convocatoria c ON c.convocatoria_id=r.id_convocatoria AND c.activo=1 "
            + "CROSS JOIN cat_fuentes_externas f WHERE f.activo=1 ORDER BY p.id_plan,c.convocatoria_id,f.id_fuente_externa")
            .getResultList();
    }

    public void insertar(PersonaExternaCargaDTO fila) {
        insertarLote(Collections.singletonList(fila));
    }

    public void insertarLote(List<PersonaExternaCargaDTO> filas) {
        for (int inicio=0; inicio<filas.size(); inicio+=250) {
            insertarBloque(filas.subList(inicio,Math.min(inicio+250,filas.size())));
        }
    }

    private void insertarBloque(List<PersonaExternaCargaDTO> filas) {
        String columnas = String.join(",", PersonaExternaCargaDTO.COLUMNAS);
        List<String> bloques = new ArrayList<>();
        for (int n=0;n<filas.size();n++) {
            List<String> parametros = new ArrayList<>();
            for (String columna : PersonaExternaCargaDTO.COLUMNAS) parametros.add(":" + columna + "_" + n);
            bloques.add("("+String.join(",",parametros)+")");
        }
        Query insert = entityManager.createNativeQuery("INSERT INTO tbl_persona_externos (" + columnas + ") VALUES "
            + String.join(",",bloques));
        for (int n=0;n<filas.size();n++) {
        PersonaExternaCargaDTO fila = filas.get(n);
        for (String columna : PersonaExternaCargaDTO.COLUMNAS) {
            String valor = fila.get(columna);
            Object dato = columna.equals("password") ? valor : valor.trim();
            if (columna.startsWith("id_") || columna.equals("tipo_usuario")) dato = Integer.valueOf(valor.trim());
            if (columna.equals("fecha_nacimiento")) dato = java.sql.Date.valueOf(valor.trim());
            if (columna.equals("curp")) dato = valor.trim().toUpperCase(Locale.ROOT);
            insert.setParameter(columna + "_" + n, dato);
        }
        }
        insert.executeUpdate();
    }
}
