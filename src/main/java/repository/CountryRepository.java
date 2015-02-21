package repository;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import entity.Country;

@RequestScoped
public class CountryRepository {

  @PersistenceContext
  private EntityManager em;

  @Transactional
  public List<Country> getAllCountries() {
    return em.createNamedQuery(Country.JPQL_SELECE_ALL, Country.class).getResultList();
  }

}
