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
 * M�todos de persist�ncia de objetos ao Banco de Dados
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
	 * @param Recebe uma senha como par�metro
	 * @return
	 */
	public Senha criar(Senha senha) {
		manager.persist(senha);
		return getLast();
	}

	/**
	 * Carrega o �ltimo objeto do tipo Senha inserido no Banco de Dados 
	 * @return Retorna um objeto do tipo Senha
	 */
	private Senha getLast() {
		return (Senha) manager.createQuery("SELECT s FROM Senha s ORDER BY s.id DESC", Senha.class).setMaxResults(1)
				.getSingleResult();
	}

	/**
	 * Procura um objeto do tipo Senha
	 * @param Recebe um int id como par�metro
	 * @return Retorna o objeto encontrado do tipo Senha
	 */
	public Senha carregar(int id) {
		return manager.find(Senha.class, id);
	}

	/**
	 * Este m�todo � respons�vel calcular o tempo de espera para esta senha ser atendida.
	 * O m�todo calcula a m�dia de tempo m�dio de atendimento de todos os servi�os e multiplica pelo tanto de senhas
	 * que est�o aguardando atendimento � frente da senha passada como par�metro
	 * @param Recebe o id da senha selecionada como par�metro
	 * @return Retorna o tempo de espera calculado
	 */
	public Date getTempoEspera(int id) {
		// Cria o comando sql que far� o c�lculo
		String sql = "select sec_to_time((avg(tempo_medio)) * (select count(s.id) from senha s where s.id <= ? and s.status = 'Aguardando Atendimento')) from servico;";

		// Cria um objeto Query que recebe o comando sql como par�metro
		Query query = manager.createNativeQuery(sql);

		// Seta os par�metro para a query
		query.setParameter(1, id);

		// Recebe o resultado da query
		Date result = new Date();
		result = (Date) query.getSingleResult();

		return result;

	}

	/**
	 * Este m�todo � respons�vel por calcula a data e hora de previs�o de atendimento conforme o tempo de espera que foi fornecido
	 * no m�todo getTempoEspera().
	 * Ele recebe o tempo atual e o tempo de espera e retorna o tempo esperado.
	 * @param Recebe o tempo de espera como par�metro
	 * @return Retorna a data calculada
	 */
	public Date getPrevisao(Date date) {
		// Cria o comando sql que far� o c�lculo
		String sql = "SELECT ADDTIME(now(), ?);";

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
	 * Este m�todo � respons�vel por selecionar a primeira senha que possui status 'Aguardando Atendimento'
	 * @return Retorna objeto do tipo Senha
	 */
	public Senha proximaSenha() {

		// Cria o comando sql
		String sql = "SELECT s FROM Senha s WHERE s.status = :status";

		// Cria o objeto Query passando o comando sql como par�metro
		Query query = manager.createQuery(sql, Senha.class);
		
		// Seta os par�metros para a quer
		query.setParameter("status", Status.AGUARDANDO_ATENDIMENTO);

		// Cria uma lista que ir� receber o resultado
		List<Senha> result = new ArrayList<Senha>();

		// Recebe o resultado limitado a uma linha
		result = query.setMaxResults(1).getResultList();

		// Cria o objeto Senha
		Senha senha = new Senha();
		
		// Verifica se o resultado � existente
		try {
			senha = result.get(0);
		} catch (Exception e) {
			senha = null;
		}
		return senha;

	}

	/**
	 * Este m�todo � respons�vel por selecionar a senha que est� sofrendo atendimento
	 * @return Retorna um objeto do tipo Senha
	 */
	public Senha senhaAtual() {

		// Cria o comando sql
		String sql = "SELECT s FROM Senha s WHERE s.status = :status";

		// Cria o objeto Query passando o comando sql como par�metro
		Query query = manager.createQuery(sql, Senha.class);
		
		// Seta os par�metros para a query
		query.setParameter("status", Status.EM_ATENDIMENTO);

		// Cria uma lista que ir� receber o resultado
		List<Senha> result = new ArrayList<Senha>();

		// Recebe o resultado limitado a uma linha
		result = query.setMaxResults(1).getResultList();

		// Cria um objeto do tipo senha
		Senha senha = new Senha();
		
		// Verifica se o resultado � existente
		try {
			senha = result.get(0);
		} catch (Exception e) {
			senha = null;
		}
		return senha;

	}

	/**
	 * Este m�todo � repons�vel por atualizar o status da senha para atendimento e seu hor�rio de in�cio
	 * @param Recebe um objeto do tipo Senha como para�metro
	 */
	public void atender(Senha senha) {
		manager.merge(senha);

	}

	/**
	 *Este m�todo � repons�vel por atualizar o status da senha para finalizada, seu hor�rio de fim e tempo de atendimento
	 * @param Recebe um objeto do tipo Senha como para�metro
	 */
	public void finalizar(Senha senha) {
		manager.merge(senha);

	}

	/**
	 * Este m�todo � respons�vel por calcular o tempo de atendimento da senha selecionada
	 * campo fim - campo inicio
	 * @param Recebe a senha selecionada como par�metro
	 * @return Retorna o tempo de atendimento
	 */
	public Date getTimeDiff(Senha senha) {

		// Cria o comando sql
		String sql = "select timediff((select fim from senha where senha.id = ?), (select inicio from senha where senha.id = ?));";

		// Cria um objeto Query passando o comando sql como par�metro
		Query query = manager.createNativeQuery(sql);

		// Seta os par�metros para a query
		query.setParameter(1, senha.getId());
		query.setParameter(2, senha.getId());

		// Armazena o resultado da query
		Date result = new Date();
		result = (Date) query.getSingleResult();

		return result;

	}

	/**
	 * Este m�todo � responsavel por verificar se existe alguma senha em atendimento
	 * @param Recebe um status como para�metro
	 * @return Retorna verdairo caso exista e falso caso n�o exista
	 */
	public boolean isSenhaEmAtendimento(String status) {
		
		// Cria o comando sql
		String sql = "SELECT s FROM Senha s WHERE status = :status";

		// Cria um objeto Query passando o comando sql como par�metro
		Query query = manager.createQuery(sql, Senha.class);
		
		// Seta o par�metro para a query
		query.setParameter("status", status);

		// Cria uma lista que ir� receber o resultado limitado a uma linha
		List<Senha> result = new ArrayList<Senha>();
		result = query.setMaxResults(1).getResultList();

		// Verifica existe ou n�o um retorno da query
		try {
			result.get(0);
			return true;
		} catch (Exception e) {
			return false;
		}

	}
	
	/**
	 * Verifica se existe alguma senha cadastrada no Banco de Dados
	 * @return Retorna verdadeiro caso existe e falso caso n�o exista nenhuma senha cadastrada
	 */
	public boolean haveSenha() {
		
		// Cria o comando sql
		String sql = "SELECT s FROM Senha s WHERE status = :status";

		// Cria um objeto Query passando o comando sql como par�metro
		Query query = manager.createQuery(sql, Senha.class);
		
		// Seta o par�metro para a query
		query.setParameter("status", Status.AGUARDANDO_ATENDIMENTO);

		// Cria uma lista que ir� receber o resultado limitado a uma linha
		List<Senha> result = new ArrayList<Senha>();
		result = query.setMaxResults(1).getResultList();

		// Verifica existe ou n�o um retorno da query
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
