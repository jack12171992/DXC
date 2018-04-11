import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class ExcelWriter {
	private String[] header = {"Words", "Count"};
	Iterator<Entry> it;
	String sheetTag;
	String nameOfTheOutputFile;
	Workbook workbook;
	
	ExcelWriter(Iterator<Entry> it, String sheetTag, String nameOfTheOutputFile){
		this.it = it;
		this.sheetTag = sheetTag;
		this.nameOfTheOutputFile = nameOfTheOutputFile;
	}
	
	public void writeExcelFile() throws IOException, FileNotFoundException {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet(sheetTag);
		Row headerRow = sheet.createRow(0);
		for(int i = 0; i < header.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(header[i]);
		}
		
		int rowNum = 1;
		Row row;
		Entry currentEntry;
		while(it.hasNext()) {
			row = sheet.createRow(rowNum++);
			currentEntry = (Entry)it.next();
			row.createCell(0).setCellValue(currentEntry.value);
			row.createCell(1).setCellValue(currentEntry.key);
		}
		
		for(int i = 0; i < header.length; i++) {
			sheet.autoSizeColumn(i);
		}
		
		FileOutputStream fileOut = new FileOutputStream(nameOfTheOutputFile);
		workbook.write(fileOut);
		fileOut.close();
		workbook.close();
	}
}
