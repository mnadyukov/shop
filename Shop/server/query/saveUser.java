package server.query;

import java.sql.Connection;

import server.ErrorManager;
import server.utilities.DatabaseConnectionPool;
import server.utilities.Structure;

public class saveUser implements Query{
	
	public Structure execute(Structure args) {
		Connection conn=null;
		try {
			String query=
				"INSERT INTO "+
					"person "+
				"VALUES ("+
					"NEXT VALUE FOR person_id, "+//id
					"'"+args.getChildren("login").get(0).getValue(0)+"', "+//login
					"'"+args.getChildren("password").get(0).getValue(0)+"', "+//password
					"'customer', "+//role
					"'"+args.getChildren("first_name").get(0).getValue(0)+"', "+//first_name
					"'"+args.getChildren("second_name").get(0).getValue(0)+"', "+//second_name
					"'"+args.getChildren("patronymic").get(0).getValue(0)+"', "+//patronymic
					"'"+args.getChildren("address").get(0).getValue(0)+"', "+//address
					"'"+args.getChildren("phone").get(0).getValue(0)+"', "+//phone
					"'"+args.getChildren("email").get(0).getValue(0)+"'"+//email
				");";
			conn=DatabaseConnectionPool.getConnection();
			conn.createStatement().execute(query);
			return(new Structure("YES"));
		}catch(Exception e) {
			ErrorManager.register(saveUser.class.getName()+".execute(Structure): "+e);
			return(null);
		}finally {
			DatabaseConnectionPool.releaseConnection(conn);
		}
	}
}
