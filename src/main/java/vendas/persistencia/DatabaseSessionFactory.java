package vendas.persistencia;

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

import vendas.modelo.Fornecedor;
import vendas.modelo.Item;
import vendas.modelo.Pessoa;
import vendas.modelo.Produto;
import vendas.modelo.Venda;

public class DatabaseSessionFactory {
	private static SessionFactory sessionFactory;
	
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			throw new SessionException("A sessão não foi iniciada");
		}
		
		return sessionFactory;
	}
	
	public static boolean createSessionFactory(String username, String password) throws CredentialException {
		try {
			var cfg = new Configuration();
			
			DatabaseSessionFactory.addAnnotatedClasses(cfg);
			
			var sessionFactory = cfg
					.setProperty(AvailableSettings.JAKARTA_JDBC_USER, username)
					.setProperty(AvailableSettings.JAKARTA_JDBC_PASSWORD, password)
					.buildSessionFactory();
			
			DatabaseSessionFactory.sessionFactory = sessionFactory;
			
			return true;
		} catch (HibernateException e) {
			if (e.getCause() instanceof JDBCException jdbcException) {
				var sqlState = jdbcException.getSQLState();
				
				if (sqlState.equals("28P01")) {
					throw new CredentialException("Senha está incorreta");
				}
			}
			
			e.printStackTrace();
			System.out.println(e);
			
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			
			return false;
		}
	}

	private static void addAnnotatedClasses(Configuration cfg) {
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
	}
}
