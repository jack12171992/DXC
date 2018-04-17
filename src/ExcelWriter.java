import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;


//This class is designed to write the final output excel file
public class ExcelWriter {
	private String[] header1 = {"Words", "Count"};
	private String[] header2 = {"Word", "Response"};
	Iterator it;
	String sheetTag;
	String nameOfTheOutputFile;
	Workbook workbook;
	
	ExcelWriter(Iterator<Entry> it, String sheetTag, String nameOfTheOutputFile){
		this.it = it;
		this.sheetTag = sheetTag;
		this.nameOfTheOutputFile = nameOfTheOutputFile;
	}
	
	ExcelWriter(String nameOfTheOutputFile){
		this.nameOfTheOutputFile = nameOfTheOutputFile;
	}
	
	
	//This method is used to write a list of unique word at column index "1", and 
	//the corresponding frequency at column index "2" (Assuming the starting column index is 1)
	public void writeExcelFileOnlyOnce() throws IOException, FileNotFoundException {
		workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet(sheetTag);
		Row headerRow = sheet.createRow(0);
		int sizeOfHeader = header1.length;
		for(int i = 0; i < sizeOfHeader; i++) {			//Setup the header
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(header1[i]);
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
		
		for(int i = 0; i < sizeOfHeader; i++) {			//Adjust the size of the column to appropriate size
			sheet.autoSizeColumn(i);
		}
		
		FileOutputStream fileOut = new FileOutputStream(nameOfTheOutputFile);
		workbook.write(fileOut);						//Actually write the result in the output file
		fileOut.close();
		workbook.close();								//Close the workbook after done editing
	}
	
	//This method is used to write the result in more than one separated sheet
	//And the final excel file may have more than one sheet that contains different table of results
	public void writeExcelFileMoreThanOnce(Iterator<WordNode> it,String sheetTag, boolean firstTimeCreate, boolean closed) throws IOException, FileNotFoundException{
		if(firstTimeCreate) {						//First time create a new excel workbook
			workbook = new XSSFWorkbook();
		}
		this.sheetTag = sheetTag;					//Assign different sheet name
		this.it = it;								//Assign different iterator that corresponds to different data
		Sheet sheet = workbook.createSheet(sheetTag);
		Row headerRow = sheet.createRow(0);
		int sizeOfHeader = header2.length;
		int rowNum = 1;
		Row row;
		WordNode node;
		ArrayList<String> list;
		Iterator<String> itList;
		boolean firstTime = true;
		for(int i = 0; i < sizeOfHeader; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(header2[i]);
		}
		
		while(it.hasNext()) {
			node = (WordNode)it.next();
			list = node.getList();
			itList = list.iterator();
			row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(node.getValue());
			while(itList.hasNext()) {				//A WordNode might exist in more than one responses
				if(firstTime) {
					firstTime = false;
				}else {
					row = sheet.createRow(rowNum++);
				}
				row.createCell(1).setCellValue(itList.next());
			}
		}
		
		for(int i = 0; i < sizeOfHeader; i++) {			
			sheet.autoSizeColumn(i);
		}
		
		FileOutputStream fileOut = new FileOutputStream(nameOfTheOutputFile);
		workbook.write(fileOut);
		fileOut.close();
		if(closed) {
			workbook.close();
		}
	}
}
