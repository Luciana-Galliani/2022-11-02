/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.itunes;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.itunes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	private boolean grafoCreato = false;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnPlaylist"
    private Button btnPlaylist; // Value injected by FXMLLoader

    @FXML // fx:id="cmbGenere"
    private ComboBox<String> cmbGenere; // Value injected by FXMLLoader

    @FXML // fx:id="txtDTOT"
    private TextField txtDTOT; // Value injected by FXMLLoader

    @FXML // fx:id="txtMax"
    private TextField txtMax; // Value injected by FXMLLoader

    @FXML // fx:id="txtMin"
    private TextField txtMin; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaPlaylist(ActionEvent event) {
    	
    	if(!grafoCreato) {
    		this.txtResult.setText("Creare prima il grafico");
    		return;
    	}
    	
    	String d = this.txtDTOT.getText();
    	if(d == "") {
    		this.txtResult.setText("Inserire una durata tot");
    		return;
    	}
    	
    	int dTot =0;
    	try {
    		//in minuti
    		dTot = Integer.parseInt(d);
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Inserire dei parametri numerici");
    		return;
    	}
    	
    	this.model.getPath(dTot*60); //da min a s
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtResult.clear();
    	
    	String genere = this.cmbGenere.getValue();
    	
    	if( genere == null) {
    		this.txtResult.setText("Scegliere un genere");
    		return;
    	}
    	
    	String minimo = this.txtMin.getText();
    	String massimo = this.txtMax.getText();
    	
    	if( minimo == "" || massimo == "") {
    		this.txtResult.setText("Inserire dei parametri");
    		return;
    	}
    	
    	int min =0;
    	int max=0;
    	try {
    		//sono in secondi
    		min = Integer.parseInt(minimo);
    		max = Integer.parseInt(massimo);
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Inserire dei parametri numerici");
    		return;
    	}

    	this.model.creaGrafo(genere, min, max);
    	this.txtResult.appendText("Grafo creato!\n");
    	this.txtResult.appendText("#Vertici: "+this.model.nVertici()+"\n");
    	this.txtResult.appendText("#Archi: "+this.model.nArchi()+"\n\n");
    	
    	this.grafoCreato=true;
    	
    	this.txtResult.appendText(this.model.getConnectedComponents());
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPlaylist != null : "fx:id=\"btnPlaylist\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbGenere != null : "fx:id=\"cmbGenere\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtDTOT != null : "fx:id=\"txtDTOT\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMax != null : "fx:id=\"txtMax\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMin != null : "fx:id=\"txtMin\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.cmbGenere.getItems().addAll(this.model.generi());
    }

}
