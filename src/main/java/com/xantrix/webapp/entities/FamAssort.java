package com.xantrix.webapp.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "famassort")
public class FamAssort implements Serializable
{
	private static final long serialVersionUID = 3788120361600509595L;
	
	@Id
	//@NotNull(message = "{NotNull.FamAssort.id.Validation}")
	@Column(name = "ID")
	private int id;
	
	@Column(name = "DESCRIZIONE")
	private String descrizione;
	
	@OneToMany(fetch = FetchType.LAZY, 
			   mappedBy = "famAssort")
	@JsonIgnore
	private Set<Articoli> articoli = new HashSet<>();
	
	public FamAssort() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Set<Articoli> getArticoli() {
		return articoli;
	}

	public void setArticoli(Set<Articoli> articoli) {
		this.articoli = articoli;
	}
}
