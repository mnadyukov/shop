package server.query;

import java.sql.Connection;
import java.sql.ResultSet;

import server.ErrorManager;
import server.utilities.Converter;
import server.utilities.DatabaseConnectionPool;
import server.utilities.Structure;

public class getOrdersByStatus implements Query {

	public Structure execute(Structure args) {
		Connection conn=null;
		try {
			conn=DatabaseConnectionPool.getConnection();
			String query=
				"SELECT "+
					"z.id, "+
					"z.zakaz_date, "+
					"(SELECT SUM(price*quantity) FROM zakaz_product WHERE zakaz=z.id GROUP BY zakaz) "+
				"FROM "+
					"zakaz z "+
				(args.getValue(0).isEmpty()?"":
					"WHERE "+
						"z.status="+args.getValue(0))+
				";";
			ResultSet rs=conn.createStatement().executeQuery(query);
			Structure res=new Structure("");
			while(rs.next()) {
				res.addChild(
					new Structure("")
						.addChild(""+rs.getLong(1))
						.addChild(Converter.TimestampToString(rs.getDate(2).getTime()).split(" ")[0])
						.addChild(""+rs.getDouble(3))
				);
			}
			return(res);
		}catch(Exception e) {
			ErrorManager.register(getOrdersByStatus.class.getName()+".execute: "+e);
			return(null);
		}finally {
			DatabaseConnectionPool.releaseConnection(conn);
		}
	}

}
