package Controller;

import Model.Game.Partie;
import View.AmiralView;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;

public class AmiralController implements EventHandler<MouseEvent>{
    private AmiralView view;
    private Partie model;

    public AmiralController(Stage stage,Partie model){
        BorderPane root = new BorderPane();
        Scene scene;
        scene = new Scene(root, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight(), Color.BLACK);
        stage.setScene(scene);
        stage.setTitle("Test Amiral Vue");
        stage.getIcons().add(new Image("Assets/img/Schooner.png"));
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.show();
        this.model = model;
        this.view=new AmiralView(stage,model,model.getEquipeA());
        view.setAmiralView();
    }

    @Override
    public void handle(MouseEvent event) {

    }
}
