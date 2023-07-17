package server.component;

public enum Layout {
	
	VERTICAL("v"),
	HORIZONTAL("h");
	
	private String value;
	
	private Layout(String value) {
		this.value=value;
	}
	
	public String getValue() {
		return(value);
	}
}
