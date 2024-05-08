package org.hibernate.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class PointWrapper {

  @Id
  private Long id;

  @ManyToOne
  private Point point;

  @ManyToOne
  private Link link;

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }
}
