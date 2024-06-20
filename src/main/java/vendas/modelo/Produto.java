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
public class Produto {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer codigo;
	
	@Column(length = 50) 
	String descricao;
	
	@Column(scale = 9, precision = 2)
	BigDecimal valor;

	Integer quantidade;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "fornecedor_codigo", referencedColumnName = "codigo")
	Fornecedor fornecedor;
	
	public Produto() {}

	public Produto(String descricao, BigDecimal valor, Integer quantidade, Fornecedor fornecedor) {
		super();
		this.descricao = descricao;
		this.valor = valor;
		this.quantidade = quantidade;
		this.fornecedor = fornecedor;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	};
}
