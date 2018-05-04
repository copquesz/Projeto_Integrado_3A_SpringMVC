package br.com.usjt.cartorio.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.usjt.cartorio.entity.Senha;
import br.com.usjt.cartorio.entity.Status;

/**
 * Métodos de persistência de objetos ao Banco de Dados
 * 
 * @author Lucas Copque - 816112862
 * @category DAO
 *
 */
@Repository
public class SenhaDAO {

	@PersistenceContext
	EntityManager manager;

	/**
	 * Persiste o objeto no Banco de Dados
	 * @param Recebe uma senha como parâmetro
	 * @return
	 */
	public Senha criar(Senha senha) {
		manager.persist(senha);
		return getLast();
	}

	/**
	 * Carrega o último objeto do tipo Senha inserido no Banco de Dados 
	 * @return Retorna um objeto do tipo Senha
	 */
	private Senha getLast() {
		return (Senha) manager.createQuery("SELECT s FROM Senha s ORDER BY s.id DESC", Senha.class).setMaxResults(1)
				.getSingleResult();
	}

	/**
	 * Procura um objeto do tipo Senha
	 * @param Recebe um int id como parâmetro
	 * @return Retorna o objeto encontrado do tipo Senha
	 */
	public Senha carregar(int id) {
		return manager.find(Senha.class, id);
	}

	/**
	 * Este método é responsável calcular o tempo de espera para esta senha ser atendida.
	 * O método calcula a média de tempo médio de atendimento de todos os serviços e multiplica pelo tanto de senhas
	 * que estão aguardando atendimento à frente da senha passada como parâmetro
	 * @param Recebe o id da senha selecionada como parâmetro
	 * @return Retorna o tempo de espera calculado
	 */
	public Date getTempoEspera(int id) {
		// Cria o comando sql que fará o cálculo
		String sql = "select sec_to_time((avg(tempo_medio)) * (select count(s.id) from senha s where s.id <= ? and s.status = 'Aguardando Atendimento')) from servico;";

		// Cria um objeto Query que recebe o comando sql como parâmetro
		Query query = manager.createNativeQuery(sql);

		// Seta os parâmetro para a query
		query.setParameter(1, id);

		// Recebe o resultado da query
		Date result = new Date();
		result = (Date) query.getSingleResult();

		return result;

	}

	/**
	 * Este método é responsável por calcula a data e hora de previsão de atendimento conforme o tempo de espera que foi fornecido
	 * no método getTempoEspera().
	 * Ele recebe o tempo atual e o tempo de espera e retorna o tempo esperado.
	 * @param Recebe o tempo de espera como parâmetro
	 * @return Retorna a data calculada
	 */
	public Date getPrevisao(Date date) {
		// Cria o comando sql que fará o cálculo
		String sql = "SELECT ADDTIME(now(), ?);";

		// Cria um objeto Query que recebe o comando sql como parâmetro
		Query query = manager.createNativeQuery(sql);

		// Seta os parâmetros para a query
		query.setParameter(1, date);

		// Objeto que irá armazenar o resultado
		Date result = new Date();
		result = (Date) query.getSingleResult();

		return result;

	}

	/**
	 * Este método é responsável por selecionar a primeira senha que possui status 'Aguardando Atendimento'
	 * @return Retorna objeto do tipo Senha
	 */
	public Senha proximaSenha() {

		// Cria o comando sql
		String sql = "SELECT s FROM Senha s WHERE s.status = :status";

		// Cria o objeto Query passando o comando sql como parâmetro
		Query query = manager.createQuery(sql, Senha.class);
		
		// Seta os parâmetros para a quer
		query.setParameter("status", Status.AGUARDANDO_ATENDIMENTO);

		// Cria uma lista que irá receber o resultado
		List<Senha> result = new ArrayList<Senha>();

		// Recebe o resultado limitado a uma linha
		result = query.setMaxResults(1).getResultList();

		// Cria o objeto Senha
		Senha senha = new Senha();
		
		// Verifica se o resultado é existente
		try {
			senha = result.get(0);
		} catch (Exception e) {
			senha = null;
		}
		return senha;

	}

	/**
	 * Este método é responsável por selecionar a senha que está sofrendo atendimento
	 * @return Retorna um objeto do tipo Senha
	 */
	public Senha senhaAtual() {

		// Cria o comando sql
		String sql = "SELECT s FROM Senha s WHERE s.status = :status";

		// Cria o objeto Query passando o comando sql como parâmetro
		Query query = manager.createQuery(sql, Senha.class);
		
		// Seta os parâmetros para a query
		query.setParameter("status", Status.EM_ATENDIMENTO);

		// Cria uma lista que irá receber o resultado
		List<Senha> result = new ArrayList<Senha>();

		// Recebe o resultado limitado a uma linha
		result = query.setMaxResults(1).getResultList();

		// Cria um objeto do tipo senha
		Senha senha = new Senha();
		
		// Verifica se o resultado é existente
		try {
			senha = result.get(0);
		} catch (Exception e) {
			senha = null;
		}
		return senha;

	}

	/**
	 * Este método é reponsável por atualizar o status da senha para atendimento e seu horário de início
	 * @param Recebe um objeto do tipo Senha como paraâmetro
	 */
	public void atender(Senha senha) {
		manager.merge(senha);

	}

	/**
	 *Este método é reponsável por atualizar o status da senha para finalizada, seu horário de fim e tempo de atendimento
	 * @param Recebe um objeto do tipo Senha como paraâmetro
	 */
	public void finalizar(Senha senha) {
		manager.merge(senha);

	}

	/**
	 * Este método é responsável por calcular o tempo de atendimento da senha selecionada
	 * campo fim - campo inicio
	 * @param Recebe a senha selecionada como parâmetro
	 * @return Retorna o tempo de atendimento
	 */
	public Date getTimeDiff(Senha senha) {

		// Cria o comando sql
		String sql = "select timediff((select fim from senha where senha.id = ?), (select inicio from senha where senha.id = ?));";

		// Cria um objeto Query passando o comando sql como parâmetro
		Query query = manager.createNativeQuery(sql);

		// Seta os parâmetros para a query
		query.setParameter(1, senha.getId());
		query.setParameter(2, senha.getId());

		// Armazena o resultado da query
		Date result = new Date();
		result = (Date) query.getSingleResult();

		return result;

	}

	/**
	 * Este método é responsavel por verificar se existe alguma senha em atendimento
	 * @param Recebe um status como paraâmetro
	 * @return Retorna verdairo caso exista e falso caso não exista
	 */
	public boolean isSenhaEmAtendimento(String status) {
		
		// Cria o comando sql
		String sql = "SELECT s FROM Senha s WHERE status = :status";

		// Cria um objeto Query passando o comando sql como parâmetro
		Query query = manager.createQuery(sql, Senha.class);
		
		// Seta o parâmetro para a query
		query.setParameter("status", status);

		// Cria uma lista que irá receber o resultado limitado a uma linha
		List<Senha> result = new ArrayList<Senha>();
		result = query.setMaxResults(1).getResultList();

		// Verifica existe ou não um retorno da query
		try {
			result.get(0);
			return true;
		} catch (Exception e) {
			return false;
		}

	}
	
	/**
	 * Verifica se existe alguma senha cadastrada no Banco de Dados
	 * @return Retorna verdadeiro caso existe e falso caso não exista nenhuma senha cadastrada
	 */
	public boolean haveSenha() {
		
		// Cria o comando sql
		String sql = "SELECT s FROM Senha s WHERE status = :status";

		// Cria um objeto Query passando o comando sql como parâmetro
		Query query = manager.createQuery(sql, Senha.class);
		
		// Seta o parâmetro para a query
		query.setParameter("status", Status.AGUARDANDO_ATENDIMENTO);

		// Cria uma lista que irá receber o resultado limitado a uma linha
		List<Senha> result = new ArrayList<Senha>();
		result = query.setMaxResults(1).getResultList();

		// Verifica existe ou não um retorno da query
		try {
			result.get(0);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * Cria uma lista com todas as senhas
	 * @return Retorna uma lista de senhas
	 */
	public List<Senha> listar() {
		return (ArrayList<Senha>) manager.createQuery("SELECT s FROM Senha s where status = 'Aguardando Atendimento'", Senha.class).setMaxResults(5).getResultList();
	}

}
