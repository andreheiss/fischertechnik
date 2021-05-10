package datenschicht.betriebsverwaltung;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.google.inject.Inject;

import datenschicht.EntityManagerUtil;
import domain.betriebsverwaltung.core.Werk;
import domain.betriebsverwaltung.required.IWerkHibernate;

public class WerkHibernate implements IWerkHibernate{


		private EntityManager em;

		@Inject
		public WerkHibernate(EntityManagerUtil etmU) {
			em = etmU.getEntityManager();
		}

		@Override
		public boolean createWerk(Werk w) {

			em.getTransaction().begin();

			try {
				em.persist(w);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return false;
			}

			em.getTransaction().commit();

			return true;
		}

		@Override
		public List<Werk> getWerk(Werk w) {

			em.getTransaction().begin();

			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Werk> criteria = builder.createQuery(Werk.class);
			Root<Werk> root = criteria.from(Werk.class);

			
			if (w.getWerksnummer() != 0) {

				criteria.where(builder.equal(root.get("werksnummer"), w.getWerksnummer()));
			} else {

//				if (t.getGueltigVon() != null) {
//
//					criteria.where(builder.equal(root.get("gueltigVon"), t.getGueltigVon()));
//				}
//				if (t.getGueltigBis() != null) {
//
//					criteria.where(builder.equal(root.get("gueltigBis"), t.getGueltigBis()));
//				}
				if (w.getBezeichnung() != null) {

					criteria.where(builder.equal(root.get("bezeichnung"), w.getBezeichnung()));
				}
				
			}

			em.getTransaction().commit();

			return em.createQuery(criteria).getResultList();
		}

		@Override
		public boolean changeWerk(Werk w) {

			em.getTransaction().begin();

			try {
				em.merge(w);
				em.flush();
			} catch (Exception e) {
				return false;
			}

			em.getTransaction().commit();

			return true;
		}

		@Override
		public boolean deleteWerk(Werk w) {

			em.getTransaction().begin();

			try {
				em.remove(w);
				em.flush();
				em.clear();
			} catch (Exception e) {
				return false;
			}

			em.getTransaction().commit();

			return true;
		}
	}
	

