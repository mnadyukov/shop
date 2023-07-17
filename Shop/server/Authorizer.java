package server;

import server.query.getUserDetails;
import server.utilities.Structure;

/**
 * Предназначен для получения информации о текущем пользователе из базы данных.
 * Полученная информация используется для заполнения объекта {@link server.User User}.
 * @version 1.0
 * @author Надюков Михаил
 */
public class Authorizer {

	/**
	 * Выполняет получение информации о текущем пользователе и заполняет объект {@link server.User User}.
	 * @param user Объект {@link server.User User}, который будет заполнен.
	 */
	public static void authorize(User user) {
		Structure res=new getUserDetails().execute(new Structure("").addChild(user.NAME));
		if(res.getChildren().size()==0) {
			user.PASSWORD=null;
		}else {
			user.ID=Long.parseLong(res.getValue());
			user.PASSWORD=res.getValue(0);
			user.ROLE=res.getValue(1);
		}
	}
	
}
