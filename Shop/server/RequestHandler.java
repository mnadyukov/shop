package server;

import java.net.Socket;

import server.action.Action;
import server.http.HttpServer;
import server.utilities.Structure;

/**
 * Обрабатывает запросы клиентов.
 * Для каждого запроса создается отдельный объект данного класса, который запускается на выполнение в отдельном потоке.
 * @version 1.0
 * @author Надюков Михаил
 */
public class RequestHandler implements Runnable{

	private HttpServer HS;
	private User user;
	
	/**
	 * Создает объект данного класса.
	 * @param s Сокет, через который осуществлено соединение с клиентом.
	 */
	public RequestHandler(Socket s) {
		user=new User();
		HS=new HttpServer(s);
	}
	
	/**
	 * Управляет обработкой запроса клиента.
	 */
	public void run() {
		try {
			String message=HS.getMessage(user);
			if(message==null)throw new Exception("ошибка при получении сообщения");
			if(message.equals("\u0000")) {
				HS.sendMessage(new Structure("").addChild("wrong_user").toString(),user);
				return;
			}
			if(message.isEmpty()) {
				return;
			}else {
				Structure msg=Structure.fromString(message);
				Action action=(Action)Class.forName("server.action."+msg.getValue()).getConstructor().newInstance();
				message=action.execute(msg,user);
				if(message==null) {
					HS.sendMessage(new Structure("").addChild("server_error").toString(), user);
					throw new Exception("ошибка обработки сообщения");
				}
				HS.sendMessage(message,user);
			}
		}catch(Exception e) {
			ErrorManager.register(RequestHandler.class.getName()+".run(): "+e);
		}finally {
			HS.close();
		}
	}
	
}
