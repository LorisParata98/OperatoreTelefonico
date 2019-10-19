import java.time.Duration;

class Chiamata {
	private String numero;
	private Duration duration;
	private Boolean isReceived;
	
	Chiamata(String numero, Duration duration, Boolean isReceived){
		this.duration = duration;
		this.numero = numero;
		this.isReceived = isReceived;
	}
	
	/**
	 * Returns the phone number of this data
	 * @return the phone number
	 */
	public String getNumero() {
		return numero;
	}
	
	/**
	 * Set actual phone number to the new one
	 * @param number the new phone number REQUIRE not null String, no characters except '+' '-' ' ' and numbers
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	
	public Duration getDuration() {
		return duration;
	}
	
	public void setDuration(Duration duration) {
		this.duration = duration;
	}
	
	public Boolean isReceived() {
		return isReceived;
	}
	
}
