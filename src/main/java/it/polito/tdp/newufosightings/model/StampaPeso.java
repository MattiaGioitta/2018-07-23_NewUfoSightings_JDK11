package it.polito.tdp.newufosightings.model;

public class StampaPeso {

	private State s;
	private Double peso;
	/**
	 * @param s
	 * @param peso
	 */
	public StampaPeso(State s, Double peso) {
		super();
		this.s = s;
		this.peso = peso;
	}
	/**
	 * @return the s
	 */
	public State getS() {
		return s;
	}
	/**
	 * @param s the s to set
	 */
	public void setS(State s) {
		this.s = s;
	}
	/**
	 * @return the peso
	 */
	public Double getPeso() {
		return peso;
	}
	/**
	 * @param peso the peso to set
	 */
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	@Override
	public String toString() {
		return  s.getName() + " peso=" + peso ;
	}
	
	
}
