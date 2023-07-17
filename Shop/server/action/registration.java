package server.action;

import server.ErrorManager;
import server.User;
import server.utilities.Structure;
import server.utilities.TextPattern;
import server.component.Form;
import server.component.Label;
import server.component.Layout;
import server.component.Logic;
import server.component.Text;
import server.component.TextString;
import server.query.saveUser;
import server.component.Parameter;
import server.query.checkLogin;

public class registration implements Action{

	public String execute(Structure message, User user) {
		try {
			if(message.getChildren().size()==0)return(getForm(null,null));
			String method=message.getChildren("phase").get(0).getValue(0);
			return((String)registration.class.getDeclaredMethod(method, Structure.class, User.class).invoke(this,message,user));
		}catch(Exception e) {
			ErrorManager.register(registration.class.getName()+".execute(Structure,User): "+e);
			return(null);
		}
	}
	
	private String getForm(Structure message, User user) {
		try {
			TextString login=new TextString("login")
				.setTitle("Логин")
				.setMode(TextString.Mode.INPUT)
				.setGrow(0)
				.setHidden(Logic.NO)
				.setMinWidth(10)
				.setMaxWidth(30)
				.setValue("")
				.setRegexp(TextPattern.LOGIN);	
			TextString password=new TextString("password")
				.setTitle("Пароль")
				.setMode(TextString.Mode.INPUT)
				.setGrow(0)
				.setHidden(Logic.YES)
				.setMinWidth(10)
				.setMaxWidth(30)
				.setValue("")
				.setRegexp(TextPattern.PASSWORD);
			TextString secondName=new TextString("second_name")
				.setTitle("Фамилия")
				.setMode(TextString.Mode.INPUT)
				.setGrow(0)
				.setHidden(Logic.NO)
				.setMinWidth(10)
				.setMaxWidth(30)
				.setValue("")
				.setRegexp(TextPattern.SECOND_NAME);
			TextString firstName=new TextString("first_name")
				.setTitle("Имя")
				.setMode(TextString.Mode.INPUT)
				.setGrow(0)
				.setHidden(Logic.NO)
				.setMinWidth(10)
				.setMaxWidth(30)
				.setValue("")
				.setRegexp(TextPattern.FIRST_NAME);
			TextString patronymic=new TextString("patronymic")
				.setTitle("Отчество")
				.setMode(TextString.Mode.INPUT)
				.setGrow(0)
				.setHidden(Logic.NO)
				.setMinWidth(10)
				.setMaxWidth(30)
				.setValue("")
				.setRegexp(TextPattern.THIRD_NAME);		
			Text address=new Text("address")
				.setTitle("Адрес")
				.setMode(Text.Mode.INPUT)
				.setGrow(0)
				.setMinWidth(10)
				.setMaxWidth(20)
				.setMinHeight(5)
				.setMaxHeight(15)
				.setValue("")
				.setRegexp(TextPattern.ADDRESS);
			TextString phone=new TextString("phone")
				.setTitle("Телефон")
				.setMode(TextString.Mode.INPUT)
				.setGrow(0)
				.setHidden(Logic.NO)
				.setMinWidth(10)
				.setMaxWidth(30)
				.setValue("")
				.setRegexp(TextPattern.PHONE);
			TextString email=new TextString("email")
				.setTitle("Электронная почта")
				.setMode(TextString.Mode.INPUT)
				.setGrow(0)
				.setHidden(Logic.NO)
				.setMinWidth(10)
				.setMaxWidth(30)
				.setValue("")
				.setRegexp(TextPattern.EMAIL);
				
			Parameter phase=new Parameter("phase","save");
				
			Form register=new Form()
				.setTitle("Регистрация")
				.setLayout(Layout.VERTICAL)
				.setMinWidth(20)
				.setMaxWidth(30)
				.setMinHeight(0)
				.setMaxHeight(40)
				.addComponent(phase)
				.addComponent(login)
				.addComponent(password)
				.addComponent(secondName)
				.addComponent(firstName)
				.addComponent(patronymic)
				.addComponent(address)
				.addComponent(phone)
				.addComponent(email)
				.addCommand("registration", "Зарегистрироваться");
				
			Structure mssg=new Structure("")
				.addChild(
					new Structure("set_workarea")
						.addChild(register.getComponent())
				);
				
			return(mssg.toString());
		}catch(Exception e) {
			ErrorManager.register(registration.class.getName()+".getForm(Structure,User): "+e);
			return(null);
		}
	}
	
	private String save(Structure message, User user) {
		Label res=new Label();
		Structure mssg=new Structure("")
			.addChild(
				new Structure("set_workarea")
					.addChild(res.getComponent())
			);
		if(checkLogin(message.getChildren("login").get(0).getValue(0))) {
			if(new saveUser().execute(message).getValue().equals("NO")) {
				return(null);
			}else {
				res.setText("Вы успешно зарегистрированы");
				return(mssg.toString());
			}
		}else {
			res.setText("Пользователь с таким логином уже существует");
			return(mssg.toString());
		}
	}
	
	private boolean checkLogin(String login) {
		try {
			Structure str=new checkLogin().execute(new Structure(login));
			return(str.getValue().equals("NO"));
		}catch(Exception e) {
			ErrorManager.register(registration.class.getName()+".checkLogin(String): "+e);
			return(false);
		}
	}
	
}
