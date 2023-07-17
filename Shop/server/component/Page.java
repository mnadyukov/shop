package server.component;

import server.utilities.Structure;
import server.utilities.TextPattern;

//page{<title><x-count><y-count><total_count><command>{PARAMETERS}}
public class Page implements Component {

	private Structure page;
	
	public Page() {
		page=new Structure("page")
			.addChild("")
			.addChild("1")
			.addChild("1")
			.addChild("1")
			.addChild("")
			.addChild("params");
	}
	
	public Page setTitle(String value) {
		if(value!=null && value.matches(TextPattern.TITLE))page.setValue(value, 0);
		return(this);
	}
	
	public Page setXCount(int value) {
		if(value>=1 && value<=100)page.setValue(""+value, 1);
		return(this);
	}
	
	public Page setYCount(int value) {
		if(value>=1 && value<=100)page.setValue(""+value, 2);
		return(this);
	}
	
	public Page setTotalCount(int value) {
		if(value>0)page.setValue(""+value, 3);
		return(this);
	}
	
	public Page setCommand(String value) {
		if(value!=null && value.matches(TextPattern.NAME))page.setValue(value, 4);
		return(this);
	}
	
	public Page addParameter(String name, String value) {
		if(name!=null && value!=null)page.getChild(5).addChild(new Structure(name).addChild(value));
		return(this);
	}
	
	public Structure getComponent() {
		return(page);
	}

}
