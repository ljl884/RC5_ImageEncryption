package test;

import java.util.ArrayList;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.crypto.params.RC5Parameters;
import org.bouncycastle.util.encoders.Hex;


public class CBCOperater {
	
	private static int BLOCK_STRING_SIZE =16;
	
	private BlockCipher engine;
	private CipherParameters param;
	private ArrayList<String> input;
	private byte[] IV;
	
	public CBCOperater(BlockCipher engine,RC5Parameters param,byte[] IV, ArrayList<String> input) {
		this.engine=engine;
		this.param=param;
		this.input = input;
		this.IV = IV;
	}
	
	public ArrayList<String> encrypt() throws Exception{
		
		ArrayList<String> result = new ArrayList<String>();
		
		byte[] out =IV;
		
		for(String block: input){
			
			//if the size of the string is not expected, throw exception
			if(block.length()!=BLOCK_STRING_SIZE){
				throw new Exception("block size exception");
			}
			
			
			MyRC5Engine rc5engine = new MyRC5Engine(engine, new ParametersWithIV(
	                param, out), block);
			
			out=rc5engine.encrypt(); //out is the cipher text of this block
			
			result.add(Hex.toHexString(out));
		
		}
		
		return result;
	}
	
	public ArrayList<String> decrypt() throws Exception{
		ArrayList<String> result = new ArrayList<String>();
		
		byte[] IV = this.IV;
		
		for(String block: input){
			
			//if the size of the string is not expected, throw exception
			if(block.length()!=BLOCK_STRING_SIZE){
				throw new Exception("block size exception");
			}
			
			
			MyRC5Engine rc5engine = new MyRC5Engine(engine, new ParametersWithIV(
	                param, IV), block);
			
			IV = Hex.decode(block);
			
			byte[] out=rc5engine.decrypt(); //out is the cipher text of this block
			
			result.add(Hex.toHexString(out));
		
		}
		
		return result;
	}
	
	
	
}
