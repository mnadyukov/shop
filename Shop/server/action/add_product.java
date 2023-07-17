package server.action;

import server.ErrorManager;
import server.User;
import server.utilities.Structure;
import server.utilities.TextPattern;
import server.component.Catalog;
import server.component.Form;
import server.component.Image;
import server.component.Label;
import server.component.Layout;
import server.component.Logic;
import server.component.Parameter;
import server.component.Text;
import server.component.TextString;
import server.query.productSave;

public class add_product implements Action{

	public String execute(Structure message, User user) {
		try {
			String phase="fill";
			if(message.getChildren("phase").size()==1)phase=message.getChildren("phase").get(0).getValue(0);
System.out.println(phase);
			return((String)add_product.class.getDeclaredMethod(phase,Structure.class,User.class).invoke(this, message,user));
		}catch(Exception e) {
			ErrorManager.register(add_product.class.getName()+".execute(Structure,User): "+e);
			return(null);
		}
	}
	
	private String fill(Structure message,User user) {
		try {
			Parameter phase=new Parameter("phase","save");
			Image img=new Image("img")
				.setMode(Image.Mode.EDIT)
				.setSize(15);
			Text name=new Text("name")
				.setTitle("Наименование")
				.setMode(Text.Mode.INPUT)
				.setGrow(0)
				.setMinWidth(10)
				.setMaxWidth(30)
				.setMinHeight(3)
				.setMaxHeight(15)
				.setValue("")
				.setRegexp("^.{1,}$");
			Text description=new Text("desc")
				.setTitle("Описание")
				.setMode(Text.Mode.INPUT)
				.setGrow(0)
				.setMinWidth(10)
				.setMaxWidth(30)
				.setMinHeight(5)
				.setMaxHeight(15)
				.setValue("")
				.setRegexp("^.{10,}$");
			TextString price=new TextString("price")
				.setTitle("Цена")
				.setMode(TextString.Mode.INPUT)
				.setGrow(0)
				.setHidden(Logic.NO)
				.setMinWidth(10)
				.setMaxWidth(20)
				.setRegexp(TextPattern.PRICE);
			Catalog catalog=new Catalog("catalog")
				.setTitle("Каталог продукции")
				.setMode(Catalog.Mode.SELECT)
				.setMaxWidth(30)
				.setMaxHeight(20)
				.setHandler("edit_catalog");
			Form form=new Form()
				.setTitle("Добавление нового товара")
				.setLayout(Layout.VERTICAL)
				.setMinWidth(40)
				.setMaxWidth(0)
				.setMinHeight(0)
				.setMaxHeight(30)
				.addCommand("add_product", "Добавить")
				.addComponent(phase)
				.addComponent(img)
				.addComponent(name)
				.addComponent(description)
				.addComponent(price)
				.addComponent(catalog);
			return(new Structure("").addChild(new Structure("set_workarea").addChild(form.getComponent())).toString());
		}catch(Exception e) {
			ErrorManager.register(add_product.class.getName()+".fill_product: "+e);
			return(null);
		}
	}
	
	private String save(Structure message, User user) {
		try {
			Structure res=new productSave().execute(message);
			if(res==null)throw new Exception("ошибка сохранения нового товара");
			Label label=new Label()
				.setText("Товар успешно сохранен");
			return(new Structure("").addChild(new Structure("set_workarea").addChild(label.getComponent())).toString());
		}catch(Exception e) {
			ErrorManager.register(add_product.class.getName()+".save_product(Structure,User): "+e);
			return(null);
		}
	}

}
