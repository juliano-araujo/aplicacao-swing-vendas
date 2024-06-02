package vendas.modelo;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Fornecedor {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer codigo;
	
	@Column(length = 45) 
	String descricao;
	
//	@OneToMany(mappedBy = Produto_)
	Set<Produto> produtos;
	
	public Fornecedor() {};

	public Fornecedor(String descricao) {
		super();
		this.descricao = descricao;
	}
}
