package entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@NamedQuery(name = Country.JPQL_SELECE_ALL, query = "SELECT c FROM Country c")
public class Country implements Serializable {

  public static final String JPQL_SELECE_ALL = "Country.selectALL";

  @Id
  @GeneratedValue
  private long id;
  private String name;
  private long population;

}
