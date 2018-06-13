package View;

import Controller.AmiralController;
import Controller.MatelotController;
import Model.Game.Equipe;
import Model.Game.Joueur;
import Model.Game.Matelot;
import Model.Game.Partie;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

public class MatelotView {
    private Stage stage;
    public Partie partie;
    public Joueur joueur;
    public Label typeMatelot;
    public Button[][] myTeam;
    public Button[][] ennemyTeam;
    public Label monCamp;
    public Label campEnnemi;

    public MatelotView(Stage stage, Partie model, Joueur joueur){
        partie = model;
        this.stage = stage;
        this.joueur = joueur;
        initAttributes();
    }

    private void initAttributes() {
        stage.getScene().getStylesheets().add(new File("src/Assets/css/matelotView.css").toURI().toString());
        typeMatelot = new Label("Vous êtes un "+joueur.returnType());
        typeMatelot.setId("typeMatelot");
        initMyTeamArray();
        initEnnemyTeamArray();
        monCamp = new Label("Votre camp");
        monCamp.setId("moncamp");
        campEnnemi = new Label("Camp Adverse");
        campEnnemi.setId("campEnnemi");

    }

    private void initEnnemyTeamArray() {
        ennemyTeam = new Button[10][10];
        for (int i=0;i<10;i++){
            for (int j=0;j<10;j++){
                ennemyTeam[i][j] = new Button();
                ennemyTeam[i][j].setId(i+"#"+j);
                ennemyTeam[i][j].getStyleClass().add("caseButton");
            }
        }
    }

    private void initMyTeamArray() {
        myTeam = new Button[10][10];
        for (int i=0;i<10;i++){
            for (int j=0;j<10;j++){
                myTeam[i][j] = new Button();
                myTeam[i][j].setId(i+"#"+j);
                myTeam[i][j].getStyleClass().add("caseButton");
            }
        }
    }

    public void setMatelotView()
    {
        stage.getScene().getRoot().setVisible(false);
        ((BorderPane) stage.getScene().getRoot()).getChildren().clear();

        GridPane panneau = new GridPane();
        panneau.setId("panneau");
        generateGridView(panneau,myTeam);
        GridPane panneauAdverse = new GridPane();
        panneauAdverse.setId("panneauAdverse");
        generateGridView(panneauAdverse,ennemyTeam);
        VBox leftBox = new VBox(20);
        leftBox.getChildren().addAll(panneau,monCamp);
        VBox rightBox = new VBox(20);
        rightBox.getChildren().addAll(panneauAdverse,campEnnemi);

        HBox boxFinal = new HBox(30);
        boxFinal.getChildren().addAll(leftBox,rightBox);

        ((BorderPane) stage.getScene().getRoot()).setTop(typeMatelot);
        ((BorderPane) stage.getScene().getRoot()).setCenter(boxFinal);

        stage.getScene().getRoot().setVisible(true);
    }

    private void generateGridView(GridPane panneau,Button[][] team) {
        int chr = 65;
        Label lab;
        for (int k=0;k<team.length;k++){
            lab = new Label(""+(char)(chr+k));
            lab.getStyleClass().add("letterIndic");
            panneau.add(lab,k+1,0);
        }
        for (int i=0;i<team.length;i++){
            lab = new Label(""+(i+1));
            lab.getStyleClass().add("digitIndic");
            panneau.add(lab,0,i+1);
            for (int j=0;j<team[i].length;j++){
                panneau.add(team[i][j],i+1,j+1);
            }
        }
    }

    public void setController(MatelotController eh){
        setControllerOnButtonDisplay(eh);

    }

    private void setControllerOnButtonDisplay(MatelotController eh) {
        for (int i = 0 ; i< myTeam.length;i++)
        {
            for (int k=0;k<myTeam[i].length;k++)
            {
                myTeam[i][k].setOnMouseClicked(eh);
            }
        }
    }


}
