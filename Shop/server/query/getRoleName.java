package server.query;

import java.sql.Connection;
import java.sql.ResultSet;

import server.ErrorManager;
import server.utilities.DatabaseConnectionPool;
import server.utilities.Structure;

public class getRoleName implements Query{

	public Structure execute(Structure args) {
		Connection conn=null;
		try {
			conn=DatabaseConnectionPool.getConnection();
			String query=
				"SELECT "+
					"description "+
				"FROM "+
					"role "+
				"WHERE "+
					"name='"+args.getValue()+"'"+
				";";
			ResultSet rs=conn.createStatement().executeQuery(query);
			if(!rs.next())throw new Exception("роль пользователя не найдена");
			return(new Structure(rs.getString(1)));
		}catch(Exception e) {
			ErrorManager.register(getRoleName.class.getName()+".execute: "+e);
			return(null);
		}finally {
			DatabaseConnectionPool.releaseConnection(conn);
		}
	}

}
