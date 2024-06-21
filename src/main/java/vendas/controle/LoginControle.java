package vendas.controle;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import vendas.persistencia.CredentialException;
import vendas.persistencia.DatabaseSessionFactory;
import vendas.visao.LoginVisao;

public class LoginControle {
	private LoginVisao view;
	
	public LoginControle() {
		this.view = new LoginVisao();
	
		this.bindEvents();
		this.view.setVisible(true);
	}
	
	
	private void bindEvents() {
		this.view.getBtnEntrar().addActionListener(this::btnEntrarAction);
		
		this.view.getBtnEntrar().addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				btnEntrarKey(e);
			}
		});
	}
	
	private void cleanPasswordArray(char[] arr) {
		for (int i = 0; i < arr.length; i++) {
			arr[i] = '0';
		}
	}
	
	
	private void btnEntrarAction(ActionEvent event) {
		this.login();
	}
	
	private void btnEntrarKey(KeyEvent event) {
		if (event.getKeyCode() != KeyEvent.VK_ENTER) {
			return;
		}
		
		this.login();
	}
	
	private void login() {
		System.out.println("BTN > Entrar");
		
		var username = this.view.getTxtLogin().getText().strip();
		var senhaArr = this.view.getTxtSenha().getPassword();
		
		if (StringUtils.isBlank(username) || ArrayUtils.isEmpty(senhaArr)) {
			JOptionPane.showMessageDialog(view, "Digite suas credenciais para entrar", "Credenciais Inválidas", JOptionPane.WARNING_MESSAGE);
	
			return;
		}
		
		try {
			var IsSuccesssful = DatabaseSessionFactory.createSessionFactory(username, new String(senhaArr));
			
			if (!IsSuccesssful) {
				JOptionPane.showMessageDialog(view, "Ocorreu um erro na conexão do Banco", "Erro na conexão", JOptionPane.WARNING_MESSAGE);
				
				return;
			}
		} catch (CredentialException e) {
			JOptionPane.showMessageDialog(view, "Verifique seu usuário e senha", "Credenciais Inválidas", JOptionPane.WARNING_MESSAGE);
			
			return;
		}
		
		cleanPasswordArray(senhaArr);
		System.out.println("SYS > Conectado");
		
		this.view.dispose();
		
		new HomeControle();
	}
}
