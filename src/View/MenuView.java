package View;

import Controller.AmiralController;
import Controller.MenuController;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.net.*;
import java.util.Enumeration;
import java.util.Iterator;

public class MenuView {

    private Stage stage;
    public Button createGame;
    public Button startGame;
    public Button exitGame;
    public VBox menu;
    public Label creationServer;
    public TextField ipServer;
    public TextField pseudoInput;
    public TextField ipAddressForNewServer;
    public Button joinGame;
    public Button createServer;
    public Button backToMenu;

    public MenuView(Stage stage) {
        this.stage=stage;
        initAttributes();

    }

    private void initAttributes() {
        stage.getScene().getStylesheets().add(new File("src/Assets/css/menuView.css").toURI().toString());
        stage.setFullScreen(true);
        createGame= new Button("Créer un serveur");
        startGame= new Button("Rejoindre une partie");
        exitGame= new Button ("Quitter");
        joinGame= new Button("Rejoindre");
        backToMenu = new Button ("Retour");
        createServer = new Button("Créer le serveur");
        menu= new VBox(20);
        menu.setId("buttonMenu");
        ipServer = new TextField();
        ipServer.setText("192.168.43.231");
        pseudoInput = new TextField();
        pseudoInput.setText("PlayerName");
        ipAddressForNewServer = new TextField();
        createGame.getStyleClass().add("buttonWidth");
        startGame.getStyleClass().add("buttonWidth");
        exitGame.getStyleClass().add("buttonWidth");
        ipServer.getStyleClass().add("text-area");
        pseudoInput.getStyleClass().add("text-area");
        backToMenu.getStyleClass().add("buttonWidth");
        joinGame.getStyleClass().add("buttonWidth");
        menu.getChildren().addAll(createGame,startGame,exitGame);

        creationServer = new Label();

    }
    public void setMenuView() {
        stage.getScene().getRoot().setVisible(false);
        ((BorderPane) stage.getScene().getRoot()).getChildren().clear();
        ((BorderPane) stage.getScene().getRoot()).setCenter(menu);
        stage.getScene().getRoot().setVisible(true);
    }

    public void setNewGameView() {

        creationServer.getStyleClass().add("creationServer");
        VBox b = new VBox(10);
        b.setId("buttonMenu");
        b.getChildren().addAll(ipAddressForNewServer,createServer,backToMenu);
        stage.getScene().getRoot().setVisible(false);
        ((BorderPane) stage.getScene().getRoot()).getChildren().clear();
        ((BorderPane) stage.getScene().getRoot()).setCenter(b);
        //((BorderPane) stage.getScene().getRoot()).setBottom(creationServer);
        stage.getScene().getRoot().setVisible(true);
    }

    public void setStartGameView() {

        //Creation of grid pane
        VBox buttonStartMenu = new VBox(10);
        buttonStartMenu.setId("buttonMenu");

        buttonStartMenu.getChildren().addAll(ipServer,pseudoInput,joinGame,backToMenu);
        stage.getScene().getRoot().setVisible(false);
        ((BorderPane) stage.getScene().getRoot()).getChildren().clear();
        //((BorderPane) stage.getScene().getRoot()).setCenter(ipServer);
        ((BorderPane) stage.getScene().getRoot()).setCenter(buttonStartMenu);
        stage.getScene().getRoot().setVisible(true);
    }
    public void setController(MenuController eh){
        createGame.setOnMouseClicked(eh);
        startGame.setOnMouseClicked(eh);
        exitGame.setOnMouseClicked(eh);
        joinGame.setOnMouseClicked(eh);
        backToMenu.setOnMouseClicked(eh);
        createServer.setOnMouseClicked(eh);

    }
    public Stage getStage(){
        return stage;
    }

    public void setMenuView(boolean b) {
        stage.getScene().getRoot().setVisible(false);
        ((BorderPane) stage.getScene().getRoot()).getChildren().clear();
        ((BorderPane) stage.getScene().getRoot()).setCenter(menu);
        ((BorderPane) stage.getScene().getRoot()).setBottom(creationServer);
        stage.getScene().getRoot().setVisible(true);
    }
}
