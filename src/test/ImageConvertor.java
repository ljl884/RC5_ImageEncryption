package test;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.bouncycastle.util.encoders.Hex;


/**
 * RC5 tester - vectors from ftp://ftp.nordu.net/rfc/rfc2040.txt
 *
 * RFC 2040 "The RC5, RC5-CBC, RC5-CBC-Pad, and RC5-CTS Algorithms"
 */
public class ImageConvertor{
	
	private byte[] extractBytes (String ImageName) throws IOException {
		 // open image
		 File imgPath = new File(ImageName);
		 BufferedImage bufferedImage = ImageIO.read(imgPath);

		 //System.out.println(bufferedImage);
		 // get DataBufferBytes from Raster
		 WritableRaster raster = bufferedImage .getRaster();
		 DataBufferByte data   = (DataBufferByte) raster.getDataBuffer();

		 return ( data.getData() );
		}
	
	@SuppressWarnings("null")
	private ArrayList<String> getStringList(byte[] image){
		
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
	
	public static void main(String[] arg){
		ImageConvertor IC = new ImageConvertor();
		byte [] out = null;
		try {
			out = IC.extractBytes("/Users/Egmont/Documents/test.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println(Hex.toHexString(out));
		
		System.out.println(IC.getStringList(out));
		
	}
}