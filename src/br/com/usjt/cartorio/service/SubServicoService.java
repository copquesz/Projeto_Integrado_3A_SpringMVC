package br.com.usjt.cartorio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.usjt.cartorio.dao.SubServicoDAO;
import br.com.usjt.cartorio.entity.Servico;
import br.com.usjt.cartorio.entity.SubServico;

/**
 * Aplicação das regras de negócio e assinatura dos métodos desenvolvidos na classe DAO
 * @author Lucas Copque - 816112862
 * @category Service
 *
 */
@Service
public class SubServicoService {

	private SubServicoDAO dao;

	@Autowired
	public SubServicoService(SubServicoDAO dao) {
		this.dao = dao;
	}

	public SubServico carregar(int id) {
		return dao.carregar(id);
	}

	public List<SubServico> listar(Servico servico) {
		return dao.listar(servico);
	}

}
