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

	public static SIM creaSim(String nome, String cognome, String numero, String puk) {
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

		System.out.println(proprietarioSim.toString());
		SIM sim_nuova = new SIM(proprietarioSim, numero, puk);
		System.out.println("SIM CREATA");
		sim_nuova.setNumeroDiTelefono(numero);
		listOfSim.add(sim_nuova);
		
		return sim_nuova;
	}

	private static Person SearchCliente(String nome, String cognome) {
		Person clienteCercato = null;
		// ricerca cliente se è esitente
		for (int i = 0; i <= clienti.size(); i++) {
			if ((clienti.get(i).getNome().equals(nome)) && (clienti.get(i).getCognome().equals(cognome)))
				clienteCercato = clienti.get(i);
			break;
		}

		return clienteCercato;
	}

	public void portabilizzaSim() {
		System.out.println("Inserisci numero della sim di provenienza");
		String numeroDiProvenienza = scan.next();
		for (int i = 0; i < listOfSim.size(); i++) {
			if (listOfSim.get(i).getNumeroDiTelefono().equals(numeroDiProvenienza)) {

				SIM sim_daPortabilizzare = new SIM(listOfSim.get(i).getNumeroDiTelefono(), listOfSim.get(i));

				listOfSim.add(sim_daPortabilizzare);

				copiaListaChiamate(listOfSim.get(i), sim_daPortabilizzare);
				System.out.println("Copia effettuata");
				sim_daPortabilizzare.setPortabilizzata(true);
				System.out.println("Sim portabilizzata!");
				break;
			}

		}
		System.err.println("Numero  inesistente!");
	}

	public static void copiaListaChiamate(SIM sim_diProvenienza, SIM sim_daPortabilizzare) {
		{
			for (int j = 0; j < sim_diProvenienza.chiamate.size(); j++) {
				sim_daPortabilizzare.insertChiamate(sim_diProvenienza.chiamate.get(j));
			}
		}
		System.out.println("Nessuna chiamata da copiare!");
	}

	public void deleteSim() {
		String numeroSimDaEliminare = scan.next();
		SIM simDaEliminare = searchSimWithNumber(numeroSimDaEliminare);
		if (simDaEliminare != null) {
			listOfSim.remove(simDaEliminare);
		}
		System.out.println("Sim non presente!");
	}

	// effettua una ricerca nella lista delle chiamate di una sim
	// per individuare le chiamate riguardo un numero inserito da tastiera
	public void ricercaChiamate() {
		String numeroSimDaTrovare = scan.next();
		SIM simCercata = searchSimWithNumber(numeroSimDaTrovare);
		if (simCercata.getNumeroDiTelefono() == numeroSimDaTrovare) {
			for (int i = 0; i < simCercata.chiamate.size(); i++) {
				System.out.println(simCercata.chiamate.get(i).getNumero());
				System.out.println("Durata: " + simCercata.chiamate.get(i).getDuration());
			}
		}
	}

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
	public boolean isAttiva(String numeroDaControllare) {
		boolean simAttiva = false;
		Date dataUltimaRicarica = new Date(); // stampa data attuale
		dataUltimaRicarica.setYear(dataUltimaRicarica.getYear() - 1);// sottrae 1 anno dalla data attuale
		if (searchSimWithNumber(numeroDaControllare).getDataUltimaRicarica().after(dataUltimaRicarica)) {
			simAttiva = true;
		}
		return simAttiva;
	}

	public void saveDataInFile(String num) throws IOException {

		String fileContent = "";
		FileWriter fileWriter = new FileWriter(
				"C:/Universita/Programmazione Orienta Agli Oggetti/Esercizi/Programmazione_Oggetti/info_" + num
						+ ".txt");
		PrintWriter printWriter = new PrintWriter(fileWriter);
		printWriter.print(fileContent);

		printWriter.printf(searchSimWithNumber(num).toString());
		printWriter.println("Stato Sim : " + isAttiva(num));
		printWriter.close();
	}

	private SIM searchSimWithNumber(String num) {
		SIM finded = null;
		for (int i = 0; i < listOfSim.size(); i++) {
			if (listOfSim.get(i).getNumeroDiTelefono().equals(num))
				finded = listOfSim.get(i);
		}
		System.out.println("Sim Trovata!");
		return finded;
	}

	public static void main(String[] args) throws IOException {
		System.out.println("Sistema avviato");
		creaCliente("Loris", "Parata");
		creaSim("Loris", "Parata", "3486785337", "1234");

	}
}// end class OperatoreTelefonico
