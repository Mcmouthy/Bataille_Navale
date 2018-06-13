package Controller;

import Model.Communication.ClientTCP;
import Model.Exception.PlacementCoinToucheException;
import Model.Exception.UnAuthorizedPlacementException;
import Model.Game.*;
import View.AmiralView;
import View.DialogInfo;
import View.PopupAssignationView;
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
import org.w3c.dom.css.CSSRule;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;


public class AmiralController implements EventHandler<MouseEvent>{
    private AmiralView view;
    private Partie model;
    private Equipe equipeInView;
    private ClientTCP client;
    private boolean placeTete = true;
    private Button lastButtonPlaced;
    private String idButtonX = "";
    private String idButtonY = "";
    private PopupAssignationView popupAssignationView;

    public AmiralController(Stage stage, Partie model, Equipe equipe, ClientTCP client){
        BorderPane root = new BorderPane();
        Scene scene;
        scene = new Scene(root, Screen.getPrimary().getVisualBounds().getWidth(),Screen.getPrimary().getVisualBounds().getHeight(), Color.BLACK);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.setTitle("Amiral, vos ordres ?!");
        stage.getIcons().add(new Image("Assets/img/Schooner.png"));
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.show();
        this.model = model;
        this.client = client;
        this.view=new AmiralView(stage,model,equipe);
        equipeInView = equipe;
        popupAssignationView = new PopupAssignationView(equipeInView);
        view.setController(this);
        view.setAmiralView();
    }

    @Override
    public void handle(MouseEvent event) {
        /** ACTIONS **/

        //** if abandon button clicked **//
        if (event.getSource().equals(view.abandonButton))
        {
            try {
                client.oosReq.writeInt(-2);
                client.oosReq.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (event.getSource().equals(view.readyButton))
        {
            if (equipeInView.isPlacementBateaux()){
                DialogInfo.showText(Alert.AlertType.INFORMATION,"Information", "Vous devez d'abord placer " +
                        "tous les navires pour engager le combat");
            }else{
                try {
                    sendPlacementToServer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                view.abandonButton.setDisable(false);
                view.readyButton.setDisable(true);
            }
        }else if(event.getSource() instanceof Button && isInCentralGrid(event.getSource()))
        {
                int x,y;
                String idButton = ((Button)event.getSource()).getId();
                x = Integer.parseInt(idButton.split("#")[0]);
                y = Integer.parseInt(idButton.split("#")[1]);

                if (equipeInView.isPlacementBateaux())
                {
                    if (placeTete)
                    {
                        if (equipeInView.getaPlacer().getTailleNavire()>1){
                            if (checkArroundCases(idButton,idButton,idButton)){
                                equipeInView.getPlateau().changeEtatCase(x,y,Etat.BATEAU);
                                ((Button)event.getSource()).setText(""+equipeInView.getaPlacer().getNomNavire());
                                ((Button)event.getSource()).getStyleClass().add("bateau");
                                placeTete = false;
                                lastButtonPlaced = (Button)event.getSource();
                                //ajout de la position du bateau dans l'objet Bateau a placer
                                Bateau b = equipeInView.getBateauByName(equipeInView.getaPlacer().getNomNavire());
                                b.setPositions(new Case[equipeInView.getaPlacer().getTailleNavire()]);
                                Case c = new Case(x,y,Etat.BATEAU);
                                b.getPositions()[0]=c;
                                view.resetLastPlacement.setDisable(false);

                                //mettre en rouge les cases disponible pour placer fin du bateau
                                displaypossiblePlacement(idButton);
                            }else{
                                DialogInfo.showText(Alert.AlertType.WARNING,"Attention", "Placement impossible," +
                                        "vos navires se toucheraient !");

                            }
                        }else{
                            try
                            {
                                checkArroundCasesForOneSizedShip(((Button) event.getSource()).getId());
                                lastButtonPlaced = (Button)event.getSource();
                                equipeInView.getPlateau().changeEtatCase(x,y,Etat.BATEAU);
                                ((Button)event.getSource()).setText(""+equipeInView.getaPlacer().getNomNavire());
                                ((Button)event.getSource()).getStyleClass().add("bateau");
                                Case[] positions = new Case[1];
                                positions[0] = new Case(x,y,Etat.BATEAU);
                                equipeInView.getBateauByName(equipeInView.getaPlacer().getNomNavire()).setPositions(positions);
                                //recuperer l'index de aPlacer
                                int indexNextPlacement = equipeInView.getNextPlacement();
                                equipeInView.setaPlacer(equipeInView.getBateauxEquipe().get(indexNextPlacement));
                                view.placementNavire.setText("Placer le navire : "+equipeInView.getaPlacer().getNomNavire());
                            }catch(PlacementCoinToucheException e){
                                DialogInfo.showText(Alert.AlertType.WARNING,"Attention", e.getMessage());
                            }


                        }
                    }else{
                        //On place les autres positions du bateau selon sa taille
                        try
                        {
                            checkValidPlacement(event.getSource(),equipeInView.getaPlacer());
                            //check if all cases between head and queue is ETAT.VIDE
                            if(((Button) event.getSource()).getStyleClass().contains("possiblePlacement"))
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
                                                if (equipeInView.getaPlacer().getTailleNavire()==4){
                                                    newY = headY - Integer.parseInt("" + idButtonY.charAt(1))-(placementLoop-1)+1;
                                                }else{
                                                    newY = headY - Integer.parseInt("" + idButtonY.charAt(1))-(placementLoop-1);
                                                }
                                            }else newY = headY + Integer.parseInt("" + idButtonY.charAt(1))-(placementLoop-1);
                                            equipeInView.getaPlacer().getPositions()[placementLoop] = new Case(headX, newY, Etat.BATEAU);
                                            equipeInView.getPlateau().changeEtatCase(headX,newY,Etat.BATEAU);
                                            view.tableau[headX][newY].getStyleClass().add("bateau");

                                        }
                                        if (!idButtonX.equals("")){
                                            int newX;
                                            if (idButtonX.charAt(0)=='-') {
                                                newX = headX - (Integer.parseInt("" + idButtonX.charAt(1))-(placementLoop-1));
                                            }else newX = headX + (Integer.parseInt("" + idButtonX.charAt(1))-(placementLoop-1));
                                            equipeInView.getaPlacer().getPositions()[placementLoop] = new Case(newX,headY,Etat.BATEAU);
                                            equipeInView.getPlateau().changeEtatCase(newX,headY,Etat.BATEAU);
                                            view.tableau[newX][headY].getStyleClass().add("bateau");
                                        }
                                    }
                                    equipeInView.getaPlacer().getPositions()[equipeInView.getaPlacer().getPositions().length-1] = new Case(x,y,Etat.BATEAU);
                                    equipeInView.getPlateau().changeEtatCase(x,y,Etat.BATEAU);
                                    ((Button)event.getSource()).getStyleClass().add("bateau");
                                    hidePossiblePlacement();
                                    try{
                                        equipeInView.setaPlacer(equipeInView.getBateauxEquipe().get(equipeInView.getNextPlacement()));
                                        view.placementNavire.setText("Placer le navire : "+equipeInView.getaPlacer().getNomNavire());
                                        view.resetLastPlacement.setDisable(true);
                                    }catch(ArrayIndexOutOfBoundsException e){
                                        placeTete = false;
                                        model.setPlacementBateaux(false);
                                        equipeInView.setPlacementBateaux(false);
                                        view.readyButton.setDisable(false);
                                        view.rightSideButtonDisplay.getChildren().remove(view.resetLastPlacement);
                                        view.rightSideButtonDisplay.getChildren().remove(view.resetAllPlacement);
                                        view.placementNavire.setText("Tous les navires ont été placés !");
                                    }



                                }else{
                                    placeTete = true;
                                    equipeInView.getaPlacer().getPositions()[equipeInView.getaPlacer().getPositions().length-1] = new Case(x,y,Etat.BATEAU);
                                    equipeInView.getPlateau().changeEtatCase(x,y,Etat.BATEAU);
                                    hidePossiblePlacement();
                                    ((Button)event.getSource()).getStyleClass().add("bateau");
                                    equipeInView.setaPlacer(equipeInView.getBateauxEquipe().get(equipeInView.getNextPlacement()));
                                    view.placementNavire.setText("Placer le navire : "+equipeInView.getaPlacer().getNomNavire());
                                    view.resetLastPlacement.setDisable(true);
                                }
                            }else{
                                DialogInfo.showText(Alert.AlertType.WARNING,"Attention", "Placement interdit, sélectionnez les cases rouges pour placer la poupe du navire !");
                            }
                        }catch(UnAuthorizedPlacementException e){
                            DialogInfo.showText(Alert.AlertType.WARNING,"Attention", e.getMessage());
                        }

                    }
                }


        }else if (event.getSource().equals(view.resetLastPlacement)){
            resetPreviousHead();
        }else if (event.getSource().equals(view.resetAllPlacement)){
            clearAllGrid();
        }else if (event.getSource().equals(view.assignationGestion)){
            PopupAssignationController popupAssignationController = new PopupAssignationController(view.getStage(),equipeInView);
        }
    }

    private void sendPlacementToServer() throws IOException {
        client.oosReq.writeInt(1);
        client.oosReq.flush();
        client.oosReq.writeObject(equipeInView.getBateauxEquipe());
        client.oosReq.flush();
        System.out.println("got it");
    }

    private void checkValidPlacement(Object source, Bateau bateau) throws UnAuthorizedPlacementException {

        String idSource = ((Button)source).getId();
        if (!view.getScene().lookup("#"+idSource).getStyleClass().contains("possiblePlacement"))throw new UnAuthorizedPlacementException();
        String lastId = lastButtonPlaced.getId();
        int[] tempIdlast = {Integer.parseInt(lastId.split("#")[0]),Integer.parseInt(lastId.split("#")[1])};

        //if equals on bottom
        if ((tempIdlast[0]+"#"+(tempIdlast[1]+(bateau.getTailleNavire()-1))).equals(idSource))
        {
            idButtonY = "+"+(bateau.getTailleNavire()-2);
            idButtonX="";

            // chek on top
        }else if((tempIdlast[0]+"#"+(tempIdlast[1]-(bateau.getTailleNavire()-1))).equals(idSource))
        {
            idButtonY = "-"+(bateau.getTailleNavire()-2);
            idButtonX="";

            //check on right
        }else if(((tempIdlast[0]+(bateau.getTailleNavire()-1))+"#"+(tempIdlast[1])).equals(idSource))
        {
            idButtonX = "+"+(bateau.getTailleNavire()-2);
            idButtonY="";

            //check on left
        }else if(((tempIdlast[0]-(bateau.getTailleNavire()-1))+"#"+tempIdlast[1]).equals(idSource))
        {
            idButtonX = "-"+(bateau.getTailleNavire()-2);
            idButtonY="";
        }else{
            idButtonX="";
            idButtonY="";
            throw new UnAuthorizedPlacementException();
        }

    }

    private void displaypossiblePlacement(String sourceId) {
        /*affiche en rouge les cases ou l'on peut placer la queue du bateau*/
        Button head = lastButtonPlaced;
        Bateau aplacer = equipeInView.getaPlacer();
        int x,y;
        int newX;
        int newY;
        String position;
        String newPosition[] = new String[4];
        //decalage en x+(size-1)
        position = head.getId(); //X#Y
        x= Integer.parseInt(position.split("#")[0]);
        y= Integer.parseInt(position.split("#")[1]);
        switch (aplacer.getTailleNavire())
        {
            case 2:
                //Check case between head and queue
                //decalage en x+(size-1)
                newX = x+(aplacer.getTailleNavire()-1);
                if (checkArroundCases((x+1)+"#"+y,lastButtonPlaced.getId(),sourceId)){
                    newPosition[0] = newX+"#"+position.split("#")[1];
                }

                //decalage en x-(size-1)
                newX = x-(aplacer.getTailleNavire()-1);
                if (checkArroundCases((x-1)+"#"+y,lastButtonPlaced.getId(),sourceId)){
                    newPosition[1] = newX+"#"+position.split("#")[1];
                }

                //decalage en y+(size-1)
                newY = y+(aplacer.getTailleNavire()-1);
                if (checkArroundCases(x+"#"+(y+1),lastButtonPlaced.getId(),sourceId)){
                    newPosition[2] = position.split("#")[0]+"#"+newY;
                }

                //decalage en y-(size-1)
                newY = y-(aplacer.getTailleNavire()-1);
                if (checkArroundCases(x+"#"+(y-1),lastButtonPlaced.getId(),sourceId)){
                    newPosition[3] = position.split("#")[0]+"#"+newY;
                }
                break;
            case 3:
                //Check 2 cases between ta maman et ton padre !
                //decalage en x+(size-1)
                newX = x+(aplacer.getTailleNavire()-1);
                if (checkArroundCases((x+1)+"#"+y,lastButtonPlaced.getId(),sourceId)
                        && checkArroundCases((x+2)+"#"+y,lastButtonPlaced.getId(),sourceId)){
                    newPosition[0] = newX+"#"+position.split("#")[1];
                }

                //decalage en x-(size-1)
                newX = x-(aplacer.getTailleNavire()-1);
                if (checkArroundCases((x-1)+"#"+y,lastButtonPlaced.getId(),sourceId)
                        && checkArroundCases((x-2)+"#"+y,lastButtonPlaced.getId(),sourceId)){
                    newPosition[1] = newX+"#"+position.split("#")[1];
                }

                //decalage en y+(size-1)
                newY = y+(aplacer.getTailleNavire()-1);
                if (checkArroundCases(x+"#"+(y+1),lastButtonPlaced.getId(),sourceId)
                        && checkArroundCases(x+"#"+(y+2),lastButtonPlaced.getId(),sourceId)){
                    newPosition[2] = position.split("#")[0]+"#"+newY;
                }

                //decalage en y-(size-1)
                newY = y-(aplacer.getTailleNavire()-1);
                if (checkArroundCases(x+"#"+(y-1),lastButtonPlaced.getId(),sourceId)
                        && checkArroundCases(x+"#"+(y-2),lastButtonPlaced.getId(),sourceId)){
                    newPosition[3] = position.split("#")[0]+"#"+newY;
                }
                break;
            case 4:
                //decalage en x+(size-1)
                newX = x+(aplacer.getTailleNavire()-1);
                if (checkArroundCases((x+1)+"#"+y,lastButtonPlaced.getId(),sourceId)
                        && checkArroundCases((x+2)+"#"+y,lastButtonPlaced.getId(),sourceId)
                        && checkArroundCases((x+3)+"#"+y,lastButtonPlaced.getId(),sourceId)){
                    newPosition[0] = newX+"#"+position.split("#")[1];
                }

                //decalage en x-(size-1)
                newX = x-(aplacer.getTailleNavire()-1);
                if (checkArroundCases((x-1)+"#"+y,lastButtonPlaced.getId(),sourceId)
                        && checkArroundCases((x-2)+"#"+y,lastButtonPlaced.getId(),sourceId)
                        && checkArroundCases((x-3)+"#"+y,lastButtonPlaced.getId(),sourceId)){
                    newPosition[1] = newX+"#"+position.split("#")[1];
                }

                //decalage en y+(size-1)
                newY = y+(aplacer.getTailleNavire()-1);
                if (checkArroundCases(x+"#"+(y+1),lastButtonPlaced.getId(),sourceId)
                        && checkArroundCases(x+"#"+(y+2),lastButtonPlaced.getId(),sourceId)
                        && checkArroundCases(x+"#"+(y+3),lastButtonPlaced.getId(),sourceId)){
                    newPosition[2] = position.split("#")[0]+"#"+newY;
                }

                //decalage en y-(size-1)
                newY = y-(aplacer.getTailleNavire()-1);
                if (checkArroundCases(x+"#"+(y-1),lastButtonPlaced.getId(),sourceId)
                        && checkArroundCases(x+"#"+(y-2),lastButtonPlaced.getId(),sourceId)
                        && checkArroundCases(x+"#"+(y-3),lastButtonPlaced.getId(),sourceId)){
                    newPosition[3] = position.split("#")[0]+"#"+newY;
                }
            default:
                break;
        }

        for (int i=0;i<newPosition.length;i++){
            try{

                view.getScene().lookup("#"+newPosition[i]).getStyleClass().add("possiblePlacement");
            }catch (NullPointerException e){}
        }


    }

    private void hidePossiblePlacement(){
        for (int i=0;i<view.tableau.length;i++)
        {
            for (int j=0;j<view.tableau[i].length;j++)
            {
                if(view.tableau[i][j].getStyleClass().contains("possiblePlacement"))view.tableau[i][j].getStyleClass().remove("possiblePlacement");
            }
        }
    }


    private boolean isInCentralGrid(Object source) {
        for (int i=0;i<view.tableau.length;i++){
            for (int k=0;k<view.tableau[i].length;k++){
                if (view.tableau[i][k].equals(source))return true;
            }
        }
        return false;
    }

    private boolean checkArroundCases(String coords,String headPosition, String sourcePosition)
    {
        int x=Integer.parseInt(coords.split("#")[0]);
        int y=Integer.parseInt(coords.split("#")[1]);

        for (int col=x-1;col<=(x + 1);col++)
        {
            for (int row=y-1;row<=(y+1);row++)
            {
                //if not the center cell
                if(!((col+"#"+row).equals(coords) || (col+"#"+row).equals(headPosition) || (col+"#"+row).equals(sourcePosition)))
                {
                    try
                    {
                        if (equipeInView.getPlateau().getCaseByCoord(col,row).getState()!=Etat.EAU){
                            return false;
                        }
                    }catch (IndexOutOfBoundsException e){}
                }
            }
        }
        return true;
    }

    private void checkArroundCasesForOneSizedShip(String coords) throws PlacementCoinToucheException
    {
        int x=Integer.parseInt(coords.split("#")[0]);
        int y=Integer.parseInt(coords.split("#")[1]);

        for (int col=x-1;col<=(x + 1);col++)
        {
            for (int row=y-1;row<=(y+1);row++)
            {
                //if not the center cell
                if(! ((col== x) && (row == y)))
                {
                    try
                    {
                        if (equipeInView.getPlateau().getCaseByCoord(col,row).getState()==Etat.BATEAU){
                            throw new PlacementCoinToucheException();
                        }
                    }catch (IndexOutOfBoundsException e){}
                }
            }
        }
    }

    private void clearAllGrid()
    {
        //clear la vue
        placeTete=true;
        lastButtonPlaced = null;
        for (int i=0;i<view.tableau.length;i++)
        {
            for (int j=0;j<view.tableau[i].length;j++)
            {
                view.tableau[i][j].getStyleClass().remove("possiblePlacement");
                view.tableau[i][j].getStyleClass().remove("bateau");
                view.tableau[i][j].setText("");
            }
        }
        //clear le model
        //clear les positions des bateaux dans l'equipe
        equipeInView.getPlateau().initPlateau();
        Bateau s1= new Bateau("S1",false,true,1);
        Bateau s2= new Bateau("S2",false,true,1);
        Bateau s3= new Bateau("S3",false,true,1);
        Bateau s4= new Bateau("S4",false,true,1);
        Bateau t1= new Bateau("T1",false,true,2);
        Bateau t2= new Bateau("T2",false,true,2);
        Bateau t3= new Bateau("T3",false,true,2);
        Bateau c1= new Bateau("C1",false,true,3);
        Bateau c2= new Bateau("C2",false,true,3);
        Bateau cu= new Bateau("Cu",false,true,4);
        equipeInView.setBateauxEquipe(Arrays.asList( new Bateau[]{s1,s2,s3,s4,t1,t2,t3,c1,c2,cu}));
        equipeInView.setaPlacer(equipeInView.getBateauxEquipe().get(0));
        view.placementNavire.setText("Placer le navire : "+equipeInView.getaPlacer().getNomNavire());
    }

    private void resetPreviousHead()
    {
        //clear la cellule dans la vue
        placeTete=true;
        lastButtonPlaced.getStyleClass().remove("bateau");
        lastButtonPlaced.setText("");
        for (int i=0;i<view.tableau.length;i++)
        {
            for (int j=0;j<view.tableau[i].length;j++)
            {
                view.tableau[i][j].getStyleClass().remove("possiblePlacement");
            }
        }
        //clear la cellue dans le model
        equipeInView.getPlateau().getCaseById(lastButtonPlaced.getId()).setState(Etat.EAU);
        //clear la position de la tete du bateau à placer
        equipeInView.getaPlacer().getPositions()[0]=null;
    }
}
