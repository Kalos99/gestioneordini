package it.prova.gestioneordini.service.articolo;

import java.util.List;

import it.prova.gestioneordini.dao.articolo.ArticoloDAO;
import it.prova.gestioneordini.dao.categoria.CategoriaDAO;
import it.prova.gestioneordini.dao.ordine.OrdineDAO;
import it.prova.gestioneordini.model.Articolo;
import it.prova.gestioneordini.model.Categoria;

public interface ArticoloService {

	public List<Articolo> listAllArticoli() throws Exception;

	public Articolo caricaSingoloArticolo(Long id) throws Exception;

	public void aggiorna(Articolo articoloInstance) throws Exception;

	public void inserisciNuovo(Articolo articoloInstance) throws Exception;

	public void rimuovi(Articolo articoloInstance) throws Exception;
	
	public void collegaACategoriaEsistente(Categoria categoriaInstance, Articolo articoloInstance) throws Exception;
	
	public void disassociaDaCategoriaEsistente(Categoria categoriaInstance, Articolo articoloInstance) throws Exception;
	
	public Articolo caricaArticoloSingoloConCategorie(Long id) throws Exception;

	// per injection
	public void setArticoloDAO(ArticoloDAO articoloDAO);
	
	public void setCategoriaDAO(CategoriaDAO categoriaDAO);
	
	public void setOrdineDAO(OrdineDAO ordineDAO);
}
