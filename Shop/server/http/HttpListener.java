package server.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import server.ErrorManager;
import server.RequestHandler;
import server.SystemParameters;

public class HttpListener {

	private static ExecutorService pool=Executors.newCachedThreadPool();
	
	public static void listen() {
		ServerSocket SS=null;
		try {
			SS=new ServerSocket(SystemParameters.SERVER_PORT);
			Socket S;
			while(true) {
				S=SS.accept();
				pool.execute(new RequestHandler(S));
			}
		}catch(Exception e) {
			ErrorManager.register(HttpListener.class.getName()+".listen(): "+e);
		}finally {
			try {
				SS.close();
			} catch (IOException e) {
				ErrorManager.register(HttpListener.class.getName()+".listen().finally: "+e);
			}
		}
	}
	
}
