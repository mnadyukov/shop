package server.query;

import java.sql.Connection;
import java.sql.ResultSet;

import server.ErrorManager;
import server.utilities.DatabaseConnectionPool;
import server.utilities.Structure;

public class catalogRemove implements Query {

	public Structure execute(Structure args) {
		Connection conn=null;
		try {
			conn=DatabaseConnectionPool.getConnection();
			String query=
				"SELECT "+
					"id "+
				"FROM "+
					"product_catalog "+
				"WHERE "+
					"parent="+args.getValue(1)+
				";";
			ResultSet rs=conn.createStatement().executeQuery(query);
			if(rs.next())return(new Structure("NO"));
			query=
				"SELECT "+
					"id "+
				"FROM "+
					"product "+
				"WHERE "+
					"product_catalog="+args.getValue(1)+
				";";
			rs=conn.createStatement().executeQuery(query);
			if(rs.next())return(new Structure("NO"));
			query="DELETE FROM product_catalog WHERE id="+args.getValue(1)+";";
			conn.createStatement().execute(query);
			return(new Structure("YES"));
		}catch(Exception e) {
			ErrorManager.register(catalogRemove.class.getName()+".execute: "+e);
			return(null);
		}finally {
			DatabaseConnectionPool.releaseConnection(conn);
		}
	}

}
