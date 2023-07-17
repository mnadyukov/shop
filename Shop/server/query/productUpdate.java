package server.query;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.util.Base64;

import server.ErrorManager;
import server.SystemParameters;
import server.utilities.DatabaseConnectionPool;
import server.utilities.Structure;

public class productUpdate implements Query {

	public Structure execute(Structure args) {
		Connection conn=null;
		boolean autoCommit=true;
		FileOutputStream fos=null;
		try {
			conn=DatabaseConnectionPool.getConnection();
			autoCommit=conn.getAutoCommit();
			conn.setAutoCommit(false);
			String query=
				"UPDATE "+
					"product "+
				"SET "+
					"name='"+args.getChildren("name").get(0).getValue(0)+"', "+
					"description='"+args.getChildren("desc").get(0).getValue(0)+"', "+
					"price="+args.getChildren("price").get(0).getValue(0)+" "+
					(args.getChildren("catalog").get(0).getValue(0).isEmpty()?"":(",product_catalog="+args.getChildren("catalog").get(0).getValue(0)+" "))+
				"WHERE "+
					"id="+args.getChildren("id").get(0).getValue(0)+
				";";
			conn.createStatement().execute(query);
			if(!args.getChildren("img").get(0).getValue(0).startsWith("http:")) {
				File f=new File(SystemParameters.SERVER_DIRECTORY+"/client/resources/"+args.getChildren("id").get(0).getValue(0)+".jpg");
				if(f.exists())f.delete();
				f.createNewFile();
				fos=new FileOutputStream(f);
				String img_base64=args.getChildren("img").get(0).getValue(0);
				fos.write(Base64.getDecoder().decode(img_base64));
			}
			conn.commit();
			return(args);
		}catch(Exception e) {
			ErrorManager.register(productUpdate.class.getName()+".execute: "+e);
			try {
				conn.rollback();
			}catch(Exception ex) {
				ErrorManager.register(productUpdate.class.getName()+".execute.catch: "+ex);
			}
			return(null);
		}finally {
			try {
				conn.setAutoCommit(autoCommit);
				if(fos!=null)fos.close();
			}catch(Exception e) {
				ErrorManager.register(productUpdate.class.getName()+".execute.finally: "+e);
			}
			try {
				if(fos!=null)fos.close();
			}catch(Exception e) {
				ErrorManager.register(productUpdate.class.getName()+".execute.finally: "+e);
			}
			DatabaseConnectionPool.releaseConnection(conn);
		}
	}

}
