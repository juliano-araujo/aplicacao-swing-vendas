package vendas;

public class PrivilegeException extends VendasException {
	public  PrivilegeException(String errorMessage, Throwable err) {
		super(errorMessage, err);
	}
}
