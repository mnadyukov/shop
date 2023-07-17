package server.query;

import java.sql.Connection;
import java.sql.ResultSet;

import server.ErrorManager;
import server.utilities.Converter;
import server.utilities.DatabaseConnectionPool;
import server.utilities.Structure;

public class getOrders implements Query{

	public Structure execute(Structure args) {
		Connection conn=null;
		try {
			conn=DatabaseConnectionPool.getConnection();
			String query=
				"SELECT DISTINCT "+
					"z.id, "+
					"z.zakaz_date, "+
					"z.delivery_date, "+
					"zs.name, "+
					"(SELECT SUM(price*quantity) FROM zakaz_product WHERE zakaz=z.id GROUP BY zakaz) "+
				"FROM "+
					"zakaz z "+
						"LEFT JOIN zakaz_status zs ON z.status=zs.id "+
				"WHERE "+
					"z.customer="+args.getValue()+" "+
				";";
			Structure res=new Structure("");
			ResultSet rs=conn.createStatement().executeQuery(query);
			while(rs.next())
				res.addChild(new Structure("")
					.addChild(""+rs.getLong(1))
					.addChild(Converter.TimestampToString(rs.getDate(2).getTime()).split(" ")[0])
					.addChild(rs.getDate(3)==null?"":Converter.TimestampToString(rs.getDate(3).getTime()).split(" ")[0])
					.addChild(rs.getString(4))
					.addChild(""+rs.getDouble(5))
				);
			return(res);
		}catch(Exception e) {
			ErrorManager.register(getOrders.class.getName()+".execute(Structure): "+e);
			return(null);
		}finally {
			DatabaseConnectionPool.releaseConnection(conn);
		}
	}

}
