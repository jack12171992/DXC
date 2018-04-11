public class Entry implements Comparable<Entry>{
	int key;
	String value;
	
	Entry(int key, String value){
		this.key = key;
		this.value = value;
	}
	
	@Override
	public int compareTo(Entry other) {
//		if(this.key != other.key) {
//			return Integer.compare(other.key, this.key);
//		}
		return Integer.compare(other.key, key);
	}
		
}
