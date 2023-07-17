package server.action;

import server.User;
import server.component.Form;
import server.component.Layout;
import server.component.Logic;
import server.component.TextString;
import server.utilities.Structure;
import server.utilities.TextPattern;

public class enter implements Action{

	public String execute(Structure message, User user) {
		TextString name=new TextString("name")
			.setTitle("Имя пользователя")
			.setMode(TextString.Mode.INPUT)
			.setGrow(0)
			.setHidden(Logic.NO)
			.setMinWidth(15)
			.setMaxWidth(15)
			.setValue("")
			.setRegexp(TextPattern.LOGIN);
		TextString pass=new TextString("password")
			.setTitle("Пароль")
			.setMode(TextString.Mode.INPUT)
			.setGrow(0)
			.setHidden(Logic.YES)
			.setMinWidth(15)
			.setMaxWidth(15)
			.setValue("")
			.setRegexp(TextPattern.PASSWORD);
		Form form=new Form()
			.setTitle("Вход в магазин")
			.setLayout(Layout.VERTICAL)
			.setBorderWidth(1)
			.setMinWidth(0)
			.setMaxWidth(0)
			.setMinHeight(0)
			.setMaxHeight(0)
			.addCommand("#authorizing", "Войти")
			.addComponent(name)
			.addComponent(pass);
		return(
			new Structure("")
				.addChild(
					new Structure("set_workarea")
						.addChild(form.getComponent())
				)
				.toString()
		);
	}

}
