//This class is used to keep track of the frequency of each unique word
public class Entry implements Comparable<Entry>{
	int key;
	String value;
	
	Entry(int key, String value){
		this.key = key;
		this.value = value;
	}
	
	//Override the compareTo method so the priority queue is worked as a MaxHeap, firstly by the frequency of the word
	//If there's a tie, then sorted by alphabetical order
	@Override
	public int compareTo(Entry other) {
		if(this.key != other.key) {
			return Integer.compare(other.key, this.key);
		}
		return Integer.compare(other.key, key);
	}
		
}
