package mx.gob.sedesol.basegestor.model.especificaciones;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.UsuarioDatosLaboralesDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.model.entities.admin.RelPersonaCorreo_;
import mx.gob.sedesol.basegestor.model.entities.admin.RelPersonaTelefono_;
import mx.gob.sedesol.basegestor.model.entities.admin.RelUsuarioDatosLaborales;
import mx.gob.sedesol.basegestor.model.entities.admin.RelUsuarioDatosLaborales_;
import mx.gob.sedesol.basegestor.model.entities.admin.TblPersona_;

public class DatosLaboralesEspecificacion implements Specification<RelUsuarioDatosLaborales> {

	private UsuarioDatosLaboralesDTO criterios;

	public DatosLaboralesEspecificacion(PersonaDTO criterios) {
		this.criterios = new UsuarioDatosLaboralesDTO();
		this.criterios.setPersona(criterios);
	}

	public DatosLaboralesEspecificacion(UsuarioDatosLaboralesDTO criterios) {
		this.criterios = criterios;
	}

	@Override
	public Predicate toPredicate(Root<RelUsuarioDatosLaborales> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

		List<Predicate> predicates = new ArrayList<>();

		if (ObjectUtils.isNotNull(criterios)) {

			if (!ObjectUtils.isNullOrEmpty(criterios.getPersona().getNombre())) {
				predicates.add(cb.like(root.get(RelUsuarioDatosLaborales_.persona).get(TblPersona_.nombre),
						"%" + criterios.getPersona().getNombre() + "%"));
			}

			if (!ObjectUtils.isNullOrEmpty(criterios.getPersona().getApellidoPaterno())) {
				predicates.add(cb.like(root.get(RelUsuarioDatosLaborales_.persona).get(TblPersona_.apellidoPaterno),
						"%" + criterios.getPersona().getApellidoPaterno() + "%"));
			}

			if (!ObjectUtils.isNullOrEmpty(criterios.getPersona().getApellidoMaterno())) {
				predicates.add(cb.like(root.get(RelUsuarioDatosLaborales_.persona).get(TblPersona_.apellidoMaterno),
						"%" + criterios.getPersona().getApellidoMaterno() + "%"));
			}

			if (!ObjectUtils.isNullOrEmpty(criterios.getPersona().getCurp())) {
				predicates.add(cb.like(root.get(RelUsuarioDatosLaborales_.persona).get(TblPersona_.curp),
						"%" + criterios.getPersona().getCurp() + "%"));
			}

			if (!ObjectUtils.isNullOrEmpty(criterios.getPersona().getUsuario())) {
				predicates.add(cb.like(root.get(RelUsuarioDatosLaborales_.persona).get(TblPersona_.usuario),
						"%" + criterios.getPersona().getUsuario() + "%"));
			}

			if (!ObjectUtils.isNullOrCero(criterios.getPersona().getTipoUsuario())) {
				predicates.add(cb.equal(root.get(RelUsuarioDatosLaborales_.persona).get(TblPersona_.tipoUsuario),
						criterios.getPersona().getTipoUsuario()));
			}

			if (!ObjectUtils.isNull(criterios.getPersona().getActivo())) {
				predicates.add(cb.equal(root.get(RelUsuarioDatosLaborales_.persona).get(TblPersona_.activo),
						criterios.getPersona().getActivo()));
			}
			
			if (!ObjectUtils.isNullOrEmpty(criterios.getPersona().getIdEntidadFederativa())) {
				predicates.add(cb.like(root.get(RelUsuarioDatosLaborales_.persona).get(TblPersona_.idEntidadFederativa),
						"%" + criterios.getPersona().getIdEntidadFederativa() + "%"));
			}
			
			if (!ObjectUtils.isNullOrEmpty(criterios.getPersona().getEntidadFederativa())) {
				predicates.add(cb.like(root.get(RelUsuarioDatosLaborales_.persona).get(TblPersona_.entidadFederativa),
						"%" + criterios.getPersona().getEntidadFederativa() + "%"));
			}
			
			if (!ObjectUtils.isNullOrEmpty(criterios.getPersona().getIdMunicipio())) {
				predicates.add(cb.like(root.get(RelUsuarioDatosLaborales_.persona).get(TblPersona_.idMunicipio),
						"%" + criterios.getPersona().getIdMunicipio() + "%"));
			}
			
			if (!ObjectUtils.isNullOrEmpty(criterios.getPersona().getMunicipio())) {
				predicates.add(cb.like(root.get(RelUsuarioDatosLaborales_.persona).get(TblPersona_.municipio),
						"%" + criterios.getPersona().getMunicipio() + "%"));
			}
			
			if (!ObjectUtils.isNullOrEmpty(criterios.getPersona().getIdDependencia())) {
				predicates.add(cb.like(root.get(RelUsuarioDatosLaborales_.persona).get(TblPersona_.idDependencia),
						"%" + criterios.getPersona().getIdDependencia() + "%"));
			}
			
			if (!ObjectUtils.isNullOrEmpty(criterios.getPersona().getClaveDependencia())) {
				predicates.add(cb.like(root.get(RelUsuarioDatosLaborales_.persona).get(TblPersona_.claveDependencia),
						"%" + criterios.getPersona().getClaveDependencia() + "%"));
			}
			
			if (!ObjectUtils.isNullOrEmpty(criterios.getPersona().getDependencia())) {
				predicates.add(cb.like(root.get(RelUsuarioDatosLaborales_.persona).get(TblPersona_.dependencia),
						"%" + criterios.getPersona().getDependencia() + "%"));
			}
			
			if (!ObjectUtils.isNullOrEmpty(criterios.getPersona().getIdUnidadAdministrativa())) {
				predicates.add(cb.like(root.get(RelUsuarioDatosLaborales_.persona).get(TblPersona_.idUnidadAdministrativa),
						"%" + criterios.getPersona().getIdUnidadAdministrativa() + "%"));
			}
			
			if (!ObjectUtils.isNullOrEmpty(criterios.getPersona().getClaveUnidadAdministrativa())) {
				predicates.add(cb.like(root.get(RelUsuarioDatosLaborales_.persona).get(TblPersona_.claveUnidadAdministrativa),
						"%" + criterios.getPersona().getClaveUnidadAdministrativa() + "%"));
			}
			
			if (!ObjectUtils.isNullOrEmpty(criterios.getPersona().getUnidadAdministrativa())) {
				predicates.add(cb.like(root.get(RelUsuarioDatosLaborales_.persona).get(TblPersona_.unidadAdministrativa),
						"%" + criterios.getPersona().getUnidadAdministrativa() + "%"));
			}
			
			if (!ObjectUtils.isNullOrEmpty(criterios.getPersona().getRegistradoPor())) {
				predicates.add(cb.like(root.get(RelUsuarioDatosLaborales_.persona).get(TblPersona_.registradoPor),
						"%" + criterios.getPersona().getRegistradoPor() + "%"));
			}
			
			
			if (!ObjectUtils.isNullOrEmpty(criterios.getPuesto())) {
				predicates.add(cb.like(root.get(RelUsuarioDatosLaborales_.puesto), "%" + criterios.getPuesto() + "%"));
			}
			
			if (!ObjectUtils.isNullOrEmpty(criterios.getIdOrdenGobierno())) {
				predicates.add(cb.like(root.get(RelUsuarioDatosLaborales_.idOrdenGobierno),
						"%" + criterios.getIdOrdenGobierno() + "%"));
			}

			if (!ObjectUtils.isNullOrEmpty(criterios.getOrdenGobierno())) {
				predicates.add(cb.like(root.get(RelUsuarioDatosLaborales_.ordenGobierno),
						"%" + criterios.getOrdenGobierno() + "%"));
			}

			if (!ObjectUtils.isNull(criterios.getPersona().getPersonaCorreos())) {

				if (!criterios.getPersona().getPersonaCorreos().isEmpty()) {
					predicates.add(root.join(RelUsuarioDatosLaborales_.persona).join(TblPersona_.personaCorreos)
							.get(RelPersonaCorreo_.correoElectronico)
							.in(criterios.getPersona().getPersonaCorreos().get(0).getCorreoElectronico()));
				}

			}

			if (!ObjectUtils.isNull(criterios.getPersona().getRelPersonaTelefonos())) {

				if (!criterios.getPersona().getRelPersonaTelefonos().isEmpty()) {
					predicates.add(root.join(RelUsuarioDatosLaborales_.persona).join(TblPersona_.relPersonaTelefonos)
							.get(RelPersonaTelefono_.telefono)
							.in(criterios.getPersona().getRelPersonaTelefonos().get(0).getTelefono()));
				}

			}

		}

		return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	}

}
