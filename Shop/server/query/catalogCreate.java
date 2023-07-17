package server.query;

import java.sql.Connection;

import server.ErrorManager;
import server.utilities.DatabaseConnectionPool;
import server.utilities.Structure;

public class catalogCreate implements Query {

	public Structure execute(Structure args) {
		Connection conn=null;
		try {
			conn=DatabaseConnectionPool.getConnection();
			String query=
				"INSERT INTO "+
					"product_catalog "+
				"("+
					"id, "+
					"parent, "+
					"name "+
				") VALUES ("+
					"NEXT VALUE FOR product_catalog_id, "+
					args.getValue(0)+", "+
					"'"+args.getValue(3)+"'"+
				");";
			conn.createStatement().execute(query);
			return(args);
		}catch(Exception e) {
			ErrorManager.register(catalogCreate.class.getName()+".execute: "+e);
			return(null);
		}finally {
			DatabaseConnectionPool.releaseConnection(conn);
		}
	}

}
