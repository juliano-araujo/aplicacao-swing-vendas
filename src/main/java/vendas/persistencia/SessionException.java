package vendas.persistencia;

public class SessionException extends RuntimeException {
	public  SessionException(String errorMessage) {
		super(errorMessage);
	}
	
	public  SessionException(String errorMessage, Throwable err) {
		super(errorMessage, err);
	}
}
