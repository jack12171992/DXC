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


public class ReadFile {
	Map<String, Integer> lookupTable = new HashMap<>(125, 0.7f);
	Workbook workbook;
	Sheet dataSheet;
	Iterator<Row> iterator;
	ArrayList<Cell> excelData = new ArrayList<Cell>();
	int numOfRow;
	
	ReadFile(Workbook workbook){
		this.workbook = workbook;
		dataSheet = workbook.getSheetAt(0);
		iterator = dataSheet.iterator();
		numOfRow = 0;
	}
	
	public ArrayList<Cell> finalListOfWords(){
		Cell currentCell = null;
		Row currentRow = null;
		Iterator<Cell> cellIterator = null;
		boolean endOfRow = false;
		String currentString;
		
		while(iterator.hasNext()) {
			currentRow = iterator.next();
			cellIterator = currentRow.iterator();
			while(cellIterator.hasNext()) {
				currentCell = cellIterator.next();
//				System.out.println(currentCell);
//				currentString = (String)currentCell.getStringCellValue();
				currentString = currentCell.getStringCellValue();
				findWords(currentString);
				excelData.add(currentCell);
				if(!endOfRow) {
					numOfRow++;
				}
			}
			if(!endOfRow) {
				endOfRow = true;
			}
		}
		return excelData;
	}
	
	public int numberOfElements() {
		return numOfRow;
	}
	
	private void findWords(String currentString) {
//		System.out.println(currentCell.getCellType());
		if(lookupTable.get(currentString) == null) {
			lookupTable.put(currentString, 1);
		}else {
			lookupTable.computeIfPresent(currentString, (k,v) -> v+1);
		}
	}
	
	public void printTable() {
		Iterator it = lookupTable.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			System.out.println(pair.getKey() + ":    " + pair.getValue());
		}
	}
	
}
