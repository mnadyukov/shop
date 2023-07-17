package server.action;

import server.ErrorManager;
import server.User;
import server.component.Label;
import server.query.editAccount;
import server.utilities.Structure;

public class edit_account implements Action{

	public String execute(Structure message, User user) {
		try {
			message.setValue(""+user.ID);
			Structure res=new editAccount().execute(message);
			Label label=new Label();
			Structure resp=new Structure("").addChild(new Structure("set_workarea").addChild(label.getComponent()));
			if(res==null) {
				label.setText("Ошибка при изменении данных пользователя");
			}else {
				label.setText("Данные пользователя успешно изменены");
			}
			return(resp.toString());
		}catch(Exception e) {
			ErrorManager.register(edit_account.class.getName()+".execute: "+e);
			return(null);
		}
	}

	
	
}
