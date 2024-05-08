package org.hibernate.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Point {

  @Id
  private Long id;

  @Column
  private String code;

  @OneToOne(mappedBy = "point")
  private StopPoint stopPoint;

  @OneToOne(mappedBy = "point")
  private TimingPoint timingPoint;

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }
}
