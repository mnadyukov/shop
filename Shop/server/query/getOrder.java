package server.query;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import server.ErrorManager;
import server.utilities.Converter;
import server.utilities.DatabaseConnectionPool;
import server.utilities.Structure;

public class getOrder implements Query {

	public Structure execute(Structure args) {
		Connection conn=null;
		boolean autoCommit=true;
		try {
			conn=DatabaseConnectionPool.getConnection();
			autoCommit=conn.getAutoCommit();
			conn.setAutoCommit(false);
			String query=
				"SELECT "+
					"z.id, "+
					"z.zakaz_date, "+
					"z.delivery_date, "+
					"zs.name, "+
					"z.note "+
				"FROM "+
					"zakaz z "+
						"LEFT JOIN zakaz_status zs ON z.status=zs.id "+
				"WHERE "+
					"z.id="+args.getValue(0)+
				";";
			ResultSet rs=conn.createStatement().executeQuery(query);
			if(!rs.next())throw new Exception("заказ №"+args.getValue(0)+" не найден");
			Structure res=new Structure("")
				.addChild(rs.getLong(1)+"")
				.addChild(Converter.TimestampToString(rs.getDate(2).getTime()).split(" ")[0])
				.addChild(
					rs.getDate(3)==null?
						"":
						(Converter.TimestampToString(rs.getDate(3).getTime()).split(" ")[0])
				)
				.addChild(rs.getString(4))
				.addChild(rs.getString(5))
				.addChild("");
			query=
				"SELECT "+
					"zp.ordinal, "+
					"p.name, "+
					"zp.price, "+
					"zp.quantity "+
				"FROM "+
					"zakaz_product zp "+
						"LEFT JOIN product p ON p.id=zp.product "+
				"WHERE "+
					"zp.zakaz="+res.getValue(0)+" "+
				"ORDER BY "+
					"zp.ordinal "+
				";";
			rs=conn.createStatement().executeQuery(query);
			Structure order=res.getChild(5);
			while(rs.next())
				order.addChild(new Structure("")
					.addChild((rs.getInt(1)+1)+"")
					.addChild(rs.getString(2))
					.addChild(rs.getDouble(3)+"")
					.addChild(rs.getDouble(4)+"")
				);
			return(res);
		}catch(Exception e) {
			ErrorManager.register(getOrder.class.getName()+".execute(Structure): "+e);
			return(null);
		}finally {
			try {
				conn.commit();
				conn.setAutoCommit(autoCommit);
			} catch (SQLException e) {
				ErrorManager.register(getOrder.class.getName()+".execute(Structure).finally: "+e);
			}
			DatabaseConnectionPool.releaseConnection(conn);
		}
	}

}
