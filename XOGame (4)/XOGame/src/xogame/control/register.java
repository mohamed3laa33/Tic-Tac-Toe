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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.User;
import xogame.XOGame;

public class register implements Initializable
{
    @FXML
    private PasswordField confirm;

    @FXML
    private PasswordField password;

    @FXML
    private TextField email;

    @FXML
    private TextField username;
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{

	}
        

	@FXML
	private void register(ActionEvent event)
	{
            System.out.println(username.getText());
            if (User.exists(username.getText())) {
             System.out.println("User already exists");
         }else{
             User.register(username.getText(), email.getText(), password.getText());
             if (User.login(username.getText(), password.getText())) {
                 try
		{
			Pane tableViewParent = FXMLLoader.load(XOGame.window.getResource("view/TTTst-view.fxml"));
			Scene tableViewScene = new Scene(tableViewParent);
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(tableViewScene);
                        window.setTitle(username.getText());
			window.show();
		}
		catch (IOException ex)
		{
			Logger.getLogger(TTTstView.class.getName()).log(Level.SEVERE, null, ex);
		}
	
             }
	}}
        	@FXML
	private void back(ActionEvent event)
	{

		try
		{
			
			Pane tableViewParent = FXMLLoader.load(XOGame.window.getResource("view/TTT.fxml"));
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
