package test;

import java.util.ArrayList;
import java.util.Arrays;

import javax.crypto.Cipher;

import test.RC532Engine;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.engines.RC564Engine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.crypto.params.RC5Parameters;
import org.bouncycastle.crypto.test.BlockCipherVectorTest;
import org.bouncycastle.jcajce.provider.symmetric.RC5;
import org.bouncycastle.jce.provider.test.BlockCipherTest;
import org.bouncycastle.util.encoders.Hex;
import org.bouncycastle.util.test.SimpleTestResult;
import org.bouncycastle.util.test.Test;
import org.bouncycastle.util.test.TestResult;

public class Main {
	
	public static void main(String[] arg) throws Exception{
		MyRC5Engine engine = new MyRC5Engine(new CBCBlockCipher(new RC532Engine()), new ParametersWithIV(
                new RC5Parameters(Hex.decode("01"), 2),
                Hex.decode("0000000000000000")), "0000000000000000");
		byte[] out=engine.encrypt();
		System.out.println(Hex.toHexString(out));
		
		MyRC5Engine engine2 = new MyRC5Engine(new CBCBlockCipher(new RC532Engine()), new ParametersWithIV(
                new RC5Parameters(Hex.decode("01"), 2),
                Hex.decode("0000000000000000")), Hex.toHexString(out));
		byte[] out2=engine2.decrypt();
		System.out.println(Hex.toHexString(out2));
		
		//----------------
		
		//System.out.println("CBC mode:");
		
		ArrayList<String> input = new ArrayList<String>();
		input.add("0000000000000000");
		input.add("0000000000000000");
		input.add("0000000000000000");
		CBCOperater operater = new CBCOperater( 
				new CBCBlockCipher(new RC532Engine()),
				new RC5Parameters(Hex.decode("01"), 2),
				Hex.decode("0000000000000000"),
				input);
		ArrayList<String> cipher = operater.encrypt();
		for(String s:cipher){
			System.out.println(s);
		}
		
		CBCOperater operater2 = new CBCOperater( 
				new CBCBlockCipher(new RC532Engine()),
				new RC5Parameters(Hex.decode("01"), 2),
				Hex.decode("0000000000000000"),
				cipher);
		ArrayList<String> plaintext = operater2.decrypt();
		for(String s:plaintext){
			System.out.println(s);
		}
	}

}
