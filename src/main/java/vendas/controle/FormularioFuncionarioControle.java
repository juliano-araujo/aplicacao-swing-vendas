package vendas.controle;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;

import vendas.modelo.Pessoa;
import vendas.visao.FormularioFuncionarioVisao;

public class FormularioFuncionarioControle {
    private FormularioFuncionarioVisao view;
    
    private Pessoa funcionario;
    private Consumer<Pessoa> listener;
    
    public FormularioFuncionarioControle(Frame frame, Pessoa funcionario, Consumer<Pessoa> listener) {
		this.view = new FormularioFuncionarioVisao(frame, true);
		
		this.funcionario = funcionario;
		this.listener = listener; 
		
		this.initComponents();
		this.bindEvents();
		
		this.view.setVisible(true);
	}
    
    public FormularioFuncionarioControle(Frame frame, Consumer<Pessoa> listener) {
		this(frame, null, listener);
	}
    
	private void initComponents() {
		if (this.funcionario == null)
			return;
		
		this.view.getFieldNome().setText(this.funcionario.getNome());
		this.view.getFieldCPF().setText(this.funcionario.getCpf());
		this.view.getFieldFuncao().setText(this.funcionario.getFuncao());
		
	}
    
    private void bindEvents() {
        this.view.getBtnConfirmar().addActionListener(this::btnConfirmar);
    }
    
    private void btnConfirmar(ActionEvent event) {
        String nome = this.view.getFieldNome().getText();
        String cpf = this.view.getFieldCPF().getText();
        String funcao = this.view.getFieldFuncao().getText();
                
        if (StringUtils.isBlank(nome)) {
            JOptionPane.showMessageDialog(view, "Insira o nome do cliente", "Campo vazio", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (StringUtils.isBlank(cpf)) {
            JOptionPane.showMessageDialog(view, "Insira o cpf do cliente", "Campo vazio", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (StringUtils.isBlank(funcao)) {
            JOptionPane.showMessageDialog(view, "Insira a função do cliente", "Campo vazio", JOptionPane.WARNING_MESSAGE);
            return;
        }


        var pessoa = new Pessoa(cpf, nome, null);

        this.listener.accept(pessoa);

        this.view.dispose();
    }
}
