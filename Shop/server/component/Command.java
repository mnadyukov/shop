package server.component;

import server.utilities.Structure;
import server.utilities.TextPattern;

//command{<title><type:b|t><COMMAND>}
public class Command implements Component{

	public static enum Type{
		BUTTON("b"),
		TEXT("t");
		
		private String value;
		
		private Type(String value) {
			this.value=value;
		}
		
		public String getValue() {
			return(value);
		}
		
	}
	
	private Structure command;

	public Command() {
		command=new Structure("command")
			.addChild("")
			.addChild(Type.BUTTON.getValue())
			.addChild("");
	}
	
	public Command setTitle(String value) {
		if(value!=null && value.matches(TextPattern.TITLE))command.setValue(value, 0);
		return(this);
	}
	
	public Command setType(Type value) {
		if(value!=null)command.setValue(value.getValue(),1);
		return(this);
	}
	
	public Command setCommand(String com) {
		if(com!=null && com.matches(TextPattern.ACTION))command.setChild(new Structure(com), 2);
		return(this);
	}
	
	public Command setCommand(Structure com) {
		if(com!=null && com.getValue().matches(TextPattern.ACTION))command.setChild(com, 2);
		return(this);
	}
	
	public Structure getComponent() {
		return(command);
	}
	
	
	
}
