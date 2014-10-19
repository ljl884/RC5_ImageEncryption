package test;

import test.TestCase.OperationMode;

public class Experiment {
	
	public static String inputFileName = "/Users/Egmont/Documents/CryptoImageTest/Pattern.bmp";
	public static String outFolder = "/Users/Egmont/Documents/CryptoImageTest/";

	public static void main (String[] args) throws Exception{
		

		TestCase testcase = new TestCase(16, 2, "00000000", "0000", 
				inputFileName, outFolder, 
				OperationMode.ECB);
		testcase.perform();
		
		TestCase testcase1 = new TestCase(16, 2, "00000000", "00000000", 
				inputFileName, outFolder, 
				OperationMode.ECB);
		testcase1.perform();
		
		TestCase testcase2 = new TestCase(16, 2, "00000000", "000000000000", 
				inputFileName, outFolder, 
				OperationMode.ECB);
		testcase2.perform();

		TestCase testcase3 = new TestCase(16, 2, "00000000", "0000000000000000", 
				inputFileName, outFolder, 
				OperationMode.ECB);
		testcase3.perform();

		TestCase testcase4 = new TestCase(16, 12, "00000000", "0000", 
				inputFileName, outFolder, 
				OperationMode.ECB);
		testcase4.perform();

		TestCase testcase5 = new TestCase(16, 12, "00000000", "00000000", 
				inputFileName, outFolder, 
				OperationMode.ECB);
		testcase5.perform();

		TestCase testcase6 = new TestCase(16, 12, "00000000", "000000000000", 
				inputFileName, outFolder, 
				OperationMode.ECB);
		testcase6.perform();

		TestCase testcase7 = new TestCase(16, 12, "00000000", "0000000000000000", 
				inputFileName, outFolder, 
				OperationMode.ECB);
		testcase7.perform();

		TestCase testcase8 = new TestCase(32, 2, "0000000000000000", "0000", 
				inputFileName, outFolder, 
				OperationMode.ECB);
		testcase8.perform();

		TestCase testcase9 = new TestCase(32, 2, "0000000000000000", "00000000", 
				inputFileName, outFolder, 
				OperationMode.ECB);
		testcase9.perform();

		TestCase testcase10 = new TestCase(32, 2, "0000000000000000", "000000000000", 
				inputFileName, outFolder, 
				OperationMode.ECB);
		testcase10.perform();

		TestCase testcase11 = new TestCase(32, 2, "0000000000000000", "0000000000000000", 
				inputFileName, outFolder, 
				OperationMode.ECB);
		testcase11.perform();

		TestCase testcase12 = new TestCase(32, 12, "0000000000000000", "0000", 
				inputFileName, outFolder, 
				OperationMode.ECB);
		testcase12.perform();

		TestCase testcase13 = new TestCase(32, 12, "0000000000000000", "00000000", 
				inputFileName, outFolder, 
				OperationMode.ECB);
		testcase13.perform();

		TestCase testcase14 = new TestCase(32, 12, "0000000000000000", "000000000000", 
				inputFileName, outFolder, 
				OperationMode.ECB);
		testcase14.perform();

		TestCase testcase15 = new TestCase(32, 12, "0000000000000000", "0000000000000000", 
				inputFileName, outFolder, 
				OperationMode.ECB);
		testcase15.perform();
		
	}

}
