package it.polito.tdp.newufosightings.model;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ThreadLocalRandom;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.newufosightings.db.NewUfoSightingsDAO;
import it.polito.tdp.newufosightings.model.Event.EventType;

public class Simulator {
	
	//coda degli eventi
	private PriorityQueue<Event> queue;
	private NewUfoSightingsDAO dao;
	
	//stato del mondo
	private int T1;
	private double alfa;
	private Graph<State,DefaultWeightedEdge> graph;
	private List<Sighting> eventi;
	private Map<String,State> idMap;
	
	public void init(int anno, String forma,
			Graph<State,DefaultWeightedEdge> graph, int t1,int alfa,
			Map<String,State> idMap) {
		this.queue = new PriorityQueue<>();
		this.dao = new NewUfoSightingsDAO();
		this.idMap = idMap;
		this.eventi = this.dao.loadAllSightings(anno,forma);
		this.graph = graph;
		this.T1=t1;
		this.alfa= alfa;
		
		for(Sighting s : this.eventi) {
			Event e = new Event(s.getDatetime(),s,EventType.ALLERTA);
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
		State s = idMap.get(e.getEvento().getState().toUpperCase());
		switch(e.getTipo()) {
		case ALLERTA:
			s.decrementaDefcon(1);
			double prob = Math.random()*100;
			if(prob<alfa) {
				for(State st : Graphs.neighborListOf(this.graph, s)) {
					st.decrementaDefcon(0.5);;
				}
			}
			this.queue.add(new Event(e.getTime().plusDays(this.T1),e.getEvento(),EventType.ALLERTA_CESSATA));
			break;
		case ALLERTA_CESSATA:
			s.incrementaDefcon(1);
			double probl = Math.random()*100;
			if(probl<alfa) {
				for(State st : Graphs.neighborListOf(this.graph, s)) {
					st.incrementaDefcon(0.5);
				}
			}
			break;
		}
		
		
	}
	

}
