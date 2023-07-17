
let CRYPTO={
	
	ORDER:{
		encode:[0,1,2,3,4,5,6,7,0,1,2,3,4,5,6,7,0,1,2,3,4,5,6,7,7,6,5,4,3,2,1,0],
		decode:[0,1,2,3,4,5,6,7,7,6,5,4,3,2,1,0,7,6,5,4,3,2,1,0,7,6,5,4,3,2,1,0]
	},
	
	K:[
		[0xC,0x4,0x6,0x2,0xA,0x5,0xB,0x9,0xE,0x8,0xD,0x7,0x0,0x3,0xF,0x1],
		[0x6,0x8,0x2,0x3,0x9,0xA,0x5,0xC,0x1,0xE,0x4,0x7,0xB,0xD,0x0,0xF],
		[0xB,0x3,0x5,0x8,0x2,0xF,0xA,0xD,0xE,0x1,0x7,0x4,0xC,0x9,0x6,0x0],
		[0xC,0x8,0x2,0x1,0xD,0x4,0xF,0x6,0x7,0x0,0xA,0x5,0x3,0xE,0x9,0xB],
		[0x7,0xF,0x5,0xA,0x8,0x1,0x6,0xD,0x0,0x9,0x3,0xE,0xB,0x4,0x2,0xC],
		[0x5,0xD,0xF,0x6,0x9,0x2,0xC,0xA,0xB,0x7,0x8,0x1,0x4,0x3,0xE,0x0],
		[0x8,0xE,0x2,0x5,0x6,0x9,0x1,0xC,0xF,0x4,0xB,0x0,0xD,0xA,0x3,0x7],
		[0x1,0x7,0xE,0xD,0x0,0x5,0x8,0x3,0x4,0xF,0xA,0x6,0x9,0xC,0xB,0x2]
	],
	
	N:[],
	
	SUMM:function(a,b,mod){
		let N=((a<0?4294967296+a:a)+(b<0?4294967296+b:b))%mod;
		return(N<0?4294967296+N:N);
	},
	
	SHIFT:function(num_4b){
		let N=(num_4b>>>21)+(num_4b<<11);
		return(N<0?4294967296+N:N);
	},
	
	SUBST:function(num_4b){
		let b0,b1,b2,b3,b4,b5,b6,b7;
		b0=num_4b>>>28;
		b1=(num_4b<<4)>>>28;
		b2=(num_4b<<8)>>>28;
		b3=(num_4b<<12)>>>28;
		b4=(num_4b<<16)>>>28;
		b5=(num_4b<<20)>>>28;
		b6=(num_4b<<24)>>>28;
		b7=(num_4b<<28)>>>28;
		b0=CRYPTO.K[0][b0];
		b1=CRYPTO.K[1][b1];
		b2=CRYPTO.K[2][b2];
		b3=CRYPTO.K[3][b3];
		b4=CRYPTO.K[4][b4];
		b5=CRYPTO.K[5][b5];
		b6=CRYPTO.K[6][b6];
		b7=CRYPTO.K[7][b7];
		let N=(b0<<28)+(b1<<24)+(b2<<20)+(b3<<16)+(b4<<12)+(b5<<8)+(b6<<4)+b7
		return(N<0?4294967296+N:N);
	},
	
	encode:function(bytes){
		try{
			while((bytes.length%8)!==0)bytes.push(0);
			return(CRYPTO.code(bytes,CRYPTO.ORDER.encode));
		}catch(e){
			console.log("CRYPTO.encode: "+e);
			return(null);
		}
	},
	
	decode:function(bytes){
		try{
			return(CRYPTO.code(bytes,CRYPTO.ORDER.decode));
		}catch(e){
			console.log("CRYPTO.decode: "+e);
			return(null);
		}
	},
	
	code:function(bytes, order){
		try{
			let b=[];
			let NN;
			for(let i=0;i<bytes.length;i+=8){
				CRYPTO.N[0]=((bytes[i]<<24)+(bytes[i+1]<<16)+(bytes[i+2]<<8)+(bytes[i+3]));
				CRYPTO.N[1]=((bytes[i+4]<<24)+(bytes[i+5]<<16)+(bytes[i+6]<<8)+(bytes[i+7]));
				for(let n=0;n<32;n++){
					NN=CRYPTO.SUMM(CRYPTO.N[0],USER.KEY[order[n]],4294967296);
					NN=CRYPTO.SUBST(NN);
					NN=CRYPTO.SHIFT(NN);
					NN=NN^CRYPTO.N[1];
					NN=(NN<0?4294967296+NN:NN);
					if(n<31){
						CRYPTO.N[1]=CRYPTO.N[0];
						CRYPTO.N[0]=NN;
					}else{
						CRYPTO.N[1]=NN;
					}
				}
				b.push(CRYPTO.N[0]>>>24);
				b.push((CRYPTO.N[0]<<8)>>>24);
				b.push((CRYPTO.N[0]<<16)>>>24);
				b.push((CRYPTO.N[0]<<24)>>>24);
				b.push(CRYPTO.N[1]>>>24);
				b.push((CRYPTO.N[1]<<8)>>>24);
				b.push((CRYPTO.N[1]<<16)>>>24);
				b.push((CRYPTO.N[1]<<24)>>>24);
			};
			return(b);
		}catch(e){
			console.log("CRYPTO.code: "+e);
			return(null);
		}
	}
	
};