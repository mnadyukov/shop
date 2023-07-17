package server.query;

import java.sql.Connection;
import java.sql.ResultSet;

import server.ErrorManager;
import server.utilities.DatabaseConnectionPool;
import server.utilities.Structure;

public class getProductsByCatalog implements Query{

	public Structure execute(Structure args) {
		Connection conn=null;
		try {
			String query=
				"SELECT "+
				"FIRST "+args.getValue(1)+" "+
				"SKIP "+args.getValue(0)+" "+
					"product.id, "+
					"product.name, "+
					"product.price "+
				"FROM "+
					"product "+
				"WHERE "+
					"product.product_catalog="+args.getValue()+" "+
				";";
System.out.println(query);
			conn=DatabaseConnectionPool.getConnection();
			ResultSet rs=conn.createStatement().executeQuery(query);
			Structure res=new Structure("");
			while(rs.next())
				res.addChild(
					new Structure("")
						.addChild(rs.getLong(1)+"")
						.addChild(rs.getString(2))
						.addChild(rs.getDouble(3)+"")
				);
			return(res);
		}catch(Exception e) {
			ErrorManager.register(getProductsByCatalog.class.getName()+".execute: "+e);
			return(null);
		}finally {
			DatabaseConnectionPool.releaseConnection(conn);
		}
	}
	
}
