package server.query;

import java.sql.Connection;

import server.ErrorManager;
import server.utilities.DatabaseConnectionPool;
import server.utilities.Structure;

public class catalogRename implements Query {

	public Structure execute(Structure args) {
		Connection conn=null;
		try {
			conn=DatabaseConnectionPool.getConnection();
			String query=
				"UPDATE "+
					"product_catalog "+
				"SET "+
					"name='"+args.getValue(3)+"' "+
				"WHERE "+
					"id="+args.getValue(1)+
				";";
			conn.createStatement().execute(query);
			return(args);
		}catch(Exception e) {
			ErrorManager.register(catalogRename.class.getName()+".execute: "+e);
			return(null);
		}finally {
			DatabaseConnectionPool.releaseConnection(conn);
		}
	}

}
