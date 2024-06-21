package vendas.controle;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;

import vendas.modelo.Pessoa;
import vendas.persistencia.ConstraintException;
import vendas.persistencia.DatabaseException;
import vendas.persistencia.DatabaseSessionFactory;
import vendas.visao.BotaoClientesVisao;

public class ClientesControle {
	private BotaoClientesVisao view;
	
	private List<Pessoa> list;

	public ClientesControle() {
		this.view = new BotaoClientesVisao();

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

		List<Pessoa> clientesList = sessionFactory.fromSession(session -> {
			return session.createSelectionQuery("from Pessoa where funcao is null", Pessoa.class)
					.getResultList();
		});

		var model = this.view.getTableModel();
		
		model.setRowCount(0);
		
		clientesList.forEach(cliente -> {
			model.addRow(new Object[] {
					cliente.getCodigo(),
					cliente.getNome(),
					cliente.getCpf()
			});
			
			this.list.add(cliente);
		});
	}

	private void bindEvents() {
		this.view.getBtnRemover().addActionListener(this::btnRemover);
		
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
	
	public void btnRemover(ActionEvent event) {
		if (this.view.getTable().getSelectionModel().isSelectionEmpty()) {
			return;
		}
		
		int selectedRow = this.view.getTable().getSelectedRow();
		
		var cliente = this.list.get(selectedRow);
		
		try {
			DatabaseSessionFactory.inTransaction(session -> {
				session.remove(cliente);
			});
		} catch (DatabaseException e) {
			if (e instanceof ConstraintException) {
				JOptionPane.showMessageDialog(view, "Existem vendas feitas com essa cliente, portanto não é possível excluí-lo", "Operação cancelada", JOptionPane.WARNING_MESSAGE);
			
				return;
			}
			
			JOptionPane.showMessageDialog(view, e.getMessage(), "Erro na operação", JOptionPane.WARNING_MESSAGE);
			
			return;
		}
		
		JOptionPane.showMessageDialog(view, "Cliente excluído", "Operação concluída com sucesso", JOptionPane.INFORMATION_MESSAGE);
		
		this.list.remove(selectedRow);
		this.view.getTableModel().removeRow(selectedRow);
	}

	public BotaoClientesVisao getView() {
		return view;
	}
}
