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
import javax.persistence.Transient;

/**
 * Definição das regras de negócio, atributos, get's e set's
 * 
 * @author Lucas Copque - 816112862
 * @category Entity
 *
 */
@Entity
@Table(name = "senha")
public class Senha implements Serializable {

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
	@Column(name = "emissao")
	private Date emissao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "inicio")
	private Date inicio;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fim")
	private Date fim;

	@Temporal(TemporalType.TIME)
	@Column(name = "tempo_atendimento")
	private Date tempoAtendimento;

	@Temporal(TemporalType.TIME)
	@Transient
	private Date tempoEspera;

	@Temporal(TemporalType.TIMESTAMP)
	@Transient
	private Date previsao;

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

	public Date getEmissao() {
		return emissao;
	}

	public void setEmissao(Date emissao) {
		this.emissao = emissao;
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

	public Date getTempoEspera() {
		return tempoEspera;
	}

	public void setTempoEspera(Date tempoEspera) {
		this.tempoEspera = tempoEspera;
	}

	public Date getPrevisao() {
		return previsao;
	}

	public void setPrevisao(Date previsao) {
		this.previsao = previsao;
	}

	@Override
	public String toString() {
		return "Senha [id=" + id + ", status=" + status + ", emissao=" + emissao + ", inicio=" + inicio + ", fim=" + fim
				+ ", tempoAtendimento=" + tempoAtendimento + ", tempoEspera=" + tempoEspera + ", previsao=" + previsao
				+ "]";
	}

}
