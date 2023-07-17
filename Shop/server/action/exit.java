package server.action;

import server.ErrorManager;
import server.User;
import server.component.Command;
import server.component.Tree;
import server.component.VSpace;
import server.utilities.ProductCatalog;
import server.utilities.Structure;

public class exit implements Action{

	public String execute(Structure message, User user) {
		try {
			Command enter=new Command()
					.setTitle("Войти")
					.setType(Command.Type.TEXT)
					.setCommand("enter");
			
			Command registration=new Command()
					.setTitle("Регистрация")
					.setType(Command.Type.TEXT)
					.setCommand("registration");
							
			Command cart=new Command()
					.setTitle("Корзина")
					.setType(Command.Type.TEXT)
					.setCommand("#cart");
			
			VSpace sp=new VSpace(0.5);
					
			Tree products=ProductCatalog.get()
				.setMinWidth(10)
				.setMaxWidth(30)
				.setMinHeight(0)
				.setMaxHeight(20);
			Structure res=new Structure("").addChild("set_menuarea");
			res.getChild(0)
				.addChild(enter.getComponent())
				.addChild(registration.getComponent())
				.addChild(cart.getComponent())
				.addChild(sp.getComponent())
				.addChild(products.getComponent());
			return(res.toString());
		}catch(Exception e) {
			ErrorManager.register(exit.class.getName()+".exit(): "+e);
			return(null);
		}
	}

}
