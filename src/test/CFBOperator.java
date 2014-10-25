package test;

import java.util.ArrayList;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.crypto.params.RC5Parameters;
import org.bouncycastle.util.encoders.Hex;

public class CFBOperator extends Operator{

	public CFBOperator(BlockCipher engine, RC5Parameters param, byte[] iV,
			ArrayList<String> input) {
		super(engine, param, iV, input);
		
	}

	@Override
	public ArrayList<String> encrypt() throws Exception {
	ArrayList<String> result = new ArrayList<String>();
		
		byte[] out =IV;
		
		for(String block: input){
			
			//if the size of the string is not expected, throw exception
			if(block.length()!=BLOCK_STRING_SIZE){
				System.out.println(block.length());
				throw new Exception("block size exception");
			}
			
			
			MyRC5Engine rc5engine = new MyRC5Engine(engine, new ParametersWithIV(
	                param, out), block);
			
			out=rc5engine.encrypt(); //out is the cipher text of this block
			
			result.add(Hex.toHexString(out));
		
		}
		
		return result;
	}

	@Override
	public ArrayList<String> decrypt() throws Exception {
		
		return null;
	}

}
