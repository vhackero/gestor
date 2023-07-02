package mx.gob.sedesol.basegestor.service.impl.analisisdatos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.CommonGroupByDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.DomicilioPersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaRolDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.UsuarioDatosLaboralesDTO;
import mx.gob.sedesol.basegestor.commons.dto.analisisdatos.AnalisisDatosUsuarioDTO;
import mx.gob.sedesol.basegestor.commons.dto.analisisdatos.EstadisticasUsuarioDTO;
import mx.gob.sedesol.basegestor.commons.utils.EstatusPersonaEnum;
import mx.gob.sedesol.basegestor.commons.utils.GeneroEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.OrdenGobiernoEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoUsuarioEnum;
import mx.gob.sedesol.basegestor.model.entities.admin.CatEntidadFederativa;
import mx.gob.sedesol.basegestor.mongo.service.BitacoraEntradaSistemaService;
import mx.gob.sedesol.basegestor.mongo.service.BitacoraService;
import mx.gob.sedesol.basegestor.service.admin.CatalogoComunService;
import mx.gob.sedesol.basegestor.service.admin.DomicilioPersonaService;
import mx.gob.sedesol.basegestor.service.admin.MunicipioService;
import mx.gob.sedesol.basegestor.service.admin.PersonaRolesService;
import mx.gob.sedesol.basegestor.service.admin.UsuarioDatosLaboralesService;

@Service("analisisDatosUsuarioFacade")
public class AnalisisDatosUsuarioFacade {

	private static final Logger log = Logger.getLogger(AnalisisDatosUsuarioFacade.class);

	@Autowired
	private CatalogoComunService<CatEntidadFederativa, Integer> catEntidadFederativaService;

	@Autowired
	private UsuarioDatosLaboralesService usuarioDatosLaboralesService;

	@Autowired
	private PersonaRolesService personaRolesService;

	@Autowired
	private MunicipioService municipioService;

	@Autowired
	private DomicilioPersonaService domicilioPersonaService;

	@Autowired
	private BitacoraEntradaSistemaService bitacoraEntradaSistemaService;

	@Autowired
	private BitacoraService bitacoraService;

	public List<AnalisisDatosUsuarioDTO> llenarTabla(List<DomicilioPersonaDTO> personas, String rangoEdad,
			List<String> rangoEdades) {

		List<AnalisisDatosUsuarioDTO> tblDatosPersona = new ArrayList<>();

		/* BUSQUEDA POR RANGO DE EDAD */
		// Obtener el rango de edad
		// Compararlo con todos los rangos para saber cual es
		// Establecer edades limites
		// Solo se guarda el usuario que este dentro de esa edad
		int edadMinima = 0;
		int edadMaxima = 0;
		if (!ObjectUtils.isNullOrEmpty(rangoEdad)) {
			if (rangoEdad.equals(rangoEdades.get(0))) {
				edadMinima = 18;
				edadMaxima = 29;
			} else if (rangoEdad.equals(rangoEdades.get(1))) {
				edadMinima = 30;
				edadMaxima = 39;
			} else if (rangoEdad.equals(rangoEdades.get(2))) {
				edadMinima = 40;
				edadMaxima = 49;
			} else if (rangoEdad.equals(rangoEdades.get(3))) {
				edadMinima = 50;
				edadMaxima = 65;
			}
		}

		for (DomicilioPersonaDTO persona : personas) {
			AnalisisDatosUsuarioDTO dato = new AnalisisDatosUsuarioDTO();
			//UsuarioDatosLaboralesDTO datosLaborales = usuarioDatosLaboralesService
			//		.buscarPorId(persona.getPersona().getIdPersona().intValue());
			
			if(!persona.getPersona().getDatosLaborales().isEmpty()){
				UsuarioDatosLaboralesDTO datosLaborales = persona.getPersona().getDatosLaborales().get(0);
				dato.setDatosLaborales(datosLaborales);
			}else{
				UsuarioDatosLaboralesDTO datosLaborales = new UsuarioDatosLaboralesDTO();
				log.info("la persona con id :" + persona.getPersona().getIdPersona() + " no tiene datos laborales");
				dato.setDatosLaborales(datosLaborales);
			}
			
			
			List<PersonaRolDTO> roles = persona.getPersona().getRelPersonaRoles();
			
			//List<PersonaRolDTO> roles = personaRolesService
			//		.obtieneRelPersonaRolesPorUsuario(persona.getPersona().getUsuario());

			dato.setDomicilioPersona(persona);
			//dato.setDatosLaborales(datosLaborales);
			dato.setListaRoles(roles);


			if (!ObjectUtils.isNullOrEmpty(rangoEdad)) {
				int edad = dato.getDomicilioPersona().getPersona().edad();
				if (entre(edad, edadMinima, edadMaxima)) {
					tblDatosPersona.add(dato);
				}
			}else{
				tblDatosPersona.add(dato);
			}
		}

		return tblDatosPersona;
	}

	public EstadisticasUsuarioDTO generarEstadisticas(List<AnalisisDatosUsuarioDTO> datosPersona) {
		EstadisticasUsuarioDTO estadisticas = new EstadisticasUsuarioDTO();
		estadisticas.setTotalUsuarios(datosPersona.size());
		estadisticas.setFechaCreacion(new Date());

		Integer usuariosExternos = 0;
		Integer usuariosInternos = 0;
		Integer numeroMujeres = 0;
		Integer numeroHombres = 0;
		Integer numeroActivos = 0;
		Integer numeroInactivos = 0;
		Integer ordenFederal = 0;
		Integer ordenEstatal = 0;
		Integer ordenMunicipal = 0;
		Map<String, Integer> puestosNoOrdenados = new HashMap<>();
		List<CommonGroupByDTO> puestoCantidad = new ArrayList<>();
		Integer rangoEdad18 = 0;
		Integer rangoEdad30 = 0;
		Integer rangoEdad40 = 0;
		Integer rangoEdad50 = 0;
		Integer programaSocial = 0;
		Integer sinProgramaSocial = 0;
		
		for (AnalisisDatosUsuarioDTO persona : datosPersona) {

			if (persona.getDomicilioPersona().getPersona().getTipoUsuario()
					.equals(TipoUsuarioEnum.INTERNO.getValor())) {
				usuariosInternos++;
			} else if (persona.getDomicilioPersona().getPersona().getTipoUsuario()
					.equals(TipoUsuarioEnum.EXTERNO.getValor())) {
				usuariosExternos++;
			}

			if (!ObjectUtils.isNullOrEmpty(persona.getDomicilioPersona().getPersona().getGenero())) {
				if (persona.getDomicilioPersona().getPersona().getGenero().equals(GeneroEnum.FEMENINO.getValor())) {
					numeroMujeres++;
				} else if (persona.getDomicilioPersona().getPersona().getGenero()
						.equals(GeneroEnum.MASCULINO.getValor())) {
					numeroHombres++;
				}
			}

			if (persona.getDomicilioPersona().getPersona().getActivo().equals(EstatusPersonaEnum.ACTIVO.getValor())) {
				numeroActivos++;
			} else if (persona.getDomicilioPersona().getPersona().getActivo()
					.equals(EstatusPersonaEnum.INACTIVO.getValor())) {
				numeroInactivos++;
			}

			if (!ObjectUtils.isNullOrEmpty(persona.getDatosLaborales())
					&& !ObjectUtils.isNullOrEmpty(persona.getDatosLaborales().getIdOrdenGobierno())) {
				
				if(persona.getDatosLaborales().getIdOrdenGobierno().equals(OrdenGobiernoEnum.FEDERAL.getValor())) {
					ordenFederal++;
				}
				if(persona.getDatosLaborales().getIdOrdenGobierno().equals(OrdenGobiernoEnum.ESTATAL.getValor())) {
					ordenEstatal++;
				}
				if(persona.getDatosLaborales().getIdOrdenGobierno().equals(OrdenGobiernoEnum.MUNICIPAL.getValor())) {
					ordenMunicipal++;
				}
			
			}

			if (!ObjectUtils.isNull(persona.getDatosLaborales())
					&& !ObjectUtils.isNull(persona.getDatosLaborales().getPuesto())) {
				if (!ObjectUtils.isNullOrEmpty(persona.getDatosLaborales().getPuesto())) {
					if (puestosNoOrdenados.containsKey(persona.getDatosLaborales().getPuesto())) {
						puestosNoOrdenados.put(persona.getDatosLaborales().getPuesto(),
								puestosNoOrdenados.get(persona.getDatosLaborales().getPuesto()) + 1);
					} else {
						puestosNoOrdenados.put(persona.getDatosLaborales().getPuesto(), 1);
					}
				}
			}

			if (entre(persona.getDomicilioPersona().getPersona().edad(), 18, 29)) {
				rangoEdad18++;
			} else if (entre(persona.getDomicilioPersona().getPersona().edad(), 30, 39)) {
				rangoEdad30++;
			} else if (entre(persona.getDomicilioPersona().getPersona().edad(), 40, 49)) {
				rangoEdad40++;
			} else if (entre(persona.getDomicilioPersona().getPersona().edad(), 50, 65)) {
				rangoEdad50++;
			}

			if (ObjectUtils.isNotNull(persona.getDatosLaborales())
					&& ObjectUtils.isNotNull(persona.getDatosLaborales().getProgramaSocial()))
				if (persona.getDatosLaborales().getProgramaSocial()) {
					programaSocial++;
				} else {
					sinProgramaSocial++;
				}
		}

		Map<String, Integer> puestosOrdenados = sortByValue(puestosNoOrdenados);

		int primerosTresPuestos = 0;
		for (Entry<String, Integer> puesto : puestosOrdenados.entrySet()) {

			switch (primerosTresPuestos) {
			case 0:
				estadisticas.setNombrePuesto1(puesto.getKey());
				estadisticas.setCantidadPuesto1(puesto.getValue());
				break;
			case 1:
				estadisticas.setNombrePuesto2(puesto.getKey());
				estadisticas.setCantidadPuesto2(puesto.getValue());
				break;
			case 2:
				estadisticas.setNombrePuesto3(puesto.getKey());
				estadisticas.setCantidadPuesto3(puesto.getValue());
				break;
			}
			primerosTresPuestos++;
			if (primerosTresPuestos == 3)
				break;
		}

		estadisticas.setNumeroActivos(numeroActivos);
		estadisticas.setNumeroInactivos(numeroInactivos);
		estadisticas.setUsuariosExternos(usuariosExternos);
		estadisticas.setUsuariosInternos(usuariosInternos);
		estadisticas.setNumeroHombres(numeroHombres);
		estadisticas.setNumeroMujeres(numeroMujeres);
		estadisticas.setOrdenEstatal(ordenEstatal);
		estadisticas.setOrdenFederal(ordenFederal);
		estadisticas.setOrdenMunicipal(ordenMunicipal);
		estadisticas.setPuestoCantidad(puestoCantidad);

		estadisticas.setRangoEdad18(rangoEdad18);
		estadisticas.setRangoEdad30(rangoEdad30);
		estadisticas.setRangoEdad40(rangoEdad40);
		estadisticas.setRangoEdad50(rangoEdad50);

		estadisticas.setProgramaSocial(programaSocial);
		estadisticas.setSinProgramaSocial(sinProgramaSocial);


		log.info(estadisticas.getFechaCreacion() + " fecha");
		log.info(estadisticas.getNumeroActivos() + " activos");
		log.info(estadisticas.getNumeroHombres() + " hombres");
		log.info(estadisticas.getNumeroInactivos() + " inactivos");
		log.info(estadisticas.getNumeroMujeres() + " mujeres");
		log.info(estadisticas.getOrdenEstatal() + " estatal");
		log.info(estadisticas.getOrdenFederal() + " federal");
		log.info(estadisticas.getOrdenMunicipal() + " municipal");
		for (CommonGroupByDTO puesto : puestoCantidad) {
			log.info(puesto.getNombreCampo() + " puesto " + puesto.getValorNumerico() + "<- cantidad");
		}
		log.info(estadisticas.getRangoEdad18() + " 18-29");
		log.info(estadisticas.getRangoEdad30() + " 30-39");
		log.info(estadisticas.getRangoEdad40() + " 40-49");
		log.info(estadisticas.getRangoEdad50() + " 50-65");
		log.info(estadisticas.getTotalUsuarios() + " total usuarios");
		log.info(estadisticas.getUsuariosExternos() + " externos");
		log.info(estadisticas.getUsuariosInternos() + " internos");
		log.info(estadisticas.getProgramaSocial() + " programa social");
		log.info(estadisticas.getSinProgramaSocial() + " sin programa social");

		log.info(estadisticas.getUsuariosLunesPasado() + " lunes pasado");
		log.info(estadisticas.getUsuariosMartesPasado() + " martes pasado");
		log.info(estadisticas.getUsuariosMiercolesPasado() + " miercoles pasado");
		log.info(estadisticas.getUsuariosJuevesPasado() + " jueves pasado");
		log.info(estadisticas.getUsuariosViernesPasado() + " viernes pasado");
		log.info(estadisticas.getUsuariosLunes() + " lunes");
		log.info(estadisticas.getUsuariosMartes() + " martes");
		log.info(estadisticas.getUsuariosMiercoles() + " miercoles");
		log.info(estadisticas.getUsuariosJueves() + " jueves");
		log.info(estadisticas.getUsuariosViernes() + " viernes");

		return estadisticas;
	}

	private static Map<String, Integer> sortByValue(Map<String, Integer> unsortMap) {

		// 1. Convert Map to List of Map
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());
		// 2. Ordenar la List con Collections.sort utilizando un Comparator

		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});
		// Recorrer la lista ordenada y ponerlos en un linkedHashMap para que se
		// respete el orden
		// de insercion

		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		for (Map.Entry<String, Integer> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		/*
		 * //classic iterator example for (Iterator<Map.Entry<String, Integer>>
		 * it = list.iterator(); it.hasNext(); ) { Map.Entry<String, Integer>
		 * entry = it.next(); sortedMap.put(entry.getKey(), entry.getValue()); }
		 */

		return sortedMap;
	}

	private static boolean entre(int edad, int menor, int mayor) {
		return edad>=menor && edad <= mayor;
	}

	public CatalogoComunService<CatEntidadFederativa, Integer> getCatEntidadFederativaService() {
		return catEntidadFederativaService;
	}

	public void setCatEntidadFederativaService(
			CatalogoComunService<CatEntidadFederativa, Integer> catEntidadFederativaService) {
		this.catEntidadFederativaService = catEntidadFederativaService;
	}

	public MunicipioService getMunicipioService() {
		return municipioService;
	}

	public void setMunicipioService(MunicipioService municipioService) {
		this.municipioService = municipioService;
	}

	public DomicilioPersonaService getDomicilioPersonaService() {
		return domicilioPersonaService;
	}

	public void setDomicilioPersonaService(DomicilioPersonaService domicilioPersonaService) {
		this.domicilioPersonaService = domicilioPersonaService;
	}

	public UsuarioDatosLaboralesService getUsuarioDatosLaboralesService() {
		return usuarioDatosLaboralesService;
	}

	public void setUsuarioDatosLaboralesService(UsuarioDatosLaboralesService usuarioDatosLaboralesService) {
		this.usuarioDatosLaboralesService = usuarioDatosLaboralesService;
	}

	public PersonaRolesService getPersonaRolesService() {
		return personaRolesService;
	}

	public void setPersonaRolesService(PersonaRolesService personaRolesService) {
		this.personaRolesService = personaRolesService;
	}

	public BitacoraEntradaSistemaService getBitacoraEntradaSistemaService() {
		return bitacoraEntradaSistemaService;
	}

	public void setBitacoraEntradaSistemaService(BitacoraEntradaSistemaService bitacoraEntradaSistemaService) {
		this.bitacoraEntradaSistemaService = bitacoraEntradaSistemaService;
	}

	public BitacoraService getBitacoraService() {
		return bitacoraService;
	}

	public void setBitacoraService(BitacoraService bitacoraService) {
		this.bitacoraService = bitacoraService;
	}

}
