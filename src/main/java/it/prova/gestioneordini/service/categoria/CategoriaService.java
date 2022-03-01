package it.prova.gestioneordini.service.categoria;

import java.util.List;

import it.prova.gestioneordini.dao.articolo.ArticoloDAO;
import it.prova.gestioneordini.dao.categoria.CategoriaDAO;
import it.prova.gestioneordini.model.Categoria;


public interface CategoriaService {

	
	public List<Categoria> listAllCategorie() throws Exception;

	public Categoria caricaSingolaCategoria(Long id) throws Exception;

	public void aggiorna(Categoria categoriaInstance) throws Exception;

	public void inserisciNuovo(Categoria categoriaInstance) throws Exception;

	public void rimuovi(Categoria categoriaInstance) throws Exception;

	//per injection
	public void setCategoriaDAO(CategoriaDAO categoriaDAO);
	
	public void setArticoloDAO(ArticoloDAO articoloDAO);

}
