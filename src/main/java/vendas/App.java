package vendas;

import vendas.modelo.Fornecedor;
import vendas.persistencia.DatabaseSessionFactory;

public class App {

	public static void main(String[] args) {
		var sessionFactory = DatabaseSessionFactory.createSessionFactory("postgres", "jujufoda");
		
		sessionFactory.inTransaction(session -> {
			Fornecedor f = new Fornecedor("Juliano");
			
			session.persist(f);
		});
	}
}
