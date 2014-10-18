package test;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import org.bouncycastle.util.encoders.Hex;
import org.bouncycastle.util.test.TestResult;

import sun.awt.CharsetString;
import test.RC532Engine;

class TestRotation{
    public static void main(
            String[]    args) throws UnsupportedEncodingException
        {
    	//RC532Engine rc5 = new test.RC532Engine();
    	//System.out.println(rc5.rotateLeft(1, 8));
    	//System.out.println(Arrays.toString(Hex.decode("0102")));
    	
    
    	
    	System.out.println(Arrays.toString(Hex.decode("89504e470d0a1a0a")));
    	//System.out.println(1<<8);
    	
        }

}