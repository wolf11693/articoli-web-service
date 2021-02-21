package com.xantrix.webapp.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "INGREDIENTI")
public class Ingredienti implements Serializable {
	private static final long serialVersionUID = -6230810713799336046L;

	@Id
	@Column(name = "CODART")
	private String codArt;

	@Column(name = "INFO")
	private String info;

	@OneToOne
	@PrimaryKeyJoinColumn
	@JsonIgnore
	private Articoli articolo;
	
	public Ingredienti() {
		super();
	}

	public String getCodArt() {
		return codArt;
	}

	public void setCodArt(String codArt) {
		this.codArt = codArt;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Articoli getArticolo() {
		return articolo;
	}

	public void setArticolo(Articoli articolo) {
		this.articolo = articolo;
	}
	
}
