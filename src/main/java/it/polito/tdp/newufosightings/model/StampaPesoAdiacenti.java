package it.polito.tdp.newufosightings.model;

public class StampaPesoAdiacenti implements Comparable<StampaPesoAdiacenti>{

	private State s;
	private double peso;
	/**
	 * @param s
	 * @param peso
	 */
	public StampaPesoAdiacenti(State s, double peso) {
		super();
		this.s = s;
		this.peso = peso;
	}
	@Override
	public String toString() {
		return s.getName() + " peso=" + peso ;
	}
	@Override
	public int compareTo(StampaPesoAdiacenti o) {
		// TODO Auto-generated method stub
		return this.s.getName().compareTo(o.s.getName());
	}
	
	
}
