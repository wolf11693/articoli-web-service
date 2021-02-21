package com.xantrix.webapp.service;

import java.util.List;

import com.xantrix.webapp.entities.Articoli;
import com.xantrix.webapp.service.param.SelArticoliParam;

public interface ArticoliService {

	public Iterable<Articoli> SelTutti();

	public List<Articoli> SelByDescrizione(SelArticoliParam param);

	public Articoli SelByBarcode(String barcode);
	
	public Articoli SelByCodArt(String codArt);

	public void InsArticolo(Articoli articolo);

	public void DelArticolo(Articoli articolo);
	
}
