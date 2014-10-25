package test;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.params.RC5Parameters;
import org.bouncycastle.util.encoders.Hex;

public class MyRC5Engine {
	
	private BlockCipher engine;
	private CipherParameters param;
	private byte[] input;
	
	
	public MyRC5Engine(BlockCipher engine,CipherParameters param, String input)
	{
		this.engine=engine;
		this.param=param;
		this.input=Hex.decode(input);
	}
	
	public byte[] encrypt() throws Exception{
		BufferedBlockCipher cipher = new BufferedBlockCipher(engine);
		cipher.init(true, param);
		byte[] out = new byte[input.length];
		int len1 = cipher.processBytes(input, 0, input.length, out, 0);
		cipher.doFinal(out, len1);
		return out;
	}
	
	public byte[] decrypt() throws Exception{
		BufferedBlockCipher cipher = new BufferedBlockCipher(engine);
		cipher.init(false, param);
		byte[] out = new byte[input.length];
		int len2 = cipher.processBytes(input, 0, input.length, out, 0);
		cipher.doFinal(out, len2);
		return out;
	}
}
