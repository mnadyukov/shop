
function Structure(value){
	this.parent=null;
	this.children=[];
	this.value=value===undefined?null:value;
};

Structure.fromString=function(str){
	let struct,current;
	let word="";
	for(let i=0;i<str.length;i++){
		if(str[i]==="\x01"){
			struct=new Structure(word);
			if(current)current.addChild(struct);
			current=struct;
			word="";
		}else if(str[i]==="\x02"){
			if(current.parent===null)break;
			current=current.parent;
		}else{
			word+=str[i];
		}
	}
	return(current);
};

Structure.prototype={
	
	setValue:function(value,index){
		if(Number.isNaN(+index)){
			if(value!==undefined)this.value=value;
		}else{
			if(index>=0 && index <this.children.length)this.getChild(index).setValue(value);
		}
		return(this);
	},
	
	getValue:function(index){
		if(index===null)return;
		if(index===undefined)return(this.value);
		return(this.children[index] && this.children[index].getValue());
	},
	
	addChild:function(child){
		if(child===undefined)return(this);
		if(child!==null && child.constructor===this.constructor){
			this.children.push(child);
			child.parent=this;
		};
		if(child.constructor===String)this.addChild(new Structure(child));
		return(this);
	},
	
	addUniqueChild(child){
		return(this.removeChildren(child).addChild(child));
	},
	
	getChild:function(index){
		return(this.children[index]);
	},
	
	setChild:function(child,index){
		try{
			if(child===null || child===undefined)throw new Error("дочерний узел null или undefined");
			if(child.constructor!==this.constructor)throw new Error("дочерний узел не Structure");
			if(index===null || index===undefined)throw new Error("индекс null или undefined");;
			if(index<0 || index>=this.children.length)throw new Error("индекс вне допустимого диапазона");
			this.children[index]=child;
			child.parent=this;
			return(this);
		}catch(e){
			console.log("Structure.prototype.setChild: "+e);
			return(this);
		}
	},
	
	removeChildren:function(child){
		if(child===undefined)return(this);
		if(child.constructor===this.constructor)this.children=this.children.filter(v=>v!==child);
		if(child.constructor===String)this.children=this.children.filter(v=>v.getValue()!==child);
		return(this);
	},
	
	getChildren:function(value){
		if(value===undefined)return(this.children);
		return(this.children.filter(v=>v.getValue()===value));
	},
	
	toString:function(){
		let str="";
		_toString(this);
		function _toString(struct){
			str+=struct.value+"\x01";
			struct.children.forEach(v=>_toString(v));
			str+="\x02";
		}
		return(str);
	},
	
	getParent:function(){
		return(this.parent);
	},
	
	sort:function(sortFunction){
		this.children.sort(sortFunction);
		return(this);
	}
	
};

let HEX={
	
	hexToBytes:function(hex){
		let bytes=[];
		for(let i=0;i<hex.length;i+=2)bytes[i>>1]=Number.parseInt(hex.substring(i,i+2),16);
		return(bytes);
	},
	
	bytesToHex:function(bytes){
		let hex="";
		for(let i=0;i<bytes.length;i++)hex+=(bytes[i]<0x10?"0":"")+bytes[i].toString(0x10);
		return(hex.toUpperCase());
	}
	
};