package vendas.modelo;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Funcionario {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer codigo;
	
	@Column(length = 14) 
	String cpf;
	
	@Column(length = 45) 
	String nome;
	
	@Column(length = 50) 
	String funcao;
	
	@OneToMany(mappedBy = "funcionario")
	List<Venda> vendas;
	
	public Funcionario() {};

	public Funcionario(String cpf, String nome, String funcao) {
		super();
		this.cpf = cpf;
		this.nome = nome;
		this.funcao = funcao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
}
