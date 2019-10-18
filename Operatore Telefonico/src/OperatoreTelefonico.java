import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class OperatoreTelefonico {
	public static Scanner scan = new Scanner(System.in);

	private static SIM[] sims = new SIM[10];

	public static SIM creaSim() {
		SIM sim_nuova = new SIM();
		for (int i = 0; i < sims.length; i++) {
			if (sims[i] == null) {

				System.out.println("Inserisci informazioni");
				System.out.println("Numero telefonico: ");
				String numero = scan.next();
				System.out.println("Codice puk :");
				String puk = scan.next();
				System.out.println("Proprietario: ");
				String proprietario = scan.next();
				scan.next();
				System.out.println("Credito iniziale: ");
				int credito = scan.nextInt();
				Date dataPrimaRicarica = new Date();

				sim_nuova = new SIM(numero, puk, proprietario, credito, dataPrimaRicarica);
				sims[i] = sim_nuova;
				break;
			}

		}
		return sim_nuova;
	}

	public static void portabilizzaSim() {
		System.out.println("Inserisci numero della sim di provenienza");
		String numeroDiProvenienza = scan.next();
		for (int i = 0; i < sims.length; i++) {
			if ((sims[i] != null) && (sims[i].getNumeroDiTelefono().equals(numeroDiProvenienza))) {

				SIM sim_daPortabilizzare = new SIM(numeroDiProvenienza, sims[i].getPUK(), sims[i].getProprietario(),
						sims[i].getCreditoDisponibile(), sims[i].getDataUltimaRicarica());

				for (int j = 0; j < sims.length; j++) {

					if (sims[j] == null) {
						sims[j] = sim_daPortabilizzare;
						break;

					}

				}
				copiaListaChiamate(sims[i], sim_daPortabilizzare);
				System.out.println("Copia effettuata");
				sim_daPortabilizzare.setPortabilizzata(true);
				System.out.println("Sim portabilizzata!");
				break;
			}

		}
		System.err.println("Numero  inesistente!");
	}

	public static void copiaListaChiamate(SIM sim_diProvenienza, SIM sim_daPortabilizzare) {

		System.out.println(sim_diProvenienza.chiamateEffettuate[0]);
		{
			for (int j = 0; j < sim_diProvenienza.chiamateEffettuate.length; j++) {
				if (sim_diProvenienza.chiamateEffettuate != null) {
					if (sim_daPortabilizzare == null) {
						sim_daPortabilizzare.insertChiamate(sim_diProvenienza.chiamateEffettuate[j].getNumero(),
								sim_diProvenienza.chiamateEffettuate[j].getTempo());
					}
				}
				System.out.println("Nessuna chiamata da copiare!");
			}
		}

	}

	public void deleteSim() {
		String numeroSimDaEliminare = scan.next();
		for (int i = 0; i < sims.length; i++) {
			if (sims[i].getNumeroDiTelefono() == numeroSimDaEliminare)
				sims[i] = null;
		}
		System.out.println("Sim non presente!");
	}

	// effettua una ricerca nella lista delle chiamate di una sim
	// per individuare le chiamate riguardo un numero inserito da tastiera
	public void ricercaChiamate() {
		String numeroSimDaTrovare = scan.next();
		SIM simCercata = searchSimWithNumber(numeroSimDaTrovare);
		if (simCercata.getNumeroDiTelefono() == numeroSimDaTrovare) {
			for (int i = 0; i < simCercata.chiamateEffettuate.length; i++) {
				System.out.println(simCercata.chiamateEffettuate[i].getNumero());
				System.out.println("Durata: " + simCercata.chiamateEffettuate[i].getTempo());
			}
		}
	}

	public boolean isPortabilizzata(String num) {
		for (int i = 0; i < sims.length; i++) {
			if (sims[i].getNumeroDiTelefono().equals(num)) {
				return sims[i].getPortabilizzata();
			}
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	// controlla se la scheda è ancora attiva
	public static boolean isAttiva(String numeroDaControllare) {
		boolean simAttiva = false;
		Date dataUltimaRicarica = new Date(); // stampa data attuale
		dataUltimaRicarica.setYear(dataUltimaRicarica.getYear() - 1); // sottrae 1 anno dalla data attuale
		if (searchSimWithNumber(numeroDaControllare).getDataUltimaRicarica().after(dataUltimaRicarica)) {
			simAttiva = true;
		}
		return simAttiva;
	}

	public static void usingPrintWriter(String num) throws IOException {
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

	private static SIM searchSimWithNumber(String num) {
		SIM finded = new SIM();
		for (int i = 0; i < sims.length; i++) {
			if ((sims[i] != null) && (sims[i].getNumeroDiTelefono().equals(num)))
				finded = sims[i];
		}
		System.out.println("Sim Trovata!");
		return finded;
	}

	public static void main(String[] args) throws IOException {

		// SIM sim1 = creaSim();

		SIM sim2 = new SIM("3486785337", "1234", "Loris Parata", 20, new Date());
		sims[0] = sim2;
		SIM sim3 = new SIM("348455337", "1234", "Loris P4a", 20, new Date());
		sims[1] = sim3;
		SIM sim4 = new SIM("1234", "1234", "Ls Parata", 20, new Date());
		sims[2] = sim4;

		sim2.insertChiamate("3486785337", 13);
		sim2.insertChiamate("3486785337", 12);
		sim2.minutiTotaliChiamate();
		sim2.toStringChiamate();
		/*
		 * sim2.attivaPromozione(); sim2.attivaPromozione(); sim2.getPromozioniAttive();
		 * sim2.disattivaPromozione(); sim2.getPromozioniAttive();
		 */
		// System.out.println(isAttiva("3486785337"));
		portabilizzaSim();
		System.out.println(Arrays.deepToString(sims));
		

		// System.out.println(searchSimWithNumber("3486785337"));

		// usingPrintWriter("3486785337");

	}
}// end class OperatoreTelefonico
