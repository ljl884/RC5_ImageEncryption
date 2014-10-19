package test;

import java.io.IOException;
import java.util.ArrayList;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.params.RC5Parameters;
import org.bouncycastle.operator.OutputCompressor;
import org.bouncycastle.util.encoders.Hex;


public class TestCase {
	private static int wordSize = 32;
	private static int rounds = 12;
	private static String initializationVector = "0000000000000000";
	private static String secretKey = "00000000";
	private static String inputFileName;
	private static String outputFolder;
	
	public enum OperationMode{  
	   ECB,CBC  
	}  
	private OperationMode mode;
	
	public TestCase(int wordSize,int rounds,String IV,String key,String inputFile,String outputFolder,OperationMode mode){
		this.wordSize = wordSize;
		this.rounds = rounds;
		this.initializationVector = IV;
		this.secretKey = key;
		this.inputFileName = inputFile;
		this.outputFolder = outputFolder;
		this.mode = mode;
	}
	
	public void perform() throws Exception{
		BlockCipher engine = null;
		RC5Parameters param = new RC5Parameters(Hex.decode(this.secretKey), this.rounds);
		ImageConverter IC = new ImageConverter();
		String modeString = "";
		byte [] inputBytes = null;
		try {
			inputBytes = IC.imageToBytes(inputFileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ArrayList<String> input = IC.makeBlocks(inputBytes, wordSize);
		ArrayList<String> output = null;
		
		
		switch (wordSize) {
		case 16:
			engine = new RC516Engine();
			break;
		case 32:
			engine = new RC532Engine();
			break;
		case 64:
			engine = new RC564Engine();
			break;

		default:
			break;
			
		}
		
		switch (mode) {
		case ECB:
			
			ECBOperator ecbOperator = 
			new ECBOperator(engine, param, input);
			output = ecbOperator.encrypt();
			modeString = "ECB";
			break;
			
		case CBC:
			
			CBCOperator cbcOperator = 
			new CBCOperator(new CBCBlockCipher(engine), param, Hex.decode(this.initializationVector), input);
			output = cbcOperator.encrypt();
			modeString = "CBC";
			break;

		default:
			break;
						
		}
		
		
		String outputFileName = outputFolder+"encrpted_"+modeString+"_"+wordSize+"_"+rounds+"_"+secretKey.length()*2+".bmp";
		IC.bytesToimage(IC.stringListToBytes(output), outputFileName);
	}
	
	
}
