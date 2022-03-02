package it.prova.gestioneordini.service.ordine;

import java.util.List;

import it.prova.gestioneordini.dao.articolo.ArticoloDAO;
import it.prova.gestioneordini.dao.ordine.OrdineDAO;
import it.prova.gestioneordini.model.Articolo;
import it.prova.gestioneordini.model.Categoria;
import it.prova.gestioneordini.model.Ordine;

public interface OrdineService {
	
	public List<Ordine> listAllOrdini() throws Exception;

	public Ordine caricaSingoloOrdine(Long id) throws Exception;

	public void aggiorna(Ordine ordineInstance) throws Exception;

	public void inserisciNuovo(Ordine ordineInstance) throws Exception;

	public void rimuovi(Ordine ordineInstance) throws Exception;
	
	public Ordine caricaOrdineSingoloConArticoli(Long id) throws Exception;
	
	public void associaArticoloAdOrdine(Ordine ordineInstance, Articolo articoloInstance) throws Exception;
	
	public void rimuoviArticoloDaOrdineEsistente(Articolo articoloInstance) throws Exception;
	
	public void rimuoviForzatamenteOrdine(Ordine ordineInstance) throws Exception;
	
	public List<Ordine> trovaOrdiniConCategoria(Categoria input) throws Exception;
	
	public List<Categoria> trovaCategorieDistinteInOrdine(Long id) throws Exception;

	//per injection
	public void setOrdineDAO(OrdineDAO ordineDAO);

	public void setArticoloDAO(ArticoloDAO articoloDAO);

}
