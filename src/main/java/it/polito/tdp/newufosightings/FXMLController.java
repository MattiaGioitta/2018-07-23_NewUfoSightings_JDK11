package it.polito.tdp.newufosightings;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.newufosightings.model.Model;
import it.polito.tdp.newufosightings.model.StampaPeso;
import it.polito.tdp.newufosightings.model.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

//controller turno A --> switchare al branch master_turnoB per turno B

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
    private Button btnSelezionaAnno;

    @FXML
    private ComboBox<String> cmbBoxForma;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private TextField txtT1;

    @FXML
    private TextField txtAlfa;

    @FXML
    private Button btnSimula;

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtResult.clear();
    	Integer anno; 
    	try {
    		anno = Integer.parseInt(this.txtAnno.getText());
    		if(anno<1910 || anno>2014) {
    			this.txtResult.setText("Inserisci un anno tra 1910 e 2014");
        		return;
    		}
    		String forma = this.cmbBoxForma.getValue();
    		if(forma == null) {
    			this.txtResult.setText("Scegli una forma");
        		return;
    		}
    		this.model.createGraph(forma,anno);
    		this.txtResult.appendText("Grafo creato\n");
    		this.txtResult.appendText("#Vertici: "+this.model.nVertici()+"\n");
    		this.txtResult.appendText("#Archi: "+this.model.nArchi()+"\n");
    		
    		List<StampaPeso> lista = this.model.getStampe();
    		for(StampaPeso s : lista) {
    			this.txtResult.appendText(s.toString()+"\n");
    		}
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Inserisci l'anno nel formato corretto");
    		return;
    	}
    	

    }

    @FXML
    void doSelezionaAnno(ActionEvent event) {
    	this.txtResult.clear();
    	Integer anno; 
    	try {
    		anno = Integer.parseInt(this.txtAnno.getText());
    		if(anno<1910 || anno>2014) {
    			this.txtResult.setText("Inserisci un anno tra 1910 e 2014");
        		return;
    		}
    		this.cmbBoxForma.getItems().addAll(this.model.getForme(anno));
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Inserisci l'anno nel formato corretto");
    		return;
    	}

    }

    @FXML
    void doSimula(ActionEvent event) {
    	
    	this.txtResult.clear();
    	this.model.reset();
    	Integer T;
    	Integer alfa;
    	Integer anno; 
    	try {
    		anno = Integer.parseInt(this.txtAnno.getText());
    		if(anno<1910 || anno>2014) {
    			this.txtResult.setText("Inserisci un anno tra 1910 e 2014");
        		return;
    		}
    		String forma = this.cmbBoxForma.getValue();
    		if(forma == null) {
    			this.txtResult.setText("Scegli una forma");
        		return;
    		}
    		T=Integer.parseInt(this.txtT1.getText());
    		if(T>365) {
    			this.txtResult.setText("Inserisci un numero T minore di 365");
        		return;
    		}
    		alfa = Integer.parseInt(this.txtAlfa.getText());
    		if(alfa<0 || alfa>100) {
    			this.txtResult.setText("Inserisci un numero alfa tra 0 e 100");
        		return;
    		}
    		this.model.simula(alfa,T,anno,forma);
    		for(State s : this.model.stati()) {
    			this.txtResult.appendText(s.stampaDefcon()+"\n");
    		}
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Inserisci i campi T1 o alfa nel formato corretto");
    		return;
    	}

    }

    @FXML
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert btnSelezionaAnno != null : "fx:id=\"btnSelezionaAnno\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert cmbBoxForma != null : "fx:id=\"cmbBoxForma\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert txtT1 != null : "fx:id=\"txtT1\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert txtAlfa != null : "fx:id=\"txtAlfa\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
	}
}
