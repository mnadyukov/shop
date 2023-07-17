package server.component;

import server.utilities.Structure;
import server.utilities.TextPattern;

//parameter{<name><value>}
public class Parameter implements Component{

	private Structure parameter;
	
	public Parameter(String name) {
		this(name,"");
	}
	
	public Parameter(String name, String value) {
		if(name==null || !name.matches(TextPattern.NAME))name="parameter";
		if(value==null)value="";
		parameter=new Structure("parameter")
			.addChild(name)
			.addChild(value);
	}
	
	public Parameter setName(String value) {
		if(value!=null && value.matches(TextPattern.NAME))parameter.setValue(value,0);
		return(this);
	}
	
	public Parameter setValue(String value) {
		if(value!=null)parameter.setValue(value,1);
		return(this);
	}
	
	public Structure getComponent() {
		return(parameter);
	}

}
