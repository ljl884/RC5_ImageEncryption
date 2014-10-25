package test;

import java.util.ArrayList;

import test.TestCase.OperationMode;

public class Experiment {
	
	public static String inputFileName = "D:/Crypto/2.bmp";
	public static String outFolder = "D:/Crypto/";

	private static int[] wordsizeList ={16,32,64};
	private static int[] roundsList = {2,6,12};
	private static int[] keySizeList = {8,16,32,64};
	private static OperationMode[] modeList = {OperationMode.CBC,OperationMode.ECB,OperationMode.CFB};

	public static void main (String[] args) throws Exception{
		
		testSuite();
		
	}
	

	
	private static void testSuite() throws Exception {
		String IV ="";
		String key = "";
		int testNumber = 1;
		for(int wordsize:wordsizeList){
			for(int rounds: roundsList){
				for(int keySize: keySizeList){
					for(OperationMode operationMode: modeList){
						System.out.println("Performing test case"+testNumber);
						IV = getIV(wordsize);
						key = getKey(keySize);
						TestCase testCase = new TestCase(wordsize, rounds, IV, key, inputFileName, outFolder, operationMode);
						testCase.perform();
						testNumber++;
					}
					
				}
			}
		}
		System.out.println("Mission Complete!");
	}

	public static String getIV(int wordSize){
		switch (wordSize) {
		case 16:
			return "00000000";
			
		case 32:
			return "0000000000000000";
			
		case 64:
			return "00000000000000000000000000000000";
			
		default:
			return "";
			
			}
	}
	
	public static String getKey(int keySize){
		switch (keySize) {
		case 8:
			return "0000";
			
		case 16:
			return "00000000";
			
		case 32:
			return "0000000000000000";
			
		case 64:
			return "00000000000000000000000000000000";
			
		default:
			return "";
		}
	}
	

	
}

