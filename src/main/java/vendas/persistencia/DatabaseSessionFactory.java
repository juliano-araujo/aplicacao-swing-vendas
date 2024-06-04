package vendas.persistencia;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

import vendas.modelo.Fornecedor;
import vendas.modelo.Item;
import vendas.modelo.Pessoa;
import vendas.modelo.Produto;
import vendas.modelo.Venda;

public class DatabaseSessionFactory {
	
	public static SessionFactory createSessionFactory(String username, String password) {
		Configuration cfg = new Configuration();
		
		cfg = DatabaseSessionFactory.addAnnotatedClasses(cfg);
		
		SessionFactory sessionFactory = cfg
				.setProperty(AvailableSettings.JAKARTA_JDBC_USER, username)
				.setProperty(AvailableSettings.JAKARTA_JDBC_PASSWORD, password)
				.buildSessionFactory();
		
		return sessionFactory;
	}

	private static Configuration addAnnotatedClasses(Configuration cfg) {
		Class<?>[] classes = { 
				Fornecedor.class,
				Item.class,
				Pessoa.class,
				Produto.class,
				Venda.class
				};

		for (int i = 0; i < classes.length; i++) {
			cfg.addAnnotatedClass(classes[i]);
		}

		return cfg;
	}
}
