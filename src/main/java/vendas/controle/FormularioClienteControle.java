package vendas.controle;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;
import javax.swing.JOptionPane;
import org.apache.commons.lang3.StringUtils;
import vendas.modelo.Pessoa;
import vendas.visao.FormularioClienteVisao;

public class FormularioClienteControle {
    private FormularioClienteVisao view;
    
    private Pessoa cliente;
    private Consumer<Pessoa> listener;
    
    public FormularioClienteControle(Frame frame, Pessoa cliente, Consumer<Pessoa> listener) {
		this.view = new FormularioClienteVisao(frame, true);
		
		this.cliente = cliente;
		this.listener = listener; 
		
		this.initComponents();
		this.bindEvents();
		
		this.view.setVisible(true);
	}
    
    public FormularioClienteControle(Frame frame, Consumer<Pessoa> listener) {
    	this(frame, null, listener);
    }
    
	private void initComponents() {
		if (this.cliente == null)
			return;
		
		this.view.getFieldNome().setText(this.cliente.getNome());
		this.view.getFieldCPF().setText(this.cliente.getCpf());
	}
    
    private void bindEvents() {
        this.view.getBtnConfirmar().addActionListener(this::btnConfirmar);
    }
    
    private void btnConfirmar(ActionEvent event) {
        String nome = this.view.getFieldNome().getText();
        String cpf = this.view.getFieldCPF().getText();

        if (StringUtils.isBlank(nome)) {
            JOptionPane.showMessageDialog(view, "Insira o nome do cliente", "Campo vazio", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (StringUtils.isBlank(cpf)) {
            JOptionPane.showMessageDialog(view, "Insira o cpf do cliente", "Campo vazio", JOptionPane.WARNING_MESSAGE);
            return;
        }

        var pessoa = new Pessoa(cpf, nome, null);

        this.listener.accept(pessoa);

        this.view.dispose();
    }
}
