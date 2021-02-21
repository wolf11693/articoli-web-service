package com.xantrix.webapp.unittest.controllertest;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.xantrix.webapp.Application;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SelectArtTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setup() {
		DefaultMockMvcBuilder mockMvcBuilder = MockMvcBuilders.webAppContextSetup(wac);
		this.mockMvc = mockMvcBuilder.build();
	}

	

	private static final String JSON_OBJECT_EXPECTED = new StringBuilder()
			// @formatter:off
							.append("{").append("\n")
							.append("	\"codArt\": \"002000301\",").append("\n")
							.append("	\"descrizione\": \"ACQUA ULIVETO 15 LT\",").append("\n")
							.append("	\"um\": \"PZ\",").append("\n")
							.append("	\"codStat\": \"\",").append("\n")
							.append("	\"pzCart\": 6,").append("\n")
							.append("	\"pesoNetto\": 1.5,").append("\n")
							.append("	\"idStatoArt\": \"1\",").append("\n")
							.append("	\"dataCreaz\": \"2010-06-14\",").append("\n")
							.append("	\"barcode\": [").append("\n")
							.append("		{").append("\n")
							.append("			\"barcode\": \"8008490000021\",").append("\n")
							.append("			\"idTipoArt\": \"CP\"").append("\n")
							.append("		}").append("\n")
							.append("	],").append("\n")
							.append("	\"famAssort\": {").append("\n")
							.append("		\"id\": 1,").append("\n")
							.append("		\"descrizione\": \"DROGHERIA ALIMENTARE\"").append("\n")
							.append("	},").append("\n")
							.append("	\"ingredienti\": null,").append("\n")
							.append("	\"iva\": {").append("\n")
							.append("		\"idIva\": 22,").append("\n")
							.append("		\"descrizione\": \"IVA RIVENDITA 22%\",").append("\n")
							.append("		\"aliquota\": 22").append("\n")
							.append("	}")
							.append("}")
							.toString();
			// @formatter:on


	@Test
	public void testCercaArticoloByEan_OK() throws Exception {
		String barCodeToFind = "8008490000021";
		
		MockHttpServletRequestBuilder mockHttpRequest = MockMvcRequestBuilders
																.get("/api/articoli/cerca/ean/" + barCodeToFind)
																.accept(MediaType.APPLICATION_JSON);
		mockMvc
				.perform(mockHttpRequest)
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))

				// articoli
				.andExpect(jsonPath("$.codArt").exists())
				.andExpect(jsonPath("$.codArt").value("002000301"))
				
				.andExpect(jsonPath("$.descrizione").exists())
				.andExpect(jsonPath("$.descrizione").value("ACQUA ULIVETO 15 LT"))
				
				.andExpect(jsonPath("$.um").exists())
				.andExpect(jsonPath("$.um").value("PZ"))
				
				.andExpect(jsonPath("$.codStat").exists())
				.andExpect(jsonPath("$.codStat").value(""))
				
				.andExpect(jsonPath("$.pzCart").exists())
				.andExpect(jsonPath("$.pzCart").value("6"))
				
				.andExpect(jsonPath("$.pesoNetto").exists())
				.andExpect(jsonPath("$.pesoNetto").value("1.5"))
				
				.andExpect(jsonPath("$.idStatoArt").exists())
				.andExpect(jsonPath("$.idStatoArt").value("1"))
				
				.andExpect(jsonPath("$.dataCreaz").exists())
				.andExpect(jsonPath("$.dataCreaz").value("2010-06-14"))

				// barcode
				.andExpect(jsonPath("$.barcode[0].barcode").exists())
				.andExpect(jsonPath("$.barcode[0].barcode").value(barCodeToFind))
				
				.andExpect(jsonPath("$.barcode[0].idTipoArt").exists())
				.andExpect(jsonPath("$.barcode[0].idTipoArt").value("CP"))

				// famAssort
				.andExpect(jsonPath("$.famAssort.id").exists())
				.andExpect(jsonPath("$.famAssort.id").value("1"))
				
				.andExpect(jsonPath("$.famAssort.descrizione").exists())
				.andExpect(jsonPath("$.famAssort.descrizione").value("DROGHERIA ALIMENTARE"))

				// ingredienti
				.andExpect(jsonPath("$.ingredienti").isEmpty())

				// Iva
				.andExpect(jsonPath("$.iva.idIva").exists())
				.andExpect(jsonPath("$.iva.idIva").value("22"))
				
				.andExpect(jsonPath("$.iva.descrizione").exists())
				.andExpect(jsonPath("$.iva.descrizione").value("IVA RIVENDITA 22%"))
				
				.andExpect(jsonPath("$.iva.aliquota").exists())
				.andExpect(jsonPath("$.iva.aliquota").value("22"))
				.andDo(print());
	}

	@Test
	public void testCercaArticoloByEan_OK_notFound() throws Exception {
		String barcodeToFind = "8008490002138";
		
		MockHttpServletRequestBuilder mockHttpRequest = MockMvcRequestBuilders
																.get("/api/articoli/cerca/ean/" + barcodeToFind)
																.contentType(MediaType.APPLICATION_JSON)
																.content(JSON_OBJECT_EXPECTED)
																.accept(MediaType.APPLICATION_JSON);
		
		mockMvc
				.perform(mockHttpRequest)
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
				.andExpect(jsonPath("$.messages[0]").value("Il barcode " + barcodeToFind + " non è stato trovato!"))
				.andDo(print());
	}

	@Test
	public void testCercaArticoloByCodice_OK() throws Exception {
		String codiceArticoloToFind = "002000301";
		
		MockHttpServletRequestBuilder mockHttpRequest = MockMvcRequestBuilders.get("/api/articoli/cerca/codice/" + codiceArticoloToFind)
																			  .accept(MediaType.APPLICATION_JSON);
		
		mockMvc
				.perform(mockHttpRequest)
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(content().json(JSON_OBJECT_EXPECTED))
				.andReturn();
	}

	@Test
	public void testCercaArticoloByCodice_KO_notFound() throws Exception {
		String codiceArticoloToFind = "002000301b";
		
		MockHttpServletRequestBuilder mockHttpRequest = MockMvcRequestBuilders
																.get("/api/articoli/cerca/codice/" + codiceArticoloToFind)
																.contentType(MediaType.APPLICATION_JSON)
																.content(JSON_OBJECT_EXPECTED)
																.accept(MediaType.APPLICATION_JSON);
		
		mockMvc
				.perform(mockHttpRequest)
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
				.andExpect(jsonPath("$.messages[0]").value("L'articolo con codice " + codiceArticoloToFind + " non è stato trovato!"))
				.andDo(print());
	}

	@Test
	public void testCercaArticoloByDescrizione_OK() throws Exception {
		 final String JSON_OBJECT_ARRAY_EXPECTED = new StringBuilder()
					// @formatter:off
																	.append("[").
																		append(JSON_OBJECT_EXPECTED)
																	.append("]").toString();
					// @formatter:on
		 
		String descrizioneArticoloToFind = "ACQUA ULIVETO 15 LT";
		
		MockHttpServletRequestBuilder mockHttpRequest = MockMvcRequestBuilders
																.get("/api/articoli/cerca/descrizione/" + descrizioneArticoloToFind)
																.accept(MediaType.APPLICATION_JSON);
		
		mockMvc
				.perform(mockHttpRequest)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(content().json(JSON_OBJECT_ARRAY_EXPECTED))
				.andReturn();
	}
}
