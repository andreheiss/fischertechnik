package datenschicht;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtil {

	private EntityManagerFactory emf;
	private EntityManager em;
	
	public EntityManagerUtil() {
		emf = Persistence.createEntityManagerFactory("persistence-unit");
		em = emf.createEntityManager();
	}
	
	public EntityManager getEntityManager() {
		
		return em;
	}
	
	public void close() {
		em.close();
		emf.close();
	}
}