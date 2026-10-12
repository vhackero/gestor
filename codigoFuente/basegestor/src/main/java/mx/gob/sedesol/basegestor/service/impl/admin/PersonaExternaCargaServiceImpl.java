package mx.gob.sedesol.basegestor.service.impl.admin;

import java.time.LocalDate;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import mx.gob.sedesol.basegestor.commons.dto.admin.*;
import mx.gob.sedesol.basegestor.model.repositories.admin.PersonaExternaCargaRepository;
import mx.gob.sedesol.basegestor.service.admin.PersonaExternaCargaService;

@Service("personaExternaCargaService")
public class PersonaExternaCargaServiceImpl implements PersonaExternaCargaService {
    @Autowired
    private PersonaExternaCargaRepository repository;

    @Override
    @Transactional(rollbackFor=Exception.class)
    @PreAuthorize("hasPermission(null, 'IMP_FUEN_EXT')")
    public ResultadoCargaExternaDTO importar(List<PersonaExternaCargaDTO> filas) {
        ResultadoCargaExternaDTO resultado = new ResultadoCargaExternaDTO();
        if (filas == null || filas.isEmpty() || filas.size() > 15000) {
            resultado.agregarError("El archivo debe contener entre 1 y 15000 registros.");
            return resultado;
        }
        Set<Integer> planes = repository.planes(), fuentes = repository.fuentes(), convocatorias = repository.convocatorias();
        Set<String> relaciones = repository.relacionesPlanConvocatoria();
        Map<String,Integer> usuarios = new LinkedHashMap<>();
        for (PersonaExternaCargaDTO fila : filas) {
            String prefijo = "Fila " + fila.getFila() + ": ";
            for (String columna : PersonaExternaCargaDTO.COLUMNAS) {
                String valor = fila.get(columna);
                if (valor == null || valor.trim().isEmpty()) resultado.agregarError(prefijo + columna + " es obligatorio.");
                else if (valor.length() > (columna.equals("curp") ? 18 : 255)) resultado.agregarError(prefijo + columna + " supera la longitud permitida.");
                if (valor != null && (valor.indexOf('\uFFFD') >= 0 || valor.codePoints().anyMatch(c -> c > 0xFFFF)))
                    resultado.agregarError(prefijo + columna + " contiene caracteres no admitidos por la tabla.");
            }
            String usuario = valor(fila,"nombre_usuario");
            if (!usuario.matches("[A-Za-z0-9_.@-]{1,100}")) resultado.agregarError(prefijo + "nombre_usuario debe tener hasta 100 caracteres, sin espacios ni acentos.");
            String clave = usuario.toLowerCase(Locale.ROOT);
            Integer primeraFila = usuarios.putIfAbsent(clave,fila.getFila());
            if (primeraFila != null) resultado.agregarError(prefijo + "el usuario " + usuario + " se repite en la fila " + primeraFila + ".");
            if (!valor(fila,"correo_institucional").matches("[^\\s@]+@[^\\s@]+\\.[^\\s@]+")) resultado.agregarError(prefijo + "correo_institucional no es válido.");
            if (!valor(fila,"curp").toUpperCase(Locale.ROOT).matches("[A-Z]{4}[0-9]{6}[HM][A-Z]{5}[A-Z0-9][0-9]")) resultado.agregarError(prefijo + "curp debe tener 18 caracteres con formato válido.");
            try {
                String fechaTexto = valor(fila,"fecha_nacimiento");
                if (!fechaTexto.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")) throw new IllegalArgumentException();
                LocalDate fecha = LocalDate.parse(fechaTexto);
                if (fecha.isAfter(LocalDate.now()) || fecha.getYear() < 1000) throw new IllegalArgumentException();
            } catch (RuntimeException e) { resultado.agregarError(prefijo + "fecha_nacimiento debe ser una fecha válida no futura en formato AAAA-MM-DD."); }
            Integer plan = entero(fila,"id_plan"), fuente = entero(fila,"id_fuente_externa"), convocatoria = entero(fila,"id_convocatoria"), tipo = entero(fila,"tipo_usuario");
            if (!planes.contains(plan)) resultado.agregarError(prefijo + "id_plan no existe.");
            if (!fuentes.contains(fuente)) resultado.agregarError(prefijo + "id_fuente_externa no existe o está inactivo.");
            if (!convocatorias.contains(convocatoria)) resultado.agregarError(prefijo + "id_convocatoria no existe o está inactiva.");
            if (plan != null && convocatoria != null && !relaciones.contains(plan+":"+convocatoria)) resultado.agregarError(prefijo + "el plan no pertenece a la convocatoria indicada.");
            if (tipo == null || (tipo != 1 && tipo != 2)) resultado.agregarError(prefijo + "tipo_usuario debe ser 1 (Interno) o 2 (Externo).");
        }
        if (!resultado.getErrores().isEmpty()) return resultado;
        Set<String> existentes = repository.usuariosExistentes(usuarios.keySet());
        for (String existente : existentes) resultado.agregarError("Fila " + usuarios.get(existente) + ": el usuario " + existente + " ya existe en la fuente externa.");
        if (!resultado.getErrores().isEmpty()) return resultado;
        repository.insertarLote(filas);
        resultado.setInsertados(filas.size());
        return resultado;
    }
    private String valor(PersonaExternaCargaDTO fila,String columna) { String v=fila.get(columna); return v==null ? "" : v.trim(); }
    private Integer entero(PersonaExternaCargaDTO fila,String columna) {
        try { String v=valor(fila,columna); return v.matches("[0-9]+") ? Integer.valueOf(v) : null; }
        catch (NumberFormatException e) { return null; }
    }
    @Override
    @PreAuthorize("hasPermission(null, 'IMP_FUEN_EXT')")
    public List<Object[]> catalogosPlantilla() { return repository.catalogosPlantilla(); }
}
