package server.component;

import server.utilities.Structure;
import server.utilities.TextPattern;

//form{<title><layout><border-width><min-width><max-width><min-height><max-height>{COMMANDS}{FIELDS}}
//COMMANDS:=<command_1>{title_1}...
public class Form implements Component{

	private Structure form;
	
	public Form() {
		form=new Structure("form")
			.addChild("")
			.addChild(Layout.VERTICAL.getValue())
			.addChild("0")
			.addChild("0")
			.addChild("0")
			.addChild("0")
			.addChild("0")
			.addChild("")
			.addChild("");
	}
	
	public Form setTitle(String value) {
		if(value!=null && value.matches(TextPattern.TITLE))form.setValue(value, 0);
		return(this);
	}
	
	public Form setLayout(Layout value) {
		if(value!=null)form.setValue(value.getValue(), 1);
		return(this);
	}
	
	public Form setBorderWidth(int value) {
		if(value>=0 && value<=10)form.setValue(""+value, 2);
		return(this);
	}
	
	public Form setMinWidth(int value) {
		if(value>=0 && value<=100)form.setValue(""+value, 3);
		return(this);
	}
	
	public Form setMaxWidth(int value) {
		if(value>0 && value<=100)form.setValue(""+value, 4);
		return(this);
	}
	
	public Form setMinHeight(int value) {
		if(value>=0 && value<=100)form.setValue(""+value, 5);
		return(this);
	}
	
	public Form setMaxHeight(int value) {
		if(value>=0 && value<=100)form.setValue(""+value, 6);
		return(this);
	}
	
	public Form addCommand(String command, String title) {
		if(command==null)return(this);
		if(title==null)return(this);
		if(command.matches(TextPattern.ACTION) && title.matches(TextPattern.TITLE)) {
			form.getChild(7).addChild(
				new Structure(command).addChild(title)
			);
		}
		return(this);
	}
	
	public Form addComponent(Component comp) {
		if(comp!=null)form.getChild(8).addChild(comp.getComponent());
		return(this);
	}
	
	public Structure getComponent() {
		return(form);
	}

}
