import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.util.*;

//this class is designed to read the excel sheet and write excel sheet
public class ExcelReader {
	
	//This method is a helper method to print out the list of strings in arraylist
	static void printList(ArrayList<String> list) {
		Iterator<String> it = list.iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
		}
	}

	//main function that executes everything, which reads in excel file and writes the result in a new excel file
	public static void main(String args[]) throws IOException, EncryptedDocumentException, InvalidFormatException {
		
		boolean alter = true;
		boolean firstTime = true;
		int numberOfSheets = 2;
		int count = 0;
		boolean closed = false;
		ArrayList<String> allResponses = new ArrayList<String>();
		FileInputStream excel_File = new FileInputStream(new File("SurveyResponse.xlsx"));     //Original input file with the responses
		FileInputStream excel_File1 = new FileInputStream(new File("FinalListOfWords.xlsx"));       //Final input file with the list of unique words
		Workbook workbook = new XSSFWorkbook(excel_File);
		Workbook workbook1 = new XSSFWorkbook(excel_File1);
		ExcelWriter writer;
		ExcelWriter writer1 = new ExcelWriter("FinalAnalysis.xlsx");                           //Final output file
		String sheetTag;
		
		while(count < numberOfSheets) {
			if(alter) {												                           //Reads the original file and write the frequency of each word in new excel file
				ReadInput reader = new ReadInput(workbook, count);
				Iterator<Entry> finalList = reader.getList();  								   //Return the iterator that points to the list of unique words
				writer = new ExcelWriter(finalList,"Result1", "ResultFromSurvey.xlsx");
				writer.writeExcelFileOnlyOnce();
				allResponses = reader.getReponses();                                           //Return an arraylist of all the responses
				alter = false;                                       
			}else {
				ReadFile readFiler = new ReadFile(workbook1, allResponses, count);             //Take in different arraylist of responses and write in different sheet
				Iterator<WordNode> it = readFiler.readSheets();
				sheetTag = "finalResult" + count;
				if((count + 1) == numberOfSheets) {                                            //If reading at the last sheet, need ot close it
					closed = true;
				}
				writer1.writeExcelFileMoreThanOnce(it , sheetTag, firstTime, closed);                   //The actual final output file gets written
				alter = true;
				
				if(firstTime) {                                                                //At the first time, needs to create new XSSFWorkbook, 
					firstTime = false;                                                         //after that needs to used the already created XSSFWorkbook to continue to write the result in
				}
				
				count++;
			}
		}
		System.out.println("Done");
	}
}
