package mx.gob.sedesol.basegestor.service.admin;
import java.util.List;
import mx.gob.sedesol.basegestor.commons.dto.admin.AsignacionPlanUsuarioDTO;
public interface AsignacionPlanUsuarioService {
    List<Object[]> planes();
    List<Object[]> convocatorias(Integer plan);
    List<AsignacionPlanUsuarioDTO> buscar(String usuarios);
    int asignar(String usuarios,Integer plan,Integer convocatoria,Long actor);
    int modificar(List<AsignacionPlanUsuarioDTO> seleccion,Integer plan,Integer convocatoria,Long actor);
    void eliminarAsignacion(AsignacionPlanUsuarioDTO asignacion,Long actor);
}
