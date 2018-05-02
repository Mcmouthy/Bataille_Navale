package Controller;

import Model.Exception.UnAuthorizedPlacementException;
import Model.Game.*;
import View.AmiralView;
import View.DialogInfo;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class AmiralController implements EventHandler<MouseEvent>{
    private AmiralView view;
    private Partie model;
    private Equipe equipeInView;
    private boolean placeTete = true;
    private Button lastButtonPlaced;
    private String idButtonX = "";
    private String idButtonY = "";

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
                int x,y;
                String idButton = ((Button)event.getSource()).getId();
                x = Integer.parseInt(idButton.split("#")[0]);
                y = Integer.parseInt(idButton.split("#")[1]);

                if (equipeInView.isPlacementBateaux())
                {
                    if (placeTete)
                    {
                        //placer la tete du bateaux, mettre en rouge ou on peut
                        //pas placer la fin du bateau


                        //changement de l'etat de la case sur le plateau à ETAT.BATEAU
                        equipeInView.getPlateau().changeEtatCase(x,y,Etat.BATEAU);
                        ((Button)event.getSource()).setText(""+equipeInView.getaPlacer().getNomNavire());
                        ((Button)event.getSource()).getStyleClass().add("bateau");
                        if (equipeInView.getaPlacer().getTailleNavire()>1){
                            placeTete = false;
                            lastButtonPlaced = (Button)event.getSource();
                            //ajout de la position du bateau dans l'objet Bateau a placer
                            Bateau b = equipeInView.getBateauByName(equipeInView.getaPlacer().getNomNavire());
                            b.setPositions(new Case[equipeInView.getaPlacer().getTailleNavire()]);
                            Case c = new Case(x,y,Etat.BATEAU);
                            b.getPositions()[0]=c;

                            //mettre en rouge les cases indisponible pour placer fin du bateau
                            displaypossiblePlacement();
                        }else{
                            Case[] positions = new Case[1];
                            positions[0] = new Case(x,y,Etat.BATEAU);
                            equipeInView.getBateauByName(equipeInView.getaPlacer().getNomNavire()).setPositions(positions);
                            //recuperer l'index de aPlacer
                            int indexNextPlacement = equipeInView.getNextPlacement();
                            equipeInView.setaPlacer(equipeInView.getBateauxEquipe().get(indexNextPlacement));
                            view.placementNavire.setText("Placer le navire : "+equipeInView.getaPlacer().getNomNavire());

                        }
                    }else{
                        //On place les autres positions du bateau selon sa taille
                        try
                        {
                            checkValidPlacement(event.getSource(),equipeInView.getaPlacer());
                            //check if all cases between head and queue is ETAT.VIDE
                            if(checkVoidCasesBetweenPlacement(event.getSource(),lastButtonPlaced,equipeInView.getaPlacer()))
                            {
                                //need to fill all cases beetween two cases in model and view
                                if (equipeInView.getaPlacer().getTailleNavire()>2){
                                    placeTete = true;
                                    int headX = equipeInView.getaPlacer().getPositions()[0].getX();
                                    int headY = equipeInView.getaPlacer().getPositions()[0].getY();
                                    for (int placementLoop=1;placementLoop<equipeInView.getaPlacer().getTailleNavire()-1;placementLoop++)
                                    {
                                        if (!idButtonY.equals(""))
                                        {
                                            int newY;
                                            if (idButtonY.charAt(0)=='-') {
                                                newY = headY - Integer.parseInt("" + idButtonY.charAt(1))-(placementLoop-1);
                                            }else newY = headY + Integer.parseInt("" + idButtonY.charAt(1))-(placementLoop-1);
                                            equipeInView.getaPlacer().getPositions()[placementLoop] = new Case(headX, newY, Etat.BATEAU);
                                            view.tableau[headX][newY].getStyleClass().add("bateau");

                                        }
                                        if (!idButtonX.equals("")){
                                            int newX;
                                            if (idButtonX.charAt(0)=='-') {
                                                newX = headX - (Integer.parseInt("" + idButtonX.charAt(1))-(placementLoop-1));
                                            }else newX = headX + (Integer.parseInt("" + idButtonX.charAt(1))-(placementLoop-1));
                                            equipeInView.getaPlacer().getPositions()[placementLoop] = new Case(newX,headY,Etat.BATEAU);
                                            view.tableau[newX][headY].getStyleClass().add("bateau");
                                        }
                                    }
                                    equipeInView.getaPlacer().getPositions()[equipeInView.getaPlacer().getPositions().length-1] = new Case(x,y,Etat.BATEAU);
                                    ((Button)event.getSource()).getStyleClass().add("bateau");
                                    try{
                                        equipeInView.setaPlacer(equipeInView.getBateauxEquipe().get(equipeInView.getNextPlacement()));
                                        view.placementNavire.setText("Placer le navire : "+equipeInView.getaPlacer().getNomNavire());
                                    }catch(ArrayIndexOutOfBoundsException e){
                                        placeTete = false;
                                        model.setPlacementBateaux(false);
                                        equipeInView.setPlacementBateaux(false);
                                        view.placementNavire.setText("Tous les navires ont été placés !");
                                    }



                                }else{
                                    placeTete = true;
                                    equipeInView.getaPlacer().getPositions()[equipeInView.getaPlacer().getPositions().length-1] = new Case(x,y,Etat.BATEAU);
                                    ((Button)event.getSource()).getStyleClass().add("bateau");
                                    equipeInView.setaPlacer(equipeInView.getBateauxEquipe().get(equipeInView.getNextPlacement()));
                                    view.placementNavire.setText("Placer le navire : "+equipeInView.getaPlacer().getNomNavire());
                                }
                            }
                        }catch(UnAuthorizedPlacementException e){
                            DialogInfo.showText(Alert.AlertType.WARNING,"Attention", e.getMessage());
                        }

                    }
                }

            }
        }
    }

    private boolean checkVoidCasesBetweenPlacement(Object source, Button lastButtonPlaced,Bateau b) {
        switch(b.getTailleNavire()){
            case 2:
                return true;
            case 3: //need to check 1 case
                String idToCheck = "";
                if (!idButtonY.equals(""))
                {
                    int tempY = Integer.parseInt(lastButtonPlaced.getId().split("#")[1]);
                    int newY;
                    if (idButtonY.charAt(0)=='-') newY = tempY - Integer.parseInt(""+idButtonY.charAt(1));
                    else newY = tempY + Integer.parseInt(""+idButtonY.charAt(1));
                    idToCheck = lastButtonPlaced.getId().split("#")[0]+"#"+newY;
                    if (equipeInView.getPlateau().getCaseByCoord(Integer.parseInt(idToCheck.split("#")[0]),
                            Integer.parseInt(idToCheck.split("#")[1])).getState()==Etat.EAU)
                        return true;
                }else if (!idButtonX.equals(""))
                {
                    int tempX = Integer.parseInt(lastButtonPlaced.getId().split("#")[0]);
                    int newX;
                    if (idButtonX.charAt(0)=='-') newX = tempX - Integer.parseInt(""+idButtonX.charAt(1));
                    else newX = tempX + Integer.parseInt(""+idButtonX.charAt(1));
                    idToCheck = newX+"#"+lastButtonPlaced.getId().split("#")[1];
                    if (equipeInView.getPlateau().getCaseByCoord(Integer.parseInt(idToCheck.split("#")[0]),
                            Integer.parseInt(idToCheck.split("#")[1])).getState()==Etat.EAU)
                        return true;
                    else return false;

                }
            case 4: //need to check 2 cases

                int headX = Integer.parseInt(lastButtonPlaced.getId().split("#")[0]);
                int headY = Integer.parseInt(lastButtonPlaced.getId().split("#")[1]);
                String[] idsToCheck = new String[2];
                if (!idButtonX.equals(""))
                {
                    for (int k =0;k<2;k++)
                    {
                        if (idButtonX.charAt(0)=='-')
                        {
                            idsToCheck[k] = (headX-(k+1))+"#"+headY;
                        }else{
                            idsToCheck[k] = (headX+(k+1))+"#"+headY;
                        }
                    }
                }
                if (!idButtonY.equals(""))
                {
                    for (int k =0;k<2;k++)
                    {
                        if (idButtonY.charAt(0)=='-')
                        {
                            idsToCheck[k] = headX+"#"+(headY-(k+1));
                        }else{
                            idsToCheck[k] = headX+"#"+(headY+(k+1));
                        }
                    }
                }
                if (equipeInView.getPlateau().getCaseById(idsToCheck[0]).getState()==Etat.EAU
                        && equipeInView.getPlateau().getCaseById(idsToCheck[1]).getState()==Etat.EAU) return true;
                else return false;

            default:
                return false;
        }

    }

    private boolean checkValidPlacement(Object source, Bateau bateau) throws UnAuthorizedPlacementException {
        String idSource = ((Button)source).getId();
        String lastId = lastButtonPlaced.getId();
        int[] tempIdlast = {Integer.parseInt(lastId.split("#")[0]),Integer.parseInt(lastId.split("#")[1])};

        //if equals on bottom
        if ((tempIdlast[0]+"#"+(tempIdlast[1]+(bateau.getTailleNavire()-1))).equals(idSource))
        {
            idButtonY = "+"+(bateau.getTailleNavire()-2);
            idButtonX="";
            return true;
            // chek on top
        }else if((tempIdlast[0]+"#"+(tempIdlast[1]-(bateau.getTailleNavire()-1))).equals(idSource))
        {
            idButtonY = "-"+(bateau.getTailleNavire()-2);
            idButtonX="";
            return true;
            //check on right
        }else if(((tempIdlast[0]+(bateau.getTailleNavire()-1))+"#"+(tempIdlast[1])).equals(idSource))
        {
            idButtonX = "+"+(bateau.getTailleNavire()-2);
            idButtonY="";
            return true;
            //check on left
        }else if(((tempIdlast[0]-(bateau.getTailleNavire()-1))+"#"+tempIdlast[1]).equals(idSource))
        {
            idButtonX = "-"+(bateau.getTailleNavire()-2);
            idButtonY="";
            return true;
        }
        idButtonX="";
        idButtonY="";
        throw new UnAuthorizedPlacementException();

    }

    private void displaypossiblePlacement() {
        /*affiche en rouge les cases ou l'on peut placer la queue du bateau*/

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
