import Controller.MenuController;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.application.Application;
import java.io.File;

public class Appli extends Application{
    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        MenuController MenuController = new MenuController(primaryStage);

    }
}
