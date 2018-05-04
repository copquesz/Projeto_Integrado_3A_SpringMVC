package br.com.usjt.cartorio.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.usjt.cartorio.dao.AtendimentoDAO;
import br.com.usjt.cartorio.entity.Atendimento;
import br.com.usjt.cartorio.entity.Senha;
import br.com.usjt.cartorio.entity.Servico;
import br.com.usjt.cartorio.entity.Status;
import br.com.usjt.cartorio.entity.SubServico;

/**
 * Aplicação das regras de negócio e assinatura dos métodos desenvolvidos na
 * classe DAO
 * 
 * @author Lucas Copque - 816112862
 * @category Service
 *
 */
@Service
public class AtendimentoService {

	private AtendimentoDAO dao;

	@Autowired
	public AtendimentoService(AtendimentoDAO dao) {
		this.dao = dao;
	}

	public Atendimento criar(Atendimento atendimento, SubServico subServico, Senha senha, Servico servico) {
		atendimento.setStatus(Status.AGUARDANDO_ATENDIMENTO);
		atendimento.setInicio(null);
		atendimento.setFim(null);
		atendimento.setSubServico(subServico);
		atendimento.setSenha(senha);
		atendimento.setServico(servico);
		return dao.criar(atendimento);
	}

	public Atendimento carregar(int id) {
		return dao.carregar(id);
	}
	
	public Atendimento getAtual() {
		return dao.getAtual();
	}

	public boolean isProximoAtendimento(Senha senha) {

		return dao.isProximoAtendimento(senha, 1);
	}

	public Atendimento proximoAtendimento(Senha senha) {
		return dao.proximoAtendimento(senha, Status.AGUARDANDO_ATENDIMENTO);
	}

	public void atender(Atendimento atendimento) {
		atendimento.setStatus(Status.EM_ATENDIMENTO);
		atendimento.setInicio(new Date());
		dao.atender(atendimento);
	}

	public void finalizar(Atendimento atendimento) {
		atendimento.setStatus(Status.FINALIZADO);
		atendimento.setFim(new Date());
		dao.atender(atendimento);
	}

	public Date getTempoAtendimento(Atendimento atendimento) {
		return dao.getTempoAtendimento(atendimento.getId());

	}
	
	public Date getTimeDiff(Date date) {
		return dao.getTimeDiff(date);

	}
	
	public List<Atendimento> listar() {
		return dao.listar();
	}

	public List<Atendimento> listar(Senha senha) {
		return dao.listar(senha);
	}

	public List<Atendimento> listar(Senha senha, String status) {
		senha.setStatus(status);
		return dao.listar(senha);
	}

}
