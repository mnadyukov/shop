
let COMMAND={
	
	//{<com_1><com_2>...}
	execute:function(data){
		try{
			let desc=(data.constructor===String?Structure.fromString(data):data);
			desc.getChildren().forEach(v=>{COMMAND[v.getValue()](v);});
		}catch(e){
			console.log("COMMAND.execute: "+e);
		}
	},
	
	empty:function(desc){
		//do nothing
	},
	
	exit:function(desc){
		USER.clear();
		PROPERTIES.clear();
		CART.clear();
		COMMAND.clear_workarea();
		CONNECTOR.send(desc.toString());
	},
	
	authorizing:function(desc){
		USER.name=desc.getChild(0).getValue(0);
		let password=desc.getChild(1).getValue(0);
		while(true){
			if(password.length>=32){
				password=password.substring(0,32);
				break;
			}
			password+=password;
		}
		for(let i=0;i<password.length;i+=4)
			USER.KEY[i/4]=
				(password.charCodeAt(i)<<24)+
				(password.charCodeAt(i+1)<<16)+
				(password.charCodeAt(i+2)<<8)+
				(password.charCodeAt(i+3));
		CONNECTOR.send(desc.toString());
	},
	
	wrong_user:function(){
		USER.name=null;
		USER.KEY=[];
		COMMAND.set_workarea(
			new Structure("")
				.addChild(
					new Structure("label")
						.addChild("Пользователь с таким именем и паролем не найден")
				)
		);
	},
	
	server_error:function(){
		try{
			COMMAND.set_workarea(
				new Structure("")
					.addChild(
						new Structure("label")
							.addChild("Внутренняя ошибка сервера")
					)
			);
		}catch(e){
			console.log("COMMAND.server_error: "+e);
		}
	},
	
	application:function(desc){
		if(APPLICATION && APPLICATION.HTML)APPLICATION.HTML.remove();
		APPLICATION=new GUI.application(desc);
		document.body.appendChild(APPLICATION.HTML);
	},
	
	//add_properties{<key_1>{<value_1>}<key_2>{<value_2>}...}
	set_properties:function(desc){
		try{
			desc.getChildren().forEach(v=>PROPERTIES.set(v.getValue(),v.getValue(0)));
		}catch(e){
			console.log("COMMAND.set_properties: "+e);
		}
	},
	
	cart:function(){
		CART.viewCart();
	},
	
	clear_cart:function(){
		CART.clearCart();
	},
	
	new_order:function(desc){
		try{
			let order="";
			CART.positions.forEach(v=>{
				if(v.getValue(3)!=="0")order+=(v.getValue(0)+" "+v.getValue(2)+" "+v.getValue(3)+";");
			});
			let note=
				"ФИО: "+desc.getChildren("second")[0].getValue(0)+" "+desc.getChildren("first")[0].getValue(0)+" "+desc.getChildren("third")[0].getValue(0)+"; "+
				"Адрес доставки: "+desc.getChildren("address")[0].getValue(0)+"; "+
				"Телефон: "+desc.getChildren("phone")[0].getValue(0)+"; "+
				"Электронная почта: "+desc.getChildren("email")[0].getValue(0)+";\n"+
				desc.getChildren("note")[0].getValue(0);
			let res=new Structure("new_order")
				.addChild(new Structure("order").addChild(order))
				.addChild(new Structure("note").addChild(note))
			CONNECTOR.send(res.toString());
		}catch(e){
			console.log("COMMAND.new_order: "+e);
		}
	},
	
	//set_workarea{<guiElement_1><guiElement_2>...}
	set_workarea:function(desc){
		try{
			COMMAND.clear_workarea();
			if(APPLICATION && APPLICATION.WORK_AREA){
				desc.getChildren().forEach(v=>{
					APPLICATION.WORK_AREA.appendChild(new GUI[v.getValue()](v).HTML);
				});
			};
		}catch(e){
			console.log("COMMAND.set_workarea: "+e);
		}
	},
	
	clear_workarea:function(){
		try{
			if(APPLICATION && APPLICATION.WORK_AREA){
				while(APPLICATION.WORK_AREA.firstChild)APPLICATION.WORK_AREA.removeChild(APPLICATION.WORK_AREA.firstChild);
			};
		}catch(e){
			console.log("COMMAND.clear_workarea: "+e);
		}
	},
	
	//set_menuarea{<guiElement_1><guiElement_2>...}
	add_menuarea:function(desc){
		try{
			if(APPLICATION && APPLICATION.MENU_AREA){
				desc.getChildren().forEach(v=>APPLICATION.MENU_AREA.appendChild(new GUI[v.getValue()](v).HTML));
			};
		}catch(e){
			console.log("COMMAND.set_menuarea: "+e);
		}
	},
	
	clear_menuarea:function(){
		try{
			if(APPLICATION && APPLICATION.MENU_AREA){
				while(APPLICATION.MENU_AREA.firstChild)APPLICATION.MENU_AREA.removeChild(APPLICATION.MENU_AREA.firstChild);
			};
		}catch(e){
			console.log("COMMAND.clear_menuarea: "+e);
		}
	},
	
	set_menuarea:function(desc){
		COMMAND.clear_menuarea();
		COMMAND.add_menuarea(desc);
	},
	
}