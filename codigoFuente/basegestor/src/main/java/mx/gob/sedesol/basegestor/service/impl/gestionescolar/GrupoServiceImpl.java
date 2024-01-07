package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.GrupoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.PersonaResponsabilidadesDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.TblInscripcionResumenDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.RelPersonaResponsabilidades;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblEvento;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblGrupo;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.GrupoRepo;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.PersonaResponsabilidadesRepo;
import mx.gob.sedesol.basegestor.service.ParametroWSMoodleService;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.admin.RelPersonaPlataformaMoodleService;
import mx.gob.sedesol.basegestor.service.gestionescolar.GrupoService;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.AlumnoGrupo;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Grupo;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client.BorrarGrupo;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client.CrearGrupo;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client.UsuariosGrupoWS;

@Service("grupoService")
public class GrupoServiceImpl extends ComunValidacionService<GrupoDTO> implements GrupoService {

	private static final Logger logger = Logger.getLogger(GrupoServiceImpl.class);

	@Autowired
	private GrupoRepo grupoRepo;

	@Autowired
	private PersonaResponsabilidadesRepo personaResponsabilidadesRepo;

	@Autowired
	private RelPersonaPlataformaMoodleService relPersonaPlataformaMoodleService;

	@Autowired
	private ParametroWSMoodleService parametroWSMoodleService;

	private ModelMapper modelMapper = new ModelMapper();

	@PostConstruct
	public void init() {

		PropertyMap<EventoCapacitacionDTO, TblEvento> tblEventoMap = new PropertyMap<EventoCapacitacionDTO, TblEvento>() {
			protected void configure() {
				skip().setCategoriaEC(null);
				skip().setCatEstadoEventoCapacitacion(null);
				skip().setFichaDescriptivaPrograma(null);
				skip().setAmbienteVirtualAprendizajes(null);
				skip().setReponsableProduccionEcs(null);
				skip().setCatModalidadPlanPrograma(null);
			}
		};
		modelMapper.addMappings(tblEventoMap);
	}

	@Override
	public List<GrupoDTO> getGruposByEvento(Integer idEvento) {
		List<TblGrupo> lista = grupoRepo.getGruposByEvento(idEvento);

		Type objetoDTO = new TypeToken<List<GrupoDTO>>() {
		}.getType();

		return modelMapper.map(lista, objetoDTO);
	}

 	@Override
	public List<GrupoDTO> generarGruposDispersion( EventoCapacitacionDTO evento, TblInscripcionResumenDTO inscripcionResumen,  Long usuarioModifico) {

		List<GrupoDTO> gruposDTO = new ArrayList<GrupoDTO>();
		
		//for (TblInscripcionResumenDTO inscripcionResumen: listaInscripcionResumen ) {
			Integer numeroGrupos = inscripcionResumen.getNoGrupos();
			if(inscripcionResumen.getGrupoResto() != null) {
				numeroGrupos =numeroGrupos+inscripcionResumen.getGrupoResto() ;
			}
			List<String> nombresGrupo = generarNombresGrupoDispercion(inscripcionResumen.getGrupo(), numeroGrupos);
			
			System.out.println(" generarGruposDispersion >>  nombresGrupo: " + nombresGrupo.size());
			//List<String> inscrip=   obtenerListaInscripcionesByIdPLan(33);
		    Optional.ofNullable(nombresGrupo).orElse(Collections.emptyList()).stream()
	        .forEach(System.out::println);
			gruposDTO = almacenarGrupo(nombresGrupo, evento, usuarioModifico);
		//}
		
	
		return gruposDTO;
	}
	
	@Override
	public List<GrupoDTO> generarGrupos(EventoCapacitacionDTO evento, int numeroGrupos, Long usuarioModifico,
			ParametroWSMoodleDTO parametroWSMoodleDTO) {

		List<GrupoDTO> gruposDTO;

		List<String> nombresGrupo = generarNombresGrupo(evento, numeroGrupos);
		if (evento.getCatModalidadPlanPrograma().getId() == ConstantesGestor.MODALIDAD_LINEA
				|| evento.getCatModalidadPlanPrograma().getId() == ConstantesGestor.MODALIDAD_MIXTO) {
			gruposDTO = almacenarGruposMoodle(evento, usuarioModifico, nombresGrupo, parametroWSMoodleDTO);
		} else {
			gruposDTO = almacenarGrupo(nombresGrupo, evento, usuarioModifico);
		}
		return gruposDTO;
	}

	private List<GrupoDTO> almacenarGruposMoodle(EventoCapacitacionDTO evento, Long usuarioModifico,
			List<String> nombresGrupo, ParametroWSMoodleDTO parametroWSMoodleDTO) {

		List<GrupoDTO> gruposDTO = new ArrayList<>();
		List<Grupo> grupos = generarGruposMoodle(evento, nombresGrupo);
		CrearGrupo crearGrupoWS = new CrearGrupo(parametroWSMoodleDTO);
		try {
			List<Grupo> gruposResultado = crearGrupoWS.crearGrupos(grupos);
			for (Grupo grupoResultado : gruposResultado) {
				logger.info("###GMoodle" + grupoResultado.getName() + " " + grupoResultado.getId() + "#");
				gruposDTO.add(almacenarGrupo(grupoResultado, evento.getIdEvento(), usuarioModifico));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return gruposDTO;
	}

	private List<GrupoDTO> almacenarGrupo(List<String> nombresGrupo, EventoCapacitacionDTO evento,
			Long usuarioModifico) {
		List<GrupoDTO> grupos = new ArrayList<>();
		System.out.println("almacenarGrupo>>>>>> "+nombresGrupo.size());
		
		for (String nombre : nombresGrupo) {
			TblGrupo grupoEnt = new TblGrupo();
			grupoEnt.setEvento(modelMapper.map(evento, TblEvento.class));
			grupoEnt.setNombre(nombre);
			grupoEnt.setUsuarioModifico(usuarioModifico);
			grupoEnt.setFechaRegistro(new Date());
			grupoEnt.setFachaActualizacion(grupoEnt.getFechaRegistro());
			grupoRepo.save(grupoEnt);

			grupos.add(modelMapper.map(grupoEnt, GrupoDTO.class));
		}
		return grupos;
	}

	private List<String> generarNombresGrupo(EventoCapacitacionDTO evento, int numeroGrupos) {
		List<String> nombresGrupo = new ArrayList<>();
		Integer maximo;
		List<TblGrupo> gruposAlmacenados = grupoRepo.getGruposByEvento(evento.getIdEvento());
		if (gruposAlmacenados.isEmpty()) {
			maximo = 1;
		} else {
			maximo = 1;
			for (TblGrupo grupoAlmacenado : gruposAlmacenados) {
				String numeroGrupo = grupoAlmacenado.getNombre()
						.replace("EC-" + grupoAlmacenado.getEvento().getIdEvento() + "-G-", "");
				Integer numero = Integer.parseInt(numeroGrupo);
				if (numero > maximo) {
					maximo = numero;
				}
			}
			maximo++;
		}
		for (int i = 0; i < numeroGrupos; i++) {
			nombresGrupo.add("EC-" + String.valueOf(evento.getIdEvento()) + "-G-" + maximo);
			maximo++;
		}
		return nombresGrupo;
	}
	private List<String> generarNombresGrupoDispercion(String nombreGrupoBase, int numeroGrupos) {
		List<String> nombresGrupo = new ArrayList<>();
		String nombreGrupo = nombreGrupoBase.substring(0,19);
		System.out.println(" generarNombresGrupoDispercion >>  nombreGrupoBase: " + nombreGrupoBase+ " <-> nombreGrupo:"+nombreGrupo);

		 
		
		for (int i = 1; i <= numeroGrupos; i++) {
			if(numeroGrupos<=9) {
				nombresGrupo.add( nombreGrupo + "00" + i);
			}else {
				nombresGrupo.add( nombreGrupo + "0" + i);
			}
		}
		//System.out.println(" generarNombresGrupoDispercion >>  nombresGrupo: " + nombresGrupo.size());
		return nombresGrupo;
	}

	private List<Grupo> generarGruposMoodle(EventoCapacitacionDTO evento, List<String> nombres) {
		List<Grupo> grupos = new ArrayList<>();
		for (String nombre : nombres) {
			Grupo grupo = new Grupo();
			grupo.setName(nombre);
			grupo.setCourseid(evento.getIdCursoLmsBorrador());
			grupos.add(grupo);
		}
		return grupos;
	}

	private GrupoDTO almacenarGrupo(Grupo grupo, Integer idEvento, Long usuarioModifico) {
		TblGrupo grupoEnt = new TblGrupo();
		grupoEnt.setEvento(new TblEvento());
		grupoEnt.getEvento().setIdEvento(idEvento);
		grupoEnt.setNombre(grupo.getName());
		grupoEnt.setUsuarioModifico(usuarioModifico);
		grupoEnt.setFechaRegistro(new Date());
		grupoEnt.setFachaActualizacion(grupoEnt.getFechaRegistro());
		grupoEnt.setIdMoodle(grupo.getId());
		grupoRepo.save(grupoEnt);

		return modelMapper.map(grupoEnt, GrupoDTO.class);
	}

	@Override
	public GrupoDTO generarGrupo(Integer idEvento, Long usuarioModifico) {
		TblGrupo grupo = new TblGrupo();
		String nombreGrupo = null;
		int contador = 1;
		while (ObjectUtils.isNull(nombreGrupo)) {
			nombreGrupo = String.valueOf(idEvento) + "-" + contador;
			if (!grupoRepo.getGruposByEventoAndNombre(idEvento, nombreGrupo).isEmpty()) {
				nombreGrupo = null;
			}
			contador++;
		}
		grupo.setEvento(new TblEvento());
		grupo.getEvento().setIdEvento(idEvento);
		grupo.setNombre(nombreGrupo);
		grupo.setUsuarioModifico(usuarioModifico);
		grupoRepo.save(grupo);

		return modelMapper.map(grupo, GrupoDTO.class);
	}

	@Override
	public ResultadoDTO<GrupoDTO> eliminarGrupo(GrupoDTO grupo, Long usuarioModifico, Integer idPlataforma) {
		ResultadoDTO<GrupoDTO> resultado = new ResultadoDTO<>();
		try {


			if (grupo.getEvento().getCatModalidadPlanPrograma().getId() == ConstantesGestor.MODALIDAD_LINEA
					|| grupo.getEvento().getCatModalidadPlanPrograma().getId() == ConstantesGestor.MODALIDAD_MIXTO) {
				

				BorrarGrupo borrarGrupo = new BorrarGrupo(parametroWSMoodleService.buscarPorId(idPlataforma));

				ArrayList<Integer> lista = new ArrayList<>();
				lista.add(grupo.getIdMoodle());
				borrarGrupo.borrarGrupo(lista);
			}
			grupoRepo.delete(grupo.getIdGrupo());
			
			resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_EXITOSA.getId());
		} catch (Exception e) {
			resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_FALLIDA);
			logger.error(e.getMessage(), e);
		}
		return resultado;
	}

	@Override
	public ResultadoDTO<GrupoDTO> actualizarGrupo(GrupoDTO grupo, Long usuarioModifico) {
		ResultadoDTO<GrupoDTO> resultado = new ResultadoDTO<>();
		try {

			TblGrupo entidad = modelMapper.map(grupo, TblGrupo.class);
			grupoRepo.saveAndFlush(entidad);

			resultado.setDto(modelMapper.map(entidad, GrupoDTO.class));
			resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_EXITOSA.getId());
		} catch (Exception e) {
			resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
			logger.error(e.getMessage(), e);
		}
		return resultado;
	}

	@Override
	public ResultadoDTO<GrupoDTO> establecerFacilitador(GrupoDTO grupo, EventoCapacitacionDTO evento,
			ParametroWSMoodleDTO parametroWSMoodleDTO) {
		ResultadoDTO<GrupoDTO> resultado = new ResultadoDTO<>();
		GrupoDTO grupoActual = buscarGrupoPorId(grupo.getIdGrupo());
		PersonaResponsabilidadesDTO facilitadorAnterior;
		if (ObjectUtils.isNotNull(grupo)) {
			facilitadorAnterior = grupoActual.getFacilitador();
			grupoActual.setFacilitador(grupo.getFacilitador());
			grupoActual.setUsuarioModifico(grupo.getUsuarioModifico());
			grupoActual.setFachaActualizacion(new Date());
			actualizarGrupo(grupoActual, grupo.getUsuarioModifico());

		} else {
			facilitadorAnterior = null;
		}
		if (evento.getCatModalidadPlanPrograma().getId() == ConstantesGestor.MODALIDAD_LINEA
				|| evento.getCatModalidadPlanPrograma().getId() == ConstantesGestor.MODALIDAD_MIXTO) {
			RelPersonaResponsabilidades personaResponsable = personaResponsabilidadesRepo
					.findOne(grupo.getFacilitador().getId());
			if (ObjectUtils.isNotNull(personaResponsable)) {
				Integer idPersonaLms = relPersonaPlataformaMoodleService.obtenerIdMoodle(
						modelMapper.map(personaResponsable.getTblPersona(), PersonaDTO.class),
						evento.getIdPlataformaLmsBorrador(), grupo.getUsuarioModifico());
				if (!ObjectUtils.isNullOrCero(idPersonaLms)) {
					UsuariosGrupoWS usuariosGrupoWS = new UsuariosGrupoWS(parametroWSMoodleDTO);
					List<AlumnoGrupo> listaFacilitadorGrupo = generarAlumnosGrupo(grupo.getIdMoodle(), idPersonaLms);

					try {
						usuariosGrupoWS.agregarUsuariosGrupo(listaFacilitadorGrupo);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}

				}
			}
		}
		if (ObjectUtils.isNotNull(facilitadorAnterior)) {
			Integer idPersonaLms = relPersonaPlataformaMoodleService.obtenerIdMoodle(
					modelMapper.map(facilitadorAnterior.getTblPersona(), PersonaDTO.class),
					evento.getIdPlataformaLmsBorrador(), grupo.getUsuarioModifico());
			if (!ObjectUtils.isNullOrCero(idPersonaLms)) {
				UsuariosGrupoWS usuariosGrupoWS = new UsuariosGrupoWS(parametroWSMoodleDTO);
				List<AlumnoGrupo> listaFacilitadorAnteroirGrupo = generarAlumnosGrupo(grupo.getIdMoodle(),
						idPersonaLms);
				try {
					usuariosGrupoWS.quitarUsuariosGrupo(listaFacilitadorAnteroirGrupo);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return resultado;
	}

	private List<AlumnoGrupo> generarAlumnosGrupo(Integer idGrupoMoodle, Integer idPersonaMoodle) {
		List<AlumnoGrupo> listaAlumnosGrupo = new ArrayList<>();
		AlumnoGrupo alumnoGrupo = new AlumnoGrupo();
		alumnoGrupo.setGroupid(idGrupoMoodle);
		alumnoGrupo.setUserid(idPersonaMoodle);
		listaAlumnosGrupo.add(alumnoGrupo);
		return listaAlumnosGrupo;
	}

	@Override
	public GrupoDTO buscarGrupoPorId(Integer idGrupo) {

		return modelMapper.map(grupoRepo.findOne(idGrupo), GrupoDTO.class);

	}

	@Override
	public Map<Integer, Boolean> validaActasCerradas(List<Integer> idEventoCapacitacion) {

		if (ObjectUtils.isNullOrEmpty(idEventoCapacitacion)) {
			idEventoCapacitacion = null;
		}

		Date fechaActual = new Date();
		List<TblGrupo> gruposList = grupoRepo.getGruposByEventoIdEventos(idEventoCapacitacion, fechaActual);

		Map<Integer, Boolean> map = new HashMap<Integer, Boolean>();

		gruposList.forEach(gpo -> {
			if (!map.containsKey(gpo.getEvento().getIdEvento())) {
				map.put(gpo.getEvento().getIdEvento(), Boolean.TRUE);
			}
		});

		return map;
	}

	@Override
	public void validarPersistencia(GrupoDTO dto, ResultadoDTO<GrupoDTO> resultado) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	@Override
	public void validarActualizacion(GrupoDTO dto, ResultadoDTO<GrupoDTO> resultado) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	@Override
	public void validarEliminacion(GrupoDTO dto, ResultadoDTO<GrupoDTO> resultado) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}
}
