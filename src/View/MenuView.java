package View;

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
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MenuView {

    private Stage stage;
    public Button createGame;
    public Button startGame;
    public Button exitGame;
    public VBox menu;
    public Label creationServer;

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
        menu= new VBox(20);
        menu.setId("buttonMenu");
        createGame.getStyleClass().add("buttonWidth");
        startGame.getStyleClass().add("buttonWidth");
        exitGame.getStyleClass().add("buttonWidth");
        menu.getChildren().addAll(createGame,startGame,exitGame);
        createGame.setOnAction(event -> this.setNewGameView());
        startGame.setOnAction(event -> this.setStartGameView());
        exitGame.setOnAction(event -> MenuController.exitProgram());
        try {
            creationServer = new Label("Le serveur a été créé avec l'adresse IP : "+InetAddress.getLocalHost().getHostAddress());

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
    public void setMenuView() {
        stage.getScene().getRoot().setVisible(false);
        ((BorderPane) stage.getScene().getRoot()).getChildren().clear();
        ((BorderPane) stage.getScene().getRoot()).setCenter(menu);
        stage.getScene().getRoot().setVisible(true);
    }

    public void setNewGameView() {

        creationServer.getStyleClass().add("creationServer");
        stage.getScene().getRoot().setVisible(false);
        ((BorderPane) stage.getScene().getRoot()).getChildren().clear();
        ((BorderPane) stage.getScene().getRoot()).setCenter(menu);
        ((BorderPane) stage.getScene().getRoot()).setBottom(creationServer);
        stage.getScene().getRoot().setVisible(true);
    }

    public void setStartGameView() {

        //Creation of grid pane
        TextField ipServer = new TextField();
        ipServer.setText("Taper l'IP du serveur");
        HBox buttonStartMenu = new HBox(10);
        buttonStartMenu.setId("buttonMenu");
        startGame= new Button("Rejoindre");
        exitGame= new Button ("Retour");
        exitGame.setOnAction(event -> setMenuView());

        buttonStartMenu.getChildren().addAll(ipServer,startGame,exitGame);
        stage.getScene().getRoot().setVisible(false);
        ((BorderPane) stage.getScene().getRoot()).getChildren().clear();
        //((BorderPane) stage.getScene().getRoot()).setCenter(ipServer);
        ((BorderPane) stage.getScene().getRoot()).setCenter(buttonStartMenu);
        stage.getScene().getRoot().setVisible(true);
    }

}
