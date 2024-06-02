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

	public Item(Integer quantidade, BigDecimal valorParcial, Produto produto, Venda venda) {
		super();
		this.quantidade = quantidade;
		this.valorParcial = valorParcial;
		this.produto = produto;
		this.venda = venda;
	}
}
