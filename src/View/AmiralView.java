package View;
import Model.Game.*;

import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.File;


public class AmiralView {
    private Stage stage;
    private Partie partie;
    private Equipe equipeAmiral;
    private Amiral  amiral;
    private Equipe equipeAdverse;
    private boolean placementBateau = true;
    private Button[][] tableau;
    private GridPane assignationsPanel;
    private Label bateauxAdversesLabel;
    private Label bateauxAlliesLabel;
    private VBox affichageNavireAlliePane;
    private VBox affichageNavireAdversePane;
    private VBox leftSideScreenDisplay;





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
        tableau = new Button[10][10];
        initTableauButton();
        assignationsPanel = new GridPane();
        assignationsPanel.setId("assignations_tab");
        assignationsPanel.getStyleClass().add("assignationsGrid");
        bateauxAdversesLabel = new Label("Navires Adverses");
        bateauxAdversesLabel.getStyleClass().add("NavLabel");
        bateauxAlliesLabel = new Label("Navires Alliés");
        bateauxAlliesLabel.getStyleClass().add("NavLabel");
        initGridPaneArray();
        initNaviresAdversesPane();
        initNaviresAlliesPane();
        leftSideScreenDisplay = new VBox();
        leftSideScreenDisplay.getChildren().addAll(affichageNavireAlliePane,affichageNavireAdversePane);
        leftSideScreenDisplay.getStyleClass().add("leftSideScreen");


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
            //System.out.println(equipeAmiral.getBateauxEquipe().get(k).getNomNavire());
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
            //System.out.println(equipeAmiral.getBateauxEquipe().get(k).getNomNavire());
            for (int i=0;i<navire.length;i++){
                navire[i]= new Rectangle(10,10);
                navire[i].getStyleClass().addAll("navireAdverse","navireOK");
                panneau.getChildren().add(navire[i]);
            }
            affichageNavireAdversePane.getChildren().add(panneau);
        }
    }

    private void initGridPaneArray() {
        Label lab = new Label("Navire");
        lab.getStyleClass().addAll("assignationCell","assignationHeader");
        Label role = new Label("Rôle");
        role.getStyleClass().addAll("assignationCell","assignationHeader");
        Label assign = new Label("Joueur");
        assign.getStyleClass().addAll("assignationCell","assignationHeader");
        assignationsPanel.add(lab,0,0);
        assignationsPanel.add(role,1,0);
        assignationsPanel.add(assign,2,0);
        for (int i=1; i<10;i++)
        {
            lab = new Label("test" + i);
            lab.getStyleClass().add("assignationCell");
            role = new Label("Att" + i);
            role.getStyleClass().add("assignationCell");
            ComboBox<String> nomJoueur = new ComboBox<>();
            nomJoueur.getStyleClass().add("assignationsCell");
            nomJoueur.getItems().addAll("truc", "machin", "chose");
            assignationsPanel.add(lab,0,i);
            assignationsPanel.add(role,1,i);
            assignationsPanel.add(nomJoueur,2,i);
        }
    }

    private void initTableauButton() {
        for (int i=0;i<10;i++){
            for (int j=0;j<10;j++){
                tableau[i][j] = new Button();
                tableau[i][j].getStyleClass().add("caseButton");
            }
        }
    }

    public void setAmiralView(){
        stage.getScene().getRoot().setVisible(false);

        ((BorderPane) stage.getScene().getRoot()).getChildren().clear();
        ((BorderPane) stage.getScene().getRoot()).setRight(assignationsPanel);

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

}
