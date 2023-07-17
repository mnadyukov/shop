package server.action;

import server.ErrorManager;
import server.User;
import server.query.getCatalogPath;
import server.query.getProduct;
import server.utilities.Structure;

public class view_product_full implements Action{

	public String execute(Structure message, User user) {
		try {
			Structure product=new getProduct().execute(message);
			if(product==null)throw new Exception("ошибка получения данных продукта");
			Structure cat_path=new getCatalogPath().execute(new Structure(product.getValue(4)));			
			Structure view=new Structure("product_view")
				.addChild(product.getValue(0))
				.addChild(product.getValue(1))
				.addChild(product.getValue(2))
				.addChild(product.getValue(3))
				.addChild(product.getValue(6))
				.addChild(cat_path)
				.addChild(user.ROLE.equals("employee")?"1":"0");
			return(new Structure("").addChild(new Structure("set_workarea").addChild(view)).toString());
		}catch(Exception e) {
			ErrorManager.register(view_product_full.class.getName()+".execute(Structure,User): "+e);
			return(null);
		}
	}

}
