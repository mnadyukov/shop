package server.action;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;

import server.ErrorManager;
import server.User;
import server.component.Catalog;
import server.component.Layout;
import server.component.Panel;
import server.query.catalogCreate;
import server.query.catalogGet;
import server.query.catalogGetParent;
import server.query.catalogPaste;
import server.query.catalogRemove;
import server.query.catalogRename;
import server.query.getCatalogPath;
import server.utilities.Structure;

public class edit_catalog implements Action {
	
	public String execute(Structure message, User user) {
		try {
			if(message.getChildren().size()==0) {
				Panel pan=new Panel()
					.setTitle("Редактирование каталога продукции")
					.setLayout(Layout.VERTICAL)
					.setBorderWidth(1);
				Catalog catalog=new Catalog("cat")
					.setTitle("Каталог продукции")
					.setMode(Catalog.Mode.EDIT)
					.setHandler("edit_catalog");
				pan.addComponent(catalog);
				Structure res=new Structure("").addChild(new Structure("set_workarea").addChild(pan.getComponent()));
				return(res.toString());
			}else{
				Method method=this.getClass().getDeclaredMethod(message.getValue(0), Structure.class,User.class);
				if(method==null)throw new Exception("метод каталога "+message.getValue(0)+" не найден");
				return((String)method.invoke(this,message.getChild(0),user));
			}
		}catch(Exception e) {
			ErrorManager.register(edit_catalog.class.getName()+".execute: "+e);
			return(null);
		}
	}

	private String get(Structure message, User user) {
		try {
			Structure content=new catalogGet().execute(message);
			if(content==null)throw new Exception("ошибка получения содержимого группы каталога продукции");
			Structure path=new getCatalogPath().execute(message.getChild(0));
			if(path==null)throw new Exception("ошибка получения пути текущей группы каталога продукции");
			return(
				message.getChild(0)
					.addChild(path)
					.addChild(content)
					.toString()
			);
		}catch(Exception e) {
			ErrorManager.register(edit_catalog.class.getName()+".get: "+e);
			return(null);
		}
	}
	
	private String up(Structure message, User user) {
		try {
			Structure res=new catalogGetParent().execute(message);
			if(res==null)throw new Exception("ошибка получения родительской группы каталога продукции");
			return(get(new Structure("").addChild(res),user));
		}catch(Exception e) {
			ErrorManager.register(edit_catalog.class.getName()+".up: "+e);
			return(null);
		}
	}
	
	private String remove(Structure message, User user) {
		try {
			Structure res=new catalogRemove().execute(message);
			if(res==null)throw new Exception("ошибка удаления группы каталога продукции");
			return(get(message,user));
		}catch(Exception e) {
			ErrorManager.register(edit_catalog.class.getName()+".remove: "+e);
			return(null);
		}
	}
	
	private String paste(Structure message, User user) {
		try {
			Structure res=new catalogPaste().execute(message);
			if(res==null)throw new Exception("ошибка вставки группы каталога продукции");
			return(get(message,user));
		}catch(Exception e) {
			ErrorManager.register(edit_catalog.class.getName()+".paste: "+e);
			return(null);
		}
	}
	
	private String rename(Structure message, User user) {
		try {
			Structure res=new catalogRename().execute(message);
			if(res==null)throw new Exception("ошибка переименования группы каталога продукции");
			return(get(message,user));
		}catch(Exception e) {
			ErrorManager.register(edit_catalog.class.getName()+".rename: "+e);
			return(null);
		}
	}
	
	private String create(Structure message, User user) {
		try {
			Structure res=new catalogCreate().execute(message);
			if(res==null)throw new Exception("ошибка создания группы каталога продукции");
			return(get(message,user));
		}catch(Exception e) {
			ErrorManager.register(edit_catalog.class.getName()+".create: "+e);
			return(null);
		}
	}
	
}

