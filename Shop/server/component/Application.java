package server.component;

import server.utilities.Structure;
import server.utilities.TextPattern;

//application{<title><html_logo>{<MENU_AREA>}{<WORK_AREA>}}
public class Application implements Component{

	private Structure application;
	
	public Application() {
		application=new Structure("application")
			.addChild("")
			.addChild("")
			.addChild("")
			.addChild("");
	}
	
	public Application setTitle(String value) {
		if(value!=null && value.matches(TextPattern.TITLE))application.setValue(value,0);
		return(this);
	}
	
	public Application setLogo(String value) {
		if(value!=null && value.matches(TextPattern.TITLE))application.setValue(value,1);
		return(this);
	}
	
	public Application addToMenuArea(Component comp) {
		if(comp!=null)application.getChild(2).addChild(comp.getComponent());
		return(this);
	}
	
	public Application addToWorkArea(Component comp) {
		if(comp!=null)application.getChild(3).addChild(comp.getComponent());
		return(this);
	}

	public Structure getComponent() {
		return(application);
	}
	
}
