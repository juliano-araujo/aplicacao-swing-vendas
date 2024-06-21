package vendas.controle;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;

import vendas.modelo.Fornecedor;
import vendas.modelo.Produto;
import vendas.persistencia.DatabaseSessionFactory;
import vendas.visao.FormularioProdutoVisao;

public class FormularioProdutoControle {
	private FormularioProdutoVisao view;
	
	private Consumer<Produto> listener;
	
	public FormularioProdutoControle(Frame frame, Consumer<Produto> listener) {
		this.view = new FormularioProdutoVisao(frame, true);
		
		this.listener = listener; 
		
		this.loadData();
		this.bindEvents();
		
		this.view.setVisible(true);
	}
	
	private void bindEvents() {
		this.view.getBtnConfirmar().addActionListener(this::btnConfirmar);
	}

	private void loadData() {
		var sessionFactory = DatabaseSessionFactory.getSessionFactory();
		
		List<Fornecedor> fornecedorList = sessionFactory.fromSession(session -> {
			var query = session.createQuery("from Fornecedor order by descricao", Fornecedor.class);

			return query.getResultList();
		});

		FornecedorComboItem[] comboFornecedorArr = fornecedorList.stream()
				.map(FornecedorComboItem::new)
				.toArray(FornecedorComboItem[]::new);
		
		JComboBox<FornecedorComboItem> cbFornecedor = this.view.getCbFornecedor();
		cbFornecedor.setModel(new DefaultComboBoxModel<FornecedorComboItem>(comboFornecedorArr));
		cbFornecedor.setSelectedIndex(-1);
	}
	
	private void btnConfirmar(ActionEvent event) {
		String descricao = this.view.getFieldProduto().getText();
		Integer quantidade = this.view.getSpinnerQuantidadeValue();
		
		if (StringUtils.isBlank(descricao)) {
			JOptionPane.showMessageDialog(view, "Insira o nome do produto", "Campo vazio", JOptionPane.WARNING_MESSAGE);
		}
		
		FornecedorComboItem fornecedorCombo = (FornecedorComboItem) this.view.getCbFornecedor().getSelectedItem();
		
		if (fornecedorCombo == null) {
			JOptionPane.showMessageDialog(view, "Insira um fornecedor", "Campo vazio", JOptionPane.WARNING_MESSAGE);
			
			return;
		}
		
		Fornecedor fornecedor = fornecedorCombo.getItem();
		
		String valorStr = this.view.getFieldValor().getText().replace(',', '.');
		
		if (StringUtils.isBlank(valorStr)) {
			JOptionPane.showMessageDialog(view, "Insira um valor", "Campo vazio", JOptionPane.WARNING_MESSAGE);

			return;
		}
		
		BigDecimal valor = BigDecimal.ZERO;
		
		try {
			valor = new BigDecimal(valorStr);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(view, "O valor inserido não possui a formatação correta, insira um valor no formato 000,00", "Formatação incorreta", JOptionPane.WARNING_MESSAGE);

			return;
		}
		
		var produto = new Produto(descricao, valor, quantidade, fornecedor);

 		this.listener.accept(produto);

		this.view.dispose();
	}
	
	public class FornecedorComboItem {
		private Fornecedor fornecedor;

		protected FornecedorComboItem(Fornecedor fornecedor) {
			this.fornecedor = fornecedor;
		}

		@Override
		public String toString() {
			return fornecedor.getDescricao();
		}

		protected Fornecedor getItem() {
			return this.fornecedor;
		}
	}
}
