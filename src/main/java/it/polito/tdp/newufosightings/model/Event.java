package it.polito.tdp.newufosightings.model;

import java.time.LocalDateTime;

public class Event implements Comparable<Event>{
	
	public enum EventType {
		ALLERTA,
		ALLERTA_CESSATA,
	}
	private LocalDateTime time;
	private Sighting evento;
	private EventType tipo;
	
	/**
	 * @param time
	 * @param evento
	 */
	public Event(LocalDateTime time, Sighting evento,EventType tipo) {
		super();
		this.time = time;
		this.evento = evento;
		this.tipo = tipo;
	}
	/**
	 * @return the tipo
	 */
	public EventType getTipo() {
		return tipo;
	}
	/**
	 * @return the time
	 */
	public LocalDateTime getTime() {
		return time;
	}
	/**
	 * @return the evento
	 */
	public Sighting getEvento() {
		return evento;
	}
	@Override
	public int compareTo(Event o) {
		// TODO Auto-generated method stub
		return this.time.compareTo(o.getTime());
	}
	
	

}
