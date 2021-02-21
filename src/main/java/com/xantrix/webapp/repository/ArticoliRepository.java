package com.xantrix.webapp.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xantrix.webapp.entities.Articoli;

public interface ArticoliRepository extends PagingAndSortingRepository<Articoli, Integer> {

	public List<Articoli> findByDescrizioneLike(String descrizione);

	public List<Articoli> findByDescrizioneLike(String descrizione, Pageable pageable);

	public Articoli findByCodArt(String codArt);

}
