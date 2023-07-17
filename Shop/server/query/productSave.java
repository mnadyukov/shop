package server.query;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Base64;
import java.io.File;
import java.io.FileOutputStream;

import server.ErrorManager;
import server.SystemParameters;
import server.utilities.DatabaseConnectionPool;
import server.utilities.Structure;

public class productSave implements Query {

	public Structure execute(Structure args) {
		Connection conn=null;
		boolean autoCommit=true;
		FileOutputStream fos=null;
		try {
			conn=DatabaseConnectionPool.getConnection();
			autoCommit=conn.getAutoCommit();
			conn.setAutoCommit(false);
			String query=
				"INSERT INTO "+
					"product "+
				"( "+
					"id, "+
					"name, "+
					"description, "+
					"price, "+
					"product_catalog, "+
					"status "+
				") VALUES ("+
					"NEXT VALUE FOR product_id, "+
					"'"+args.getChildren("name").get(0).getValue(0)+"', "+
					"'"+args.getChildren("desc").get(0).getValue(0)+"', "+
					args.getChildren("price").get(0).getValue(0)+", "+
					args.getChildren("catalog").get(0).getValue(0)+", "+
					"1 "+
				") RETURNING "+
					"id"+
				";";
			ResultSet rs=conn.createStatement().executeQuery(query);
			if(!rs.next())throw new Exception("1:ошибка сохранения данных нового товара");
			long id=rs.getLong(1);
			File f=new File(SystemParameters.SERVER_DIRECTORY+"/client/resources/"+id+".jpg");
			if(f.exists())f.delete();
			f.createNewFile();
			fos=new FileOutputStream(f);
			String img_base64=args.getChildren("img").get(0).getValue(0);
			fos.write(Base64.getDecoder().decode(img_base64));
			conn.commit();
			return(args);
		}catch(Exception e) {
			ErrorManager.register(productSave.class.getName()+".execute: "+e);
			try {
				conn.rollback();
			}catch(Exception ex) {
				ErrorManager.register(productSave.class.getName()+".execute.catch: "+ex);
			}
			return(null);
		}finally {
			try {
				conn.setAutoCommit(autoCommit);
				if(fos!=null)fos.close();
			}catch(Exception ex) {
				ErrorManager.register(productSave.class.getName()+".execute.finally: "+ex);
			}
			try {
				if(fos!=null)fos.close();
			}catch(Exception ex) {
				ErrorManager.register(productSave.class.getName()+".execute.finally: "+ex);
			}
			DatabaseConnectionPool.releaseConnection(conn);
		}
	}

}
