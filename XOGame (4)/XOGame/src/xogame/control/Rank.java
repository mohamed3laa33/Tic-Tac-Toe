/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xogame.control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import xogame.XOGame;

/**
 * FXML Controller class
 *
 * @author The Ghost
 */
public class Rank implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    
	@FXML
	private void back(ActionEvent event)
	{

		try
		{
			Pane tableViewParent = FXMLLoader.load(XOGame.window.getResource("view/TTTnetwork.fxml"));
			Scene tableViewScene = new Scene(tableViewParent);
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(tableViewScene);
			window.show();
		}
		catch (IOException ex)
		{
			Logger.getLogger(TTTsingle.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
