package vendas.controle;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import vendas.modelo.Produto;
import vendas.persistencia.DatabaseSessionFactory;
import vendas.utils.FormatUtils;
import vendas.visao.AdicionarProdutoDialogVisao;

public class AdicionarProdutoControle {
	private AdicionarProdutoDialogVisao view;
	
	private List<Produto> produtoList;
	private BiConsumer<Produto, Integer> listener;
	
	public AdicionarProdutoControle(Frame frame, List<String> resumo, BiConsumer<Produto, Integer> addProductListener) {
		this.view = new AdicionarProdutoDialogVisao(frame, true);
		
		this.produtoList = new ArrayList<Produto>();
		this.listener = addProductListener; 
		
		this.initComponents(resumo);
		this.loadData();
		this.bindEvents();
		
		this.view.setVisible(true);
	}
	
	private void initComponents(List<String> resumo) {
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		listModel.addAll(0, resumo);
		
		this.view.getListResumo().setModel(listModel);
	}

	private void bindEvents() {
		this.view.getBtnContinuar().addActionListener(this::btnContinuar);
	}

	private void loadData() {
		var sessionFactory = DatabaseSessionFactory.getSessionFactory();
		
		List<Produto> produtosList = sessionFactory.fromSession(session -> {
			var query = session.createQuery("from Produto where quantidade > 0 order by descricao", Produto.class);

			return query.getResultList();
		});
		
		DefaultTableModel model = this.view.getTableModel();
		
		produtosList.forEach(produto -> {
			model.addRow(new Object[] {
					produto.getCodigo(),
					produto.getDescricao(),
					FormatUtils.toMonetaryString(produto.getValor()),
					produto.getQuantidade()
					});
			
			this.produtoList.add(produto);
		});
	}
	
	private int getSpinnerValue() {
		var spinner = this.view.getSpinnerQuantidade();
		try {
			spinner.commitEdit();
		}
		catch (ParseException pe) {
			JComponent editor = spinner.getEditor();
			if (editor instanceof DefaultEditor) {
				((DefaultEditor)editor).getTextField().setValue(spinner.getValue());
			}
		}
		return (int) spinner.getValue();
	}
	
	private void btnContinuar(ActionEvent event) {
		JTable table = this.view.getTable();
		
		int selectedRow = table.getSelectedRow();
		
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(view, "Selecione um produto na tabela", "Nenhum produto selecionado", JOptionPane.WARNING_MESSAGE);
			
			return;
		}
		
		var produto = produtoList.get(selectedRow);

		Integer quantidade = this.getSpinnerValue();
		 
		this.listener.accept(produto, quantidade);
		
		this.view.dispose();
	}
}
