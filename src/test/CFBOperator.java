package test;

import java.util.ArrayList;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.params.RC5Parameters;

public class CFBOperator extends Operator{

	public CFBOperator(BlockCipher engine, RC5Parameters param, byte[] iV,
			ArrayList<String> input) {
		super(engine, param, iV, input);
		
	}

	@Override
	public ArrayList<String> encrypt() throws Exception {
		
		return null;
	}

	@Override
	public ArrayList<String> decrypt() throws Exception {
		
		return null;
	}

}
