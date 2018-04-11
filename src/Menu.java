import Controller.AmiralController;
import Controller.MenuController;
import Model.Game.*;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.application.Application;

import java.util.ArrayList;
import java.util.Arrays;
//Import to Play MP3
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class Menu extends Application{
    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        music();
        MenuController MenuController = new MenuController(primaryStage);

    }

    public void music(){
        String path = "src/Assets/sounds/Karstenholymoly_-_The_Thunderstorm.mp3";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        MediaView mediaView = new MediaView(mediaPlayer);
    }
}
