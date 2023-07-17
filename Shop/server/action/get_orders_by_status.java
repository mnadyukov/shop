package server.action;

import server.ErrorManager;
import server.User;
import server.component.Label;
import server.component.Layout;
import server.component.Panel;
import server.component.Tree;
import server.query.getOrdersByStatus;
import server.utilities.Structure;

public class get_orders_by_status implements Action {

	public String execute(Structure message, User user) {
		try {
			Structure orders=new getOrdersByStatus().execute(message);
			if(orders==null)throw new Exception("ошибка получения списка заказов по статусу");
			Panel panel=new Panel()
				.setTitle("Заказы со статусом: "+message.getValue(1))
				.setLayout(Layout.VERTICAL);
			if(orders.getChildren().size()==0) {
				Label no_orders=new Label().setText("Заказы не найдены");
				panel.addComponent(no_orders);
			}else {
				Tree order_list=new Tree();
				panel.addComponent(order_list);
				for(Structure order:orders.getChildren()) {
					order_list.addLeaf(new Tree.Leaf()
						.setTitle(
							"Заказ №"+order.getValue(0)+
							" от "+order.getValue(1)+
							" на сумму "+order.getValue(2)+"руб"
						)
						.setCommand(new Structure("view_order").addChild(order.getValue(0)))
					);	
				}
			}
			Structure workarea=new Structure("set_workarea").addChild(panel.getComponent());
			return(new Structure("").addChild(workarea).toString());
		}catch(Exception e) {
			ErrorManager.register(view_orders.class.getName()+".execute(Structure,User): "+e);
			return(null);
		}
	}

}
