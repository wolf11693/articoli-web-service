package com.xantrix.webapp.service.param;

import org.springframework.data.domain.Pageable;

public class SelArticoliParam {
	private boolean paginato = false;
	private String descrizione;
	private Pageable pageable;
	
	public SelArticoliParam() {
		this(null);
	}
	
	public SelArticoliParam(String descrizione) {
		super();
		this.descrizione = descrizione;
	}
	
	public boolean isPaginato() {
		return paginato;
	}
	
	public void setPaginato(boolean paginato) {
		this.paginato = paginato;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Pageable getPageable() {
		return pageable;
	}

	public void setPageable(Pageable pageable) {
		this.pageable = pageable;
	}
	
}
