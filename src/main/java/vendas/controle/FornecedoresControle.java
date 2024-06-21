package vendas.controle;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;

import org.apache.commons.lang3.StringUtils;

import vendas.modelo.Fornecedor;
import vendas.persistencia.ConstraintException;
import vendas.persistencia.DatabaseException;
import vendas.persistencia.DatabaseSessionFactory;
import vendas.visao.BotaoFornecedoresVisao;

public class FornecedoresControle {
	private BotaoFornecedoresVisao view;

	private List<Fornecedor> list;

	public FornecedoresControle() {
		this.view = new BotaoFornecedoresVisao();

		this.list = new ArrayList<Fornecedor>();

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

		List<Fornecedor> fornecedorsList = sessionFactory.fromSession(session -> {
			return session.createSelectionQuery("from Fornecedor", Fornecedor.class)
					.getResultList();
		});

		var model = this.view.getTableModel();

		model.setRowCount(0);

		fornecedorsList.forEach(fornecedor -> {
			model.addRow(new Object[] {
					fornecedor.getCodigo(),
					fornecedor.getDescricao(),
			});

			this.list.add(fornecedor);
		});
	}

	private void bindEvents() {
		this.view.getBtnRemover().addActionListener(this::btnRemover);
		this.view.getBtnAtualizar().addActionListener(this::btnAtualizar);
		this.view.getBtnAdicionar().addActionListener(this::btnAdicionar);

		this.view.getTable().getSelectionModel().addListSelectionListener(this::tableSelect);
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

	private void btnRemover(ActionEvent event) {
		if (this.view.getTable().getSelectionModel().isSelectionEmpty()) {
			return;
		}

		int selectedRow = this.view.getTable().getSelectedRow();

		var fornecedor = this.list.get(selectedRow);

		try {
			DatabaseSessionFactory.inTransaction(session -> {
				session.remove(fornecedor);
			});
		} catch (DatabaseException e) {
			if (e instanceof ConstraintException) {
				JOptionPane.showMessageDialog(view, "Existem produtos registrados para esse fornecedor, portanto não é possível excluí-lo", "Operação cancelada", JOptionPane.WARNING_MESSAGE);

				return;
			}

			JOptionPane.showMessageDialog(view, e.getMessage(), "Erro na operação", JOptionPane.WARNING_MESSAGE);

			return;
		}

		JOptionPane.showMessageDialog(view, "Fornecedor excluído", "Operação concluída com sucesso", JOptionPane.INFORMATION_MESSAGE);

		this.list.remove(selectedRow);
		this.view.getTableModel().removeRow(selectedRow);
	}

	private void btnAtualizar(ActionEvent event) {
		if (this.view.getTable().getSelectionModel().isSelectionEmpty()) {
			return;
		}
		
		int selectedRow = this.view.getTable().getSelectedRow();

		var fornecedor = this.list.get(selectedRow);

		String descricao = JOptionPane.showInputDialog(view, "Digite o novo nome do fornecedor:", fornecedor.getDescricao());

		if (descricao == null)
			return;
		
		if (StringUtils.isBlank(descricao)) {
			JOptionPane.showMessageDialog(view, "Preencha o novo nome do fornecedor", "Campo vazio", JOptionPane.WARNING_MESSAGE);

			return;
		}

		fornecedor.setDescricao(descricao);

		try {
			DatabaseSessionFactory.inTransaction(session -> {
				session.merge(fornecedor);
			});
		} catch (DatabaseException e) {
			JOptionPane.showMessageDialog(view, e.getMessage(), "Erro na operação", JOptionPane.WARNING_MESSAGE);

			return;
		}
		
		JOptionPane.showMessageDialog(view, "Fornecedor alterado", "Operação concluída com sucesso", JOptionPane.INFORMATION_MESSAGE);

		var tableModel = this.view.getTableModel();
		
		tableModel.removeRow(selectedRow);
		tableModel.insertRow(selectedRow, new Object[] {
				fornecedor.getCodigo(),
				fornecedor.getDescricao()
		});
		
	}

	private void btnAdicionar(ActionEvent event) {
		String descricao = JOptionPane.showInputDialog(view, "Digite o nome do fornecedor:");
		
		if (descricao == null)
			return;

		if (StringUtils.isBlank(descricao)) {
			JOptionPane.showMessageDialog(view, "Preencha o nome do fornecedor", "Campo vazio", JOptionPane.WARNING_MESSAGE);

			return;
		}

		var fornecedor = new Fornecedor(descricao);

		try {
			DatabaseSessionFactory.inTransaction(session -> {
				session.persist(fornecedor);
			});
		} catch (DatabaseException e) {
			JOptionPane.showMessageDialog(view, e.getMessage(), "Erro na operação", JOptionPane.WARNING_MESSAGE);

			return;
		}

		JOptionPane.showMessageDialog(view, "Fornecedor adicionado", "Operação concluída com sucesso", JOptionPane.INFORMATION_MESSAGE);
	
		this.list.add(fornecedor);
		this.view.getTableModel().addRow(new Object[] {
				fornecedor.getCodigo(),
				fornecedor.getDescricao()
		});
	}


	public BotaoFornecedoresVisao getView() {
		return view;
	}
}
