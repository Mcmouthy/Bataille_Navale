package Controller;

import Model.Game.*;
import View.AmiralView;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    private Equipe equipeInView;
    private boolean placeTete = true;
    private Button lastButtonPlaced;

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
        equipeInView = model.getEquipeA();
        view.setController(this);
        view.setAmiralView();
    }

    @Override
    public void handle(MouseEvent event) {
        /** ACTIONS **/

        //** if abandon button clicked **//
        if (event.getSource().equals(view.abandonButton))
        {
            equipeInView.setAbandon(true);
        }else if (event.getSource().equals(view.readyButton))
        {
            equipeInView.setPret(true);
            view.abandonButton.setDisable(false);
            view.readyButton.setDisable(true);
        }else if(event.getSource() instanceof Button)
        {
            if (isInCentralGrid(event.getSource()))
            {
                if (equipeInView.isPlacementBateaux())
                {
                    if (placeTete)
                    {
                        //placer la tete du bateaux, mettre en rouge ou on peut
                        //pas placer la fin du bateau
                        int x,y;
                        String idButton = ((Button)event.getSource()).getId();
                        x = Integer.parseInt(idButton.split("#")[0]);
                        y = Integer.parseInt(idButton.split("#")[1]);
                        equipeInView.getPlateau().changeEtatCase(x,y,Etat.BATEAU);
                        ((Button)event.getSource()).getStyleClass().add("bateau");
                        if (equipeInView.getaPlacer().getTailleNavire()>1){
                            placeTete = false;
                            lastButtonPlaced = (Button)event.getSource();
                            //mettre en rouge les cases indisponible pour placer fin du bateau
                            disableImpossiblePlacement();
                        }else{
                            Case[] positions = new Case[1];
                            positions[0] = new Case(0,0,Etat.BATEAU);
                            equipeInView.getBateauByName(equipeInView.getaPlacer().getNomNavire()).setPositions(positions);
                            equipeInView.getBateauByName(equipeInView.getaPlacer().getNomNavire()).getPositions()[0].setX(x);
                            equipeInView.getBateauByName(equipeInView.getaPlacer().getNomNavire()).getPositions()[0].setY(y);
                            ((Button)event.getSource()).setText(""+equipeInView.getaPlacer().getNomNavire());
                            //recuperer l'index de aPlacer
                            int indexNextPlacement = equipeInView.getNextPlacement();
                            equipeInView.setaPlacer(equipeInView.getBateauxEquipe().get(indexNextPlacement));
                            view.placementNavire.setText("Placer le navire : "+equipeInView.getaPlacer().getNomNavire());

                        }
                    }else{
                        //On place les autres positions du bateau selon sa taille
                        if (checkValidPlacement(event.getSource(),equipeInView.getaPlacer()))
                        {
                            System.out.println("OK");
                        }else{
                            System.out.println("Error");
                        }

                    }
                }

            }
        }
    }

    private boolean checkValidPlacement(Object source, Bateau bateau) {
        String idSource = ((Button)source).getId();
        String lastId = lastButtonPlaced.getId();
        int[] tempIdsource = {Integer.parseInt(idSource.split("#")[0]),Integer.parseInt(idSource.split("#")[1])};
        int[] tempIdlast = {Integer.parseInt(lastId.split("#")[0]),Integer.parseInt(lastId.split("#")[1])};

        //if equals on bottom
        if ((tempIdlast[0]+"#"+(tempIdlast[1]+(bateau.getTailleNavire()-1))).equals(idSource))
        {
            return true;
            // chek on top
        }else if((tempIdlast[0]+"#"+(tempIdlast[1]-(bateau.getTailleNavire()-1))).equals(idSource))
        {
            return true;
            //check on right
        }else if(((tempIdlast[0]+(bateau.getTailleNavire()-1))+"#"+(tempIdlast[1])).equals(idSource))
        {
            return true;
            //check on left
        }else if(((tempIdlast[0]-(bateau.getTailleNavire()-1))+"#"+tempIdlast[1]).equals(idSource))
        {
            return true;
        }else{
            return false;
        }
    }

    private void disableImpossiblePlacement() {

    }

    private boolean isInCentralGrid(Object source) {
        for (int i=0;i<view.tableau.length;i++){
            for (int k=0;k<view.tableau[i].length;k++){
                if (view.tableau[i][k].equals(source))return true;
            }
        }
        return false;
    }
}
