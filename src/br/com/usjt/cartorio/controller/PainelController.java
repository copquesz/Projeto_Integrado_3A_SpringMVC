package br.com.usjt.cartorio.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.synth.SynthSeparatorUI;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.usjt.cartorio.entity.Atendimento;
import br.com.usjt.cartorio.entity.Senha;
import br.com.usjt.cartorio.entity.Servico;
import br.com.usjt.cartorio.entity.Status;
import br.com.usjt.cartorio.entity.SubServico;
import br.com.usjt.cartorio.service.AtendimentoService;
import br.com.usjt.cartorio.service.SenhaService;
import br.com.usjt.cartorio.service.ServicoService;
import br.com.usjt.cartorio.service.SubServicoService;

/**
 * Controlador da aplicação responsável por gerenciar as classes que serão instanciadas e seus respectivos métodos de acordo com
 * cada request solicitada
 * 
 * @author Lucas Copque - 816112862
 * @category Controller
 *
 */
@Controller
public class PainelController {
	private ServicoService servicoService;
	private SubServicoService subServicoService;
	private SenhaService senhaService;
	private AtendimentoService atendimentoService;

	@Autowired
	public PainelController(ServicoService servicoService, SubServicoService subServicoService,
			SenhaService senhaService, AtendimentoService atendimentoService) {
		super();
		this.servicoService = servicoService;
		this.subServicoService = subServicoService;
		this.senhaService = senhaService;
		this.atendimentoService = atendimentoService;
	}

	/**
	 * Este é o método principal onde será carregado o painel. 
	 * Este método verifica se existe alguma senha em atendimento
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String inicio(Model model) {
		// Verifica se existe alguma senha em atendimento
		boolean isSenhaEmAtendimento = senhaService.isSenhaEmAtendimento();
		model.addAttribute("isSenhaEmAtendimento", isSenhaEmAtendimento);
		
		// Verifica se existe senha aguardando atendimento
		boolean haveSenha = senhaService.haveSenha();
		model.addAttribute("haveSenha", haveSenha);
		
		Atendimento atendimento = new Atendimento();
		atendimento = atendimentoService.getAtual();
		model.addAttribute("atendimento", atendimento);
		
		List<Senha> senhasAux = new ArrayList<Senha>();
		List<Senha> senhas = new ArrayList<Senha>();
		senhasAux = senhaService.listar();
		
		for (Senha senha : senhasAux) {
			senha.setTempoEspera(senhaService.getTempoEspera(senha));
			senha.setPrevisao(senhaService.getPrevisao(senha));	
			senhas.add(senha);
		}
		model.addAttribute("senhas", senhas);
		
		List<Atendimento> atendimentosAux = new ArrayList<Atendimento>();
		List<Atendimento> atendimentos = new ArrayList<Atendimento>();
		atendimentosAux = atendimentoService.listar();
		System.out.println(atendimentosAux);
		
		for (Atendimento atendimento2 : atendimentosAux) {
			System.out.println(atendimento2.getInicio());
			atendimento2.setTempoAtras(atendimentoService.getTimeDiff(atendimento2.getInicio()));
			atendimentos.add(atendimento2);
		}
		model.addAttribute("atendimentos", atendimentos);
		
		
		return "painel";
	}

	/**
	 * Este método lista todos os serviços disponível para emitir uma senha. 
	 * Este método verifica se existe alguma senha em atendimento
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "listar-servicos", method = RequestMethod.GET)
	public String listarServicos(Model model) {

		// Verifica se existe uma senha em atendimento
		boolean isSenhaEmAtendimento = senhaService.isSenhaEmAtendimento();
		model.addAttribute("isSenhaEmAtendimento", isSenhaEmAtendimento);
		
		// Verifica se existe senha aguardando atendimento
		boolean haveSenha = senhaService.haveSenha();
		model.addAttribute("haveSenha", haveSenha);

		// Carrega um List de Servicos
		List<Servico> listaServicos = new ArrayList<Servico>();
		listaServicos = servicoService.listar();
		model.addAttribute("listaServicos", listaServicos);

		return "listar-servicos";
	}

	/**
	 * Este método método ecebe o serviço selecionado.
	 * Este método cria uma senha para atendimento deste serviço
	 * Este método cria um atendimento para cada sub-serviço do serviço selecionado
	 * Este método verifica se existe uma senha em atendimento
	 * @param servico
	 * @param model
	 * @return
	 */
	@Transactional
	@RequestMapping(value = "emitir-senha", method = RequestMethod.POST)
	public String emitirSenha(@Valid Servico servico, Model model) {

		// Verifica se existe uma senha em atendimento
		boolean isSenhaEmAtendimento = senhaService.isSenhaEmAtendimento();
		model.addAttribute("isSenhaEmAtendimento", isSenhaEmAtendimento);
		
		// Verifica se existe senha aguardando atendimento
		boolean haveSenha = senhaService.haveSenha();
		model.addAttribute("haveSenha", haveSenha);

		// Carrega o Servico selecionado
		servico = servicoService.carregar(servico.getId());	
		model.addAttribute("servico", servico);

		// Carrega um List de SubServicos
		List<SubServico> listaSubServicos = new ArrayList<SubServico>();
		listaSubServicos = subServicoService.listar(servico);
		model.addAttribute("listaSubServicos", listaSubServicos);

		// Cria uma Senha
		Senha senha = new Senha();
		senha = senhaService.criar(senha);

		Atendimento atendimento;
		// Cria um Atendimento para cada SubServico
		for (int i = 0; i < listaSubServicos.size(); i++) {
			atendimento = new Atendimento();
			atendimento = atendimentoService.criar(atendimento, listaSubServicos.get(i), senha,
					listaSubServicos.get(i).getServico());
		}

		// Pega o tempo de espera para atendimento
		senha.setTempoEspera(senhaService.getTempoEspera(senha));

		// Pega a previsão para atendimento da senha
		senha.setPrevisao(senhaService.getPrevisao(senha));		
		
		model.addAttribute("senha", senha);

		return "senha-emitida";
	}

	/**
	 * Este método direciona para a página de cosultar senha
	 * Este método verifica se existe uma senha em atendimento
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "consultar-senha", method = RequestMethod.GET)
	public String consultarSenhaPesquisar(Model model) {

		// Verifica se existe uma senha em atendimento
		boolean isSenhaEmAtendimento = senhaService.isSenhaEmAtendimento();
		model.addAttribute("isSenhaEmAtendimento", isSenhaEmAtendimento);
		
		// Verifica se existe senha aguardando atendimento
		boolean haveSenha = senhaService.haveSenha();
		model.addAttribute("haveSenha", haveSenha);

		return null;

	}

	/**
	 * Este método verifica se existe uma senha recebida como parâmetro
	 * Este método verifica se existe uma senha em atendimento
	 * @param senha
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "consultar-senha", method = RequestMethod.POST)
	public String consultarSenha(Senha senha, Model model) {

		// Verifica se existe uma senha em atendimento
		boolean isSenhaEmAtendimento = senhaService.isSenhaEmAtendimento();
		model.addAttribute("isSenhaEmAtendimento", isSenhaEmAtendimento);
		
		// Verifica se existe senha aguardando atendimento
		boolean haveSenha = senhaService.haveSenha();
		model.addAttribute("haveSenha", haveSenha);

		// Carrega a senha passando o id recebido da request
		senha = senhaService.carregar(senha.getId());
		try {
			senha.setTempoEspera(senhaService.getTempoEspera(senha));
			senha.setPrevisao(senhaService.getPrevisao(senha));
		} catch (Exception e) {
			// TODO: handle exception
		}		
		model.addAttribute("senha", senha);

		// Verifica o status da senha e atribui um boolean para a jsp rabalhar com jstl
		if (senha.getStatus().equals("Aguardando Atendimento")) {
			model.addAttribute("status1", true);
		}

		else if (senha.getStatus().equals("Em Atendimento")) {
			model.addAttribute("status2", true);
		}

		else {
			model.addAttribute("status3", true);
		}
	
		// Carrega a lista de atendimento para a senha seleciona
		List<Atendimento> atendimentos = new ArrayList<Atendimento>();
		atendimentos = atendimentoService.listar(senha, Status.AGUARDANDO_ATENDIMENTO);
		model.addAttribute("atendimentos", atendimentos);

		Servico servico = new Servico();
		try {
			servico = atendimentos.get(0).getServico();
		} catch (Exception e) {
			// TODO: handle exception
		}		
		model.addAttribute("servico", servico);

		return "senha-consultada";

	}

	/**
	 * Este método exibe a próxima senha para atendimento
	 * Este método verifica se existe uma senha em atendimento
	 * Este método verifica se existe um sub-serviço para aguardando atendimento para a senha selecionada
	 * @param model
	 * @return
	 */
	@Transactional
	@RequestMapping(value = "proxima-senha", method = RequestMethod.GET)
	public String proximaSenha(Model model) {

		// Verifica se existe uma senha em atendimento
		boolean isSenhaEmAtendimento = senhaService.isSenhaEmAtendimento();
		model.addAttribute("isSenhaEmAtendimento", isSenhaEmAtendimento);
				
		// Verifica se existe senha aguardando atendimento
		boolean haveSenha = senhaService.haveSenha();
		model.addAttribute("haveSenha", haveSenha);
		
		Senha senha = new Senha();
		senha = senhaService.proximaSenha();
		model.addAttribute("senha", senha);

		// Verifica se possui próximo sub-serviço para atendimento
		boolean isProximoAtendimento = atendimentoService.isProximoAtendimento(senha);
		senhaService.atender(senha);

		
		// Lista todos os atendimentos existentes para a senha selecionada
		List<Atendimento> atendimentos = new ArrayList<Atendimento>();
		atendimentos = atendimentoService.listar(senha, Status.AGUARDANDO_ATENDIMENTO);
		model.addAttribute("atendimentos", atendimentos);

		// Carrega o servico da senha selecionada
		Servico servico = new Servico();
		servico = atendimentos.get(0).getServico();
		model.addAttribute("servico", servico);

		return "proxima-senha";
	}

	/**
	 * Este método exibe a senha que esta atualmente em atendimento
	 * Este método verifica se existe uma senha em atendimento
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "senha-atual", method = RequestMethod.GET)
	public String senhaAtual(Model model) {

		// Verifica se existe uma senha em atendimento
		boolean isSenhaEmAtendimento = senhaService.isSenhaEmAtendimento();
		model.addAttribute("isSenhaEmAtendimento", isSenhaEmAtendimento);

		// Carrega a senha que esta em atendimento
		Senha senha = new Senha();
		senha = senhaService.senhaAtual();
		model.addAttribute("senha", senha);
		
		// Verifica se existe um próximo sub-serviço aguardando atendimento para a senha selecionada
		boolean isProximoAtendimento = atendimentoService.isProximoAtendimento(senha);
		model.addAttribute("isProximoAtendimento", isProximoAtendimento);

		// Lista todos os atendimentos da senha selecionada
		List<Atendimento> atendimentos = new ArrayList<Atendimento>();
		atendimentos = atendimentoService.listar(senha, Status.AGUARDANDO_ATENDIMENTO);
		model.addAttribute("atendimentos", atendimentos);

		// Carrega o servico da senha selecionada
		Servico servico = new Servico();
		servico = atendimentos.get(0).getServico();
		model.addAttribute("servico", servico);

		return "proxima-senha";
	}

	/**
	 * Este método atende o próximo sub-serviço 
	 * Este método verifica se existe uma senha em atendimento
	 * Este método verifica se existe um próximo atendimento para atender
	 * @param model
	 * @return
	 */
	@Transactional
	@RequestMapping(value = "atender-subservico", method = RequestMethod.GET)
	public String atenderSubServico(Model model) {

		// Verifica se existe uma senha em atendimento
		boolean isSenhaEmAtendimento = senhaService.isSenhaEmAtendimento();
		model.addAttribute("isSenhaEmAtendimento", isSenhaEmAtendimento);

		// Carrega a senha atual que esta em atendimento
		Senha senha = new Senha();
		senha = senhaService.senhaAtual();
		model.addAttribute("senha", senha);

		// Verifica se possui um atendimento para ser atendido
		// Carrega o atendimento que será atendido
		// Altera o status do atendimento para 'Em Atendimento'
		Atendimento atendimento = new Atendimento();
		if (atendimentoService.isProximoAtendimento(senha)) {
			atendimento = atendimentoService.proximoAtendimento(senha);
			atendimentoService.atender(atendimento);

		}
		model.addAttribute("atendimento", atendimento);

		return "proximo-atendimento";
	}

	/**
	 * Este método finaliza o atendimento atual
	 * @param atendimento
	 * @param model
	 * @return Redireciona para o método senhaAtual(Model model)
	 */
	@Transactional
	@RequestMapping(value = "finalizar-subservico", method = RequestMethod.POST)
	public String finalizarSubServico(Atendimento atendimento, Model model) {

		// Carrega o atendimento recebido da request
		atendimento = atendimentoService.carregar(atendimento.getId());
		// Altera o status do atendimento para finalizado
		atendimentoService.finalizar(atendimento);

		return "redirect:senha-atual";
	}

	/**
	 * Este método finaliza a senha após atender todos seus sub-serviços
	 * Este método verifica se existe uma senha em atendimento
	 * @param model
	 * @return
	 */
	@Transactional
	@RequestMapping(value = "finalizar-senha", method = RequestMethod.GET)
	public String finalizarSenha(Model model) {
		
		// Verifica se existe uma senha em atendimento
		boolean isSenhaEmAtendimento = senhaService.isSenhaEmAtendimento();
		model.addAttribute("isSenhaEmAtendimento", isSenhaEmAtendimento);
				
		// Verifica se existe senha aguardando atendimento
		boolean haveSenha = senhaService.haveSenha();
		model.addAttribute("haveSenha", haveSenha);

		// Carrega a senha que esta em atendimento
		// Altera o status da senha para finaliada
		Senha senha = new Senha();
		senha = senhaService.senhaAtual();
		senhaService.finalizar(senha);

		// Carrega um array auxiliar com todos os atendimentos para a senha selecionada
		List<Atendimento> atendimentosAux = new ArrayList<Atendimento>();
		List<Atendimento> atendimentos = new ArrayList<Atendimento>();
		atendimentosAux = atendimentoService.listar(senha);

		// Atribui o tempo de atendimento utilizado para cada atendimento e seta o novo objeto para o novo array
		for (Atendimento atendimento : atendimentosAux) {
			atendimento.setTempoAtendimento(atendimentoService.getTempoAtendimento(atendimento));
			atendimentos.add(atendimento);
		}
		model.addAttribute("atendimentos", atendimentos);

		// Carrega o serviço da senha selecionada e atualiza o seu tempo médio de atendimento de acordo com o tempo
		// o tempo utilizado para atender todos os sub-serviços
		Servico servico = new Servico();
		servico = servicoService.carregar(atendimentos.get(0).getServico().getId());
		servico.setTempoMedio(servicoService.calculaNovoTempoMedio(servico));
		servicoService.atualizaNovoTempoMedio(servico);

		return "senha-finalizada";
	}

}
