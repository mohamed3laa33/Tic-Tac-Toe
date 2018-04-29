/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xogame.control;

import database.DBM;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import xogame.XOGame;
import model.Game;
import model.User;
import network.Msg;
import session.Session;

/**
 * FXML Controller class
 *
 * @author A'laa
 */
public class TTTnetwork implements Initializable {
    
    @FXML
    private Label turnLabel;
    
    @FXML
    public Label statusLabel;
    
    Boolean xTurn = false;
    Boolean gameEnded = false;

    @FXML
    private Button nine;

    @FXML
    private Button six;

    @FXML
    private Button four;

    @FXML
    private Button one;

    @FXML
    private Button seven;

    @FXML
    private Button two;

    @FXML
    private Button five;

    @FXML
    private Button three;

    @FXML
    private Button eight;

    Game game;
    User user;
    int x = 0;
    int o = 0;
    
    public static Socket s;
    public static ObjectOutputStream oos;
    public static ObjectInputStream ois;
    Msg msg;

    @FXML
    ListView users;
    Button square1;
    Button square2;
    //ArrayList<User> allUsers;

    // squares
    @FXML
    Button square3, square4, square5, square6, square7, square8, square9;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //.getStage().setTitle(newTitle);
//        Platform.class.
       // window.setTitle("Zizo");
       
        try {
            xTurn = true;
            users.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                user = (User) newValue;
                System.out.println(user.getUserName());
                
                try {
                    if (Session.getAuth().getId() != user.getId()) {
                        oos = new ObjectOutputStream(s.getOutputStream());
                        //                      Type          to           from
                        oos.writeObject(new Msg("REQUEST", user.getId(), Session.getAuth().getId()));
                    }
                } catch (IOException ex) {
                    Logger.getLogger(TTTnetwork.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            });
        } catch (Exception e) {
        }
        
        
        // NETWORK
        try {
            s = new Socket("127.0.0.1", 5005);
            oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(new Msg("USER", Session.getAuth()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Runnable myRunnable = new Runnable() {

            public void run() {
                while (true) {
                    try {
                        ois = new ObjectInputStream(s.getInputStream());
                        msg = (Msg) ois.readObject();
                        

                        
                        if (msg != null) {
                            switch (msg.type) {
                                 case "NOTIFICATION":
                                     Platform.runLater(() -> {
                                         statusLabel.setText(msg.message);
                                     });
     
                                 	// sendMessage(msg);
                                 	//sendToAllExceptMe(msg);
                                 	break;
                                 case "REFRESH":// Refresh all users list
                                 	// sendMessage(msg);
                                 	//sendToAllExceptMe(msg);
                                        setUsersView(User.all());
                                 	break;
                                case "REQUEST":// Refresh all users list
                                    Platform.runLater(() -> {
                                        
                                        Alert alert = new Alert(AlertType.CONFIRMATION);
                                        alert.setTitle("Confirmation Dialog");
                                        
                                        alert.setHeaderText(User.find(msg.from).getUserName() + " watnts to play a gmae with you");
                                        alert.setContentText("Would you like to play right now?");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {
                                            turnLabel.setText("X turn");
                                            xTurn = true;
                                            Session.player1 = msg.from;
                                            Session.player2 = msg.to;
                                            Session.amIX = false;
                                            Session.gameId = Game.createNewGagme(Session.player1, Session.player2);
                                           
                                            // NETWORK
                                            try {
                                                oos = new ObjectOutputStream(s.getOutputStream());
                                                oos.writeObject(new Msg("ACCEPT", Session.player1 , Session.player2, Session.gameId));
                                                // create game;
                                            } catch (IOException ex) {
                                                ex.printStackTrace();
                                            }
                                            
                                        } else {
                                            // ... user chose CANCEL or closed the dialog
                                                                                        // NETWORK
                                            try {
                                                oos = new ObjectOutputStream(s.getOutputStream());
                                                oos.writeObject(new Msg("REJECT", msg.from , msg.to));
                                            } catch (IOException ex) {
                                                ex.printStackTrace();
                                            }
                                        }
                                    });

                                    break;
                                case "ACCEPT":
                                    Platform.runLater(() -> {
                                        Alert alert = new Alert(AlertType.INFORMATION);
                                        alert.setTitle(Session.getAuth().getUserName());
                                        alert.setHeaderText("Request Accepted!");
                                        
                                        String s = User.find(msg.from).getUserName() + " has accepted your request, you can start the game as the player X";
                                        alert.setContentText(s);
                                        alert.show();
                                        
                                        turnLabel.setText("X turn");
                                        xTurn = true;
                                        Session.player1 = msg.to;
                                        Session.player2 = msg.from;
                                        Session.amIX = true;
                                        Session.gameId = msg.position;
                                        System.out.println("Game ID is ..." + Session.gameId);
                                        
                                    });
                                    break;
                                case "REJECT":
                                    Platform.runLater(() -> {
                                    
                                        Alert alert = new Alert(AlertType.INFORMATION);
                                        alert.setTitle(Session.getAuth().getUserName());
                                        alert.setHeaderText("Request Rejected!");
                                        String s = User.find(msg.from).getUserName() + " has declined your request :(";
                                        alert.setContentText(s);
                                        alert.show();
                                        
                                        Session.player1 = 0;
                                        Session.player2 = 0;
                                        Session.amIX = null;
                                    });
                                    break;
                                case "MOVE":// Refresh all u
                                   
                                    Platform.runLater(() -> {
                                        
                                        xTurn = !xTurn;
                                        if (xTurn) {
                                            turnLabel.setText("X turn");
                                            //checkForWinner(Session.player1, Session.player2);
                                        } else {
                                            turnLabel.setText("O turn");
                                            //checkForWinner(Session.player1, Session.player2);
                                        }
                                    });

                                    
                                    switch(msg.position){
                                        
                                        case 0:
                                            Platform.runLater(() -> {
                                                one.setText(getXO());
                                                checkForWinner(Session.player1, Session.player2);
                                                
                                            });
                                            break;
                                        case 1:
                                            Platform.runLater(() -> {
                                                two.setText(getXO());
                                                checkForWinner(Session.player1, Session.player2);
                                            });
                                            break;
                                        case 2:
                                            Platform.runLater(() -> {
                                                three.setText(getXO());
                                                checkForWinner(Session.player1, Session.player2);
                                            });
                                            break;
                                        case 3:
                                            Platform.runLater(() -> {
                                                four.setText(getXO());
                                                checkForWinner(Session.player1, Session.player2);
                                            });
                                            break;
                                        case 4:
                                            Platform.runLater(() -> {
                                                five.setText(getXO());
                                                checkForWinner(Session.player1, Session.player2);
                                            });
                                            break;
                                        case 5:
                                            Platform.runLater(() -> {
                                                six.setText(getXO());
                                                checkForWinner(Session.player1, Session.player2);
                                            });
                                            break;
                                        case 6:
                                            Platform.runLater(() -> {
                                                seven.setText(getXO());
                                                checkForWinner(Session.player1, Session.player2);
                                            });
                                            break;
                                        case 7:
                                            Platform.runLater(() -> {
                                                eight.setText(getXO());
                                                checkForWinner(Session.player1, Session.player2);
                                            });
                                            break;
                                        case 8:
                                            Platform.runLater(() -> {
                                                nine.setText(getXO());
                                                checkForWinner(Session.player1, Session.player2);
                                            });
                                            break;
                                            
                                        

                                          
                                    }
                                    
//                                    break;
//                                default:
                               
                                break;

                            }
                        }
                        //ta.setText(ta.getText() + '\n' + msg.message);
                        //System.out.println(msg.message);
                    } catch (Exception ex) {
                        //ex.printStackTrace();
                        System.err.println("Server is now offline!");
                        break;
                    }
                }
            }
        };

        Thread thread = new Thread(myRunnable);
        thread.start();
    }

    @FXML
    private void back(ActionEvent event) {
        try {
            TTTnetwork.s.close();
            Pane tableViewParent = FXMLLoader.load(XOGame.window.getResource("view/TTTst-view.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
            
//            if (!Game.isUnfinished(0, user.getId())) {
//              getGameState();
//              Game.saveGameState(game.getGameId(), game.getState());
//            }

        } catch (IOException ex) {
            //Logger.getLogger(TTTplay.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void rank(ActionEvent event) {
        try {
            Pane tableViewParent = FXMLLoader.load(XOGame.window.getResource("view/rank.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();  
      } catch (IOException ex) {
            Logger.getLogger(TTTplay.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public void receiveRequest(Game game, User user) {
//        this.game = game;
//        this.user = user;
//        setGameState();
//        x = user.getId();
//        o = 0;
//    }

    private void setUsersView(ArrayList<User> allUsers) {
        ObservableList<User> data = FXCollections.observableArrayList(allUsers);
        users.setItems(data);
        users.setCellFactory(param -> new ListCell<User>() {
            private ImageView imageView = new ImageView();

            @Override
            public void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    if (user.getStatus()) {
                        imageView.setImage(new Image("/xogame/images/online_user.png"));
                    } else {
                        imageView.setImage(new Image("/xogame/images/offline_user.png"));
                    }
                    setText(user.getUserName());
                    setGraphic(imageView);
                }
            }
        });
    }

    public void setGameState() {
        square1.setText(game.getState().square1);
        square2.setText(game.getState().square2);
        square3.setText(game.getState().square3);
        square4.setText(game.getState().square4);
        square5.setText(game.getState().square5);
        square6.setText(game.getState().square6);
        square7.setText(game.getState().square7);
        square8.setText(game.getState().square8);
        square9.setText(game.getState().square9);
    }

    public void getGameState() {
        game.getState().square1 = square1.getText();
        game.getState().square2 = square2.getText();
        game.getState().square3 = square3.getText();
        game.getState().square4 = square4.getText();
        game.getState().square5 = square5.getText();
        game.getState().square6 = square6.getText();
        game.getState().square7 = square7.getText();
        game.getState().square8 = square8.getText();
        game.getState().square9 = square9.getText();
    }
    
    public void checkForWinner(int player1, int player2) {
        int winnerId = 0;
        
        if (one.getText().equalsIgnoreCase("X") && two.getText().equalsIgnoreCase("X") && three.getText().equalsIgnoreCase("X")) {
            winnerId = player1;
        } else if (four.getText().equalsIgnoreCase("X") && five.getText().equalsIgnoreCase("X") && six.getText().equalsIgnoreCase("X")) {
            winnerId = player1;
        } else if (seven.getText().equalsIgnoreCase("X") && eight.getText().equalsIgnoreCase("X") && nine.getText().equalsIgnoreCase("X")) {
            winnerId = player1;
        } else if (one.getText().equalsIgnoreCase("X") && four.getText().equalsIgnoreCase("X") && seven.getText().equalsIgnoreCase("X")) {
            winnerId = player1;
        } else if (two.getText().equalsIgnoreCase("X") && five.getText().equalsIgnoreCase("X") && eight.getText().equalsIgnoreCase("X")) {
            winnerId = player1;
        } else if (three.getText().equalsIgnoreCase("X") && six.getText().equalsIgnoreCase("X") && nine.getText().equalsIgnoreCase("X")) {
            winnerId = player1;
        } else if (one.getText().equalsIgnoreCase("X") && five.getText().equalsIgnoreCase("X") && nine.getText().equalsIgnoreCase("X")) {
            winnerId = player1;
        } else if (three.getText().equalsIgnoreCase("X") && five.getText().equalsIgnoreCase("X") && seven.getText().equalsIgnoreCase("X")) {
            winnerId = player1;
        } else if (one.getText().equalsIgnoreCase("O") && two.getText().equalsIgnoreCase("O") && three.getText().equalsIgnoreCase("O")) {
            winnerId = player2;
        } else if (four.getText().equalsIgnoreCase("O") && five.getText().equalsIgnoreCase("O") && six.getText().equalsIgnoreCase("O")) {
            winnerId = player2;
        } else if (seven.getText().equalsIgnoreCase("O") && eight.getText().equalsIgnoreCase("O") && nine.getText().equalsIgnoreCase("O")) {
            winnerId = player2;
        } else if (one.getText().equalsIgnoreCase("O") && four.getText().equalsIgnoreCase("O") && seven.getText().equalsIgnoreCase("O")) {
            winnerId = player2;
        } else if (two.getText().equalsIgnoreCase("O") && five.getText().equalsIgnoreCase("O") && eight.getText().equalsIgnoreCase("O")) {
            winnerId = player2;
        } else if (three.getText().equalsIgnoreCase("O") && six.getText().equalsIgnoreCase("O") && nine.getText().equalsIgnoreCase("O")) {
            winnerId = player2;
        } else if (one.getText().equalsIgnoreCase("O") && five.getText().equalsIgnoreCase("O") && nine.getText().equalsIgnoreCase("O")) {
            winnerId = player2;
        } else if (three.getText().equalsIgnoreCase("O") && five.getText().equalsIgnoreCase("O") && seven.getText().equalsIgnoreCase("O")) {
            winnerId = player2;
        }
        
        if(winnerId != 0){
            DBM.exec("UPDATE game SET winnerId = ? WHERE id = ?", "" + winnerId, "" + Session.gameId);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle(Session.getAuth().getUserName());
            alert.setHeaderText("Result !");
            String s = "The winner is " + User.find(winnerId).getUserName();
            Session.player1 = 0;
            Session.player2 = 0;
            Session.amIX = null;
            alert.setContentText(s);
            alert.show();
            clearBoard();
            
            
        }
        
        if(winnerId == 0 && gameEnded == true){
            try {
                DBM.exec("UPDATE game SET winnerId = ? WHERE id = ?", "-1", "" + Session.gameId);
            } catch (Exception e) {
            }
            
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle(Session.getAuth().getUserName());
            alert.setHeaderText("Result !");
            String s = "Tie !";
            alert.setContentText(s);
            alert.show();
            clearBoard();
        }

        //System.out.println(winnerId + " Won");
        //Game.setWinner(game.getGameId(), winnerId);
    }
    
    private void clearBoard(){
        Session.player1 = 0;
        Session.player2 = 0;
        Session.amIX = null;
        Session.gameId = 0;
        
        one.setText("");
        two.setText("");
        three.setText("");
        four.setText("");
        five.setText("");
        six.setText("");
        seven.setText("");
        eight.setText("");
        nine.setText("");
        turnLabel.setText("Turn");
        
    }
    
    @FXML
    private void oneClicked(ActionEvent event) {
        
        
        System.err.println("Am I X = " + Session.amIX + ", Xturn = " + xTurn);
        if (xTurn != Session.amIX) {
            return;
        }
        
        if(one.getText().equals("X") || one.getText().equals("O")){
            return;
        }
        
        try {
            oos = new ObjectOutputStream(s.getOutputStream());
           // Msg(String type, int to, int from, int position)
//           new Msg(type, message, o, o, x)
            oos.writeObject(new Msg("MOVE", Session.gameId + "", Session.getOpponent() , Session.getAuth().getId(), 0));
            //oos.reset();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    private void twoClicked(ActionEvent event) {
        
        System.err.println("Am I X = " + Session.amIX + ", Xturn = " + xTurn);
        if (xTurn != Session.amIX) {
            return;
        }
        
        if (two.getText().equals("X") || two.getText().equals("O")) {
            return;
        }
        
        try {
            oos = new ObjectOutputStream(s.getOutputStream());
           // Msg(String type, int to, int from, int position)
            oos.writeObject(new Msg("MOVE", Session.gameId + "", Session.getOpponent() , Session.getAuth().getId(), 1));
            //oos.reset();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    private void threeClicked(ActionEvent event) {
        
        System.err.println("Am I X = " + Session.amIX + ", Xturn = " + xTurn);
        if (xTurn != Session.amIX) {
            return;
        }
        
        if (three.getText().equals("X") || three.getText().equals("O")) {
            return;
        }
        
        try {
            oos = new ObjectOutputStream(s.getOutputStream());
            // Msg(String type, int to, int from, int position)
            oos.writeObject(new Msg("MOVE", Session.gameId + "", Session.getOpponent() , Session.getAuth().getId(), 2));
            //oos.reset();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    private void fourClicked(ActionEvent event) {
        
        System.err.println("Am I X = " + Session.amIX + ", Xturn = " + xTurn);
        if (xTurn != Session.amIX) {
            return;
        }
        
        if (four.getText().equals("X") || four.getText().equals("O")) {
            return;
        }
        try {
            oos = new ObjectOutputStream(s.getOutputStream());
            // Msg(String type, int to, int from, int position)
            oos.writeObject(new Msg("MOVE", Session.gameId + "", Session.getOpponent() , Session.getAuth().getId(), 3));
            //oos.reset();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    private void fiveClicked(ActionEvent event) {
        
        System.err.println("Am I X = " + Session.amIX + ", Xturn = " + xTurn);
        if (xTurn != Session.amIX) {
            return;
        }
        
        if (five.getText().equals("X") || five.getText().equals("O")) {
            return;
        }
        try {
            oos = new ObjectOutputStream(s.getOutputStream());
            // Msg(String type, int to, int from, int position)
            oos.writeObject(new Msg("MOVE", Session.gameId + "", Session.getOpponent() , Session.getAuth().getId(), 4));
            //oos.reset();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    private void sixClicked(ActionEvent event) {
        
        System.err.println("Am I X = " + Session.amIX + ", Xturn = " + xTurn);
        if (xTurn != Session.amIX) {
            return;
        }
        
        if (six.getText().equals("X") || six.getText().equals("O")) {
            return;
        }
                try {
            oos = new ObjectOutputStream(s.getOutputStream());
            // Msg(String type, int to, int from, int position)
            oos.writeObject(new Msg("MOVE", Session.gameId + "", Session.getOpponent() , Session.getAuth().getId(), 5));
            //oos.reset();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    private void sevenClicked(ActionEvent event) {
        
        System.err.println("Am I X = " + Session.amIX + ", Xturn = " + xTurn);
        if (xTurn != Session.amIX) {
            return;
        }
        
        if (seven.getText().equals("X") || seven.getText().equals("O")) {
            return;
        }
                try {
            oos = new ObjectOutputStream(s.getOutputStream());
            // Msg(String type, int to, int from, int position)
            oos.writeObject(new Msg("MOVE", Session.gameId + "", Session.getOpponent() , Session.getAuth().getId(), 6));
            //oos.reset();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    private void eightClicked(ActionEvent event) {
        
        System.err.println("Am I X = " + Session.amIX + ", Xturn = " + xTurn);
        if (xTurn != Session.amIX) {
            return;
        }
        
        if (eight.getText().equals("X") || eight.getText().equals("O")) {
            return;
        }
                try {
            oos = new ObjectOutputStream(s.getOutputStream());
            // Msg(String type, int to, int from, int position)
            oos.writeObject(new Msg("MOVE", Session.gameId + "", Session.getOpponent() , Session.getAuth().getId(), 7));
            //oos.reset();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    private void nineClicked(ActionEvent event) {
        
        System.err.println("Am I X = " + Session.amIX + ", Xturn = " + xTurn);
        if (xTurn != Session.amIX) {
            return;
        }
        
        if (nine.getText().equals("X") || nine.getText().equals("O")) {
            return;
        }
                try {
            oos = new ObjectOutputStream(s.getOutputStream());
            // Msg(String type, int to, int from, int position)
            oos.writeObject(new Msg("MOVE", Session.gameId + "", Session.getOpponent() , Session.getAuth().getId(), 8));
            //oos.reset();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public Boolean isXTurn(){
        int turnCounter = 0;
        if(one.getText().equals("X") || one.getText().equals("O"))
            turnCounter++;
        if (two.getText().equals("X") || two.getText().equals("O"))
            turnCounter++;
        if(three.getText().equals("X") || three.getText().equals("O"))
            turnCounter++;
        if (four.getText().equals("X") || four.getText().equals("O"))
            turnCounter++;
        if(five.getText().equals("X") || five.getText().equals("O"))
            turnCounter++;
        if (six.getText().equals("X") || six.getText().equals("O"))
            turnCounter++;
        if(seven.getText().equals("X") || seven.getText().equals("O"))
            turnCounter++;
        if (eight.getText().equals("X") || eight.getText().equals("O"))
            turnCounter++;
        if (nine.getText().equals("X") || nine.getText().equals("O"))
            turnCounter++;
        
        if(turnCounter == 8){
            gameEnded = true;
        }
        
        if (turnCounter % 2 == 0) {
            return true;
        }
        
        return false;
    }
    
    public String getXO(){
        if (isXTurn())
            return "X";
        return "O";
    }
    
    
}
