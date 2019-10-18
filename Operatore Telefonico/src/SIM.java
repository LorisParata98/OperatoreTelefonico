import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class SIM {

	private String numeroDiTelefono, PUK, proprietario;
	private float creditoDisponibile;
	private Date dataUltimaRicarica;
	private String[] promozioniAttive;
	public Chiamata[] chiamateEffettuate;
	private boolean portabilizzata;
	Scanner scan = new Scanner(System.in);

	public SIM(String numeroDiTelefono, String PUK, String proprietario, float creditoDisponibile,
			Date dataUltimaRicarica) {

		this.numeroDiTelefono = numeroDiTelefono;
		this.PUK = PUK;
		this.proprietario = proprietario;
		this.creditoDisponibile = creditoDisponibile;
		this.dataUltimaRicarica = dataUltimaRicarica;
		this.promozioniAttive = new String[3];
		this.chiamateEffettuate = new Chiamata[15];
		this.setPortabilizzata(false);
	}

	public SIM() {
	}

	public void insertChiamate(String numero, int durataInMinuti) {

		for (int i = 0; i < chiamateEffettuate.length; i++) {
			Chiamata chiamata = new Chiamata(numero, durataInMinuti);
			if (chiamateEffettuate[i] == null) {
				chiamateEffettuate[i] = chiamata;
				break;
			}

		}

	}

	public void minutiTotaliChiamate() {
		int minutiTotali = 0;
		for (int i = 0; i < chiamateEffettuate.length; i++) {
			if (chiamateEffettuate[i] != null) {
				minutiTotali += chiamateEffettuate[i].getTempo();
				System.out.println(chiamateEffettuate[i].getTempo());
			}
		}
		System.out.println("Minuti totali chiamate : " + minutiTotali);
	}

	public void toStringChiamate() {
		for (int i = 0; i < chiamateEffettuate.length; i++) {
			if (chiamateEffettuate[i]!=null) {
				System.out.println(i+".Chiamata effettuata: " + chiamateEffettuate[i].getNumero() + " tempo chiamata:"+chiamateEffettuate[i].getTempo());
			}
			
		}
	}

	public String getNumeroDiTelefono() {
		return numeroDiTelefono;
	}

	public void setNumeroDiTelefono(String numeroDiTelefono) {
		this.numeroDiTelefono = numeroDiTelefono;
	}

	public String getPUK() {
		return PUK;
	}

	public void setPUK(String pUK) {
		PUK = pUK;
	}

	public String getProprietario() {
		return proprietario;
	}

	public void setProprietario(String proprietario) {
		this.proprietario = proprietario;
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

	public void getPromozioniAttive() {
		String promozioni = "";
		for (int i = 0; i < promozioniAttive.length; i++) {
			if (promozioniAttive[i] != null) {
				promozioni += promozioniAttive[i] + ",";
			}
		}
		System.out.println("Promozioni attive:"+promozioni);
	}

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

	public void attivazionePromozioneEffettiva(String promo, int costo) {
		if (creditoDisponibile - costo > 0) {
			creditoDisponibile = creditoDisponibile - costo;
			for (int i = 0; i < promozioniAttive.length; i++) {
				if (promozioniAttive[i] == null) {
					promozioniAttive[i] = promo;
					break;

				}
			}
		} else  System.out.println("Credito Insufficiente");
		
		System.out.println("Promozione attivata!");
		
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
			if (promozioniAttive[i] == promo) {
				promozioniAttive[i] = null;
			}
		}
		System.out.println("Promozione disattivata");

	}

	public void setChiamateEffettuate(Chiamata[] chiamateEffettuate) {
		this.chiamateEffettuate = chiamateEffettuate;
	}

	public boolean getPortabilizzata() {
		return portabilizzata;
	}

	public void setPortabilizzata(boolean portabilizzata) {
		this.portabilizzata = portabilizzata;
	}

	@Override
	public String toString() {
		return "SIM : numeroDiTelefono=" + numeroDiTelefono + "\n" + "proprietario=" + proprietario + "\n"
				+ " creditoDisponibile=" + creditoDisponibile + "\n" + " dataUltimaRicarica=" + dataUltimaRicarica
				+ "\n" + " portabilizzata=" + portabilizzata + "\n";
	}

}
