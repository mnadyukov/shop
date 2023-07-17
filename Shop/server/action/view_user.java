package server.action;

import server.ErrorManager;
import server.User;
import server.query.getRoles;
import server.query.getUser;
import server.utilities.Structure;
import server.component.Form;
import server.component.Label;
import server.component.Layout;
import server.component.List;
import server.component.Parameter;
import server.component.VSpace;

public class view_user implements Action{

	public String execute(Structure message, User user) {
		try {
			Structure params=new getUser().execute(new Structure(message.getValue(0)));
			if(params==null)throw new Exception("пользователь не найден");
			Structure roles=new getRoles().execute(null);
			if(roles==null)throw new Exception("ошибка при получении ролей пользователей");
			Form form=new Form()
				.setTitle("")
				.setLayout(Layout.VERTICAL)
				.setBorderWidth(0)
				.setMinWidth(10)
				.setMaxWidth(30)
				.setMinHeight(10)
				.setMaxHeight(30)
				.addCommand("edit_user_role", "Изменить роль");
			Parameter param=new Parameter("person",message.getValue(0));
			Label second=new Label().setText("<b>Фамилия:</b> "+params.getValue(2));
			Label first=new Label().setText("<b>Имя:</b> "+params.getValue(1));
			Label third=new Label().setText("<b>Отчество:</b> "+params.getValue(3));
			Label address=new Label().setText("<b>Адрес:</b> "+params.getValue(4));
			Label phone=new Label().setText("<b>Телефон:</b> "+params.getValue(5));
			Label email=new Label().setText("<b>Электронная почта:</b> "+params.getValue(6));
			VSpace sp=new VSpace().setSize(1.0f);
			List role=new List("role")
				.setTitle("Роль пользователя")
				.setGrow(0)
				.setMinWidth(10)
				.setMaxWidth(20)
				.setValue(params.getValue(0));
			for(Structure r:roles.getChildren())
				role.addOption(new List.Option(r.getValue(),r.getValue(0)));
			form
				.addComponent(second)
				.addComponent(first)
				.addComponent(third)
				.addComponent(address)
				.addComponent(phone)
				.addComponent(email)
				.addComponent(sp)
				.addComponent(role)
				.addComponent(param);
			Structure pan=new Structure("").addChild(new Structure("set_workarea").addChild(form.getComponent()));
			return(pan.toString());
		}catch(Exception e) {
			ErrorManager.register(view_user.class.getName()+".execute(Structure,User): "+e);
			return(null);
		}
	}

}
