package Controller;

import Model.Communication.ClientTCP;
import Model.Game.Equipe;
import Model.Game.Joueur;
import Model.Game.Partie;
import View.AmiralView;
import View.MatelotView;
import View.PopupAssignationView;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MatelotController extends Controller implements EventHandler<MouseEvent> {
    private MatelotView view;
    private Partie model;
    public Equipe equipeInview;
    private ClientTCP client;
    public MatelotController(Stage stage, Partie p, Equipe e, Joueur j, ClientTCP client) {
        BorderPane root = new BorderPane();
        Scene scene;
        scene = new Scene(root, Screen.getPrimary().getVisualBounds().getWidth(),Screen.getPrimary().getVisualBounds().getHeight(), Color.BLACK);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.setTitle("Matelot à vos postes !");
        stage.getIcons().add(new Image("Assets/img/Schooner.png"));
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.show();
        this.model = p;
        equipeInview = e;
        this.client = client;
        this.view=new MatelotView(stage,model,j);
        view.setController(this);
        view.setMatelotView();
    }

    @Override
    public void handle(MouseEvent event) {

    }

    public void test(){
        System.out.println("on a reçu une maj !");
    }
}
