package vendas.persistencia;

public class CredentialException extends DatabaseException {
	public  CredentialException(String errorMessage) {
		super(errorMessage);
	}
	
	public  CredentialException(String errorMessage, Throwable err) {
		super(errorMessage, err);
	}
}
