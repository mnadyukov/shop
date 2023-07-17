
function Test(){
	let app=new Structure("application")
		.addChild("Кот Матроскин")
		.addChild("<div><div style='font-size:2em;font-family:sans-serif;'>Кот Матроскин</div><div style='font-size:1em;font-family:sans-serif;font-weight:bold;'>Магазин товаров для кошек</div></div>")
		.addChild(new Structure(""))
		.addChild(new Structure(""))
	;
	let account=new Structure("command")
		.addChild("Личный кабинет")
		.addChild("account")
	;
	let cart=new Structure("command")
		.addChild("Корзина")
		.addChild("cart")
	;
	COMMAND.execute(
		new Structure()
			.addChild(app)
			.addChild(
				new Structure("set_menuarea")
					.addChild(account)
					.addChild(cart)
			)
			.addChild(
				new Structure("add_properties")
					.addChild(new Structure("host").addChild("localhost"))
					.addChild(new Structure("port").addChild("4000"))
			)
	);
	console.log(PROPERTIES);
};