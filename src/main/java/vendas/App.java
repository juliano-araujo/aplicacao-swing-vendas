package vendas;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.schema.Action;

import vendas.modelo.*;


public class App {

	public static void main(String[] args) {
		SessionFactory sessionFactory = new Configuration()
				.addAnnotatedClass(Fornecedor.class)
				.addAnnotatedClass(Item.class)
				.addAnnotatedClass(Pessoa.class)
				.addAnnotatedClass(Produto.class)
				.addAnnotatedClass(Venda.class)
				.setProperty(AvailableSettings.JAKARTA_JDBC_URL, "jdbc:postgresql://ep-empty-union-87225778.us-east-2.aws.neon.tech/Trabalho_Vendas?sslmode=require")
				.setProperty(AvailableSettings.JAKARTA_JDBC_USER, "Admin")
				.setProperty(AvailableSettings.JAKARTA_JDBC_PASSWORD, "gA3abY9mwGjU")
	            // Automatic schema export
	            .setProperty(AvailableSettings.JAKARTA_HBM2DDL_DATABASE_ACTION,
	                         Action.ACTION_VALIDATE)
	            // SQL statement logging
	            .setProperty(AvailableSettings.SHOW_SQL, true)
	            .setProperty(AvailableSettings.FORMAT_SQL, true)
	            .setProperty(AvailableSettings.HIGHLIGHT_SQL, true)
	            // Create a new SessionFactory
	            .buildSessionFactory();

	}
}
