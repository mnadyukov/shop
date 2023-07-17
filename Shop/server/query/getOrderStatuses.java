package server.query;

import java.sql.Connection;
import java.sql.ResultSet;

import server.ErrorManager;
import server.utilities.DatabaseConnectionPool;
import server.utilities.Structure;

public class getOrderStatuses implements Query {

	public Structure execute(Structure args) {
		Connection conn=null;
		try {
			conn=DatabaseConnectionPool.getConnection();
			String query=
				"SELECT "+
					"id, "+
					"name "+
				"FROM "+
					"zakaz_status "+
				";";
			ResultSet rs=conn.createStatement().executeQuery(query);
			Structure res=new Structure("");
			while(rs.next()) {
				res.addChild(new Structure(""+rs.getShort(1)).addChild(rs.getString(2)));
			}
			return(res);
		}catch(Exception e) {
			ErrorManager.register(getOrderStatuses.class.getName()+".execute: "+e);
			return(null);
		}finally {
			DatabaseConnectionPool.releaseConnection(conn);
		}
	}

}
