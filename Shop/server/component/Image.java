package server.component;

import server.utilities.Structure;
import server.utilities.TextPattern;

//image{<name><src><mode:view|edit><size>}
public class Image implements Component{

	public static enum Mode{
		VIEW("view"),
		EDIT("edit");
		
		private String value;
		
		private Mode(String value) {
			this.value=value;
		}
		
		public String getValue() {
			return(value);
		}
		
	}
	
	private Structure image;
	
	public Image(String name) {
		if(name==null || !name.matches(TextPattern.NAME))name="image";
		image=new Structure("image")
			.addChild(name)
			.addChild("")
			.addChild(Mode.VIEW.getValue())
			.addChild("1");
	}
	
	public Image setName(String value) {
		if(value!=null && value.matches(TextPattern.NAME))image.setValue(value,0);
		return(this);
	}
	
	public Image setSrc(String value) {
		if(value!=null)image.setValue(value, 1);
		return(this);
	}
	
	public Image setMode(Mode value) {
		if(value!=null)image.setValue(value.getValue(),2);
		return(this);
	}
	
	public Image setSize(int value) {
		if(value>0 && value<=100)image.setValue(""+value,3);
		return(this);
	}
	
	public Structure getComponent() {
		return(image);
	}

}
