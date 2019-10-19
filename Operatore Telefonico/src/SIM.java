import java.util.*;
import java.time.*;

public class SIM {
	private String numeroDiTelefono, puk;
	private float creditoDisponibile;
	private Date dataUltimaRicarica;
	private Person proprietario;
	private String[] promozioniAttive;
	public List<Chiamata> chiamate;
	private boolean portabilizzata;
	Scanner scan = new Scanner(System.in);

	/**
	 * Crea una nuova SIM
	 * 
	 * @param proprietario
	 * @param numeroDiTelefono
	 * @param puk
	 */
	public SIM(Person proprietario, String numeroDiTelefono, String puk) {
		this.proprietario = proprietario;
		this.numeroDiTelefono = numeroDiTelefono;
		this.setPuk(puk);
	}

	/**
	 * Crea una nuova SIM portabilizzata
	 * 
	 * @param number
	 * @param old_Sim
	 */
	public SIM(String number, SIM old_Sim) {
		this.proprietario = old_Sim.proprietario;
		this.numeroDiTelefono = old_Sim.numeroDiTelefono;
		this.creditoDisponibile = old_Sim.creditoDisponibile;
		this.dataUltimaRicarica = old_Sim.dataUltimaRicarica;
		this.promozioniAttive = old_Sim.promozioniAttive;
		this.chiamate = old_Sim.chiamate;
		this.portabilizzata = true;
	}

	public SIM() {
	};

	/**
	 * Aggiunge una chiamata alla lista
	 * 
	 * @param chiamata
	 */
	public void insertChiamate(Chiamata chiamata) {
		chiamate.add(chiamata);
	}

	/**
	 * Calcola il totale di tempo delle chiamate effettuate e ricevute
	 * 
	 * @return
	 */
	public Duration minutiTotaliChiamate() {
		Duration minutiTotali = Duration.ZERO;
		for (int i = 0; i < chiamate.size(); i++) {
			minutiTotali = minutiTotali.plus(chiamate.get(i).getDuration());
		}
		return minutiTotali;
	}

	public float ricarica(float importo) {
		creditoDisponibile += importo;
		return creditoDisponibile;
	}

	public void toStringChiamate() {
		for (int i = 0; i < chiamate.size(); i++) {
			System.out.println(i + ".Chiamata effettuata: " + chiamate.get(i).getNumero() + " tempo chiamata:"
					+ chiamate.get(i).getDuration());

		}
	}

	/**
	 * 
	 * @return RETURN the phone number
	 */
	public String getNumeroDiTelefono() {
		return numeroDiTelefono;
	}

	/**
	 * SET actual phone number to the new one
	 * 
	 * @param numeroDiTelefono the phone number REQUIRE not null String,no
	 *                         characters except "+" "-" " " and
	 */
	public void setNumeroDiTelefono(String numeroDiTelefono) {
		this.numeroDiTelefono = numeroDiTelefono;
	}

	public String getPUK() {
		return puk;
	}

	public void setPUK(String puk) {
		this.puk = puk;
	}

	public float getCreditoDisponibile() {
		return creditoDisponibile;
	}

	public void setCreditoDisponibile(float creditoDisponibile) {
		this.creditoDisponibile = creditoDisponibile;
	}

	public Date getDataUltimaRicarica() {
		return dataUltimaRicarica;
	}

	public void setDataUltimaRicarica(Date dataUltimaRicarica) {
		this.dataUltimaRicarica = dataUltimaRicarica;
	}
	

	public void setChiamateEffettuate(List<Chiamata> chiamateEffettuate) {
		this.chiamate = chiamateEffettuate;
	}

	public boolean getPortabilizzata() {
		return portabilizzata;
	}

	public void setPortabilizzata(boolean portabilizzata) {
		this.portabilizzata = portabilizzata;
	}
	public String getPuk() {
		return puk;
	}

	public void setPuk(String puk) {
		this.puk = puk;
	}

	/**
	 * Visualizza le promozioni attive relative alla SIM
	 */
	public void getPromozioniAttive() {
		String promozioni = "";
		for (int i = 0; i < promozioniAttive.length; i++) {
			if (promozioniAttive[i] != null) {
				promozioni += promozioniAttive[i] + ",";
			}
		}
		System.out.println("Promozioni attive:" + promozioni);
	}

	/**
	 * Attiva la promozione desiderata
	 */
	public void attivaPromozione() {
		String promo;
		int costo;
		System.out.println("Quale promozione desideri attivare?" + "\n" + "1. Minuti Illimiatati" + "\n"
				+ "2. Chiama e richiama" + "\n" + "3. Dati Unlimited" + "\n");
		int keyPromozione = scan.nextInt();
		switch (keyPromozione) {
		case 1:
			promo = "Minuti Illimitati";
			costo = 10;
			attivazionePromozioneEffettiva(promo, costo);
			break;
		case 2:
			promo = "Chiama e richiama";
			costo = 15;
			attivazionePromozioneEffettiva(promo, costo);
			break;
		case 3:
			promo = "Dati Unlimited";
			costo = 0;
			attivazionePromozioneEffettiva(promo, costo);
			break;

		default:
			System.out.println("Valore inserito non valido");
			break;
		}

	}

	/**
	 * Attiva la promozione desiderata e sottrae il credito necessario all
	 * attivazione
	 * 
	 * @param promo
	 * @param costo
	 */
	public void attivazionePromozioneEffettiva(String promo, int costo) {
		if (controlloPromozione(promo) == false) {
			if (creditoDisponibile - costo > 0) {
				creditoDisponibile = creditoDisponibile - costo;
				for (int i = 0; i < promozioniAttive.length; i++) {
					if (promozioniAttive[i] == null) {
						promozioniAttive[i] = promo;
						System.out.println("Promozione attivata!");
						break;

					}
				}
			} else
				System.out.println("Credito Insufficiente");
		}

	}

	/**
	 * 
	 * @param promo
	 * @return false se la promozione è già attiva 
	 */
	private boolean controlloPromozione(String promo) {
		boolean giaAttiva = false;
		for (int i = 0; i < promozioniAttive.length; i++) {
			if (promozioniAttive[i].equals(promo))
				giaAttiva = true;
			System.out.println("Promozione già attiva");
		}
		return giaAttiva;
	}

	public void disattivaPromozione() {
		String promo;
		System.out.println("Quale promozione desideri disattivare?" + "\n" + "1. Minuti Illimiatati" + "\n"
				+ "2. Chiama e richiama" + "\n" + "3. Dati Unlimited" + "\n");
		int keyPromozione = scan.nextInt();
		switch (keyPromozione) {
		case 1:
			promo = "Minuti Illimitati";
			disattivazionePromozione(promo);
			break;
		case 2:
			promo = "Chiama e richiama";
			disattivazionePromozione(promo);
			break;
		case 3:
			promo = "Dati Unlimited";
			disattivazionePromozione(promo);
			break;
		default:
			System.out.println("Valore inserito non valido");
			break;
		}

	}

	public void disattivazionePromozione(String promo) {

		for (int i = 0; i < promozioniAttive.length; i++) {
			if (promozioniAttive[i].equals(promo)) {
				promozioniAttive[i] = null;
			}
		}
		System.out.println("Promozione disattivata");

	}


	public String toString() {
		return "SIM : numeroDiTelefono=" + numeroDiTelefono + "\n" + "nome=" + proprietario.getNome() + "\n"
				+ "cognome=" + proprietario.getCognome() + "\n" + " creditoDisponibile=" + creditoDisponibile + "\n"
				+ " dataUltimaRicarica=" + dataUltimaRicarica + "\n" + " portabilizzata=" + portabilizzata + "\n";
	}

	

}
