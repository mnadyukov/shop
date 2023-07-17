package server.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import server.ErrorManager;
import server.utilities.Converter;
import server.utilities.DatabaseConnectionPool;
import server.utilities.Structure;

public class setOrder implements Query{

	public Structure execute(Structure args){
		Connection conn=null;
		boolean autoCommit=true;
		try {
			conn=DatabaseConnectionPool.getConnection();
			autoCommit=conn.getAutoCommit();
			conn.setAutoCommit(false);
			String query=
				"INSERT INTO "+
					"Zakaz "+
				"( "+
					"id, "+
					"customer, "+
					"zakaz_date, "+
					"status, "+
					"note "+
				") VALUES ("+
					"NEXT VALUE FOR zakaz_id, "+
					args.getValue()+", "+
					"'"+Converter.TimestampToString(Calendar.getInstance().getTimeInMillis()).split(" ")[0]+"', "+
					"1, "+
					"'"+args.getValue(1)+"' "+
				") RETURNING "+
					"id"+
				";";
			ResultSet rs=conn.createStatement().executeQuery(query);
			if(!rs.next())throw new Exception("ошибка при записи заказа в базу данных");
			long order_id=rs.getLong(1);
			query=
				"INSERT INTO "+
					"zakaz_product "+
				"( "+
					"zakaz, "+
					"ordinal, "+
					"product, "+
					"price, "+
					"quantity "+
				") VALUES ( "+
					order_id+", "+
					"?, "+
					"?, "+
					"?, "+
					"? "+
				");";
			PreparedStatement pstmt=conn.prepareStatement(query);
			Structure order=args.getChild(0);
			for(int i=0;i<order.getChildren().size();i++) {
				pstmt.setInt(1,i);
				pstmt.setLong(2, Long.parseLong(order.getChild(i).getValue()));
				pstmt.setDouble(3, Double.parseDouble(order.getChild(i).getValue(0)));
				pstmt.setDouble(4, Double.parseDouble(order.getChild(i).getValue(1)));
				pstmt.execute();
			}
			conn.commit();
			Structure res=new Structure(""+order_id);
			return(res);
		}catch(Exception e) {
			ErrorManager.register(setOrder.class.getName()+".execute(Structure): "+e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				ErrorManager.register(setOrder.class.getName()+".execute(Structure).catch: "+e1);
			}
			return(null);
		}finally {
			try {
				conn.setAutoCommit(autoCommit);
			} catch (SQLException e) {
				ErrorManager.register(setOrder.class.getName()+".execute(Structure).finally: "+e);
			}
			DatabaseConnectionPool.releaseConnection(conn);
		}
	}

}
