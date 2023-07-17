package server.component;

import server.utilities.Structure;
import server.utilities.TextPattern;

//panel{<title><layout><border-width><min-width><max-width><min-height><max-height>{CHILDREN}}
public class Panel implements Component {

	private Structure panel;
	
	public Panel() {
		panel=new Structure("panel")
			.addChild("")
			.addChild(Layout.VERTICAL.getValue())
			.addChild("0")
			.addChild("0")
			.addChild("0")
			.addChild("0")
			.addChild("0")
			.addChild("");
	}
	
	public Panel setTitle(String value) {
		if(value!=null && value.matches(TextPattern.TITLE))panel.setValue(value, 0);
		return(this);
	}
	
	public Panel setLayout(Layout value) {
		if(value!=null)panel.setValue(value.getValue(), 1);
		return(this);
	}
	
	public Panel setBorderWidth(int value) {
		if(value>=0 && value<=10)panel.setValue(""+value, 2);
		return(this);
	}
	
	public Panel setMinWidth(int value) {
		if(value>=0 && value<=100)panel.setValue(""+value, 3);
		return(this);
	}
	
	public Panel setMaxWidth(int value) {
		if(value>=0 && value<=100)panel.setValue(""+value, 4);
		return(this);
	}
	
	public Panel setMinHeight(int value) {
		if(value>=0 && value<=100)panel.setValue(""+value, 5);
		return(this);
	}
	
	public Panel setMaxHeight(int value) {
		if(value>=0 && value<=100)panel.setValue(""+value, 6);
		return(this);
	}
	
	public Panel addComponent(Component comp) {
		if(comp!=null)panel.getChild(7).addChild(comp.getComponent());
		return(this);
	}
	
	public Structure getComponent() {
		return(panel);
	}

}
