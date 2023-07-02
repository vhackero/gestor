package mx.gob.sedesol.gestorweb.ws;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaCorreoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaTelefonoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.UsuarioDatosLaboralesDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ProgramaSocialDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelGrupoParticipanteDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.FichaDescProgramaDTO;
import mx.gob.sedesol.basegestor.commons.dto.ws.DatosCuis;
import mx.gob.sedesol.basegestor.commons.dto.ws.Evento;
import mx.gob.sedesol.basegestor.commons.utils.DateUtils;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.OrdenGobiernoEnum;
import mx.gob.sedesol.basegestor.service.gestionescolar.EventoCapacitacionService;
import mx.gob.sedesol.basegestor.service.gestionescolar.GrupoParticipanteService;
import mx.gob.sedesol.basegestor.service.impl.admin.PersonaServiceFacade;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.EventoCapacitacionServiceFacade;
import mx.gob.sedesol.basegestor.service.planesyprogramas.FichaDescProgramaService;

@RestController
@RequestMapping("/validarEncuestador")
public class ServicioCuisREST {
	@Autowired
	private PersonaServiceFacade personaServiceFacade;

	@Autowired
	private FichaDescProgramaService fichaDescProgramaService;

	@Autowired
	private EventoCapacitacionService eventoCapacitacionService;

	@Autowired
	private EventoCapacitacionServiceFacade eventoCapacitacionServiceFacade;

	@Autowired
	private GrupoParticipanteService grupoParticipanteService;

	private List<ProgramaSocialDTO> listaProgramasSociales;

	@RequestMapping("/{idPersona}")
	public DatosCuis obtenerDatos(@PathVariable long idPersona) {
		Integer idPrograma = 48;

		return procesarInfo(idPersona, idPrograma);
	}

	private DatosCuis procesarInfo(Long idPersona, Integer idPrograma) {
		DatosCuis datos = new DatosCuis();

		listaProgramasSociales = eventoCapacitacionServiceFacade.obtenerProgramasSociales();
		FichaDescProgramaDTO programa = fichaDescProgramaService.buscarPorId(idPrograma);
		PersonaDTO persona = personaServiceFacade.getPersonaService().buscarPorId(idPersona);
		UsuarioDatosLaboralesDTO datosLaborales = personaServiceFacade.getUsuarioDatosLaboralesService()
				.obtenerDatosLaboralesPorPersona(idPersona);
		PersonaTelefonoDTO telefonoDTO = personaServiceFacade.getPersonaTelefonoService()
				.obtenerTelefonosPersonaPorTipo(idPersona, 1);
		PersonaTelefonoDTO celularDTO = personaServiceFacade.getPersonaTelefonoService()
				.obtenerTelefonosPersonaPorTipo(idPersona, 2);
		PersonaCorreoDTO correoDTO = personaServiceFacade.getPersonaCorreoService()
				.obtenerCorreoInstitucional(idPersona);

		String tipo_usuario = "";
		String usuario = "";
		String curp = "";
		String nombre = "";
		String primer_apellido = "";
		String segundo_apellido = "";
		String fecha_nacimiento = "";
		String institucion_en_que_labora = "";
		String correo_usuario = "";
		String telefono = "";
		String celular = "";
		String sede_usuario_labora = "";
		String municipio_usuario_labora = "";
		String orden_gob_usuario_labora = "";
		String puesto_usuario_labora = "";
		String modalidad_programa = "";

		if (ObjectUtils.isNotNull(persona.getTipoUsuario())) {
			tipo_usuario = obtenerInternoExterno(persona.getTipoUsuario());
		}
		if (ObjectUtils.isNotNull(persona.getUsuario())) {
			usuario = persona.getUsuario();
		}
		if (ObjectUtils.isNotNull(persona.getCurp())) {
			curp = persona.getCurp();
		}
		if (ObjectUtils.isNotNull(persona.getNombre())) {
			nombre = persona.getNombre();
		}
		if (ObjectUtils.isNotNull(persona.getApellidoPaterno())) {
			primer_apellido = persona.getApellidoPaterno();
		}
		if (ObjectUtils.isNotNull(persona.getApellidoMaterno())) {
			segundo_apellido = persona.getApellidoPaterno();
		}
		if (ObjectUtils.isNotNull(persona.getCurp())) {
			fecha_nacimiento = obtenerFechaDeCurp(persona.getCurp());
		}
		if (ObjectUtils.isNotNull(datosLaborales.getInstitucion())) {
			institucion_en_que_labora = datosLaborales.getInstitucion();
		}
		if (ObjectUtils.isNotNull(correoDTO) && ObjectUtils.isNotNull(correoDTO.getCorreoElectronico())) {
			correo_usuario = correoDTO.getCorreoElectronico();
		}
		if (ObjectUtils.isNotNull(telefonoDTO) && ObjectUtils.isNotNull(telefonoDTO.getTelefono())) {
			telefono = telefonoDTO.getTelefono();
		}
		if (ObjectUtils.isNotNull(celularDTO) && ObjectUtils.isNotNull(celularDTO.getTelefono())) {
			celular = celularDTO.getTelefono();
		}
		if (ObjectUtils.isNotNull(datosLaborales.getSede())
				&& ObjectUtils.isNotNull(datosLaborales.getSede().getNombre())) {
			sede_usuario_labora = datosLaborales.getSede().getNombre();
		}
		if (ObjectUtils.isNotNull(datosLaborales.getMunicipio())
				&& ObjectUtils.isNotNull(datosLaborales.getMunicipio().getNombre())) {
			municipio_usuario_labora = datosLaborales.getMunicipio().getNombre();
		}
		if (ObjectUtils.isNotNull(datosLaborales.getOrdenGobierno())) {
			orden_gob_usuario_labora = obtenerNombreOrdenPorId(datosLaborales.getIdOrdenGobierno());
		}
		if (ObjectUtils.isNotNull(datosLaborales.getPuesto())) {
			puesto_usuario_labora = datosLaborales.getPuesto();
		}
		if (ObjectUtils.isNotNull(programa.getCatModalidad())
				&& ObjectUtils.isNotNull(programa.getCatModalidad().getNombre())) {
			modalidad_programa = programa.getCatModalidad().getNombre();
		}
		List<Evento> eventos = obtenerDatosEventos(idPrograma, idPersona);
		String fecha_acreditacion = obtenerFechaAcreditacion(eventos);

		datos.setTipo_usuario(tipo_usuario);
		datos.setUsuario(usuario);
		datos.setCurp(curp);
		datos.setNombre(nombre);
		datos.setPrimer_apellido(primer_apellido);
		datos.setSegundo_apellido(segundo_apellido);
		datos.setFecha_nacimiento(fecha_nacimiento);
		datos.setInstitucion_en_que_labora(institucion_en_que_labora);
		datos.setCorreo_usuario(correo_usuario);
		datos.setTelefono_usuario(telefono);
		datos.setCelular_usuario(celular);
		datos.setSede_usuario_labora(sede_usuario_labora);
		datos.setMunicipio_usuario_labora(municipio_usuario_labora);
		datos.setOrden_gob_usuario_labora(orden_gob_usuario_labora);
		datos.setPuesto_usuario_labora(puesto_usuario_labora);
		datos.setModalidad_programa(modalidad_programa);
		datos.setEventos(eventos);
		datos.setFecha_acreditacion(fecha_acreditacion);

		return datos;
	}

	
    public String obtenerNombreOrdenPorId(String idOrden) {
		String nombreOrden = "";
		if(ObjectUtils.isNotNull(idOrden)) {
			for(OrdenGobiernoEnum ordenEnum : OrdenGobiernoEnum.values()){
				if(idOrden.equals(ordenEnum.getValor())) {
					nombreOrden = ordenEnum.getDescripcion();
				}
			}
		}else {
			return nombreOrden;
		}
		return nombreOrden;
	}
	
	
	private String obtenerFechaAcreditacion(List<Evento> eventos) {
		List<Date> fechas = new ArrayList<>();
		for (Evento evento : eventos) {
			if (ObjectUtils.isNotNull(evento.getFecha_intento())) {
				Date fecha = DateUtils.convierteStringADate(evento.getFecha_intento(), "dd/MM/yy");
				fechas.add(fecha);
			}
		}
		if (!fechas.isEmpty()) {
			Collections.sort(fechas, Collections.reverseOrder());
			Date fechaAux = fechas.get(0);
			String fecha = DateUtils.convierteDateAString(fechaAux, "dd/MM/yy");
			return fecha;
		}
		return "";
	}

	private String obtenerInternoExterno(Integer tipo) {
		return tipo.intValue() == 1 ? "Interno" : "Externo";
	}

	private String obtenerFechaDeCurp(String curp) {
		String anio = curp.substring(4, 6);
		String mes = curp.substring(6, 8);
		String dia = curp.substring(8, 10);
		return dia + "/" + mes + "/" + anio;
	}

	private List<Evento> obtenerDatosEventos(Integer idPrograma, Long idPersona) {

		Set<String> listaIdsEventos = obtenerIdsEventosPorPrograma(idPrograma);

		List<RelGrupoParticipanteDTO> registrosCalificaciones = grupoParticipanteService
				.obtenerEventosCapacitacionPorIdParticipante(idPersona,3);

		Set<Evento> eventos = new HashSet<>();

		if (!registrosCalificaciones.isEmpty()) {
			for (RelGrupoParticipanteDTO rgp : registrosCalificaciones) {

				Evento evento = new Evento();
				if (ObjectUtils.isNotNull(rgp.getGrupo().getEvento())) {
					int id_evento_int = rgp.getGrupo().getEvento().getIdEvento();
					String id_evento = String.valueOf(id_evento_int);
					evento.setId_evento(id_evento);
				}

				if (ObjectUtils.isNotNull(rgp.getCalifFinal())) {
					double calificacion_double = rgp.getCalifFinal();
					String calificacion = String.valueOf(calificacion_double);
					evento.setCalificacion(calificacion);
				}

				if (ObjectUtils.isNotNull(rgp.getFechaRegistro())) {
					Date fecha_date = rgp.getFechaRegistro();
					String fecha_intento = DateUtils.darFormatoFecha(fecha_date, "dd/MM/yy");
					evento.setFecha_intento(fecha_intento);
				}
				if (ObjectUtils.isNotNull(rgp.getGrupo().getEvento().getIdProgramaSocial())) {
					for (ProgramaSocialDTO programaSocial : listaProgramasSociales) {
							if (rgp.getGrupo().getEvento().getIdProgramaSocial()
									.equals(programaSocial.getIdProgramaSocial())) {
								String unidad_responsable = programaSocial.getUnidadResponsable().getNombre();
								evento.setUnidad_responsable(unidad_responsable);
							}
					}
				}else{
					evento.setUnidad_responsable("");
				}

				if (ObjectUtils.isNotNull(evento.getId_evento())) {
					for (String idEvento : listaIdsEventos) {

						if (evento.getId_evento().equals(idEvento)) {
							eventos.add(evento);
						}
					}
				}
			}
		}
		List<Evento> eventosRetorno = new ArrayList<>();
		if (!eventos.isEmpty()) {
			for (Evento evento : eventos) {
				eventosRetorno.add(evento);
			}
			return eventosRetorno;
		} else {
			
			return eventosRetorno;
		}

	}

	private Set<String> obtenerIdsEventosPorPrograma(Integer idPrograma) {
		List<EventoCapacitacionDTO> eventos = eventoCapacitacionService.obtenerEventosPorPrograma(idPrograma);
		Set<String> listaIdsEventos = new HashSet<>();
		for (EventoCapacitacionDTO evento : eventos) {
			int id_evento = evento.getIdEvento();
			String idEvento = String.valueOf(id_evento);
			listaIdsEventos.add(idEvento);
		}
		return listaIdsEventos;
	}

}
