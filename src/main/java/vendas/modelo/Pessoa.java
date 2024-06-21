package vendas.modelo;

import java.util.List;

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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getFuncao() {
		return funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}

	public List<Venda> getVendas() {
		return vendas;
	}

	public void setVendas(List<Venda> vendas) {
		this.vendas = vendas;
	}

	public List<Venda> getCompras() {
		return compras;
	}

	public void setCompras(List<Venda> compras) {
		this.compras = compras;
	}
}
