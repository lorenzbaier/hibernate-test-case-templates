package org.hibernate.bugs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.entities.Point;
import org.hibernate.entities.PointWrapper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ToOneJoinFetchTest {

	private EntityManagerFactory entityManagerFactory;

	@Before
	public void init() {
		entityManagerFactory = Persistence.createEntityManagerFactory( "templatePU" );
	}

	@After
	public void destroy() {
		entityManagerFactory.close();
	}

	// Entities are auto-discovered, so just add them anywhere on class-path
	// Add your tests, using standard JUnit.
	@Test
	public void testToOneAttributeJoinFetch() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		//language=HQL
		var hql = """
		SELECT p
		FROM PointWrapper pw
		JOIN pw.point p
		JOIN FETCH p.stopPoint s
		JOIN FETCH p.timingPoint t
		ORDER BY p.code DESC
		""";
		var res = entityManager.createQuery(hql, Point.class).getResultList();
		Assert.assertEquals(0, res.size());
		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
