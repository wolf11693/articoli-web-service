package com.xantrix.webapp.unittest.controllertest;

import com.xantrix.webapp.Application;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InsertArtTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setup() {
		DefaultMockMvcBuilder mockMvcBuilder = MockMvcBuilders.webAppContextSetup(wac);
		this.mockMvc = mockMvcBuilder.build();
	}
	
	

	private static final String JSON_OBJECT_FOR_TEST = new StringBuilder()
	// @formatter:off
												.append("{\n")
												.append("	\"codArt\": \"123Test\",").append("\n")
												.append("	\"descrizione\": \"ARTICOLO TEST\",").append("\n")
												.append("	\"um\": \"PZ\",").append("\n")
												.append("	\"codStat\": \" TEST\",").append("\n")
												.append("	\"pzCart\": 1,").append("\n")
												.append("	\"pesoNetto\": 0,").append("\n")
												.append("	\"idStatoArt\": \"1\",").append("\n")
												.append("	\"dataCreaz\": \"2018-09-26\",").append("\n")
												.append("	\"famAssort\": {").append("\n")
													.append("	\"id\": 1").append("\n")
												.append("	}").append("\n")
												.append("}")
												.toString();
												// @formatter:on

	@Test
	public void A_testInserimentoArticolo_OK() throws Exception {
		MockHttpServletRequestBuilder mockHttpRequest = MockMvcRequestBuilders
																.post("/api/articoli/inserisci")
																.contentType(MediaType.APPLICATION_JSON)
																.content(JSON_OBJECT_FOR_TEST)
																.accept(MediaType.APPLICATION_JSON);

		mockMvc
				.perform(mockHttpRequest)
				.andExpect(status().isCreated())
				.andDo(print());
	}

	@Test
	public void B_testInserimentoArticolo_KO_conflict() throws Exception {
		MockHttpServletRequestBuilder mockHttpRequest = MockMvcRequestBuilders
																.post("/api/articoli/inserisci")
																.contentType(MediaType.APPLICATION_JSON)
																.content(JSON_OBJECT_FOR_TEST)
																.accept(MediaType.APPLICATION_JSON);
		
		mockMvc
				.perform(mockHttpRequest)
				.andExpect(status().isNotAcceptable())
				.andExpect(jsonPath("$.code").value(HttpStatus.NOT_ACCEPTABLE.value()))
				.andExpect(jsonPath("$.messages[0]").value("Articolo 123Test presente in anagrafica! Impossibile utilizzare il metodo POST"))
				.andDo(print());
	}

	private static final String ERROR_ENTRY_JSON = "	\"descrizione\": \"\",";
	private static final String ERROR_JSON_OBJECT = new StringBuilder()
	// @formatter:off
														.append("{\n")
														.append("	\"codArt\": \"789Test\",").append("\n")
														.append(ERROR_ENTRY_JSON).append("\n")
														.append("	\"um\": \"PZ\",").append("\n")
														.append("	\"codStat\": \" TEST\",").append("\n")
														.append("	\"pzCart\": 1,").append("\n")
														.append("	\"pesoNetto\": 0,").append("\n")
														.append("	\"idStatoArt\": \"1\",").append("\n")
														.append("	\"dataCreaz\": \"2018-09-26\",").append("\n")
														.append("	\"famAssort\": {").append("\n")
															.append("	\"id\": 1").append("\n")
														.append("	}").append("\n")
														.append("}")
														.toString();
														// @formatter:on

	@Test
	public void C_testInserimentoArticolo_KO_badRequest() throws Exception {
		MockHttpServletRequestBuilder mockHttpRequest = MockMvcRequestBuilders
																.post("/api/articoli/inserisci")
																.contentType(MediaType.APPLICATION_JSON)
																.content(ERROR_JSON_OBJECT).accept(MediaType.APPLICATION_JSON);
		
		mockMvc
				.perform( mockHttpRequest )
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
				// .andExpect(jsonPath("$.messaggio").value("Articolo 123Test presente in
				// anagrafica! Impossibile utilizzare il metodo POST"))
				.andDo(print());
	}

	@Test
	public void D_testModificaArticolo_OK() throws Exception {
		MockHttpServletRequestBuilder mockHttpRequest = MockMvcRequestBuilders
																.put("/api/articoli/modifica")
																.contentType(MediaType.APPLICATION_JSON)
																.content(JSON_OBJECT_FOR_TEST)
																.accept(MediaType.APPLICATION_JSON);
		
		mockMvc
				.perform( mockHttpRequest )
				.andExpect(status().isCreated())
				.andDo(print());
	}

	@Test
	public void E_testEliminaArticolo_OK() throws Exception {
		String idArticoliToDel = "123Test";
		
		MockHttpServletRequestBuilder mockHttpRequest = MockMvcRequestBuilders
																.delete("/api/articoli/elimina/" + idArticoliToDel)
																.accept(MediaType.APPLICATION_JSON);
		
		mockMvc
				.perform( mockHttpRequest )
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.code").value(HttpStatus.OK.toString()))
				.andExpect(jsonPath("$.message").value("Eliminazione Articolo 123Test Eseguita Con Successo"))
				.andDo(print());
	}
}