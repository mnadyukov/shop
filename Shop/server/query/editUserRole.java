package server.query;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import server.ErrorManager;
import server.utilities.DatabaseConnectionPool;
import server.utilities.Structure;

public class editUserRole implements Query{

	public Structure execute(Structure args) {
		Connection conn=null;
		boolean autoCommit=true;
		try {
			conn=DatabaseConnectionPool.getConnection();
			autoCommit=conn.getAutoCommit();
			conn.setAutoCommit(false);
			String query=
				"UPDATE "+
					"person "+
				"SET "+
					"role='"+args.getChildren("role").get(0).getValue(0)+"' "+
				"WHERE "+
					"id="+args.getChildren("person").get(0).getValue(0)+
				";";
			conn.createStatement().execute(query);
			query=
				"SELECT "+
					"COUNT(1) "+
				"FROM "+
					"person "+
				"WHERE "+
					"role='admin'"+
				";";
			ResultSet rs=conn.createStatement().executeQuery(query);
			if(!rs.next())throw new Exception("ошибка подсчета системных администраторов");
			if(rs.getInt(1)==0) {//администраторов больше нет
				conn.rollback();
				return(new Structure("NO"));
			}else {
				conn.commit();
				return(new Structure("YES"));
			}
		}catch(Exception e) {
			ErrorManager.register(editUserRole.class.getName()+".execute(Structure): "+e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				ErrorManager.register(editUserRole.class.getName()+".execute(Structure).catch: "+e1);
			}
			return(null);
		}finally {
			try {
				conn.setAutoCommit(autoCommit);
			} catch (SQLException e) {
				ErrorManager.register(editUserRole.class.getName()+".execute(Structure).finally: "+e);
			}
			DatabaseConnectionPool.releaseConnection(conn);
		}
	}

}
