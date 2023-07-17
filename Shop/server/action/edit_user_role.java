package server.action;

import server.ErrorManager;
import server.User;
import server.component.Label;
import server.query.editUserRole;
import server.utilities.Structure;

public class edit_user_role implements Action{

	public String execute(Structure message, User user) {
		try {
			Structure res=new editUserRole().execute(message);
			if(res==null)throw new Exception("ошибка при изменении роли пользователя");
			Label label=new Label();
			if(res.getValue().equals("NO")) {
				label.setText(
					"В системе существует только один системный администратор. "+
					"Изменение роли приведет к тому, что в системе не останется "+
					"ни одного системного администратора. Изменение не выполнено."
				);		
			}else {
				label.setText(
					"Изменение роли успешно выполнено."
				);	
			}
			return(new Structure("").addChild(new Structure("set_workarea").addChild(label.getComponent())).toString());
		}catch(Exception e) {
			ErrorManager.register(edit_user_role.class.getName()+".execute(Structure,User): "+e);
			return(null);
		}
	}
	
	

}
