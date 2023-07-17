package server.query;

import java.sql.Connection;
import java.sql.ResultSet;

import server.ErrorManager;
import server.utilities.DatabaseConnectionPool;
import server.utilities.Structure;

public class getUsersByRole implements Query{

	public Structure execute(Structure args) {
		Connection conn=null;
		try {
			conn=DatabaseConnectionPool.getConnection();
			String query=
				"SELECT "+
					"p.id, "+
					"p.first_name, "+
					"p.second_name, "+
					"p.patronymic, "+
					"r.description "+
				"FROM "+
					"person p "+
					"LEFT JOIN role r ON p.role=r.name "+
				(args.getValue(0).isEmpty()?"":
				("WHERE "+
					"role='"+args.getValue(0)+"' "))+
				"ORDER BY "+
					"id ASC "+
				";";
			ResultSet rs=conn.createStatement().executeQuery(query);
			Structure res=new Structure("");
			while(rs.next()) {
				res.addChild(
					new Structure(""+rs.getLong(1))
						.addChild(rs.getString(2))
						.addChild(rs.getString(3))
						.addChild(rs.getString(4))
						.addChild(rs.getString(5))
				);
			}
			return(res);
		}catch(Exception e) {
			ErrorManager.register(getUsersByRole.class.getName()+".execute(Structure): "+e);
			return(null);
		}finally {
			DatabaseConnectionPool.releaseConnection(conn);
		}
	}

}
