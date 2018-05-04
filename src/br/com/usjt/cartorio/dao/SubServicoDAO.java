package br.com.usjt.cartorio.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.usjt.cartorio.entity.Servico;
import br.com.usjt.cartorio.entity.SubServico;

/**
 * Métodos de persistência de objetos ao Banco de Dados
 * 
 * @author Lucas Copque - 816112862
 * @category DAO
 *
 */
@Repository
public class SubServicoDAO {

	@PersistenceContext
	EntityManager manager;

	/**
	 * Procura um objeto do tipo SubServico
	 *  
	 * @param Recebe um int id como parâmetro
	 * @return Retorna o objeto encontrado
	 */
	public SubServico carregar(int id) {
		return manager.find(SubServico.class, id);
	}

	/**
	 * Carrega uma lista de SubServico
	 * 
	 * @param Recebe um objeto do tipo Servico
	 * @return Retorna uma lista de objeto do tipo Servico
	 */
	public List<SubServico> listar(Servico servico) {

		String sql = "SELECT sb FROM SubServico sb WHERE sb.servico = :servico";

		Query query = manager.createQuery(sql, SubServico.class);
		query.setParameter("servico", servico);

		return (ArrayList<SubServico>) query.getResultList();
	}

}
