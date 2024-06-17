package vendas.persistencia;

public class PrivilegeException extends DatabaseException {
	public  PrivilegeException(String errorMessage) {
		super(errorMessage);
	}
	
	public  PrivilegeException(String errorMessage, Throwable err) {
		super(errorMessage, err);
	}
}
