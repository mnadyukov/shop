package server.query;

import java.sql.Connection;
import java.sql.ResultSet;

import server.ErrorManager;
import server.utilities.DatabaseConnectionPool;
import server.utilities.Structure;

public class getRoles implements Query{

	public Structure execute(Structure args) {
		Connection conn=null;
		try {
			conn=DatabaseConnectionPool.getConnection();
			String query=
				"SELECT "+
					"name, "+
					"description "+
				"FROM "+
					"role "+
				";";
			ResultSet rs=conn.createStatement().executeQuery(query);
			Structure res=new Structure("");
			while(rs.next())res.addChild(new Structure(rs.getString(1)).addChild(rs.getString(2)));
			return(res);
		}catch(Exception e) {
			ErrorManager.register(getRoles.class.getName()+".execute(Structure): "+e);
			return(null);
		}finally {
			DatabaseConnectionPool.releaseConnection(conn);
		}
	}

	
	
}
