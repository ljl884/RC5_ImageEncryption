package test;

import java.util.ArrayList;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.params.RC5Parameters;
import org.bouncycastle.util.encoders.Hex;

public class ECBOPerater extends Operater {

	public ECBOPerater(BlockCipher engine, RC5Parameters param,
			ArrayList<String> input) {
		super(engine, param, input);
		
	}

	@Override
	public ArrayList<String> encrypt() throws Exception {
		
		ArrayList<String> result  = new ArrayList<String>();
		
		for(String block:input){
			if (block.length()!=BLOCK_STRING_SIZE) {
				System.out.println(block.length());
				throw new Exception("block size exception");
			}
			
			MyRC5Engine rc5Engine = new MyRC5Engine(engine, param, block);
			
			result.add(Hex.toHexString(rc5Engine.encrypt()));
			
		}
		
		
		return result;
	}

	@Override
	public ArrayList<String> decrypt() throws Exception {
		
		ArrayList<String> result  = new ArrayList<String>();
		
		for(String block:input){
			if (block.length()!=BLOCK_STRING_SIZE) {
				System.out.println(block.length());
				throw new Exception("block size exception");
			}
			
			MyRC5Engine rc5Engine = new MyRC5Engine(engine, param, block);
			
			result.add(Hex.toHexString(rc5Engine.decrypt()));
		}
		
		return result;
	}

}
