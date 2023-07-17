package server.query;

import java.sql.Connection;

import server.ErrorManager;
import server.utilities.DatabaseConnectionPool;
import server.utilities.Structure;

public class editAccount implements Query{

	public Structure execute(Structure args) {
		Connection conn=null;
		try {
			conn=DatabaseConnectionPool.getConnection();
			String query=
				"UPDATE "+
					"person "+
				"SET "+
					"login='"+args.getChildren("login").get(0).getValue(0)+"', "+
					"password='"+args.getChildren("password").get(0).getValue(0)+"', "+
					"first_name='"+args.getChildren("first_name").get(0).getValue(0)+"', "+
					"second_name='"+args.getChildren("second_name").get(0).getValue(0)+"', "+
					"patronymic='"+args.getChildren("patronymic").get(0).getValue(0)+"', "+
					"phone='"+args.getChildren("phone").get(0).getValue(0)+"', "+
					"email='"+args.getChildren("email").get(0).getValue(0)+"' "+
				"WHERE "+
					"id="+args.getValue()+
				";";
			conn.createStatement().execute(query);
			return(args);
		}catch(Exception e) {
			ErrorManager.register(editAccount.class.getName()+".execute(Structure): "+e);
			return(null);
		}finally {
			DatabaseConnectionPool.releaseConnection(conn);
		}
	}

	
	
}
