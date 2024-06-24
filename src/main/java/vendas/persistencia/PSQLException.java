package vendas.persistencia;

public class PSQLException extends DatabaseException {
	private String hint;
	
	public  PSQLException(String errorMessage, String hint) {
		super(errorMessage);
		
		this.hint = hint;
	}
	
	public  PSQLException(String errorMessage, String hint, Throwable err) {
		super(errorMessage, err);
		
		this.hint = hint;
	}

	public String getHint() {
		return hint;
	}
}
