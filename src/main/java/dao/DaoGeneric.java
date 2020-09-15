package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import posjavamavenhibernate.HibernateUtil;

public class DaoGeneric<E> {

	private EntityManager entityManager = HibernateUtil.getEntityManager();

	/* METODO SALVAR */
	public void salvar(E entidade) {

		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(entidade);
		transaction.commit();
	}

	/* UPDATE */
	public E updateMerge(E entidade) { // Salva ou atualiza

		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		E entidadeSalva = entityManager.merge(entidade);
		transaction.commit();

		return entidadeSalva;
	}

	/* CONSULTA 01 */
	public E pesquisar(E entidade) {

		Object id = HibernateUtil.getPrimaryKey(entidade);

		E e = (E) entityManager.find(entidade.getClass(), id);

		return e;

	}

	/* CONSULTA 02 */
	public E pesquisar(Long id, Class<E> entidade) {
        
		//AJUSTANDO PARA CARREGAR OS DADOS DO TELEFONE USUARIO
		//E e = (E) entityManager.find(entidade, id);
		//CORRE~ÇÃO PARA NAO TRAZER OS DADOS EM MEMORIA
		entityManager.clear();//LIMPAR O QUE ESTIVER EM MEMORIA
		E e = (E) entityManager.createQuery("from " + entidade.getSimpleName() + " where id = " + id).getSingleResult();
		return e;

	}
	/* DELETE */

	public void deletarPorId(E entidade) throws Exception{

		Object id = HibernateUtil.getPrimaryKey(entidade);

		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		entityManager.createNativeQuery(
						"delete from " + entidade.getClass().getSimpleName().toLowerCase() + 
						" where id =" + id).executeUpdate(); // FAZ DELETE

		transaction.commit();// GRAVA ALTERAÇÕE NO BANCO

	}
	
	/*Consulta todos - LISTAR*/
	
	public List<E> listar(Class<E> entidade){
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		 
		List<E> lista = entityManager.createQuery("from " + entidade.getName()).getResultList();
		
		transaction.commit();
		
		return lista;
	}
	
	/*DEIXANDO COMO PUBLICA O EntityManager pra criar consulta de Querys*/
	
	public EntityManager getEntityManager() {
		
		return entityManager;
	}
	
	
}
