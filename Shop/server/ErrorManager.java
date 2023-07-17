package server;

import java.util.Calendar;

import server.utilities.Converter;

/**
 * Осуществляет сквозную регистрацию ошибок, возникающих в приложении.
 * Текущая реализация осуществляет лишь вывод соответствующего сообщения в стандартный поток ошибок.
 * @version 1.0
 * @author Надюков Михаил
*/
public class ErrorManager {

	/**
	 * Осуществляет регистрацию ошибки приложения.
	 * @param error Текстовое описание ошибки.
	 */
	public static void register(String error) {
		System.err.println(
			Converter.TimestampToString(Calendar.getInstance().getTimeInMillis())+
			"=>"+
			error
		);
	}
}
