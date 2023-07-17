package server.component;

import server.utilities.Structure;

public class VSpace implements Component {

	private Structure vspace;
	
	public VSpace() {
		this(0.0);
	}
	
	public VSpace(double size) {
		if(size<0 || size>100)size=0;
		vspace=new Structure("vspace")
			.addChild(""+size);
	}
	
	public VSpace setSize(double value) {
		if(value>=0 && value<=100)vspace.setValue(""+value,0);
		return(this);
	}

	public Structure getComponent() {
		return(vspace);
	}
	
}
