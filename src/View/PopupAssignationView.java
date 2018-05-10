package View;


import Controller.PopupAssignationController;
import Model.Game.Bateau;
import Model.Game.Equipe;
import Model.Game.Joueur;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;

public class PopupAssignationView {

    VBox rightSideScreenDisplay;
    Equipe equipe;

    public PopupAssignationView(Equipe equipe){
        VBox rightSideScreenDisplay = new VBox();
        this.equipe=equipe;
    }


    public void popup(Stage stage) {
        final Stage dialog = new Stage();
        dialog.setTitle("Assignations");

        dialog.initModality(Modality.NONE);
        dialog.initOwner(stage.getScene().getWindow());
        initRightSideScreenDisplay(equipe);
        Scene dialogScene = new Scene(rightSideScreenDisplay);
        dialogScene.getStylesheets().add(new File("src/Assets/css/popupAssignation.css").toURI().toString());
        dialog.setScene(dialogScene);
        dialog.show();
    }

    public void setController(PopupAssignationController eh)
    {

    }


    private void initRightSideScreenDisplay(Equipe e) {

        rightSideScreenDisplay = new VBox();
        rightSideScreenDisplay.setId("assignations_tab");
        rightSideScreenDisplay.getStyleClass().add("assignationsGrid");

        Label lab = new Label("Navire");
        lab.getStyleClass().addAll("assignationCell","assignationHeader");
        Label role = new Label("Poste");
        role.getStyleClass().addAll("assignationCell","assignationHeader");
        Label assign = new Label("Joueur");
        assign.getStyleClass().addAll("assignationCell","assignationHeader");
        HBox row = new HBox();
        row.getChildren().addAll(lab,role,assign);
        row.setId("AssignationHead");


        rightSideScreenDisplay.getChildren().add(row);
        ComboBox<String> assignCombobox;
        for (Bateau b:e.getBateauxEquipe())
        {
            lab = new Label(b.getNomNavire());
            lab.setId(b.getNomNavire()+"#Att");
            lab.getStyleClass().add("assignationCell");
            role = new Label("Attaquant");
            role.setId(b.getNomNavire()+"#AttRole");
            role.getStyleClass().add("assignationCell");
            assignCombobox = new ComboBox<>();
            for (Joueur j: e.getLesJoueurs()) {
                assignCombobox.getItems().add(j.getPseudo());
            }
            assignCombobox.setId(b.getNomNavire()+"#AttAssign");
            assignCombobox.getStyleClass().add("assignationComboBox");
            row = new HBox();
            row.getChildren().addAll(lab,role,assignCombobox);
            row.setId(b.getNomNavire()+"#lineAtt");
            row.getStyleClass().add("rowAssignation");
            rightSideScreenDisplay.getChildren().add(row);

            //Same thing but with defense line
            lab = new Label(b.getNomNavire());
            lab.setId(b.getNomNavire()+"#Def");
            lab.getStyleClass().add("assignationCell");
            role = new Label("Défenseur");
            role.setId(b.getNomNavire()+"#DefRole");
            role.getStyleClass().add("assignationCell");
            assignCombobox = new ComboBox<>();
            for (Joueur j: e.getLesJoueurs()) {
                assignCombobox.getItems().add(j.getPseudo());
            }
            assignCombobox.setId(b.getNomNavire()+"#DefAssign");
            assignCombobox.getStyleClass().add("assignationComboBox");
            row = new HBox();
            row.getChildren().addAll(lab,role,assignCombobox);
            row.setId(b.getNomNavire()+"#lineDef");
            row.getStyleClass().add("rowAssignation");
            rightSideScreenDisplay.getChildren().add(row);

        }
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }
}
