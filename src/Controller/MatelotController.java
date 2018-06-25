package Controller;

import Model.Communication.ClientTCP;
import Model.Game.*;
import View.AmiralView;
import View.DialogInfo;
import View.MatelotView;
import View.PopupAssignationView;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MatelotController extends Controller implements EventHandler<MouseEvent> {
    private MatelotView view;
    private Partie model;
    public Equipe equipeInview;
    private ClientTCP client;
    private Bateau selected;
    private Button shouttedButton;
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
        if (event.getSource() instanceof Button){
           if (isInGrid(view.myTeam,event.getSource()))
           {
               //si on entre dans ce morceau de code on selectionne un bateau ou on en deplace un
               if (((Button) event.getSource()).getStyleClass().contains("bateau")){
                   if (selected==null){
                       selected = equipeInview.getBateauByPosition(((Button) event.getSource()).getId());
                       if (selected!=null){
                           if (!selected.isRecharge()){
                               Matelot[] assignedToSelected = equipeInview.getAmiral().getAssignations().get(selected);
                               if (assignedToSelected !=null ) {
                                   if (assignedToSelected[Amiral.ATT].getPseudo().equals(view.joueur.getPseudo())) {
                                   /*
                                   Afficher les possibilités de tire sur le paneau adverse
                                    */
                                       for (Case c : selected.getPositions()) {
                                           for (int i = -1; i <= 1; i++) {
                                               for (int j = -1; j <= 1; j++) {
                                                   try {
                                                       view.ennemyTeam[c.getX() + i][c.getY() + j].getStyleClass().add("possibleFire");
                                                   } catch (ArrayIndexOutOfBoundsException e) {
                                                   }
                                               }
                                           }
                                       }
                                   } else if (assignedToSelected[Amiral.DEF].equals(view.joueur)) {
                                   /*
                                   Afficher les possibilités de déplacement du bateau
                                    */
                                       for (Case c : selected.getPositions()) {
                                           for (int i = -1; i <= 1; i++) {
                                               for (int j = -1; j <= 1; j++) {
                                                   try {
                                                       view.myTeam[c.getX() + i][c.getY() + j].getStyleClass().add("possibleDeplacement");
                                                   } catch (ArrayIndexOutOfBoundsException e) {
                                                   }
                                               }
                                           }
                                       }
                                   }
                               }
                           }else{
                               DialogInfo.showText(Alert.AlertType.WARNING,"Rechargement","Nous sommes en train de recharger");
                           }
                       }
                   }else{


                   }



               }

           }else{

               if (((Button) event.getSource()).getStyleClass().contains("possibleFire")){
                   selected.setRecharge(true);
                   System.out.println("On tire sur la case "+ ((Button) event.getSource()).getId());
                   shouttedButton= (Button) event.getSource();
                   try {
                       client.oosReq.writeInt(2);
                       client.oosReq.flush();
                       client.oosReq.writeObject(shouttedButton.getId());
                       client.oosReq.flush();

                   } catch (IOException e) {
                       e.printStackTrace();
                   }
                   for (int k=0;k<view.ennemyTeam.length;k++)
                   {
                       for (int a=0;a<view.ennemyTeam[k].length;a++){
                           view.ennemyTeam[k][a].getStyleClass().remove("possibleFire");
                       }
                   }
               }


           }
        }

    }
    private boolean isInGrid(Button[][] tab, Object source) {
        for (int i=0;i<tab.length;i++){
            for (int k=0;k<tab[i].length;k++){
                if (tab[i][k].equals(source))return true;
            }
        }
        return false;
    }

    public void updateShipPlacement()
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (Bateau b: equipeInview.getBateauxEquipe())
                {
                    for (Case c : b.getPositions()){
                        if (b.getPositions()[0].equals(c)){
                            view.myTeam[c.getX()][c.getY()].setText(b.getNomNavire());
                        }
                        view.myTeam[c.getX()][c.getY()].getStyleClass().add("bateau");
                        view.myTeam[c.getX()][c.getY()].setId(c.getX()+"#"+c.getY());
                    }
                }
            }
        });

    }

    public void updateShotted(int isShotted)
    {
        if (isShotted==1){
            // on a touche
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    shouttedButton.getStyleClass().add("shotted");
                }
            });

        }else{
            //dans l'eau
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    shouttedButton.getStyleClass().remove("possibleFire");
                    shouttedButton.getStyleClass().add("nothingToShoot");
                }
            });

        }
        Bateau oldSelected = selected;
        shouttedButton.getStyleClass().remove("nothingToShoot");
        oldSelected.setRecharge(false);
        selected=null;
    }
}
