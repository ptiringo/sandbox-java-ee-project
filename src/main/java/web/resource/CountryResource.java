package web.resource;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import repository.CountryRepository;
import entity.Country;

@Path("country")
@RequestScoped
public class CountryResource {

  @Inject
  private CountryRepository repository;

  @GET
  @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
  @Transactional
  public List<Country> getAll() {
    return repository.getAll();
  }

}
