package it.polito.tdp.newufosightings.model;

public class Adiacenza {
	private State s1;
	private State s2;
	private int peso;
	/**
	 * @param s1
	 * @param s2
	 * @param peso
	 */
	public Adiacenza(State s1, State s2, int peso) {
		super();
		this.s1 = s1;
		this.s2 = s2;
		this.peso = peso;
	}
	/**
	 * @return the s1
	 */
	public State getS1() {
		return s1;
	}
	/**
	 * @param s1 the s1 to set
	 */
	public void setS1(State s1) {
		this.s1 = s1;
	}
	/**
	 * @return the s2
	 */
	public State getS2() {
		return s2;
	}
	/**
	 * @param s2 the s2 to set
	 */
	public void setS2(State s2) {
		this.s2 = s2;
	}
	/**
	 * @return the peso
	 */
	public int getPeso() {
		return peso;
	}
	/**
	 * @param peso the peso to set
	 */
	public void setPeso(int peso) {
		this.peso = peso;
	}
	
	

}
