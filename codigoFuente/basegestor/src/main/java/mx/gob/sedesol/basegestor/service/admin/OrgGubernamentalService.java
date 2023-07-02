package mx.gob.sedesol.basegestor.service.admin;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.OrgGubernamentalDTO;

public interface OrgGubernamentalService extends CommonService<OrgGubernamentalDTO, Integer> {

	public OrgGubernamentalDTO obtenerOrgGubernamentalPorId(Integer id);
	public List<OrgGubernamentalDTO> obtenerOrgGubsPadres();
}
