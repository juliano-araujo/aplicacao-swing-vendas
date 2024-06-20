package vendas;

import javax.swing.SwingUtilities;

import vendas.controle.LoginControle;

public class App {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			System.out.println("SYS > INICIADO");
			
			
			new LoginControle();
		});
	}
}
