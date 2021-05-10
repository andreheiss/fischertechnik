package datenschicht.betriebsmittelverwaltung;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.google.inject.Inject;

import datenschicht.EntityManagerUtil;
import domain.betriebsmittelverwaltung.core.Betriebsmittel;
import domain.betriebsmittelverwaltung.required.IBetriebsmittelHibernate;

public class BetriebsmittelHibernate implements IBetriebsmittelHibernate {

	private EntityManager em;

	@Inject
	public BetriebsmittelHibernate(EntityManagerUtil etmU) {
		em = etmU.getEntityManager();
	}

	@Override
	public boolean createBetriebsmittel(Betriebsmittel bm) {
		em.getTransaction().begin();

		try {
			em.persist(bm);
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			em.getTransaction().rollback();
			return false;
		}

		
	}

	@Override
	public List<Betriebsmittel> getBetriebsmittel(Betriebsmittel bm) {
		
		
		CriteriaBuilder builder = em.getCriteriaBuilder();

		CriteriaQuery<Betriebsmittel> criteria = builder.createQuery(Betriebsmittel.class);
		Root<Betriebsmittel> root = criteria.from(Betriebsmittel.class);
		criteria.select(root);

		if (bm.getBetriebsmittelnummer() != 0) {

			criteria.where(builder.equal(root.get("betriebsmittelnummer"), bm.getBetriebsmittelnummer()));
		} else {

			if (bm.getGueltigVon() != null) {

				criteria.where(builder.equal(root.get("gueltigVon"), bm.getGueltigVon()));
			}
			if (bm.getGueltigBis() != null) {

				criteria.where(builder.equal(root.get("gueltigBis"), bm.getGueltigBis()));
			}
			if (bm.getBezeichnung() != null) {

				criteria.where(builder.like( builder.upper(root.get("bezeichnung")), "%" + bm.getBezeichnung().toUpperCase()+"%") );
			}
			if (bm.getArt() != null) {

				criteria.where(builder.equal(root.get("betriebsmittelart"), bm.getArt()));
			}
		}
			if(bm.getWerksnummer()!= null){
				
				criteria.where(builder.equal(root.get("werksnummer"), bm.getWerksnummer()));
			
		}
		

		return em.createQuery(criteria).getResultList();
		
		
	}

	@Override
	public boolean changeBetriebsmittel(Betriebsmittel bm) {
		em.getTransaction().begin();

		try {
			em.merge(bm);
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			em.getTransaction().rollback();
			return false;
		}

		
	}

	@Override
	public boolean deleteBetriebsmittel(Betriebsmittel bm) {
		em.getTransaction().begin();

		try {
			em.remove(bm);
			em.flush();
			em.clear();
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			em.getTransaction().rollback();
			return false;
		}

		
	}

}
