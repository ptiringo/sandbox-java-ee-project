package web.resource;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import repository.CountryRepository;
import entity.Country;

@Path(CountryResource.PATH)
@RequestScoped
@Api(value = CountryResource.PATH, description = "国に関する操作")
public class CountryResource {

  static final String PATH = "/country";

  @Inject
  private CountryRepository repository;

  @GET
  @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
  @Transactional
  @ApiOperation(value = "すべての国情報を取得", response = Country.class, responseContainer = "List")
  public List<Country> getAll() {
    return repository.getAll();
  }

}
