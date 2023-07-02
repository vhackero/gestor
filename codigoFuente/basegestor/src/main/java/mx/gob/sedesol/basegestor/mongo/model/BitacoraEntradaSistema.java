package mx.gob.sedesol.basegestor.mongo.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "BitacoraEntradaSistema")
public class BitacoraEntradaSistema {
	
	@Id
	private long idPersona;
	private Date primerEntrada;
	private Date ultimaEntrada;
	
	public BitacoraEntradaSistema() {
		
		this.primerEntrada = new Date();
		this.ultimaEntrada = new Date();
	}
	
	public BitacoraEntradaSistema(long idPersona) {
		this.idPersona = idPersona;
		
		this.primerEntrada = new Date();
		this.ultimaEntrada = new Date();
	}
	
	public long getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(long idPersona) {
		this.idPersona = idPersona;
	}

	public Date getPrimerEntrada() {
		return primerEntrada;
	}

	public void setPrimerEntrada(Date primerEntrada) {
		this.primerEntrada = primerEntrada;
	}

	public Date getUltimaEntrada() {
		return ultimaEntrada;
	}

	public void setUltimaEntrada(Date ultimaEntrada) {
		this.ultimaEntrada = ultimaEntrada;
	}


}
