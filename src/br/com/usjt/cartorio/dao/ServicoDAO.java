package br.com.usjt.cartorio.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.usjt.cartorio.entity.Servico;

/**
 * M�todos de persist�ncia de objetos ao Banco de Dados
 * 
 * @author Lucas Copque - 816112862
 * @category DAO
 *
 */
@Repository
public class ServicoDAO {

	@PersistenceContext
	EntityManager manager;

	/**
	 * Procura um objeto do tipo Servico
	 * 
	 * @param Recebe um int id como par�metro
	 * @return Retorna o objeto encontrado
	 */
	public Servico carregar(int id) {
		return manager.find(Servico.class, id);
	}

	/**
	 * Este m�todo lista todos os atendimento que possuem como Foreign Key o Servico passado como par�metro e calcula a m�dia
	 * @param Recebe um objeto do tipo Servico como Par�metro
	 * @return Retorna o tempo m�dio calculado
	 */
	public Date calculaNovoTempoMedio(Servico servico) {
		// Cria a query
		String sql = "select time(avg(s.tempo_atendimento)) from senha s inner join atendimento a on s.id = a.senha_id inner join subservico sb on a.subservico_id = sb.id inner join servico sv on sb.servico_id = sv.id where sv.id = ?;";
		
		// Cria um objeto Query passando a sql como par�metro
		Query query = manager.createNativeQuery(sql);

		// Passa par�metro para a query
		query.setParameter(1, servico.getId());

		// Objeto que receber� o resultado da query
		Date result = new Date();
		result = (Date) query.getSingleResult();

		return result;

	}

	/**
	 * Este m�todo � respons�vel por atualizar o novo tempo m�dio de atendimento do servi�o
	 * @param Recebe um objeto do tipo Servico como par�metro
	 */
	public void atualizaNovoTempoMedio(Servico servico) {
		manager.merge(servico);
	}

	/**
	 * Este m�todo � respons�vel por listar todos os servi�os existentes no Banco de Dados
	 * @return
	 */
	public List<Servico> listar() {
		return (ArrayList<Servico>) manager.createQuery("SELECT s FROM Servico s", Servico.class).getResultList();
	}

}
