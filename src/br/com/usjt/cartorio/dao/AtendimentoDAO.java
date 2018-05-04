package br.com.usjt.cartorio.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.usjt.cartorio.entity.Atendimento;
import br.com.usjt.cartorio.entity.Senha;
import br.com.usjt.cartorio.entity.Servico;
import br.com.usjt.cartorio.entity.Status;
import br.com.usjt.cartorio.entity.SubServico;

/**
 * M�todos de persist�ncia de objetos ao Banco de Dados
 * 
 * @author Lucas Copque - 816112862
 * @category DAO
 *
 */
@Repository
public class AtendimentoDAO {

	@PersistenceContext
	EntityManager manager;

	/**
	 * Persiste o objeto no Banco de Dados
	 * @param Recebe um atendimento como par�metro
	 * @return
	 */
	public Atendimento criar(Atendimento atendimento) {
		manager.persist(atendimento);
		return getLast();
	}

	/**
	 * Seleciona o ultimo objeto do tipo Atendimento incluso no Banco de Dados
	 * @return
	 */
	private Atendimento getLast() {
		return (Atendimento) manager.createQuery("SELECT a FROM Atendimento a ORDER BY a.id DESC", Atendimento.class)
				.setMaxResults(1).getSingleResult();
	}

	/**
	 * Procura um objeto do tipo Atendimento no Banco de Dados
	 * @param Recebe um int id como para�metro
	 * @return
	 */
	public Atendimento carregar(int id) {
		return manager.find(Atendimento.class, id);
	}
	
	/**
	 * Retorna o atendimento atual para ser exibido no painel
	 * @return
	 */
	public Atendimento getAtual() {
		// Cria o comando sql
		String sql = "SELECT a FROM Atendimento a WHERE status != :status order by a.id desc";

		// Cria o objeto Query passando o comando sql como par�metro
		Query query = manager.createQuery(sql, Atendimento.class);
		
		//Seta os par�metros para a query		
		query.setParameter("status", Status.AGUARDANDO_ATENDIMENTO);

		// Cria uma lista que receber� o resultado limitado a uma �nica linha
		List<Atendimento> result = new ArrayList<Atendimento>();
		result = query.setMaxResults(1).getResultList();

		Atendimento atendimento;
		// Verifica se o resultado possui retorno
		try {
			atendimento = result.get(0);			
		} catch (Exception e) {
			atendimento = null;
		}
		
		return atendimento;

	}

	/**
	 * Verifica se existe um pr�ximo sub-servico para atendimento para aquela senha
	 * @param Recebe uma senha e um status como par�metro
	 * @return Retorna verdadeiro caso existe e falso caso n�o exista
	 */
	public boolean isProximoAtendimento(Senha senha, int status) {
		
		// Cria o comando sql
		String sql = "SELECT a FROM Atendimento a WHERE a.senha = :senha and status = :status";

		// Cria o objeto Query passando o comando sql como par�metro
		Query query = manager.createQuery(sql, Atendimento.class);
		
		//Seta os par�metros para a query
		query.setParameter("senha", senha);
		query.setParameter("status", Status.AGUARDANDO_ATENDIMENTO);

		// Cria uma lista que receber� o resultado limitado a uma �nica linha
		List<Atendimento> result = new ArrayList<Atendimento>();
		result = query.setMaxResults(1).getResultList();

		// Verifica se o resultado possui retorno
		try {
			result.get(0);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * Seleciona o pr�ximo sub-servico para atendimento
	 * @param Recebe uma senha e um status como par�metro
	 * @return Retorna um atendimento
	 */
	public Atendimento proximoAtendimento(Senha senha, String status) {

		// Defini a Query
		String sql = "SELECT a FROM Atendimento a WHERE a.senha = :senha and status = :status";

		// Cria a Query
		Query query = manager.createQuery(sql, Atendimento.class);

		// Seta os par�metros para a Query
		query.setParameter("senha", senha);
		query.setParameter("status", status);

		// Cria uma lista com o resultado
		List<Atendimento> result = new ArrayList<Atendimento>();

		// Recebe o resultado limitado a uma linha
		result = query.setMaxResults(1).getResultList();

		// Atribui o resultado para o objeto e retorna nulo caso n�o tenha resultado
		Atendimento atendimento = new Atendimento();
		try {
			atendimento = result.get(0);
		} catch (Exception e) {
			atendimento = null;
		}
		return atendimento;
	}

	/**
	 * Altera o atendimento colocando o status dele como em atendimento e dando um hor�rio de inicio.
	 * @param Recebe o atendimento selecionado como par�metro
	 */
	public void atender(Atendimento atendimento) {
		manager.merge(atendimento);
	}

	/**
	 * Altera o atendimento colocando o status dele como finalizado e dando um hor�rio de fim.
	 * @param Recebe o atendimento selecionado como par�metro
	 */
	public void finalizar(Atendimento atendimento) {
		manager.merge(atendimento);
	}

	/**
	 * Calcula o tempo utilizado para atendimento
	 * fim - inicio
	 * @param Recebe o id do atendimento como par�metro
	 * @return Retorna o tempo de atendimento
	 */
	public Date getTempoAtendimento(int id) {

		// Cria o comando sql
		String sql = "select subtime(time(a.fim), time(a.inicio)) from atendimento a where a.id = ?;";

		// Cria o objeto query passando o comando sql como par�metro
		Query query = manager.createNativeQuery(sql);

		// Seta o par�metro para a query
		query.setParameter(1, id);

		// Armazena o resultado da query
		Date result = new Date();
		result = (Date) query.getSingleResult();

		return result;

	}
	
	/**
	 * Calcula a diferen�a de tempo atual com um time passado como par�metro
	 * @param date
	 * @return
	 */
	public Date getTimeDiff(Date date) {
		// Cria o comando sql que far� o c�lculo
		String sql = "SELECT TIMEDIFF(TIME(NOW()), TIME(?));";

		// Cria um objeto Query que recebe o comando sql como par�metro
		Query query = manager.createNativeQuery(sql);

		// Seta os par�metros para a query
		query.setParameter(1, date);

		// Objeto que ir� armazenar o resultado
		Date result = new Date();
		result = (Date) query.getSingleResult();

		return result;

	}
	
	/**
	 * Procura todos os atendimento diferente de aguardando atendimento para listar o hist�rico no painel
	 * @param Recebe uma senha como par�metro
	 * @return Retorna uma lista de atendimentos
	 */
	public List<Atendimento> listar() {
		
		// Cria o comando sql
		String sql = "SELECT a FROM Atendimento a WHERE status != :status order by a.id desc";

		// Cria uma query passando o comando sql como par�metro
		Query query = manager.createQuery(sql, Atendimento.class);
		
		// Seta o par�metro para a query
		query.setParameter("status", Status.AGUARDANDO_ATENDIMENTO);

		return (ArrayList<Atendimento>) query.setMaxResults(4).getResultList();
	}

	/**
	 * Procura todos os atendimento de uma senha
	 * @param Recebe uma senha como par�metro
	 * @return Retorna uma lista de atendimentos
	 */
	public List<Atendimento> listar(Senha senha) {
		
		// Cria o comando sql
		String sql = "SELECT a FROM Atendimento a WHERE a.senha = :senha";

		// Cria uma query passando o comando sql como par�metro
		Query query = manager.createQuery(sql, Atendimento.class);
		
		// Seta o par�metro para a query
		query.setParameter("senha", senha);

		return (ArrayList<Atendimento>) query.getResultList();
	}

}
