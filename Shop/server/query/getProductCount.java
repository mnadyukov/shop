package server.query;

import java.sql.Connection;
import java.sql.ResultSet;

import server.ErrorManager;
import server.utilities.DatabaseConnectionPool;
import server.utilities.Structure;

public class getProductCount implements Query {

	public Structure execute(Structure args) {
		Connection conn=null;
		try {
			conn=DatabaseConnectionPool.getConnection();
			String query=
				"SELECT "+
					"COUNT(1) "+
				"FROM "+
					"product "+
				"WHERE "+
					"product_catalog="+args.getValue()+
				";";
			ResultSet rs=conn.createStatement().executeQuery(query);
			if(!rs.next())throw new Exception("ошибка базы данных");
			return(new Structure(""+rs.getInt(1)));
		}catch(Exception e) {
			ErrorManager.register(getProductCount.class.getName()+".execute: "+e);
			return(null);
		}finally {
			DatabaseConnectionPool.releaseConnection(conn);
		}
	}

}
