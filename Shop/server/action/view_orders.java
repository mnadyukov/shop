package server.action;

import server.ErrorManager;
import server.User;
import server.component.Label;
import server.component.Tree;
import server.query.getOrders;
import server.utilities.Structure;

public class view_orders implements Action{


	public String execute(Structure message, User user) {
		try {
			Structure orders=new getOrders().execute(new Structure(user.NAME==null?"0":""+user.ID));
			if(orders==null)throw new Exception("ошибка получения списка заказов пользователя");
			if(orders.getChildren().size()==0) {
				Label no_orders=new Label().setText("Заказы не найдены");
				return(new Structure("")
					.addChild(new Structure("set_workarea").addChild(no_orders.getComponent()))
					.toString()
				);
			}else {
				Structure workarea=new Structure("set_workarea");
				Tree order_list=new Tree();
				for(Structure order:orders.getChildren()) {
					order_list.addLeaf(new Tree.Leaf()
						.setTitle(
							"Заказ №"+order.getValue(0)+
							" от "+order.getValue(1)+
							" на сумму "+order.getValue(4)+"руб"+
							" ("+order.getValue(3)+")"
						)
						.setCommand(new Structure("view_order").addChild(order.getValue(0)))
					);	
				}
				workarea.addChild(order_list.getComponent());
				return(new Structure("").addChild(workarea).toString());
			}
		}catch(Exception e) {
			ErrorManager.register(view_orders.class.getName()+".execute(Structure,User): "+e);
			return(null);
		}
	}

}
