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
	@JoinColumn(name = "funcionario_codigo", referencedColumnName = "codigo")
	Funcionario funcionario;
	
	@OneToMany(mappedBy = "venda")
	List<Item> itens;
	
	public Venda() {}

	public Venda(LocalDateTime horario, BigDecimal valorTotal, Funcionario funcionario) {
		super();
		this.horario = horario;
		this.valorTotal = valorTotal;
		this.funcionario = funcionario;
	}
	
}
