
let CONNECTOR={
	
	HOST:"localhost",
	PORT:"4000",
	
	send:function(mssg,callback){
		try{
			if(mssg.startsWith("#")){
				COMMAND.execute(new Structure("").addChild(Structure.fromString(mssg.substring(1))));
				return;
			}
			let socket=new XMLHttpRequest();
			socket.CALLBACK=callback;
			socket.open(
				"POST",
				"http://"+CONNECTOR.HOST+":"+CONNECTOR.PORT,
				true
			);
			let request;
			if(USER.name===null){
				request=mssg;
				socket.setRequestHeader("Crypto","false");
			}else{
				request=PACKAGER.getPackage(mssg);
				if(request===null)throw new Error("error while packaging the message");
				socket.setRequestHeader("Crypto","true")
			}
			socket.onload=CONNECTOR.process.bind(socket);
			socket.send(request+"\n");
		}catch(e){
			console.log("CONNECTOR.send:"+e);
		}
	},
	
	process:function(e){
		try{
			if(this.readyState!==XMLHttpRequest.DONE)return;
			let message;
			switch(this.status){
				case 200:{
					if(this.getResponseHeader("Crypto")==="false"){
						message=this.responseText;
					}else{
						message=PACKAGER.getMessage(this.responseText);
						if(message===null)throw new Error("error while unpackaging the message");
					}
					if(this.CALLBACK){
						this.CALLBACK(message);
					}else{					
						COMMAND.execute(message);
					}
					break;
				}
				default:{
					throw new Error("error while processing message");
				}					
			}
		}catch(e){
			console.log("CONNECTOR.process: "+e);
		}
	}
	
};

let PACKAGER={
	
	getPackage:function(mssg){
		try{
			return(
				USER.name+"\x00"+HEX.bytesToHex(
					CRYPTO.encode(
						UTF8.stringToBytes(USER.name+"\x00"+mssg)
					)
				)
			);
		}catch(e){
			console.log("PACKAGER.getPackage: "+e);
			return(null);
		}
		
	},
	
	getMessage:function(pckg){
		try{
			let mssg=UTF8.bytesToString(
				CRYPTO.decode(
					HEX.hexToBytes(pckg)
				)
			).split("\x00");
			if(mssg[0]!==USER.name)throw new Error("unknown user");
			return(mssg[1]);
		}catch(e){
			console.log("PACKAGER.getMessage: "+e);
			return(null);
		}
	}

};

