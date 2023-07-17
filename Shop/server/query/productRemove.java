package server.query;

import java.sql.Connection;

import server.ErrorManager;
import server.utilities.DatabaseConnectionPool;
import server.utilities.Structure;

public class productRemove implements Query {

	public Structure execute(Structure args) {
		Connection conn=null;
		try {
			conn=DatabaseConnectionPool.getConnection();
			String query=
				"DELETE FROM "+
					"product "+
				"WHERE "+
					"id="+args.getValue(0)+
				";";
			conn.createStatement().execute(query);
			return(args);
		}catch(Exception e) {
			ErrorManager.register(productRemove.class.getName()+".execute: "+e);
			return(null);
		}finally {
			DatabaseConnectionPool.releaseConnection(conn);
		}
	}

}
