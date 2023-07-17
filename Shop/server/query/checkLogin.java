package server.query;

import java.sql.Connection;
import java.sql.ResultSet;

import server.ErrorManager;
import server.utilities.DatabaseConnectionPool;
import server.utilities.Structure;

public class checkLogin implements Query{

	public Structure execute(Structure args) {
		Connection conn=null;
		try {
			String query=
				"SELECT "+
					"person.login "+
				"FROM "+
					"person "+
				"WHERE "+
					"login='"+args.getValue()+"'"+
				";";
			conn=DatabaseConnectionPool.getConnection();
			ResultSet rs=conn.createStatement().executeQuery(query);
			if(rs.next()) {
				return(new Structure("YES"));
			}else {
				return(new Structure("NO"));
			}
		}catch(Exception e) {
			ErrorManager.register(checkLogin.class.getName()+".execute: "+e);
			return(null);
		}finally {
			DatabaseConnectionPool.releaseConnection(conn);
		}
	}
	
}
