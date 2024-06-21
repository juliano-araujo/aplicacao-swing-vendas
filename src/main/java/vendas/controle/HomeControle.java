package vendas.controle;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import vendas.visao.HomeVisao;

public class HomeControle {
	private HomeVisao view;
	
	private VendaControle vendaControle;
	private ProdutosControle produtosControle;
	private FornecedoresControle fornecedoresControle;
	private ClientesControle clientesControle;
	
	public HomeControle() {
		this.view = new HomeVisao();
		
		this.vendaControle = new VendaControle();
		this.produtosControle = new ProdutosControle();
		this.fornecedoresControle = new FornecedoresControle();
		this.clientesControle = new ClientesControle();
	
		this.bindEvents();
		this.view.setVisible(true);
	}
	
	
	private void bindEvents() {
		this.view.getBtnVendas().addActionListener(this::btnVendas);
		
		this.view.getBtnFuncionarios().addActionListener(this::btnFuncionarios);
		this.view.getBtnClientes().addActionListener(this::btnClientes);
		this.view.getBtnProdutos().addActionListener(this::btnProdutos);
		this.view.getBtnFornecedores().addActionListener(this::btnFornecedores);
	}
	
	private void setPnlPrincipal(JPanel subView) {
		var pnlPrincipal = this.view.getPnlPrincipal();
		
		if (pnlPrincipal.getComponentCount() != 0) {
			pnlPrincipal.removeAll();
		}
		
		pnlPrincipal.add(subView);
		subView.setVisible(true);
		subView.setSize(437, 483);
		
		this.view.repaint();
		this.view.revalidate();
	}
	
	private void btnVendas(ActionEvent event) {
		System.out.println("BTN > Vendas");
		
		var view = this.vendaControle.getView();
		this.setPnlPrincipal(view);
		
		this.vendaControle.activate();
	}
	
	private void btnFuncionarios(ActionEvent event) {
		System.out.println("BTN > Funcionários");
	}
	
	private void btnClientes(ActionEvent event) {
		System.out.println("BTN > Clientes");
		
		var view = this.clientesControle.getView();
		this.setPnlPrincipal(view);
		
		this.clientesControle.activate();
	}
	
	private void btnProdutos(ActionEvent event) {
		System.out.println("BTN > Produtos");
		
		var view = this.produtosControle.getView();
		this.setPnlPrincipal(view);
		
		this.produtosControle.activate();
	}
	
	private void btnFornecedores(ActionEvent event) {
		System.out.println("BTN > Fornecedores");
		
		var view = this.fornecedoresControle.getView();
		this.setPnlPrincipal(view);
		
		this.fornecedoresControle.activate();
	}

}
