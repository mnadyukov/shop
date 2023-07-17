package server.crypto;

import java.util.Arrays;

import server.ErrorManager;

public class Gost28147Simple implements Cryptographer{

	private final byte[][] K=
		{
			{0xC,0x4,0x6,0x2,0xA,0x5,0xB,0x9,0xE,0x8,0xD,0x7,0x0,0x3,0xF,0x1},
			{0x6,0x8,0x2,0x3,0x9,0xA,0x5,0xC,0x1,0xE,0x4,0x7,0xB,0xD,0x0,0xF},
			{0xB,0x3,0x5,0x8,0x2,0xF,0xA,0xD,0xE,0x1,0x7,0x4,0xC,0x9,0x6,0x0},
			{0xC,0x8,0x2,0x1,0xD,0x4,0xF,0x6,0x7,0x0,0xA,0x5,0x3,0xE,0x9,0xB},
			{0x7,0xF,0x5,0xA,0x8,0x1,0x6,0xD,0x0,0x9,0x3,0xE,0xB,0x4,0x2,0xC},
			{0x5,0xD,0xF,0x6,0x9,0x2,0xC,0xA,0xB,0x7,0x8,0x1,0x4,0x3,0xE,0x0},
			{0x8,0xE,0x2,0x5,0x6,0x9,0x1,0xC,0xF,0x4,0xB,0x0,0xD,0xA,0x3,0x7},
			{0x1,0x7,0xE,0xD,0x0,0x5,0x8,0x3,0x4,0xF,0xA,0x6,0x9,0xC,0xB,0x2}
		}
	;
	private final int[] ORDER_ENCODE={0,1,2,3,4,5,6,7,0,1,2,3,4,5,6,7,0,1,2,3,4,5,6,7,7,6,5,4,3,2,1,0};
	private final int[] ORDER_DECODE={0,1,2,3,4,5,6,7,7,6,5,4,3,2,1,0,7,6,5,4,3,2,1,0,7,6,5,4,3,2,1,0};
	
	private long[] KEY;
	private long[] N;
	
	public Gost28147Simple(){
		KEY=new long[8];
		N=new long[2];
	}

	public byte[] encode(byte[] bytes, String password){
		try{
			if(!setKey(password))throw new Exception("error while getting KEY");
			int tail=bytes.length%8;
			if(tail!=0)bytes=Arrays.copyOf(bytes,bytes.length+(8-tail));
			return(code(bytes,ORDER_ENCODE));
		}catch(Exception e){
			ErrorManager.register(Gost28147Simple.class.getName()+".encode(byte[], String): "+e);
			return(null);
		}
	}
	
	public byte[] decode(byte[] bytes, String password){
		try{
			if(bytes.length%8!=0)throw new Exception("wrong byte array");
			if(!setKey(password))throw new Exception("error while getting KEY");
			return(code(bytes,ORDER_DECODE));
		}catch(Exception e){
			ErrorManager.register(Gost28147Simple.class.getName()+".decode(byte[], String): "+e);
			return(null);
		}
	}
	
	private byte[] code(byte[] bytes, int[] order){
		try{
			byte[] b=new byte[bytes.length];
			long NN;
			for(int i=0;i<bytes.length;i+=8) {
				N[0]=(
					(unsigning(bytes[i])<<24)+
					(unsigning(bytes[i+1])<<16)+
					(unsigning(bytes[i+2])<<8)+
					(unsigning(bytes[i+3]))
				);
				N[1]=(
					(unsigning(bytes[i+4])<<24)+
					(unsigning(bytes[i+5])<<16)+
					(unsigning(bytes[i+6])<<8)+
					(unsigning(bytes[i+7]))
				);
				for(int n=0;n<32;n++) {
					NN=summ(N[0],KEY[order[n]],4294967296L);
					NN=subst(NN);
					NN=shift(NN);
					NN=NN^N[1];
					if(n<31){
						N[1]=N[0];
						N[0]=NN;
					}else{
						N[1]=NN;
					}
				}
				b[i]=(byte)(N[0]>>>24);
				b[i+1]=(byte)((N[0]<<8)>>>24);
				b[i+2]=(byte)((N[0]<<16)>>>24);
				b[i+3]=(byte)((N[0]<<24)>>>24);
				b[i+4]=(byte)(N[1]>>>24);
				b[i+5]=(byte)((N[1]<<8)>>>24);
				b[i+6]=(byte)((N[1]<<16)>>>24);
				b[i+7]=(byte)((N[1]<<24)>>>24);
			}
			
			return(b);
		}catch(Exception e){
			ErrorManager.register(Gost28147Simple.class.getName()+".code(byte[], String): "+e);
			return(null);
		}
	}
	
	private boolean setKey(String password){
		try{
			if(password==null || !password.matches("^[a-zA-Z0-9_!@#$%&*.,:;/()-+=?]{6,}$"))throw new Exception("wrong password");
			while(true){
				if(password.length()>=32){
					password=password.substring(0,32);
					break;
				}
				password+=password;
			}
			byte[] key=password.getBytes("UTF-8");
			for(int i=0;i<key.length;i+=4){
				KEY[i>>>2]=0;
				for(int j=0;j<4;j++)
					KEY[i>>>2]+=((long)(key[i+j]<0?256+key[i+j]:key[i+j]))<<(24-j*8);
			}
			return(true);
		}catch(Exception e){
			ErrorManager.register(Gost28147Simple.class.getName()+".setKey(String): "+e);
			return(false);
		}
	}
	
	private long summ(long a, long b, long mod) {
		return(((a<0?4294967296L+a:a)+(b<0?4294967296L+b:b))%mod);
	}
	
	private long subst(long num) {
		try {
			long b0,b1,b2,b3,b4,b5,b6,b7;
			b0=(num&4026531840L)>>>28;
			b1=(num&251658240L)>>>24;
			b2=(num&15728640L)>>>20;
			b3=(num&983040L)>>>16;
			b4=(num&61440L)>>>12;
			b5=(num&3840L)>>>8;
			b6=(num&240L)>>>4;
			b7=(num&15L);
			b0=K[0][(int)b0];
			b1=K[1][(int)b1];
			b2=K[2][(int)b2];
			b3=K[3][(int)b3];
			b4=K[4][(int)b4];
			b5=K[5][(int)b5];
			b6=K[6][(int)b6];
			b7=K[7][(int)b7];
			return(
				b0*268435456L+
				b1*16777216L+
				b2*1048576L+
				b3*65536L+
				b4*4096L+
				b5*256L+
				b6*16L+
				b7
			);
		}catch(Exception e) {
			ErrorManager.register(Gost28147Simple.class.getName()+".subst(long)): "+e);
			throw e;
		}
	}
	
	private long shift(long num) {
		return(((num&2097151L)<<11)+(num>>>21));
	}
	
	private long unsigning(byte b) {
		return(b<0?256L+b:(long)b);
	}

	public static void main(String[] args) throws Exception{
		String message="Привет=Hello!";
		byte[] b=message.getBytes("UTF-8");
		for(int i=0;i<b.length;i++)System.out.print((b[i]<0?256+b[i]:b[i])+", ");
		System.out.println();
		String password="masterkey";
		Gost28147Simple G=new Gost28147Simple();
		G.setKey(password);
		for(int i=0;i<G.KEY.length;i++)System.out.print((G.KEY[i]<0?4294967296L+G.KEY[i]:G.KEY[i])+", ");
		System.out.println();
		b=G.encode(b, password);
		for(int i=0;i<b.length;i++)System.out.print((b[i]<0?256+b[i]:b[i])+", ");
		
	}
	
}

