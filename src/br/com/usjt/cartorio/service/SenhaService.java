package br.com.usjt.cartorio.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.usjt.cartorio.dao.SenhaDAO;
import br.com.usjt.cartorio.entity.Senha;
import br.com.usjt.cartorio.entity.Status;

/**
 * Aplicação das regras de negócio e assinatura dos métodos desenvolvidos na
 * classe DAO
 * 
 * @author Lucas Copque - 816112862
 * @category Service
 *
 */
@Service
public class SenhaService {

	private SenhaDAO dao;

	@Autowired
	public SenhaService(SenhaDAO dao) {
		this.dao = dao;
	}

	public Senha criar(Senha senha) {
		senha.setStatus(Status.AGUARDANDO_ATENDIMENTO);
		senha.setEmissao(new Date());
		senha.setInicio(null);
		senha.setFim(null);
		return dao.criar(senha);
	}

	public Senha carregar(int id) {
		return dao.carregar(id);
	}

	public Date getTempoEspera(Senha senha) {
		return dao.getTempoEspera(senha.getId());
	}

	public Date getPrevisao(Senha senha) {

		return dao.getPrevisao(senha.getTempoEspera());
	}

	public Senha proximaSenha() {
		return dao.proximaSenha();
	}

	public Senha senhaAtual() {
		return dao.senhaAtual();
	}

	public void atender(Senha senha) {
		senha.setStatus(Status.EM_ATENDIMENTO);
		senha.setInicio(new Date());
		dao.atender(senha);
	}

	public void finalizar(Senha senha) {
		senha.setStatus(Status.FINALIZADO);
		senha.setFim(new Date());
		senha.setTempoAtendimento(getTimeDiff(senha));

		dao.atender(senha);
	}

	public Date getTimeDiff(Senha senha) {
		return dao.getTimeDiff(senha);
	}

	public boolean isSenhaEmAtendimento() {
		return dao.isSenhaEmAtendimento(Status.EM_ATENDIMENTO);
	}
	
	public boolean haveSenha() {
		return dao.haveSenha();
	}

	public List<Senha> listar() {
		return dao.listar();
	}

}
