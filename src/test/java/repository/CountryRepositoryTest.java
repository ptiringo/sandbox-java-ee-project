package repository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import entity.Country;

@RunWith(Arquillian.class)
public class CountryRepositoryTest {

  @Deployment
  public static WebArchive createDeployment() {
    return ShrinkWrap.create(WebArchive.class)
        .addClasses(Country.class, CountryRepository.class)
        .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
        .addAsResource("insert-initial-data.sql")
        .addAsResource("countries.csv")
        .addAsWebInfResource("test-h2-ds.xml");
  }

  @Inject
  private CountryRepository repository;

  @Test
  public void getAll() {
    assertThat(repository.getAll().size(), is(3));
  }

}
