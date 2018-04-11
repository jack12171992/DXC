import org.apache.poi.ss.usermodel.*;
import java.util.*;


public class ReadFile {
	Map<String, Integer> lookupTable = new HashMap<>(125, 0.7f);
	Workbook workbook;
	Sheet dataSheet;
	Iterator<Row> iterator;
	ArrayList<Cell> excelData = new ArrayList<Cell>();
//	TreeMap<Integer, String> myTreeMap = new TreeMap<>();
	PriorityQueue<Entry> myPQ = new PriorityQueue<>();
	int numOfRow;
	final int maxWordLength = 20;
	final String whiteSpace = " ";
	
	
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

		
		while(iterator.hasNext()) {
			currentRow = iterator.next();
			cellIterator = currentRow.iterator();
			while(cellIterator.hasNext()) {
				currentCell = cellIterator.next();
//				System.out.println(currentCell);
//				currentString = (String)currentCell.getStringCellValue();
//				currentString = currentCell.getStringCellValue();
				findWords(currentCell);
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
	
	public int numberOfElements_In_A_Row() {
		return numOfRow;
	}
	
	private void findWords(Cell currentCell) {
//		System.out.println(currentCell.getCellType());
		String currentString = currentCell.getStringCellValue();
		if(lookupTable.get(currentString) == null) {
			lookupTable.put(currentString, 1);
		}else {
			lookupTable.computeIfPresent(currentString, (k,v) -> v+1);
		}
	}
	
	private void prioritize() {
		Iterator<Map.Entry<String, Integer>> it = lookupTable.entrySet().iterator();
		int currentValue;
		String currentString;
		while(it.hasNext()) {
			Map.Entry<String, Integer> pair = (Map.Entry<String, Integer>)it.next();
			currentValue = ((Integer)pair.getValue()).intValue();
			currentString = pair.getKey().toString();
			myPQ.add(new Entry(currentValue, currentString));
		}	
	}
	
	private void printText(String currentString, int currentCount) {
		int whiteSpaces = maxWordLength - currentString.length();
		System.out.print(currentString);
		for(int i = 0; i < whiteSpaces; i++) {
			System.out.print(whiteSpace);
		}
		System.out.print(currentCount);
		System.out.println();
	}
	
	public void printTable() {
		prioritize();
		Iterator<Entry> it = myPQ.iterator();
		Entry currentEntry;
		System.out.println("Value:              Count:");
		while(it.hasNext()) {
			currentEntry = (Entry) it.next(); 
			printText(currentEntry.value, currentEntry.key);
		}
	}
}
