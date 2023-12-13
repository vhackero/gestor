package mx.gob.sedesol.gestorweb.beans.administracion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;

import org.modelmapper.ModelMapper;
import org.primefaces.context.RequestContext;

import mx.gob.sedesol.basegestor.commons.dto.admin.AsentamientoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.EntidadFederativaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.MunicipioDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.TipoAsentamientoDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.service.admin.AsentamientoService;
import mx.gob.sedesol.basegestor.service.admin.EntidadFederativaService;
import mx.gob.sedesol.basegestor.service.admin.MunicipioService;
import mx.gob.sedesol.basegestor.service.admin.TipoAsentamientoService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.utils.BitacoraUtil;
import mx.gob.sedesol.gestorweb.commons.utils.ObjectUtils;
import mx.gob.sedesol.gestorweb.sistema.SistemaBean;

@SessionScoped
@ManagedBean(name = "localizacionBean")
public class LocalizacionBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	private transient ModelMapper modelMapper = new ModelMapper();

	@ManagedProperty(value = "#{sistema}")
	private SistemaBean sistema;

	@ManagedProperty(name = "entidadFederativaService", value = "#{entidadFederativaService}")
	private transient EntidadFederativaService entidadFederativaService;

	@ManagedProperty(name = "tipoAsentamientoService", value = "#{tipoAsentamientoService}")
	private transient TipoAsentamientoService tipoAsentamientoService;

	@ManagedProperty(name = "municipioService", value = "#{municipioService}")
	private transient MunicipioService municipioService;

	@ManagedProperty("#{bitacoraBean}")
	private BitacoraBean bitacoraBean;

	private List<EntidadFederativaDTO> entidadesFederativas;
	private EntidadFederativaDTO entidadFederativaDTO;
	private int idEntidadFederativa;

	private List<MunicipioDTO> municipios;
	private MunicipioDTO municipio = new MunicipioDTO();
	private MunicipioDTO municipioModificar;

	private boolean nuevo;

	@ManagedProperty(name = "asentamientoService", value = "#{asentamientoService}")
	private transient AsentamientoService asentamientoService;

	private List<AsentamientoDTO> asentamientos;
	private AsentamientoDTO asentamiento;
	private AsentamientoDTO asentamientoModificar;
	private List<TipoAsentamientoDTO> tiposAsentamiento;

	@PostConstruct
	public void init() {
		entidadesFederativas = entidadFederativaService.findAll();
		municipios = new ArrayList<>();
	}

	public void cargarMunicipios() {
		if (!ObjectUtils.isNullOrCero(idEntidadFederativa)) {
			entidadFederativaDTO = entidadFederativaService.buscarPorId(idEntidadFederativa);
			municipios = municipioService.buscarPorEntidadFederativa(idEntidadFederativa);
		}
		if (!municipios.isEmpty()) {
			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "BUS_MUN_ENT", "", requestActual(),
					TipoServicioEnum.LOCAL);
		}

	}

	public void nuevoMunicipio() {
		municipio = new MunicipioDTO(getUsuarioEnSession().getIdPersona());
		municipio.setEntidadFederativa(entidadFederativaDTO);
		nuevo = true;
	}

	public void cargarMunicipio() {
		municipio = new MunicipioDTO();
		modelMapper.map(municipioModificar, municipio);
		municipio.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
		municipio.setFechaActualizacion(new Date());
		nuevo = false;
	}

	public String guardarMunicipio() {
		ResultadoDTO<MunicipioDTO> resultado;
		if (municipioService.findDuplicateMunicipio(municipio)) {
			agregarMsgError("Ya existe el ID", null, sistema);
		}else {
			if (nuevo) {
				resultado = municipioService.guardar(municipio);
			} else {
				resultado = municipioService.actualizar(municipio);
			}
			if (resultado.getResultado() == ResultadoTransaccionEnum.EXITOSO) {

				if (nuevo) {
					bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CRE_MUN",
							String.valueOf(resultado.getDto().getIdMunicipio()), requestActual(), TipoServicioEnum.LOCAL);
				} else {
					bitacoraBean.guardarBitacora(idPersonaEnSesion(), "EDI_MUN",
							String.valueOf(resultado.getDto().getIdMunicipio()), requestActual(), TipoServicioEnum.LOCAL);
				}
				if (!ObjectUtils.isNullOrCero(idEntidadFederativa)) {
					entidadFederativaDTO = entidadFederativaService.buscarPorId(idEntidadFederativa);
					municipios = municipioService.buscarPorEntidadFederativa(idEntidadFederativa);
				}
				RequestContext.getCurrentInstance().execute("PF('aModal').hide();");
				agregarMsgInfo(resultado.getMensajes().get(0), null, sistema);
			} else {
				agregarMsgError(resultado.getMensajes(), null, sistema);
			}
		}
		return "";
	}

	public String cargarAsentamientos() {
		asentamientos = asentamientoService.buscarPorMunicipio(municipio.getIdMunicipio());
		tiposAsentamiento = tipoAsentamientoService.findAll();
		asentamiento = new AsentamientoDTO();
		asentamiento.setTipoAsentamiento(new TipoAsentamientoDTO());
		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CON_ASE_MUN", String.valueOf(municipio.getIdMunicipio()),
				requestActual(), TipoServicioEnum.LOCAL);
		return ConstantesGestorWeb.NAVEGA_ASENTAMIENTOS;
	}

	public void nuevoAsentamiento() {
		asentamiento = new AsentamientoDTO(getUsuarioEnSession().getIdPersona());
		asentamiento.setMunicipio(municipio);
		nuevo = true;
	}

	public void cargarAsentamiento() {
		asentamiento = new AsentamientoDTO();
		modelMapper.map(asentamientoModificar, asentamiento);
		asentamiento.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
		asentamiento.setFechaActualizacion(new Date());
		nuevo = false;
	}

	public void guardarAsentamiento() {
		ResultadoDTO<AsentamientoDTO> resultado;

		if (asentamientoService.findDuplicateAsentamiento(asentamiento)) {
			agregarMsgError("Ya existe el ID", null, sistema);
		}else {
			if (nuevo) {
				resultado = asentamientoService.guardar(asentamiento);
			} else {
				resultado = asentamientoService.actualizar(asentamiento);
			}
			if (resultado.getResultado() == ResultadoTransaccionEnum.EXITOSO) {

				if (nuevo) {
					bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CRE_MUN",
							String.valueOf(resultado.getDto().getIdAsentamiento()), requestActual(),
							TipoServicioEnum.LOCAL);
				} else {
					bitacoraBean.guardarBitacora(idPersonaEnSesion(), "EDI_MUN",
							String.valueOf(resultado.getDto().getIdAsentamiento()), requestActual(),
							TipoServicioEnum.LOCAL);
				}
				cargarListaAsentamientos();
				RequestContext.getCurrentInstance().execute("PF('amodal').hide();");
				agregarMsgInfo(resultado.getMensajes().get(0), null, sistema);
			} else {
				agregarMsgError(resultado.getMensajes(), null, sistema);
			}
		}
	}

	public void cargarListaAsentamientos() {
		asentamientos = asentamientoService.buscarPorMunicipio(municipio.getIdMunicipio());
		tiposAsentamiento = tipoAsentamientoService.findAll();
		asentamiento = new AsentamientoDTO();
		asentamiento.setTipoAsentamiento(new TipoAsentamientoDTO());
	}

	public EntidadFederativaService getEntidadFederativaService() {
		return entidadFederativaService;
	}

	public void setEntidadFederativaService(EntidadFederativaService entidadFederativaService) {
		this.entidadFederativaService = entidadFederativaService;
	}

	public MunicipioService getMunicipioService() {
		return municipioService;
	}

	public void setMunicipioService(MunicipioService municipioService) {
		this.municipioService = municipioService;
	}

	public List<EntidadFederativaDTO> getEntidadesFederativas() {
		return entidadesFederativas;
	}

	public void setEntidadesFederativas(List<EntidadFederativaDTO> entidadesFederativas) {
		this.entidadesFederativas = entidadesFederativas;
	}

	public EntidadFederativaDTO getEntidadFederativaDTO() {
		return entidadFederativaDTO;
	}

	public void setEntidadFederativaDTO(EntidadFederativaDTO entidadFederativaDTO) {
		this.entidadFederativaDTO = entidadFederativaDTO;
	}

	public List<MunicipioDTO> getMunicipios() {
		return municipios;
	}

	public void setMunicipios(List<MunicipioDTO> municipios) {
		this.municipios = municipios;
	}

	public MunicipioDTO getMunicipio() {
		return municipio;
	}

	public void setMunicipio(MunicipioDTO municipio) {
		this.municipio = municipio;
	}

	public MunicipioDTO getMunicipioModificar() {
		return municipioModificar;
	}

	public void setMunicipioModificar(MunicipioDTO municipioModificar) {
		this.municipioModificar = municipioModificar;
	}

	public int getIdEntidadFederativa() {
		return idEntidadFederativa;
	}

	public void setIdEntidadFederativa(int idEntidadFederativa) {
		this.idEntidadFederativa = idEntidadFederativa;
	}

	public boolean isNuevo() {
		return nuevo;
	}

	public void setNuevo(boolean nuevo) {
		this.nuevo = nuevo;
	}

	public AsentamientoService getAsentamientoService() {
		return asentamientoService;
	}

	public void setAsentamientoService(AsentamientoService asentamientoService) {
		this.asentamientoService = asentamientoService;
	}

	public List<AsentamientoDTO> getAsentamientos() {
		return asentamientos;
	}

	public void setAsentamientos(List<AsentamientoDTO> asentamientos) {
		this.asentamientos = asentamientos;
	}

	public AsentamientoDTO getAsentamiento() {
		return asentamiento;
	}

	public void setAsentamiento(AsentamientoDTO asentamiento) {
		this.asentamiento = asentamiento;
	}

	public AsentamientoDTO getAsentamientoModificar() {
		return asentamientoModificar;
	}

	public void setAsentamientoModificar(AsentamientoDTO asentamientoModificar) {
		this.asentamientoModificar = asentamientoModificar;
	}

	public TipoAsentamientoService getTipoAsentamientoService() {
		return tipoAsentamientoService;
	}

	public void setTipoAsentamientoService(TipoAsentamientoService tipoAsentamientoService) {
		this.tipoAsentamientoService = tipoAsentamientoService;
	}

	public List<TipoAsentamientoDTO> getTiposAsentamiento() {
		return tiposAsentamiento;
	}

	public void setTiposAsentamiento(List<TipoAsentamientoDTO> tiposAsentamiento) {
		this.tiposAsentamiento = tiposAsentamiento;
	}

	public SistemaBean getSistema() {
		return sistema;
	}

	public void setSistema(SistemaBean sistema) {
		this.sistema = sistema;
	}

	public BitacoraBean getBitacoraBean() {
		return bitacoraBean;
	}

	public void setBitacoraBean(BitacoraBean bitacoraBean) {
		this.bitacoraBean = bitacoraBean;
	}

}
