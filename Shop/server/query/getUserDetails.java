package server.query;

import java.sql.Connection;
import java.sql.ResultSet;

import server.ErrorManager;
import server.utilities.DatabaseConnectionPool;
import server.utilities.Structure;

public class getUserDetails implements Query{

	public Structure execute(Structure args) {
		Connection conn=null;
		try {
			conn=DatabaseConnectionPool.getConnection();
			String query=
				"SELECT "+
					"person.id, "+
					"person.password, "+
					"person.role "+
				"FROM "+
					"person "+
				"WHERE "+
					"person.login='"+args.getValue(0)+"'"+
				";";
			ResultSet rs=conn.createStatement().executeQuery(query);
			Structure res=new Structure("");
			if(!rs.next())return(res);
			res.setValue(""+rs.getLong(1))
				.addChild(rs.getString(2))
				.addChild(rs.getString(3))
			;
			return(res);
		}catch(Exception e) {
			ErrorManager.register(getUserDetails.class.getName()+".execute(Structure): "+e);
			return(null);
		}finally {
			DatabaseConnectionPool.releaseConnection(conn);
		}
	}
	
}
