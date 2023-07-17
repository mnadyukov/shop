let APPLICATION;

let PROPERTIES=new Map();

let USER={
	name:null,
	KEY:[],
	clear:function(){
		USER.name=null;
		USER.KEY=[];
	}
};

let CART={
	
	POSITIONS:[],
	
	clear:function(){
		this.POSITIONS=[];
	},

};
	
//{id name price quant}
CART.addPosition=function(desc){
	try{
		for(let pos of this.POSITIONS){
			if(pos.getValue(0)!==desc.getValue(0))continue;
			pos.setValue((+pos.getValue(3)+1)+"",3);
			return;
		}
		this.POSITIONS.push(
			new Structure("")
				.addChild(desc.getValue(0))
				.addChild(desc.getValue(1))
				.addChild(desc.getValue(2))
				.addChild("1")
		);
	}catch(e){
		console.log("CART.addPosition: "+e);
	}
};

CART.updateSumm=function(){
	try{
		let summ=this.POSITIONS.reduce((a,b)=>a+(+b.getValue(2)*+b.getValue(3)),0);
		this.HTML.SUMM.textContent="Сумма заказа: "+summ+" руб";
		this.HTML.ORDER.disabled=(summ===0);
	}catch(e){
		console.log("CART.updateSumma: "+e);
	}
};

CART.changeQuantity=function(e){
	this.POSITIONS[e.target.INDEX].setValue(""+e.target.value,3);
	this.updateSumm();
};

CART.updateCart=function(){
	try{
		while(this.HTML.PANEL.firstChild)this.HTML.PANEL.firstChild.remove();
		this.POSITIONS.forEach((v,i)=>{
			let tr=document.createElement("tr");
			this.HTML.PANEL.appendChild(tr);
			tr.className="cart_position";
			let td=document.createElement("td");
			tr.appendChild(td);
			td.className="cart_cell";
			td.style.textAlign="right";
			td.style.verticalAlign="middle";
			td.style.width="3em";
			td.textContent=""+(i+1);
			td=document.createElement("td");
			tr.appendChild(td);
			td.className="cart_cell";
			td.style.textAlign="left";
			td.style.verticalAlign="middle";
			td.style.maxWidth="30em";
			td.textContent=v.getValue(1);
			td=document.createElement("td");
			tr.appendChild(td);
			td.className="cart_cell";
			td.style.textAlign="right";
			td.style.verticalAlign="middle";
			td.style.width="8em";
			td.textContent=v.getValue(2)+" руб";
			td=document.createElement("td");
			tr.appendChild(td);
			td.className="cart_cell";
			td.style.textAlign="right";
			td.style.verticalAlign="middle";
			td.style.width="5em";
			let quant=document.createElement("input");
			td.appendChild(quant);
			quant.type="number";
			quant.style.width="4em";
			quant.step=1;
			quant.min=0;
			quant.value=v.getValue(3);
			quant.INDEX=i;
			quant.onchange=this.changeQuantity.bind(this);
		});
		this.updateSumm();
		this.HTML.FIO.INPUT.value=
			(PROPERTIES.get("second")?PROPERTIES.get("second"):"")+
			(PROPERTIES.get("first")?" "+PROPERTIES.get("first"):"")+
			(PROPERTIES.get("third")?" "+PROPERTIES.get("third"):"");
		this.HTML.ADDRESS.INPUT.textContent=PROPERTIES.get("address")?PROPERTIES.get("address"):"";
		this.HTML.PHONE.INPUT.value=PROPERTIES.get("phone")?PROPERTIES.get("phone"):"";
		this.HTML.EMAIL.INPUT.value=PROPERTIES.get("email")?PROPERTIES.get("email"):"";
	}catch(e){
		console.log("CART.updateCart: "+e);
	}
};

CART.clearCart=function(){
	CART.POSITIONS=[];
	this.updateCart();
};

//new_order{{<id{<price><count>}>...}<fio><address><phone><email><note>}
CART.placeOrder=function(){
console.log("order");
	try{
		let order=new Structure("new_order");
		let positions=new Structure("order");
		this.POSITIONS.forEach(v=>{
			if(v.getValue(3)==="0")return;
			positions.addChild(new Structure(v.getValue(0)).addChild(v.getValue(2)).addChild(v.getValue(3)));
		});
		order.addChild(positions);
		let check=true;
		let value;
		[	this.HTML.FIO,
			this.HTML.ADDRESS,
			this.HTML.PHONE,
			this.HTML.EMAIL,
			this.HTML.NOTE
		].forEach(v=>{
			v.onInput();
			value=v.getValue();
			if(value!==null){
				order.addChild(value);
			}else{
				check=false;
			}
		});
		if(check)CONNECTOR.send(order.toString());
	}catch(e){
		console.log("CART.placeOrder: "+e);
	}
};

CART.HTML=(function(){
	try{
		let html=document.createElement("div");
		html.className="form";
		let title=document.createElement("div");
		html.appendChild(title);
		title.className="form_title";
		title.textContent="Моя корзина";
		html.PANEL=document.createElement("table");
		html.appendChild(html.PANEL);
		html.PANEL.className="cart_table";
		html.SUMM=document.createElement("div");
		html.appendChild(html.SUMM);
		html.SUMM.className="label";
		html.FIO=new GUI.string(
			new Structure("string")
				.addChild("fio")
				.addChild("input")
				.addChild("ФИО покупателя*")
				.addChild("0")
				.addChild("1")
				.addChild("20")
				.addChild("40")
				.addChild("ФИО")
				.addChild("^.+$")
		);
		html.appendChild(html.FIO.HTML);
		html.ADDRESS=new GUI.text(
			new Structure("text")
				.addChild("address")
				.addChild("input")
				.addChild("Адрес доставки*")
				.addChild("1")
				.addChild("20")
				.addChild("40")
				.addChild("5")
				.addChild("15")
				.addChild("")
				.addChild("^.{10,}$")
		);
		html.appendChild(html.ADDRESS.HTML);
		html.PHONE=new GUI.string(
			new Structure("string")
				.addChild("phone")
				.addChild("input")
				.addChild("Телефон")
				.addChild("0")
				.addChild("1")
				.addChild("20")
				.addChild("40")
				.addChild("телефон")
				.addChild("^(\\+?[0-9]{6,})?$")
		);
		html.appendChild(html.PHONE.HTML);
		html.EMAIL=new GUI.string(
			new Structure("string")
				.addChild("email")
				.addChild("input")
				.addChild("Электронная почта")
				.addChild("0")
				.addChild("1")
				.addChild("20")
				.addChild("40")
				.addChild("")
				.addChild("^(.+@.+)?$")
		);
		html.appendChild(html.EMAIL.HTML);
		html.NOTE=new GUI.text(
			new Structure("text")
				.addChild("note")
				.addChild("input")
				.addChild("Комментарии")
				.addChild("1")
				.addChild("20")
				.addChild("40")
				.addChild("5")
				.addChild("15")
				.addChild("")
				.addChild("^.*$")
		);
		html.appendChild(html.NOTE.HTML);
		let bar=document.createElement("div");
		html.appendChild(bar);
		bar.className="form_commandbar";
		let clear=document.createElement("input");
		bar.appendChild(clear);
		clear.type="button";
		clear.className="form_button";
		clear.value="Очистить корзину";
		clear.onclick=CART.clearCart.bind(CART);
		html.ORDER=document.createElement("input");
		bar.appendChild(html.ORDER);
		html.ORDER.type="button";
		html.ORDER.className="form_button";
		html.ORDER.value="Сделать заказ";
		html.ORDER.onclick=CART.placeOrder.bind(CART);
		return(html);
	}catch(e){
		console.log("CART.HTML: "+e);
	}
})();

CART.viewCart=function(){
	try{
		COMMAND.clear_workarea();
		this.updateCart();
		APPLICATION.WORK_AREA.appendChild(this.HTML);
	}catch(e){
		console.log("CART.viewCart: "+e);
	}
};