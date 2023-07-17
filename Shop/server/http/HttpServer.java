package server.http;

import java.net.Socket;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import server.Authorizer;
import server.ErrorManager;
import server.SystemParameters;
import server.User;
import server.crypto.Cryptographer;
import server.crypto.Empty;
import server.utilities.Converter;

public class HttpServer {
	
	private static HashMap<String,String> MIME;
	private HashMap<String,String> headers;
	private Socket S;
	private Cryptographer crypto;
	
	static {
		MIME=new HashMap<String,String>();
		MIME.put("html", "text/html; charset=utf-8");
		MIME.put("js", "application/javascript; charset=utf-8");
		MIME.put("css", "text/css; charset=utf-8");
		MIME.put("txt", "text/plain; charset=utf-8");
		MIME.put("jpg", "image/jpeg");
		MIME.put("png", "image/png");
		MIME.put("bmp", "image/bmp");
		MIME.put("ico", "image/vnd.microsoft.icon");
	}
	
	public HttpServer(Socket s) {
		S=s;
		headers=new HashMap<String,String>();
		try {
			crypto=(Cryptographer)Class.forName(Cryptographer.class.getPackageName()+"."+SystemParameters.CRYPTOGRAPHER).getConstructor().newInstance();
		}catch(Exception e) {
			ErrorManager.register(HttpServer.class.getName()+"(Socket): "+e);
			crypto=new Empty();
		}
	}
	
	public String getMessage(User user) {
		try {
			BufferedReader br=new BufferedReader(new InputStreamReader(S.getInputStream(),"UTF-8"));
			String line,method,file;
			line=br.readLine();
			if(line==null)return(null);
			method=line.split(" ")[0];
			file=line.split(" ")[1].substring(1);
			int pos;
			while(true) {
				line=br.readLine();
				if(line.isEmpty())break;
				pos=line.indexOf(":");
				if(pos==-1)throw new Exception("некорректный заголовок запроса: "+line);
				headers.put(line.substring(0,pos),line.substring(pos+2));
			}
			if(method.equals("GET")) {
				GET(file);
				return("");
			}
			if(method.equals("POST")) {
				line=br.readLine();			
				return(POST(line,user));
			}
			throw new Exception("неизвестный метод запроса: "+method);
		}catch(Exception e) {
			ErrorManager.register(HttpServer.class.getName()+".getMessage(): "+e);
			return(null);
		}
	}
	
	private void GET(String file) {
		FileInputStream fis=null;
		try {
			OutputStream os=S.getOutputStream();
			String ext=file.replaceAll(".*\\.","");
			String mime=MIME.get(ext);
			String head;
			if(mime==null) {
				head=
					"HTTP/1.1 404 Not found\n"+
					"\n"
				;
				os.write(head.getBytes("UTF-8"));
				return;
			}
			fis=new FileInputStream(SystemParameters.SERVER_DIRECTORY+"/client/"+file);
			byte[] b=fis.readAllBytes();
			head=
				"HTTP/1.1 200 OK\n"+
				"Content-Type: "+mime+"\n"+
				"Content-Length: "+b.length+"\n"+
				"\n"
			;
			os.write(head.getBytes("UTF-8"));
			os.write(b);
		}catch(Exception e) {
			ErrorManager.register(HttpServer.class.getName()+".GET(String): "+e);
		}finally {
			try {
				if(fis!=null)fis.close();
			}catch(IOException ioe) {
				ErrorManager.register(HttpServer.class.getName()+".GET(String).finally: "+ioe);
			}
		}
	}
	
	/**
	 * <user_name>0<encoded_hex>
	 * <decoded_hex>=<user_name>0XX<message>
	 * XX - hex-длина хвоста сообщения (хвост должен быть отброшен)
	 * @param request Полученный пакет.
	 * Пакет содержит закодированное сообщение и служебную информацию.
	 * @param user Объект данных пользователя.
	 * Если авторизация не пройдена, то все поля объекта user равны null.
	 * @return Полученное сообщение.
	 */
	private String POST(String request, User user) {
		try {
			if(headers.get("Crypto").equals("false"))return(request);
			int pos=request.indexOf("\u0000");
			user.NAME=request.substring(0,pos);
			byte[] b=Converter.hexToByte(request.substring(pos+1));
			Authorizer.authorize(user);
			if(user.PASSWORD==null)throw new Exception("пользователь не найден: "+user.NAME);
			b=crypto.decode(b,user.PASSWORD);
			if(b==null)throw new Exception("ошибка при раскодировании сообщения");
			String[] message=new String(b,"UTF-8").split("\u0000");
			if(message.length!=2 || !message[0].equals(user.NAME)) {
				user.PASSWORD=null;
				return("\u0000");
			}
			return(message[1]);
		}catch(Exception e) {
			ErrorManager.register(HttpServer.class.getName()+".POST(String): "+e);
			user.NAME=null;
			user.PASSWORD=null;
			user.ROLE=null;
			return(null);
		}
	}
	
	public void sendMessage(String message, User user) {
		try {
			String pckg=null;
			if(user.PASSWORD==null) {
				headers.put("Crypto","false");
				pckg=message;
			}else {
				pckg=Converter.byteToHex(
					crypto.encode(
						(user.NAME+"\u0000"+message).getBytes("UTF-8"),
						user.PASSWORD)
				);
			}
			byte[] _pckg=pckg.getBytes("UTF-8");
			String head=
				"HTTP/1.1 200 OK\n"+
				"Content-Type: "+MIME.get("txt")+"\n"+
				"Content-Length: "+_pckg.length+"\n"+
				"Crypto: "+headers.get("Crypto")+"\n"+
				"\n"
			;
			OutputStream os=S.getOutputStream();
			os.write(head.getBytes("UTF-8"));
			os.write(_pckg);
		}catch(Exception e) {
			ErrorManager.register(HttpServer.class.getName()+".sendMessage(String): "+e);
		}
	}
	
	public void close() {
		try {
			S.close();
		}catch(Exception e) {
			ErrorManager.register(HttpServer.class.getName()+".close(): "+e);
		}
	}
	
}
