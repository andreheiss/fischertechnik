package datenschicht.kundenauftragsverwaltung;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.google.inject.Inject;
import datenschicht.EntityManagerUtil;
import domain.kundenauftragsverwaltung.core.Kundenauftrag;
import domain.kundenauftragsverwaltung.required.IKundenauftragHibernate;

public class KundenauftragHibernate implements IKundenauftragHibernate {
	
	private EntityManager em;

	@Inject
	public KundenauftragHibernate(EntityManagerUtil etmU) {
		em = etmU.getEntityManager();
	}

	@Override
	public boolean createKundenauftrag(Kundenauftrag ka) {
		
		em.getTransaction().begin();

		try {
			em.persist(ka);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}

		em.getTransaction().commit();

		return true;
	}

	@Override
	public List<Kundenauftrag> getKundenauftrag(Kundenauftrag ka) {
		
		em.getTransaction().begin();

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Kundenauftrag> criteria = builder.createQuery(Kundenauftrag.class);
		Root<? extends Kundenauftrag> root;

		root = criteria.from(Kundenauftrag.class);
		criteria.select(root);

		if (ka.getKundenauftragsnummer() != 0) {
			criteria.where(builder.equal(root.get("kundenauftragsnummer"), ka.getKundenauftragsnummer()));
		} else {
			if (ka.getLieferzeitpunkt() != null) {
				criteria.where(builder.equal(root.get("lieferdatum"), ka.getLieferzeitpunkt()));
			}
			if (ka.getKundennummer() != 0) {
				criteria.where(builder.equal(root.get("kundennummer"), ka.getKundennummer()));
			}
			/*if (ka.getPositionen() != null) {
				criteria.where(builder.equal(root.get("positionen"), ka.getPositionen()));
			}*/
			if (ka.getStatus() != null) {
				criteria.where(builder.equal(root.get("status"), ka.getStatus()));
			}
		}

		em.getTransaction().commit();

		return em.createQuery(criteria).getResultList();
	}

	@Override
	public boolean changeKundenauftrag(Kundenauftrag ka) {
		
		em.getTransaction().begin();

		try {
			em.merge(ka);
			em.flush();
		} catch (Exception e) {
			return false;
		}

		em.getTransaction().commit();

		return true;
	}

	@Override
	public boolean deleteKundenauftrag(Kundenauftrag ka) {
		
		em.getTransaction().begin();

		try {
			em.remove(ka);
			em.flush();
			em.clear();
		} catch (Exception e) {
			return false;
		}

		em.getTransaction().commit();

		return true;
	}
	
}