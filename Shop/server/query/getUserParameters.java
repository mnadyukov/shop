package server.query;

import java.sql.Connection;
import java.sql.ResultSet;

import server.ErrorManager;
import server.utilities.DatabaseConnectionPool;
import server.utilities.Structure;

public class getUserParameters implements Query{
	
	public Structure execute(Structure args) {
		Connection conn=null;
		try {
			conn=DatabaseConnectionPool.getConnection();
			String query=
				"SELECT "+
					"first_name, "+
					"second_name, "+
					"patronymic, "+
					"address, "+
					"phone,"+
					"email "+
				"FROM "+
					"person "+
				"WHERE "+
					"login='"+args.getValue()+"'"+
				";";
			ResultSet rs=conn.createStatement().executeQuery(query);
			if(!rs.next())throw new Exception("пользователь не найден: "+args.getValue());
			Structure res=new Structure("")
				.addChild(rs.getString(1))
				.addChild(rs.getString(2))
				.addChild(rs.getString(3))
				.addChild(rs.getString(4))
				.addChild(rs.getString(5))
				.addChild(rs.getString(6));
			return(res);
		}catch(Exception e) {
			ErrorManager.register(getUserParameters.class.getName()+".execute(Structure): "+e);
			return(null);
		}finally {
			DatabaseConnectionPool.releaseConnection(conn);
		}
	}
	
}
