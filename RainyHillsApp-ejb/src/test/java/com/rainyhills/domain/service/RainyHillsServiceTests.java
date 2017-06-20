package com.rainyhills.domain.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;

import static junit.framework.Assert.assertTrue;

/**
 * @author Vadzim Mikhalenak.
 */
public class RainyHillsServiceTests {

	private RainyHillsService rainyHillsService;
	private Context ctx = EJBContainer.createEJBContainer().getContext();

	@Before
	public void setUp() throws Exception {
		Object object = ctx.lookup("java:global/RainyHillsApp-ejb/RainyHillsService");
		assertTrue(object instanceof RainyHillsService);
		rainyHillsService = (RainyHillsService) object;
	}

	@Test
	public void testNullInput() {
		Integer waterCount = rainyHillsService.calculateRemainingWater(null);

		Assert.assertNull(waterCount);
	}

	@Test
	public void testEmptyInput() {
		Integer waterCount = rainyHillsService.calculateRemainingWater();

		Assert.assertNotNull(waterCount);
		Assert.assertEquals(0, (int) waterCount);
	}

	@Test
	public void testNegativeInput() {
		Integer waterCount = rainyHillsService.calculateRemainingWater(1, 2, -1, -2);

		Assert.assertNull(waterCount);
	}

	@Test
	public void testData1() {
		Integer waterCount = rainyHillsService.calculateRemainingWater(3, 2, 4, 1, 2);

		Assert.assertNotNull(waterCount);
		Assert.assertEquals(2, (int) waterCount);
	}

	@Test
	public void testData2() {
		Integer waterCount = rainyHillsService.calculateRemainingWater(4, 1, 1, 0, 2, 3);

		Assert.assertNotNull(waterCount);
		Assert.assertEquals(8, (int) waterCount);
	}
}
