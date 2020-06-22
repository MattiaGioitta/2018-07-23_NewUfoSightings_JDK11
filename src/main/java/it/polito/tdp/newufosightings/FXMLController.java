package it.polito.tdp.newufosightings;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.newufosightings.model.Model;
import it.polito.tdp.newufosightings.model.StampaPesoAdiacenti;
import it.polito.tdp.newufosightings.model.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

//controller turno B --> switchare al branch master_turnoA per turno A

public class FXMLController {
	
	private Model model;

	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea txtResult;

    @FXML
    private TextField txtAnno;

    @FXML
    private TextField txtxG;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private TextField txtT1;

    @FXML
    private TextField txtT2;

    @FXML
    private Button btnSimula;

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtResult.clear();
    	Integer xG;
    	Integer anno;
    	try {
    		xG = Integer.parseInt(this.txtxG.getText());
    		if(xG<1 || xG>180) {
    			this.txtResult.setText("numero xG deve essere compreso tra 1 e 180!");
        		return;
    		}
    		anno = Integer.parseInt(this.txtAnno.getText());
    		if(anno<1906 || anno>2014) {
    			this.txtResult.setText("L'anno deve essere compreso tra 1906 e 2014!");
        		return;
    		}
    		this.model.createGraph(xG,anno);
    		this.txtResult.appendText("Grafo creato con\n");
    		this.txtResult.appendText(String.format("#Vertici: %d\n#Archi: %d\n", this.model.nVertici(),this.model.nArchi()));
    		List<StampaPesoAdiacenti> lista = this.model.getPesoAdiacenti();
    		for(StampaPesoAdiacenti s : lista) {
    			this.txtResult.appendText(s.toString()+"\n");
    		}
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Valori inseriti non corretti nel formato!");
    		return; 
    	}

    }

    @FXML
    void doSimula(ActionEvent event) {
    	this.txtResult.clear();
    	Integer T1;
    	Integer T2;
    	Integer xG;
    	Integer anno;
    	try {
    		xG = Integer.parseInt(this.txtxG.getText());
    		if(xG<1 || xG>180) {
    			this.txtResult.setText("numero xG deve essere compreso tra 1 e 180!");
        		return;
    		}
    		anno = Integer.parseInt(this.txtAnno.getText());
    		if(anno<1906 || anno>2014) {
    			this.txtResult.setText("L'anno deve essere compreso tra 1906 e 2014!");
        		return;
    		}
    		T1 = Integer.parseInt(this.txtT1.getText());
    		T2 = Integer.parseInt(this.txtT2.getText());
    		if(T1>365 || T2>365) {
    			this.txtResult.setText("Inserisci t1 o t2 minori di 365!");
        		return;
    		}
    		this.model.simula(T1,T2,xG,anno);
    		List<State> stati = this.model.getStati();
    		for(State s : stati) {
    			this.txtResult.appendText(s.getName()+" numero allerte massime: "+s.getAllerteMassime()+"\n");
    		}
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Valori inseriti non corretti nel formato!");
    		return; 
    	}

    }

    @FXML
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert txtxG != null : "fx:id=\"txtxG\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert txtT1 != null : "fx:id=\"txtT1\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert txtT2 != null : "fx:id=\"txtT2\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
	}
}
