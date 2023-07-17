package server.crypto;

public interface Cryptographer {

	public byte[] encode(byte[] bytes, String password);
	
	public byte[] decode(byte[] bytes, String password);
	
}
