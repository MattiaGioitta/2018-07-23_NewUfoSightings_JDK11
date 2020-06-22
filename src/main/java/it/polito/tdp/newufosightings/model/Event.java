package it.polito.tdp.newufosightings.model;

import java.time.LocalDateTime;

public class Event implements Comparable<Event>{
	
	public enum EventType{
		ALLERTA,
		ALLERTA_CESSATA,
		ALLERTA_MASSIMA,
	}
	
	private EventType type;
	private LocalDateTime time;
	private State state;
	/**
	 * @param type
	 * @param time
	 * @param state
	 */
	public Event(EventType type, LocalDateTime time, State state) {
		super();
		this.type = type;
		this.time = time;
		this.state = state;
	}
	/**
	 * @return the type
	 */
	public EventType getType() {
		return type;
	}
	/**
	 * @return the time
	 */
	public LocalDateTime getTime() {
		return time;
	}
	/**
	 * @return the state
	 */
	public State getState() {
		return state;
	}
	@Override
	public int compareTo(Event o) {
		// TODO Auto-generated method stub
		return this.time.compareTo(o.time);
	}
	
	

}
