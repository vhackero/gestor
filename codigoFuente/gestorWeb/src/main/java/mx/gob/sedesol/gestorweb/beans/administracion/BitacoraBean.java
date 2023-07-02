package mx.gob.sedesol.gestorweb.beans.administracion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import mx.gob.sedesol.basegestor.commons.dto.admin.BitacoraDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.mongo.service.BitacoraService;
import mx.gob.sedesol.basegestor.service.admin.PersonaService;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.dto.Elemento;
import mx.gob.sedesol.gestorweb.commons.dto.TipoElementoEnum;
import mx.gob.sedesol.gestorweb.commons.utils.ObjectUtils;

@ApplicationScoped
@ManagedBean
public class BitacoraBean {

	private final static Logger logger = Logger.getLogger(BitacoraBean.class);
	public static final String IP_HEADER = "X-FORWARDED-FOR";
	public static final String NAVEGADOR_HEADER = "User-Agent";

	private Elemento arbolFuncionalidades;

	@ManagedProperty("#{personaService}")
	private PersonaService personaService;

	@ManagedProperty("#{bitacoraService}")
	private BitacoraService bitacoraService;

	public BitacoraBean() {
		arbolFuncionalidades = new Elemento("MODULOS", "Arbol funcionalidades", TipoElementoEnum.MODULO);
	}

	@PostConstruct
	public void construirArbolFuncionalidades() {

		// ADMINISTRACION
		Elemento administracion = new Elemento("MOD_ADM", "Administración", TipoElementoEnum.MODULO);

		// ACCESO AL SISTEMA
		Elemento entSalSistema = new Elemento("CMP_ING_SIS", "Entradas y salidas del sistema",
				TipoElementoEnum.COMPONENTE);
		Elemento ent1 = new Elemento("ACC_SIS", "Acceso al sistema", TipoElementoEnum.FUNCIONALIDAD);
		Elemento ent2 = new Elemento("SAL_SIS", "Salida del sistema", TipoElementoEnum.FUNCIONALIDAD);
		entSalSistema.getSubElementos().add(ent1);
		entSalSistema.getSubElementos().add(ent2);
		administracion.getSubElementos().add(entSalSistema);

		// USUARIOS
		Elemento usuarios = new Elemento("CMP_USU", "Usuarios", TipoElementoEnum.COMPONENTE);
		Elemento u1 = new Elemento("REG_USU", "Registrar usuario", TipoElementoEnum.FUNCIONALIDAD);
		Elemento u2 = new Elemento("CAR_MAS_USU", "Registrar con carga masiva de usuarios",
				TipoElementoEnum.FUNCIONALIDAD);
		Elemento u3 = new Elemento("BUS_USU", "Buscar usuarios", TipoElementoEnum.FUNCIONALIDAD);
		Elemento u4 = new Elemento("VER_USU", "Ver usuario", TipoElementoEnum.FUNCIONALIDAD);
		Elemento u5 = new Elemento("EDI_USU", "Editar usuario", TipoElementoEnum.FUNCIONALIDAD);
		Elemento u6 = new Elemento("DES_USU", "Desactivar usuario", TipoElementoEnum.FUNCIONALIDAD);
		Elemento u7 = new Elemento("EDI_ROL_USU", "Editar roles de usuario", TipoElementoEnum.FUNCIONALIDAD);
		usuarios.getSubElementos().add(u1);
		usuarios.getSubElementos().add(u2);
		usuarios.getSubElementos().add(u3);
		usuarios.getSubElementos().add(u4);
		usuarios.getSubElementos().add(u5);
		usuarios.getSubElementos().add(u6);
		usuarios.getSubElementos().add(u7);
		administracion.getSubElementos().add(usuarios);

		// ROLES Y PERMISOS
		Elemento rolesPermisos = new Elemento("CMP_RYP", "Roles y permisos", TipoElementoEnum.COMPONENTE);
		Elemento rp1 = new Elemento("CON_ROL", "Consultar roles", TipoElementoEnum.FUNCIONALIDAD);
		Elemento rp2 = new Elemento("CRE_ROL", "Crear rol", TipoElementoEnum.FUNCIONALIDAD);
		Elemento rp3 = new Elemento("EDI_ROL", "Editar rol", TipoElementoEnum.FUNCIONALIDAD);
		Elemento rp4 = new Elemento("VER_PER_ROL", "Ver permisos del rol", TipoElementoEnum.FUNCIONALIDAD);
		Elemento rp5 = new Elemento("EDI_PER_ROL", "Editar permisos del rol", TipoElementoEnum.FUNCIONALIDAD);
		rolesPermisos.getSubElementos().add(rp1);
		rolesPermisos.getSubElementos().add(rp2);
		rolesPermisos.getSubElementos().add(rp3);
		rolesPermisos.getSubElementos().add(rp4);
		rolesPermisos.getSubElementos().add(rp5);
		administracion.getSubElementos().add(rolesPermisos);

		// BITACORAS
		Elemento bitacoras = new Elemento("CMP_BIT", "Bitácoras", TipoElementoEnum.COMPONENTE);
		Elemento b1 = new Elemento("BUS_BIT", "Buscar bitácoras", TipoElementoEnum.FUNCIONALIDAD);
		Elemento b2 = new Elemento("EXP_BIT", "Exportar XLS de bitácora", TipoElementoEnum.FUNCIONALIDAD);
		bitacoras.getSubElementos().add(b1);
		bitacoras.getSubElementos().add(b2);
		administracion.getSubElementos().add(bitacoras);

		// INTERFAZ GRAFICA
		Elemento interfazGrafica = new Elemento("CMP_INT_GRA", "Interfaz gráfica", TipoElementoEnum.COMPONENTE);
		Elemento ig1 = new Elemento("CON_TEM", "Consultar temas", TipoElementoEnum.FUNCIONALIDAD);
		Elemento ig2 = new Elemento("CRE_TEM", "Crear tema", TipoElementoEnum.FUNCIONALIDAD);
		Elemento ig3 = new Elemento("EDI_TEM", "Editar tema", TipoElementoEnum.FUNCIONALIDAD);
		Elemento ig4 = new Elemento("CAR_TEM", "Cargar tema", TipoElementoEnum.FUNCIONALIDAD);
		Elemento ig5 = new Elemento("VER_ARC_TEM", "Ver archivos de tema", TipoElementoEnum.FUNCIONALIDAD);
		Elemento ig6 = new Elemento("ACT_TEM", "Activar tema", TipoElementoEnum.FUNCIONALIDAD);
		interfazGrafica.getSubElementos().add(ig1);
		interfazGrafica.getSubElementos().add(ig2);
		interfazGrafica.getSubElementos().add(ig3);
		interfazGrafica.getSubElementos().add(ig4);
		interfazGrafica.getSubElementos().add(ig5);
		interfazGrafica.getSubElementos().add(ig6);
		administracion.getSubElementos().add(interfazGrafica);

		// EDICION DE TEXTOS
		Elemento edicionTextos = new Elemento("CMP_EDI_TEX", "Edición de textos", TipoElementoEnum.COMPONENTE);
		Elemento et1 = new Elemento("BUS_TEX_SIS", "Buscar textos del sistema", TipoElementoEnum.FUNCIONALIDAD);
		Elemento et2 = new Elemento("EDI_TEX_SIS", "Editar texto del sistema", TipoElementoEnum.FUNCIONALIDAD);
		edicionTextos.getSubElementos().add(et1);
		edicionTextos.getSubElementos().add(et2);
		administracion.getSubElementos().add(edicionTextos);

		// IMAGENES DE DOCUMENTOS
		Elemento imagenesDocumentos = new Elemento("CMP_IMG_DOC", "Imágenes de documentos",
				TipoElementoEnum.COMPONENTE);
		Elemento id1 = new Elemento("CON_IMG_DOC", "Consultar imágenes de documentos", TipoElementoEnum.FUNCIONALIDAD);
		Elemento id2 = new Elemento("SUB_IMG_DOC", "Subir imagen de documento", TipoElementoEnum.FUNCIONALIDAD);
		imagenesDocumentos.getSubElementos().add(id1);
		imagenesDocumentos.getSubElementos().add(id2);
		administracion.getSubElementos().add(imagenesDocumentos);

		// PLANTILLAS DE DOCUMENTOS
		Elemento plantillasDocumentos = new Elemento("CMP_PLA_DOC", "Plantillas de documentos",
				TipoElementoEnum.COMPONENTE);
		Elemento pd1 = new Elemento("BUS_PLA_DOC", "Buscar plantillas de documentos", TipoElementoEnum.FUNCIONALIDAD);
		Elemento pd2 = new Elemento("CRE_PLA_DOC", "Crear plantilla de documento", TipoElementoEnum.FUNCIONALIDAD);
		Elemento pd3 = new Elemento("VER_PLA_DOC", "Ver plantilla de documento", TipoElementoEnum.FUNCIONALIDAD);
		Elemento pd4 = new Elemento("EDI_PLA_DOC", "Editar plantilla de documento", TipoElementoEnum.FUNCIONALIDAD);
		Elemento pd5 = new Elemento("ACT_PLA_DOC", "Activar plantilla de documento", TipoElementoEnum.FUNCIONALIDAD);
		plantillasDocumentos.getSubElementos().add(pd1);
		plantillasDocumentos.getSubElementos().add(pd2);
		plantillasDocumentos.getSubElementos().add(pd3);
		plantillasDocumentos.getSubElementos().add(pd4);
		plantillasDocumentos.getSubElementos().add(pd5);
		administracion.getSubElementos().add(plantillasDocumentos);

		// PARAMETROS DEL SISTEMA
		Elemento parametrosSistema = new Elemento("CMP_PAR_SIS", "Parámetros del sistema", TipoElementoEnum.COMPONENTE);
		Elemento ps1 = new Elemento("CRE_PAR_SIS", "Crear parámetro del sistema", TipoElementoEnum.FUNCIONALIDAD);
		Elemento ps2 = new Elemento("EDI_PAR_SIS", "Editar parámetro del sistema", TipoElementoEnum.FUNCIONALIDAD);
		Elemento ps3 = new Elemento("CON_PAR_SIS", "Consultar parámetros del sistema", TipoElementoEnum.FUNCIONALIDAD);
		parametrosSistema.getSubElementos().add(ps1);
		parametrosSistema.getSubElementos().add(ps2);
		parametrosSistema.getSubElementos().add(ps3);
		administracion.getSubElementos().add(parametrosSistema);

		// TAREAS PROGRAMADAS
		Elemento tareasProgramadas = new Elemento("CMP_TAR_PRO", "Tareas programadas", TipoElementoEnum.COMPONENTE);
		Elemento tp1 = new Elemento("CON_TAR_PRO", "Consultar tareas programadas", TipoElementoEnum.FUNCIONALIDAD);
		Elemento tp2 = new Elemento("CRE_TAR_PRO", "Crear tarea programada", TipoElementoEnum.FUNCIONALIDAD);
		Elemento tp3 = new Elemento("EDI_TAR_PRO", "Editar tarea programada", TipoElementoEnum.FUNCIONALIDAD);
		Elemento tp4 = new Elemento("VER_TAR_PRO", "Ver tarea programada", TipoElementoEnum.FUNCIONALIDAD);
		tareasProgramadas.getSubElementos().add(tp1);
		tareasProgramadas.getSubElementos().add(tp2);
		tareasProgramadas.getSubElementos().add(tp3);
		tareasProgramadas.getSubElementos().add(tp4);
		administracion.getSubElementos().add(tareasProgramadas);

		// CATALOGO DE MODULOS
		Elemento catalogoModulos = new Elemento("CMP_CAT_MOD", "Catálogo de módulos", TipoElementoEnum.COMPONENTE);
		Elemento cm1 = new Elemento("VER_ARB_FUN", "Ver árbol de funcionalidades", TipoElementoEnum.FUNCIONALIDAD);
		Elemento cm2 = new Elemento("CRE_FUN", "Crear funcionalidad", TipoElementoEnum.FUNCIONALIDAD);
		Elemento cm3 = new Elemento("EDI_FUN", "Editar funcionalidad", TipoElementoEnum.FUNCIONALIDAD);
		Elemento cm4 = new Elemento("VER_TEX_SIS_FUN", "Ver textos del sistema en funcionalidad",
				TipoElementoEnum.FUNCIONALIDAD);
		Elemento cm5 = new Elemento("CRE_TEX_SIS_FUN", "Crear texto del sistema en funcionalidad",
				TipoElementoEnum.FUNCIONALIDAD);
		Elemento cm6 = new Elemento("EDI_TEX_SIS_FUN", "Editar texto del sistema en funcionalidad",
				TipoElementoEnum.FUNCIONALIDAD);
		catalogoModulos.getSubElementos().add(cm1);
		catalogoModulos.getSubElementos().add(cm2);
		catalogoModulos.getSubElementos().add(cm3);
		catalogoModulos.getSubElementos().add(cm4);
		catalogoModulos.getSubElementos().add(cm5);
		catalogoModulos.getSubElementos().add(cm6);
		administracion.getSubElementos().add(catalogoModulos);

		// CATALOGO DE LOCALIZACION
		Elemento catalogoLocalizacion = new Elemento("CMP_CAT_LOC", "Catálogo de localización",
				TipoElementoEnum.COMPONENTE);
		Elemento cl1 = new Elemento("BUS_MUN_ENT", "Buscar municipios por entidad", TipoElementoEnum.FUNCIONALIDAD);
		Elemento cl2 = new Elemento("CRE_MUN", "Crear municipio", TipoElementoEnum.FUNCIONALIDAD);
		Elemento cl3 = new Elemento("EDI_MUN", "Editar municipio", TipoElementoEnum.FUNCIONALIDAD);
		Elemento cl4 = new Elemento("CON_ASE_MUN", "Consultar asentamientos por municipio",
				TipoElementoEnum.FUNCIONALIDAD);
		Elemento cl5 = new Elemento("CRE_MUN", "Crear asentamiento", TipoElementoEnum.FUNCIONALIDAD);
		Elemento cl6 = new Elemento("EDI_MUN", "Editar asentamiento", TipoElementoEnum.FUNCIONALIDAD);
		catalogoLocalizacion.getSubElementos().add(cl1);
		catalogoLocalizacion.getSubElementos().add(cl2);
		catalogoLocalizacion.getSubElementos().add(cl3);
		catalogoLocalizacion.getSubElementos().add(cl4);
		catalogoLocalizacion.getSubElementos().add(cl5);
		catalogoLocalizacion.getSubElementos().add(cl6);
		administracion.getSubElementos().add(catalogoLocalizacion);

		// CATALOGO DE RECURSOS
		Elemento catalogoRecursos = new Elemento("CMP_CAT_REC", "Catálogo de recursos", TipoElementoEnum.COMPONENTE);
		Elemento cr1 = new Elemento("CON_TIP_REC", "Consultar por tipo de recurso", TipoElementoEnum.FUNCIONALIDAD);
		Elemento cr2 = new Elemento("CRE_REC", "Crear recurso", TipoElementoEnum.FUNCIONALIDAD);
		Elemento cr3 = new Elemento("ELI_REC", "Eliminar recurso", TipoElementoEnum.FUNCIONALIDAD);
		catalogoRecursos.getSubElementos().add(cr1);
		catalogoRecursos.getSubElementos().add(cr2);
		catalogoRecursos.getSubElementos().add(cr3);
		administracion.getSubElementos().add(catalogoRecursos);

		// CATALOGO DE PLANES Y PROGRAMAS
		Elemento catalogoPlanesProgramas = new Elemento("CMP_CAT_PP", "Catálogo de planes y programas",
				TipoElementoEnum.COMPONENTE);
		Elemento cpp1 = new Elemento("VER_CAT_PP", "Ver catálogos de planes y programas",
				TipoElementoEnum.FUNCIONALIDAD);
		catalogoPlanesProgramas.getSubElementos().add(cpp1);
		administracion.getSubElementos().add(catalogoPlanesProgramas);

		// CATALOGO DE GESTION ESCOLAR
		Elemento catalogoGestionEscolar = new Elemento("CMP_CAT_GE", "Catálogo de gestión escolar",
				TipoElementoEnum.COMPONENTE);
		Elemento cge1 = new Elemento("VER_CAT_GE", "Ver catálogos de gestión escolar", TipoElementoEnum.FUNCIONALIDAD);
		catalogoGestionEscolar.getSubElementos().add(cge1);
		administracion.getSubElementos().add(catalogoGestionEscolar);

		// CATALOGO DE GESTION DEL APRENDIZAJE
		Elemento catalogoGestionAprendizaje = new Elemento("CMP_CAT_GA", "Catálogo de gestión del aprendizaje",
				TipoElementoEnum.COMPONENTE);
		Elemento cga1 = new Elemento("VER_CAT_GA", "Ver catálogos de gestión del aprendizaje",
				TipoElementoEnum.FUNCIONALIDAD);
		catalogoGestionAprendizaje.getSubElementos().add(cga1);
		administracion.getSubElementos().add(catalogoGestionAprendizaje);

		// CATALOGO ENCUESTAS
		Elemento catalogoEncuestas = new Elemento("CMP_CAT_ENC", "Catálogo de encuestas", TipoElementoEnum.COMPONENTE);
		Elemento ce1 = new Elemento("VER_CAT_ENC", "Ver catálogos de encuestas", TipoElementoEnum.FUNCIONALIDAD);
		catalogoEncuestas.getSubElementos().add(ce1);
		administracion.getSubElementos().add(catalogoEncuestas);

		// CATALOGO LOGISTICA E INFRAESTRUCTURA
		Elemento catalogoLogInfra = new Elemento("CMP_CAT_LEI", "Catálogo de logística e infraestructura",
				TipoElementoEnum.COMPONENTE);
		Elemento cli1 = new Elemento("VER_CAT_LEI", "Ver catálogos de logística e infraestructura",
				TipoElementoEnum.FUNCIONALIDAD);
		catalogoLogInfra.getSubElementos().add(cli1);
		administracion.getSubElementos().add(catalogoLogInfra);

		// CATALOGO CONFIGURACION DE SEDES
		Elemento catalogoConfSedes = new Elemento("CMP_CAT_CON_SED", "Catálogo de configuración de sedes",
				TipoElementoEnum.COMPONENTE);
		Elemento cs1 = new Elemento("CON_CAT_CON_SED", "Consultar catálogo de configuración de sedes",
				TipoElementoEnum.FUNCIONALIDAD);
		Elemento cs2 = new Elemento("CRE_SED", "Crear sede", TipoElementoEnum.FUNCIONALIDAD);
		Elemento cs3 = new Elemento("EDI_SED", "Editar sede", TipoElementoEnum.FUNCIONALIDAD);
		Elemento cs4 = new Elemento("VER_SED", "Ver sede", TipoElementoEnum.FUNCIONALIDAD);
		Elemento cs5 = new Elemento("ASI_ARE_SED", "Asignar áreas a sede", TipoElementoEnum.FUNCIONALIDAD);
		Elemento cs6 = new Elemento("ELI_ARE_SED", "Eliminar áreas de sede", TipoElementoEnum.FUNCIONALIDAD);
		catalogoConfSedes.getSubElementos().add(cs1);
		catalogoConfSedes.getSubElementos().add(cs2);
		catalogoConfSedes.getSubElementos().add(cs3);
		catalogoConfSedes.getSubElementos().add(cs4);
		catalogoConfSedes.getSubElementos().add(cs5);
		catalogoConfSedes.getSubElementos().add(cs6);
		administracion.getSubElementos().add(catalogoConfSedes);

		// CATALOGO ORGANISMOS GUBERNAMENTALES
		Elemento catalogoOrgGub = new Elemento("CMP_CAT_ORG_GUB", "Catálogo de organismos gubernamentales",
				TipoElementoEnum.COMPONENTE);
		Elemento og1 = new Elemento("VER_CAT_ORG_GUB", "Ver catálogo de organismos gubernamentales",
				TipoElementoEnum.FUNCIONALIDAD);
		Elemento og2 = new Elemento("CRE_ORG_GUB", "Crear organismo gubernamental", TipoElementoEnum.FUNCIONALIDAD);
		Elemento og3 = new Elemento("ELI_ORG_GUB", "Eliminar organismo gubernamental", TipoElementoEnum.FUNCIONALIDAD);
		catalogoOrgGub.getSubElementos().add(og1);
		catalogoOrgGub.getSubElementos().add(og2);
		catalogoOrgGub.getSubElementos().add(og3);
		administracion.getSubElementos().add(catalogoOrgGub);

		// CATALOGO UNIDADES RESPONSABLES
		Elemento catalogoUniRes = new Elemento("CMP_CAT_UNI_RES", "Catálogo de unidades responsables",
				TipoElementoEnum.COMPONENTE);
		Elemento ur1 = new Elemento("VER_CAT_UNI_RES", "Ver catálogo de unidades responsables",
				TipoElementoEnum.FUNCIONALIDAD);
		catalogoUniRes.getSubElementos().add(ur1);
		administracion.getSubElementos().add(catalogoUniRes);

		// CATALOGO PROGRAMAS SOCIALES
		Elemento catalogoProgSoc = new Elemento("CMP_CAT_PRO_SOC", "Catálogo de programas sociales",
				TipoElementoEnum.COMPONENTE);
		Elemento cps1 = new Elemento("VER_CAT_PRO_SOC", "Ver catálogo de programas sociales",
				TipoElementoEnum.FUNCIONALIDAD);
		catalogoProgSoc.getSubElementos().add(cps1);
		administracion.getSubElementos().add(catalogoProgSoc);

		// CATALOGO CLASIFICACION DE INSIGNIAS
		Elemento catalogoClasifIns = new Elemento("CMP_CAT_CLA", "Catálogo de clasificación de insignias",
				TipoElementoEnum.COMPONENTE);
		Elemento cci1 = new Elemento("VER_CAT_CLA", "Ver catálogo de clasificación de insignias",
				TipoElementoEnum.FUNCIONALIDAD);
		catalogoClasifIns.getSubElementos().add(cci1);
		administracion.getSubElementos().add(catalogoClasifIns);

		// CATALOGO INSIGNIAS
		Elemento catalogoInsignias = new Elemento("CMP_CAT_INS", "Catálogo de insignias", TipoElementoEnum.COMPONENTE);
		Elemento ci1 = new Elemento("VER_CAT_INS", "Ver catálogo de insignias", TipoElementoEnum.FUNCIONALIDAD);
		Elemento ci2 = new Elemento("CRE_INS", "Crear insignia", TipoElementoEnum.FUNCIONALIDAD);
		Elemento ci3 = new Elemento("EDI_INS", "Editar insignia", TipoElementoEnum.FUNCIONALIDAD);
		catalogoInsignias.getSubElementos().add(ci1);
		catalogoInsignias.getSubElementos().add(ci2);
		catalogoInsignias.getSubElementos().add(ci3);
		administracion.getSubElementos().add(catalogoInsignias);

		// PLANES Y PROGRAMAS
		Elemento planesProgramas = new Elemento("MOD_PYP", "Planes y programas", TipoElementoEnum.MODULO);

		// PLANES
		Elemento planes = new Elemento("CMP_PLA", "Planes", TipoElementoEnum.COMPONENTE);
		Elemento p1 = new Elemento("CON_PLA", "Consultar planes", TipoElementoEnum.FUNCIONALIDAD);
		Elemento p2 = new Elemento("CRE_PLA", "Crear plan", TipoElementoEnum.FUNCIONALIDAD);
		Elemento p3 = new Elemento("VER_PLA", "Ver plan", TipoElementoEnum.FUNCIONALIDAD);
		Elemento p4 = new Elemento("EDI_PLA", "Editar plan", TipoElementoEnum.FUNCIONALIDAD);
		Elemento p5 = new Elemento("CLO_PLA", "Clonar plan", TipoElementoEnum.FUNCIONALIDAD);
		planes.getSubElementos().add(p1);
		planes.getSubElementos().add(p2);
		planes.getSubElementos().add(p3);
		planes.getSubElementos().add(p4);
		planes.getSubElementos().add(p5);
		planesProgramas.getSubElementos().add(planes);

		// PROGRAMAS DE CAPACITACION
		Elemento programas = new Elemento("CMP_PRO", "Programas de capacitación", TipoElementoEnum.COMPONENTE);
		Elemento pr1 = new Elemento("GUA_BOR_PRO", "Guardar borrador programa", TipoElementoEnum.FUNCIONALIDAD);
		Elemento pr2 = new Elemento("CON_PRO", "Consultar programas", TipoElementoEnum.FUNCIONALIDAD);
		Elemento pr3 = new Elemento("BLO_PRO", "Bloquear programa", TipoElementoEnum.FUNCIONALIDAD);
		Elemento pr4 = new Elemento("VER_PRO", "Ver programa", TipoElementoEnum.FUNCIONALIDAD);
		Elemento pr5 = new Elemento("VER_PRO_PDF", "Ver programa PDF", TipoElementoEnum.FUNCIONALIDAD);
		Elemento pr6 = new Elemento("CLO_PRO", "Clonar programa", TipoElementoEnum.FUNCIONALIDAD);
		Elemento pr7 = new Elemento("DES_PRO", "Descargar programa", TipoElementoEnum.FUNCIONALIDAD);
		Elemento pr8 = new Elemento("DES_BLOQ_PRO", "Desbloquear programa", TipoElementoEnum.FUNCIONALIDAD);
		Elemento pr9 = new Elemento("FIN_PRO", "Finalizar creación de programa", TipoElementoEnum.FUNCIONALIDAD);
		Elemento pr10 = new Elemento("CAN_PRO", "Cancelar programa", TipoElementoEnum.FUNCIONALIDAD);
		programas.getSubElementos().add(pr1);
		programas.getSubElementos().add(pr2);
		programas.getSubElementos().add(pr3);
		programas.getSubElementos().add(pr4);
		programas.getSubElementos().add(pr5);
		programas.getSubElementos().add(pr6);
		programas.getSubElementos().add(pr7);
		programas.getSubElementos().add(pr8);
		programas.getSubElementos().add(pr9);
		programas.getSubElementos().add(pr10);
		planesProgramas.getSubElementos().add(programas);

		// COMPETENCIAS POR EJE
		Elemento competenciasEje = new Elemento("CMP_COM_EJE", "Competencias por eje", TipoElementoEnum.COMPONENTE);
		Elemento ceje1 = new Elemento("CON_COM_EJE", "Consultar competencias por eje", TipoElementoEnum.FUNCIONALIDAD);
		Elemento ceje2 = new Elemento("EDI_COMP_EJE", "Editar competencias por eje", TipoElementoEnum.FUNCIONALIDAD);
		competenciasEje.getSubElementos().add(ceje1);
		competenciasEje.getSubElementos().add(ceje2);
		planesProgramas.getSubElementos().add(competenciasEje);

		// ADMINISTRAR COMPETENCIAS
		Elemento adminComp = new Elemento("CMP_ADM_COM", "Administrar competencias", TipoElementoEnum.COMPONENTE);
		Elemento ac1 = new Elemento("CON_COM", "Consultar competencias", TipoElementoEnum.FUNCIONALIDAD);
		Elemento ac2 = new Elemento("CRE_COM", "Crear competencia", TipoElementoEnum.FUNCIONALIDAD);
		Elemento ac3 = new Elemento("EDI_COM", "Editar competencia", TipoElementoEnum.FUNCIONALIDAD);
		adminComp.getSubElementos().add(ac1);
		adminComp.getSubElementos().add(ac2);
		adminComp.getSubElementos().add(ac3);
		planesProgramas.getSubElementos().add(adminComp);

		// GESTION ESCOLAR
		Elemento gestionEscolar = new Elemento("MOD_GE", "Gestión escolar", TipoElementoEnum.MODULO);

		// EVENTOS DE CAPACITACION
		Elemento eventosCapacitacion = new Elemento("CMP_EVT", "Eventos de capacitación", TipoElementoEnum.COMPONENTE);
		Elemento evt1 = new Elemento("FIN_CRE_EVT", "Finalizar creación del evento de capacitación",
				TipoElementoEnum.FUNCIONALIDAD);
		Elemento evt2 = new Elemento("CON_EVT", "Consultar eventos de capacitación", TipoElementoEnum.FUNCIONALIDAD);
		Elemento evt3 = new Elemento("VER_EVT_PDF", "Ver ficha del evento de capacitación",
				TipoElementoEnum.FUNCIONALIDAD);
		Elemento evt4 = new Elemento("DES_EVT", "Descargar ficha del evento de capacitación",
				TipoElementoEnum.FUNCIONALIDAD);
		Elemento evt5 = new Elemento("GUA_BOR_EVT", "Guardar borrador evento de capacitación",
				TipoElementoEnum.FUNCIONALIDAD);
		Elemento evt6 = new Elemento("VER_EVT", "Ver evento de capacitación", TipoElementoEnum.FUNCIONALIDAD);
		Elemento evt7 = new Elemento("CRE_GPO", "Crear grupo", TipoElementoEnum.FUNCIONALIDAD);
		Elemento evt8 = new Elemento("ELI_GPO", "Eliminar grupo", TipoElementoEnum.FUNCIONALIDAD);
		Elemento evt9 = new Elemento("MTR_PAR", "Matricular participantes", TipoElementoEnum.FUNCIONALIDAD);
		Elemento evt10 = new Elemento("DES_MTR_PAR", "Desmatricular participantes", TipoElementoEnum.FUNCIONALIDAD);
		Elemento evt11 = new Elemento("REG_DIA_EVT", "Registrar día en evento de capacitación",
				TipoElementoEnum.FUNCIONALIDAD);
		Elemento evt12 = new Elemento("REG_ASI_PAR", "Registrar asistencia de alumnos", TipoElementoEnum.FUNCIONALIDAD);
		Elemento evt13 = new Elemento("REG_CAL", "Registrar calificaciones", TipoElementoEnum.FUNCIONALIDAD);
		Elemento evt14 = new Elemento("CER_ACT", "Cerrar acta", TipoElementoEnum.FUNCIONALIDAD);
		eventosCapacitacion.getSubElementos().add(evt1);
		eventosCapacitacion.getSubElementos().add(evt2);
		eventosCapacitacion.getSubElementos().add(evt3);
		eventosCapacitacion.getSubElementos().add(evt4);
		eventosCapacitacion.getSubElementos().add(evt5);
		eventosCapacitacion.getSubElementos().add(evt6);
		eventosCapacitacion.getSubElementos().add(evt7);
		eventosCapacitacion.getSubElementos().add(evt8);
		eventosCapacitacion.getSubElementos().add(evt9);
		eventosCapacitacion.getSubElementos().add(evt10);
		eventosCapacitacion.getSubElementos().add(evt11);
		eventosCapacitacion.getSubElementos().add(evt12);
		eventosCapacitacion.getSubElementos().add(evt13);
		eventosCapacitacion.getSubElementos().add(evt14);
		gestionEscolar.getSubElementos().add(eventosCapacitacion);

		// RESPONSABLES DE EVENTOS DE CAPACITACION
		Elemento responsablesEventos = new Elemento("CMP_RES_EVT", "Responsables de eventos de capacitación",
				TipoElementoEnum.COMPONENTE);
		Elemento re1 = new Elemento("AGR_RES_EVT", "Agregar responsable de evento de capacitación",
				TipoElementoEnum.FUNCIONALIDAD);
		Elemento re2 = new Elemento("ELI_RES_EVT", "Eliminar responsable de evento de capacitación",
				TipoElementoEnum.FUNCIONALIDAD);
		Elemento re3 = new Elemento("CON_RES_EVT", "Consultar responsable de evento de capacitación",
				TipoElementoEnum.FUNCIONALIDAD);
		responsablesEventos.getSubElementos().add(re1);
		responsablesEventos.getSubElementos().add(re2);
		responsablesEventos.getSubElementos().add(re3);
		gestionEscolar.getSubElementos().add(responsablesEventos);

		// EXPEDIENTE ALUMNO
		Elemento expedienteAlumno = new Elemento("CMP_EXP_ALM", "Expediente alumno", TipoElementoEnum.COMPONENTE);
		Elemento ea1 = new Elemento("CON_EXP_ALM", "Consultar expedientes de alumnos", TipoElementoEnum.FUNCIONALIDAD);
		Elemento ea2 = new Elemento("VER_EXP_ALM", "Ver expediente alumno", TipoElementoEnum.FUNCIONALIDAD);
		Elemento ea3 = new Elemento("VER_CON_ALM", "Ver constancia alumno", TipoElementoEnum.FUNCIONALIDAD);
		expedienteAlumno.getSubElementos().add(ea1);
		expedienteAlumno.getSubElementos().add(ea2);
		expedienteAlumno.getSubElementos().add(ea3);
		gestionEscolar.getSubElementos().add(expedienteAlumno);

		// EXPEDIENTE GRUPO
		Elemento expedienteGrupo = new Elemento("CMP_EXP_GPO", "Expediente grupo", TipoElementoEnum.COMPONENTE);
		Elemento eg1 = new Elemento("CON_EXP_ALM_GPO", "Consultar expediente de alumnos en grupo",
				TipoElementoEnum.FUNCIONALIDAD);
		Elemento eg2 = new Elemento("VER_CON_ALM", "Ver constancia del alumno", TipoElementoEnum.FUNCIONALIDAD);
		Elemento eg3 = new Elemento("DES_CON_ALM", "Descargar constancia del alumno", TipoElementoEnum.FUNCIONALIDAD);
		Elemento eg4 = new Elemento("DES_TOD_CON_GPO", "Descargar todas las constancias del grupo",
				TipoElementoEnum.FUNCIONALIDAD);
		expedienteGrupo.getSubElementos().add(eg1);
		expedienteGrupo.getSubElementos().add(eg2);
		expedienteGrupo.getSubElementos().add(eg3);
		expedienteGrupo.getSubElementos().add(eg4);
		gestionEscolar.getSubElementos().add(expedienteGrupo);

		// GESTION DEL APRENDIZAJE
		Elemento gestionAprendizaje = new Elemento("MOD_GA", "Gestión del aprendizaje", TipoElementoEnum.MODULO);

		// AMBIENTES VIRTUALES DE APRENDIZAJE
		Elemento avas = new Elemento("CMP_AVAS", "Ambientes virtuales de aprendizaje", TipoElementoEnum.COMPONENTE);
		Elemento ava1 = new Elemento("CON_AVA", "Consultar AVAS", TipoElementoEnum.FUNCIONALIDAD);
		Elemento ava2 = new Elemento("VER_FOA_PDF", "Ver FOA PDF", TipoElementoEnum.FUNCIONALIDAD);
		Elemento ava3 = new Elemento("VER_AVA", "Ver AVA", TipoElementoEnum.FUNCIONALIDAD);
		Elemento ava4 = new Elemento("DCT_AVA", "Desactivar AVA", TipoElementoEnum.FUNCIONALIDAD);
		Elemento ava5 = new Elemento("IR_AVA_MDL", "Ir al AVA en Moodle", TipoElementoEnum.FUNCIONALIDAD);
		Elemento ava6 = new Elemento("ASI_RES_PRO", "Asignar responsable de producción",
				TipoElementoEnum.FUNCIONALIDAD);
		Elemento ava7 = new Elemento("ELI_RES_PRO", "Eliminar responsable de producción",
				TipoElementoEnum.FUNCIONALIDAD);
		Elemento ava8 = new Elemento("ACT_AVA", "Activar AVA", TipoElementoEnum.FUNCIONALIDAD);
		Elemento ava9 = new Elemento("ASG_REC_UNI_DID", "Asignar recurso a unidad didáctica",
				TipoElementoEnum.FUNCIONALIDAD);
		Elemento ava10 = new Elemento("ELI_REC_UNI_DID", "Eliminar recurso de unidad didáctica",
				TipoElementoEnum.FUNCIONALIDAD);
		Elemento ava11 = new Elemento("ASI_COL_PRO_OA", "Asignar colaboradores de producción",
				TipoElementoEnum.FUNCIONALIDAD);
		Elemento ava12 = new Elemento("GUA_BOR_FOA", "Guardar borrador FOA", TipoElementoEnum.FUNCIONALIDAD);
		Elemento ava13 = new Elemento("FIN_FOA", "Finalizar FOA", TipoElementoEnum.FUNCIONALIDAD);
		Elemento ava14 = new Elemento("VAL_GUI_INS", "Validación de guión instruccional",
				TipoElementoEnum.FUNCIONALIDAD);
		Elemento ava15 = new Elemento("VAL_DES_OAS", "Validación del desarrollo del OA",
				TipoElementoEnum.FUNCIONALIDAD);
		Elemento ava16 = new Elemento("VAL_SCO", "Validación del SCORM", TipoElementoEnum.FUNCIONALIDAD);
		Elemento ava17 = new Elemento("EMP_CON_AVA", "Empezar construcción del AVA", TipoElementoEnum.FUNCIONALIDAD);
		Elemento ava18 = new Elemento("ARC_AVA", "Archivar AVA", TipoElementoEnum.FUNCIONALIDAD);
		avas.getSubElementos().add(ava1);
		avas.getSubElementos().add(ava2);
		avas.getSubElementos().add(ava3);
		avas.getSubElementos().add(ava4);
		avas.getSubElementos().add(ava5);
		avas.getSubElementos().add(ava6);
		avas.getSubElementos().add(ava7);
		avas.getSubElementos().add(ava8);
		avas.getSubElementos().add(ava9);
		avas.getSubElementos().add(ava10);
		avas.getSubElementos().add(ava11);
		avas.getSubElementos().add(ava12);
		avas.getSubElementos().add(ava13);
		avas.getSubElementos().add(ava14);
		avas.getSubElementos().add(ava15);
		avas.getSubElementos().add(ava16);
		avas.getSubElementos().add(ava17);
		avas.getSubElementos().add(ava18);
		gestionAprendizaje.getSubElementos().add(avas);

		// MIS LOGROS
		Elemento misLogros = new Elemento("MOD_MIS_LOG", "Mis logros", TipoElementoEnum.MODULO);
		// MIS LOGROS
		Elemento componenteMisLogros = new Elemento("CMP_MIS_LOG", "Mis logros", TipoElementoEnum.COMPONENTE);
		Elemento ml1 = new Elemento("VER_MIS_LOG", "Ver mis logros", TipoElementoEnum.FUNCIONALIDAD);
		componenteMisLogros.getSubElementos().add(ml1);
		misLogros.getSubElementos().add(componenteMisLogros);

		// MIS CURSOS
		Elemento misCursos = new Elemento("MOD_MIS_CUR", "Mis cursos", TipoElementoEnum.MODULO);
		// MIS CURSOS
		Elemento componenteMisCursos = new Elemento("CMP_MIS_CUR", "Mis cursos", TipoElementoEnum.COMPONENTE);
		Elemento mc1 = new Elemento("VER_MIS_CUR", "Ver mis cursos", TipoElementoEnum.FUNCIONALIDAD);
		componenteMisCursos.getSubElementos().add(mc1);
		misCursos.getSubElementos().add(componenteMisCursos);

		// EXPEDIENTE ACADEMICO
		Elemento expedienteAcademico = new Elemento("MOD_EXP_ACA", "Expediente académico", TipoElementoEnum.MODULO);

		// EXPEDIENTE ACADEMICO
		Elemento componenteExpedienteAcademico = new Elemento("CMP_EXP_ACA", "Expediente académico",
				TipoElementoEnum.COMPONENTE);
		Elemento eaca1 = new Elemento("VER_EXP_ACA", "Ver expediente académico", TipoElementoEnum.FUNCIONALIDAD);
		Elemento eaca2 = new Elemento("GEN_EXP_ACA_PDF", "Generar expediente académico en PDF",
				TipoElementoEnum.FUNCIONALIDAD);
		Elemento eaca3 = new Elemento("VER_CON_EXP_ACA", "Ver constancia", TipoElementoEnum.FUNCIONALIDAD);
		componenteExpedienteAcademico.getSubElementos().add(eaca1);
		componenteExpedienteAcademico.getSubElementos().add(eaca2);
		componenteExpedienteAcademico.getSubElementos().add(eaca3);
		expedienteAcademico.getSubElementos().add(componenteExpedienteAcademico);

		// MI PERFIL
		Elemento miPerfil = new Elemento("MOD_PER", "Mi perfil", TipoElementoEnum.MODULO);

		// MI PERFIL
		Elemento componenteMiPerfil = new Elemento("CMP_PER", "Mi perfil", TipoElementoEnum.COMPONENTE);
		Elemento mp1 = new Elemento("VER_PER", "Ver mi perfil", TipoElementoEnum.FUNCIONALIDAD);
		Elemento mp2 = new Elemento("EDI_PER", "Editar mi perfil", TipoElementoEnum.FUNCIONALIDAD);
		componenteMiPerfil.getSubElementos().add(mp1);
		componenteMiPerfil.getSubElementos().add(mp2);
		miPerfil.getSubElementos().add(componenteMiPerfil);

		arbolFuncionalidades.getSubElementos().add(administracion);
		arbolFuncionalidades.getSubElementos().add(planesProgramas);
		arbolFuncionalidades.getSubElementos().add(gestionEscolar);
		arbolFuncionalidades.getSubElementos().add(gestionAprendizaje);
		arbolFuncionalidades.getSubElementos().add(misLogros);
		arbolFuncionalidades.getSubElementos().add(misCursos);
		arbolFuncionalidades.getSubElementos().add(expedienteAcademico);
		arbolFuncionalidades.getSubElementos().add(miPerfil);

	}

	public List<Elemento> obtenerSubElementosPorClave(String claveRecibida) {

		for (Elemento modulo : arbolFuncionalidades.getSubElementos()) {
			if (modulo.getClave().equals(claveRecibida)) {
				return modulo.getSubElementos();
			}
			for (Elemento componente : modulo.getSubElementos()) {
				if (componente.getClave().equals(claveRecibida)) {
					return componente.getSubElementos();
				}
			}
		}
		return new ArrayList<>();
	}

	/***
	 * Metodo que crea el DTO de bitacora y lo persiste.
	 * 
	 * @param idPersona
	 * @param modulo
	 * @param funcionalidad
	 * @param idElementoAfectado
	 * @param request
	 * @param tipoServicio
	 */
	public void guardarBitacora(Long idPersona, String clave, String idElementoAfectado, HttpServletRequest request,
			TipoServicioEnum tipoServicio) {

		PersonaDTO persona = personaService.buscarPorId(idPersona);

		BitacoraDTO bitacora = new BitacoraDTO();

		bitacora.setIdUsuario(String.valueOf(persona.getIdPersona()));
		bitacora.setUsuario(persona.getUsuario());
		bitacora.setNombreCompleto(persona.getNombreCompleto());
		bitacora.setClaveModulo(obtenerClaveModulo(clave));
		bitacora.setModulo(obtenerNombreModulo(clave));
		bitacora.setClaveComponente(obtenerClaveComponente(clave));
		bitacora.setComponente(obtenerNombreComponente(clave));
		bitacora.setClaveFuncionalidad(clave);
		bitacora.setFuncionalidad(obtenerNombreFuncionalidad(clave));
		bitacora.setIdElementoAfectado(idElementoAfectado);
		bitacora.setFechaRegistro(new Date());
		bitacora.setIp(obtenerIp(request));
		bitacora.setNavegador(obtenerNombreNavegador(request));
		bitacora.setTipoServicio(tipoServicio.getDescripcion());
		bitacoraService.guardarBitacora(bitacora);

	}

	private String obtenerClaveModulo(String clave) {
		for (Elemento modulo : arbolFuncionalidades.getSubElementos()) {

			for (Elemento componente : modulo.getSubElementos()) {

				for (Elemento funcionalidad : componente.getSubElementos()) {

					if (funcionalidad.getClave().equals(clave)) {
						return modulo.getClave();
					}
				}
			}
		}
		return null;
	}

	private String obtenerNombreModulo(String clave) {
		for (Elemento modulo : arbolFuncionalidades.getSubElementos()) {

			for (Elemento componente : modulo.getSubElementos()) {

				for (Elemento funcionalidad : componente.getSubElementos()) {

					if (funcionalidad.getClave().equals(clave)) {
						return modulo.getNombreElemento();
					}
				}
			}
		}
		return null;
	}

	private String obtenerClaveComponente(String clave) {
		for (Elemento modulo : arbolFuncionalidades.getSubElementos()) {

			for (Elemento componente : modulo.getSubElementos()) {

				for (Elemento funcionalidad : componente.getSubElementos()) {

					if (funcionalidad.getClave().equals(clave)) {
						return componente.getClave();
					}
				}
			}
		}
		return null;
	}

	private String obtenerNombreComponente(String clave) {
		for (Elemento modulo : arbolFuncionalidades.getSubElementos()) {

			for (Elemento componente : modulo.getSubElementos()) {

				for (Elemento funcionalidad : componente.getSubElementos()) {

					if (funcionalidad.getClave().equals(clave)) {
						return componente.getNombreElemento();
					}
				}
			}
		}
		return null;
	}

	private String obtenerNombreFuncionalidad(String clave) {
		for (Elemento modulo : arbolFuncionalidades.getSubElementos()) {

			for (Elemento componente : modulo.getSubElementos()) {

				for (Elemento funcionalidad : componente.getSubElementos()) {

					if (funcionalidad.getClave().equals(clave)) {
						return funcionalidad.getNombreElemento();
					}
				}
			}
		}
		return null;
	}

	private String obtenerIp(HttpServletRequest request) {
		if (ObjectUtils.isNullOrEmpty(request.getHeader(IP_HEADER))) {
			return request.getRemoteAddr();
		} else {
			return request.getHeader(IP_HEADER);
		}
	}

	private String obtenerNombreNavegador(HttpServletRequest request) {
		UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader(NAVEGADOR_HEADER));
		Browser browser = userAgent.getBrowser();
		return browser.getName();
	}

	public PersonaService getPersonaService() {
		return personaService;
	}

	public void setPersonaService(PersonaService personaService) {
		this.personaService = personaService;
	}

	public BitacoraService getBitacoraService() {
		return bitacoraService;
	}

	public void setBitacoraService(BitacoraService bitacoraService) {
		this.bitacoraService = bitacoraService;
	}

	public Elemento getArbolFuncionalidades() {
		return arbolFuncionalidades;
	}

	public void setArbolFuncionalidades(Elemento arbolFuncionalidades) {
		this.arbolFuncionalidades = arbolFuncionalidades;
	}

}
