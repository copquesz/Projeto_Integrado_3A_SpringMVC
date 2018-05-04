package br.com.usjt.cartorio.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * Definição das regras de negócio, atributos, get's e set's
 * 
 * @author Lucas Copque - 816112862
 * @category Entity
 *
 */
@Entity
@Table(name = "atendimento")
public class Atendimento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "status")
	private String status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "inicio")
	private Date inicio;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fim")
	private Date fim;

	@Transient
	private Date tempoAtendimento;

	@Transient
	private Date tempoAtras;

	@JoinColumn(name = "senha_id")
	@ManyToOne
	private Senha senha;

	@JoinColumn(name = "subservico_id")
	@ManyToOne
	private SubServico subServico;

	@JoinColumn(name = "servico_id")
	@ManyToOne
	private Servico servico;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFim() {
		return fim;
	}

	public void setFim(Date fim) {
		this.fim = fim;
	}

	public Date getTempoAtendimento() {
		return tempoAtendimento;
	}

	public void setTempoAtendimento(Date tempoAtendimento) {
		this.tempoAtendimento = tempoAtendimento;
	}

	public Date getTempoAtras() {
		return tempoAtras;
	}

	public void setTempoAtras(Date tempoAtras) {
		this.tempoAtras = tempoAtras;
	}

	public Senha getSenha() {
		return senha;
	}

	public void setSenha(Senha senha) {
		this.senha = senha;
	}

	public SubServico getSubServico() {
		return subServico;
	}

	public void setSubServico(SubServico subServico) {
		this.subServico = subServico;
	}

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	@Override
	public String toString() {
		return "Atendimento [id=" + id + ", status=" + status + ", inicio=" + inicio + ", fim=" + fim
				+ ", tempoAtendimento=" + tempoAtendimento + ", tempoAtras=" + tempoAtras + ", senha=" + senha
				+ ", subServico=" + subServico + ", servico=" + servico + "]";
	}

}
