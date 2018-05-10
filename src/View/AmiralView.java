package View;
import Controller.AmiralController;
import Model.Game.*;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.File;
import java.util.Map;


public class AmiralView {
    private Stage stage;
    public Partie partie;
    public Equipe equipeAmiral;
    public Amiral  amiral;
    public Equipe equipeAdverse;
    public boolean placementBateau = true;
    public Button[][] tableau;
    public VBox assignationsPanel;
    public Label bateauxAdversesLabel;
    public Label bateauxAlliesLabel;
    public VBox affichageNavireAlliePane;
    public VBox affichageNavireAdversePane;
    public VBox leftSideScreenDisplay;
    public VBox rightSideScreenDisplay;
    public Button abandonButton;
    public Button finPlacementBateau;
    public Button readyButton;
    public HBox rightSideButtons;
    public Label phaseJeux;
    public Label placementNavire;
    public Button resetLastPlacement;
    public VBox rightSideButtonDisplay;
    public Button resetAllPlacement;
    public Button assignationGestion;



    public AmiralView(Stage stage,Partie model, Equipe equipe){
        partie = model;
        equipeAmiral = equipe;
        if (equipe.equals(model.getEquipeA())) equipeAdverse = model.getEquipeB();
        else equipeAdverse = model.getEquipeA();

        amiral = equipe.getAmiral();
        this.stage = stage;
        initAttributes();
    }

    private void initAttributes() {

        stage.getScene().getStylesheets().add(new File("src/Assets/css/amiralView.css").toURI().toString());
        //initialize 2-D array for all buttons
        initTableauButton();

        //initiamize left side display for ally and ennemy ship
        initLeftSideScreenDisplay();

        //initialize rightside screen for assignations
        initRightSideScreenDisplay();


    }

    private void initLeftSideScreenDisplay() {
        bateauxAdversesLabel = new Label("Navires Adverses");
        bateauxAdversesLabel.getStyleClass().add("NavLabel");
        bateauxAlliesLabel = new Label("Navires Alliés");
        bateauxAlliesLabel.getStyleClass().add("NavLabel");
        initNaviresAdversesPane();
        initNaviresAlliesPane();
        leftSideScreenDisplay = new VBox();
        leftSideScreenDisplay.getChildren().addAll(affichageNavireAlliePane,affichageNavireAdversePane);
        leftSideScreenDisplay.getStyleClass().add("leftSideScreen");

    }

    private void initRightSideScreenDisplay() {

        rightSideScreenDisplay = new VBox();
        rightSideScreenDisplay.setId("assignations_tab");
        rightSideScreenDisplay.getStyleClass().add("assignationsGrid");

        assignationGestion = new Button("Gérer les assignations");
        assignationGestion.setId("assignationsGestion");
        rightSideScreenDisplay.getChildren().add(assignationGestion);

        phaseJeux = new Label("Placement des navires.");
        phaseJeux.setId("labelIndication");
        rightSideScreenDisplay.getChildren().add(phaseJeux);

        placementNavire = new Label("Placer le navire : "+ equipeAmiral.getBateauxEquipe().get(0).getNomNavire());
        placementNavire.setId("placementNavireIndication");
        if (placementBateau){
            rightSideScreenDisplay.getChildren().add(placementNavire);
        }

        resetLastPlacement = new Button("Annuler le dernier placement de tête");
        resetLastPlacement.setDisable(true);

        abandonButton = new Button("Abandonner la partie");
        abandonButton.setId("abandonButton");
        if (!equipeAmiral.isPret()){
            abandonButton.setDisable(true);
        }

        readyButton = new Button("Engager le combat !");
        readyButton.setId("readyButton");
        if (equipeAmiral.isPret()){
            readyButton.setDisable(true);
        }

        resetAllPlacement = new Button("Vider la grille de placement");

        rightSideButtons = new HBox();
        rightSideButtonDisplay = new VBox();
        rightSideButtons.setId("rightSideButtons");
        rightSideButtons.getChildren().addAll(abandonButton,readyButton);
        rightSideButtonDisplay.getChildren().addAll(rightSideButtons,resetLastPlacement,resetAllPlacement);
        rightSideButtonDisplay.setSpacing(10);

        rightSideScreenDisplay.getChildren().add(rightSideButtonDisplay);

    }

    private void initNaviresAlliesPane() {
        Label nomBateau;
        Rectangle[] navire;
        affichageNavireAlliePane = new VBox();
        affichageNavireAlliePane.setId("afficheAllie");
        affichageNavireAlliePane.getChildren().add(bateauxAlliesLabel);
        for (int k=0; k<equipeAmiral.getBateauxEquipe().size();k++){
            HBox panneau = new HBox();
            panneau.getStyleClass().add("navireSquare");
            nomBateau = new Label(equipeAmiral.getBateauxEquipe().get(k).getNomNavire());
            navire = new Rectangle[equipeAmiral.getBateauxEquipe().get(k).getTailleNavire()];
            panneau.getChildren().add(nomBateau);

            for (int i=0;i<navire.length;i++){
                navire[i]= new Rectangle(10,10);
                navire[i].getStyleClass().addAll("navireAllie","navireOK");
                panneau.getChildren().add(navire[i]);
            }
            affichageNavireAlliePane.getChildren().add(panneau);
        }

    }

    private void initNaviresAdversesPane() {
        Label nomBateau;
        Rectangle[] navire;
        affichageNavireAdversePane = new VBox();
        affichageNavireAdversePane.setId("afficheAdverse");
        affichageNavireAdversePane.getChildren().add(bateauxAdversesLabel);
        for (int k=0; k<equipeAdverse.getBateauxEquipe().size();k++){
            HBox panneau = new HBox();
            panneau.getStyleClass().add("navireSquare");
            nomBateau = new Label(equipeAdverse.getBateauxEquipe().get(k).getNomNavire());
            navire = new Rectangle[equipeAdverse.getBateauxEquipe().get(k).getTailleNavire()];
            panneau.getChildren().add(nomBateau);

            for (int i=0;i<navire.length;i++){
                navire[i]= new Rectangle(10,10);
                navire[i].getStyleClass().addAll("navireAdverse","navireOK");
                panneau.getChildren().add(navire[i]);
            }
            affichageNavireAdversePane.getChildren().add(panneau);
        }
    }


    private void initTableauButton() {
        tableau = new Button[10][10];
        for (int i=0;i<10;i++){
            for (int j=0;j<10;j++){
                tableau[i][j] = new Button();
                tableau[i][j].setId(i+"#"+j);
                tableau[i][j].getStyleClass().add("caseButton");
            }
        }
    }

    public void setAmiralView(){
        stage.getScene().getRoot().setVisible(false);

        ((BorderPane) stage.getScene().getRoot()).getChildren().clear();
        ((BorderPane) stage.getScene().getRoot()).setRight(rightSideScreenDisplay);

        GridPane panneau = new GridPane();
        panneau.setId("panneau");
        int chr = 65;
        Label lab;
        for (int k=0;k<tableau.length;k++){
            lab = new Label(""+(char)(chr+k));
            lab.getStyleClass().add("letterIndic");
            panneau.add(lab,k+1,0);
        }
        for (int i=0;i<tableau.length;i++){
            lab = new Label(""+(i+1));
            lab.getStyleClass().add("digitIndic");
            panneau.add(lab,0,i+1);
            for (int j=0;j<tableau[i].length;j++){
                panneau.add(tableau[i][j],i+1,j+1);
            }
        }

        ((BorderPane) stage.getScene().getRoot()).setCenter(panneau);
        ((BorderPane) stage.getScene().getRoot()).setLeft(leftSideScreenDisplay);


        stage.getScene().getRoot().setVisible(true);
    }

    public void setController(AmiralController eh){
        setControllerOnButtonDisplay(eh);
        readyButton.setOnMouseClicked(eh);
        abandonButton.setOnMouseClicked(eh);
        resetLastPlacement.setOnMouseClicked(eh);
        resetAllPlacement.setOnMouseClicked(eh);
        assignationGestion.setOnMouseClicked(eh);

    }

    private void setControllerOnButtonDisplay(AmiralController eh) {
        for (int i = 0 ; i< tableau.length;i++)
        {
            for (int k=0;k<tableau[i].length;k++)
            {
                tableau[i][k].setOnMouseClicked(eh);
            }
        }
    }

    public Scene getScene()
    {
        return stage.getScene();
    }

    public Stage getStage()
    {
        return stage;
    }

}
