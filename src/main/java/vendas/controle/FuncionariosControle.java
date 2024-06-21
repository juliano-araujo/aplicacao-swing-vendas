package vendas.controle;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;

import vendas.modelo.Pessoa;
import vendas.persistencia.ConstraintException;
import vendas.persistencia.DatabaseException;
import vendas.persistencia.DatabaseSessionFactory;
import vendas.visao.BotaoFuncionariosVisao;

public class FuncionariosControle {
	private BotaoFuncionariosVisao view;
	
	private List<Pessoa> list;

	public FuncionariosControle() {
		this.view = new BotaoFuncionariosVisao();

		this.list = new ArrayList<Pessoa>();

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

		List<Pessoa> funcionariosList = sessionFactory.fromSession(session -> {
			return session.createSelectionQuery("from Pessoa where funcao is not null", Pessoa.class)
					.getResultList();
		});

		var model = this.view.getTableModel();
		
		model.setRowCount(0);
		
		funcionariosList.forEach(funcionario -> {
			model.addRow(new Object[] {
					funcionario.getCodigo(),
					funcionario.getNome(),
					funcionario.getCpf(),
					funcionario.getFuncao()
			});
			
			this.list.add(funcionario);
		});
	}

	private void bindEvents() {
		this.view.getBtnRemover().addActionListener(this::btnRemover);
		this.view.getBtnAdicionar().addActionListener(this::btnAdicionar);
		this.view.getBtnAtualizar().addActionListener(this::btnAtualizar);
		
		this.view.getTable().getSelectionModel().addListSelectionListener(this::tableSelect);
	}
	
	private void insertFuncionario(Pessoa funcionario) {
		try {
			DatabaseSessionFactory.inTransaction(session -> {
				session.persist(funcionario);
			});
		} catch (DatabaseException e) {
			JOptionPane.showMessageDialog(view, e.getMessage(), "Erro na operação", JOptionPane.WARNING_MESSAGE);

			return;
		}

		JOptionPane.showMessageDialog(view, "Funcionario Inserido", "Operação concluída", JOptionPane.INFORMATION_MESSAGE);
		
		var model = this.view.getTableModel();
		
		this.list.add(funcionario);
		
		model.addRow(new Object[] {
				funcionario.getCodigo(),
				funcionario.getNome(),
				funcionario.getCpf(),
				funcionario.getFuncao()
		});
	}
	
	private void updateFuncionario(Pessoa newFuncionario) {
		int selectedRow = this.view.getTable().getSelectedRow();

		var oldFuncionario = this.list.get(selectedRow);
		
		newFuncionario.setCodigo(oldFuncionario.getCodigo());
		
		try {
			DatabaseSessionFactory.inTransaction(session -> {
				session.merge(newFuncionario);
			});
		} catch (DatabaseException e) {
			JOptionPane.showMessageDialog(view, e.getMessage(), "Erro na operação", JOptionPane.WARNING_MESSAGE);

			return;
		}
		
		JOptionPane.showMessageDialog(view, "Funcionario alterado", "Operação concluída com sucesso", JOptionPane.INFORMATION_MESSAGE);
		
		this.list.set(selectedRow, newFuncionario);
		
		var tableModel = this.view.getTableModel();
		tableModel.removeRow(selectedRow);
		tableModel.insertRow(selectedRow, new Object[] {
				newFuncionario.getCodigo(),
				newFuncionario.getNome(),
				newFuncionario.getCpf(),
				newFuncionario.getFuncao()
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
		
		var funcionario = this.list.get(selectedRow);
		
		try {
			DatabaseSessionFactory.inTransaction(session -> {
				session.remove(funcionario);
			});
		} catch (DatabaseException e) {
			if (e instanceof ConstraintException) {
				JOptionPane.showMessageDialog(view, "Existem vendas feitas com essa funcionario, portanto não é possível excluí-lo", "Operação cancelada", JOptionPane.WARNING_MESSAGE);
			
				return;
			}
			
			JOptionPane.showMessageDialog(view, e.getMessage(), "Erro na operação", JOptionPane.WARNING_MESSAGE);
			
			return;
		}
		
		JOptionPane.showMessageDialog(view, "Funcionario excluído", "Operação concluída com sucesso", JOptionPane.INFORMATION_MESSAGE);
		
		this.list.remove(selectedRow);
		this.view.getTableModel().removeRow(selectedRow);
	}

	public void btnAdicionar(ActionEvent event) {
		JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this.view);
		new FormularioFuncionarioControle(mainFrame, this::insertFuncionario);
	}
	
	public void btnAtualizar(ActionEvent event) {
		if (this.view.getTable().getSelectionModel().isSelectionEmpty()) {
			return;
		}
		
		int selectedRow = this.view.getTable().getSelectedRow();

		var funcionario = this.list.get(selectedRow);

		JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this.view);
		new FormularioFuncionarioControle(mainFrame, funcionario, this::updateFuncionario);
	}
	
	public BotaoFuncionariosVisao getView() {
		return view;
	}
}
