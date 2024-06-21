package vendas.controle;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;

import vendas.modelo.Fornecedor;
import vendas.modelo.Produto;
import vendas.persistencia.DatabaseSessionFactory;
import vendas.utils.FormatUtils;
import vendas.visao.FormularioProdutoVisao;

public class FormularioProdutoControle {
	private FormularioProdutoVisao view;
	
	private Produto produto;
	private Consumer<Produto> listener;
	
	
	public FormularioProdutoControle(Frame frame, Produto produto, Consumer<Produto> listener) {
		this.view = new FormularioProdutoVisao(frame, true);
		
		this.produto = produto;		
		this.listener = listener; 
		
		this.initComponents();
		this.bindEvents();
		
		this.view.setVisible(true);
	}
	
	public FormularioProdutoControle(Frame frame, Consumer<Produto> listener) {
		this(frame, null, listener);
	}

	private void bindEvents() {
		this.view.getBtnConfirmar().addActionListener(this::btnConfirmar);
	}

	
	private void initComponents() {
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
		
		if (this.produto == null)
			return;
		
		String valorStr = FormatUtils.toMonetaryString(this.produto.getValor(), false);
		
		this.view.getFieldProduto().setText(this.produto.getDescricao());
		this.view.getSpinnerQuantidade().setValue(this.produto.getQuantidade());
		this.view.getFieldValor().setText(valorStr);
		
		int listIndex = IntStream.range(0, fornecedorList.size())
				.filter(i -> fornecedorList.get(i).getCodigo().equals(this.produto.getFornecedor().getCodigo()))
				.findAny()
				.orElse(-1);

		cbFornecedor.setSelectedIndex(listIndex);
	}
	
	private void btnConfirmar(ActionEvent event) {
		String descricao = this.view.getFieldProduto().getText();
		Integer quantidade = this.view.getSpinnerQuantidadeValue();
		
		if (StringUtils.isBlank(descricao)) {
			JOptionPane.showMessageDialog(view, "Insira o nome do produto", "Campo vazio", JOptionPane.WARNING_MESSAGE);
			
			return;
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
