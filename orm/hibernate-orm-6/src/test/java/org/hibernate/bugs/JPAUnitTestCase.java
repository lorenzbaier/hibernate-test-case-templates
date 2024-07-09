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

	// Entities are auto-discovered, so just add them anywhere on class-path
	// Add your tests, using standard JUnit.
	@Test
	public void hhh123Test() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		var child = new Child();
		child.setId(1L);

		var parent = new Parent();
		parent.setId(1L);
		parent.setChildren(List.of(child));
		child.setParent(parent);

		entityManager.persist(parent);
		entityManager.persist(child);

		entityManager.getTransaction().commit();
		entityManager.close();

		var em2 = entityManagerFactory.createEntityManager();

		em2.getTransaction().begin();
		var cb = em2.getCriteriaBuilder();
		var queryParents = cb.createQuery(Parent.class);
		queryParents.from(Parent.class);
		var parents = em2.createQuery(queryParents).getResultList();

		var queryChildren = cb.createQuery(Child.class);
		var cR = queryChildren.from(Child.class);
		queryChildren.where(cb.equal(cR.get("parent").get("id"), 1L));
		var children = em2.createQuery(queryChildren).getResultList();

		var lazyLoadedChild = parents.getFirst().getChildren().getFirst();
		var preLoadedChild = children.getFirst();

		em2.getTransaction().commit();

		assertEquals(preLoadedChild, lazyLoadedChild);

		em2.close();
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
