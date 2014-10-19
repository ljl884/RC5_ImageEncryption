package test;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.params.RC5Parameters;
import org.bouncycastle.util.encoders.Hex;

/**
 * Image Converter 
 */
public class ImageConverter{
	
	 public byte[] imageHeader = new byte[76];
	 
	 byte[] imageToBytes (String ImageName) throws IOException {
		 // open image
		 File imgPath = new File(ImageName);
		 BufferedImage bufferedImage = ImageIO.read(imgPath);
		 
		 ByteArrayOutputStream baos=new ByteArrayOutputStream(1000);
		 ImageIO.write(bufferedImage, "bmp", baos);
		 baos.flush();
		 
		 byte[] bytearray = baos.toByteArray();
		 baos.close();
		 return ( bytearray );
		}
	
	
	byte[] stringListToBytes (ArrayList<String> list) throws IOException{
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream(1000);
		DataOutputStream out = new DataOutputStream(baos);
		for (String element : list) {
		    out.write((Hex.decode(element)));
		}
		
		byte[] bytearray = baos.toByteArray();
		baos.close();
		return bytearray;
	}
	
	void bytesToimage (byte[] imagebyte, String fileName) throws IOException{
		
		//recover image header
		for (int n=0;n<imageHeader.length;n++){
			imagebyte[n]=imageHeader[n];
		}
		
		byte dataToWrite[] = imagebyte;
				FileOutputStream out = new FileOutputStream(fileName);
				out.write(dataToWrite);
				out.close();		 
	}
	
	
	ArrayList<String> makeBlocks(byte[] image,int wordsize){
		
		int blocksize = wordsize/4;
		
		ArrayList<String> imageString = new ArrayList<String>();
		byte[] block = new byte[blocksize];
		
		// save image header first 76 bytes
		for(int n=0;n<imageHeader.length;n++){
			imageHeader[n]=image[n];
		}
		
		// make blocks as 64 bit (8 bytes) per block
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
			i+=(blocksize-1);
		}
		return imageString;	
	}
	
	
	
}