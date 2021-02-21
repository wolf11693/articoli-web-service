package com.xantrix.webapp.controller;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xantrix.webapp.entities.Articoli;
import com.xantrix.webapp.exception.ConflictException;
import com.xantrix.webapp.exception.NotFoundException;
import com.xantrix.webapp.exception.ValidationException;
import com.xantrix.webapp.service.ArticoliService;
import com.xantrix.webapp.service.param.SelArticoliParam;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(path = "/api/articoli")
public class ArticoliController {

	private static final Logger log = LoggerFactory.getLogger(ArticoliController.class);

	@Autowired
	private ArticoliService articoliService;
	
	@Autowired
	private ResourceBundleMessageSource errorMessageValidation;

	
	
	@ApiOperation(
			value = "Ricerca l'articolo per codice a barre", 
		    notes = "Restituisce i dati dell'articolo in formato JSON",
		    response = Articoli.class, 
		    produces = "application/json"
	)
	@ApiResponses(
			value = { @ApiResponse(code = 200, message = "Articolo Trovato"),
					  @ApiResponse(code = 404, message = "Articolo Non Trovato") }
	)
	@GetMapping(
				path = "/cerca/ean/{eanArt}", 
				produces = { "application/json" }
	)
	public ResponseEntity<?> cercaArticoloByEan(
			@ApiParam(name = "Barcode univoco dell'articolo") @PathVariable("eanArt") String barcode) throws NotFoundException, Exception {
		log.info("==> GET /api/articoli/cerca/ean/{}", barcode);
		log.info("cercaArticoloByEan - START - barcode/ean={}", barcode);

		Articoli articoloFounded = this.articoliService.SelByBarcode(barcode);
		if(articoloFounded == null) {
			String errorMsg = String.format("Il barcode %s non è stato trovato!", barcode);
			throw new NotFoundException(errorMsg);
		}
		
		log.info("cercaArticoloByEan - END");
		return new ResponseEntity<>(articoloFounded, HttpStatus.OK);
	}

	@ApiOperation(
			value = "Ricerca l'articolo per codice", 
		    notes = "Restituisce i dati dell'articolo in formato JSON",
		    response = Articoli.class, 
		    produces = "application/json"
	)
	@ApiResponses(
			value = { @ApiResponse(code = 200, message = "Articolo Trovato"),
					  @ApiResponse(code = 404, message = "Articolo Non Trovato") }
	)
	@GetMapping(path = "/cerca/codice/{codArt}", produces = { "application/json" })
	public ResponseEntity<?> cercaArticoloByCodice(@PathVariable("codArt") String codiceArticolo) throws NotFoundException, Exception {
		log.info("==> GET /api/articoli/cerca/codice/{}", codiceArticolo);
		log.info("cercaArticoloByCodice - START - codiceArticolo={}", codiceArticolo);

		Articoli articoloFounded = this.articoliService.SelByCodArt(codiceArticolo);
		if(articoloFounded == null) {
			String errorMsg = String.format("L'articolo con codice %s non è stato trovato!", codiceArticolo);
			throw new NotFoundException(errorMsg);
		}
		
		log.info("cercaArticoloByCodice - END");
		return new ResponseEntity<>(articoloFounded, HttpStatus.OK);
	}
	
	@ApiOperation(
			value = "Ricerca l'articolo mediante filtro sulla sua descrizione", 
		    notes = "Restituisce i dati dell'articolo in formato JSON",
		    response = Articoli.class, 
		    produces = "application/json"
	)
	@ApiResponses(
			value = { @ApiResponse(code = 200, message = "Articolo Trovato"),
					  @ApiResponse(code = 404, message = "Articolo Non Trovato") }
	)
	@GetMapping(
				path = "/cerca/descrizione/{filterDescArt}",
				produces = { "application/json" }
	)
	public ResponseEntity<?> cercaArticoloByDescrizione(
			@ApiParam(name = "filtro descrizione dell'articolo") @PathVariable("filterDescArt") String filtroDescrizioneArticolo) throws NotFoundException, Exception {
		log.info("==> GET /api/articoli/cerca/descrizione/{}", filtroDescrizioneArticolo);
		log.info("cercaArticoloByDescrizione - START - filtroDescrizioneArticolo={}", filtroDescrizioneArticolo);

		SelArticoliParam selArticoliParam = new SelArticoliParam();
		selArticoliParam.setDescrizione(filtroDescrizioneArticolo);
		List<Articoli> articoliList = this.articoliService.SelByDescrizione(selArticoliParam);
		if (articoliList == null || articoliList.isEmpty()) {
			String errorMsg = String.format("Non è stato trovato alcun articolo avente il parametro %s", filtroDescrizioneArticolo);
			throw new NotFoundException(errorMsg);
		}

		log.info("cercaArticoloByDescrizione - END");
		return new ResponseEntity<>(articoliList, HttpStatus.OK);
	}
	
	@ApiOperation(
			value = "Ricerca l'articolo mediante filtro sulla sua descrizione effettuando la paginazione", 
		    notes = "Restituisce i dati dell'articolo in formato JSON",
		    response = Articoli.class, 
		    produces = "application/json"
	)
	@ApiResponses(
			value = { @ApiResponse(code = 200, message = "Articolo Trovato"),
					  @ApiResponse(code = 404, message = "Articolo Non Trovato") }
	)
	@GetMapping(
				path = "/cerca/descrizione/{filterDescArt}/{page}/{rows}", 
				produces = "application/json"
	)
	public ResponseEntity<List<Articoli>> cercaArticoloByDescrizionePaginato(
			@ApiParam(name = "filtro descrizione dell'articolo") @PathVariable("filterDescArt") String filtroDescrizioneArticolo,
			@ApiParam(name = "numero di pagina") @PathVariable("page") int pageNum, 
			@ApiParam(name = "numero articoli per pagina") @PathVariable("rows") int nRecordXPage) throws NotFoundException {
		log.info("==> GET /api/articoli/cerca/descrizione/{}/{}/{}", filtroDescrizioneArticolo);
		log.info("cercaArticoloByDescrizionePaginato - START - filtroDescrizioneArticolo={} , pagina={}, numeroRecordXPagina", filtroDescrizioneArticolo, pageNum, nRecordXPage);

		nRecordXPage = (nRecordXPage < 0) ? 10 : nRecordXPage;

		SelArticoliParam param = new SelArticoliParam();
		param.setDescrizione(filtroDescrizioneArticolo + "%");
		param.setPaginato(true);
		param.setPageable(PageRequest.of(pageNum, nRecordXPage));
		List<Articoli> articoliList = articoliService.SelByDescrizione(param);

		if (articoliList == null || articoliList.isEmpty()) {
			String errorMsg = String.format("Non è stato trovato alcun articolo avente il parametro %s", filtroDescrizioneArticolo);
			log.warn(errorMsg);
			throw new NotFoundException(errorMsg);
		}

		log.info("cercaArticoloByDescrizionePaginato - END");
		return new ResponseEntity<List<Articoli>>(articoliList, HttpStatus.OK);
	}

	@PostMapping(
				 path = "/inserisci", 
				 consumes = { "application/json" }, 
				 produces = { "application/json" }
	)
	public ResponseEntity<?> inserisciArticolo(
				@Valid @RequestBody Articoli articolo, 
				BindingResult bindingResult,
				UriComponentsBuilder ucBuilder) throws ConflictException, Exception {
		log.info("==> POST /api/articoli/inserisci");
		log.info("inserisciArticolo - START - articolo={}", articolo);

		if(bindingResult.hasErrors()){
			FieldError fieldError = bindingResult.getFieldError();
			Locale locale = LocaleContextHolder.getLocale();
			String errorMsg = errorMessageValidation.getMessage(fieldError, locale);
			log.warn(errorMsg);
			throw new ValidationException(errorMsg);
		}
		
		Articoli articoloFetched = articoliService.SelByCodArt(articolo.getCodArt());
		if(articoloFetched != null) {
			String errorMsg = String.format("Articolo %s presente in anagrafica! " + "Impossibile utilizzare il metodo POST", articolo.getCodArt());
			log.warn(errorMsg);
			throw new ConflictException(errorMsg);
		}
		articoliService.InsArticolo(articolo);
			
		log.info("inserisciArticolo - END");
		return new ResponseEntity<>(articolo, HttpStatus.CREATED);

	}

	@PutMapping(
				path = "/modifica",
				consumes = { "application/json" },
				produces = { "application/json" }
				)
	public ResponseEntity<?> modificaArticolo(
				@Valid @RequestBody Articoli articoloToUpdate,
				BindingResult bindingResult) throws ValidationException, NotFoundException, Exception {
		log.info("==> PUT /api/articoli/inserisci");
		log.info("modificaArticolo - START - articoli={}", articoloToUpdate);

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode responseNode = mapper.createObjectNode();
		
		if(bindingResult.hasErrors()){
			FieldError fieldError = bindingResult.getFieldError();
			Locale locale = LocaleContextHolder.getLocale();
			String errorMsg = errorMessageValidation.getMessage(fieldError, locale);
			log.info("error validation occured in field={}, errorMessage={}", fieldError, errorMsg);
			throw new ValidationException(errorMsg);
		}
		
		Articoli articoloFetched = this.articoliService.SelByCodArt(articoloToUpdate.getCodArt());
		if (articoloFetched == null) {
			String errorMsg = String.format("L'articolo da aggiornare non esiste");
			throw new NotFoundException(errorMsg);
		}
		
		this.articoliService.InsArticolo(articoloToUpdate);
		
		responseNode.put("code", HttpStatus.OK.toString());
		responseNode.put("message", "Modifica Articolo " + articoloToUpdate.getCodArt() + " Eseguita Con Successo");

		log.info("inserisciArticolo - END");
		return new ResponseEntity<>(responseNode, HttpStatus.CREATED);
	}

	@DeleteMapping(
				   path = "/elimina/{codArt}", 
				   produces = { "application/json" }
	)
	public ResponseEntity<?> eliminaArticolo(
				@PathVariable("codArt") String codiceArticolo) throws NotFoundException, Exception {
		log.info("==> DELETE /api/articoli/elimina/{}", codiceArticolo);
		log.info("eliminaArticolo - START - idArticolo={}", codiceArticolo);

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode responseNode = mapper.createObjectNode();
		
		Articoli articoloFetched = articoliService.SelByCodArt(codiceArticolo);
		if(articoloFetched == null) {
			String errorMsg = String.format("Articolo %s non presente in anagrafica! " ,codiceArticolo);
			log.warn(errorMsg);
			throw new NotFoundException(errorMsg);
		}
		
		articoliService.DelArticolo(articoloFetched);
		
		responseNode.put("code", HttpStatus.OK.toString());
		responseNode.put("message", "Eliminazione Articolo " + codiceArticolo + " Eseguita Con Successo");
		
		log.info("inserisciArticolo - END");
		return new ResponseEntity<>(responseNode, HttpStatus.OK);
	}

}