package Controller;

import Model.Game.Partie;
import View.AmiralView;
import View.MenuView;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MenuController implements EventHandler<MouseEvent>{
    private MenuView view;

    public MenuController(Stage stage){
        BorderPane root = new BorderPane();
        Scene scene;
        scene = new Scene(root, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight(), Color.BLACK);
        stage.setScene(scene);
        stage.setTitle("Menu Principal");
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.show();
        this.view=new MenuView(stage);
        view.setMenuView();
    }

    public static void exitProgram(){
       System.exit(0);
    }

    @Override
    public void handle(MouseEvent event) {

    }
}
