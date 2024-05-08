package org.hibernate.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Link {

  @Id
  private Long id;

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }
}
