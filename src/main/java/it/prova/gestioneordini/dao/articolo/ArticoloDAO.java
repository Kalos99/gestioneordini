package it.prova.gestioneordini.dao.articolo;

import java.util.List;

import it.prova.gestioneordini.dao.IBaseDAO;
import it.prova.gestioneordini.model.Articolo;
import it.prova.gestioneordini.model.Categoria;
import it.prova.gestioneordini.model.Ordine;

public interface ArticoloDAO extends IBaseDAO<Articolo> {
	public List<Articolo> findAllByCategoria(Categoria categoriaInput) throws Exception; 
	public List<Articolo> findAllByOrdine(Ordine ordineInput) throws Exception; 
}
