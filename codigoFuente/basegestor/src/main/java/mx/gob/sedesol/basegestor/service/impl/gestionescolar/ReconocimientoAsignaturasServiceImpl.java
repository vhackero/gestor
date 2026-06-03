package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.GrupoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ReconocimientoAsignaturaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelGrupoParticipanteDTO;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.GrupoParticipanteRepo;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.IReconocimientoAsignaturasRepository;
import mx.gob.sedesol.basegestor.service.gestionescolar.GrupoParticipanteService;
import mx.gob.sedesol.basegestor.service.gestionescolar.ReconocimientoAsignaturasService;

@Service("reconocimientoAsignaturasService")
public class ReconocimientoAsignaturasServiceImpl implements ReconocimientoAsignaturasService {

	@Autowired
	private IReconocimientoAsignaturasRepository reconocimientoAsignaturasRepository;

	@Autowired
	private GrupoParticipanteService grupoParticipanteService;

	@Autowired
	private GrupoParticipanteRepo grupoParticipanteRepo;

	@Override
	@Transactional(readOnly = true)
	public List<ReconocimientoAsignaturaDTO> buscarPorMatriculaYPeriodo(String matricula, String periodo) {
		if (matricula == null || matricula.trim().isEmpty() || periodo == null || periodo.trim().isEmpty()) {
			return Collections.emptyList();
		}
		return reconocimientoAsignaturasRepository.buscarPorMatriculaYPeriodo(matricula.trim(), periodo.trim());
	}

	@Override
	@Transactional
	public ResultadoDTO<RelGrupoParticipanteDTO> eliminarRegistro(ReconocimientoAsignaturaDTO registro,
			Long usuarioModifico) {
		RelGrupoParticipanteDTO participante = new RelGrupoParticipanteDTO();
		participante.setId(registro.getId().intValue());
		participante.setUsuarioModifico(usuarioModifico);
		participante.setIdPersonaLms(
				registro.getIdUsuarioMoodle() != null ? registro.getIdUsuarioMoodle().intValue() : null);

		PersonaDTO persona = new PersonaDTO();
		persona.setIdPersona(registro.getIdPersonaParticipante());
		persona.setUsuario(registro.getMatricula());
		participante.setPersona(persona);

		GrupoDTO grupo = new GrupoDTO();
		grupo.setIdGrupo(registro.getIdGrupo() != null ? registro.getIdGrupo().intValue() : null);
		grupo.setIdMoodle(registro.getIdGrupoMoodle() != null ? registro.getIdGrupoMoodle().intValue() : null);
		participante.setGrupo(grupo);

		EventoCapacitacionDTO evento = new EventoCapacitacionDTO();
		evento.setIdEvento(registro.getIdEvento() != null ? registro.getIdEvento().intValue() : null);
		evento.setIdCursoLmsBorrador(registro.getIdCursoMoodle());
		evento.setIdPlataformaLmsBorrador(registro.getIdPlataformaMoodle());
		evento.getCatModalidadPlanPrograma().setId(registro.getIdModalidad());

		return grupoParticipanteService.eliminarParticipante(participante, evento);
	}

	@Override
	@Transactional
	public boolean actualizarRegistro(Long idRegistro) {
		if (idRegistro == null) {
			return false;
		}
		return grupoParticipanteRepo.actualizarReconocimientoAsignatura(idRegistro.intValue()) > 0;
	}
}
