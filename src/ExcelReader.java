import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;

public class ExcelReader {
	
	public static void main(String args[]) throws IOException, EncryptedDocumentException, InvalidFormatException {
			FileInputStream excel_File = new FileInputStream(new File("SurveyResponse.xlsx"));
			Workbook workbook = new XSSFWorkbook(excel_File);
			ReadInput reader = new ReadInput(workbook);
			Iterator<Entry> finalList = reader.getData();
			ExcelWriter writer = new ExcelWriter(finalList,"Result1", "result.xlsx");
			writer.writeExcelFile();
			
//			while(pageNum < 3) {
//				if((pageNum + 1) == 3) {
//					closed = true;
//				}
//				sheetTag += pageNum;
//				ReadInput reader = new ReadInput(workbook, pageNum++);
//				Iterator<Entry> finalList = reader.getData();
//				ExcelWriter writer = new ExcelWriter(finalList, sheetTag, outputFile, closed);
//				writer.writeExcelFile();
//			}
//			ReadInput reader = new ReadInput(workbook, pageNum);
//			Iterator<Entry> finalList = reader.getData();
//			ExcelWriter writer = new ExcelWriter(finalList);
//			writer.writeExcelFile();
	}
}
