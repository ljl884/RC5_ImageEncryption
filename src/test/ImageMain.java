package test;

import java.io.IOException;
import java.util.ArrayList;

import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.params.RC5Parameters;
import org.bouncycastle.util.encoders.Hex;

public class ImageMain{
	
	public static int wordSize = 32;
	public static int rounds = 12;
	public static String initializationVector = "0000000000000000";
	public static String secretKey = "00000000";
	public static String inputFileName = "/Users/Egmont/Documents/CryptoImageTest/logo.bmp";
	
	public static void main(String[] arg) throws Exception{
		
		ImageConverter IC = new ImageConverter();
		byte [] inputBytes = null;
		try {
			inputBytes = IC.imageToBytes(inputFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Encrypt image
//		CBCOperater operater = new CBCOperater( 
//				new CBCBlockCipher(new RC532Engine()),
//				new RC5Parameters(Hex.decode(secretKey), rounds), //Secret Key, Rounds
//				Hex.decode(initializationVector),                 //Initialization Vector
//				IC.makeBlocks(inputBytes,wordSize*2));			//imageBytes, wordsize
//		ArrayList<String> ciphertext = operater.encrypt();
		
		ECBOPerater ecboperater = new ECBOPerater(new RC532Engine(),
				new RC5Parameters(Hex.decode(secretKey), rounds),
				IC.makeBlocks(inputBytes, wordSize));
		ArrayList<String> ecbciphertext = ecboperater.encrypt();
		
		IC.bytesToimage(IC.stringListToBytes(ecbciphertext),"/Users/Egmont/Documents/CryptoImageTest/encrypted.bmp");


		// Decrypt image
//		CBCOperater operater2 = new CBCOperater( 
//				new CBCBlockCipher(new RC532Engine()),
//				new RC5Parameters(Hex.decode(secretKey), rounds),
//				Hex.decode(initializationVector),
//				ciphertext);
//		ArrayList<String> plaintext = operater2.decrypt();
//
//		IC.bytesToimage(IC.stringListToBytes(plaintext),"/Users/Egmont/Documents/CryptoImageTest/decrypted.bmp");
	}
}