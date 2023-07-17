
let GUI={
	
	//application{<title><html_logo>{<MENU_AREA>}{<WORK_AREA>}}
	application:function(data){
		try{
			let desc=(data.constructor===String?Structure.fromString(data):data);
			document.title=desc.getValue(0);
			this.HTML=document.createElement("div");
			this.HTML.className="application";
			let logo=document.createElement("div");
			this.HTML.appendChild(logo);
			logo.className="application_logo";
			logo.innerHTML=desc.getValue(1);
			let panel=document.createElement("div");
			this.HTML.appendChild(panel);
			panel.className="application_panel";
			this.MENU_AREA=document.createElement("div");
			panel.appendChild(this.MENU_AREA);
			this.MENU_AREA.className="application_menu_area";
			this.WORK_AREA=document.createElement("div");
			panel.appendChild(this.WORK_AREA);
			this.WORK_AREA.className="application_work_area";
			desc.getChild(2).getChildren().forEach(v=>this.MENU_AREA.appendChild(new GUI[v.getValue()](v).HTML));
			desc.getChild(3).getChildren().forEach(v=>this.WORK_AREA.appendChild(new GUI[v.getValue()](v).HTML));
		}catch(e){
			console.log("GUI.application: "+e);
			return(null);
		}
	},
		
	//string{<name><mode:input|edit><title><hidden><grow><min-width><max-width><value><regexp>}
	string:function(desc,register){
		try{
			this.NAME=desc.getValue(0);
			this.REGEXP=desc.getValue(8);
			this.HTML=document.createElement("div");
			this.HTML.className="string";
			this.HTML.style.grow=desc.getValue(4);
			if(desc.getValue(2)!==""){
				let title=document.createElement("div");
				this.HTML.appendChild(title);
				title.className="field_title";
				title.textContent=desc.getValue(2);
			}
			let inputbox=document.createElement("div");
			this.HTML.appendChild(inputbox);
			inputbox.className="string_box";
			this.INPUT=document.createElement("input");
			inputbox.appendChild(this.INPUT);
			switch(desc.getValue(3)){
				case "0":{this.INPUT.type="text";break;}
				case "1":{this.INPUT.type="password";break}
				default:{throw new Error("неизвестное значение hided="+desc.getValue(3));}
			}
			this.INPUT.className="string_input";
			this.INPUT.style.minWidth=(+desc.getValue(5)===0?undefined:(desc.getValue(5)+"em"));
			this.INPUT.style.maxWidth=(+desc.getValue(6)===0?undefined:(desc.getValue(6)+"em"));
			this.INPUT.value=desc.getValue(7);
			this.INPUT.oninput=GUI.string.prototype.onInput.bind(this);
			switch(desc.getValue(1)){
				case "input":{break;}
				case "edit":{
					this.VALUE=desc.getValue(7);
					this.CANCEL=document.createElement("input");
					inputbox.appendChild(this.CANCEL);
					this.CANCEL.type="button";
					this.CANCEL.className="field_button";
					this.CANCEL.value="Сбросить";
					this.CANCEL.disabled=true;
					this.CANCEL.onclick=GUI.string.prototype.onCancel.bind(this);
					break;
				}
				default:{throw new Error("неизвестное значение mode="+desc.getValue(1));}
			}
			if(register)register(this);
		}catch(e){
			console.log("GUI.string: "+e);
			return(null);
		}
	},
	
	//text{<name><mode:input|edit><title><grow><min-width><max-width><min-height><max-height><value><regexp>}
	text:function(desc,register){
		try{
			this.NAME=desc.getValue(0);
			this.REGEXP=desc.getValue(9);
			this.HTML=document.createElement("div");
			this.HTML.className="text";
			this.HTML.style.grow=desc.getValue(3);
			if(desc.getValue(2)!==""){
				let title=document.createElement("div");
				this.HTML.appendChild(title);
				title.className="field_title";
				title.textContent=desc.getValue(2);
			}
			let inputbox=document.createElement("div");
			this.HTML.appendChild(inputbox);
			inputbox.className="text_box";
			this.INPUT=document.createElement("div");
			inputbox.appendChild(this.INPUT);
			this.INPUT.contentEditable="true";
			this.INPUT.className="text_input";
			this.INPUT.style.minWidth=(+desc.getValue(4)===0?undefined:(desc.getValue(4)+"em"));
			this.INPUT.style.maxWidth=(+desc.getValue(5)===0?undefined:(desc.getValue(5)+"em"));
			this.INPUT.style.minHeight=(+desc.getValue(6)===0?undefined:(desc.getValue(6)+"em"));
			this.INPUT.style.maxHeight=(+desc.getValue(7)===0?undefined:(desc.getValue(7)+"em"));
			this.INPUT.textContent=desc.getValue(8);
			this.INPUT.onkeyup=GUI.text.prototype.onInput.bind(this);
			switch(desc.getValue(1)){
				case "input":{break;}
				case "edit":{//редактирование
					this.VALUE=desc.getValue(8);
					this.CANCEL=document.createElement("input");
					inputbox.appendChild(this.CANCEL);
					this.CANCEL.type="button";
					this.CANCEL.className="field_button";
					this.CANCEL.value="Сбросить";
					this.CANCEL.disabled=true;
					this.CANCEL.onclick=GUI.text.prototype.onCancel.bind(this);
					break;
				}
				default:{throw new Error("неизвестное значение mode="+desc.getValue(1));}
			}
			if(register)register(this);
		}catch(e){
			console.log("GUI.text: "+e);
			return(null);
		}
	},
	
	//image{<name><src><mode:view|edit><size>}
	image:function(desc,register){
		try{
			this.NAME=desc.getValue(0);
			this.MODE=desc.getValue(2);
			let panel=document.createElement("div");
			panel.className="image_panel";
			panel.style.minWidth=desc.getValue(3)+"em";
			panel.style.maxWidth=panel.style.minWidth;
			panel.style.minHeight=panel.style.minWidth;
			panel.style.maxHeight=panel.style.minWidth;
			this.IMAGE=document.createElement("img");
			panel.appendChild(this.IMAGE);
			this.IMAGE.className="image_image";
			this.IMAGE.style.maxWidth=panel.style.maxWidth;
			this.IMAGE.style.maxHeight=panel.style.maxHeight;
			this.IMAGE.src=desc.getValue(1);
			switch(this.MODE){
				case "view":{
					this.HTML=panel;
					break;
				}
				case "edit":{
					this.HTML=document.createElement("div");
					this.HTML.className="image";
					this.HTML.appendChild(panel);
					let button=document.createElement("input");
					this.HTML.appendChild(button);
					button.type="file";
					button.className="field_button";
					button.accept="image/*";
					button.onchange=GUI.image.prototype.onChange.bind(this);
					if(register)register(this);
					break;
				}
				default:{
					throw new Error("неизвестный параметр mode="+desc.getValue(2));
				}
			}
		}catch(e){
			console.log("GUI.image: "+e);
			return(null);
		}
	},
	
	//label{<text><border-width>}
	label:function(desc){
		try{
			this.HTML=document.createElement("div");
			this.HTML.className="label";
			this.HTML.style.borderWidth=desc.getValue(1)+"px";
			this.HTML.innerHTML=desc.getValue(0);
		}catch(e){
			console.log("GUI.label: "+e);
			return(null);
		}
	},
	
	//command{<title><type:b|t><COMMAND>}
	command:function(desc){
		try{
			this.COMMAND=desc.getChild(2);
			switch(desc.getValue(1)){
				case "b":{
					this.HTML=document.createElement("input");
					this.HTML.className="command_button";
					this.HTML.type="button";
					this.HTML.value=desc.getValue(0);
					break;
				}
				case "t":{
					this.HTML=document.createElement("div");
					this.HTML.className="command_text";
					this.HTML.textContent=desc.getValue(0);
					break;
				}
				default:{throw new Error("неизвестное значение type="+desc.getValue(1));}
			}
			this.HTML.onclick=GUI.command.prototype.onClick.bind(this);
		}catch(e){
			console.log("GUI.command: "+e);
			return(null);
		}
	},
	
	//catalog{<name><title><mode:view|select|edit><max-width><max-height><handler>}
	catalog:function(desc,register){
		try{
			this.NAME=desc.getValue(0);
			this.STATE={
				CURRENT:"0"
			};
			this.EDIT={};
			this.HTML=document.createElement("div");
			this.HTML.className="catalog";
			this.HTML.style.maxWidth=(+desc.getValue(3)===0?undefined:desc.getValue(3)+"em");
			this.TITLE=document.createElement("div");
			this.HTML.appendChild(this.TITLE);
			this.TITLE.className="field_title";
			this.TITLE.title=desc.getValue(1);
			this.PANEL=document.createElement("div");
			this.HTML.appendChild(this.PANEL);
			this.PANEL.className="catalog_panel";
			this.PANEL.style.maxHeight=(+desc.getValue(4)===0?undefined:desc.getValue(4)+"em");
			switch(desc.getValue(2)){
				case "view":{break;}
				case "select":{
					this.HANDLER=desc.getValue(5);
					this.STATE.SELECTED=null;
					//select bar
					let bar=document.createElement("div");
					this.HTML.appendChild(bar);
					bar.className="catalog_bar";
					let title=document.createElement("div");
					bar.appendChild(title);
					title.className="catalog_bar_title";
					title.textContent="Выбрана группа:";
					this.SELECTED=document.createElement("div");
					bar.appendChild(this.SELECTED);
					this.SELECTED.className="catalog_bar_value";
					//command bar
					let commandbar=document.createElement("div");
					this.HTML.appendChild(commandbar);
					commandbar.className="catalog_editbar";
					//up
					this.EDIT.UP=this.getEditButton("up","Вверх");
					if(this.EDIT.UP===null)throw new Error("ошибка построения командной панели");
					commandbar.appendChild(this.EDIT.UP);
					//clear
					this.EDIT.CLEAR=this.getEditButton("clear","Сбросить выделение");
					if(this.EDIT.CLEAR===null)throw new Error("ошибка построения командной панели");
					commandbar.appendChild(this.EDIT.CLEAR);
					if(register)register(this);
					break;
				}
				case "edit":{
					this.HANDLER=desc.getValue(5);
					this.STATE.SELECTED=null;
					this.STATE.CUTTED=null;
					//select bar
					let bar=document.createElement("div");
					this.HTML.appendChild(bar);
					bar.className="catalog_bar";
					let title=document.createElement("div");
					bar.appendChild(title);
					title.className="catalog_bar_title";
					title.textContent="Выбрана группа:";
					this.SELECTED=document.createElement("div");
					bar.appendChild(this.SELECTED);
					this.SELECTED.className="catalog_bar_value";
					//cutted bar
					bar=document.createElement("div");
					this.HTML.appendChild(bar);
					bar.className="catalog_bar";
					title=document.createElement("div");
					bar.appendChild(title);
					title.className="catalog_bar_title";
					title.textContent="Группа в памяти:";
					this.CUTTED=document.createElement("div");
					bar.appendChild(this.CUTTED);
					this.CUTTED.className="catalog_bar_value";
					//new name bar
					bar=document.createElement("div");
					this.HTML.appendChild(bar);
					bar.className="catalog_bar";
					title=document.createElement("div");
					bar.appendChild(title);
					title.className="catalog_bar_title";
					title.textContent="Новое имя группы:";
					this.NEW_NAME=document.createElement("div");
					bar.appendChild(this.NEW_NAME);
					this.NEW_NAME.contentEditable=true;
					this.NEW_NAME.className="catalog_bar_input";
					this.NEW_NAME.textContent="";
					this.NEW_NAME.oninput=this.checkState.bind(this);
					//command bar
					let commandbar=document.createElement("div");
					this.HTML.appendChild(commandbar);
					commandbar.className="catalog_editbar";
					//up
					this.EDIT.UP=this.getEditButton("up","Вверх");
					if(this.EDIT.UP===null)throw new Error("ошибка построения командной панели");
					commandbar.appendChild(this.EDIT.UP);
					//create
					this.EDIT.CREATE=this.getEditButton("create","Создать");
					if(this.EDIT.CREATE===null)throw new Error("ошибка построения командной панели");
					commandbar.appendChild(this.EDIT.CREATE);
					//cut
					this.EDIT.CUT=this.getEditButton("cut","Вырезать");
					if(this.EDIT.CUT===null)throw new Error("ошибка построения командной панели");
					commandbar.appendChild(this.EDIT.CUT);
					//paste
					this.EDIT.PASTE=this.getEditButton("paste","Вставить");
					if(this.EDIT.PASTE===null)throw new Error("ошибка построения командной панели");
					commandbar.appendChild(this.EDIT.PASTE);
					//remove
					this.EDIT.REMOVE=this.getEditButton("remove","Удалить");
					if(this.EDIT.REMOVE===null)throw new Error("ошибка построения командной панели");
					commandbar.appendChild(this.EDIT.REMOVE);
					//rename
					this.EDIT.RENAME=this.getEditButton("rename","Переименовать");
					if(this.EDIT.RENAME===null)throw new Error("ошибка построения командной панели");
					commandbar.appendChild(this.EDIT.RENAME);
					this.checkState();
					break;
				}
				default:{throw new Error("неизвестный параметр mode="+desc.getValue(2));}
			}
			this.get.bind(this)({target:{ID:"0"}});
		}catch(e){
			console.log("GUI.catalog: "+e);
			return(null);
		}
	},
	
	//parameter{<name><value>}
	parameter:function(desc,register){
		try{
			this.NAME=desc.getValue(0);
			this.VALUE=desc.getValue(1);
			this.HTML=undefined;
			if(register)register(this);
		}catch(e){
			console.log("GUI.parameter: "+e);
			return(null);
		}
	},
	
	//table{<name><title><mode:view|singleselect|multiselect><border-width><hline-width><vline-width><min-width><max-width><min-height><max-height>(hide|show){HEADER}{DATA}}
	//HEADER:={<title><width><halign><valign><visible><datable><type>}{...}...
	//DATA:={<value_1.1><value_1.2>...}{<value_2.1><value_2.2>...}...
	/*
	table:function(desc,register){
		try{
			this.NAME=desc.getValue(0);
			this.MODE=desc.getValue(2);
			this.HEADERED=false;
			this.HTML=document.createElement("div");
			this.HTML.className="table";
			if(desc.getValue(1)!==""){
				let title=document.createElement("div");
				this.HTML.appendChild(title);
				title.className="field_title";
				title.textContent=desc.getValue(1);
			}
			let panel=document.createElement("div");
			this.HTML.appendChild(panel);
			panel.className="table_panel";
			panel.style.minWidth=(+desc.getValue(3)===0?undefined:desc.getValue(3)+"em");
			panel.style.maxWidth=(+desc.getValue(4)===0?undefined:desc.getValue(4)+"em");
			panel.style.minHeight=(+desc.getValue(5)===0?undefined:desc.getValue(5)+"em");
			panel.style.maxHeight=(+desc.getValue(6)===0?undefined:desc.getValue(6)+"em");
			this.TABLE=document.createElement("table");
			panel.appendChild(this.TABLE);
			this.TABLE.className="table_table";
			let header=desc.getChild(7);
			let tr,td,sel;
			if(header.getChild(0).getValue(0)!==""){
				this.HEADERED=true;
				tr=document.createElement("tr");
				this.TABLE.appendChild(tr);
				tr.className="table_header";
				if(this.MODE==="singleselect"){
					td=document.createElement("td");
					tr.appendChild(td);
					td.className="table_cell";
				}
				if(this.MODE==="multiselect"){
					td=document.createElement("td");
					tr.appendChild(td);
					td.className="table_cell";
					sel=document.createElement("input");
					td.appendChild(sel);
					sel.type="checkbox";
					sel.onchange=this.selectAll.bind(this);
				}
				header.getChildren().forEach(v=>{
					td=document.createElement("td");
					tr.appendChild(td);
					td.className="table_cell";
					td.textContent=v.getValue(0);
					td.style.minWidth=v.getValue(1)+"em";
					switch(v.getValue(2)){
						case "l":{td.style.textAlign="left";break;}
						case "c":{td.style.textAlign="center";break;}
						case "r":{td.style.textAlign="right";break;}
						case "j":{td.style.textAlign="justify";break;}
						default:{throw new Error("неизвестный параметр halign="+v.getValue(2));}
					}
					switch(v.getValue(3)){
						case "t":{td.style.verticalAlign="top";break;}
						case "m":{td.style.verticalAlign="middle";break;}
						case "b":{td.style.verticalAlign="bottom";break;}
						default:{throw new Error("неизвестный параметр valign="+v.getValue(3));}
					}
				});
			}
			
			desc.getChild(8).getChildren().forEach((row,rpos)=>{
				tr=document.createElement("tr");
				this.TABLE.appendChild(tr);
				if(this.MODE==="singleselect" || this.MODE==="multiselect"){
					td=document.createElement("td");
					tr.appendChild(td);
					td.className="table_cell";
					sel=document.createElement("input");
					td.appendChild(sel);
					sel.type="checkbox";
					sel.onchange=this.selectRow.bind(this);
				}
				row.getChildren().forEach((cell,cpos)=>{
					td=document.createElement("td");
					tr.appendChild(td);
					td.textContent=cell.getValue();
					if(rpos===0)td.style.minWidth=header.getChild(cpos).getValue(1)+"em";
					switch(header.getChild(cpos).getValue(2)){
						case "l":{td.style.textAlign="left";break;}
						case "c":{td.style.textAlign="center";break;}
						case "r":{td.style.textAlign="right";break;}
						case "j":{td.style.textAlign="justify";break;}
					}
					switch(header.getChild(cpos).getValue(3)){
						case "t":{td.style.verticalAlign="top";break;}
						case "m":{td.style.verticalAlign="middle";break;}
						case "b":{td.style.verticalAlign="bottom";break;}
					}
					if(cell.getChild(0)){
						td.COMMAND=cell.getChild(0);
						td.onclick=this.cellClick;
						td.style.cursor="pointer";
						td.style.textDecoration="underline";
					}
				});
			});
			
			if(register && (desc.getValue(2)==="ss" || desc.getValue(2)==="ms"))register(this);
		}catch(e){
			console.log("GUI.table: "+e);
			return(null);
		}
	},
	*/
	table:function(desc,register){
		try{
			this.NAME=desc.getValue(0);
			this.MODE=desc.getValue(2);
			this.OFFSET=this.MODE==="view"?0:
				this.MODE==="singleselect"?1:
					this.MODE==="multiselect"?1:0;
			this.SORTORDER=[];
			this.BORDER={
				HORIZONTAL:desc.getValue(4)+"px",
				VERTICAL:desc.getValue(5)+"px"
			};
			this.HTML=document.createElement("div");
			this.HTML.className="table";
			if(desc.getValue(1)!==""){
				let title=document.createElement("div");
				this.HTML.appendChild(title);
				title.className="field_title";
				title.textContent=desc.getValue(1);
			}
			let panel=document.createElement("div");
			this.HTML.appendChild(panel);
			panel.className="table_panel";
			panel.style.minWidth=(+desc.getValue(6)===0?undefined:desc.getValue(6)+"em");
			panel.style.maxWidth=(+desc.getValue(7)===0?undefined:desc.getValue(7)+"em");
			panel.style.minHeight=(+desc.getValue(8)===0?undefined:desc.getValue(8)+"em");
			panel.style.maxHeight=(+desc.getValue(9)===0?undefined:desc.getValue(9)+"em");
			this.TABLE=document.createElement("table");
			panel.appendChild(this.TABLE);
			this.TABLE.className="table_table";
			this.TABLE.style.borderTopWidth=desc.getValue(3)+"px";
			this.TABLE.style.borderRightWidth=this.TABLE.style.borderTopWidth;
			this.TABLE.style.borderBottomWidth=this.TABLE.style.borderTopWidth;
			this.TABLE.style.borderLeftWidth=this.TABLE.style.borderTopWidth;
			this.createHeader(desc.getChild(10));
			this.createData(desc.getChild(11));
			this.createRows();
			this.fillTable();
			if(register)register(this);
		}catch(e){
			console.log("GUI.table: "+e);
			return(null);
		}
	},
	
	//list{<name><title><grow><min-width><max-width><value>{OPTIONS}}
	//OPTIONS:=<value_1>{<title_1>}...
	list:function(desc,register){
		try{
			this.NAME=desc.getValue(0);
			this.HTML=document.createElement("div");
			this.HTML.className="list";
			this.HTML.style.flexGrow=desc.getValue(2);
			if(desc.getValue(1)!==""){
				let title=document.createElement("div");
				this.HTML.appendChild(title);
				title.className="field_title";
				title.textContent=desc.getValue(1);
			}
			this.LIST=document.createElement("select");
			this.HTML.appendChild(this.LIST);
			this.LIST.className="list_list";
			this.LIST.style.minWidth=(+desc.getValue(3)===0?undefined:desc.getValue(3)+"em");
			this.LIST.style.maxWidth=(+desc.getValue(4)===0?undefined:desc.getValue(4)+"em");
			let opt;
			desc.getChild(6).getChildren().forEach(v=>{
				opt=document.createElement("option");
				opt.value=v.getValue();
				opt.textContent=v.getValue(0);
				this.LIST.appendChild(opt);
			});
			this.LIST.value=desc.getValue(5);
			if(register)register(this);
		}catch(e){
			console.log("GUI.list: "+e);
			return(null);
		}
	},
	
	//form{<title><layout><border-width><min-width><max-width><min-height><max-height>{COMMANDS}{FIELDS}}
	//COMMANDS:=<command_1>{title_1}...
	form:function(desc){
		try{
			this.FIELDS=[];
			this.HTML=document.createElement("div");
			this.HTML.className="form";
			this.HTML.style.borderWidth=desc.getValue(2)+"px";
			this.HTML.style.minWidth=(+desc.getValue(3)===0?undefined:(desc.getValue(3)+"em"));
			this.HTML.style.maxWidth=(+desc.getValue(4)===0?undefined:(desc.getValue(4)+"em"));
			this.HTML.style.minHeight=(+desc.getValue(5)===0?undefined:(desc.getValue(5)+"em"));
			this.HTML.style.maxHeight=(+desc.getValue(6)===0?undefined:(desc.getValue(6)+"em"));
			if(desc.getValue(0)!==""){
				let title=document.createElement("div");
				this.HTML.appendChild(title);
				title.className="form_title";
				title.textContent=desc.getValue(0);
			}
			let work_panel=document.createElement("div");
			this.HTML.appendChild(work_panel);
			work_panel.className="form_panel";
			switch(desc.getValue(1)){
				case "h":{work_panel.style.flexDirection="row";break;}
				case "v":{work_panel.style.flexDirection="column";break;}
				default:{throw new Error("неизвестный параметр direction="+desc.getValue(1));}
			}
			let combar=document.createElement("div");
			this.HTML.appendChild(combar);
			combar.className="form_commandbar";
			let button;
			desc.getChild(7).getChildren().forEach(v=>{
				button=document.createElement("input");
				combar.appendChild(button);
				button.className="form_button";
				button.type="button";
				button.value=v.getValue(0);
				button.COMMAND=v.getValue();
				button.FORM=this;
				button.onclick=GUI.form.prototype.onClick;
			});
			let comp;
			desc.getChild(8).getChildren().forEach(v=>{
				if(GUI[v.getValue()]){
					comp=new GUI[v.getValue()](v,GUI.form.prototype.fieldRegister.bind(this)).HTML;
					if(comp)work_panel.appendChild(comp);
				}
			});
		}catch(e){
			console.log("GUI.form: "+e);
			return(null);
		}
	},
	
	//panel{<title><direction><border-width><min-width><max-width><min-height><max-height>{CHILDREN}}
	panel:function(desc,register){
		try{
			this.HTML=document.createElement("div");
			this.HTML.className="panel";
			if(desc.getValue(0)!==""){
				let title=document.createElement("div");
				this.HTML.appendChild(title);
				title.className="panel_title";
				title.textContent=desc.getValue(0);
			}
			let panelbox=document.createElement("div");
			this.HTML.appendChild(panelbox);
			panelbox.className="panel_box";
			switch(desc.getValue(1)){
				case "h":{
					panelbox.style.flexDirection="row";
					panelbox.style.alignItems="start";
					break;
				}
				case "v":{
					panelbox.style.flexDirection="column";
					panelbox.style.alignItems="stretch";
					break;
				}
				default:{throw new Error("неизвестное значение direction="+desc.getValue(1));}
			}
			this.HTML.style.borderWidth=desc.getValue(2)+"px";
			this.HTML.style.minWidth=(+desc.getValue(3)===0?undefined:desc.getValue(3)+"em");
			this.HTML.style.maxWidth=(+desc.getValue(4)===0?undefined:desc.getValue(4)+"em");
			this.HTML.style.minHeight=(+desc.getValue(5)===0?undefined:desc.getValue(5)+"em");
			this.HTML.style.maxHeight=(+desc.getValue(6)===0?undefined:desc.getValue(6)+"em");
			desc.getChild(7).getChildren().forEach(v=>{
				if(GUI[v.getValue()]){
					comp=new GUI[v.getValue()](v,register);
					if(comp && comp.HTML)panelbox.appendChild(comp.HTML);
				}
			});
		}catch(e){
			console.log("GUI.panel: "+e);
			return(null);
		}
	},
	
	//page{<title><x-count><y-count><total_objects><command><params{PARAMETERS}>}
	page:function(desc,register){
		this.XCOUNT=+desc.getValue(1);
		this.YCOUNT=+desc.getValue(2);
		this.TOTAL=+desc.getValue(3);
		this.COMMAND=desc.getValue(4);
		this.PARAMETERS=desc.getChild(5);
		this.HTML=document.createElement("div");
		this.HTML.className="page";
		if(desc.getValue(0)!==""){
			let title=document.createElement("div");
			this.HTML.appendChild(title);
			title.className="field_title";
			title.textContent=desc.getValue(0);
		}
		this.GRID=document.createElement("div");
		this.HTML.appendChild(this.GRID);
		this.GRID.className="page_grid";
		this.GRID.style.gridTemplateColumns="repeat("+this.XCOUNT+", minmax(2em,1fr))";
		this.PAGEBAR=document.createElement("div");
		this.HTML.appendChild(this.PAGEBAR);
		this.PAGEBAR.className="page_pagebar";
		this.pageClick({target:{NUMBER:1}});
	},
	
	//tree{<minWidth><maxWidth><minHeight><maxHeight>{<SUBTREE>...}}
	//SUBTREE:=b{<title{SUBTREES}>} | l{<title{<COMMAND>}>}
	tree:function(data){
		try{
			let desc=(data.constructor===String?Structure.fromString(data):data);
			this.HTML=document.createElement("div");
			this.HTML.className="tree";
			this.HTML.style.minWidth=(+desc.getValue(0)===0?undefined:desc.getValue(0)+"em");
			this.HTML.style.maxWidth=(+desc.getValue(1)===0?undefined:desc.getValue(1)+"em");
			this.HTML.style.minHeight=(+desc.getValue(2)===0?undefined:desc.getValue(2)+"em");
			this.HTML.style.maxHeight=(+desc.getValue(3)===0?undefined:desc.getValue(3)+"em");
			desc.getChild(4).getChildren().forEach(v=>createSub(v,this.HTML));
			
			function createSub(sub,panel){
				try{
					let subtree=document.createElement("div");
					panel.appendChild(subtree);
					subtree.className="tree_subtree";
					if(sub.getValue()==="l"){
						let title=document.createElement("div");
						subtree.appendChild(title);
						title.className="tree_leaf";
						title.textContent=sub.getValue(0);
						title.COMMAND=sub.getChild(0).getChild(0);
						title.onclick=GUI.tree.prototype.onLeafClick.bind(title);
					}else if(sub.getValue()==="b"){
						let branch=document.createElement("div");
						panel.appendChild(branch);
						branch.className="tree_branch";
						branch.TITLE=document.createElement("div");
						branch.appendChild(branch.TITLE);
						branch.TITLE.className="tree_branch_title";
						branch.TITLE.textContent="► "+sub.getValue(0);
						branch.TITLE.onclick=GUI.tree.prototype.onBranchClick.bind(branch);
						branch.PANEL=document.createElement("div");
						branch.appendChild(branch.PANEL);
						branch.PANEL.className="tree_branch_panel";
						branch.PANEL.style.display="none";
						sub.getChild(0).getChildren().forEach(v=>createSub(v,branch.PANEL));
					}else{
						throw new Error("неизвестный тип узла");
					}
				}catch(ee){
						console.log("GUI.tree.createSub: "+ee);
				}
			};
		}catch(e){
			console.log("GUI.tree: "+e);
			return(null);
		}
	},

	vspace:function(desc){
		try{
			this.HTML=document.createElement("div");
			this.HTML.style.minHeight=desc.getValue(0)+"em";
			this.HTML.style.maxHeight=this.HTML.style.minHeight;
		}catch(e){
			console.log("GUI.vspace: "+e);
		}
	},

	
	product_short:function(desc){
		try{
			this.HTML=document.createElement("div");
			this.HTML.className="product_short";
			let image=new Image();
			this.HTML.appendChild(image);
			image.className="product_short_image";
			image.src="http://"+CONNECTOR.HOST+":"+CONNECTOR.PORT+"/resources/"+desc.getValue(0)+".jpg";
			let name=document.createElement("div");
			this.HTML.appendChild(name);
			name.className="product_short_name";
			name.textContent=desc.getValue(1);
			name.ID=desc.getValue(0);
			name.onclick=this.onClick;
			let price=document.createElement("div");
			this.HTML.appendChild(price);
			price.className="product_short_price";
			price.textContent=desc.getValue(2)+" руб";
			let cart=document.createElement("input");
			this.HTML.appendChild(cart);
			cart.type="button";
			cart.className="form_button";
			cart.value="В корзину";
			cart.onclick=this.toCart.bind(desc);
			cart.style.justifySelf="flex-end";
		}catch(e){
			console.log("GUI.product_short: "+e);
		}
	},
	
	//product_view{<id><name><description><price><status_name><catalog_path><can_edit>}
	product_view:function(desc){
		try{
			this.HTML=document.createElement("div");
			this.HTML.className="product_view";
			let path=document.createElement("div");
			this.HTML.appendChild(path);
			path.className="product_view_path";
			path.textContent=desc.getValue(5);
			let img=new Image();
			this.HTML.appendChild(img);
			img.src="http://"+CONNECTOR.HOST+":"+CONNECTOR.PORT+"/resources/"+desc.getValue(0)+".jpg";
			img.className="product_view_image";
			let name=document.createElement("div");
			this.HTML.appendChild(name);
			name.className="product_view_panel";
			let title=document.createElement("div");
			name.appendChild(title);
			title.className="product_view_title";
			title.textContent="Наименование:";
			let text=document.createElement("div");
			name.appendChild(text);
			text.className="product_view_text";
			text.textContent=desc.getValue(1);
			let description=document.createElement("div");
			this.HTML.appendChild(description);
			description.className="product_view_panel";
			title=document.createElement("div");
			description.appendChild(title);
			title.className="product_view_title";
			title.textContent="Описание:";
			text=document.createElement("div");
			description.appendChild(text);
			text.className="product_view_text";
			text.textContent=desc.getValue(2);
			let price=document.createElement("div");
			this.HTML.appendChild(price);
			price.className="product_view_price";
			price.textContent="Цена: "+desc.getValue(3)+" руб";
			let bar=document.createElement("div");
			this.HTML.appendChild(bar);
			bar.className="product_view_bar";
			let cart=document.createElement("input");
			bar.appendChild(cart);
			cart.className="form_button";
			cart.type="button";
			cart.value="В корзину";
			cart.onclick=GUI.product_short.prototype.toCart.bind(new Structure("").addChild(desc.getValue(0)).addChild(desc.getValue(1)).addChild(desc.getValue(3)));
			if(desc.getValue(6)==="1"){
				let edit=document.createElement("input");
				bar.appendChild(edit);
				edit.className="form_button";
				edit.type="button";
				edit.value="Изменить";
				edit.onclick=function(){
					CONNECTOR.send(new Structure("edit_product").addChild(desc.getValue(0)).toString());
				};
				let remove=document.createElement("input");
				bar.appendChild(remove);
				remove.className="form_button";
				remove.type="button";
				remove.value="Удалить";
				remove.onclick=function(){
					CONNECTOR.send(new Structure("remove_product").addChild(desc.getValue(0)).toString());
				};
			}
		}catch(e){
			console.log("GUI.product_view: "+e);
		}
	},
	
};

GUI.string.prototype={
	
	onInput:function(e){
		if(this.CANCEL)this.CANCEL.disabled=false;
		if(this.REGEXP==="")return;
		if(this.INPUT.value.match(this.REGEXP)===null){
			this.INPUT.style.backgroundColor="red";
		}else{
			this.INPUT.style.backgroundColor="white";
		}
	},
	
	onCancel:function(){
		this.INPUT.value=this.VALUE;
		GUI.string.prototype.onInput.bind(this)();
		this.CANCEL.disabled=true;
	},
	
	getValue:function(){
		if(this.INPUT.value.match(this.REGEXP)!==null){
			return(new Structure(this.NAME).addChild(this.INPUT.value));
		}else{
			return(null);
		}
	}

};

GUI.text.prototype={
	
	onInput:function(e){
		if(this.CANCEL)this.CANCEL.disabled=false;
		if(this.REGEXP===""){
			return;
		}
		if(this.INPUT.textContent.match(this.REGEXP)===null){
			this.INPUT.style.backgroundColor="red";
		}else{
			this.INPUT.style.backgroundColor="white";
		}
	},
	
	onCancel:function(){
		this.INPUT.textContent=this.VALUE;
		GUI.text.prototype.onInput.bind(this)();
		this.CANCEL.disabled=true;
	},
	
	getValue:function(){
		if(this.INPUT.textContent.match(this.REGEXP)!==null){
			return(new Structure(this.NAME).addChild(this.INPUT.textContent));
		}else{
			return(null);
		}
	}

};

GUI.command.prototype={
	
	onClick:function(e){
		CONNECTOR.send(this.COMMAND.toString());
	},
	
};

GUI.parameter.prototype={
	
	getValue:function(){
		return(new Structure(this.NAME).addChild(this.VALUE));
	}
	
};

GUI.label.prototype={
	
	getValue:function(){
		return(undefined);
	}
	
};

GUI.image.prototype={
	
	onChange:function(e){
		let reader=new FileReader();
		reader.onload=(function(e){
			this.IMAGE.src=e.target.result;
		}).bind(this);
		reader.onerror=function(e){
			console.log("GUI.image.prototype.onChange: "+e);
		}
		reader.readAsDataURL(e.target.files[0]);
	},
	
	getValue:function(){
		let src=this.IMAGE.src;
		let pos=src.indexOf(",")+1;
		return(new Structure(this.NAME).addChild(src.substring(pos)));
	},
	
};

GUI.form.prototype={
	
	onClick:function(){
		let res=new Structure(this.COMMAND);
		let f_data;
		this.FORM.FIELDS.forEach(v=>{
			f_data=v.getValue();
			if(f_data===null)return;
			if(f_data!==undefined)res.addChild(f_data);
		});
		CONNECTOR.send(res.toString());
		this.FORM.HTML.remove();
	},
	
	fieldRegister:function(comp){
		this.FIELDS.push(comp);
	},
	
};

GUI.list.prototype={
	
	getValue:function(){
		return(new Structure(this.NAME).addChild(this.LIST.value));
	}
	
};

GUI.table.prototype={
	
	HALIGN:{
		"l":"left",
		"c":"center",
		"r":"right",
		"j":"justify"
	},
	
	VALIGN:{
		"t":"top",
		"m":"middle",
		"b":"bottom"
	},
	
	createHeader:function(header){
		try{
			let tr=document.createElement("tr");
			this.TABLE.appendChild(tr);
			tr.className="table_header";
			this.HEADER=[];
			this.DATABLE=[];
			let h_c;
			switch(this.MODE){
				case "view":break;
				case "singleselect":{
					h_c={
						TITLE:"",
						WIDTH:"0",
						HALIGN:"center",
						VALIGN:"middle",
						VISIBLE:true,
						TYPE:"c"
					};
					this.HEADER.push(h_c);
					break;
				}
				case "multiselect":{
					h_c={
						TITLE:"",
						WIDTH:"0",
						HALIGN:"center",
						VALIGN:"middle",
						VISIBLE:true,
						TYPE:"s"
					};
					this.HEADER.push(h_c);
					break;
				}
			}
			header.getChildren().forEach((v,i)=>{
				h_c={
					TITLE:v.getValue(0),
					WIDTH:v.getValue(1)+"em",
					HALIGN:this.HALIGN[v.getValue(2)],
					VALIGN:this.VALIGN[v.getValue(3)],
					VISIBLE:(v.getValue(4)==="1"),
					TYPE:v.getValue(6)
				};
				this.HEADER.push(h_c);
				if(v.getValue(5)==="1")this.DATABLE.push(i);
			});
			this.HEADERED=header.getValue()==="show";
			if(this.HEADERED){
				let td;
				this.HEADER.forEach((v,i)=>{
					td=document.createElement("td");
					tr.appendChild(td);
					td.className="table_header_cell";
					td.style.width=v.WIDTH+"em";
					td.style.textAlign=v.HALIGN;
					td.style.verticalAlign=v.VALIGN;
					td.style.borderTopWidth=this.BORDER.HORIZONTAL;
					td.style.borderBottomWidth=td.style.borderTopWidth;
					td.style.borderLeftWidth=this.BORDER.VERTICAL;
					td.style.borderRightWidth=td.style.borderLeftWidth;
					td.style.display=(v.VISIBLE?"table-cell":"none");
					switch(v.TYPE){
						case "n":
						case "t":
						case "c":{
							td.textContent=v.TITLE;
							break;
						}
						case "s":{
							let sel=document.createElement("input");
							td.appendChild(sel);
							sel.type="checkbox";
							sel.onclick=this.selectAll.bind(this);
						}
					}
					td.INDEX=i;
					td.onclick=this.headerClick.bind(this);
				});
			}
		}catch(e){
			console.log("GUI.table.prototype.createHeader: "+e);
		}
	},
	
	createData:function(data){
		try{
			this.DATA=[];
			let row;
			data.getChildren().forEach(r=>{
				row=[];
				switch(this.MODE){
					case "view":break;
					case "singleselect":
					case "multiselect":{
						row.push("0");
						break;
					}
				}
				r.getChildren().forEach(v=>{
					row.push(v.getValue());
				});
				this.DATA.push(row);
			});
		}catch(e){
			console.log("GUI.table.prototype.createData: "+e);
		}
	},
	
	createRows:function(){
		try{
			let tr,td,sel;
			for(let i=0;i<this.DATA.length;i++){
				tr=document.createElement("tr");
				this.TABLE.appendChild(tr);
				tr.INDEX=i;
				this.HEADER.forEach(v=>{
					td=document.createElement("td");
					tr.appendChild(td);
					td.className="table_cell";
					td.style.borderTopWidth=this.BORDER.HORIZONTAL;
					td.style.borderBottomWidth=this.BORDER.HORIZONTAL;
					td.style.borderLeftWidth=this.BORDER.VERTICAL;
					td.style.borderRightWidth=this.BORDER.VERTICAL;
					td.style.textAlign=v.HALIGN;
					td.style.verticalAlign=v.VALIGN;
					td.style.display=(v.VISIBLE?"table-cell":"none");
					if(i===0)td.style.width=v.WIDTH;
					switch(v.TYPE){
						case "t":
						case "n":break;
						case "c":
						case "s":{
							sel=document.createElement("input");
							td.appendChild(sel);
							sel.type="checkbox";
							sel.onclick=this.selectRow.bind(this);
						}
					}
				});
			}
		}catch(e){
			console.log("GUI.table.prototype.createRows: "+e);
		}
	},
	
	fillTable:function(){
		try{
			let row=this.TABLE.firstChild;
			if(this.HEADERED)row=row.nextSibling;
			let cell;
			this.DATA.forEach(v=>{
				cell=row.firstChild;
				v.forEach((d,i)=>{
					switch(this.HEADER[i].TYPE){
						case "n":
						case "t":{
							cell.textContent=d;
							break;
						}
						case "c":
						case "s":{
							cell.firstChild.checked=(d==="1");
							break;
						}
					}
					cell=cell.nextSibling;
				});
				row=row.nextSibling;
			});
		}catch(e){
			console.log("GUI.table.prototype.fillTable: "+e);
		}
	},
	
	selectAll:function(e){
		try{
			e.stopPropagation();
			this.TABLE.childNodes.forEach((v,i)=>{
				if(i===0)return;
				v.firstChild.firstChild.checked=e.target.checked;
			});
			this.DATA.forEach(v=>{v[0]=(e.target.checked?"1":"0");});
		}catch(e){
			console.log("GUI.table.prototype.selectAll: "+e);
		}
	},
	
	headerClick:function(e){
		try{
			if(e.ctrlKey){
				this.SORTORDER=this.SORTORDER.filter(v=>v!==e.target.INDEX);
				this.SORTORDER.push(e.target.INDEX);
			}else{
				this.SORTORDER=[e.target.INDEX];
			}
			this.sortTable();
			this.fillTable();
		}catch(e){
			console.log("GUI.table.prototype.headerClick: "+e);
		}
	},
	
	sortTable:function(){
		try{
			let offset;
			this.DATA.sort((a,b)=>{
				for(let i=0;i<this.SORTORDER.length;i++){
					if(this.HEADER[this.SORTORDER[i]].TYPE==="n"){
						if(+a[this.SORTORDER[i]]<+b[this.SORTORDER[i]])return(-1);
						if(+a[this.SORTORDER[i]]>+b[this.SORTORDER[i]])return(1);
					}else{
						if(a[this.SORTORDER[i]]<b[this.SORTORDER[i]])return(-1);
						if(a[this.SORTORDER[i]]>b[this.SORTORDER[i]])return(1);
					}
				}
				return(0);
			});
		}catch(e){
			console.log("GUI.table.prototype.sortTable: "+e);
		}
	},
	
	selectRow:function(e){
		try{
			let checked=e.target.checked;
			switch(this.MODE){
				case "view":break;
				case "singleselect":{
					if(this.HEADERED){
						this.TABLE.childNodes.forEach((v,i)=>{
							if(i===0)return;
							v.firstChild.firstChild.checked=false;
						});
					}else{
						this.TABLE.childNodes.forEach(v=>{
							v.firstChild.firstChild.checked=false;
						});
					}
					this.DATA.forEach(v=>{
						v[0]="0";
					});
					break;
				}
				case "multiselect":{
					this.TABLE.firstChild.firstChild.firstChild.checked=false;
					break;
				}
			}
			e.target.checked=checked;
			let pos=e.target.parentElement.parentElement.INDEX;
			this.DATA[pos][0]=checked?"1":"0";
		}catch(e){
			console.log("GUI.table.prototype.selectRow: "+e);
		}
	},
	
	getValue:function(){
		try{
			if(this.MODE==="view")return;
			let res=new Structure(this.NAME);
			let row;
			this.DATA.forEach(v=>{
				if(v[0]==="0")return;
				let row=new Structure("");
				this.DATABLE.forEach(d=>{
					row.addChild(v[d+1]);
				});
				res.addChild(row);
			});
			return(res);
		}catch(e){
			console.log("GUI.table.prototype.getValue: "+e);
			return(null);
		}
	}
};

GUI.page.prototype={
	
	createPagebar:function(page_num){
		try{
		while(this.PAGEBAR.firstChild)this.PAGEBAR.firstChild.remove();
		let pages=Math.floor(this.TOTAL/(this.XCOUNT*this.YCOUNT))+(this.TOTAL%(this.XCOUNT*this.YCOUNT)===0?0:1);
		let num;
		if((pages-page_num)>8){
			if(page_num>3){
				num=document.createElement("div");
				this.PAGEBAR.appendChild(num);
				num.className="page_number";
				num.textContent="<<";
				num.NUMBER=1;
				num.onclick=this.pageClick.bind(this);
			}
			for(let i=(page_num>2?page_num-2:1);i<=(page_num>2?page_num+2:5);i++){
				num=document.createElement("div");
				this.PAGEBAR.appendChild(num);
				num.className="page_number";
				num.textContent=""+i;
				if(i===page_num)num.style.backgroundColor="silver";
				num.NUMBER=i;
				num.onclick=this.pageClick.bind(this);
			}
			let dots=document.createElement("div");
			this.PAGEBAR.appendChild(dots);
			dots.className="page_dots";
			dots.textContent="...";
			for(let i=pages-4;i<=pages;i++){
				num=document.createElement("div");
				this.PAGEBAR.appendChild(num);
				num.className="page_number";
				num.textContent=""+i;
				num.NUMBER=i;
				num.onclick=this.pageClick.bind(this);
			}
		}else{
			if(pages>10){
				num=document.createElement("div");
				this.PAGEBAR.appendChild(num);
				num.className="page_number";
				num.textContent="<<";
				num.NUMBER=1;
				num.onclick=this.pageClick.bind(this);
			}
			for(let i=(pages<10?1:pages-9);i<=pages;i++){
				num=document.createElement("div");
				this.PAGEBAR.appendChild(num);
				num.className="page_number";
				num.textContent=""+i;
				if(i===page_num)num.style.backgroundColor="silver";
				num.NUMBER=i;
				num.onclick=this.pageClick.bind(this);
			}
		}
		}catch(e){
			console.log("GUI.page.prototype.createPagebar: "+e);
		}
	},
	
	setPage:function(desc){
		try{
			desc=(desc.constructor===String?Structure.fromString(desc):desc);
			while(this.GRID.firstChild)this.GRID.firstChild.remove();
			let x=1,y=1,html;
			desc.getChildren().forEach(comp=>{
				if(GUI[comp.getValue()]){
					html=new GUI[comp.getValue()](comp).HTML;
					if(html){
						this.GRID.appendChild(html);
						html.style.gridRowStart=y+"";
						html.style.gridRowEnd=(y+1)+"";
						html.style.gridColumnStart=x+"";
						html.style.gridColumnEnd=(x+1)+"";
						x++;
						if(x>this.XCOUNT){
							x=1;
							y++;
						}
					}
				}
			});
		}catch(e){
			console.log("GUI.page.prototype.setPage: "+e);
		}
	},
	
	pageClick:function(e){
		try{
			this.createPagebar(e.target.NUMBER);
			let count=this.XCOUNT*this.YCOUNT;
			let start=(e.target.NUMBER-1)*count;
			CONNECTOR.send(new Structure(this.COMMAND).addChild(""+start).addChild(""+count).addChild(this.PARAMETERS).toString(),this.setPage.bind(this));
		}catch(e){
			console.log("GUI.page.prototype.pageClick: "+e);
		}
	},
	
};

GUI.catalog.prototype={
	
	getEditButton:function(name,title){
		try{
			let e_button=document.createElement("input");
			e_button.type="button";
			e_button.className="field_button";
			e_button.NAME=name;
			e_button.value=title;
			e_button.onclick=this.process.bind(this);
			return(e_button);
		}catch(e){
			console.log("GUI.catalog.prototype.get_edit_button: "+e);
			return(null);
		}
	},
	
	getValue:function(){
		return(new Structure(this.NAME).addChild(this.STATE.SELECTED?this.STATE.SELECTED.ID:""));
	},
	
	clearPanel:function(){
		while(this.PANEL.firstChild)this.PANEL.firstChild.remove();
	},
	
	process:function(e){
		try{
			switch(e.target.NAME){
				case "cut":{
					if(this.STATE.CUTTED)this.STATE.CUTTED.style.display="block";
					this.STATE.CUTTED=this.STATE.SELECTED;
					this.STATE.CUTTED.style.display="none";
					this.checkState();
					return;
				}
				case "clear":{
					this.STATE.SELECTED.className="catalog_element";
					this.STATE.SELECTED=null;
					this.checkState();
					return;
				}
			}
			let res=new Structure(this.HANDLER)
				.addChild(new Structure(e.target.NAME)
					.addChild(this.STATE.CURRENT)
					.addChild(this.STATE.SELECTED?this.STATE.SELECTED.ID:"0")
					.addChild(this.STATE.CUTTED?this.STATE.CUTTED.ID:"0")
					.addChild(this.NEW_NAME?this.NEW_NAME.textContent:""));
			CONNECTOR.send(res.toString(),this.view.bind(this));
			switch(e.target.NAME){
				case "create":{
					this.NEW_NAME.textContent="";
					break;
				}
				case "paste":{
					this.STATE.CUTTED.style.display="block";
					this.STATE.CUTTED=null;
					break;
				}
				case "rename":{
					if(this.NEW_NAME)this.NEW_NAME.textContent="";
					break;
				}
			}
			this.checkState();
		}catch(err){
			console.log("GUI.catalog.prototype.process: "+err);
		}
	},

	checkState:function(){
		try{
			if(this.EDIT.UP)this.EDIT.UP.disabled=(this.STATE.CURRENT==="0");
			if(this.EDIT.CUT)this.EDIT.CUT.disabled=(this.STATE.SELECTED===null);
			if(this.EDIT.PASTE)this.EDIT.PASTE.disabled=(this.STATE.CUTTED===null);
			if(this.EDIT.REMOVE)this.EDIT.REMOVE.disabled=(this.STATE.SELECTED===null);
			if(this.EDIT.RENAME)this.EDIT.RENAME.disabled=(this.STATE.SELECTED===null || this.NEW_NAME.textContent==="");
			if(this.SELECTED)this.SELECTED.textContent=(this.STATE.SELECTED?this.STATE.SELECTED.textContent:"");
			if(this.CUTTED)this.CUTTED.textContent=(this.STATE.CUTTED?this.STATE.CUTTED.textContent:"");
			if(this.EDIT.CLEAR)this.EDIT.CLEAR.disabled=(this.STATE.SELECTED===null);
			if(this.EDIT.CREATE)this.EDIT.CREATE.disabled=(this.NEW_NAME.textContent==="");
		}catch(e){
			console.log("GUI.catalog.prototype.checkState: "+e);
		}
	},
		
	get:function(e){
		CONNECTOR.send(new Structure(this.HANDLER).addChild(new Structure("get").addChild(""+e.target.ID)).toString(),this.view.bind(this));
	},
	
	view:function(data){
		try{
			let desc=Structure.fromString(data);
			if(desc.getValue()==="")return;
			this.clearPanel();
			this.STATE.CURRENT=desc.getValue();
			this.TITLE.textContent=this.TITLE.title+": "+desc.getValue(0);
			desc.getChild(1).getChildren().forEach(v=>{
				let elt=document.createElement("div");
				this.PANEL.appendChild(elt);
				elt.className="catalog_element";
				elt.textContent=v.getValue(0);
				elt.ID=v.getValue();
				elt.onclick=this.onClick.bind(this);
				elt.ondblclick=this.onDblclick.bind(this);
			});
			this.STATE.CURRENT=desc.getValue();
			this.checkState();
		}catch(e){
			console.log("GUI.catalog.prototype.view: "+e);
		}
	},
	
	onClick:function(e){
		if(e.target.ID===this.STATE.SELECTED){
			this.STATE.SELECTED.className="catalog_element";
			this.STATE.SELECTED=null;
		}else{
			if(this.STATE.SELECTED)this.STATE.SELECTED.className="catalog_element";
			this.STATE.SELECTED=e.target;
			this.STATE.SELECTED.className="catalog_element catalog_selected";
		}
		this.checkState();
	},

	onDblclick:function(e){
		this.STATE.CURRENT=e.target.ID;
		this.STATE.SELECTED=null;
		this.get({target:{ID:this.STATE.CURRENT}});
		this.checkState();
	}
	
};

GUI.tree.prototype={
	
	onLeafClick:function(){
		CONNECTOR.send(this.COMMAND.toString());
	},
	
	onBranchClick:function(){
		if(this.TITLE.textContent.startsWith("►")){
			this.TITLE.textContent="▼"+this.TITLE.textContent.substring(1);
			this.PANEL.style.display="block";
		}else{
			this.TITLE.textContent="►"+this.TITLE.textContent.substring(1);
			this.PANEL.style.display="none";
		}
	}
	
};

GUI.product_short.prototype={

	onClick:function(e){
		try{
			CONNECTOR.send(new Structure("view_product_full").addChild(this.ID).toString());
		}catch(e){
			console.log("GUI.product_short.prototype.onClick: "+e);
		}
	},
	
	toCart:function(e){
console.log(this.toString());
		try{
			CART.addPosition(this.addChild("1"));
		}catch(e){
			console.log("GUI.product_short.prototype.toCart: "+e);
		}
	},

};