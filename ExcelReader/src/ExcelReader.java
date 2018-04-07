import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.*;
import java.io.File;

public class ExcelReader {
//	public static boolean endOfRow(int elements_in_Row, int index) {
//		if(((index+1) % elements_in_Row) == 0) {
//			return true;
//		}
//		return false;
//	}
//
//	public static boolean startOfRow(int elements_in_Row, int index) {
//		if((index % elements_in_Row) == 0) {
//			return true;
//		}
//		return false;
//	}	
	
	
	public static void main(String args[]) throws IOException, EncryptedDocumentException, InvalidFormatException {
			FileInputStream excel_File = new FileInputStream(new File("cars.xlsx"));
			Workbook workbook = new XSSFWorkbook(excel_File);
			ReadFile reader = new ReadFile(workbook);
			ArrayList<Cell> excelData = reader.finalListOfWords();
			
			reader.printTable();
			
//			System.out.println(excelData.size());
//			System.out.println(reader.numberOfElements());
	}
}
