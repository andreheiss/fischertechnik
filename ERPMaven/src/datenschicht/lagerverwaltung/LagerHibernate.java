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
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return false;
		}

	}

	@Override
	public boolean changeLagerplatz(Lagerplatz lp) {
		em.getTransaction().begin();

		try {
			em.merge(lp);
			em.flush();
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			em.getTransaction().rollback();
			return false;
		}

	}

	@Override
	public boolean deleteLagerplatz(Lagerplatz lp) {
		em.getTransaction().begin();

		try {
			em.remove(lp);
			em.flush();
			em.clear();
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			em.getTransaction().rollback();
			return false;
		}

	}

	@Override
	public List<Lagerplatz> getLagerplatz(Lagerplatz lp) {

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
//			if (lp.getTeil() != null) {
//
//				criteria.where(builder.equal(root.get("teilenummer"), lp.getTeil()));
//			}
			if (lp.getTeilenummer() != null) {

				criteria.where(builder.equal(root.get("teilenummer"), lp.getTeilenummer()));
			}
		}

		return em.createQuery(criteria).getResultList();

	}

	@Override
	public int getLagerbestand(int teilenummer) {

		CriteriaBuilder builder = em.getCriteriaBuilder();

		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Lagerplatz> root = criteria.from(Lagerplatz.class);
		criteria.select(builder.count(criteria.from(Lagerplatz.class)));
		criteria.where(builder.equal(root.get("teilenummer"), teilenummer));

		return em.createQuery(criteria).getSingleResult().intValue();

	}

}
