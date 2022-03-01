package it.prova.gestioneordini.test;

import it.prova.gestioneordini.dao.EntityManagerUtil;
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
}