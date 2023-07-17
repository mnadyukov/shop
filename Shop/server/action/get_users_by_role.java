package server.action;

import server.ErrorManager;
import server.User;
import server.component.Command;
import server.component.Label;
import server.query.getUsersByRole;
import server.utilities.Structure;

public class get_users_by_role implements Action{

	public String execute(Structure message, User user) {
		try {
			Structure users=new getUsersByRole().execute(message);
			if(users==null)throw new Exception("ошибка при получении данных пользователя");
			Structure res=new Structure("").addChild("set_workarea");
			if(users.getChildren().size()==0) {
				res.getChild(0).addChild(new Label().setText("Пользователи не найдены").getComponent());
			}else {
				Command person;
				for(Structure usr:users.getChildren()) {
					person=new Command()
						.setTitle(usr.getValue()+". "+usr.getValue(1)+" "+usr.getValue(0)+" "+usr.getValue(2)+": "+usr.getValue(3))
						.setType(Command.Type.TEXT)
						.setCommand(new Structure("view_user").addChild(usr.getValue()));
					res.getChild(0).addChild(person.getComponent());
				}
			}
			return(res.toString());
		}catch(Exception e) {
			ErrorManager.register(get_users_by_role.class.getName()+".execute(Structure,User): "+e);
			return(null);
		}
	}

	
	
}
