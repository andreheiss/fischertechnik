package datenschicht.geschaeftspartnerverwaltung;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.google.inject.Inject;
import datenschicht.EntityManagerUtil;
import domain.geschaeftspartnerverwaltung.core.Geschaeftspartner;
import domain.geschaeftspartnerverwaltung.core.Kunde;
import domain.geschaeftspartnerverwaltung.core.Lieferant;
import domain.geschaeftspartnerverwaltung.required.IGeschaeftspartnerHibernate;

public class GeschaeftspartnerHibernate implements IGeschaeftspartnerHibernate {
	
	private EntityManager em;

	@Inject
	public GeschaeftspartnerHibernate(EntityManagerUtil etmU) {
		em = etmU.getEntityManager();
	}

	@Override
	public boolean createGeschaeftspartner(Geschaeftspartner gs) {
		
		em.getTransaction().begin();

		try {
			em.persist(gs);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}

		em.getTransaction().commit();

		return true;
	}

	@Override
	public List<Geschaeftspartner> getGeschaeftspartner(Geschaeftspartner gs) {
		
		em.getTransaction().begin();

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Geschaeftspartner> criteria = builder.createQuery(Geschaeftspartner.class);
		Root<? extends Geschaeftspartner> root;

		if (gs instanceof Kunde) {
			root = criteria.from(Kunde.class);
		} else if (gs instanceof Lieferant) {
			root = criteria.from(Lieferant.class);
		} else {
			root = criteria.from(Geschaeftspartner.class);
		}
		criteria.select(root);

		if (gs.getPartnernummer() != 0) {
			criteria.where(builder.equal(root.get("partnernummer"), gs.getPartnernummer()));
		} else {
			if (gs.getAdresse() != null) {
				criteria.where(builder.equal(root.get("adresse"), gs.getAdresse()));
			}
		}

		em.getTransaction().commit();

		return em.createQuery(criteria).getResultList();
	}

	@Override
	public boolean changeGeschaeftspartner(Geschaeftspartner gs) {
		
		em.getTransaction().begin();

		try {
			em.merge(gs);
			em.flush();
		} catch (Exception e) {
			return false;
		}

		em.getTransaction().commit();

		return true;
	}

	@Override
	public boolean deleteGeschaeftspartner(Geschaeftspartner gs) {
		
		em.getTransaction().begin();

		try {
			em.remove(gs);
			em.flush();
			em.clear();
		} catch (Exception e) {
			return false;
		}

		em.getTransaction().commit();

		return true;
	}

}