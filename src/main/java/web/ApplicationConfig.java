package web;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;

import org.reflections.Reflections;

import web.filter.CORSFilter;

import com.wordnik.swagger.jaxrs.config.BeanConfig;

@ApplicationPath("api")
public class ApplicationConfig extends Application {

  private static final String RESOURCE_PACKAGE = "web.resource";

  public ApplicationConfig() {
    BeanConfig beanConfig = new BeanConfig();
    beanConfig.setVersion("0.0.1");
    beanConfig.setSchemes(new String[] {"http"});
    beanConfig.setHost("localhost:8080");
    beanConfig.setBasePath("/sample-web-project/api");
    beanConfig.setResourcePackage(RESOURCE_PACKAGE);
    beanConfig.setScan(true);
  }

  @Override
  public Set<Class<?>> getClasses() {
    Set<Class<?>> resources = new HashSet<>();

    new Reflections(RESOURCE_PACKAGE).getTypesAnnotatedWith(Path.class).forEach(resources::add);
    
    resources.add(CORSFilter.class);

    resources.add(com.wordnik.swagger.jaxrs.listing.ApiListingResource.class);
    resources.add(com.wordnik.swagger.jaxrs.listing.SwaggerSerializers.class);

    return resources;
  }

}
