package org.hibernate.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class JoinedId {
  @Column(name = "left_p", insertable = false, updatable = false)
  private Long leftP;
  @Column(name = "right_p", insertable = false, updatable = false)
  private Long rightP;
}
