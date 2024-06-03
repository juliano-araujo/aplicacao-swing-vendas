package vendas.modelo;

import java.util.List;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Pessoa {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer codigo;
	
	@NaturalId
	@Column(length = 14) 
	String cpf;
	
	@Basic(optional = false)
	@Column(length = 45) 
	String nome;
	
	@Column(length = 50) 
	String funcao;
	
	@OneToMany(mappedBy = Venda_.VENDEDOR)
	List<Venda> vendas;
	
	@OneToMany(mappedBy = Venda_.COMPRADOR)
	List<Venda> compras;
	
	public Pessoa() {};

	public Pessoa(String cpf, String nome, String funcao) {
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
