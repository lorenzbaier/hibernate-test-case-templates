package org.hibernate.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Joined {
  @EmbeddedId
  private JoinedId id;

  @ManyToOne
  private Child child;

  public JoinedId getId() {
    return id;
  }

  public void setId(JoinedId id) {
    this.id = id;
  }

  public Child getChild() {
    return child;
  }

  public void setChild(Child child) {
    this.child = child;
  }
}
