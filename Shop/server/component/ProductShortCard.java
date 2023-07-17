package server.component;

import server.utilities.Structure;
import server.utilities.TextPattern;

//product_short{<id><name><price>}
public class ProductShortCard implements Component {

	private Structure card;
	
	public ProductShortCard(){
		card=new Structure("product_short")
			.addChild("0")
			.addChild("")
			.addChild("0");
	}
	
	public ProductShortCard setId(long value) {
		if(value>=0)card.setValue(""+value,0);
		return(this);
	}
	
	public ProductShortCard setName(String value) {
		if(value!=null && value.matches(TextPattern.DESCRIPTION))card.setValue(value,1);
		return(this);
	}
	
	public ProductShortCard setPrice(double value) {
		if(value>0.0)card.setValue(""+value,2);
		return(this);
	}
	
	public Structure getComponent() {
		return(card);
	}

}
