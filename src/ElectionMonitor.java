import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ElectionMonitor {
	//File names: input, zip output and date output
	private static final String inputFileName = "../input/itcont.txt";
	private static final String outputFileName_zip = "../output/medianvals_by_zip.txt";
	private static final String outputFileName_date = "../output/medianvals_by_date.txt";
	
	//core structure process median
	private static HashMap<String, HashMap<String, Median>> zipMap; //for running median zip mapping
	private static TreeMap<String, Median> dateMap;					//for sorted date mapping


	public static void main(String[] args) throws IOException, FileNotFoundException {
		//IO initialization
		File inputFile = new File(inputFileName);
		File outputFile_zip = new File(outputFileName_zip);
		File outputFile_date = new File(outputFileName_date);
		FileWriter zipWriter = new FileWriter(outputFile_zip);
		FileWriter dateWriter = new FileWriter(outputFile_date);
		Scanner in = new Scanner(inputFile);
		
		//Data container initialization
		zipMap = new HashMap<String, HashMap<String, Median>>();
		dateMap = new TreeMap<String, Median>();
		String[] info;
		double currentAmount = 0;
		
		//read file
		while(in.hasNextLine()){
			String line = in.nextLine();
			info = line.split("\\|");
			//extract information
			String CMTE_ID = info[0];
			String ZIP_CODE = info[10];
			String TRANSACTION_DT = info[13];
			String TRANSACTION_AMT = info[14];
			String OTHER_ID = info[15];
	
			if(isValidZipCode(ZIP_CODE)){
				ZIP_CODE = ZIP_CODE.substring(0, 5);
			}
			//Null input validation
			if(OTHER_ID.length() == 0 && CMTE_ID.length() != 0 && TRANSACTION_AMT.length() != 0){
				//current amount to add
				currentAmount = Double.parseDouble(TRANSACTION_AMT);
				//-------------------Process ZIP file output------------------------------	
				//ZipCode validation
				if(isValidZipCode(ZIP_CODE)){
					
					//Get zip map reference to candidate
					HashMap<String, Median> zipMedianUnderName = zipMap.getOrDefault(CMTE_ID, new HashMap<String, Median>());
					Median zipMedian = zipMedianUnderName.getOrDefault(ZIP_CODE, new Median());
				
					//Validate zipcode before calculation of median
					zipMedian.addNum(currentAmount);
			
					//Write back to map
					zipMedianUnderName.put(ZIP_CODE, zipMedian);
					zipMap.put(CMTE_ID, zipMedianUnderName);
					
					//Write result to file
					int zipMedianNum = zipMedian.findMedian();
					zipWriter.write(CMTE_ID 			//CMTE_ID
							+ "|" + ZIP_CODE 			//ZIP_CODE
							+ "|" + zipMedianNum 		//Current Median of this Zip and Candidate
							+ "|" + zipMedian.size 		//Total number of transactions by this receipient on this zip
							+ "|" + zipMedian.total 	//Total amount of contributions by this receipient on this zip
							+ "\n");
				}
				//-------------------Process DATE file output------------------------------	
				//Add date from stream to Median
				//Date validation
				if(isValidDate(TRANSACTION_DT)){
					
					//Get map reference for current candidate
					StringBuffer buffer = new StringBuffer();
					String id = buffer.append(CMTE_ID + "|").append(TRANSACTION_DT).toString();
					Median dateMedian = dateMap.getOrDefault(id, new Median());
					dateMedian.addNum(currentAmount);
					
					//Write back to map
					dateMap.put(id, dateMedian);
				}				
			}
		}
		
		//Write distinguish results to date output AFTER streaming
		for(String each: dateMap.keySet()){
			String[] set = each.split("\\|");
			Median median = dateMap.get(each);
			int number = median.findMedian();
			dateWriter.write(set[0] 			//CMTE_ID
					 + "|" + set[1] 			//TRANSACTION_DT
					 + "|" + number 			//Current Median of this  recipient on this date
					 + "|" + median.size 		//Total number of transactions on this date
					 + "|" + median.total 		//Total amount of contributions on this date
					 + "\n");
		}
				
		//Clean up
		zipWriter.close();
		dateWriter.close();
		in.close();
	}

	
	//Validate date 
	private static boolean isValidDate(String dateInput){
		if(dateInput.length() != 8 ) return false;
		try {
		//using java date format for validation
		Date date = new SimpleDateFormat("MMddyyyy").parse(dateInput);
		return true;
		} catch (Exception ex) {
			return false;
		}
	}
	
	//Validate zipCode
	private static boolean isValidZipCode(String zipCode){
		if(zipCode.length() < 5) return false;
		return true;
	}
}
