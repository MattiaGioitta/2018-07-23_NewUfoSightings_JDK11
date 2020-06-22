package it.polito.tdp.newufosightings.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.newufosightings.db.NewUfoSightingsDAO;

public class Model {
	
	private Graph<State,DefaultWeightedEdge> graph;
	private NewUfoSightingsDAO dao;
	private Map<String,State> idMap;
	private Simulator sim;
	
	public Model() {
		this.dao = new NewUfoSightingsDAO();
		this.idMap = new HashMap<>();
	}

	public void createGraph(Integer xG, Integer anno) {
		this.graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.dao.loadAllStates(idMap);
		Graphs.addAllVertices(this.graph, idMap.values());
		List<Adiacenza> adiacenze = this.dao.getAdiacenze(anno,xG,idMap);
		for(Adiacenza a : adiacenze) {
				if(this.graph.getEdge(a.getS1(), a.getS2())==null) {
					Graphs.addEdgeWithVertices(this.graph, a.getS1(), a.getS2(),a.getPeso());
				}
			}
		}
		
	

	public Integer nVertici() {
		// TODO Auto-generated method stub
		return this.graph.vertexSet().size();
	}

	public Integer nArchi() {
		// TODO Auto-generated method stub
		return this.graph.edgeSet().size();
	}

	public List<StampaPesoAdiacenti> getPesoAdiacenti() {
		List<StampaPesoAdiacenti> l = new ArrayList<>();
		for(State s : this.graph.vertexSet()) {
			double peso = 0.0;
			for(State st : Graphs.neighborListOf(this.graph, s)) {
				peso+=this.graph.getEdgeWeight(this.graph.getEdge(s, st));
			}
			l.add(new StampaPesoAdiacenti(s,peso));
		}
		Collections.sort(l);
		return l;
	}

	public void simula(Integer t1, Integer t2,int xG,int anno) {
		this.sim = new Simulator();
		this.sim.init(t1, t2, xG, anno);
		this.sim.run();
	}

	public List<State> getStati() {
		// TODO Auto-generated method stub
		return this.sim.getState();
	}

}
