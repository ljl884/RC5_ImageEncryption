package test;

import java.util.ArrayList;

import test.TestCase.OperationMode;

public class Experiment {
	
	public static String inputFileName = "/Users/Egmont/Documents/CryptoImageTest/Logo.bmp";
	public static String outFolder = "/Users/Egmont/Documents/CryptoImageTest/";

	public static void main (String[] args) throws Exception{
		

		TestCase testcase = new TestCase(16, 2, "00000000", "0000", 
				inputFileName, outFolder, 
				OperationMode.CBC);
		testcase.perform();
		
		TestCase testcase1 = new TestCase(16, 2, "00000000", "00000000", 
				inputFileName, outFolder, 
				OperationMode.CBC);
		testcase1.perform();
		
		TestCase testcase2 = new TestCase(16, 2, "00000000", "000000000000", 
				inputFileName, outFolder, 
				OperationMode.CBC);
		testcase2.perform();

		TestCase testcase3 = new TestCase(16, 2, "00000000", "0000000000000000", 
				inputFileName, outFolder, 
				OperationMode.CBC);
		testcase3.perform();

		TestCase testcase4 = new TestCase(16, 12, "00000000", "0000", 
				inputFileName, outFolder, 
				OperationMode.CBC);
		testcase4.perform();

		TestCase testcase5 = new TestCase(16, 12, "00000000", "00000000", 
				inputFileName, outFolder, 
				OperationMode.CBC);
		testcase5.perform();

		TestCase testcase6 = new TestCase(16, 12, "00000000", "000000000000", 
				inputFileName, outFolder, 
				OperationMode.CBC);
		testcase6.perform();

		TestCase testcase7 = new TestCase(16, 12, "00000000", "0000000000000000", 
				inputFileName, outFolder, 
				OperationMode.CBC);
		testcase7.perform();

		TestCase testcase8 = new TestCase(32, 2, "0000000000000000", "0000", 
				inputFileName, outFolder, 
				OperationMode.CBC);
		testcase8.perform();

		TestCase testcase9 = new TestCase(32, 2, "0000000000000000", "00000000", 
				inputFileName, outFolder, 
				OperationMode.CBC);
		testcase9.perform();

		TestCase testcase10 = new TestCase(32, 2, "0000000000000000", "000000000000", 
				inputFileName, outFolder, 
				OperationMode.CBC);
		testcase10.perform();

		TestCase testcase11 = new TestCase(32, 2, "0000000000000000", "0000000000000000", 
				inputFileName, outFolder, 
				OperationMode.CBC);
		testcase11.perform();

		TestCase testcase12 = new TestCase(32, 12, "0000000000000000", "0000", 
				inputFileName, outFolder, 
				OperationMode.CBC);
		testcase12.perform();

		TestCase testcase13 = new TestCase(32, 12, "0000000000000000", "00000000", 
				inputFileName, outFolder, 
				OperationMode.CBC);
		testcase13.perform();

		TestCase testcase14 = new TestCase(32, 12, "0000000000000000", "000000000000", 
				inputFileName, outFolder, 
				OperationMode.CBC);
		testcase14.perform();

		TestCase testcase15 = new TestCase(32, 12, "0000000000000000", "0000000000000000", 
				inputFileName, outFolder, 
				OperationMode.CBC);
		testcase15.perform();
		
	}

}
