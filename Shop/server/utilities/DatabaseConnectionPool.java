package server.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Properties;

import server.ErrorManager;
import server.SystemParameters;

/**
 * Пул соединений с базой данных.
 * Каждые 60 секунд из пула удаляется свободное соединение, если таких более одного.
 * Одно соединение всегда присутствует в пуле.
 * Если свободных соединений нет, то по запросу создается новое соединение и немедленно передается запросившему его объекту.
 * @version 1.0
 * @author Надюков Михаил
 */
public class DatabaseConnectionPool {
	
	private static class Cleaner implements Runnable{
		
		public void run() {
			try {
				while(true) {
					Thread.sleep(60000);
					cleanPool();
				}
			}catch(Exception e) {
				ErrorManager.register(Cleaner.class.getName()+".run(): "+e);
			}
		}
		
	}

	private static ArrayList<Connection> pool;
	
	static {
		try {
			pool=new ArrayList<Connection>();
//			Class.forName(SystemParameters.DATABASE_DRIVER);
			new Thread(new Cleaner()).start();
		} catch (Exception e) {
			ErrorManager.register(DatabaseConnectionPool.class.getName()+".static: "+e);
		}
	}
	
	/**
	 * Предоставляет соединение с базой данных из пула.
	 * @return Соединение с базой данных, готовое к использованию.
	 * Предоставляемое соединение не имеет открытых транзакций.
	 * Предоставляемое соединение находится в режиме автоматического подтверждения транзакций.
	 * null, если произошла ошибка.
	 */
	public static synchronized Connection getConnection() {
		try {
			if(pool.size()==0) {
				return(createConnection());
			}else {
				return(pool.remove(pool.size()-1));
			}
		}catch(Exception e) {
			ErrorManager.register(DatabaseConnectionPool.class.getName()+".getConnection(): "+e);
			return(null);
		}
		
	}
	
	/**
	 * Возвращает соединение с базой данных в пул.
	 * Возвращаются только открытые соединения.
	 * Для возвращенного соединения устанавливается автоматический режим подтверждения транзакций.
	 * Если соединение имело незавершенные транзакции, то эти транзакции полностью откатываются.
	 * @param conn Возвращаемое в пул соединение с базой данных.
	 */
	public static synchronized void releaseConnection(Connection conn) {
		try {
			if(conn==null || conn.isClosed())return;
			try {conn.rollback();}catch(Exception e) {}
			conn.setAutoCommit(true);
			pool.add(conn);
		}catch(Exception e) {
			ErrorManager.register(DatabaseConnectionPool.class.getName()+".releaseConnection(Connection): "+e);
		}
	}
	
	private static Connection createConnection() {
		try {
			Properties props=new Properties();
			props.setProperty("user",SystemParameters.DATABASE_USER);
			props.setProperty("password",SystemParameters.DATABASE_PASSWORD);
			props.setProperty("encoding","UTF8");
			Connection conn=DriverManager.getConnection(SystemParameters.DATABASE_CONNECTION,props);
			conn.setAutoCommit(true);
			return(conn);
		}catch(Exception e) {
			ErrorManager.register(DatabaseConnectionPool.class.getName()+".createConnection(): "+e);
			return(null);
		}
	}
	
	private static synchronized void cleanPool() {
		if(pool.size()>1)pool.remove(pool.size()-1);
	}
	
}
