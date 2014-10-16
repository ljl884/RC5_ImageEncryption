package test;

import org.bouncycastle.util.test.TestResult;
import test.RC532Engine;

class TestRotation{
    public static void main(
            String[]    args)
        {
    	RC532Engine rc532Engine = new test.RC532Engine();
    	
    	System.out.println(rc532Engine.rotateLeft(8, 2));
    	
        }
}