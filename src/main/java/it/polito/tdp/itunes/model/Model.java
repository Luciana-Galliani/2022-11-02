package it.polito.tdp.itunes.model;

import it.polito.tdp.itunes.db.ItunesDAO;
import java.util.*;

import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

public class Model {
	//parte 1
	private ItunesDAO dao;
	private SimpleGraph<Track, DefaultEdge> grafo;
	
	//parte 2
	private List<Track> bestPath;
	private int bestScore;
	Set<Track> compConnessMagg;
	
	public Model() {
		dao = new ItunesDAO();
	}
	
	public List<String> generi(){
		return this.dao.getGenres();
	}
	
	public void creaGrafo(String genere, int min, int max) {
		grafo = new SimpleGraph< Track, DefaultEdge>(DefaultEdge.class);
		
		Graphs.addAllVertices(grafo, this.dao.getTracksConCondizioni(genere, min, max));
		
		for(Track t: this.grafo.vertexSet())
			this.dao.countPlaylist(t);
		
		for(Track t : this.grafo.vertexSet()) {
			for(Track t2: this.grafo.vertexSet()) {
				if(!t.equals(t2) && t.getnPlaylist() == t2.getnPlaylist()) {
					Graphs.addEdgeWithVertices(grafo, t, t2);
				}
			}
		}
		
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public String getConnectedComponents(){
		String result ="";
		int n=0;
		
		ConnectivityInspector<Track, DefaultEdge> insp = new ConnectivityInspector<>(this.grafo);
		
		List<Set<Track>> componenti = insp.connectedSets();
		
		for(Set<Track> t: componenti) {
			result += "Componente con "+ t.size() +" vertici inseriti in ";
			if(t.size() > this.compConnessMagg.size())
				this.compConnessMagg = new HashSet<>(t);
			for(Track tr : t) {
				n = tr.getnPlaylist();
			}
			result += ""+ n +" playlist\n";
			
		}
		
		return result;
	}
	
	
	//ricorsione
	public List<Track> getPath(int d){
		bestPath = new LinkedList<>();
		bestScore = 0;
		
		LinkedList<Track> parziale = new LinkedList<>();
		
	//	cerca(parziale, d);
		
		return bestPath;
		
	}
	
	
//	public void cerca(LinkedList<Track> parziale, int x) {
		
//		//condizione di terminazione
//		if(parziale.getLast().equals(a2)) {
//			
//			//controllo se questa soluzione è migliore del best 
//			if ( getScore(parziale) > bestScore) {
//				bestScore = getScore(parziale);
//				this.bestPath = new LinkedList<>(parziale);
//			}
//			return;
//		}
//
//		//continuo ad aggiungere elementi in parziale
//		List<Album> successors = Graphs.successorListOf(this.grafo, parziale.getLast());
//		
//		for (Album a : successors) {
//			if( this.grafo.getEdgeWeight(this.grafo.getEdge(parziale.getLast(), a)) >= x && !parziale.contains(a)) {
//				parziale.add(a);
//				cerca(a2, parziale, x);
//				parziale.remove(a); //backtracking
//			}
//		}
//	}
}
