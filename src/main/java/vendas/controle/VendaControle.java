package vendas.controle;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import vendas.modelo.Item;
import vendas.modelo.Pessoa;
import vendas.modelo.Produto;
import vendas.modelo.Venda;
import vendas.persistencia.DatabaseSessionFactory;
import vendas.utils.FormatUtils;
import vendas.visao.BotaoVendasVisao;

public class VendaControle {
	private BotaoVendasVisao view;

	private List<Item> itens;

	public VendaControle() {
		this.view = new BotaoVendasVisao();
		this.itens = new ArrayList<Item>();

		this.bindEvents();
	}
	
	public void activate() {
		this.loadData();

		this.view.setVisible(true);
	}

	private List<Pessoa> carregarPessoas() {
		var sessionFactory = DatabaseSessionFactory.getSessionFactory();

		List<Pessoa> list = sessionFactory.fromSession(session -> {
			return session.createSelectionQuery("from Pessoa", Pessoa.class).getResultList();
		});

		return list;
	}

	private void loadData() {
		List<Pessoa> pessoaList = carregarPessoas();

		PessoaComboItem[] comboClienteArr = pessoaList.stream()
				.map(PessoaComboItem::new)
				.toArray(PessoaComboItem[]::new);

		PessoaComboItem[] comboFuncionarioArr = pessoaList.stream()
				.filter(pessoa -> pessoa.getFuncao() != null)
				.map(PessoaComboItem::new)
				.toArray(PessoaComboItem[]::new);

		JComboBox<PessoaComboItem> cbCliente = this.view.getCBoxCliente();
		cbCliente.setModel(new DefaultComboBoxModel<PessoaComboItem>(comboClienteArr));
		cbCliente.setSelectedIndex(-1);

		JComboBox<PessoaComboItem> cbFuncionario = this.view.getCBoxFuncionario();
		cbFuncionario.setModel(new DefaultComboBoxModel<PessoaComboItem>(comboFuncionarioArr));
		cbFuncionario.setSelectedIndex(-1);
	}

	private void bindEvents() {
		this.view.getBtnAdicionar().addActionListener(this::btnAdicionar);
		this.view.getBtnConcluir().addActionListener(this::btnConcluir);
	}

	public BotaoVendasVisao getView() {
		return view;
	}

	private BigDecimal getValorTotal() {
		return this.itens.stream()
				.reduce(BigDecimal.ZERO,
						(acc, item) -> {
							return acc.add(item.getValorParcial());
						}, (acc1, acc2) -> {
							return acc1.add(acc2);
						});
	}

	private void updateValorTotalView() {
		BigDecimal valorTotal = this.getValorTotal();

		this.view.getTxtValor().setText(FormatUtils.toMonetaryString(valorTotal));
	}

	private void addItem(Produto produto, Integer quantidade) {
		Integer codigoProduto = produto.getCodigo();
		BigDecimal valorProduto = produto.getValor();

		Integer listIndex = IntStream.range(0, this.itens.size())
				.filter(i -> this.itens.get(i).getProduto().getCodigo().equals(codigoProduto))
				.findAny()
				.orElse(-1);

		DefaultTableModel model = this.view.getTbItensModel();

		if (listIndex == -1) {			
			BigDecimal valorParcial = valorProduto.multiply(BigDecimal.valueOf(quantidade));

			Item newItem = new Item(quantidade, valorParcial, produto);
			itens.add(newItem);

			model.addRow(new Object[] {
					produto.getDescricao(),
					FormatUtils.toMonetaryString(valorProduto),
					quantidade,
					FormatUtils.toMonetaryString(valorParcial)
			});

			this.updateValorTotalView();

			return;
		}

		Item oldItem = this.itens.get(listIndex);
		Produto produtoItem = oldItem.getProduto();

		Integer oldQuantidade = oldItem.getQuantidade();
		Integer newQuantidade = oldQuantidade + quantidade;

		BigDecimal valorParcial = valorProduto.multiply(BigDecimal.valueOf(newQuantidade));

		oldItem.setQuantidade(newQuantidade);
		oldItem.setValorParcial(valorParcial);

		model.removeRow(listIndex);

		model.insertRow(listIndex, new Object[] {
				produtoItem.getDescricao(),
				FormatUtils.toMonetaryString(valorProduto),
				newQuantidade,
				FormatUtils.toMonetaryString(valorParcial)
		});

		this.updateValorTotalView();
	}

	private void reset() {
		this.itens = new ArrayList<Item>();

		this.view.getCBoxCliente().setSelectedIndex(-1);
		this.view.getCBoxFuncionario().setSelectedIndex(-1);

		this.view.getTbItensModel().setRowCount(0);
	}
	
	private List<String> getResumoCompras() {
		List<String> resumo = this.itens.stream()
				.map(item -> {
					var produto = item.getProduto();
					
					return produto.getDescricao() + " X " + item.getQuantidade()   + " = " + item.getValorParcial();
				})
				.toList();
		
		return resumo;
	}

	private void btnAdicionar(ActionEvent event) {
		JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this.view);
		var resumo = this.getResumoCompras();
		
		new AdicionarProdutoControle(mainFrame, resumo, this::addItem);
	}

	private void btnConcluir(ActionEvent event) {
		PessoaComboItem vendedorFromCombo = (PessoaComboItem) this.view.getCBoxFuncionario().getSelectedItem();
		PessoaComboItem clienteFromCombo = (PessoaComboItem) this.view.getCBoxCliente().getSelectedItem();

		if (vendedorFromCombo == null) {
			JOptionPane.showMessageDialog(view, "Selecione o funcionário", "Funcionário não selecionado", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (clienteFromCombo == null) {
			JOptionPane.showMessageDialog(view, "Selecione o cliente", "Cliente não selecionado", JOptionPane.WARNING_MESSAGE);
			return;
		}

		Pessoa vendedor = vendedorFromCombo.getItem();
		Pessoa cliente = clienteFromCombo.getItem();

		var horario = LocalDateTime.now();

		BigDecimal valorTotal = this.getValorTotal();

		var venda = new Venda(horario, valorTotal, vendedor, cliente);
		venda.setItens(itens);

		var sessionFactory = DatabaseSessionFactory.getSessionFactory();

		// TODO try catch shenanigans
		sessionFactory.inTransaction(session -> {
			session.persist(venda);
		});

		JOptionPane.showMessageDialog(view, "Venda realizada com sucesso", "Venda finalizada", JOptionPane.INFORMATION_MESSAGE);

		this.reset();
	}

	public class PessoaComboItem {
		private Pessoa pessoa;

		protected PessoaComboItem(Pessoa pessoa) {
			this.pessoa = pessoa;
		}

		@Override
		public String toString() {
			return pessoa.getNome();
		}

		protected Pessoa getItem() {
			return this.pessoa;
		}
	}
}
