package br.com.usjt.cartorio.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Definição das regras de negócio, atributos, get's e set's
 * 
 * @author Lucas Copque - 816112862
 * @category Entity
 *
 */
@Entity
@Table(name = "servico")
public class Servico implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "nome")
	private String nome;

	@Temporal(TemporalType.TIME)
	@Column(name = "tempo_medio")
	private Date tempoMedio;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getTempoMedio() {
		return tempoMedio;
	}

	public void setTempoMedio(Date tempoMedio) {
		this.tempoMedio = tempoMedio;
	}

	@Override
	public String toString() {
		return "Servico [id=" + id + ", nome=" + nome + ", tempoMedio=" + tempoMedio + "]";
	}

}
