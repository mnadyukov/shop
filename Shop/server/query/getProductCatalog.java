package server.query;

import java.sql.Connection;
import java.sql.ResultSet;

import server.ErrorManager;
import server.utilities.DatabaseConnectionPool;
import server.utilities.Structure;

public class getProductCatalog implements Query{

	public Structure execute(Structure args) {
		Connection conn=null;
		try {
			Structure res=new Structure("");
			String query=
				"SELECT "+
					"id, "+
					"parent, "+
					"name "+
				"FROM "+
					"product_catalog"+
				";";
			conn=DatabaseConnectionPool.getConnection();
			ResultSet rs=conn.createStatement().executeQuery(query);
			while(rs.next()) 
				res.addChild(
					new Structure("")
						.addChild(rs.getLong(1)+"")
						.addChild(rs.getLong(2)+"")
						.addChild(rs.getString(3))
				);
			return(res);
		}catch(Exception e) {
			ErrorManager.register(getProductCatalog.class.getName()+".execute(Structure): "+e);
			return(null);
		}finally {
			DatabaseConnectionPool.releaseConnection(conn);
		}
	}
	
}
