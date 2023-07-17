package server.crypto;

public class Empty implements Cryptographer {
	
	public byte[] encode(byte[] raw, String password) {
		return(raw);
	}
	
	public byte[] decode(byte[] raw, String password) {
		return(raw);
	}

}
