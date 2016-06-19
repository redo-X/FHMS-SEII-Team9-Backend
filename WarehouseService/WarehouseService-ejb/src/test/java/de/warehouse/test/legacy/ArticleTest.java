package de.warehouse.test.legacy;

import static org.junit.Assert.*;

import org.junit.Test;

import de.warehouse.persistence.Article;

/**
 * @author Florian
 */
public class ArticleTest {
	
	@Test(expected=IllegalArgumentException.class)
	public void testSetNegativeQuantityOnStock() {
		Article a = new Article();
		a.setQuantityOnStock(-10);
	}
	
	@Test
	public void testSetPositiveQuantityOnStock() {
		Article a = new Article();
		a.setQuantityOnStock(10);
		
		assertEquals(10, a.getQuantityOnStock(), 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSetQuantityOnCommitmentGreaterThanStockedQuantity() {
		Article a = new Article();
		a.setQuantityOnStock(10);
		a.setQuantityOnCommitment(20);
	}
	@Test
	public void testSetQuantityOnCommitmentLessThanStockedQuantity() {
		Article a = new Article();
		a.setQuantityOnStock(10);
		a.setQuantityOnCommitment(5);
		
		assertEquals(5, a.getQuantityOnCommitment(), 0);
	}
	@Test
	public void testGetFreeQuantityAfterSetStockedAndCommitedQuantity() {
		Article a = new Article();
		a.setQuantityOnStock(10);
		a.setQuantityOnCommitment(5);
		
		assertEquals(5, a.getFreeQuantity(), 0);
	}
}
