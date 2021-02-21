package com.xantrix.webapp.unittest.repositorytest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xantrix.webapp.Application;
import com.xantrix.webapp.entities.Articoli;
import com.xantrix.webapp.repository.ArticoliRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
@SpringBootTest
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ArticoliRepositoryTest {
	@Autowired
	private ArticoliRepository articoliRepository;

	@Test
	public void testFindByDescrizioneLike_OK() {
		List<Articoli> items = articoliRepository.findByDescrizioneLike("ACQUA ULIVETO%");
		assertEquals(2, items.size());
	}

	@Test
	public void testFindByDescrizioneLikePage_OK() {
		List<Articoli> items = articoliRepository.findByDescrizioneLike("ACQUA%", PageRequest.of(0, 10));
		assertEquals(10, items.size());
	}

	@Test
	public void testFindByCodArt_OK() throws Exception {
		String theDescription = "ACQUA ULIVETO 15 LT";
		String theCodArt = "002000301";
			
		assertThat( articoliRepository.findByCodArt(theCodArt).getDescrizione() )
											.containsIgnoringCase(theDescription.subSequence(0, theCodArt.length()));
	}

}