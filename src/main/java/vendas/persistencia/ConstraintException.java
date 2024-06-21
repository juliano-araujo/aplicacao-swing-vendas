package vendas.persistencia;

public class ConstraintException extends DatabaseException {
	public  ConstraintException(String errorMessage) {
		super(errorMessage);
	}
	
	public  ConstraintException(String errorMessage, Throwable err) {
		super(errorMessage, err);
	}
}
