package mx.gob.sedesol.basegestor.mongo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;
import mx.gob.sedesol.basegestor.commons.dto.admin.BitacoraDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.mongo.model.Bitacora;
import mx.gob.sedesol.basegestor.mongo.model.BitacoraEntradaSistema;
import mx.gob.sedesol.basegestor.mongo.repositories.BitacoraEntradaSistemaRepo;
import mx.gob.sedesol.basegestor.mongo.repositories.BitacoraRepo;
import mx.gob.sedesol.basegestor.mongo.service.BitacoraEntradaSistemaService;

@Service("bitacoraEntradaSistemaService")
public class BitacoraEntradaSistemaServiceImpl implements BitacoraEntradaSistemaService {

    private static final Logger LOG = Logger.getLogger(BitacoraEntradaSistemaServiceImpl.class);

    @Autowired
    private BitacoraEntradaSistemaRepo bitacoraEntradaSistemaRepo;

    @Autowired
    private BitacoraRepo bitacoraRepo;

	@Override
	public ResultadoDTO<BitacoraDTO> guardarBitacora(long idPersona, String registro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BitacoraDTO> obtenerBitacoraEntrada(long idPersona) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BitacoraDTO obtieneBitacoraUltimaEntradaUsuario(Long idPersona) {
		// TODO Auto-generated method stub
		return null;
	}

   /* @Override
    public ResultadoDTO<BitacoraDTO> guardarBitacora(long idPersona, String registro) {
        ResultadoDTO<BitacoraDTO> resultado = new ResultadoDTO<>();

        BitacoraEntradaSistema bitacoraEntradaSistema = bitacoraEntradaSistemaRepo.findOne(idPersona);

        if (ObjectUtils.isNull(bitacoraEntradaSistema)) {
            bitacoraEntradaSistema = new BitacoraEntradaSistema(idPersona);
        } else {
            bitacoraEntradaSistema.setUltimaEntrada(new Date());
        }

        bitacoraEntradaSistemaRepo.save(bitacoraEntradaSistema);

        Bitacora bitacora = new Bitacora(idPersona, ConstantesBitacora.BITACORA_ENTRADA_SISTEMA, registro);
        bitacoraRepo.save(bitacora);
        resultado.agregaMensaje(MensajesSistemaEnum.BITACORA_ENTRADA_SISTEMA_GUARDAR_EXITO.getId());

        return resultado;
    }

    @Override
    public List<BitacoraDTO> obtenerBitacoraEntrada(long idPersona) {
        List<BitacoraDTO> bitacora = new ArrayList<>();

        BitacoraEntradaSistema bitacoraEntradaSistema = bitacoraEntradaSistemaRepo.findOne(idPersona);

        if (ObjectUtils.isNotNull(bitacoraEntradaSistema)) {
            BitacoraDTO bitacoraPrimera = new BitacoraDTO();
            bitacoraPrimera.setIdUsuario(idPersona);
            bitacoraPrimera.setFechaBitacora(bitacoraEntradaSistema.getPrimerEntrada());
            bitacoraPrimera.setFuncion(ConstantesBitacora.BITACORA_PRIMER_ENTRADA);

            BitacoraDTO bitacoraUltima = new BitacoraDTO();
            bitacoraUltima.setIdUsuario(idPersona);
            bitacoraUltima.setFechaBitacora(bitacoraEntradaSistema.getUltimaEntrada());
            bitacoraUltima.setFuncion(ConstantesBitacora.BITACORA_ULTIMA_ENTRADA);

            bitacora.add(bitacoraPrimera);
            bitacora.add(bitacoraUltima);
        }
        return bitacora;
    }

    @Override
    public BitacoraDTO obtieneBitacoraUltimaEntradaUsuario(Long idPersona) {
        BitacoraDTO bitacora = new BitacoraDTO();
        BitacoraEntradaSistema bitacoraEntradaSistema = bitacoraEntradaSistemaRepo.findOne(idPersona);
        if (ObjectUtils.isNotNull(bitacoraEntradaSistema)) {
            bitacora.setIdUsuario(idPersona);
            bitacora.setFechaBitacora(bitacoraEntradaSistema.getUltimaEntrada());
            bitacora.setFuncion(ConstantesBitacora.BITACORA_ULTIMA_ENTRADA);

        }
        return bitacora;
    }*/

}
