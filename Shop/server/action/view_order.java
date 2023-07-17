package server.action;

import server.ErrorManager;
import server.User;
import server.query.getOrder;
import server.utilities.Structure;
import server.component.Label;
import server.component.Layout;
import server.component.Logic;
import server.component.Panel;
import server.component.Table;

public class view_order implements Action{

	public String execute(Structure message, User user) {
		try {
			Structure order=new getOrder().execute(message);
			Panel pan=new Panel()
				.setTitle("Заказ №"+order.getValue(0))
				.setLayout(Layout.VERTICAL)
				.setBorderWidth(0)
				.setMinWidth(10)
				.setMaxWidth(40)
				.setMinHeight(20)
				.setMaxHeight(40);
			Label zakaz_date=new Label()
				.setText("Дата заказа: "+order.getValue(1));
			Label delivery_date=new Label()
				.setText("Дата доставки: "+order.getValue(2));
			Label status=new Label()
				.setText("Статус заказа: "+order.getValue(3));
			Label note=new Label()
				.setText("Комментарии: "+order.getValue(4));
			Table positions=new Table("pos")
				.setTitle("Позиции заказа")
				.setMode(Table.Mode.VIEW)
				.setMinWidth(0)
				.setMaxWidth(60)
				.setMinHeight(10)
				.setMaxHeight(30)
				.setBorderWidth(0)
				.setHorizontalLinesWidth(1)
				.setVerticalLinesWidth(1)
				.setHeaderVisibility(Logic.YES)
				.addColumn(new Table.Column()
					.setTitle("№")
					.setWidth(3)
					.setValign(Table.Column.VAlign.MIDDLE)
					.setHalign(Table.Column.HAlign.RIGHT)
					.setType(Table.Column.Type.NUMBER)
					.setVisible(Logic.YES)
					.setDatable(Logic.NO))
				.addColumn(new Table.Column()
						.setTitle("Наименование")
						.setWidth(30)
						.setValign(Table.Column.VAlign.MIDDLE)
						.setHalign(Table.Column.HAlign.LEFT)
						.setType(Table.Column.Type.TEXT)
						.setVisible(Logic.YES)
						.setDatable(Logic.NO))
				.addColumn(new Table.Column()
						.setTitle("Цена")
						.setWidth(8)
						.setValign(Table.Column.VAlign.MIDDLE)
						.setHalign(Table.Column.HAlign.RIGHT)
						.setType(Table.Column.Type.NUMBER)
						.setVisible(Logic.YES)
						.setDatable(Logic.NO))
				.addColumn(new Table.Column()
						.setTitle("Количество")
						.setWidth(5)
						.setValign(Table.Column.VAlign.MIDDLE)
						.setHalign(Table.Column.HAlign.RIGHT)
						.setType(Table.Column.Type.NUMBER)
						.setVisible(Logic.YES)
						.setDatable(Logic.NO));
				for(Structure dat:order.getChild(5).getChildren())positions.addDatarow(dat);
			double summa=0.0;
			for(Structure pos:order.getChild(5).getChildren())
				summa+=Double.parseDouble(pos.getValue(2))*Double.parseDouble(pos.getValue(3));
			Label summ=new Label()
					.setText("Сумма заказа: "+summa+"руб");
			pan.addComponent(zakaz_date);
			pan.addComponent(delivery_date);
			pan.addComponent(status);
			pan.addComponent(note);
			pan.addComponent(positions);
			pan.addComponent(summ);
			return(new Structure("")
				.addChild(new Structure("set_workarea")
					.addChild(pan.getComponent())
				).toString()
			);
		}catch(Exception e) {
			ErrorManager.register(view_order.class.getName()+".execute(Structure,User): "+e);
			return(null);
		}
	}

	
	
}
