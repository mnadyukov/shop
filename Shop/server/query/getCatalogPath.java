package server.query;

import java.sql.Connection;
import java.sql.ResultSet;

import server.ErrorManager;
import server.utilities.DatabaseConnectionPool;
import server.utilities.Structure;

public class getCatalogPath implements Query{

	public Structure execute(Structure args) {
		Connection conn=null;
		try {
			conn=DatabaseConnectionPool.getConnection();
			String query=
				"WITH RECURSIVE "+
					"groups (id,name,parent) "+
				"AS ("+
					"SELECT "+
						"id, "+
						"name, "+
						"parent "+
					"FROM "+
						"product_catalog "+
					"WHERE "+
						"id="+args.getValue()+" "+
					"UNION ALL "+
					"SELECT "+
						"pc.id, "+
						"pc.name, "+
						"pc.parent "+
					"FROM "+
						"product_catalog pc "+
						"JOIN groups g ON g.parent=pc.id "+
				") SELECT "+
					"name "+
				"FROM "+
					"groups "+
				";";
			ResultSet rs=conn.createStatement().executeQuery(query);
			String path="";
			while(rs.next())path="/"+rs.getString(1)+path;
			return(new Structure(path.isEmpty()?"":path.substring(1)));
		}catch(Exception e) {
			ErrorManager.register(getCatalogPath.class.getName()+".execute: "+e);
			return(null);
		}finally {
			DatabaseConnectionPool.releaseConnection(conn);
		}
	}

	
	
}
