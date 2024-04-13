package mx.gob.sedesol.basegestor.service.admin;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.mongo.service.BitacoraService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ComunValidacionService<A> {
    @Autowired
    private BitacoraService bitacoraService;

    public ResultadoDTO<A> sonDatosRequeridosValidos(TipoAccion accion, A dto) {

        ResultadoDTO<A> resultado = new ResultadoDTO<>();

        if (ObjectUtils.isNotNull(dto)) {
            switch (accion) {
                case PERSISTENCIA:
                    validarPersistencia(dto, resultado);
                    break;
                case ACTUALIZACION:
                    validarActualizacion(dto, resultado);
                    break;
                case ELIMINACION:
                    validarEliminacion(dto, resultado);
                    break;
            }
        } else {
            resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
        }

        return resultado;
    }


    public abstract void validarPersistencia(A dto, ResultadoDTO<A> resultado);

    public abstract void validarActualizacion(A dto, ResultadoDTO<A> resultado);

    public abstract void validarEliminacion(A dto, ResultadoDTO<A> resultado);
}
