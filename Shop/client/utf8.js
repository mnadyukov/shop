
let UTF8={
	
	bytesToString:function(bytes){
		try{
			let str="",_str;
			let pos=0,b,num;
			while(true){
				if(pos>=bytes.length)break;
				switch(true){
					case (bytes[pos]&128)===0:{
						str+=String.fromCharCode(bytes[pos]);
						pos+=1;
						break;
					}
					case (bytes[pos]&224)===192:{
						str+=decodeURIComponent(
							"%"+bytes[pos].toString(16)+
							"%"+bytes[pos+1].toString(16)
						);
						pos+=2;
						break;
					}
					case (bytes[pos]&240)===224:{
						str+=decodeURIComponent(
							"%"+bytes[pos].toString(16)+
							"%"+bytes[pos+1].toString(16)+
							"%"+bytes[pos+2].toString(16)
						);
						pos+=3;
						break;
					}
					case (bytes[pos]&248)===240:{
						str+=decodeURIComponent(
							"%"+bytes[pos].toString(16)+
							"%"+bytes[pos+1].toString(16)+
							"%"+bytes[pos+2].toString(16)+
							"%"+bytes[pos+3].toString(16)
						);
						pos+=4;
						break;
					}
					default:throw new Error("wrong byte");
				}
			}
			return(str);
		}catch(e){
			console.log("UTF8.bytesToString: "+e);
			return(null);
		}
	},
	
	stringToBytes:function(string){
		try{
			let utf8=encodeURIComponent(string);
			let bytes=[];
			let pos=0;
			let b;
			while(true){
				if(pos>=utf8.length)break;
				if(utf8[pos]==="%"){
					b=Number.parseInt(utf8[pos+1]+utf8[pos+2],16);
					switch(true){
						case (b&128)===0:{
							bytes.push(Number.parseInt(utf8[pos+1]+utf8[pos+2],16));
							pos+=3;
							break;
						}
						case (b&224)===192:{
							bytes.push(Number.parseInt(utf8[pos+1]+utf8[pos+2],16));
							bytes.push(Number.parseInt(utf8[pos+4]+utf8[pos+5],16));
							pos+=6;
							break;
						}
						case (b&240)===224:{
							bytes.push(Number.parseInt(utf8[pos+1]+utf8[pos+2],16));
							bytes.push(Number.parseInt(utf8[pos+4]+utf8[pos+5],16));
							bytes.push(Number.parseInt(utf8[pos+7]+utf8[pos+8],16));
							pos+=9;
							break;
						}
						case (b&248)===240:{
							bytes.push(Number.parseInt(utf8[pos+1]+utf8[pos+2],16));
							bytes.push(Number.parseInt(utf8[pos+4]+utf8[pos+5],16));
							bytes.push(Number.parseInt(utf8[pos+7]+utf8[pos+8],16));
							bytes.push(Number.parseInt(utf8[pos+10]+utf8[pos+11],16));
							pos+=12;
							break;
						}
						default:throw new Error("wrong URI");
					}
				}else{
					bytes.push(utf8.charCodeAt(pos));
					pos+=1;
				}
			}
			return(bytes);
		}catch(e){
			console.log("UTF8.stringToBytes: "+e);
			return(null);
		}
	},

}