package br.com.usjt.cartorio.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.usjt.cartorio.dao.ServicoDAO;
import br.com.usjt.cartorio.entity.Servico;

/**
 * Aplicação das regras de negócio e assinatura dos métodos desenvolvidos na
 * classe DAO
 * 
 * @author Lucas Copque - 816112862
 * @category Service
 *
 */
@Service
public class ServicoService {

	private ServicoDAO dao;

	@Autowired
	public ServicoService(ServicoDAO dao) {
		this.dao = dao;
	}

	public Servico carregar(int id) {
		return dao.carregar(id);
	}

	public Date calculaNovoTempoMedio(Servico servico) {
		return dao.calculaNovoTempoMedio(servico);
	}

	public void atualizaNovoTempoMedio(Servico servico) {
		dao.atualizaNovoTempoMedio(servico);
	}

	public List<Servico> listar() {
		return dao.listar();
	}

}
