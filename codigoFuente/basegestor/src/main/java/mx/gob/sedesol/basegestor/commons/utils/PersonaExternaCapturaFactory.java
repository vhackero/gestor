package mx.gob.sedesol.basegestor.commons.utils;

import java.util.*;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import mx.gob.sedesol.basegestor.commons.dto.admin.*;

/** Transformacion exclusiva de la importacion de fuente externa. Sin estado JSF. */
@Component
public class PersonaExternaCapturaFactory {
    private final PasswordEncoder encoder;
    public PersonaExternaCapturaFactory() {this(new BCryptPasswordEncoder());}
    public PersonaExternaCapturaFactory(PasswordEncoder encoder) {this.encoder=encoder;}
    public CapturaPersonaDTO crear(PersonaSigeDTO origen,Long usuario,String foto,EntidadFederativaDTO sede,
            MunicipioDTO municipio,AsentamientoDTO asentamiento,RolDTO rolAlumno) {
        if(origen.getMatricula()==null || origen.getPassword()==null || origen.getPassword().isEmpty()
                || origen.getCurp()==null || origen.getCurp().length()<9 || origen.getFechaNacimiento()==null)
            throw new IllegalArgumentException("Hay un usuario con datos incompletos en la fuente externa.");
        PersonaDTO persona=new PersonaDTO(usuario,"MX");
        persona.setUsuario(origen.getMatricula().toUpperCase(Locale.ROOT));
        String hash=encoder.encode(origen.getPassword());
        persona.setContrasenia(hash);persona.setNuevaContrasenia(hash);
        persona.setConfirmacionContrasenia(hash);persona.setContraseniaEncriptada(hash);
        persona.setRutaCompletaFoto(foto);
        persona.setCurp(origen.getCurp());persona.setUnidadAdministrativa(origen.getPassword());
        persona.setNombre(origen.getNombre());persona.setApellidoPaterno(origen.getApellidoPaterno());
        persona.setApellidoMaterno(origen.getApellidoMaterno());persona.setFechaNacimiento(origen.getFechaNacimiento());
        persona.setRfc(origen.getCurp().substring(0,9));persona.setCorreoElectronico(origen.getCorreoInstitucional());
        persona.setFuenteExterna(origen.getProgramaEducativo());persona.setIdEntidadFederativa("01");
        persona.setEntidadFederativa("Mexico");persona.setIdMunicipio("12");persona.setMunicipio("Patriotismo");
        persona.setIdDependencia("2-00");persona.setClaveDependencia("11");persona.setDependencia("Dependencia");
        persona.setIdUnidadAdministrativa("11");persona.setSso_status(String.valueOf(origen.getIdPersonaSige()));
        CapturaPersonaDTO captura=new CapturaPersonaDTO();captura.setPersona(persona);
        captura.setRoles(new ArrayList<>(Collections.singletonList(rolAlumno)));
        DomicilioPersonaDTO domicilio=new DomicilioPersonaDTO(usuario,"MX");
        domicilio.setAsentamiento(asentamiento);domicilio.setNumeroExterior("001");domicilio.setIdMunicipio("11");
        domicilio.setPersona(persona);domicilio.setIdEntidadFederativa(11);domicilio.setCalle("Reforma");
        captura.setDomicilioPersona(domicilio);
        UsuarioDatosLaboralesDTO laboral=new UsuarioDatosLaboralesDTO(persona);
        laboral.setInstitucion("UNADM");laboral.setSede(sede);laboral.setMunicipio(municipio);
        laboral.setFechaIngreso(persona.getFechaActualizacion());captura.setDatosLaborales(laboral);
        PersonaCorreoDTO correo=new PersonaCorreoDTO(usuario,1);TipoCorreoDTO tipo=new TipoCorreoDTO();
        tipo.setDescripcion("Correo");tipo.setIdTipoCorreo(1);tipo.setActivo(1);
        correo.setCorreoElectronico(origen.getCorreoInstitucional());correo.setPersona(persona);correo.setTipoCorreo(tipo);
        captura.setPersonaCorreo(correo);return captura;
    }
}
