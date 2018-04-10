import Controller.AmiralController;
import Controller.MenuController;
import Model.Game.*;
import javafx.stage.Stage;
import javafx.application.Application;

import java.util.ArrayList;
import java.util.Arrays;


public class Menu extends Application{
    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        MenuController MenuController = new MenuController(primaryStage);
    }
}
