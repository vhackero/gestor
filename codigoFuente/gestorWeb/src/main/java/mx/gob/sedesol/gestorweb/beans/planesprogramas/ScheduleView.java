package mx.gob.sedesol.gestorweb.beans.planesprogramas;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

@ManagedBean
@ViewScoped
public class ScheduleView implements Serializable {

	private Date fechaVigInicial;
	private Date fechaVigFinal;
	private Date fechaActual;

	public Date getFechaVigInicial() {
		return fechaVigInicial;
	}

	public void setFechaVigInicial(Date fechaVigInicial) {
		this.fechaVigInicial = fechaVigInicial;
	}

	public Date getFechaVigFinal() {
		return fechaVigFinal;
	}

	public void setFechaVigFinal(Date fechaVigFinal) {
		this.fechaVigFinal = fechaVigFinal;
	}
	
	public Date getFechaActual() {
		return new Date();
	}

	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}
	
	

}
