package mx.gob.sedesol.gestorweb.commons.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.primefaces.model.DefaultScheduleEvent;

import mx.gob.sedesol.gestorweb.commons.dto.SolicitudReservAreaDTO;

public class UtileriasLogistica {
	
	public static SolicitudReservAreaDTO asignaHoraInicialFinalSchedule(SolicitudReservAreaDTO reservacion){
		
		String titulo = reservacion.getEventoSchedule().getTitle();
		String styleClass = reservacion.getEventoSchedule().getStyleClass();

		Calendar calHoraInicial = Calendar.getInstance();
		calHoraInicial.setTime(reservacion.getEventoSchedule().getStartDate());

		Character charHoraA = reservacion.getHoraInicial().charAt(0);
		Character charHoraB = reservacion.getHoraInicial().charAt(1);
		String intHoraInicial = charHoraA.toString() + charHoraB.toString();
		calHoraInicial.set(Calendar.HOUR_OF_DAY, Integer.valueOf(intHoraInicial));

		Character charMinutoA = reservacion.getHoraInicial().charAt(3);
		Character charMinutoB = reservacion.getHoraInicial().charAt(4);
		String intMinutoInicial = charMinutoA.toString() + charMinutoB.toString();
		calHoraInicial.set(Calendar.MINUTE, Integer.valueOf(intMinutoInicial));
		Date horaInicial = calHoraInicial.getTime();

		Calendar calHoraFinal = Calendar.getInstance();
		calHoraFinal.setTime(reservacion.getEventoSchedule().getEndDate());

		Character charHorafinalA = reservacion.getHoraFinal().charAt(0);
		Character charHorafinalB = reservacion.getHoraFinal().charAt(1);
		String intHoraFinal = charHorafinalA.toString() + charHorafinalB.toString();
		calHoraFinal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(intHoraFinal));

		Character charMinutofinalA = reservacion.getHoraFinal().charAt(3);
		Character charMinutofinalB = reservacion.getHoraFinal().charAt(4);
		String intMinutoFinal = charMinutofinalA.toString() + charMinutofinalB.toString();
		calHoraFinal.set(Calendar.MINUTE, Integer.valueOf(intMinutoFinal));
		Date horaFinal = calHoraFinal.getTime();
		reservacion.setEventoSchedule(new DefaultScheduleEvent(titulo, horaInicial, horaFinal));
		reservacion.getEventoSchedule().setStyleClass(styleClass);
		
		return reservacion;
	}

	public static boolean validaCamposVacios(List<SolicitudReservAreaDTO> solicitudes) {
		boolean validacionTabla = true;

		for (SolicitudReservAreaDTO s : solicitudes) {
			if (s.getIdSede() == null || s.getIdArea() == null || s.getPersonalizacionBoolean() == null
					|| s.getHoraInicial() == null || s.getHoraFinal() == null) {
				validacionTabla = false;

			}
		}

		return validacionTabla;
	}

	public static boolean validarHorasAreas(List<SolicitudReservAreaDTO> solicitudes) {

		Boolean aprobado = true;

		for (SolicitudReservAreaDTO sA : solicitudes) {
			for (SolicitudReservAreaDTO sB : solicitudes) {

				if (sA != sB) {
					Integer inHIsA = 0;
					Integer inHFsA = 0;
					Integer inHIsB = 0;
					Integer inHFsB = 0;
					for (String h : sA.getHorasA()) {
						// Saca indexes de sA
						if (h.equals(sA.getHoraInicial())) {
							inHIsA = sA.getHorasA().indexOf(h);
						} else if (h.equals(sA.getHoraFinal())) {
							inHFsA = sA.getHorasA().indexOf(h);
						}
						// Saca indexes de sB
						if (h.equals(sB.getHoraInicial())) {
							inHIsB = sB.getHorasA().indexOf(h);
						} else if (h.equals(sB.getHoraFinal())) {
							inHFsB = sB.getHorasA().indexOf(h);
						}
					}

					if ((inHIsA > inHIsB) && (inHIsA <= inHFsB)) {
						if (inHIsA == inHFsB) {
							aprobado = true;
						} else {
							aprobado = validaAreas(sA, sB);
						}
					} else if ((inHFsA > inHIsB) && (inHFsA <= inHFsB)) {
						aprobado = validaAreas(sA, sB);
					}
				}
			}
		}

		return aprobado;
	}

	private static boolean validaAreas(SolicitudReservAreaDTO areaA, SolicitudReservAreaDTO areaB) {
		boolean res;

		if (areaA.getIdArea() == areaB.getIdArea()) {
			res = false;
		} else {
			res = true;
		}

		return res;
	}

}
