package Controller;

import Model.Communication.Client;
import Model.Communication.ServeurMain;
import Model.Game.Partie;
import View.AmiralView;
import View.MenuView;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MenuController implements EventHandler<MouseEvent>{
    private MenuView view;
    AudioClip clip;

    public MenuController(Stage stage){
        BorderPane root = new BorderPane();
        Scene scene;
        scene = new Scene(root, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight(), Color.BLACK);
        stage.setScene(scene);
        stage.setTitle("Bataille Navale");
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.show();
        String path = "src/Assets/sounds/Karstenholymoly_-_The_Thunderstorm.mp3";
        clip = new AudioClip(new File(path).toURI().toString());
        clip.setVolume(0.15);
        clip.play();
        this.view=new MenuView(stage);
        view.setController(this);
        view.setMenuView();
    }

    public static void exitProgram(){
       System.exit(0);
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getSource().equals(view.startGame)){
            view.setStartGameView();
        }else if(event.getSource().equals(view.joinGame)){
            String[] args= new String[]{view.ipServer.getText(),"12880",view.pseudoInput.getText()};
            Client.main(args);
            view.getStage().close();
        }else if (event.getSource().equals(view.exitGame)){
            exitProgram();
        }else if (event.getSource().equals(view.createGame)){
            view.setNewGameView();
        }else if(event.getSource().equals(view.createServer)){
            clip.stop();
            Thread server = new Thread(new Runnable() {
                @Override
                public void run() {
                    ServeurMain.main(new String[]{"12880",view.ipAddressForNewServer.getText()});
                }
            });
            server.setDaemon(true);
            server.start();
            view.creationServer.setText("Le serveur a été créé à l'adresse suivante : "+view.ipAddressForNewServer.getText());
            view.setMenuView(true);
        }else if(event.getSource().equals(view.backToMenu)){
            view.setMenuView();
        }
    }
}
