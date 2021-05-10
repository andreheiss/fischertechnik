package datenschicht.lagerverwaltung;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.google.inject.Inject;

import datenschicht.EntityManagerUtil;
import domain.lagerverwaltung.core.Lagerplatz;
import domain.lagerverwaltung.required.ILagerHibernate;

public class LagerHibernate implements ILagerHibernate {

	private EntityManager em;

	@Inject
	public LagerHibernate(EntityManagerUtil etmU) {
		em = etmU.getEntityManager();
	}

	

	


	@Override
	public boolean createLagerplatz(Lagerplatz lp) {
		em.getTransaction().begin();

		try {
			em.persist(lp);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}

		em.getTransaction().commit();

		return true;
	}

	@Override
	public boolean changeLagerplatz(Lagerplatz lp) {
		em.getTransaction().begin();

		try {
			em.merge(lp);
			em.flush();
		} catch (Exception e) {
			return false;
		}

		em.getTransaction().commit();

		return true;
	}

	@Override
	public boolean deleteLagerplatz(Lagerplatz lp) {
		em.getTransaction().begin();

		try {
			em.remove(lp);
			em.flush();
			em.clear();
		} catch (Exception e) {
			return false;
		}

		em.getTransaction().commit();

		return true;
	}

	@Override
	public List<Lagerplatz> getLagerplatz(Lagerplatz lp) {
		em.getTransaction().begin();

		CriteriaBuilder builder = em.getCriteriaBuilder();

		CriteriaQuery<Lagerplatz> criteria = builder.createQuery(Lagerplatz.class);
		Root<Lagerplatz> root = criteria.from(Lagerplatz.class);
		criteria.select(root);

		if (lp.getLagerplatznummer() != 0) {

			criteria.where(builder.equal(root.get("lagerplatznummer"), lp.getLagerplatznummer()));
		} else {

			if (lp.getPosX() != 0) {

				criteria.where(builder.equal(root.get("posX"), lp.getPosX()));
			}
			if (lp.getPosY() != 0) {

				criteria.where(builder.equal(root.get("posY"), lp.getPosX()));
			}
			if (lp.getTeil() != null) {

				criteria.where(builder.equal(root.get("teilenummer"), lp.getTeil()));
			}
		}

		em.getTransaction().commit();

		return em.createQuery(criteria).getResultList();
	}

}
