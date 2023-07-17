package server.component;

import server.utilities.Structure;
import server.utilities.TextPattern;

//list{<name><title><grow><min-width><max-width><value>{OPTIONS}}
//OPTIONS:=<value_1>{<title_1>}...
public class List implements Component {

	public static class Option implements Component{

		private Structure option;
		
		public Option(String value, String title) {
			option=new Structure((value==null || !value.matches(TextPattern.NAME))?"":value)
				.addChild((title==null || !title.matches(TextPattern.TITLE))?"":title);
		}
		
		public Structure getComponent() {
			return(option);
		}
		
	}
	
	private Structure list;
	
	public List(String name) {
		if(name==null || !name.matches(TextPattern.NAME))name="list";
		list=new Structure("list")
			.addChild(name)
			.addChild("")
			.addChild("0")
			.addChild("0")
			.addChild("0")
			.addChild("")
			.addChild("");
	}
	
	public List setName(String value) {
		if(value!=null && value.matches(TextPattern.NAME))list.setValue(value, 0);
		return(this);
	}
	
	public List setTitle(String value) {
		if(value!=null && value.matches(TextPattern.TITLE))list.setValue(value, 1);
		return(this);
	}
	
	public List setGrow(int value) {
		if(value>=0 && value<=10)list.setValue(""+value, 2);
		return(this);
	}
	
	public List setMinWidth(int value) {
		if(value>=0 && value<=100)list.setValue(""+value, 3);
		return(this);
	}
	
	public List setMaxWidth(int value) {
		if(value>=0 && value<=100)list.setValue(""+value, 4);
		return(this);
	}
	
	public List setValue(String value) {
		if(value!=null)list.setValue(value, 5);
		return(this);
	}
	
	public List addOption(Option opt) {
		if(opt!=null)list.getChild(6).addChild(opt.getComponent());
		return(this);
	}

	public Structure getComponent() {
		return(list);
	}

}
