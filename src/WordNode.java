import java.util.*;


//This class represents each unique word in the response and an arraylist of
//response that contains the word
public class WordNode {
	private String value;
	private ArrayList<String> listOfResponses = new ArrayList<String>();
	
	WordNode(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public ArrayList<String> getList(){
		return listOfResponses;
	}
	
	//If the response is already being visited / included in the final output file, 
	//then don't include it, otherwise include the response to the arraylist of the current word node
	public void addResponse(Response response) {
		boolean flag = response.alreadyContains;
		if(!flag) {
			response.changeFlag();
			listOfResponses.add(response.getValue());
		}
	}
}
