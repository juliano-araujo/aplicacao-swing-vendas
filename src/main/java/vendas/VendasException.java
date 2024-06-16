package vendas;

public class VendasException extends Exception {
	public VendasException(String errorMessage, Throwable err) {
		super(errorMessage, err);
	}
}
