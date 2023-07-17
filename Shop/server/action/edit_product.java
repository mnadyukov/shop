package server.action;

import java.lang.reflect.Method;

import server.ErrorManager;
import server.SystemParameters;
import server.User;
import server.component.Catalog;
import server.component.Form;
import server.component.Image;
import server.component.Label;
import server.component.Layout;
import server.component.Logic;
import server.component.Parameter;
import server.component.Text;
import server.component.TextString;
import server.query.getProduct;
import server.query.productUpdate;
import server.utilities.Structure;
import server.utilities.TextPattern;

public class edit_product implements Action {

	public String execute(Structure message, User user) {
		try {
			if(message.getChildren("phase").size()==1) {
				Method m=edit_product.class.getDeclaredMethod(message.getChildren("phase").get(0).getValue(0),Structure.class,User.class);
				return((String)m.invoke(this,message,user));
			}
			Structure prod_info=new getProduct().execute(message);
			if(prod_info==null)throw new Exception("ошибка получения данных товара");
			Parameter product=new Parameter("id",message.getValue(0));
			Parameter phase=new Parameter("phase","update");
			Image img=new Image("img")
				.setMode(Image.Mode.EDIT)
				.setSize(15)
				.setSrc("resources/"+message.getValue(0)+".jpg");
			Text name=new Text("name")
				.setTitle("Наименование")
				.setMode(Text.Mode.EDIT)
				.setGrow(0)
				.setMinWidth(15)
				.setMaxWidth(30)
				.setMinHeight(3)
				.setMaxHeight(10)
				.setValue(prod_info.getValue(1))
				.setRegexp(TextPattern.TITLE);
			Text desc=new Text("desc")
				.setTitle("Описание")
				.setMode(Text.Mode.EDIT)
				.setGrow(0)
				.setMinWidth(15)
				.setMaxWidth(30)
				.setMinHeight(3)
				.setMaxHeight(10)
				.setValue(prod_info.getValue(2))
				.setRegexp(TextPattern.DESCRIPTION);
			TextString price=new TextString("price")
				.setTitle("Цена")
				.setMode(TextString.Mode.EDIT)
				.setGrow(0)
				.setHidden(Logic.NO)
				.setMinWidth(10)
				.setMaxWidth(20)
				.setValue(prod_info.getValue(3))
				.setRegexp(TextPattern.PRICE);
			Catalog catalog=new Catalog("catalog")
				.setTitle("Каталог продукции")
				.setMode(Catalog.Mode.SELECT)
				.setMaxWidth(30)
				.setMaxHeight(20)
				.setHandler("edit_catalog");
			Form form=new Form()
				.setTitle("Редактирование товара")
				.setLayout(Layout.VERTICAL)
				.setBorderWidth(0)
				.setMinWidth(20)
				.setMaxWidth(30)
				.setMinHeight(15)
				.setMaxHeight(30)
				.addCommand("edit_product", "Сохранить изменения")
				.addComponent(product)
				.addComponent(phase)
				.addComponent(img)
				.addComponent(name)
				.addComponent(desc)
				.addComponent(price)
				.addComponent(catalog);
			return(new Structure("").addChild(new Structure("set_workarea").addChild(form.getComponent())).toString());
		}catch(Exception e) {
			ErrorManager.register(edit_product.class.getName()+".execute: "+e);
			return(null);
		}
	}
	
	private String update(Structure message, User user) {
		try {
			Structure res=new productUpdate().execute(message);
			if(res==null)throw new Exception("ошибка при обновлении товара");
			Label label=new Label()
				.setText("Изменения успешно сохранены");
			return(new Structure("").addChild(new Structure("set_workarea").addChild(label.getComponent())).toString());
		}catch(Exception e) {
			ErrorManager.register(edit_product.class.getName()+".update: "+e);
			return(null);
		}
	}

}
