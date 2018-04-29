package xogame.control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import xogame.XOGame;

public class TTTstView implements Initializable
{
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{

	}

	@FXML
	private void single(ActionEvent event)
	{
		try
		{
			Pane tableViewParent = FXMLLoader.load(XOGame.window.getResource("view/TTTsingle.fxml"));
			Scene tableViewScene = new Scene(tableViewParent);
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(tableViewScene);
			window.show();
		}
		catch (IOException ex)
		{
			Logger.getLogger(TTTstView.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@FXML
	private void multi(ActionEvent event)
	{
		try
		{
			Pane tableViewParent = FXMLLoader.load(XOGame.window.getResource("view/TTTplay.fxml"));
			Scene tableViewScene = new Scene(tableViewParent);
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(tableViewScene);
			window.show();
		}
		catch (IOException ex)
		{
			//Logger.getLogger(TTTstView.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
        
        @FXML
        private void network(ActionEvent event)
	{
		try
		{
			Pane tableViewParent = FXMLLoader.load(XOGame.window.getResource("view/TTTnetwork.fxml"));
			Scene tableViewScene = new Scene(tableViewParent);
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(tableViewScene);
                        window.setOnCloseRequest(e -> {
                            try {
                                TTTnetwork.s.close();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        });
                        
			window.show();
		}
		catch (IOException ex)
		{
			//Logger.getLogger(TTTstView.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@FXML
	void quit(ActionEvent event)
	{
		Platform.exit();
	}  
}
