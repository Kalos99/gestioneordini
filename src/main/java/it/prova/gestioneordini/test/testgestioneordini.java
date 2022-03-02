package it.prova.gestioneordini.test;

import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

import it.prova.gestioneordini.dao.EntityManagerUtil;
import it.prova.gestioneordini.model.Articolo;
import it.prova.gestioneordini.model.Categoria;
import it.prova.gestioneordini.model.Ordine;
import it.prova.gestioneordini.service.MyServiceFactory;
import it.prova.gestioneordini.service.articolo.ArticoloService;
import it.prova.gestioneordini.service.categoria.CategoriaService;
import it.prova.gestioneordini.service.ordine.OrdineService;

public class Testgestioneordini {

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
			testAggiungiArticoloAdOrdineEsistente(articoloServiceInstance, ordineServiceInstance);
			testCollegaArticoloECategoria(categoriaServiceInstance, articoloServiceInstance);
			testDisassociaDaCategoriaEsistente(categoriaServiceInstance, articoloServiceInstance, ordineServiceInstance);
			testRimuoviArticoloDaOrdineEsistente(articoloServiceInstance, ordineServiceInstance);
			testRimuoviForzatamenteOrdine(ordineServiceInstance, articoloServiceInstance, categoriaServiceInstance);
			testTrovaOrdiniConCategoria(ordineServiceInstance, articoloServiceInstance, categoriaServiceInstance);
			testTrovaCategorieDistineInOrdine(ordineServiceInstance, articoloServiceInstance, categoriaServiceInstance);
			
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
		System.out.println("");

		Date dataInserimento = new SimpleDateFormat("dd/MM/yyyy").parse("01/03/2022");
		Ordine ordineInstance = new Ordine("Calogero Corsello", "Via Renato Simoni 73", dataInserimento);
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if (ordineInstance.getId() == null)
			throw new RuntimeException("testInserimentoNuovoOrdine fallito ");

		System.out.println(".......testInserimentoNuovoOrdine fine: PASSED.............");
		System.out.println("");
	}
	
	private static void testInserimentoNuovaCategoria(CategoriaService categoriaServiceInstance) throws Exception{
		System.out.println(".......testInserimentoNuovaCategoria inizio.............");
		System.out.println("");

		Categoria categoriaInstance = new Categoria("Computer", "Codice1");
		categoriaServiceInstance.inserisciNuovo(categoriaInstance);
		if (categoriaInstance.getId() == null)
			throw new RuntimeException("testInserimentoNuovaCategoria fallito ");

		System.out.println(".......testInserimentoNuovaCategoria fine: PASSED.............");
		System.out.println("");
	}
	
	private static void testAggiungiArticoloAdOrdineEsistente(ArticoloService articoloServiceInstance, OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testAggiungiArticoloAdOrdineEsistente inizio.............");
		System.out.println("");

		Date dataInserimento = new SimpleDateFormat("dd/MM/yyyy").parse("12/03/2021");
		Ordine ordineInstance = new Ordine("Emanuele Seminara", "Via Tersicore 18", dataInserimento);
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if (ordineInstance.getId() == null)
			throw new RuntimeException("testAggiungiArticoloAdOrdineEsistente fallito ");

		Date dataInserimento1 = new SimpleDateFormat("dd/MM/yyyy").parse("14/01/2021");
		Articolo nuovoArticolo = new Articolo("Mac Air", "29ASDWE2W8SHD", 900, dataInserimento1);
		nuovoArticolo.setOrdine(ordineInstance);
		ordineServiceInstance.associaArticoloAdOrdine(ordineInstance, nuovoArticolo);
		Ordine ordineReloaded = ordineServiceInstance.caricaOrdineSingoloConArticoli(ordineInstance.getId());
		if(ordineReloaded.getArticoli().size() != 1) {
			throw new RuntimeException("testAggiungiArticoloAdOrdineEsistente fallito: associazione articolo-ordine fallita");
		}
		System.out.println(".......testAggiungiArticoloAdOrdineEsistente fine: PASSED.............");
		System.out.println("");
	}
	
	private static void testCollegaArticoloECategoria(CategoriaService categoriaServiceInstance, ArticoloService articoloServiceInstance) throws Exception {
		System.out.println(".......testAggiungiCategoriaAdArticoloEsistente inizio.............");
		System.out.println("");

		Categoria categoriaInstance = new Categoria("Elettronica", "Codice2");
		categoriaServiceInstance.inserisciNuovo(categoriaInstance);
		if (categoriaInstance.getId() == null)
			throw new RuntimeException("testInserimentoNuovaCategoria fallito ");

		List<Articolo> listaArticoliPresenti = articoloServiceInstance.listAllArticoli();
		Articolo ultimoArticoloAggiunto = listaArticoliPresenti.get(listaArticoliPresenti.size()-1);
		articoloServiceInstance.collegaACategoriaEsistente(categoriaInstance, ultimoArticoloAggiunto);
		Articolo articoloReloaded = articoloServiceInstance.caricaArticoloSingoloConCategorie(ultimoArticoloAggiunto.getId());
		if(articoloReloaded.getCategorie().size() != 1) {
			throw new RuntimeException("testAggiungiCategoriaAdArticoloEsistente fallito: associazione articolo-categoria fallita");
		}
		System.out.println(".......testAggiungiCategoriaAdArticoloEsistente fine: PASSED.............");
		System.out.println("");
	}
	
	private static void testDisassociaDaCategoriaEsistente(CategoriaService categoriaServiceInstance, ArticoloService articoloServiceInstance, OrdineService ordineServiceInstance) throws Exception{
		System.out.println(".......testDisassociaDaCategoriaEsistente inizio.............");
		System.out.println("");

		Categoria categoriaInstance = new Categoria("Informatica", "Codice3");
		categoriaServiceInstance.inserisciNuovo(categoriaInstance);
		if (categoriaInstance.getId() == null)
			throw new RuntimeException("testDisassociaDaCategoriaEsistente fallito ");

		Date dataInserimento = new SimpleDateFormat("dd/MM/yyyy").parse("17/01/2021");
		Articolo nuovoArticolo = new Articolo("Tastiera meccanica", "4EI323DC0D23M", 60, dataInserimento);
		List<Ordine> listaOrdiniPresenti = ordineServiceInstance.listAllOrdini();
		Ordine ultimoOrdineInserito = listaOrdiniPresenti.get(listaOrdiniPresenti.size()-1);
		nuovoArticolo.setOrdine(ultimoOrdineInserito);
		articoloServiceInstance.inserisciNuovo(nuovoArticolo);
		ordineServiceInstance.associaArticoloAdOrdine(ultimoOrdineInserito, nuovoArticolo);
		Ordine ordineReloaded = ordineServiceInstance.caricaOrdineSingoloConArticoli(ultimoOrdineInserito.getId());
		if(ordineReloaded.getArticoli().size() < 1) {
			throw new RuntimeException("testAggiungiArticoloAdOrdineEsistente fallito: associazione articolo-ordine fallita");
		}
		articoloServiceInstance.collegaACategoriaEsistente(categoriaInstance, nuovoArticolo);
		Articolo articoloReloaded = articoloServiceInstance.caricaArticoloSingoloConCategorie(nuovoArticolo.getId());
		if(articoloReloaded.getCategorie().size() != 1) {
			throw new RuntimeException("testDisassociaDaCategoriaEsistente fallito: associazione articolo-categoria fallita");
		}
		
		articoloServiceInstance.disassociaDaCategoriaEsistente(categoriaInstance, articoloReloaded);
		Articolo articoloReloaded2 = articoloServiceInstance.caricaArticoloSingoloConCategorie(articoloReloaded.getId());
		if(articoloReloaded2.getCategorie().size() !=  0) {
			throw new RuntimeException("testDisassociaDaCategoriaEsistente fallito: disassociazione articolo-categoria fallita");
		}
		System.out.println(".......testDisassociaDaCategoriaEsistente fine: PASSED.............");
		System.out.println("");
	}
	
	private static void testRimuoviArticoloDaOrdineEsistente(ArticoloService articoloServiceInstance, OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testRimuoviArticoloDaOrdineEsistente inizio.............");
		System.out.println("");

		Date dataInserimento = new SimpleDateFormat("dd/MM/yyyy").parse("02/02/2022");
		Ordine ordineInstance = new Ordine("Flavio Amato", "Via Italia 15", dataInserimento);
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if (ordineInstance.getId() == null)
			throw new RuntimeException("testRimuoviArticoloDaOrdineEsistente fallito ");

		Date dataInserimento1 = new SimpleDateFormat("dd/MM/yyyy").parse("14/05/2021");
		Articolo nuovoArticolo = new Articolo("Air pods", "SHDED24020HD2", 250, dataInserimento1);
		nuovoArticolo.setOrdine(ordineInstance);
		articoloServiceInstance.inserisciNuovo(nuovoArticolo);
		ordineServiceInstance.associaArticoloAdOrdine(ordineInstance, nuovoArticolo);
		Ordine ordineReloaded = ordineServiceInstance.caricaOrdineSingoloConArticoli(ordineInstance.getId());
		System.out.println(ordineReloaded.getArticoli().size());
		if(ordineReloaded.getArticoli().size() != 1) {
			throw new RuntimeException("testRimuoviArticoloDaOrdineEsistente fallito: associazione articolo-ordine fallita");
		}
		ordineServiceInstance.rimuoviArticoloDaOrdineEsistente(nuovoArticolo);	
		Ordine ordineReloaded2 = ordineServiceInstance.caricaOrdineSingoloConArticoli(ordineInstance.getId());
		System.out.println(ordineReloaded2.getArticoli().size());
		if(ordineReloaded2.getArticoli().size() != 0) {
			throw new RuntimeException("testRimuoviArticoloDaOrdineEsistente fallito: disassociazione articolo-ordine fallita");
		}
		System.out.println(".......testRimuoviArticoloDaOrdineEsistente fine: PASSED.............");
		System.out.println("");
	}
	
	private static void testRimuoviForzatamenteOrdine(OrdineService ordineServiceInstance, ArticoloService articoloServiceInstance, CategoriaService categoriaServiceInstance) throws Exception{
		System.out.println(".......testRimuoviForzatamenteOrdine inizio.............");
		System.out.println("");

		Date dataInserimento = new SimpleDateFormat("dd/MM/yyyy").parse("15/09/2021");
		Ordine ordineInstance = new Ordine("Luca Tamburo", "Via Nino Martoglio 2", dataInserimento);
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if (ordineInstance.getId() == null)
			throw new RuntimeException("testRimuoviForzatamenteOrdine fallito ");

		Date dataInserimento1 = new SimpleDateFormat("dd/MM/yyyy").parse("21/05/2021");
		Articolo nuovoArticolo = new Articolo("ipad", "COW24WQSCGY32", 500, dataInserimento1);
		nuovoArticolo.setOrdine(ordineInstance);
		articoloServiceInstance.inserisciNuovo(nuovoArticolo);
		ordineServiceInstance.associaArticoloAdOrdine(ordineInstance, nuovoArticolo);
		Ordine ordineReloaded = ordineServiceInstance.caricaOrdineSingoloConArticoli(ordineInstance.getId());
		if(ordineReloaded.getArticoli().size() != 1) {
			throw new RuntimeException("testRimuoviForzatamenteOrdine fallito: associazione articolo-ordine fallita");
		}
		List<Categoria> listaCategoriePresenti = categoriaServiceInstance.listAllCategorie();
		Categoria ultimaCategoriaInserita = listaCategoriePresenti.get(listaCategoriePresenti.size()-1);
		articoloServiceInstance.collegaACategoriaEsistente(ultimaCategoriaInserita, nuovoArticolo);
		Articolo articoloReloaded = articoloServiceInstance.caricaArticoloSingoloConCategorie(nuovoArticolo.getId());
		if(articoloReloaded.getCategorie().size() != 1) 
			throw new RuntimeException("testAggiungiCategoriaAdArticoloEsistente fallito: associazione articolo-categoria fallita");
		List<Ordine> listaOrdiniPresenti = ordineServiceInstance.listAllOrdini();
		ordineServiceInstance.rimuoviForzatamenteOrdine(ordineReloaded);
		List<Ordine> listaPostRimozione = ordineServiceInstance.listAllOrdini();
		if(listaOrdiniPresenti.size() == listaPostRimozione.size()) 
			throw new RuntimeException("testRimuoviForzatamenteOrdine fallito: rimozione fallita");
		
		System.out.println(".......testRimuoviForzatamenteOrdine fine: PASSED.............");
		System.out.println("");
	}
	
	private static void testTrovaOrdiniConCategoria(OrdineService ordineServiceInstance, ArticoloService articoloServiceInstance, CategoriaService categoriaServiceInstance) throws Exception{
		System.out.println(".......testTrovaOrdiniConCategoria inizio.............");
		System.out.println("");

		Date dataInserimento = new SimpleDateFormat("dd/MM/yyyy").parse("30/09/2021");
		Ordine ordineInstance = new Ordine("Matteo Politano", "Via Napoli 321", dataInserimento);
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if (ordineInstance.getId() == null)
			throw new RuntimeException("testTrovaOrdiniConCategoria fallito ");

		Date dataInserimento1 = new SimpleDateFormat("dd/MM/yyyy").parse("21/07/2021");
		Articolo nuovoArticolo = new Articolo("tapis roulant", "XSA93DF123AZR", 300, dataInserimento1);
		nuovoArticolo.setOrdine(ordineInstance);
		articoloServiceInstance.inserisciNuovo(nuovoArticolo);
		ordineServiceInstance.associaArticoloAdOrdine(ordineInstance, nuovoArticolo);
		Ordine ordineReloaded = ordineServiceInstance.caricaOrdineSingoloConArticoli(ordineInstance.getId());
		if(ordineReloaded.getArticoli().size() != 1) {
			throw new RuntimeException("testTrovaOrdiniConCategoria fallito: associazione articolo-ordine fallita");
		}
		Categoria categoriaInstance = new Categoria("Attrezzatura", "Codice4");
		categoriaServiceInstance.inserisciNuovo(categoriaInstance);
		if (categoriaInstance.getId() == null)
			throw new RuntimeException("testTrovaOrdiniConCategoria fallito ");
		articoloServiceInstance.collegaACategoriaEsistente(categoriaInstance, nuovoArticolo);
		Articolo articoloReloaded = articoloServiceInstance.caricaArticoloSingoloConCategorie(nuovoArticolo.getId());
		if(articoloReloaded.getCategorie().size() != 1) 
			throw new RuntimeException("testTrovaOrdiniConCategoria fallito: associazione articolo-categoria fallita");
		List<Ordine> ordiniConCategoriaX = ordineServiceInstance.trovaOrdiniConCategoria(categoriaInstance);
		if(ordiniConCategoriaX.size() != 1) 
			throw new RuntimeException("testTrovaOrdiniConCategoria fallito: ricerca fallita");
		
		System.out.println(".......testTrovaOrdiniConCategoria fine: PASSED.............");
		System.out.println("");
	}
	
	private static void testTrovaCategorieDistineInOrdine(OrdineService ordineServiceInstance, ArticoloService articoloServiceInstance, CategoriaService categoriaServiceInstance) throws Exception{
		System.out.println(".......testTrovaOrdiniConCategoria inizio.............");
		System.out.println("");

		Date dataInserimento = new SimpleDateFormat("dd/MM/yyyy").parse("31/10/2021");
		Ordine ordineInstance = new Ordine("Samuele Marino", "Via Marsala 235", dataInserimento);
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if (ordineInstance.getId() == null)
			throw new RuntimeException("testTrovaOrdiniConCategoria fallito ");

		Date dataInserimento1 = new SimpleDateFormat("dd/MM/yyyy").parse("24/05/2021");
		Articolo nuovoArticolo1 = new Articolo("mac", "CDMEO345ZAP24", 900, dataInserimento1);
		nuovoArticolo1.setOrdine(ordineInstance);
		articoloServiceInstance.inserisciNuovo(nuovoArticolo1);
		
		Date dataInserimento2 = new SimpleDateFormat("dd/MM/yyyy").parse("19/07/2021");
		Articolo nuovoArticolo2 = new Articolo("armadio", "ZAWEI34564QOED", 100, dataInserimento2);
		nuovoArticolo2.setOrdine(ordineInstance);
		articoloServiceInstance.inserisciNuovo(nuovoArticolo2);
		
		Date dataInserimento3 = new SimpleDateFormat("dd/MM/yyyy").parse("21/10/2021");
		Articolo nuovoArticolo3 = new Articolo("tavolo", "CSO44SFI76XPA", 60, dataInserimento3);
		nuovoArticolo3.setOrdine(ordineInstance);
		articoloServiceInstance.inserisciNuovo(nuovoArticolo3);
		
		ordineServiceInstance.associaArticoloAdOrdine(ordineInstance, nuovoArticolo1);
		ordineServiceInstance.associaArticoloAdOrdine(ordineInstance, nuovoArticolo2);
		ordineServiceInstance.associaArticoloAdOrdine(ordineInstance, nuovoArticolo3);
		
		Ordine ordineReloaded = ordineServiceInstance.caricaOrdineSingoloConArticoli(ordineInstance.getId());
		if(ordineReloaded.getArticoli().size() != 3) {
			throw new RuntimeException("testTrovaOrdiniConCategoria fallito: associazione articolo-ordine fallita");
		}
		
		Categoria categoriaInstance1 = new Categoria("Tecnologia", "Codice5");
		categoriaServiceInstance.inserisciNuovo(categoriaInstance1);
		if (categoriaInstance1.getId() == null)
			throw new RuntimeException("testTrovaOrdiniConCategoria fallito ");
		
		Categoria categoriaInstance2 = new Categoria("Arredamento", "Codice6");
		categoriaServiceInstance.inserisciNuovo(categoriaInstance2);
		if (categoriaInstance2.getId() == null)
			throw new RuntimeException("testTrovaOrdiniConCategoria fallito ");
		
		articoloServiceInstance.collegaACategoriaEsistente(categoriaInstance1, nuovoArticolo1);
		articoloServiceInstance.collegaACategoriaEsistente(categoriaInstance2, nuovoArticolo2);
		articoloServiceInstance.collegaACategoriaEsistente(categoriaInstance2, nuovoArticolo3);
		
		Articolo articoloReloaded1 = articoloServiceInstance.caricaArticoloSingoloConCategorie(nuovoArticolo1.getId());
		if(articoloReloaded1.getCategorie().size() != 1) 
			throw new RuntimeException("testTrovaOrdiniConCategoria fallito: associazione articolo-categoria fallita");
		
		Articolo articoloReloaded2 = articoloServiceInstance.caricaArticoloSingoloConCategorie(nuovoArticolo2.getId());
		if(articoloReloaded2.getCategorie().size() != 1) 
			throw new RuntimeException("testTrovaOrdiniConCategoria fallito: associazione articolo-categoria fallita");
		
		Articolo articoloReloaded3 = articoloServiceInstance.caricaArticoloSingoloConCategorie(nuovoArticolo3.getId());
		if(articoloReloaded3.getCategorie().size() != 1) 
			throw new RuntimeException("testTrovaOrdiniConCategoria fallito: associazione articolo-categoria fallita");
		
		List<Categoria> categorieDistinteInOrdine = ordineServiceInstance.trovaCategorieDistinteInOrdine(ordineReloaded.getId());
		if(categorieDistinteInOrdine.size() != 2) 
			throw new RuntimeException("testTrovaOrdiniConCategoria fallito: ricerca fallita");
		
		System.out.println(".......testTrovaOrdiniConCategoria fine: PASSED.............");
		System.out.println("");
	}
	
}