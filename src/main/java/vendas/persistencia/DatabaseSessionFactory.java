package vendas.persistencia;

import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.GenericJDBCException;
import org.hibernate.exception.SQLGrammarException;

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
	
	public static void inTransaction(Consumer<Session> action) throws DatabaseException {
		var sessionFactory = DatabaseSessionFactory.getSessionFactory();
		
		try {
			sessionFactory.inTransaction(action);

		} catch (ConstraintViolationException e) {
			e.printStackTrace();
			
			throw new ConstraintException("A operação não é possível devido à uma validação do banco de dados", e);
		} catch (SQLGrammarException e) {
			e.printStackTrace();

			if (e.getSQLState().equals("42501"));
				throw new PrivilegeException("Você não tem permissões para realizar essa operação", e);
		} catch (GenericJDBCException e) {
			e.printStackTrace();
			
			if (!e.getSQLState().equals("P0001"))
				throw new DatabaseException("Ocorreu um erro na operação do banco de dados");
			
			var cause = (org.postgresql.util.PSQLException) e.getCause();
			var localizedMessage = cause.getLocalizedMessage();

			var messagePair = extractErrorAndHint(localizedMessage);

			throw new PSQLException(messagePair.getLeft(), messagePair.getRight(), e);
		} catch (HibernateException e) {
			e.printStackTrace();

			throw new DatabaseException("Ocorreu um erro na operação do banco de dados");
		}
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
	
	private static Pair<String, String> extractErrorAndHint(String localizedMessage) {
		String regex  = "ERROR: (.*?)\\n(?:\\s+Dica: (.*?)\\n)?";

		if (localizedMessage.contains("Hint")) {
			regex = "ERROR: (.*?)\\n(?:\\s+Hint: (.*?)\\n)?";
		} else if (localizedMessage.contains("HINT")) {
			regex = "ERROR: (.*?)\\n(?:\\s+HINT: (.*?)\\n)?";
		}

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(localizedMessage);

		if (!matcher.find()) {
			return null;
		}

		var error = matcher.group(1);
		var hint = matcher.group(2);

		return new ImmutablePair<String, String>(error, hint);
	}
}
