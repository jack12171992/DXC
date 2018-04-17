//This is the class that represents the string from each response
public class Response {
	
	private String value;
	boolean alreadyContains;		//This is used to keep track if this response is already being visited or not
	
	Response(String value, boolean flag){
		this.value = value;
		alreadyContains = flag;
	}
	
	public String getValue() {
		return value;
	}
	
	public void changeFlag() {
		if(!alreadyContains) {
			alreadyContains = true;
		}
	}
}
