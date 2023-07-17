package server.component;

import server.utilities.Structure;
import server.utilities.TextPattern;

//catalog{<name><title><mode:view|select|edit><max-width><max-height><handler>}
public class Catalog implements Component{

	public static enum Mode{
		SELECT("select"),
		EDIT("edit");
		
		private String value;
		
		private Mode(String value) {
			this.value=value;
		}
		
		public String getValue() {
			return(value);
		}
		
	}
	
	private Structure catalog;
	
	public Catalog(String name) {
		if(name==null || !name.matches(TextPattern.NAME))name="catalog";
		catalog=new Structure("catalog")
			.addChild(name)
			.addChild("")
			.addChild(Mode.SELECT.getValue())
			.addChild("0")
			.addChild("0")
			.addChild("");
	}
	
	public Catalog setName(String value) {
		if(value!=null && value.matches(TextPattern.NAME))catalog.setValue(value,0);
		return(this);
	}
	
	public Catalog setTitle(String value) {
		if(value!=null && value.matches(TextPattern.TITLE))catalog.setValue(value,1);
		return(this);
	}
	
	public Catalog setMode(Mode value) {
		if(value!=null)catalog.setValue(value.getValue(),2);
		return(this);
	}
	
	public Catalog setMaxWidth(int value) {
		if(value>=0 && value<=100)catalog.setValue(""+value,3);
		return(this);
	}
	
	public Catalog setMaxHeight(int value) {
		if(value>=0 && value<=100)catalog.setValue(""+value,4);
		return(this);
	}
	
	public Catalog setHandler(String value) {
		if(value!=null && (value.isEmpty() || value.matches(TextPattern.NAME)))catalog.setValue(value,5);
		return(this);
	}
	
	public Structure getComponent() {
		return(catalog);
	}

}
