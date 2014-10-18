package test;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.params.RC5Parameters;
import org.bouncycastle.util.encoders.Hex;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;


/**
 * RC5 tester - vectors from ftp://ftp.nordu.net/rfc/rfc2040.txt
 *
 * RFC 2040 "The RC5, RC5-CBC, RC5-CBC-Pad, and RC5-CTS Algorithms"
 */
public class ImageConvertor{
	
	private byte[] imageToBytes (String ImageName) throws IOException {
		 // open image
		 File imgPath = new File(ImageName);
		 BufferedImage bufferedImage = ImageIO.read(imgPath);
		 
		 ByteArrayOutputStream baos=new ByteArrayOutputStream(1000);
		 ImageIO.write(bufferedImage, "png", baos);
		 baos.flush();
		 
		 byte[] bytearray = baos.toByteArray();
		 baos.close();
		 return ( bytearray );
		}
	
	
	private byte[] stringListToBytes (ArrayList<String> list) throws IOException{
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream(1000);
		DataOutputStream out = new DataOutputStream(baos);
		for (String element : list) {
		    out.write((Hex.decode(element)));
		}
		
		byte[] bytearray = baos.toByteArray();
		baos.close();
		return bytearray;
	}
	
	private void bytesToimage (byte[] imagebyte) throws IOException{
	 
		 BufferedImage imag=ImageIO.read(new ByteArrayInputStream(imagebyte));
		 
		 ImageIO.write(imag, "png", new File("/Users/Egmont/Documents/result.png"));
		 
	}
	
	
	@SuppressWarnings("null")
	private ArrayList<String> makeBlock64bit(byte[] image){
		
		ArrayList<String> imageString = new ArrayList<String>();
		byte[] block = new byte[8];
		
		int m = 0;
		
		for (int i=0;i<=image.length;i++){
			for (int j=0;j<block.length ;j++){
				if(m<image.length-1){
				m=i+j;
				block[j]=image[m];	
				}
						
			}
			//System.out.println(Hex.toHexString(block));
			imageString.add(Hex.toHexString(block));
			//System.out.println(imageString);
			i+=7;
		}
		return imageString;	
	}
	
	public static void main(String[] arg) throws Exception{
		ImageConvertor IC = new ImageConvertor();
		byte [] inputBytes = null;
		try {
			inputBytes = IC.imageToBytes("/Users/Egmont/Documents/test.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(IC.makeBlock64bit(inputBytes));
		
		CBCOperater operater = new CBCOperater( 
				new CBCBlockCipher(new RC532Engine()),
				new RC5Parameters(Hex.decode("01"), 2),
				Hex.decode("0000000000000000"),
				IC.makeBlock64bit(inputBytes));
		ArrayList<String> cipher = operater.encrypt();

		System.out.println(cipher);
		//byte[] bytes = IC.stringListToBytes(cipher);
		
		IC.bytesToimage(IC.stringListToBytes(cipher));



		CBCOperater operater2 = new CBCOperater( 
				new CBCBlockCipher(new RC532Engine()),
				new RC5Parameters(Hex.decode("01"), 2),
				Hex.decode("0000000000000000"),
				cipher);
		ArrayList<String> plaintext = operater2.decrypt();
		System.out.println(plaintext);
//		byte[] bytes = IC.stringListToBytes(plaintext);
		//IC.bytesToimage(IC.stringListToBytes(plaintext));
	}
}