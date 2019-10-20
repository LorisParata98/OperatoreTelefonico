import java.io.*;

import java.util.*;

public class OperatoreTelefonico {
	public static Scanner scan = new Scanner(System.in);

	static List<SIM> listOfSim = new ArrayList<SIM>();
	List<String> numeriAssegnati, pukAssegnati;
	static List<Person> clienti = new ArrayList<Person>();

	public OperatoreTelefonico() {

		numeriAssegnati = new ArrayList<String>();
		pukAssegnati = new ArrayList<String>();

	}

	/**
	 * 
	 * @param nome
	 * @param cognome
	 * @return Oggetto di tipo persona aggiunto alla lista dei clienti
	 */
	public static Person creaCliente(String nome, String cognome) {
//		System.out.println("Nome :");
//		String nome = scan.next();
//		System.out.println("Cognome :");
//		String cognome = scan.next();

		Person nuovoCliente = null;
		if (clienti.isEmpty()) {
			nuovoCliente = new Person(nome, cognome);
			clienti.add(nuovoCliente);
		} else {
			if (SearchCliente(nome, cognome) != null) {
				nuovoCliente = new Person(nome, cognome);
				clienti.add(nuovoCliente);
			} else {
				System.out.println("Cliente già esistente!");
			}
		}

		return nuovoCliente;
	}

	/**
	 * 
	 * @param nome
	 * @param cognome
	 * @param numero
	 * @param puk
	 * @return Nuovo oggetto SIM legata al proprietario avente i parametri inseriti
	 *         in input
	 */
	public static SIM creaSim(String nome, String cognome, String numero, String puk) {
		SIM sim_nuova = new SIM();
		System.out.println("Inserisci informazioni");
//		System.out.println("Numero telefonico: ");
//		String numero = scan.next();
//		System.out.println("Codice puk :");
//		String puk = scan.next();
//		System.out.println("Nome :");
//		String nome = scan.next();
//		System.out.println("Cognome :");
//		String cognome = scan.next();
		clienti.toString();

		Person proprietarioSim = SearchCliente(nome, cognome);
		if (proprietarioSim == null) {
			System.err.println("Cliente non presente nel sistema!");
		} else {
			System.out.println(proprietarioSim.toString());
			sim_nuova = new SIM(proprietarioSim, numero, puk);
			System.out.println("SIM CREATA");
			sim_nuova.setNumeroDiTelefono(numero);
			sim_nuova.ricarica(0);
			listOfSim.add(sim_nuova);
		}
		return sim_nuova;

	}

	/**
	 * 
	 * @param nome
	 * @param cognome
	 * @return Oggetto di tipo persona avente il nome e cognome cercato
	 */
	private static Person SearchCliente(String nome, String cognome) {
		Person clienteCercato = null;
		// ricerca cliente se è esitente
		for (int i = 0; i <= clienti.size(); i++) {
			if ((clienti.get(i).getNome().equals(nome)) && (clienti.get(i).getCognome().equals(cognome))) {
				clienteCercato = clienti.get(i);
				break;
			}
		}
		return clienteCercato;
	}

	/**
	 * COPIA LA SIM DA PORTABILIZZARE IN UNA NUOVA SIM ED ELIMINA QUELLA DI
	 * PROVENIENZA
	 */
	public static void portabilizzaSim() {
		System.out.println("Inserisci numero della sim di provenienza");
		String numeroDiProvenienza = scan.next();
		SIM sim_diProvenienza = searchSimWithNumber(numeroDiProvenienza);
		if (sim_diProvenienza == null) {
			System.err.println("Numero  inesistente!");
		} else {
			SIM sim_daPortabilizzare = new SIM(sim_diProvenienza.getNumeroDiTelefono(), sim_diProvenienza);
			listOfSim.add(sim_daPortabilizzare);
			System.out.println("Copia effettuata");
			sim_daPortabilizzare.setPortabilizzata(true);
			System.out.println("Sim portabilizzata!");
			listOfSim.remove(sim_diProvenienza);

		}

	}

	/**
	 * Elimina un oggetto SIM inserendo il numero tramite tastiera
	 */
	public void deleteSim() {
		String numeroSimDaEliminare = scan.next();
		SIM simDaEliminare = searchSimWithNumber(numeroSimDaEliminare);
		if (simDaEliminare != null) {
			listOfSim.remove(simDaEliminare);
		}
		System.out.println("Sim non presente!");
	}

	/**
	 * 
	 * @param numeroSimRicerca Stampa in console le chiamate effettuate dal numero
	 *                         inserito in input verso un determinato numero
	 *                         inserito via tastiera
	 */
	public static void ChiamateEffettuateAdUnNumero(String numeroSimRicerca) {
		System.out.println("Inserisci numero chiamate effettuate cercato");
		String numeroDaTrovare = scan.next();
		SIM simCercata = searchSimWithNumber(numeroSimRicerca);
		for (int i = 0; i < simCercata.chiamate.size(); i++) {
			if (simCercata.chiamate.get(i).isReceived() == false
					&& simCercata.chiamate.get(i).getNumero().equals(numeroDaTrovare)) {
				System.out.println(simCercata.chiamate.get(i).getNumero());
				System.out.println("Durata: " + simCercata.chiamate.get(i).getDuration());
			} else {
				System.out.println("Numero non presente");
			}
		}

	}

	/**
	 * 
	 * @param numeroSimRicerca Stampa in console le chiamate effettuate dal numero
	 *                         ricercato
	 */
	public static void ricercaChiamate(String numeroSimRicerca) {
		SIM simCercata = searchSimWithNumber(numeroSimRicerca);
		for (int i = 0; i < simCercata.chiamate.size(); i++) {
			if (simCercata.chiamate.get(i).isReceived() == false) {
				System.out.println(simCercata.chiamate.get(i).getNumero());
				System.out.println("Durata: " + simCercata.chiamate.get(i).getDuration());
			} else {
				System.out.println("Numero non presente");
			}
		}

	}

	/**
	 * 
	 * @param num
	 * @return Valore booleano che chiarisce se una SIM è stata portabilizzata
	 */
	public boolean isPortabilizzata(String num) {
		for (int i = 0; i < listOfSim.size(); i++) {
			if (listOfSim.get(i).getNumeroDiTelefono().equals(num)) {
				return listOfSim.get(i).getPortabilizzata();
			}
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	// controlla se la scheda è ancora attiva
	public static boolean isAttiva(String numeroDaControllare) {
		boolean simAttiva = false;
		Date dataUltimaRicarica = new Date(); // stampa data attuale
		dataUltimaRicarica.setYear(dataUltimaRicarica.getYear() - 1);// sottrae 1 anno dalla data attuale
		if (searchSimWithNumber(numeroDaControllare).getDataUltimaRicarica().after(dataUltimaRicarica)) {
			simAttiva = true;
		}
		return simAttiva;
	}

	/**
	 * 
	 * @param num
	 * @throws IOException RETURN File avente le informazioni riguardanti una
	 *                     determinata sim.
	 */
	public static void saveDataInFile(String num) throws IOException {

		String fileContent = "";
		FileWriter fileWriter = new FileWriter("C:/Users/Loris/git/OperatoreTelefonico/info_" + num + ".txt");
		PrintWriter printWriter = new PrintWriter(fileWriter);
		printWriter.print(fileContent);
		printWriter.printf(searchSimWithNumber(num).toString());
		printWriter.println("Stato Sim : " + isAttiva(num));
		printWriter.close();
	}

	/**
	 * 
	 * @param num
	 * @return oggetto di tipo SIM avente il numero dato in input
	 */
	private static SIM searchSimWithNumber(String num) {
		SIM finded = null;
		for (int i = 0; i < listOfSim.size(); i++) {
			if (listOfSim.get(i).getNumeroDiTelefono().equals(num))
				finded = listOfSim.get(i);
		}
		return finded;
	}

	public static void main(String[] args) throws IOException {
		System.out.println("Sistema avviato");
		creaCliente("Loris", "Parata");
		creaSim("Loris", "Parata", "3486785337", "1234");
		searchSimWithNumber("3486785337").ricarica(15);
		searchSimWithNumber("3486785337").insertChiamate(new Chiamata("3409538475", null, false));
		// ChiamateEffettuateAdUnNumero("3486785337");
		portabilizzaSim();
		// System.out.println(searchSimWithNumber("3486785337").chiamate.toString());
		// searchSimWithNumber("3486785337").attivaPromozione();
		// searchSimWithNumber("3486785337").disattivaPromozione();
		// System.out.println(Arrays.toString(searchSimWithNumber("3486785337").promozioniAttive));
		saveDataInFile("3486785337");
		System.out.println(listOfSim.toString());

	}
}// end class OperatoreTelefonico
