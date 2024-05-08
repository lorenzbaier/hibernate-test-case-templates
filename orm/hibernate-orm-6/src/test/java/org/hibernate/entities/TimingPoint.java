package org.hibernate.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class TimingPoint {

  @Id
  private Long id;

  @OneToOne
  private Point point;

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }
}
