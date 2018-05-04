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
 * Métodos de persistência de objetos ao Banco de Dados
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
	 * @param Recebe um int id como parâmetro
	 * @return Retorna o objeto encontrado
	 */
	public Servico carregar(int id) {
		return manager.find(Servico.class, id);
	}

	/**
	 * Este método lista todos os atendimento que possuem como Foreign Key o Servico passado como parâmetro e calcula a média
	 * @param Recebe um objeto do tipo Servico como Parâmetro
	 * @return Retorna o tempo médio calculado
	 */
	public Date calculaNovoTempoMedio(Servico servico) {
		// Cria a query
		String sql = "select time(avg(s.tempo_atendimento)) from senha s inner join atendimento a on s.id = a.senha_id inner join subservico sb on a.subservico_id = sb.id inner join servico sv on sb.servico_id = sv.id where sv.id = ?;";
		
		// Cria um objeto Query passando a sql como parâmetro
		Query query = manager.createNativeQuery(sql);

		// Passa parâmetro para a query
		query.setParameter(1, servico.getId());

		// Objeto que receberá o resultado da query
		Date result = new Date();
		result = (Date) query.getSingleResult();

		return result;

	}

	/**
	 * Este método é responsável por atualizar o novo tempo médio de atendimento do serviço
	 * @param Recebe um objeto do tipo Servico como parâmetro
	 */
	public void atualizaNovoTempoMedio(Servico servico) {
		manager.merge(servico);
	}

	/**
	 * Este método é responsável por listar todos os serviços existentes no Banco de Dados
	 * @return
	 */
	public List<Servico> listar() {
		return (ArrayList<Servico>) manager.createQuery("SELECT s FROM Servico s", Servico.class).getResultList();
	}

}
