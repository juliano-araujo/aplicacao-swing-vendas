package vendas.modelo;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Fornecedor {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer codigo;
	
	@Column(length = 45) 
	String descricao;
	
	@OneToMany(mappedBy = "fornecedor")
	List<Produto> produtos;
	
	public Fornecedor() {};

	public Fornecedor(String descricao) {
		super();
		this.descricao = descricao;
	}
}
