import org.apache.poi.ss.usermodel.*;
import java.util.*;


public class ReadInput {
	final String unWantedCharacters = "\".!,#:?*();~[]{}\\/^_<>=&%@$+|`";
	final int unWantedCharacters_Length = unWantedCharacters.length();
	String input;
	Workbook workbook;
	Sheet dataSheet;
	Iterator<Row> iterator;
	ArrayList<String> excelData = new ArrayList<String>();
	Map<String, Integer> lookupTable = new HashMap<>(125, 0.7f);
	PriorityQueue<Entry> myPQ = new PriorityQueue<>();
	final int maxWordLength = 20;
	final String whiteSpace = " ";
	
	ReadInput(Workbook workbook, int indexOfSheet){
		this.workbook = workbook;
		dataSheet = workbook.getSheetAt(indexOfSheet);
		iterator = dataSheet.iterator();
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
				excelData.add(currentString);
//				System.out.println(currentString);
			}
			++i;
		}
//		System.out.println("The size of List is: " + excelData.size());
		System.out.println(i);
	}
	
	private String trimWordTail(String inputWord) {
		int index = 0;
		String currCharacter;
		String lastChar;
		while(index < unWantedCharacters_Length) {
			if((index + 1) == unWantedCharacters_Length) {
				currCharacter = unWantedCharacters.substring(index);
			}else {
				currCharacter = unWantedCharacters.substring(index, index+1);
			}
			lastChar = inputWord.substring(inputWord.length()-1);
			if(lastChar.equals(currCharacter)) {
				inputWord = inputWord.substring(0, inputWord.length()-1);
				inputWord = trimWordTail(inputWord);
			}
			index++;
		}
		return inputWord;
	}
	
	private String[] inputProcessing(String word) {
		word = trimWordTail(word);
//		System.out.println(word);
		int index = 0;
		String currCharacter;
		int result;
		String[] finalList;
		while(index < unWantedCharacters_Length) {
			if((index + 1) == unWantedCharacters_Length) {
				currCharacter = unWantedCharacters.substring(index);
			}else {
				currCharacter = unWantedCharacters.substring(index, index+1);
			}
//			System.out.println(word);
			
			result = word.indexOf(currCharacter);
			if(result != -1) {
				if(currCharacter.equals("?")) {
					word = word.replaceAll("\\?", "");
				}else if(currCharacter.equals("*")) {
					word = word.replaceAll("\\*", "");
				}else if(currCharacter.equals("(")) {
					word = word.replaceAll("\\(", "");
				}else if(currCharacter.equals(")")) {
					word = word.replaceAll("\\)", "");
				}else if(currCharacter.equals(".")) {
					word = word.replaceAll("\\.", "");
				}else{
//					System.out.println(word);
//					System.out.println(currCharacter);
					word = word.replaceAll(currCharacter, "");  
//					System.out.println(word);
				}
			}   
			index++;
		}
//		System.out.println(word);
		word = word.replaceAll("  "," ");
		
		
		
		finalList = word.split(" ");
//		System.out.println("The size of the list is: " + finalList.length);
		return finalList;
	}
	
	private void findWords(String currentString) {
		if(lookupTable.get(currentString) == null) {
			lookupTable.put(currentString, 1);
		}else {
			lookupTable.computeIfPresent(currentString, (k,v) -> v+1);
		}
	}
	
	private void finalListOfWords() {
		getAllInputs();
//		System.out.println("The size of List is: " + excelData.size());
		Iterator<String> it = excelData.iterator();
		String[] currentListOfWords;
		int sizeOfList;
		String currentString;
		while(it.hasNext()) {
//			System.out.println(it.next());
			currentListOfWords = inputProcessing(it.next());
//			System.out.println(currentListOfWords.length);
			sizeOfList = currentListOfWords.length;
			int index = 0;
			while(index < sizeOfList) {
				currentString = currentListOfWords[index];
				findWords(currentString);
				index++;
			}
		}
	}
	
	private void prioritize() {
		finalListOfWords();
		Iterator<Map.Entry<String, Integer>> it = lookupTable.entrySet().iterator();
		int currentValue;
		String currentString;
		while(it.hasNext()) {
			Map.Entry<String, Integer> pair = (Map.Entry<String, Integer>)it.next();
			currentValue = ((Integer)pair.getValue()).intValue();
			currentString = pair.getKey().toString();
			if(!(currentString.contains("\n")) && !(currentString.equals(""))) {
				myPQ.add(new Entry(currentValue, currentString));
			}
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
	
	public Iterator<Entry> getList() {
		prioritize();
		Iterator<Entry> it = myPQ.iterator();
		return it;
	}
	
	public ArrayList<String> getReponses() {
		return excelData;
	}
}
