package vendas.modelo;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Item {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer codigo;

	Integer quantidade;
	
	@Column(name = "valor_parcial", scale = 9, precision = 2)
	BigDecimal valorParcial;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "produto_codigo", referencedColumnName = "codigo")
	Produto produto;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "venda_codigo", referencedColumnName = "codigo")
	Venda venda;
	
	public Item() {}

	public Item(Integer quantidade, BigDecimal valorParcial, Produto produto) {
		super();
		this.quantidade = quantidade;
		this.valorParcial = valorParcial;
		this.produto = produto;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public Integer getQuantidade() {
		return quantidade;
	}
	

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade ;
	}

	public BigDecimal getValorParcial() {
		return valorParcial;
	}
	
	public void setValorParcial(BigDecimal valorParcial) {
		this.valorParcial = valorParcial;
	}

	public Produto getProduto() {
		return produto;
	}

	public Venda getVenda() {
		return venda;
	}
	
	public void setVenda(Venda venda) {
		this.venda = venda;
	}
}
