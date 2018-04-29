package xogame.control;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import xogame.XOGame;
import model.Game;
import model.User;

public class TTTplay implements Initializable {

        String choise;
	@FXML
	private ComboBox<String> options;

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		ObservableList<String> data = FXCollections.observableArrayList("Easy", "Hard");
		options.setItems(data);
		options.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println(newValue);
			if (choise.equalsIgnoreCase("easy"))
                        {
                            //easy method
                        }
                        else
                        {
                         //hard method
                        }
                            
                        //options.getSelectionModel().select(newValue);
		});
	}

	@FXML
	private void back(ActionEvent event)
	{

		try
		{
			Pane tableViewParent = FXMLLoader.load(XOGame.window.getResource("view/TTTst-view.fxml"));
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

	@FXML
	private void replay(ActionEvent event)
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
			Logger.getLogger(TTTsingle.class.getName()).log(Level.SEVERE, null, ex);
		}
	}


}

