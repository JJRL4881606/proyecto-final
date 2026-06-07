package utils;

import java.util.Calendar;
import java.util.Date;

//Métodos auxiliares para trabajar con fechas
public class DateUtils {

	 // Elimina la hora de una fecha para comparar solo el día
	 public static Date normalize(Date date) {
	     Calendar cal = Calendar.getInstance();
	
	     // Carga la fecha recibida en el calendario
	     cal.setTime(date);
	
	     // Reinicia la hora a las 00:00:00
	     cal.set(Calendar.HOUR_OF_DAY, 0);
	     cal.set(Calendar.MINUTE, 0);
	     cal.set(Calendar.SECOND, 0);
	     cal.set(Calendar.MILLISECOND, 0);
	
	     // Devuelve la fecha sin información de hora
	     return cal.getTime();
	 }
	
	 // Suma o resta una cantidad de días a una fecha
	 public static Date addDays(Date date, int days) {
	     Calendar cal = Calendar.getInstance();
	
	     // Carga la fecha recibida en el calendario
	     cal.setTime(date);
	
	     // Agrega los días indicados (o los resta si son negativos)
	     cal.add(Calendar.DAY_OF_MONTH, days);
	
	     // Devuelve la nueva fecha calculada
	     return cal.getTime();
	 }
}