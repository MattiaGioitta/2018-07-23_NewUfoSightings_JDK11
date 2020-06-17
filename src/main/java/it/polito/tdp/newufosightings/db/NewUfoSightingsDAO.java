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

	public List<Sighting> loadAllSightings(int anno, String forma) {
		String sql = "SELECT * " + 
				"FROM sighting AS s " + 
				"WHERE YEAR(s.datetime)=? " + 
				"AND s.shape=?";
		List<Sighting> list = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);	
			st.setInt(1, anno);
			st.setString(2, forma);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				list.add(new Sighting(res.getInt("id"), res.getTimestamp("datetime").toLocalDateTime(),
						res.getString("city"), res.getString("state"), res.getString("country"), res.getString("shape"),
						res.getInt("duration"), res.getString("duration_hm"), res.getString("comments"),
						res.getDate("date_posted").toLocalDate(), res.getDouble("latitude"),
						res.getDouble("longitude")));
			}

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}

		return list;
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
				idMap.put(state.getId(), state);
			}

			conn.close();
			

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<String> getForme(Integer anno) {
		final String sql = "SELECT DISTINCT(shape) AS forma " + 
				"FROM sighting " + 
				"WHERE YEAR(DATETIME)=? " + 
				"ORDER BY shape ASC";
		List<String> forme = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				forme.add(res.getString("forma"));
			}

			conn.close();
			return forme;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Adiacenza> getAdiacenze(Integer anno, String forma, Map<String, State> idMap) {
		final String sql = "SELECT n.state1 AS st1,n.state2 AS st2,COUNT(s1.shape) AS peso " + 
				"FROM neighbor AS n,sighting AS s1, sighting AS s2 " + 
				"WHERE n.state1 = s1.state " + 
				"AND n.state2 = s2.state " + 
				"AND s1.shape=? " + 
				"AND s2.shape=? " + 
				"AND YEAR(s1.datetime)=? " + 
				"AND YEAR(s2.datetime)=? " + 
				"GROUP BY n.state1,n.state2";
		List<Adiacenza> adiacenze = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, forma);
			st.setString(2, forma);
			st.setInt(3, anno);
			st.setInt(4, anno);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Adiacenza a = new Adiacenza(idMap.get(res.getString("st1")),idMap.get(res.getString("st2")),res.getInt("peso"));
				adiacenze.add(a);
			}

			conn.close();
			return adiacenze;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
	}

	public List<Sighting> getEventi(int anno, String forma) {
		// TODO Auto-generated method stub
		return null;
	}

	

}

