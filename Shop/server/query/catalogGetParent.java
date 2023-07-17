package server.query;

import java.sql.Connection;
import java.sql.ResultSet;

import server.ErrorManager;
import server.utilities.DatabaseConnectionPool;
import server.utilities.Structure;

public class catalogGetParent implements Query {

	public Structure execute(Structure args) {
		Connection conn=null;
		try {
			conn=DatabaseConnectionPool.getConnection();
			String query=
				"SELECT "+
					"parent "+
				"FROM "+
					"product_catalog "+
				"WHERE "+
					"id="+args.getValue(0)+
				";";
			ResultSet rs=conn.createStatement().executeQuery(query);
			if(!rs.next())throw new Exception("родительская группа не найдена");
			return(new Structure(""+rs.getLong(1)));
		}catch(Exception e) {
			ErrorManager.register(catalogGetParent.class.getName()+".execute: "+e);
			return(null);
		}finally {
			DatabaseConnectionPool.releaseConnection(conn);
		}
	}

}
