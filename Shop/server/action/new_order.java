package server.action;

import server.ErrorManager;
import server.User;
import server.component.Label;
import server.query.getUserParameters;
import server.query.setOrder;
import server.utilities.Structure;

public class new_order implements Action{

	public String execute(Structure message, User user) {
		try {
			String note;
			if(user.ID==0) {
				note=
					"ФИО: "+message.getChildren("fio").get(0).getValue(0)+"; "+
					"Адрес доставки: "+message.getChildren("address").get(0).getValue(0)+"; "+
					"Телефон: "+message.getChildren("phone").get(0).getValue(0)+"; "+
					"Электронная почта: "+message.getChildren("email").get(0).getValue(0)+"; "+
					"Комментарии: "+message.getChildren("note").get(0).getValue(0);
			}else {
				note="";
				Structure info=new getUserParameters().execute(new Structure(user.NAME));
				if(info==null)throw new Exception("ошибка получения данных пользователя");
				String fio=
					(info.getValue(1).isEmpty()?"":info.getValue(1))+
					(info.getValue(0).isEmpty()?"":" "+info.getValue(0))+
					(info.getValue(2).isEmpty()?"":" "+info.getValue(2));
				if(
					!fio.toUpperCase().trim()
					.equals(message.getChildren("fio").get(0).getValue(0).toUpperCase().trim())
				) {
					note+="ФИО: "+fio+";";
				}
				if(
					!info.getValue(3).toUpperCase().trim()
					.equals(message.getChildren("address").get(0).getValue(0).toUpperCase().trim())
				) {
					note+=" Адрес доставки: "+message.getChildren("address").get(0).getValue(0)+";";
				}
				if(
					!info.getValue(4).toUpperCase().trim()
					.equals(message.getChildren("phone").get(0).getValue(0).toUpperCase().trim())
				) {
					note+=" Телефон: "+message.getChildren("phone").get(0).getValue(0)+";";
				}
				if(
					!info.getValue(5).toUpperCase().trim()
					.equals(message.getChildren("email").get(0).getValue(0).toUpperCase().trim())
				) {
					note+=" Электронная почта: "+message.getChildren("email").get(0).getValue(0)+";";
				}
				note+=(note.isEmpty()?"":" ")+"Комментарии: "+message.getChildren("note").get(0).getValue(0);
			}
			if(note.length()>1000)note=note.substring(0,1000);
			Structure arg=new Structure(""+user.ID)
				.addChild(message.getChildren("order").get(0))
				.addChild(note);
			Structure order=new setOrder().execute(arg);
			if(order==null)throw new Exception("ошибка записи заказа в базу данных");
			Label OK=new Label().setText("Заказ № "+order.getValue()+" успешно оформлен");
			return(new Structure("")
				.addChild("clear_cart")
				.addChild(new Structure("set_workarea").addChild(OK.getComponent()))
				.toString()
			);
		}catch(Exception e) {
			ErrorManager.register(new_order.class.getName()+".execute: "+e);
			return(null);
		}
	}

}
