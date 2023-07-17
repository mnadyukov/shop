package server.query;

import java.sql.Connection;
import java.sql.ResultSet;

import server.ErrorManager;
import server.utilities.DatabaseConnectionPool;
import server.utilities.Structure;

public class getProduct implements Query{

	public Structure execute(Structure args) {
		Connection conn=null;
		try {
			conn=DatabaseConnectionPool.getConnection();
			String query=
				"SELECT "+
					"p.id, "+
					"p.name, "+
					"p.description, "+
					"p.price, "+
					"p.product_catalog, "+
					"p.status, "+
					"ps.name "+
				"FROM "+
					"product p LEFT JOIN product_status ps ON p.status=ps.id "+
				"WHERE "+
					"p.id="+args.getValue(0)+
				";";
			ResultSet rs=conn.createStatement().executeQuery(query);
			if(!rs.next())throw new Exception("товар не найден");
			Structure res=new Structure("")
				.addChild(""+rs.getLong(1))
				.addChild(rs.getString(2))
				.addChild(rs.getString(3))
				.addChild(""+rs.getDouble(4))
				.addChild(""+rs.getLong(5))
				.addChild(""+rs.getShort(6))
				.addChild(rs.getString(7));
			return(res);
		}catch(Exception e) {
			ErrorManager.register(getProduct.class.getName()+".execute(Structure): "+e);
			return(null);
		}finally {
			DatabaseConnectionPool.releaseConnection(conn);
		}
	}

	
	
}
