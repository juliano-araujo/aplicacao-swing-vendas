package vendas.controle;

import java.util.ArrayList;
import java.util.List;

import vendas.modelo.Produto;
import vendas.persistencia.DatabaseSessionFactory;
import vendas.utils.FormatUtils;
import vendas.visao.BotaoProdutosVisao;

public class ProdutosControle {
	private BotaoProdutosVisao view;
	
	private List<Produto> list;

	public ProdutosControle() {
		this.view = new BotaoProdutosVisao();
		
		this.list = new ArrayList<Produto>();

		this.bindEvents();
	}
	
	public void activate() {
		this.loadData();

		this.view.setVisible(true);
	}

	private List<Produto> carregarProdutos() {
		var sessionFactory = DatabaseSessionFactory.getSessionFactory();

		List<Produto> list = sessionFactory.fromSession(session -> {
			return session.createSelectionQuery("select produto from Produto as produto join fetch produto.fornecedor", Produto.class)
					.getResultList();
		});

		return list;
	}

	private void loadData() {
		List<Produto> produtos = carregarProdutos();
		
		var model = this.view.getTableModel();
		
		
		produtos.forEach(produto -> {
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
	}

	public BotaoProdutosVisao getView() {
		return view;
	}

	
}
