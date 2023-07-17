package server.component;

import server.utilities.Structure;
import server.utilities.TextPattern;

//text{<name><mode:input|edit><title><grow><min-width><max-width><min-height><max-height><value><regexp>}
public class Text implements Component{

	public static enum Mode{
		INPUT("input"),
		EDIT("edit");
		
		private String value;
		
		private Mode(String value) {
			this.value=value;
		}
		
		public String getValue() {
			return(value);
		}
		
	}
	
	private Structure text;
	
	public Text(String name) {
		if(name==null || !name.matches(TextPattern.NAME))name="text";
		text=new Structure("text")
			.addChild(name)
			.addChild(Mode.INPUT.getValue())
			.addChild("")
			.addChild("0")
			.addChild("0")
			.addChild("0")
			.addChild("0")
			.addChild("0")
			.addChild("")
			.addChild("");
	}
	
	public Text setName(String value) {
		if(value!=null && value.matches(TextPattern.NAME))text.setValue(value, 0);
		return(this);
	}
	
	public Text setMode(Mode value) {
		if(value!=null)text.setValue(value.getValue(), 1);
		return(this);
	}
	
	public Text setTitle(String value) {
		if(value!=null && value.matches(TextPattern.TITLE))text.setValue(value, 2);
		return(this);
	}
	
	public Text setGrow(int value) {
		if(value>=0 && value<=10)text.setValue(""+value, 3);
		return(this);
	}
	
	public Text setMinWidth(int value) {
		if(value>=0 && value<=100)text.setValue(""+value, 4);
		return(this);
	}
	
	public Text setMaxWidth(int value) {
		if(value>=0 && value<=100)text.setValue(""+value, 5);
		return(this);
	}
	
	public Text setMinHeight(int value) {
		if(value>=0 && value<=100)text.setValue(""+value, 6);
		return(this);
	}
	
	public Text setMaxHeight(int value) {
		if(value>=0 && value<=100)text.setValue(""+value, 7);
		return(this);
	}
	
	public Text setValue(String value) {
		if(value!=null)text.setValue(value, 8);
		return(this);
	}
	
	public Text setRegexp(String value) {
		if(value!=null)text.setValue(value, 9);
		return(this);
	}

	public Structure getComponent() {
		return(text);
	}
	
}
