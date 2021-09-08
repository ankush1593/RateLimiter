package exception;

public class ClientIdException extends Exception {

	public ClientIdException() {
	    super("ClientId not present.");
	  }
	
	public ClientIdException(String msg) {
	    super(msg);
	  }
	
}
