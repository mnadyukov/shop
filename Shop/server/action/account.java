package server.action;

import server.ErrorManager;
import server.User;
import server.component.TextString;
import server.component.Form;
import server.component.Label;
import server.component.Layout;
import server.component.Logic;
import server.component.Panel;
import server.component.Text;
import server.query.getRoleName;
import server.query.getUserParameters;
import server.utilities.Structure;
import server.utilities.TextPattern;

public class account implements Action{

	public String execute(Structure message, User user) {
		try {
			
			Structure role_desc=new getRoleName().execute(new Structure(user.ROLE));
			if(role_desc==null)throw new Exception("ошибка получения роли пользователя");
			
			Structure params=new getUserParameters().execute(new Structure(user.NAME));
			if(params==null)throw new Exception("ошибка получения параметров пользователя");
			
			TextString login=new TextString("login")
				.setTitle("Логин")
				.setMode(TextString.Mode.EDIT)
				.setGrow(0)
				.setHidden(Logic.NO)
				.setMinWidth(15)
				.setMaxWidth(15)
				.setRegexp(TextPattern.LOGIN)
				.setValue(user.NAME);
			
			TextString password=new TextString("password")
				.setTitle("Пароль")
				.setMode(TextString.Mode.EDIT)
				.setGrow(0)
				.setHidden(Logic.YES)
				.setMinWidth(15)
				.setMaxWidth(15)
				.setRegexp(TextPattern.PASSWORD)
				.setValue(user.PASSWORD);
			
			Label role=new Label()
				.setText("Роль пользователя: "+role_desc.getValue());
			
			TextString second=new TextString("second_name")
				.setTitle("Фамилия")
				.setMode(TextString.Mode.EDIT)
				.setGrow(0)
				.setHidden(Logic.NO)
				.setMinWidth(15)
				.setMaxWidth(15)
				.setRegexp(TextPattern.SECOND_NAME)
				.setValue(params.getValue(1));
			
			TextString first=new TextString("first_name")
				.setTitle("Имя")
				.setMode(TextString.Mode.EDIT)
				.setGrow(0)
				.setHidden(Logic.NO)
				.setMinWidth(15)
				.setMaxWidth(15)
				.setRegexp(TextPattern.FIRST_NAME)
				.setValue(params.getValue(0));
			
			TextString third=new TextString("patronymic")
				.setTitle("Отчество")
				.setMode(TextString.Mode.EDIT)
				.setGrow(0)
				.setHidden(Logic.NO)
				.setMinWidth(15)
				.setMaxWidth(15)
				.setRegexp(TextPattern.THIRD_NAME)
				.setValue(params.getValue(2));
			
			Text address=new Text("address")
				.setTitle("Адрес")
				.setMode(Text.Mode.EDIT)
				.setGrow(0)
				.setMinWidth(15)
				.setMaxWidth(15)
				.setMinHeight(5)
				.setMaxHeight(5)
				.setRegexp(TextPattern.ADDRESS)
				.setValue(params.getValue(3));
			
			TextString phone=new TextString("phone")
				.setTitle("Телефон")
				.setMode(TextString.Mode.EDIT)
				.setGrow(0)
				.setHidden(Logic.NO)
				.setMinWidth(15)
				.setMaxWidth(15)
				.setRegexp(TextPattern.PHONE)
				.setValue(params.getValue(4));
			
			TextString email=new TextString("email")
				.setTitle("Электронная почта")
				.setMode(TextString.Mode.EDIT)
				.setGrow(0)
				.setHidden(Logic.NO)
				.setMinWidth(15)
				.setMaxWidth(15)
				.setRegexp(TextPattern.EMAIL)
				.setValue(params.getValue(5));
			
			Form pan=new Form()
				.setTitle("Личный кабинет")
				.setLayout(Layout.VERTICAL)
				.setBorderWidth(0)
				.setMinWidth(0)
				.setMaxWidth(40)
				.setMinHeight(0)
				.setMaxHeight(0)
				.addCommand("edit_account", "Изменить")
				.addComponent(login)
				.addComponent(password)
				.addComponent(role)
				.addComponent(second)
				.addComponent(first)
				.addComponent(third)
				.addComponent(address)
				.addComponent(phone)
				.addComponent(email);
			
			return(new Structure("").addChild(new Structure("set_workarea").addChild(pan.getComponent())).toString());
		}catch(Exception e) {
			ErrorManager.register(account.class.getName()+".execute: "+e);
			return(null);
		}
	}

	

}
