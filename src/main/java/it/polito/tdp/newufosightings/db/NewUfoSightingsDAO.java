package it.polito.tdp.newufosightings.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import it.polito.tdp.newufosightings.model.Adiacenza;
import it.polito.tdp.newufosightings.model.Sighting;
import it.polito.tdp.newufosightings.model.State;

public class NewUfoSightingsDAO {

	public void loadAllSightings(Map<Integer, Sighting> idMap) {
		String sql = "SELECT * FROM sighting";
		
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);	
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Sighting s = new Sighting(res.getInt("id"), res.getTimestamp("datetime").toLocalDateTime(),
						res.getString("city"), res.getString("state"), res.getString("country"), res.getString("shape"),
						res.getInt("duration"), res.getString("duration_hm"), res.getString("comments"),
						res.getDate("date_posted").toLocalDate(), res.getDouble("latitude"),
						res.getDouble("longitude"));
				idMap.put(s.getId(), s);
			}

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}

		
	}

	public void loadAllStates(Map<String, State> idMap) {
		String sql = "SELECT * FROM state";
		

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				State state = new State(rs.getString("id"), rs.getString("Name"), rs.getString("Capital"),
						rs.getDouble("Lat"), rs.getDouble("Lng"), rs.getInt("Area"), rs.getInt("Population"),
						rs.getString("Neighbors"));
				idMap.put(state.getId(),state);
			}

			conn.close();
			

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Adiacenza> getAdiacenze(Integer anno, Integer xG, Map<String, State> idMap) {
		final String sql = "SELECT s1.state AS st1,s2.state AS st2, COUNT(s1.id) AS peso " + 
				"FROM neighbor AS n,sighting AS s1,sighting AS s2 " + 
				"WHERE s1.state=n.state1 " + 
				"AND s2.state=n.state2 " + 
				"AND s1.state<>s2.state " + 
				"AND YEAR(s1.datetime)=? " + 
				"AND YEAR(s2.datetime)=? " + 
				"AND DATEDIFF(s1.datetime,s2.datetime)<? " + 
				"GROUP BY s1.state,s2.state";
		List<Adiacenza> lista = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			st.setInt(2, anno);
			st.setInt(3, xG);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Adiacenza a = new Adiacenza(idMap.get(rs.getString("St1").toUpperCase()),idMap.get(rs.getString("st2").toUpperCase()),rs.getInt("peso"));
				lista.add(a);
			}

			conn.close();
			return lista;
			

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	
	}

	public List<Sighting> AvvistamentiFiltrati(int xG, int anno, Map<Integer, Sighting> idMap) {
		final String sql = "SELECT s1.id AS id1,s2.id AS id2 " + 
				"FROM neighbor AS n,sighting AS s1,sighting AS s2 " + 
				"WHERE s1.state=n.state1 " + 
				"AND s2.state=n.state2 " + 
				"AND s1.state<>s2.state " + 
				"AND YEAR(s1.datetime)=? " + 
				"AND YEAR(s2.datetime)=? " + 
				"AND DATEDIFF(s1.datetime,s2.datetime)<? " + 
				"GROUP BY s1.state,s2.state";
		List<Sighting> lista = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			st.setInt(2, anno);
			st.setInt(3, xG);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				lista.add(idMap.get(rs.getInt("id1")));
				lista.add(idMap.get(rs.getInt("id2")));
			}

			conn.close();
			return lista;
			

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	
		
	}

	

}

