
public class Chiamata {
	private String numero;
	private int minutiChiamata;
	
	
	public Chiamata(String numero, int minutiChiamata) {
		this.numero = numero;
		this.minutiChiamata = minutiChiamata;
	}
	
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public int getTempo() {
		return minutiChiamata;
	}
	public void setTempo(int tempo) {
		this.minutiChiamata = minutiChiamata;
	}

}
