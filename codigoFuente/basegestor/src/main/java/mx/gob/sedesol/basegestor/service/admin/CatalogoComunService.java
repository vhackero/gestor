package mx.gob.sedesol.basegestor.service.admin;

import java.io.Serializable;
import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;

/**
 * 
 * @author PlanetMedia, ORMG
 *
 * Interface Generica para la implementacion de Operaciones CRUD,
 * de catalogos con propiedades comunes
 * 
 * @param <A> Entidad que implementara las operaciones CRUD
 * @param <ID> Es el tipo de variable del Identificador unico de la entidad
 */
public interface CatalogoComunService <A, ID extends Serializable> {

	/**
	 * Servicio para recuperar todos los registros del catalogo
	 * @param classEntidad
	 * @return
	 */
    List<CatalogoComunDTO> findAll(Class<A> classEntidad);
	
	/**
	 * Servicio para recuperar un registro del catalogo por identificador
	 * @param id
	 * @param classEntidad
	 * @return
	 */
    CatalogoComunDTO buscarPorId(ID id, Class<A> classEntidad);
	
	/**
	 * Servicio para guardar un registro en el catalogo
	 * @param catDto
	 * @param classEntidad
	 * @return
	 * @throws Exception
	 */
    ResultadoDTO guardar(CatalogoComunDTO catDto, Class<A> classEntidad) throws Exception;
	
	/**
	 * Servicio para actualizar un registro en el catalogo
	 * @param catDto
	 * @param classEntidad
	 * @return
	 * @throws Exception
	 */
    ResultadoDTO actualizar(CatalogoComunDTO catDto, Class<A> classEntidad) throws Exception;
	
	/**
	 * Servicio para eliminar logicamente un registro en el catalogo
	 * @param catDto
	 * @param classEntidad
	 * @return
	 * @throws Exception
	 */
    ResultadoDTO eliminaLogicamente(CatalogoComunDTO catDto, Class<A> classEntidad) throws Exception;
	
	/**
	 * Servicio para eliminar directamente un registro del catalogo
	 * @param catDto
	 * @param classEntidad
	 * @return
	 * @throws Exception
	 */
    ResultadoDTO eliminar(CatalogoComunDTO catDto, Class<A> classEntidad) throws Exception;
	
	/**
	 * Metodo para validar los datos requeridos de acuerdo al tipo de accion a ejecutar
	 * @param accion
	 * @param catDto
	 * @return
	 */
	ResultadoDTO sonDatosRequeridosValidos(TipoAccion accion, CatalogoComunDTO catDto);
	
	/**
	 * Metodo para buscar un registro por campo nombre
	 * @param nombre
	 * @param classEntidad
	 * @return
	 */
    CatalogoComunDTO buscarRegistroPorNombre(String nombre, Class<A> classEntidad);

}
