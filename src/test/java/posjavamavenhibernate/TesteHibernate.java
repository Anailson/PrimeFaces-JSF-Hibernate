package posjavamavenhibernate;

import java.util.List;

import org.junit.Test;

import dao.DaoGeneric;
import model.TelefoneUser;
import model.UsuarioPessoa;

public class TesteHibernate {

	// TESTE DE SALVAR
	@Test
	public void testeHibernateUtil() {

		HibernateUtil.getEntityManager();

		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		UsuarioPessoa pessoa = new UsuarioPessoa();

		pessoa.setNome("Analista JAVA");
		pessoa.setSobrenome("teste java");
		pessoa.setIdade(36);
		pessoa.setSenha("java");
		pessoa.setLogin("java");
		pessoa.setEmail("analistajava@java.com");

		daoGeneric.salvar(pessoa);

	}

	// TESTE DE CONSULTA - CONSULTA 01*/
	@Test
	public void testeBuscar() {

		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		UsuarioPessoa pessoa = new UsuarioPessoa();
		pessoa.setId(1L);

		pessoa = daoGeneric.pesquisar(pessoa);

		System.out.println(pessoa);

	}

	// TESTE DE CONSULTA - CONSULTA 02*/
	@Test
	public void testeBuscar2() {

		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		UsuarioPessoa pessoa = daoGeneric.pesquisar(2L, UsuarioPessoa.class);

		System.out.println(pessoa);

	}

	// TESTE DE CONSULTA - update*/
	@Test
	public void testeUpdate() {

		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		UsuarioPessoa pessoa = daoGeneric.pesquisar(2L, UsuarioPessoa.class);

		pessoa.setIdade(99);
		pessoa.setNome("Nome atualizado");
		pessoa.setSobrenome("sobrenome Atualizado");

		pessoa = daoGeneric.updateMerge(pessoa); // updateMerge consulta no banco e salva e atualiza o id2

		System.out.println(pessoa);

	}

	// TESTE DE DELETE*/
	@Test
	public void testeDelete() {

		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		UsuarioPessoa pessoa = daoGeneric.pesquisar(3L, UsuarioPessoa.class);

		daoGeneric.deletarPorId(pessoa);

	}

	// TESTE DE LISTA-CONSULTAR*/
	@Test
	public void testeConsultar() {

		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		List<UsuarioPessoa> list = daoGeneric.listar(UsuarioPessoa.class);

		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
			System.out.println("-----------------------------------------");
		}

	}

	// TESTE DE DO HQL*/
	@Test
	public void testeQueryList() {

		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		List<UsuarioPessoa> list = daoGeneric.getEntityManager().createQuery(" from UsuarioPessoa").getResultList();
		// List<UsuarioPessoa> list = daoGeneric.getEntityManager().createQuery(" from
		// UsuarioPessoa where nome = 'Anaison'").getResultList(); //COM A CODIÇÃO
		// RETORNA OS DADOS DO NOME

		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
		}

	}

	// TESTE trazendo o maximo de resultados definidos*/
	@Test
	public void testeQueryListMaxResult() {

		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		List<UsuarioPessoa> list = daoGeneric.getEntityManager().createQuery(" from UsuarioPessoa order by id")
				.setMaxResults(2) // RETORNANDO NO MAXIMO DOS DADOS
				.getResultList();

		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
		}

	}

	// TESTE Parametro*/
	@Test
	public void testeQueryListParametro() {

		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		List<UsuarioPessoa> list = daoGeneric.getEntityManager().createQuery(" from UsuarioPessoa where nome = :nome")
				.setParameter("nome", "Anailson").getResultList();

		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
		}

	}

	// TESTE */
	@Test
	public void testeQuerySomaIdade() {

		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		Long somaIdade = (Long) daoGeneric.getEntityManager().createQuery("select sum(u.idade) from UsuarioPessoa u")
				.getSingleResult(); // SOMA DE TODAS A IDADES NA TABELE PESSOA

		System.out.println("Soma de todas as idades é --> " + somaIdade);
	}

	// TESTE NameQuery*/
	@Test
	public void testeNameQuery() {

		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		List<UsuarioPessoa> list = daoGeneric.getEntityManager().createNamedQuery("UsuarioPessoa.todos")
				.getResultList();

		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
		}
	}

	// TESTE BuscaPorNome*/
	@Test
	public void testeNameQuery2() {

		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		List<UsuarioPessoa> list = daoGeneric.getEntityManager().createNamedQuery("UsuarioPessoa.buscaPorNome")
				.setParameter("nome", "DBA").getResultList();

		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
		}
	}

	// TESTE TELEFONE*/
	@Test
	public void testeGravarTelefone() {
		
		DaoGeneric daoGeneric = new DaoGeneric();
		
		UsuarioPessoa pessoa = (UsuarioPessoa) daoGeneric.pesquisar(5L, UsuarioPessoa.class);
		
		TelefoneUser telefoneUser = new TelefoneUser();
		telefoneUser.setTipo("Casa");
		telefoneUser.setNumero("353454354");
		telefoneUser.setUsuarioPessoa(pessoa); //O DONO DO TELEFONE
		
		daoGeneric.salvar(telefoneUser);
		

	}
	
	// TESTE TELEFONE*/
	@Test
	public void testeConsultaTelefone() {
		
		DaoGeneric daoGeneric = new DaoGeneric();
		
		UsuarioPessoa pessoa = (UsuarioPessoa) daoGeneric.pesquisar(5L, UsuarioPessoa.class);
		
		for (TelefoneUser fone : pessoa.getTelefoneUsers()) {
			System.out.println(fone.getNumero());
			System.out.println(fone.getTipo());
			System.out.println(fone.getUsuarioPessoa());
			System.out.println("-----------------------------");
			
		}
		

	}

}
