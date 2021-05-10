package datenschicht.teileverwaltung;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.google.inject.Inject;

import datenschicht.EntityManagerUtil;
import domain.teileverwaltung.core.Eigenfertigungsteil;
import domain.teileverwaltung.core.Fremdbezugsteil;
import domain.teileverwaltung.core.Teil;
import domain.teileverwaltung.requiredservices.ITeilHibernate;

public class TeileHibernate implements ITeilHibernate {

	private EntityManager em;

	@Inject
	public TeileHibernate(EntityManagerUtil etmU) {
		em = etmU.getEntityManager();
	}

	@Override
	public boolean createTeil(Teil t) {

		em.getTransaction().begin();

		try {
			em.persist(t);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}

		em.getTransaction().commit();

		return true;
	}

	@Override
	public List<Teil> getTeil(Teil t) {

		em.getTransaction().begin();

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Teil> criteria = builder.createQuery(Teil.class);
		Root<? extends Teil> root;

		if (t instanceof Fremdbezugsteil) {
			root = criteria.from(Fremdbezugsteil.class);
		} else if (t instanceof Eigenfertigungsteil) {
			root = criteria.from(Eigenfertigungsteil.class);
		} else {
			root = criteria.from(Teil.class);
		}
		criteria.select(root);

		if (t.getTeilenummer() != 0) {

			criteria.where(builder.equal(root.get("teilenummer"), t.getTeilenummer()));
		} else {

			if (t.getGueltigVon() != null) {

				criteria.where(builder.equal(root.get("gueltigVon"), t.getGueltigVon()));
			}
			if (t.getGueltigBis() != null) {

				criteria.where(builder.equal(root.get("gueltigBis"), t.getGueltigBis()));
			}
			if (t.getBezeichnung() != null) {

				criteria.where(builder.equal(root.get("bezeichnung"), t.getBezeichnung()));
			}
			if (t.getMengeneinheit() != null) {

				criteria.where(builder.equal(root.get("mengeneinheit"), t.getMengeneinheit()));
			}
			if (t.getTeileart() != null) {

				criteria.where(builder.equal(root.get("teileart"), t.getTeileart()));
			}
			if (t.getPlanart() != null) {

				criteria.where(builder.equal(root.get("planart"), t.getPlanart()));
			}
			if (t.getFarbe() != null) {

				criteria.where(builder.equal(root.get("farbe"), t.getFarbe()));
			}
		}

		em.getTransaction().commit();

		return em.createQuery(criteria).getResultList();
	}

	@Override
	public boolean changeTeil(Teil t) {

		em.getTransaction().begin();

		try {
			em.merge(t);
			em.flush();
		} catch (Exception e) {
			return false;
		}

		em.getTransaction().commit();

		return true;
	}

	@Override
	public boolean deleteTeil(Teil t) {

		em.getTransaction().begin();

		try {
			em.remove(t);
			em.flush();
			em.clear();
		} catch (Exception e) {
			return false;
		}

		em.getTransaction().commit();

		return true;
	}
}