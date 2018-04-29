package xogame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class XOGame extends Application
{
	public static Class window;

	@Override
	public void start(Stage stage) throws Exception
	{
		window = getClass();
		Parent root = FXMLLoader.load(window.getResource("view/TTT.fxml"));
                    
                Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
