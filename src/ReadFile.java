import org.apache.poi.ss.usermodel.*;
import java.util.*;


public class ReadFile {
	
	Workbook workbook;
	ArrayList<String> allResponses;
	Map<String, Response> lookupTable = new HashMap<>(125, 0.7f);
	Sheet dataSheet;
	Iterator<Row> iterator;
	ArrayList<WordNode> excelData = new ArrayList<WordNode>();
	
	ReadFile(Workbook workbook, ArrayList<String> response, int indexOfSheet){
		this.workbook = workbook;
		allResponses = response;
		dataSheet = workbook.getSheetAt(indexOfSheet);
		iterator = dataSheet.iterator();
	}
	
//	private void constructNodeList(ArrayList<String> responses) {
//		Iterator<String> it = responses.iterator();
//		while(it.hasNext()) {
//			
//		}
//	}
	
	private void convertToHashTable() {
		Iterator<String> it = allResponses.iterator();
		String currentResponse;
		while(it.hasNext()) {
			currentResponse = it.next();
			lookupTable.put(currentResponse, new Response(currentResponse, false));
		}
	}
	
	private void getAllInputs() {
		Cell currentCell = null;
		Row currentRow = null;
		Iterator<Cell> cellIterator = null;
		String currentString;
		int i = 0;
		while(iterator.hasNext()) {
			currentRow = iterator.next();
			cellIterator = currentRow.iterator();
			while(cellIterator.hasNext()) {
				currentCell = cellIterator.next();
				currentString = currentCell.getStringCellValue();
//				System.out.println(newNode.getValue());
				excelData.add(new WordNode(currentString));
//				System.out.println(currentString);
			}
			++i;
		}
//		System.out.println("The size of List is: " + excelData.size());
		System.out.println(i);
	}
	
	private ArrayList<WordNode> iterateThroughTheList() {
		Iterator<WordNode> it = excelData.iterator();
		Iterator<String> itList;
		WordNode currentWord;
		while(it.hasNext()) {
			currentWord = it.next();
			itList = allResponses.iterator();
			while(itList.hasNext()) {
				findWordInText(itList.next(), currentWord);
			}
		}
		return excelData;
	}
	
	private void findWordInText(String text, WordNode wordNode) {
		String word = wordNode.getValue();
		Response response;
		if(text.contains(word)) {
			response = lookupTable.get(text);
			wordNode.addResponse(response);
		}
	}
	
	public Iterator<WordNode> readSheets() {
		convertToHashTable();
		getAllInputs();
		ArrayList<WordNode> finalList = iterateThroughTheList();
		return finalList.iterator();
	}
}
