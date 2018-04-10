package View;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

public class MenuView {

    private Stage stage;
    private Button createGame;
    private Button startGame;
    private Button exitGame;
    private VBox menu;

    public MenuView(Stage stage) {
        this.stage=stage;
        initAttributes();

    }

    private void initAttributes() {
        stage.getScene().getStylesheets().add(new File("src/Assets/css/menuView.css").toURI().toString());
        createGame= new Button("Créer une partie");
        startGame= new Button("Rejoindre une partie");
        exitGame= new Button ("quitter");
        menu= new VBox();
        menu.getChildren().addAll(createGame,startGame,exitGame);

    }

    public void setMenuView() {
        stage.getScene().getRoot().setVisible(false);

        ((BorderPane) stage.getScene().getRoot()).getChildren().clear();
        ((BorderPane) stage.getScene().getRoot()).setCenter(menu);
        stage.getScene().getRoot().setVisible(true);
    }
}
