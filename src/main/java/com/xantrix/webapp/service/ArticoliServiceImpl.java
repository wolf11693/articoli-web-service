package com.xantrix.webapp.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xantrix.webapp.entities.Articoli;
import com.xantrix.webapp.entities.Barcode;
import com.xantrix.webapp.repository.ArticoliRepository;
import com.xantrix.webapp.service.param.SelArticoliParam;


@Service
public class ArticoliServiceImpl implements ArticoliService {
	
	private static final Logger log = LoggerFactory.getLogger(ArticoliServiceImpl.class);

	@Autowired
	private ArticoliRepository articoliRepo;
	
	@Autowired
	private BarcodeService barcodeService;
	
	@Override
	public Iterable<Articoli> SelTutti() {
		return articoliRepo.findAll();
	}
	
	@Override
	public List<Articoli> SelByDescrizione(SelArticoliParam param) {
		String descrizione = param.getDescrizione();
		
		if(param.isPaginato()) {
			return articoliRepo.findByDescrizioneLike(descrizione, param.getPageable());
		}
		return articoliRepo.findByDescrizioneLike(descrizione);
	}

	@Override
	@Transactional
	public Articoli SelByBarcode(String barcode)  {
		log.info("SelByBarcode - START - barcode={}", barcode);
	
		Barcode theBarcode = this.barcodeService.SelByBarcode(barcode);
		Articoli articoliFounded = null;
		if(theBarcode != null) {
			articoliFounded = theBarcode.getArticolo();
		}
		
		log.info("SelByBarcode - END");
		return articoliFounded;
	}

	
	@Override
	public Articoli SelByCodArt(String codArt) {
		log.info("SelByCodArt - START - codArt={}", codArt);
		
		Articoli theArticolo = articoliRepo.findByCodArt(codArt);
		log.info("SelByCodArt - END");

		return theArticolo;
	}
	
	@Override
	@Transactional
	public void InsArticolo(Articoli articolo) {
		log.info("InsArticolo - START - articolo={}", articolo.getDescrizione());
		articoliRepo.save(articolo);
		log.info("InsArticolo - END");
	}

	@Override
	@Transactional
	public void DelArticolo(Articoli articolo) {
		log.info("DelArticolo - START - articolo={}", articolo.getDescrizione());
		articoliRepo.delete(articolo);
		log.info("DelArticolo - START - articolo={}", articolo.getDescrizione());
		
	}
}
