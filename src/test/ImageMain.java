package test;

import java.io.IOException;
import java.util.ArrayList;

import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.params.RC5Parameters;
import org.bouncycastle.util.encoders.Hex;

public class ImageMain{
	
	public static void main(String[] arg) throws Exception{
		ImageConverter IC = new ImageConverter();
		byte [] inputBytes = null;
		try {
			inputBytes = IC.imageToBytes("/Users/Egmont/Documents/CryptoImageTest/Logo.bmp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println(IC.makeBlock64bit(inputBytes));
		
		CBCOperater operater = new CBCOperater( 
				new CBCBlockCipher(new RC532Engine()),
				new RC5Parameters(Hex.decode("01"), 2),
				Hex.decode("0000000000000000"),
				IC.makeBlocks(inputBytes,32));
		ArrayList<String> ciphertext = operater.encrypt();
		
		IC.bytesToimage(IC.stringListToBytes(ciphertext),"/Users/Egmont/Documents/CryptoImageTest/encrypted.bmp");



		CBCOperater operater2 = new CBCOperater( 
				new CBCBlockCipher(new RC532Engine()),
				new RC5Parameters(Hex.decode("01"), 2),
				Hex.decode("0000000000000000"),
				ciphertext);
		ArrayList<String> plaintext = operater2.decrypt();

		IC.bytesToimage(IC.stringListToBytes(plaintext),"/Users/Egmont/Documents/CryptoImageTest/decrypted.bmp");
	}
}