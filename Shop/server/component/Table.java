package server.component;

import server.utilities.Structure;
import server.utilities.TextPattern;

//table{<name><title><mode:view|singleselect|multiselect><border-width><hline-width><vline-width><min-width><max-width><min-height><max-height>(hide|show){HEADER}{DATA}}
//HEADER:={<title><width><halign><valign><visible><datable><type>}{...}...;
//DATA:={<value_1.1>{COMMAND_1.1}<value_1.2>{COMMAND_1.2}...}{<value_2.1>{COMMAND_2.1}<value_2.2>{COMMAND_2.2}...}...
public class Table implements Component{

	public static enum Mode{
		VIEW("view"),
		SINGLESELECT("singleselect"),
		MULTISELECT("multiselect");
		
		private String value;
		
		private Mode(String value) {
			this.value=value;
		}
		
		public String getValue() {
			return(value);
		}
		
	}
	
	public static class Column implements Component{
		
		public static enum Type{
			TEXT("t"),
			NUMBER("n");
			
			private String value;
			
			private Type(String value) {
				this.value=value;
			}
			
			public String getValue() {
				return(value);
			}
		}
		
		public static enum VAlign{
			TOP("t"),
			MIDDLE("m"),
			BOTTOM("b");
			
			private String value;
			
			private VAlign(String value) {
				this.value=value;
			}
			
			public String getValue() {
				return(value);
			}
		}
		
		public static enum HAlign{
			LEFT("l"),
			CENTER("c"),
			RIGHT("r"),
			JUSTIFY("j");
			
			private String value;
			
			private HAlign(String value) {
				this.value=value;
			}
			
			public String getValue() {
				return(value);
			}
		}
		
		private Structure column;
		
		public Column() {
			column=new Structure("")
				.addChild("")
				.addChild("0")
				.addChild(HAlign.LEFT.getValue())
				.addChild(VAlign.TOP.getValue())
				.addChild(Logic.YES.getValue())
				.addChild(Logic.NO.getValue())
				.addChild(Type.TEXT.getValue());
		}
		
		public Column setTitle(String value) {
			if(value!=null && value.matches(TextPattern.TITLE))column.setValue(value, 0);
			return(this);
		}
		
		public Column setWidth(int value) {
			if(value>=0 && value<=100)column.setValue(""+value, 1);
			return(this);
		}
		
		public Column setHalign(HAlign value) {
			if(value!=null)column.setValue(value.getValue(), 2);
			return(this);
		}
		
		public Column setValign(VAlign value) {
			if(value!=null)column.setValue(value.getValue(), 3);
			return(this);
		}
		
		public Column setVisible(Logic value) {
			if(value!=null)column.setValue(value.getValue(), 4);
			return(this);
		}
		
		public Column setDatable(Logic value) {
			if(value!=null)column.setValue(value.getValue(), 5);
			return(this);
		}
		
		public Column setType(Type value) {
			if(value!=null)column.setValue(value.getValue(), 6);
			return(this);
		}

		public Structure getComponent() {
			return(column);
		}
		
	}
	
	public static class DataRow implements Component{
		
		public static class DataCell implements Component{

			private Structure cell;
			
			public DataCell(String value, String cmnd) {
				cell=new Structure(value==null?"":value);
				if(cmnd!=null)cell.addChild(cmnd);
			}
			
			public DataCell(String value, Structure cmnd) {
				cell=new Structure(value==null?"":value);
				if(cmnd!=null)cell.addChild(cmnd);
			}
			
			public Structure getComponent() {
				return(cell);
			}
			
		}
		
		private Structure row;
		
		public DataRow() {
			row=new Structure("");
		}
		
		public DataRow addDataCell(DataCell value) {
			if(value!=null)row.addChild(value.getComponent());
			return(this);
		}

		public Structure getComponent() {
			return(row);
		}
		
		
		
	}
	
	private Structure table;
	
	public Table(String name) {
		if(name==null || !name.matches(TextPattern.NAME))name="table";
		table=new Structure("table")
			.addChild(name)
			.addChild("")
			.addChild(Mode.VIEW.getValue())
			.addChild("0")
			.addChild("0")
			.addChild("0")
			.addChild("0")
			.addChild("0")
			.addChild("0")
			.addChild("0")
			.addChild("show")
			.addChild("");
	}
	
	public Table setName(String value) {
		if(value!=null && value.matches(TextPattern.NAME))table.setValue(value, 0);
		return(this);
	}
	
	public Table setTitle(String value) {
		if(value!=null && value.matches(TextPattern.TITLE))table.setValue(value, 1);
		return(this);
	}
	
	public Table setMode(Mode value) {
		if(value!=null)table.setValue(value.getValue(), 2);
		return(this);
	}
	
	public Table setBorderWidth(int value) {
		if(value>=0 && value<=10)table.setValue(""+value, 3);
		return(this);
	}
	
	public Table setHorizontalLinesWidth(int value) {
		if(value>=0 && value<=10)table.setValue(""+value, 4);
		return(this);
	}
	
	public Table setVerticalLinesWidth(int value) {
		if(value>=0 && value<=10)table.setValue(""+value, 5);
		return(this);
	}
	
	public Table setMinWidth(int value) {
		if(value>=0 && value<=100)table.setValue(""+value, 6);
		return(this);
	}
	
	public Table setMaxWidth(int value) {
		if(value>=0 && value<=100)table.setValue(""+value, 7);
		return(this);
	}
	
	public Table setMinHeight(int value) {
		if(value>=0 && value<=100)table.setValue(""+value, 8);
		return(this);
	}
	
	public Table setMaxHeight(int value) {
		if(value>=0 && value<=100)table.setValue(""+value, 9);
		return(this);
	}
	
	public Table setHeaderVisibility(Logic value) {
		if(value!=null)table.setValue(value==Logic.YES?"show":"hide", 10);
		return(this);
	}
	
	public Table addColumn(Column value) {
		if(value!=null)table.getChild(10).addChild(value.getComponent());
		return(this);
	}
	
	public Table addDataRow(DataRow value) {
		if(value!=null)table.getChild(11).addChild(value.getComponent());
		return(this);
	}
	
	public Table addDatarow(Structure value) {
		if(value!=null)table.getChild(11).addChild(value);
		return(this);
	}
	
	public Structure getComponent() {
		return(table);
	}

}
