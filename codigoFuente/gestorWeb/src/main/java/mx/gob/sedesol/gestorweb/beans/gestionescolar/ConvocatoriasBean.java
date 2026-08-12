package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import mx.gob.sedesol.basegestor.commons.dto.admin.ActividadDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.EstatusDTO;
import mx.gob.sedesol.basegestor.commons.utils.DateUtils;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Convocatoria;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaNivelEducativo;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaNivelEducativoCompl;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaParamConsulta;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaParamNueva;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaTableroResumen;
import mx.gob.sedesol.basegestor.service.gestionescolar.ConvocatoriaService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;

@ManagedBean
@ViewScoped
public class ConvocatoriasBean extends BaseBean {

	public TimeZone getZonaHoraria() {
		return TimeZone.getDefault();
	}

	/**
	 * Serialization
	 */
	private static final long serialVersionUID = 5929433407465074144L;

	private static final Logger logger = Logger.getLogger(ConvocatoriasBean.class);

	@ManagedProperty("#{convocatoriaService}")
	private ConvocatoriaService convocatoriaService;

	// REDIRECCION OPCIONES
	private String paginaActual;
	
	private Convocatoria editarConv;

	public Convocatoria getEditarConv() {
		return editarConv;
	}

	public void setEditarConv(Convocatoria editarConv) {
		this.editarConv = editarConv;
	}

	// nivel educativo
	private List<ConvocatoriaNivelEducativo> listaNivelEducativo;
	private List<ConvocatoriaNivelEducativoCompl> listaNivelEducativoCompl;
	private List<ConvocatoriaNivelEducativoCompl> listaNivelEducativoCompl2;
	


	int valueConvocatoriaNivel;

	// CONSULTA TABLERO
	List<Convocatoria> listaConvocatoria;
	List<Convocatoria> listaConvocatoria2;
	List<ConvocatoriaNivelEducativoCompl> listaPlanesProgramas;
	List<ConvocatoriaTableroResumen> listaTableResumen;
	int valueConvocatoria;

	public List<ConvocatoriaNivelEducativoCompl> getDesmarcados() {
		return desmarcados;
	}

	public void setDesmarcados(List<ConvocatoriaNivelEducativoCompl> desmarcados) {
		this.desmarcados = desmarcados;
	}

	private List<ConvocatoriaNivelEducativoCompl> desmarcados = new ArrayList<>();

	
	// CONSULTA CONVOCATORIA
	private List<EstatusDTO> estatusLista;
	private List<EstatusDTO> estatusLista2;

	// ALTA CONVOCATORIA
	private String altaNombreConvocatoria;
	private String altaNombreCorto;
	private String altaDescripcion;
	private Date altaFechaApertura;
	private Date altaFechaCierre;
	private Integer altaNivelEducativo;
	private String altaUrl;
	private Integer altaEstatus;
	private Date altaFechaAlta;
	private Integer altaCupoLimite;
	
	private ConvocatoriaParamNueva convocatoriaParamNueva = new ConvocatoriaParamNueva();

	// CONSULTA CONVOCATORIA
	private ConvocatoriaParamConsulta convocatoriaParamConsulta = new ConvocatoriaParamConsulta();
	
	public String consulNombreConvocatoria;
	private String consulNombreCorto;
	private String consulFechaApertura;
	private String consulFechaCierre;
	private Integer consulNivelEducativo;
	private Integer valueConvocatoriaEstatus;
	
	
	Convocatoria elminarConvo ;
	
	private boolean mostrarConsultaConvocatoria = true;
	private boolean mostrarNuevaConvocatoria = false;
	
	@PostConstruct
    public void init() {
        convocatoriaParamNueva = new ConvocatoriaParamNueva(); // Inicializar el objeto
        cargarCatalogosConvocatoria();
    }
	
	public ConvocatoriasBean (){
		inicializarEstatus();
	}

	private void inicializarEstatus() {
		if (estatusLista == null || estatusLista.isEmpty()) {
			estatusLista = new ArrayList<EstatusDTO>();
			estatusLista.add(new EstatusDTO(1, "ACTIVO"));
			estatusLista.add(new EstatusDTO(0, "INACTIVO"));
		}
	}

	private void cargarCatalogosConvocatoria() {
		inicializarEstatus();
		if (convocatoriaService == null) {
			if (listaNivelEducativo == null) {
				listaNivelEducativo = new ArrayList<ConvocatoriaNivelEducativo>();
			}
			if (listaNivelEducativoCompl == null) {
				listaNivelEducativoCompl = new ArrayList<ConvocatoriaNivelEducativoCompl>();
			}
			return;
		}

		try {
			if (listaNivelEducativo == null || listaNivelEducativo.isEmpty()) {
				listaNivelEducativo = convocatoriaService.consultarNivelEducativo();
			}
			if (listaNivelEducativoCompl == null || listaNivelEducativoCompl.isEmpty()) {
				listaNivelEducativoCompl = convocatoriaService.consultarNivelEducativoCompleto();
			}
		} catch (Exception e) {
			logger.error("Error al cargar los catalogos de convocatoria", e);
		}

		if (listaNivelEducativo == null) {
			listaNivelEducativo = new ArrayList<ConvocatoriaNivelEducativo>();
		}
		if (listaNivelEducativoCompl == null) {
			listaNivelEducativoCompl = new ArrayList<ConvocatoriaNivelEducativoCompl>();
		}
		logger.info("Estatus cargados: " + estatusLista.size()
				+ ", niveles educativos: " + listaNivelEducativo.size()
				+ ", niveles educativos completos: " + listaNivelEducativoCompl.size());
	}

	
	// redireccion opciones y llenado de campos

	public String navegaNuevoConvocatoria() throws Exception {
		
		
		listaNivelEducativoCompl = new ArrayList<ConvocatoriaNivelEducativoCompl>();
		
		
		this.paginaActual = "/views/private/gestionAprendizaje/alumnoView/nuevaConvocatoria.xhtml";
		listaTableResumen = new ArrayList<ConvocatoriaTableroResumen>();
		valueConvocatoria = 0;
		listaConvocatoria2 = new ArrayList<Convocatoria>();
		
		convocatoriaParamNueva = new ConvocatoriaParamNueva();
		convocatoriaParamNueva.setAltaFechaAlta(new Date());
		
		consultarNivelEducativoCompleto();
		
		return null; // Mantener en la misma página
	}

	public String navegaConsultaConvocatoria() throws Exception {
		this.paginaActual = "/views/private/gestionAprendizaje/alumnoView/cosultaConvocatoria.xhtml";
		this.mostrarConsultaConvocatoria = true;
		this.mostrarNuevaConvocatoria = false;
		listaTableResumen = new ArrayList<ConvocatoriaTableroResumen>();
		valueConvocatoria = 0;
		
		cargarCatalogosConvocatoria();
		return null;
	}

	public String navegaConsultaTablero() throws Exception {
		this.paginaActual = "/views/private/gestionAprendizaje/alumnoView/cosultaTableroConvocatoria.xhtml";
		consultarConvocatorias();
		listaConvocatoria2 = new ArrayList<Convocatoria>();
		return null;
	}
	
	
	public void cancelar() {
		
		this.mostrarConsultaConvocatoria = false;
		this.mostrarNuevaConvocatoria = false;
		listaConvocatoria2 = new ArrayList<Convocatoria>();
		listaTableResumen = new ArrayList<ConvocatoriaTableroResumen>();
		valueConvocatoria = 0;
		
	}
	
	
	public void cancelar2() {
		
		 this.mostrarConsultaConvocatoria = true;
		 this.mostrarNuevaConvocatoria = false;
		 this.editarConv = null;
		
	}
	
	public void onChange() {
	    // Lista para almacenar elementos deseleccionados
	    List<ConvocatoriaNivelEducativoCompl> deseleccionados = new ArrayList<>();

	    // Obtener la lista seleccionada (posiblemente Strings)
	    List<?> listaSeleccionados = convocatoriaParamNueva.getListaPlanProgramaNivel();
	    List<ConvocatoriaNivelEducativoCompl> listaOriginal = convocatoriaParamNueva.getListaPlanProgramaMarcados();
	    if (listaSeleccionados == null) {
	        listaSeleccionados = new ArrayList<Object>();
	    }
	    if (listaOriginal == null) {
	        listaOriginal = new ArrayList<ConvocatoriaNivelEducativoCompl>();
	    }

	    // Verifica si los elementos de la lista original no están en la lista seleccionada
	    for (ConvocatoriaNivelEducativoCompl original : listaOriginal) {
	        boolean encontrado = false;

	        for (Object seleccionado : listaSeleccionados) {
	            ConvocatoriaNivelEducativoCompl elementoSeleccionado;

	            // Si el objeto es un String, convierte usando tu patrón
	            if (seleccionado instanceof String) {
	                elementoSeleccionado = convertirStringAObjeto((String) seleccionado);
	            } else {
	                elementoSeleccionado = (ConvocatoriaNivelEducativoCompl) seleccionado;
	            }

	            // Compara los elementos por ID (puedes ajustar si hay más atributos relevantes)
	            if (Objects.equals(elementoSeleccionado.getIdNivelEnsenanza(), original.getIdNivelEnsenanza()) &&
	                Objects.equals(elementoSeleccionado.getIdPlan(), original.getIdPlan()) &&
	                Objects.equals(elementoSeleccionado.getIdPrograma(), original.getIdPrograma())) {
	                encontrado = true;
	                break;
	            }
	        }

	        // Si no se encuentra en la lista seleccionada, significa que fue desmarcado
	        if (!encontrado) {
	            deseleccionados.add(original);
	        }
	    }
	    convocatoriaParamNueva.setListaPlanProgramaEliminar(deseleccionados);
	    // Imprimir o procesar los deseleccionados
	    logger.info("Elementos deseleccionados: " + convocatoriaParamNueva.getListaPlanProgramaEliminar());
	}

	// Método para convertir un String en un objeto ConvocatoriaNivelEducativoCompl
	private ConvocatoriaNivelEducativoCompl convertirStringAObjeto(String seleccionado) {
	    Pattern pattern = Pattern.compile("id\\w+=(\\d+)");
	    Matcher matcher = pattern.matcher(seleccionado);

	    int idNivelEnsenanza = -1;
	    int idPlan = -1;
	    int idPrograma = -1;

	    int index = 0;
	    while (matcher.find()) {
	        int value = Integer.parseInt(matcher.group(1));
	        if (index == 0) {
	            idNivelEnsenanza = value;
	        } else if (index == 1) {
	            idPlan = value;
	        } else if (index == 2) {
	            idPrograma = value;
	        }
	        index++;
	    }

	    ConvocatoriaNivelEducativoCompl elemento = new ConvocatoriaNivelEducativoCompl();
	    elemento.setIdNivelEnsenanza(idNivelEnsenanza);
	    elemento.setIdPlan(idPlan);
	    elemento.setIdPrograma(idPrograma);

	    return elemento;
	}

	
	public void editarConvocatoria() {
	        logger.info(" INICIA EDITAR ");
	        if (editarConv == null) {
	            logger.error("No se selecciono una convocatoria para editar");
	            agregarMsgError("Error", "No fue posible identificar la convocatoria seleccionada.");
	            return;
	        }

	        // Validar parámetros


	        // Validar datos requeridos

	            // Consulta los datos dinámicos
	            List<Convocatoria> detalleConvocatoria = convocatoriaService.consultarConvocatoriasId(editarConv);
	            listaPlanesProgramas = convocatoriaService.consultarPlanesProgramasId(editarConv);

	            if (detalleConvocatoria == null || detalleConvocatoria.isEmpty()) {
	                agregarMsgError("Error", "No se encontro la convocatoria seleccionada.");
	                return;
	            }

	            Convocatoria convocatoriaSeleccionada = detalleConvocatoria.get(0);

	        // Actualiza el DTO
	        convocatoriaParamNueva.setAltaCupoLimite(convocatoriaSeleccionada.getCupoLimite() == null
	                ? "0" : convocatoriaSeleccionada.getCupoLimite().toString());
	        convocatoriaParamNueva.setAltaDescripcion(convocatoriaSeleccionada.getDescripcion());
	        String estatusConvocatoria = convocatoriaSeleccionada.getActivo();
	        if (estatusConvocatoria != null) {
	            estatusConvocatoria = estatusConvocatoria.trim();
	        }
	        if ("1".equals(estatusConvocatoria) || "ACTIVO".equalsIgnoreCase(estatusConvocatoria)) {
	            convocatoriaParamNueva.setAltaEstatus("1");
	        } else if ("0".equals(estatusConvocatoria) || "INACTIVO".equalsIgnoreCase(estatusConvocatoria)) {
	            convocatoriaParamNueva.setAltaEstatus("0");
	        } else {
	            convocatoriaParamNueva.setAltaEstatus(null);
	        }
	        convocatoriaParamNueva.setAltaFechaAlta(convocatoriaSeleccionada.getFechaAlta());
	        convocatoriaParamNueva.setAltaFechaApertura(convocatoriaSeleccionada.getFecha_Apertura());
	        convocatoriaParamNueva.setAltaFechaCierre(convocatoriaSeleccionada.getFechaCierre());
	        convocatoriaParamNueva.setAltaNombreConvocatoria(convocatoriaSeleccionada.getNombre());
	        convocatoriaParamNueva.setAltaNombreCorto(convocatoriaSeleccionada.getNombreCorto());
	        convocatoriaParamNueva.setAltaUrl(convocatoriaSeleccionada.getUrlConvocatoria());

	        // Carga completa de niveles educativos
	        cargarCatalogosConvocatoria();
	        convocatoriaParamNueva.setListaPlanPrograma(listaNivelEducativoCompl);

	        // Configurar seleccionados
	        List<ConvocatoriaNivelEducativoCompl> seleccionados = new ArrayList<>();
	        if (listaPlanesProgramas == null) {
	            listaPlanesProgramas = new ArrayList<ConvocatoriaNivelEducativoCompl>();
	        }
	        for (ConvocatoriaNivelEducativoCompl plan : listaNivelEducativoCompl) {
	            for (ConvocatoriaNivelEducativoCompl seleccionado : listaPlanesProgramas) {
	                if (Objects.equals(plan.getIdNivelEnsenanza(), seleccionado.getIdNivelEnsenanza())
	                        && Objects.equals(plan.getIdPlan(), seleccionado.getIdPlan())
	                        && Objects.equals(plan.getIdPrograma(), seleccionado.getIdPrograma())) {
	                    seleccionados.add(plan);
	                }
	            }
	        }
	        
	        convocatoriaParamNueva.setListaPlanProgramaNivel(seleccionados);
	        
	        convocatoriaParamNueva.setListaPlanProgramaMarcados(seleccionados);
	        convocatoriaParamNueva.setListaPlanProgramaEliminar(new ArrayList<ConvocatoriaNivelEducativoCompl>());

	        // Cambiar visibilidad de componentes
	        this.mostrarNuevaConvocatoria = true;
	        this.mostrarConsultaConvocatoria = false;

	        logger.info(" TERMINA EDITAR ");
	}

	public void cancelarNuevaConvocatoria() {
		this.paginaActual = "";
		listaConvocatoria2 = new ArrayList<Convocatoria>();
		listaTableResumen = new ArrayList<ConvocatoriaTableroResumen>();
		valueConvocatoria = 0;
	}

	
	
	public void editarConvocatoriaBD() {
		
		logger.info(" INICIA EDITAR  ");
		
		//realizar update en bd
		
		logger.info("***********************Inicio Alta Convocatoria ID***********************");
		
		logger.info("nombre de la convocaria    : " + convocatoriaParamNueva.getAltaNombreConvocatoria());
		logger.info("nombre corto               : " + convocatoriaParamNueva.getAltaNombreCorto());
		logger.info("descripcion                : " + convocatoriaParamNueva.getAltaDescripcion());
		logger.info("fecha de apertura          : " + convocatoriaParamNueva.getAltaFechaApertura());
		logger.info("fecha cierre               : " + convocatoriaParamNueva.getAltaFechaCierre());
		logger.info("nivel educativo            : " + convocatoriaParamNueva.getListaNivelEducativoCompl());
		logger.info("url                        : " + convocatoriaParamNueva.getAltaUrl());
		logger.info("estatus                    : " + convocatoriaParamNueva.getAltaEstatus());
		logger.info("fecha alta                 : " + convocatoriaParamNueva.getAltaFechaAlta());
		logger.info("cupo limite                : " + convocatoriaParamNueva.getAltaCupoLimite());
		
		if( "".equals(convocatoriaParamNueva.getAltaNombreConvocatoria()) ||
			"".equals(convocatoriaParamNueva.getAltaNombreCorto()) ||
			convocatoriaParamNueva.getAltaFechaApertura() == null  || 
			convocatoriaParamNueva.getAltaEstatus() == null  || 
			/*convocatoriaParamNueva.getListaNivelEducativoCompl() == null  ||*/ 
			convocatoriaParamNueva.getAltaFechaCierre() == null ) {
				agregarMsgError("Error", "Ingrese los datos marcados como obligatorios.");
				return;
			} else {
				if (convocatoriaParamNueva.getAltaFechaCierre()
						.before(convocatoriaParamNueva.getAltaFechaApertura())) {
					agregarMsgError("Error", "La fecha de cierre no puede ser anterior a la fecha de apertura.");
					return;
				}
				
				if("".equals(convocatoriaParamNueva.getAltaCupoLimite())) {
					convocatoriaParamNueva.setAltaCupoLimite("0");
				}
				
				if (editarConv == null || editarConv.getConvocatoriaId() == null) {
					agregarMsgError("Error", "No fue posible identificar la convocatoria seleccionada.");
					return;
				}
				Integer convocatoriaId = editarConv.getConvocatoriaId();

				try {
					convocatoriaService.actualizarConvocatorias(convocatoriaParamNueva, convocatoriaId);
				} catch (Exception e) {
					logger.error("Error al actualizar la convocatoria", e);
					agregarMsgError("Error", "Ocurrio un problema al actualizar la convocatoria.");
					return;
				}
			}

		logger.info("***********************Termina Alta Convocatoria***********************");
		agregarMsgInfo("La convocatoria se actualizo correctamente.", null);
		
		//llenar dto consulta nombre con las variables de alta nombre que se utilizaron en el update
		convocatoriaParamConsulta = new ConvocatoriaParamConsulta();
		convocatoriaParamConsulta.setConsulNombreConvocatoria(convocatoriaParamNueva.getAltaNombreConvocatoria());
		convocatoriaParamConsulta.setConsulFechaApertura(convocatoriaParamNueva.getAltaFechaApertura());
		convocatoriaParamConsulta.setConsulFechaCierre(convocatoriaParamNueva.getAltaFechaCierre());
		convocatoriaParamConsulta.setConsulNivelEducativo(convocatoriaParamConsulta.getConsulNivelEducativo());
		convocatoriaParamConsulta.setConsulNombreCorto(convocatoriaParamNueva.getAltaNombreCorto());
		convocatoriaParamConsulta.setValueConvocatoriaEstatus(convocatoriaParamNueva.getAltaEstatus());
		
		// ejecutas el consulta filtros	
		
		try {
			consultarFiltros();
		} catch (Exception e) {
			logger.error("La convocatoria se actualizo, pero no fue posible refrescar la consulta", e);
			agregarMsgWarn("La convocatoria se actualizo, pero no fue posible refrescar los resultados.", null);
		}
		
		 
		 this.mostrarConsultaConvocatoria = true;
		 this.mostrarNuevaConvocatoria = false;
//		 convocatoriaParamConsulta = new ConvocatoriaParamConsulta();

		logger.info(" TERMINA EDITAR  ");
		
	}
	
	
		
	public List<EstatusDTO> getEstatusLista2() {
		return estatusLista2;
	}

	public void setEstatusLista2(List<EstatusDTO> estatusLista2) {
		this.estatusLista2 = estatusLista2;
	}

	public void eliminar() {

		logger.info(" INICIA ELIMINAR  ");
		if (elminarConvo == null) {
			agregarMsgError("Error", "No fue posible identificar la convocatoria seleccionada.");
			return;
		}

		try {
			if (esFechaActual(elminarConvo.getFecha_Apertura().toString())) {
				agregarMsgError("Error", "La convocatoria no puede eliminarse porque su fecha de apertura es igual o anterior al dia actual.");
			} else {
				convocatoriaService.eliminarConvocatorias(elminarConvo);
				consultarFiltros();
				agregarMsgInfo("La convocatoria se elimino correctamente.", null);
			}
		} catch (Exception e) {
			logger.error("Error al eliminar la convocatoria", e);
			agregarMsgError("Error", "Ocurrió un problema al eliminar la convocatoria.");
		}

		logger.info(" TERMINA ELIMINAR  ");

	}
	
	public boolean esFechaActual(String fechaStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        try {
            Date fechaIngresada = dateFormat.parse(fechaStr);
            LocalDate fechaIngresadaLocal = fechaIngresada.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate fechaActual = LocalDate.now();
            return !fechaIngresadaLocal.isAfter(fechaActual);
        } catch (ParseException e) {
            // Manejar error si la fecha no es válida
            System.out.println("Error al parsear la fecha: " + e.getMessage());
            return false;
        }
    }
	
	
	
	// alta convocatoria convocatorias
	
	
	public void altaConvocatorias() throws Exception {

		logger.info("***********************Inicio Alta Convocatoria***********************");
		
		logger.info("nombre de la convocaria    : " + convocatoriaParamNueva.getAltaNombreConvocatoria());
		logger.info("nombre corto               : " + convocatoriaParamNueva.getAltaNombreCorto());
		logger.info("descripcion                : " + convocatoriaParamNueva.getAltaDescripcion());
		logger.info("fecha de apertura          : " + convocatoriaParamNueva.getAltaFechaApertura());
		logger.info("fecha cierre               : " + convocatoriaParamNueva.getAltaFechaCierre());
		logger.info("nivel educativo            : " + convocatoriaParamNueva.getListaNivelEducativoCompl());
		logger.info("url                        : " + convocatoriaParamNueva.getAltaUrl());
		logger.info("estatus                    : " + convocatoriaParamNueva.getAltaEstatus());
		logger.info("fecha alta                 : " + convocatoriaParamNueva.getAltaFechaAlta());
		logger.info("cupo limite                : " + convocatoriaParamNueva.getAltaCupoLimite());
		
		if( "".equals(convocatoriaParamNueva.getAltaNombreConvocatoria()) ||
			"".equals(convocatoriaParamNueva.getAltaNombreCorto()) ||
			convocatoriaParamNueva.getAltaFechaApertura() == null  || 
			convocatoriaParamNueva.getAltaEstatus() == null  || 
			convocatoriaParamNueva.getListaNivelEducativoCompl() == null  || 
			convocatoriaParamNueva.getAltaFechaCierre() == null ) {
				
				RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion8').show()");
				
			} else {
				
				if("".equals(convocatoriaParamNueva.getAltaCupoLimite())) {
					convocatoriaParamNueva.setAltaCupoLimite("0");
				}
				
				convocatoriaService.altaConvocatorias(convocatoriaParamNueva);
				
				convocatoriaParamNueva = new ConvocatoriaParamNueva();
				convocatoriaParamNueva.setAltaFechaAlta(new Date());
				
				RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion9').show()");
				
			}

		logger.info("***********************Termina Alta Convocatoria***********************");
		
	}
	
	
	public void validarFechas() {
		if (ObjectUtils.isNotNull(convocatoriaParamConsulta.getConsulFechaCierre())) {
			if (convocatoriaParamConsulta.getConsulFechaCierre().before(convocatoriaParamConsulta.getConsulFechaApertura())) {
				convocatoriaParamConsulta.setConsulFechaCierre(convocatoriaParamConsulta.getConsulFechaApertura());
			}
		}

	}
	
	public void validarFechasAlta() {
		if (ObjectUtils.isNotNull(convocatoriaParamNueva.getAltaFechaCierre())) {
			if (convocatoriaParamNueva.getAltaFechaCierre().before(convocatoriaParamNueva.getAltaFechaApertura())) {
				convocatoriaParamNueva.setAltaFechaCierre(convocatoriaParamNueva.getAltaFechaApertura());
			}
		}
	}
	
	public void validarFechasAlta2() {
		if (ObjectUtils.isNotNull(convocatoriaParamNueva.getAltaFechaCierre())) {
			if (convocatoriaParamNueva.getAltaFechaCierre().before(convocatoriaParamNueva.getAltaFechaApertura())) {
				convocatoriaParamNueva.setAltaFechaCierre(convocatoriaParamNueva.getAltaFechaApertura());
			}
		}
	}

	// consulta convocatorias

	public void consultarConvocatoriasFiltros() throws Exception {

		listaConvocatoria = convocatoriaService.consultarConvocatorias();

		logger.info("Termina consulta lista convocatorias select");

	}

	
	
	
	
	
	
	public void limpiarCampos() {
		
		listaConvocatoria2 = new ArrayList<Convocatoria>();
		convocatoriaParamConsulta = new ConvocatoriaParamConsulta();
		convocatoriaParamNueva = new ConvocatoriaParamNueva();
		convocatoriaParamNueva.setAltaFechaAlta(new Date());
		
	}

	public void limpiarFiltros() {
		listaConvocatoria2 = new ArrayList<Convocatoria>();
		convocatoriaParamConsulta = new ConvocatoriaParamConsulta();
	}

	public void limpiarEdicion() {
		convocatoriaParamNueva = new ConvocatoriaParamNueva();
		convocatoriaParamNueva.setAltaFechaAlta(new Date());
		listaNivelEducativoCompl = convocatoriaService.consultarNivelEducativoCompleto();
		convocatoriaParamNueva.setListaPlanPrograma(listaNivelEducativoCompl);
		convocatoriaParamNueva.setListaPlanProgramaNivel(new ArrayList<ConvocatoriaNivelEducativoCompl>());
		convocatoriaParamNueva.setListaPlanProgramaMarcados(new ArrayList<ConvocatoriaNivelEducativoCompl>());
		convocatoriaParamNueva.setListaPlanProgramaEliminar(new ArrayList<ConvocatoriaNivelEducativoCompl>());
	}
	
	
	
	
	
	public void consultarFiltros() {
		
		logger.info("***********************Inicio Consulta convocatorias filtros***********************");
		
		
		if(convocatoriaParamConsulta.getValueConvocatoriaEstatus() == null ) {
			convocatoriaParamConsulta.setValueConvocatoriaEstatus("");
		}
		
		logger.info("NOMBRE : " + convocatoriaParamConsulta.getConsulNombreConvocatoria());
		logger.info("NOMBRE CORTO : " + convocatoriaParamConsulta.getConsulNombreCorto());
		logger.info("ESTATUS : " + convocatoriaParamConsulta.getValueConvocatoriaEstatus());
		logger.info("FECHA APERTURA : " + convocatoriaParamConsulta.getConsulFechaApertura());
		logger.info("FECHA CIERRE : " + convocatoriaParamConsulta.getConsulFechaCierre());
		logger.info("NIVEL EDUCATIVO : " + convocatoriaParamConsulta.getConsulNivelEducativo());
		
		
		if( "".equals(convocatoriaParamConsulta.getConsulNombreConvocatoria()) ||
			convocatoriaParamConsulta.getConsulFechaApertura() == null  || 
					convocatoriaParamConsulta.getConsulFechaCierre() == null ) {
			
			agregarMsgError("Error", "Ingrese los datos marcados como obligatorios.");
			
		} else {
			
			listaConvocatoria2 = convocatoriaService.consultarConvocatoriasFiltros(convocatoriaParamConsulta);
			
			if(listaConvocatoria2.isEmpty()) {
				agregarMsgInfo("No se encontraron convocatorias.", null);
			}
			
		}
	
		
		logger.info("***********************Termina Consulta convocatorias filtros***********************");
			
		}

	
	

	// consutlar tablero metodos

	public void consultarConvocatorias() throws Exception {

		listaConvocatoria = convocatoriaService.consultarConvocatorias();

		logger.info("Termina consulta lista convocatorias select");

	}

	public void consultarTableroResumen() throws Exception {

		listaTableResumen = new ArrayList<ConvocatoriaTableroResumen>();

		listaTableResumen = convocatoriaService.consultarTableroResumen(valueConvocatoria);

		logger.info("Termina consulta lista tabla resumenn tabla");

	}

	/// varios

	public void consultarNivelEducativo() throws Exception {

		listaNivelEducativo = convocatoriaService.consultarNivelEducativo();

		logger.info("Termina consulta lista nivel select");

	}
	
	public void consultarNivelEducativoCompleto() throws Exception {

		listaNivelEducativoCompl = convocatoriaService.consultarNivelEducativoCompleto();

		logger.info("Termina consulta lista nivel select");

	}

	// set y get

	public ConvocatoriaService getConvocatoriaService() {
		return convocatoriaService;
	}

	public void setConvocatoriaService(ConvocatoriaService convocatoriaService) {
		this.convocatoriaService = convocatoriaService;
	}

	public String getPaginaActual() {
		return paginaActual;
	}

	public void setPaginaActual(String paginaActual) {
		this.paginaActual = paginaActual;
	}

	public List<Convocatoria> getListaConvocatoria() {
		return listaConvocatoria;
	}

	public void setListaConvocatoria(List<Convocatoria> listaConvocatoria) {
		this.listaConvocatoria = listaConvocatoria;
	}

	public int getValueConvocatoria() {
		return valueConvocatoria;
	}

	public void setValueConvocatoria(int valueConvocatoria) {
		this.valueConvocatoria = valueConvocatoria;
	}

	public List<ConvocatoriaTableroResumen> getListaTableResumen() {
		return listaTableResumen;
	}

	public void setListaTableResumen(List<ConvocatoriaTableroResumen> listaTableResumen) {
		this.listaTableResumen = listaTableResumen;
	}

	public List<EstatusDTO> getEstatusLista() {
		inicializarEstatus();
		return estatusLista;
	}

	public void setEstatusLista(List<EstatusDTO> estatusLista) {
		this.estatusLista = estatusLista;
	}

	public List<ConvocatoriaNivelEducativo> getListaNivelEducativo() {
		if (listaNivelEducativo == null || listaNivelEducativo.isEmpty()) {
			cargarCatalogosConvocatoria();
		}
		return listaNivelEducativo;
	}

	public void setListaNivelEducativo(List<ConvocatoriaNivelEducativo> listaNivelEducativo) {
		this.listaNivelEducativo = listaNivelEducativo;
	}

	public int getValueConvocatoriaNivel() {
		return valueConvocatoriaNivel;
	}

	public void setValueConvocatoriaNivel(int valueConvocatoriaNivel) {
		this.valueConvocatoriaNivel = valueConvocatoriaNivel;
	}



	public ConvocatoriaParamConsulta getConvocatoriaParamConsulta() {
		return convocatoriaParamConsulta;
	}

	public void setConvocatoriaParamConsulta(ConvocatoriaParamConsulta convocatoriaParamConsulta) {
		this.convocatoriaParamConsulta = convocatoriaParamConsulta;
	}

	public String getConsulNombreConvocatoria() {
		return consulNombreConvocatoria;
	}

	public void setConsulNombreConvocatoria(String consulNombreConvocatoria) {
		this.consulNombreConvocatoria = consulNombreConvocatoria;
	}

	public String getConsulNombreCorto() {
		return consulNombreCorto;
	}

	public void setConsulNombreCorto(String consulNombreCorto) {
		this.consulNombreCorto = consulNombreCorto;
	}

	public String getConsulFechaApertura() {
		return consulFechaApertura;
	}

	public void setConsulFechaApertura(String consulFechaApertura) {
		this.consulFechaApertura = consulFechaApertura;
	}

	public String getConsulFechaCierre() {
		return consulFechaCierre;
	}

	public void setConsulFechaCierre(String consulFechaCierre) {
		this.consulFechaCierre = consulFechaCierre;
	}

	public Integer getConsulNivelEducativo() {
		return consulNivelEducativo;
	}

	public void setConsulNivelEducativo(Integer consulNivelEducativo) {
		this.consulNivelEducativo = consulNivelEducativo;
	}

	public Integer getValueConvocatoriaEstatus() {
		return valueConvocatoriaEstatus;
	}

	public void setValueConvocatoriaEstatus(Integer valueConvocatoriaEstatus) {
		this.valueConvocatoriaEstatus = valueConvocatoriaEstatus;
	}
	
	public String getAltaNombreConvocatoria() {
		return altaNombreConvocatoria;
	}

	public void setAltaNombreConvocatoria(String altaNombreConvocatoria) {
		this.altaNombreConvocatoria = altaNombreConvocatoria;
	}

	public String getAltaNombreCorto() {
		return altaNombreCorto;
	}

	public void setAltaNombreCorto(String altaNombreCorto) {
		this.altaNombreCorto = altaNombreCorto;
	}

	public String getAltaDescripcion() {
		return altaDescripcion;
	}

	public void setAltaDescripcion(String altaDescripcion) {
		this.altaDescripcion = altaDescripcion;
	}

	public Date getAltaFechaApertura() {
		return altaFechaApertura;
	}

	public void setAltaFechaApertura(Date altaFechaApertura) {
		this.altaFechaApertura = altaFechaApertura;
	}

	public Date getAltaFechaCierre() {
		return altaFechaCierre;
	}

	public void setAltaFechaCierre(Date altaFechaCierre) {
		this.altaFechaCierre = altaFechaCierre;
	}

	public Integer getAltaNivelEducativo() {
		return altaNivelEducativo;
	}

	public void setAltaNivelEducativo(Integer altaNivelEducativo) {
		this.altaNivelEducativo = altaNivelEducativo;
	}

	public String getAltaUrl() {
		return altaUrl;
	}

	public void setAltaUrl(String altaUrl) {
		this.altaUrl = altaUrl;
	}

	public Integer getAltaEstatus() {
		return altaEstatus;
	}

	public void setAltaEstatus(Integer altaEstatus) {
		this.altaEstatus = altaEstatus;
	}

	public Date getAltaFechaAlta() {
		return altaFechaAlta;
	}

	public void setAltaFechaAlta(Date altaFechaAlta) {
		this.altaFechaAlta = altaFechaAlta;
	}

	public Integer getAltaCupoLimite() {
		return altaCupoLimite;
	}

	public void setAltaCupoLimite(Integer altaCupoLimite) {
		this.altaCupoLimite = altaCupoLimite;
	}

	public List<ConvocatoriaNivelEducativoCompl> getListaNivelEducativoCompl2() {
		return listaNivelEducativoCompl2;
	}

	public void setListaNivelEducativoCompl2(List<ConvocatoriaNivelEducativoCompl> listaNivelEducativoCompl2) {
		this.listaNivelEducativoCompl2 = listaNivelEducativoCompl2;
	}

	public List<Convocatoria> getListaConvocatoria2() {
		return listaConvocatoria2;
	}

	public void setListaConvocatoria2(List<Convocatoria> listaConvocatoria2) {
		this.listaConvocatoria2 = listaConvocatoria2;
	}

	public Convocatoria getElminarConvo() {
		return elminarConvo;
	}

	public void setElminarConvo(Convocatoria elminarConvo) {
		this.elminarConvo = elminarConvo;
	}

	public ConvocatoriaParamNueva getConvocatoriaParamNueva() {
		return convocatoriaParamNueva;
	}

	public void setConvocatoriaParamNueva(ConvocatoriaParamNueva convocatoriaParamNueva) {
		this.convocatoriaParamNueva = convocatoriaParamNueva;
	}

	public List<ConvocatoriaNivelEducativoCompl> getListaNivelEducativoCompl() {
		if (listaNivelEducativoCompl == null || listaNivelEducativoCompl.isEmpty()) {
			cargarCatalogosConvocatoria();
		}
		return listaNivelEducativoCompl;
	}

	public void setListaNivelEducativoCompl(List<ConvocatoriaNivelEducativoCompl> listaNivelEducativoCompl) {
		this.listaNivelEducativoCompl = listaNivelEducativoCompl;
	}

	public boolean isMostrarConsultaConvocatoria() {
		return mostrarConsultaConvocatoria;
	}

	public void setMostrarConsultaConvocatoria(boolean mostrarConsultaConvocatoria) {
		this.mostrarConsultaConvocatoria = mostrarConsultaConvocatoria;
	}

	public boolean isMostrarNuevaConvocatoria() {
		return mostrarNuevaConvocatoria;
	}

	public void setMostrarNuevaConvocatoria(boolean mostrarNuevaConvocatoria) {
		this.mostrarNuevaConvocatoria = mostrarNuevaConvocatoria;
	}

	

}
