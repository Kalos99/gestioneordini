package it.prova.gestioneordini.dao.ordine;

import java.util.List;

import it.prova.gestioneordini.dao.IBaseDAO;
import it.prova.gestioneordini.model.Categoria;
import it.prova.gestioneordini.model.Ordine;

public interface OrdineDAO extends IBaseDAO<Ordine>{
	public Ordine findByIdFetchingArticoli(Long id) throws Exception;
	public Ordine findByIdFetchingArticoliFetchingCategorie(Long id) throws Exception;
	public List<Ordine> findAllByCategoria (Categoria input) throws Exception;
}
