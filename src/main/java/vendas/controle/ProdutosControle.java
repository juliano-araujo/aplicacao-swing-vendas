package vendas.controle;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;

import vendas.modelo.Produto;
import vendas.persistencia.ConstraintException;
import vendas.persistencia.DatabaseException;
import vendas.persistencia.DatabaseSessionFactory;
import vendas.utils.FormatUtils;
import vendas.visao.BotaoProdutosVisao;

public class ProdutosControle {
	private BotaoProdutosVisao view;
	
	private List<Produto> list;

	public ProdutosControle() {
		this.view = new BotaoProdutosVisao();

		this.list = new ArrayList<Produto>();

		this.initComponents();
		this.bindEvents();
	}
	
	public void activate() {
		this.loadData();

		this.view.setVisible(true);
	}
	
	private void initComponents() {
		this.view.getBtnAtualizar().setEnabled(false);
		this.view.getBtnRemover().setEnabled(false);
	}


	private void loadData() {
		var sessionFactory = DatabaseSessionFactory.getSessionFactory();

		List<Produto> produtosList = sessionFactory.fromSession(session -> {
			return session.createSelectionQuery("select produto from Produto as produto join fetch produto.fornecedor", Produto.class)
					.getResultList();
		});

		var model = this.view.getTableModel();
		
		model.setRowCount(0);
		
		produtosList.forEach(produto -> {
			model.addRow(new Object[] {
					produto.getCodigo(),
					produto.getDescricao(),
					produto.getFornecedor().getDescricao(),
					FormatUtils.toMonetaryString(produto.getValor()),
					produto.getQuantidade()
			});
			
			this.list.add(produto);
		});
	}

	private void bindEvents() {
		this.view.getBtnRemover().addActionListener(this::btnRemover);
		this.view.getBtnAdicionar().addActionListener(this::btnAdicionar);
		this.view.getBtnAtualizar().addActionListener(this::btnAtualizar);
		
		this.view.getTable().getSelectionModel().addListSelectionListener(this::tableSelect);
	}
	
	private void insertProduto(Produto produto) {
		try {
			DatabaseSessionFactory.inTransaction(session -> {
				session.persist(produto);
			});
		} catch (DatabaseException e) {
			JOptionPane.showMessageDialog(view, e.getMessage(), "Erro na operação", JOptionPane.WARNING_MESSAGE);

			return;
		}

		JOptionPane.showMessageDialog(view, "Produto Inserido", "Operação concluída", JOptionPane.INFORMATION_MESSAGE);
		
		var model = this.view.getTableModel();
		
		this.list.add(produto);
		
		model.addRow(new Object[] {
				produto.getCodigo(),
				produto.getDescricao(),
				produto.getFornecedor().getDescricao(),
				FormatUtils.toMonetaryString(produto.getValor()),
				produto.getQuantidade()
		});
	}
	
	private void tableSelect(ListSelectionEvent event) {
		if (this.view.getTable().getSelectionModel().isSelectionEmpty()) {
			this.view.getBtnAtualizar().setEnabled(false);
			this.view.getBtnRemover().setEnabled(false);
			
			return;
		}
		
		this.view.getBtnAtualizar().setEnabled(true);
		this.view.getBtnRemover().setEnabled(true);
		
		return;
	}
	
	public void btnRemover(ActionEvent event) {
		if (this.view.getTable().getSelectionModel().isSelectionEmpty()) {
			return;
		}
		
		int selectedRow = this.view.getTable().getSelectedRow();
		
		var produto = this.list.get(selectedRow);
		
		try {
			DatabaseSessionFactory.inTransaction(session -> {
				session.remove(produto);
			});
		} catch (DatabaseException e) {
			if (e instanceof ConstraintException) {
				JOptionPane.showMessageDialog(view, "Existem vendas feitas com essa produto, portanto não é possível excluí-lo", "Operação cancelada", JOptionPane.WARNING_MESSAGE);
			
				return;
			}
			
			JOptionPane.showMessageDialog(view, e.getMessage(), "Erro na operação", JOptionPane.WARNING_MESSAGE);
			
			return;
		}
		
		JOptionPane.showMessageDialog(view, "Produto excluído", "Operação concluída com sucesso", JOptionPane.INFORMATION_MESSAGE);
		
		this.list.remove(selectedRow);
		this.view.getTableModel().removeRow(selectedRow);
	}

	public void btnAdicionar(ActionEvent event) {
		JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this.view);
		new FormularioProdutoControle(mainFrame, this::insertProduto);
	}
	
	public void btnAtualizar(ActionEvent event) {
		JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this.view);
//		new FormularioProdutoControle(mainFrame, this::updateProduto);
	}
	
	public BotaoProdutosVisao getView() {
		return view;
	}
}
