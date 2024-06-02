package vendas.modelo;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	
	@ManyToOne(fetch = FetchType.LAZY)	
	Fornecedor fornecedor;
	
	public Produto() {};
}
