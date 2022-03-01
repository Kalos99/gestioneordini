package it.prova.gestioneordini.service.articolo;

import java.util.List;

import it.prova.gestioneordini.dao.articolo.ArticoloDAO;
import it.prova.gestioneordini.dao.categoria.CategoriaDAO;
import it.prova.gestioneordini.model.Articolo;

public interface ArticoloService {

	public List<Articolo> listAllArticoli() throws Exception;

	public Articolo caricaSingoloArticolo(Long id) throws Exception;

	public void aggiorna(Articolo articoloInstance) throws Exception;

	public void inserisciNuovo(Articolo articoloInstance) throws Exception;

	public void rimuovi(Articolo articoloInstance) throws Exception;

	// per injection
	public void setArticoloDAO(ArticoloDAO articoloDAO);
	
	public void setCategoriaDAO(CategoriaDAO categoriaDAO);
}