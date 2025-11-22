package mx.gob.sedesol.basegestor.commons.utils;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasReprobadasDTO;


public class InscripcionUtils {
	public static boolean esMateriaElectiva(String tipo) {
		return tipo.equalsIgnoreCase(ConstantesGestor.TEXTO_MATERIA_ELECTIVA);
	}
	
	public static boolean esMateriaObligatoria(String tipo) {
		return tipo.equalsIgnoreCase(ConstantesGestor.TEXTO_MATERIA_OBLIGATORIA);
	}
	
	public static boolean esMateriaOptativa(String tipo) {
		return tipo.equalsIgnoreCase(ConstantesGestor.TEXTO_MATERIA_OPTATIVA);
	}
	
	public static boolean esSemestreValidoMateriaElectiva(String semestre) {
		int numeroSemestre = obtenerNumeroSemestre(semestre);
		return numeroSemestre >= ConstantesGestor.SEMESTRE_MINIMO_PARA_ELECTIVAS;
	}
	
	public static int obtenerNumeroSemestre(String semestre) {
		String[] partes = semestre.split(" ");
		int numeroSemestre = Integer.parseInt(partes[partes.length - 1]);
		return numeroSemestre;
	}
	
	public static boolean esSemestrePar(int numeroSemestreAprobado) {
		return numeroSemestreAprobado % 2 == 0;
	}
	
	public static String obtenerClaveUnicaPrograma(InscripcionMateriasReprobadasDTO mpi) {
		return mpi.getClavePrograma() + ":" + mpi.getIdPrograma();
	}
	
	public static String claveUnicaPrograma(InscripcionMateriasDTO mpi) {
		return mpi.getClavePrograma() + ":" + mpi.getIdPrograma();
	}
	
	public static boolean sonMateriasDelMismoSemestre(InscripcionMateriasDTO materia1, InscripcionMateriasDTO materia2) {
		return materia1.getEstructura().equalsIgnoreCase(materia2.getEstructura());
	}
	
	
}
