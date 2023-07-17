package server.action;

import server.User;
import server.component.Application;
import server.component.Command;
import server.component.Tree;
import server.component.VSpace;
import server.utilities.ProductCatalog;
import server.utilities.Structure;

public class application implements Action{

	public String execute(Structure message, User user) {
		
		String logo=
			"<div "+
				"style='display:flex;"+
				"flex-direction:column;"+
				"flex-grow:1;"+
				"justify-content:center;"+
				"align-items:center;"+
				"color:white;"+
				"font-family:sans-serif;"+
				"background-color:RGB(48,48,128);"+
			"'>"+
				"<div style='font-size:2em;margin:0.25em;box-sizing:border-box;'>Камера-обскура</div>"+
				"<div style='font-weight:bold;margin:0.25em;box-sizing:border-box;'>магазин товаров для фотохудожников</div>"+
			"</div>";
		
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
		
		VSpace sp=new VSpace().setSize(0.5);
				
		Tree products=ProductCatalog.get()
			.setMinWidth(10)
			.setMaxWidth(30)
			.setMinHeight(0)
			.setMaxHeight(20);
		
		Application app=new Application()
			.setTitle("Камера-обскура")
			.setLogo(logo)
			.addToMenuArea(enter)
			.addToMenuArea(registration)	
			.addToMenuArea(cart)
			.addToMenuArea(sp)
			.addToMenuArea(products);
		return(new Structure("").addChild(app.getComponent()).toString());
	}

	
	
}
