package server.action;

import java.lang.reflect.Method;

import server.ErrorManager;
import server.User;
import server.component.Command;
import server.component.Tree;
import server.component.VSpace;
import server.query.getOrderStatuses;
import server.query.getRoles;
import server.query.getUserParameters;
import server.utilities.ProductCatalog;
import server.utilities.Structure;

public class authorizing implements Action{

	public String execute(Structure message, User user) {
		try {
			Structure user_params=new getUserParameters().execute(new Structure(user.NAME));
			if(user_params==null)throw new Exception("данные пользователя не найдены");
			return((String)authorizing.class.getDeclaredMethod(user.ROLE,Structure.class).invoke(this,user_params));
		}catch(Exception e) {
			ErrorManager.register(authorizing.class.getName()+".execute(Structure,User): "+e);
			return(null);
		}
	}
	
	private String admin(Structure userParams) {
		try {
			Command register=new Command()
				.setTitle("Выход")
				.setType(Command.Type.TEXT)
				.setCommand("#exit");
			
			Command cart=new Command()
				.setTitle("Корзина")
				.setType(Command.Type.TEXT)
				.setCommand("#cart");
			
			Command orders=new Command()
					.setTitle("Мои заказы")
					.setType(Command.Type.TEXT)
					.setCommand("view_orders");
			
			Command account=new Command()
					.setTitle("Личный кабинет")
					.setType(Command.Type.TEXT)
					.setCommand("account");
			
			VSpace sp=new VSpace(0.5);
			
			Structure roles=new getRoles().execute(null);
			if(roles==null)throw new Exception("ошибка при получении ролей пользователей");
			roles.addChild(new Structure("").addChild("Все пользователи"));			
			Tree users=new Tree()
				.setMinWidth(10)
				.setMaxWidth(20)
				.setMinHeight(0)
				.setMaxHeight(20);
			Tree.Branch root=new Tree.Branch("Пользователи");
			for(Structure role:roles.getChildren())root.addLeaf(new Tree.Leaf(role.getValue(0), new Structure("get_users_by_role").addChild(role.getValue())));
			users.addBranch(root);
			
			VSpace sp1=new VSpace(0.5);
			
			Tree products=ProductCatalog.get()
				.setMinWidth(10)
				.setMaxWidth(30)
				.setMinHeight(0)
				.setMaxHeight(20);
			
			Structure mssg=new Structure("")
				.addChild("clear_menuarea")
				.addChild(
					new Structure("add_menuarea")
						.addChild(register.getComponent())
						.addChild(cart.getComponent())
						.addChild(orders.getComponent())
						.addChild(account.getComponent())
						.addChild(sp.getComponent())
						.addChild(users.getComponent())
						.addChild(sp1.getComponent())
						.addChild(products.getComponent())
				)
				.addChild(
					new Structure("set_properties")
						.addChild(new Structure("first").addChild(userParams.getValue(0)))
						.addChild(new Structure("second").addChild(userParams.getValue(1)))
						.addChild(new Structure("third").addChild(userParams.getValue(2)))
						.addChild(new Structure("address").addChild(userParams.getValue(3)))
						.addChild(new Structure("phone").addChild(userParams.getValue(4)))
						.addChild(new Structure("email").addChild(userParams.getValue(5)))
				)
			;
			return(mssg.toString());
		}catch(Exception e) {
			ErrorManager.register(authorizing.class.getName()+".admin(): "+e);
			return(null);
		}
	}
	
	private String employee(Structure userParams) {
		try {
			Command register=new Command()
				.setTitle("Выход")
				.setType(Command.Type.TEXT)
				.setCommand("#exit");
			
			Command cart=new Command()
				.setTitle("Корзина")
				.setType(Command.Type.TEXT)
				.setCommand("#cart");
			
			Command orders=new Command()
				.setTitle("Мои заказы")
				.setType(Command.Type.TEXT)
				.setCommand("view_orders");
			
			Command account=new Command()
				.setTitle("Личный кабинет")
				.setType(Command.Type.TEXT)
				.setCommand("account");
			
			VSpace sp=new VSpace(0.5);
			
			Tree user_orders=new Tree()
				.setMinWidth(10)
				.setMaxWidth(20)
				.setMinHeight(0)
				.setMaxHeight(20);
			Tree.Branch root=new Tree.Branch("Заказы пользователей по статусам");
			user_orders.addBranch(root);
			Structure stats=new getOrderStatuses().execute(null);
			if(stats==null)throw new Exception();
			stats.addChild(new Structure("").addChild("Все заказы"));
			for(Structure stat:stats.getChildren())
				root.addLeaf(new Tree.Leaf(stat.getValue(0)).setCommand(new Structure("get_orders_by_status").addChild(stat.getValue()).addChild(stat.getValue(0))));
			
			Tree prods=new Tree()
				.setMinWidth(10)
				.setMaxWidth(20)
				.setMinHeight(0)
				.setMaxHeight(20)
				.addBranch(
					new Tree.Branch("Товары и каталог")
						.addLeaf(new Tree.Leaf("Добавить товар","add_product"))
						.addLeaf(new Tree.Leaf("Редактировать каталог", "edit_catalog")));
			
			VSpace sp1=new VSpace(0.5);
			VSpace sp2=new VSpace(0.5);
			
			Tree products=ProductCatalog.get()
				.setMinWidth(10)
				.setMaxWidth(30)
				.setMinHeight(0)
				.setMaxHeight(20);
			
			Structure mssg=new Structure("")
				.addChild("clear_menuarea")
				.addChild(
					new Structure("add_menuarea")
						.addChild(register.getComponent())
						.addChild(cart.getComponent())
						.addChild(orders.getComponent())
						.addChild(account.getComponent())
						.addChild(sp.getComponent())
						.addChild(prods.getComponent())
						.addChild(sp1.getComponent())
						.addChild(user_orders.getComponent())
						.addChild(sp2.getComponent())
						.addChild(products.getComponent())
				)
				.addChild(
					new Structure("set_properties")
						.addChild(new Structure("first").addChild(userParams.getValue(0)))
						.addChild(new Structure("second").addChild(userParams.getValue(1)))
						.addChild(new Structure("third").addChild(userParams.getValue(2)))
						.addChild(new Structure("address").addChild(userParams.getValue(3)))
						.addChild(new Structure("phone").addChild(userParams.getValue(4)))
						.addChild(new Structure("email").addChild(userParams.getValue(5)))
				)
			;
			return(mssg.toString());
		}catch(Exception e) {
			ErrorManager.register(authorizing.class.getName()+".employee(): "+e);
			return(null);
		}
	}
	
	private String customer(Structure userParams) {
		try {
			Command register=new Command()
				.setTitle("Выход")
				.setType(Command.Type.TEXT)
				.setCommand("#exit");
			
			Command cart=new Command()
				.setTitle("Корзина")
				.setType(Command.Type.TEXT)
				.setCommand("#cart");
			
			Command orders=new Command()
					.setTitle("Мои заказы")
					.setType(Command.Type.TEXT)
					.setCommand("view_orders");
			
			Command account=new Command()
					.setTitle("Личный кабинет")
					.setType(Command.Type.TEXT)
					.setCommand("account");
			
			VSpace sp=new VSpace(0.5);
			
			Tree products=ProductCatalog.get()
				.setMinWidth(10)
				.setMaxWidth(30)
				.setMinHeight(0)
				.setMaxHeight(20);
			
			Structure mssg=new Structure("")
				.addChild("clear_menuarea")
				.addChild(
					new Structure("add_menuarea")
						.addChild(register.getComponent())
						.addChild(cart.getComponent())
						.addChild(orders.getComponent())
						.addChild(account.getComponent())
						.addChild(sp.getComponent())
						.addChild(products.getComponent())
				)
				.addChild(
					new Structure("set_properties")
						.addChild(new Structure("first").addChild(userParams.getValue(0)))
						.addChild(new Structure("second").addChild(userParams.getValue(1)))
						.addChild(new Structure("third").addChild(userParams.getValue(2)))
						.addChild(new Structure("address").addChild(userParams.getValue(3)))
						.addChild(new Structure("phone").addChild(userParams.getValue(4)))
						.addChild(new Structure("email").addChild(userParams.getValue(5)))
				)
			;
			return(mssg.toString());
		}catch(Exception e) {
			ErrorManager.register(authorizing.class.getName()+".customer(): "+e);
			return(null);
		}
	}
}
 