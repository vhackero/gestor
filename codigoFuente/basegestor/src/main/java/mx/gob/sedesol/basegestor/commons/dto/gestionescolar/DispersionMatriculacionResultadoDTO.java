package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;

public class DispersionMatriculacionResultadoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int gruposProcesados;
	private int participantesDetectados;
	private int participantesMatriculados;
	private int participantesSinCupo;

	public int getGruposProcesados() {
		return gruposProcesados;
	}

	public void setGruposProcesados(int gruposProcesados) {
		this.gruposProcesados = gruposProcesados;
	}

	public int getParticipantesDetectados() {
		return participantesDetectados;
	}

	public void setParticipantesDetectados(int participantesDetectados) {
		this.participantesDetectados = participantesDetectados;
	}

	public int getParticipantesMatriculados() {
		return participantesMatriculados;
	}

	public void setParticipantesMatriculados(int participantesMatriculados) {
		this.participantesMatriculados = participantesMatriculados;
	}

	public int getParticipantesSinCupo() {
		return participantesSinCupo;
	}

	public void setParticipantesSinCupo(int participantesSinCupo) {
		this.participantesSinCupo = participantesSinCupo;
	}

}
