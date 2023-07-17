package server.component;

import server.utilities.Structure;

//label{<text><border-width>}
public class Label implements Component{

	private Structure label;

	public Label() {
		label=new Structure("label")
			.addChild("")
			.addChild("0");
	}
	
	public Label setText(String value) {
		if(value!=null)label.setValue(value,0);
		return(this);
	}
	
	public Label setBorderWidth(int value) {
		if(value>=0 && value<=10)label.setValue(""+value,1);
		return(this);
	}
	
	public Structure getComponent() {
		return(label);
	}
	
	
	
}
