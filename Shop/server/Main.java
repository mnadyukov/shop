package server;

import server.http.HttpListener;

/**
 * Осуществляет запуск серверной части приложения.
 * @version 1.0
 * @author Надюков Михаил
 */
public class Main {

	/**
	 * Запускает серверную часть приложения.
	 * @param args Стандартный аргумент функции main.
	 * В данном приложении игнорируется.
	 */
	public static void main(String[] args) {
		HttpListener.listen();
	}

}
