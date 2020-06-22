package it.polito.tdp.newufosightings.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import it.polito.tdp.newufosightings.db.NewUfoSightingsDAO;
import it.polito.tdp.newufosightings.model.Event.EventType;

public class Simulator {
	
	//coda
	private PriorityQueue<Event> queue;
	
	//output

	//stato
	private int t1;
	private int t2;
	private NewUfoSightingsDAO dao;
	private Map<String,State> states;
	private Map<Integer,Sighting> idMap;
	private List<Sighting> avvistamenti;
	private List<State> allertaMassima;
	
	
	public void init(int t1,int t2,int xG,int anno) {
		this.t1 = t1;
		this.t2 = t2;
		this.allertaMassima = new ArrayList<>();
		this.idMap = new HashMap<>();
		dao = new NewUfoSightingsDAO();
		this.states = new HashMap<>();
		this.dao.loadAllStates(states);
		this.dao.loadAllSightings(idMap);
		this.avvistamenti = this.dao.AvvistamentiFiltrati(xG,anno,idMap);
		this.queue = new PriorityQueue<>();
		
		for(Sighting s : avvistamenti) {
			Event e = new Event(EventType.ALLERTA, s.getDatetime(),states.get(s.getState().toUpperCase()));
			this.queue.add(e);
		}
			
	}
	
	public void run() {
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			processEvent(e);
		}
	}

	private void processEvent(Event e) {
		State s = e.getState();
		switch(e.getType()) {		
		case ALLERTA:
			if(s.getDefcon() == 1) {
				this.queue.add(new Event(EventType.ALLERTA_MASSIMA,e.getTime().plusDays(t2),s));
			}
			else {
				s.decrementaDefcon();
				this.queue.add(new Event(EventType.ALLERTA_CESSATA,e.getTime().plusDays(t1),s));
			}
			break;
		case ALLERTA_CESSATA:
			if(s.getDefcon()<5) {
				s.incrementaDefon();
			}
			break;
		case ALLERTA_MASSIMA:
			s.incrementaAllerta();
			if(!this.allertaMassima.contains(s)) {
				this.allertaMassima.add(s);
			}
			break;
		}
		
	}

	public List<State> getState() {
		// TODO Auto-generated method stub
		return this.allertaMassima;
	}
}
