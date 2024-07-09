package org.hibernate.bugs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import org.hibernate.entities.Child;
import org.hibernate.entities.Parent;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
public class JPAUnitTestCase {

	private EntityManagerFactory entityManagerFactory;

	@Before
	public void init() {
		entityManagerFactory = Persistence.createEntityManagerFactory( "templatePU" );
	}

	@After
	public void destroy() {
		entityManagerFactory.close();
	}

	@Test
	public void testCountQueryJoinIdentiferDescriptorNull() {
		// if a mapped superclass has a mapped superclass without id then the identifier descriptor is null...

		//language=HQL
		var tHql = """
    SELECT DISTINCT p.id, j.child
    FROM Parent p
    LEFT JOIN Joined j ON j.id.leftP = j.id.rightP
    """;

		var em = entityManagerFactory.createEntityManager();
		var cb = (HibernateCriteriaBuilder) em.getCriteriaBuilder();
		var cq = cb.createQuery(tHql, Object[].class).createCountQuery();
		em.createQuery(cq).getSingleResult();
	}
}
