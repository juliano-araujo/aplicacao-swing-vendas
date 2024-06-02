package vendas.modelo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Venda {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer codigo;
	
	LocalDateTime horario;
	
	@Column(name = "valor_total", scale = 9, precision = 2)
	BigDecimal valorTotal;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "pessoa_codigo_vendedor", referencedColumnName = "codigo")
	Pessoa vendedor;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "pessoa_codigo_comprador", referencedColumnName = "codigo")
	Pessoa comprador;
	
	@OneToMany(mappedBy = "venda")
	List<Item> itens;
	
	public Venda() {}

	public Venda(LocalDateTime horario, BigDecimal valorTotal, Pessoa vendedor, Pessoa comprador, List<Item> itens) {
		super();
		this.horario = horario;
		this.valorTotal = valorTotal;
		this.vendedor = vendedor;
		this.comprador = comprador;
		this.itens = itens;
	}
}
