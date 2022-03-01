package it.prova.gestioneordini.test;

import java.util.Date;
import java.text.SimpleDateFormat;

import it.prova.gestioneordini.dao.EntityManagerUtil;
import it.prova.gestioneordini.model.Articolo;
import it.prova.gestioneordini.model.Categoria;
import it.prova.gestioneordini.model.Ordine;
import it.prova.gestioneordini.service.MyServiceFactory;
import it.prova.gestioneordini.service.articolo.ArticoloService;
import it.prova.gestioneordini.service.categoria.CategoriaService;
import it.prova.gestioneordini.service.ordine.OrdineService;

public class testgestioneordini {

	public static void main(String[] args) {
		ArticoloService articoloServiceInstance = MyServiceFactory.getArticoloServiceInstance();
		CategoriaService categoriaServiceInstance = MyServiceFactory.getCategoriaServiceInstance();
		OrdineService ordineServiceInstance = MyServiceFactory.getOrdineServiceInstance();
		
		try {

			System.out.println("In tabella Articolo ci sono " + articoloServiceInstance.listAllArticoli().size() + " elementi.");
			System.out.println("");
			System.out.println("In tabella Categoria ci sono " + categoriaServiceInstance.listAllCategorie().size() + " elementi.");
			System.out.println("");
			System.out.println("In tabella Ordine ci sono " + ordineServiceInstance.listAllOrdini().size() + " elementi.");
			System.out.println("");
			System.out.println(
					"**************************** inizio batteria di test ********************************************");
			System.out.println(
					"*************************************************************************************************");

			testInserimentoNuovoOrdine(ordineServiceInstance);
			testInserimentoNuovaCategoria(categoriaServiceInstance);

			System.out.println(
					"****************************** fine batteria di test ********************************************");
			System.out.println(
					"*************************************************************************************************");
			System.out.println("In tabella Articolo ci sono " + articoloServiceInstance.listAllArticoli().size() + " elementi.");
			System.out.println("");
			System.out.println("In tabella Categoria ci sono " + categoriaServiceInstance.listAllCategorie().size() + " elementi.");
			System.out.println("");
			System.out.println("In tabella Ordine ci sono " + ordineServiceInstance.listAllOrdini().size() + " elementi.");
			System.out.println("");

		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			// questa è necessaria per chiudere tutte le connessioni quindi rilasciare il
			// main
			EntityManagerUtil.shutdown();
		}
	}
	
	private static void testInserimentoNuovoOrdine(OrdineService ordineServiceInstance) throws Exception{
		System.out.println(".......testInserimentoNuovoOrdine inizio.............");

		Date dataInserimento = new SimpleDateFormat("dd/MM/yyyy").parse("01/03/2022");
		Ordine ordineInstance = new Ordine("Calogero Corsello", "Via Renato Simoni 73", dataInserimento);
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if (ordineInstance.getId() == null)
			throw new RuntimeException("testInserimentoNuovoOrdine fallito ");

		System.out.println(".......testInserimentoNuovoOrdine fine: PASSED.............");
	}
	
	private static void testInserimentoNuovaCategoria(CategoriaService categoriaServiceInstance) throws Exception{
		System.out.println(".......testInserimentoNuovaCategoria inizio.............");

		Categoria categoriaInstance = new Categoria("Computer", "Codice1");
		categoriaServiceInstance.inserisciNuovo(categoriaInstance);
		if (categoriaInstance.getId() == null)
			throw new RuntimeException("testInserimentoNuovaCategoria fallito ");

		System.out.println(".......testInserimentoNuovaCategoria fine: PASSED.............");
	}
	
//	private static void testAggiungiArticoloAdOrdineEsistente(ArticoloService articoloServiceInstance, OrdineService ordineServiceInstance) throws Exception {
//		System.out.println(".......testAggiungiArticoloAdOrdineEsistente inizio.............");
//
//		Date dataInserimento = new SimpleDateFormat("dd/MM/yyyy").parse("12/03/2021");
//		Ordine ordineInstance = new Ordine("Emanuele Seminara", "Via Tersicore 18", dataInserimento);
//		ordineServiceInstance.inserisciNuovo(ordineInstance);
//		if (ordineInstance.getId() == null)
//			throw new RuntimeException("testAggiungiArticoloAdOrdineEsistente fallito ");
//
//		// mi creo un utente inserendolo direttamente su db
//		Date dataInserimento1 = new SimpleDateFormat("dd/MM/yyyy").parse("14/01/2021");
//		Articolo nuovoArticolo = new Articolo("Mac Air", "29ASDWE2W8SHD", 900, dataInserimento1);
//		articoloServiceInstance.associaAdOrdine(nuovoArticolo, ordineInstance);
//		articoloServiceInstance.inserisciNuovo(nuovoArticolo);
//		if (nuovoArticolo.getId() == null)
//			throw new RuntimeException("testAggiungiArticoloAdOrdineEsistente fallito: utente non inserito ");
//
//
//		System.out.println(".......testAggiungiArticoloAdOrdineEsistente fine: PASSED.............");
//	}
}