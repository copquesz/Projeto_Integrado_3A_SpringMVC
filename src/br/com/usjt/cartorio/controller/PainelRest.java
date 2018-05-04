package br.com.usjt.cartorio.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.usjt.cartorio.entity.Atendimento;
import br.com.usjt.cartorio.entity.Senha;
import br.com.usjt.cartorio.entity.Servico;
import br.com.usjt.cartorio.entity.SubServico;
import br.com.usjt.cartorio.service.AtendimentoService;
import br.com.usjt.cartorio.service.SenhaService;
import br.com.usjt.cartorio.service.ServicoService;
import br.com.usjt.cartorio.service.SubServicoService;

/**
 * API RESTfull/JSON do projeto utilizada para acessar e enviar dados pelo aplicativo
 * 
 * @author Lucas Copque - 816112862
 * @category API RESTfull/JSON
 *
 */
@RestController
public class PainelRest {

	private ServicoService servicoService;
	private SubServicoService subServicoService;
	private SenhaService senhaService;
	private AtendimentoService atendimentoService;

	@Autowired
	public PainelRest(ServicoService servicoService, SubServicoService subServicoService, SenhaService senhaService,
			AtendimentoService atendimentoService) {
		super();
		this.servicoService = servicoService;
		this.subServicoService = subServicoService;
		this.senhaService = senhaService;
		this.atendimentoService = atendimentoService;
	}

	/**
	 * Este m�todo exibe um ArrayList de JSON com todos os servi�os existentes no banco de dados
	 * @param model
	 * @return Retorna um Array JSON com os servi�os
	 */
	@RequestMapping(method = RequestMethod.GET, value = "rest/servicos")
	public @ResponseBody List<Servico> listarServicos(Model model) {
		
		// Lista todos os servi�os		
		List<Servico> servicos = null;
		servicos = servicoService.listar();
		return servicos;
	}

	/**
	 * Este m�todo utilizado para emitir uma senha e seus atendimentos atrav�s da API
	 * @param Recebe um int id do Servi�o como par�metro
	 * @return Retorna um Array JSON com todos os atendimentos criados, contendo a Senha, SubServi�os e Servi�o.
	 */
	@Transactional
	@RequestMapping(method = RequestMethod.POST, value = "rest/emitir-senha/{id}")
	public ResponseEntity<List<Atendimento>> emitirSenha(@PathVariable("id") int id) {

		// Carrega o servi�o selecionado passando o id como par�metro
		Servico servico = new Servico();
		servico = servicoService.carregar(id);

		// Carrega uma lista com todo os subservi�os do servi�o selecionado
		List<SubServico> subServicos = new ArrayList<SubServico>();
		subServicos = subServicoService.listar(servico);

		// Cria uma nova senha para atendimento deste servi�o
		Senha senha = new Senha();
		senha = senhaService.criar(senha);

		// Cria uma lista de atendimentos para cada sub-servi�o existente para atendimento deste servi�o e os registra no Banco de Dados
		Atendimento atendimento = null;
		List<Atendimento> atendimentos = new ArrayList<Atendimento>();
		// Cria um Atendimento para cada SubServico
		for (int i = 0; i < subServicos.size(); i++) {
			atendimento = new Atendimento();
			atendimento = atendimentoService.criar(atendimento, subServicos.get(i), senha,
					subServicos.get(i).getServico());
			atendimentos.add(atendimento);
		}

		return new ResponseEntity<List<Atendimento>>(atendimentos, HttpStatus.OK);
	}

}
