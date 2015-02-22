package web.resource;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.net.URL;
import java.util.List;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import repository.CountryRepository;
import web.ApplicationConfig;
import entity.Country;

@RunWith(Arquillian.class)
public class CountryResourceTest {

  @ArquillianResource
  private URL baseUrl;

  private String resourcePrefix = ApplicationConfig.class.getAnnotation(ApplicationPath.class)
      .value();

  @Deployment
  public static WebArchive createDeployment() {
    return ShrinkWrap.create(WebArchive.class)
        .addClasses(Country.class, CountryRepository.class, ApplicationConfig.class,
            CountryResource.class)
        .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
        .addAsResource("insert-initial-data.sql")
        .addAsResource("countries.csv")
        .addAsWebInfResource("test-h2-ds.xml");
  }

  @Test
  @RunAsClient
  public void test() throws Exception {
    List<Country> list = ClientBuilder.newClient()
        .target(new URL(baseUrl, resourcePrefix + "/country").toURI())
        .request(MediaType.APPLICATION_JSON)
        .get(new GenericType<List<Country>>() {});

    assertThat(list.size(), is(3));
  }

}
