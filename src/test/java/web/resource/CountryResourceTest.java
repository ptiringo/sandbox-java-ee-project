package web.resource;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.net.URI;
import java.net.URL;
import java.util.List;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import repository.CountryRepository;
import web.ApplicationConfig;
import entity.Country;

@RunWith(Arquillian.class)
@UsingDataSet("dataset.yml")
public class CountryResourceTest {

  @ArquillianResource
  private URL baseUrl;

  private URI countryUri;

  @Before
  public void init() throws Exception {
    countryUri =
        UriBuilder.fromUri(baseUrl.toURI())
            .path(ApplicationConfig.class.getAnnotation(ApplicationPath.class).value())
            .path(CountryResource.class.getAnnotation(Path.class).value()).build();
  }

  @Deployment
  public static WebArchive createDeployment() {
    return ShrinkWrap
        .create(WebArchive.class)
        .addClasses(Country.class, CountryRepository.class, ApplicationConfig.class,
            CountryResource.class)
        .addAsLibraries(
            Maven.resolver().resolve("com.wordnik:swagger-jaxrs_2.10:1.3.12").withTransitivity()
                .asFile()).addAsResource("test-persistence.xml", "META-INF/persistence.xml")
        .addAsWebInfResource("test-h2-ds.xml");
  }

  @Test
  @InSequence(1)
  public void setupDatabase() {
    // @RunAsClient でデータベースを初期化するための仮メソッド
  }

  @Test
  @InSequence(2)
  @RunAsClient
  public void getAll_simpleCall_rightCount() throws Exception {
    List<Country> list =
        ClientBuilder.newClient().target(countryUri).request(MediaType.APPLICATION_JSON)
            .get(new GenericType<List<Country>>() {});

    assertThat(list.size(), is(3));
  }

}
