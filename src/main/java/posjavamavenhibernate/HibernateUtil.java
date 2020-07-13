package posjavamavenhibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


//LER O ARQUIVO PERSISTENCIA UMA VEZ
public class HibernateUtil {

	public static EntityManagerFactory factory = null;

	static {
		init();
	}

	private static void init() {

		try {

			if (factory == null) {

				factory = Persistence.createEntityManagerFactory("pos-java-maven-hibernate");
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	//GERENCIADO DO BANCO
	
	public static EntityManager getEntityManager(){
		return factory.createEntityManager(); //PROVE A PARTE DE PERSISTENCIA
	}
	
	/*consultando pela chave primaria*/
	public static Object getPrimaryKey(Object entity) { //Retorna a primary Key
		
		return factory.getPersistenceUnitUtil().getIdentifier(entity);
	}
	
	
}
