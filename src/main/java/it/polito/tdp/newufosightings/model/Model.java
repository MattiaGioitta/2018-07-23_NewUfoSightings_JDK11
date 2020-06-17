package it.polito.tdp.newufosightings.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.newufosightings.db.NewUfoSightingsDAO;

public class Model {
	
	private NewUfoSightingsDAO dao;
	private Graph<State,DefaultWeightedEdge> graph;
	private Map<String,State> idMap;
	private Simulator sim;
	
	public Model() {
		this.dao = new NewUfoSightingsDAO();
		this.idMap = new HashMap<>();
		this.sim = new Simulator();
	}

	public List<String> getForme(Integer anno) {
		return this.dao.getForme(anno);
	}

	public void createGraph(String forma, Integer anno) {
		this.graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.dao.loadAllStates(this.idMap);
		Graphs.addAllVertices(this.graph, this.idMap.values());
		List<Adiacenza> adiacenze = this.dao.getAdiacenze(anno,forma,this.idMap);
		
		for(Adiacenza a : adiacenze) {
			if(this.graph.containsVertex(a.getS1()) && this.graph.containsVertex(a.getS2())) {
				if(this.graph.getEdge(a.getS1(), a.getS2()) == null) {
					Graphs.addEdgeWithVertices(this.graph, a.getS1(), a.getS2(),a.getPeso());
				}
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

	public List<StampaPeso> getStampe() {
		List<StampaPeso> lista = new ArrayList<>();
		for(State s : this.graph.vertexSet()) {
			double peso = 0.0;
			for(State st: Graphs.neighborListOf(this.graph, s)) {
				peso+=this.graph.getEdgeWeight(this.graph.getEdge(s, st));
			}
			lista.add(new StampaPeso(s,peso));
		}
		return lista;
	}

	public void simula(Integer alfa, Integer t, Integer anno, String forma) {
		this.sim.init(anno, forma, graph, t, alfa, idMap);
		this.sim.run();
		
	}
	public List<State> stati(){
		List<State> l = new ArrayList<>();
		for(State s : this.graph.vertexSet()) {
			l.add(s);
		}
		return l;
	}

	public void reset() {
		this.sim = new Simulator();
		
	}

}
