package server.action;

import server.ErrorManager;
import server.User;
import server.component.Page;
import server.component.ProductShortCard;
import server.query.getProductCount;
import server.utilities.Structure;

public class view_catalog_products implements Action{

	public String execute(Structure message, User user) {
System.out.println(""+message);
		try {
			if(message.getChildren("params").size()==0) {
				Structure count=new getProductCount().execute(new Structure(message.getValue(0)));
				if(count==null)throw new Exception("ошибка при определении количества таваров группы каталога");
				Page page=new Page()
					.setXCount(4)
					.setYCount(2)
					.setTotalCount(Integer.parseInt(count.getValue()))
					.setCommand("view_catalog_products")
					.addParameter("catalog", message.getValue(0));
				return(new Structure("").addChild(new Structure("set_workarea").addChild(page.getComponent())).toString());
			}else {
				Structure args=new Structure(message.getChildren("params").get(0).getChildren("catalog").get(0).getValue(0))
					.addChild(message.getValue(0))
					.addChild(message.getValue(1));
				Structure products=new server.query.getProductsByCatalog().execute(args);
				if(products==null)throw new Exception("ошибка получения данных");
				Structure mssg=new Structure("");
				for(Structure prod:products.getChildren()) {
					mssg.addChild(
						new ProductShortCard()
							.setId(Long.parseLong(prod.getValue(0)))
							.setName(prod.getValue(1))
							.setPrice(Double.parseDouble(prod.getValue(2)))
							.getComponent()
					);
				}
				return(mssg.toString());
			}
		}catch(Exception e) {
			ErrorManager.register(view_catalog_products.class.getName()+".execute(Structure,User): "+e);
			return(null);
		}
	}
	
}
