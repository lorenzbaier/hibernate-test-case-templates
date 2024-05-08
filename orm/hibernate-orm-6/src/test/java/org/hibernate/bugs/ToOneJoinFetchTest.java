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

		/*
		 * Fail occurs at BaseSqmToSqlAstConverter.java -> createFetch -> fromClauseIndex.findFetchedJoinByPath( resolvedNavigablePath )
		 *
		 * because FromClauseIndex -> fetchesByPath does not consider the aliases
		 */

		//language=HQL
		var hql = """
		SELECT new org.hibernate.entities.StartAndEndModel(start, end)
		FROM Link l
		JOIN PointWrapper startW ON startW.link = l
		JOIN PointWrapper endW ON endW.link = l
		JOIN startW.point start
		JOIN endW.point end
		JOIN FETCH start.stopPoint startStop
		JOIN FETCH start.timingPoint startTiming
		JOIN FETCH end.stopPoint endStop
		JOIN FETCH end.timingPoint endTiming
		ORDER BY start.code DESC
		""";
		var res = entityManager.createQuery(hql, Point.class).getResultList();
		Assert.assertEquals(0, res.size());
		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
