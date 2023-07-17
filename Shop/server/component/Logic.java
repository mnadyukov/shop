package server.component;

public enum Logic {

	NO(false),
	YES(true);
	
	private boolean value;
	
	private Logic(boolean value) {
		this.value=value;
	}
	
	public String getValue() {
		if(value) {
			return("1");
		}else {
			return("0");
		}
	}
	
}
