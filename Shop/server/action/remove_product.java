package server.action;

import server.ErrorManager;
import server.User;
import server.component.Label;
import server.query.productRemove;
import server.utilities.Structure;

public class remove_product implements Action {

	public String execute(Structure message, User user) {
		try {
			Structure res=new productRemove().execute(message);
			if(res==null)throw new Exception("ошибка при удалении товара");
			Label label=new Label()
				.setText("Товар успешно удален");
			return(new Structure("").addChild(new Structure("set_workarea").addChild(label.getComponent())).toString());
		}catch(Exception e) {
			ErrorManager.register(remove_product.class.getName()+".execute: "+e);
			return(null);
		}
	}

}
