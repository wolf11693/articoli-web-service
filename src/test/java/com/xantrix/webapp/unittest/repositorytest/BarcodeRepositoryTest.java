package com.xantrix.webapp.unittest.repositorytest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xantrix.webapp.Application;
import com.xantrix.webapp.repository.BarcodeRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
@SpringBootTest
public class BarcodeRepositoryTest {
	@Autowired
	private BarcodeRepository barcodeRepository;

	@Test
	public void testFindByBarcode_OK() {
		String theBarcode = "8008490000021";
		assertEquals(this.barcodeRepository.findByBarcode(theBarcode).getBarcode(), theBarcode);
	}
}