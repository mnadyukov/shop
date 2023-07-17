package server.component;

import server.utilities.Structure;
import server.utilities.TextPattern;

//string{<name><mode:input|edit><title><hidden><grow><min-width><max-width><value><regexp>}
public class TextString implements Component{

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
	
	private Structure string;
	
	public TextString(String name) {
		if(name==null || !name.matches(TextPattern.NAME))name="string";
		string=new Structure("string")
			.addChild(name)
			.addChild(Mode.INPUT.getValue())
			.addChild("")
			.addChild(Logic.NO.getValue())
			.addChild("0")
			.addChild("0")
			.addChild("0")
			.addChild("")
			.addChild("");
	}
	
	public TextString setName(String value) {
		if(value!=null && value.matches(TextPattern.NAME))string.setValue(value,0);
		return(this);
	}
	
	public TextString setMode(Mode value) {
		if(value!=null)string.setValue(value.getValue(),1);
		return(this);
	}
	
	public TextString setTitle(String value) {
		if(value!=null && value.matches(TextPattern.TITLE))string.setValue(value,2);
		return(this);
	}
	
	public TextString setHidden(Logic value) {
		if(value!=null)string.setValue(value.getValue(), 3);
		return(this);
	}
	
	public TextString setGrow(int value) {
		if(value>=0 && value<=10)string.setValue(""+value, 4);
		return(this);
	}
	
	public TextString setMinWidth(int value) {
		if(value>=0 && value<=100)string.setValue(""+value, 5);
		return(this);
	}
	
	public TextString setMaxWidth(int value) {
		if(value>=0 && value<=10)string.setValue(""+value, 6);
		return(this);
	}
	
	public TextString setValue(String value) {
		if(value!=null)string.setValue(value,7);
		return(this);
	}
	
	public TextString setRegexp(String value) {
		if(value!=null)string.setValue(value,8);
		return(this);
	}
	
	public Structure getComponent() {
		return(string);
	}

}
