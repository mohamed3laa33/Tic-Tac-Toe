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

public class TTT implements Initializable
{
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{

	}

	@FXML
	private void log_in(ActionEvent event)
	{
		try
		{
			Pane tableViewParent = FXMLLoader.load(XOGame.window.getResource("view/start.fxml"));
			Scene tableViewScene = new Scene(tableViewParent);
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(tableViewScene);
			window.show();
		}
		catch (IOException ex)
		{
			Logger.getLogger(TTT.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@FXML
	private void sign_up(ActionEvent event)
	{
		try
		{
			Pane tableViewParent = FXMLLoader.load(XOGame.window.getResource("view/register.fxml"));
			Scene tableViewScene = new Scene(tableViewParent);
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(tableViewScene);
			window.show();
		}
		catch (IOException ex)
		{
			Logger.getLogger(TTT.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
