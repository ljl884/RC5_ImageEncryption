package test;

import java.util.ArrayList;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.crypto.params.RC5Parameters;
import org.bouncycastle.util.encoders.Hex;

public abstract class  Operator {
	
	protected static int BLOCK_STRING_SIZE =8;
	
	protected BlockCipher engine;
	protected CipherParameters param;
	protected ArrayList<String> input;
	protected byte[] IV;
	
	public Operator(BlockCipher engine,RC5Parameters param, ArrayList<String> input) {
		this.engine=engine;
		this.param=param;
		this.input = input;
		
		BLOCK_STRING_SIZE = input.get(0).length();
		
	}
	
	public Operator(BlockCipher engine, RC5Parameters param, byte[] iV,
			ArrayList<String> input) {
		this.engine=engine;
		this.param=param;
		this.input = input;
		this.IV = iV;
		
		BLOCK_STRING_SIZE = input.get(0).length();
	}

	public abstract ArrayList<String> encrypt() throws Exception;
	
	public abstract ArrayList<String> decrypt() throws Exception;
	
}
