package Controller;

import Model.Exception.AlreadyAssignedToAttPostException;
import Model.Exception.AlreadyAssignedToDefPostException;
import Model.Exception.AlreadyAssignedToOtherMatelotException;
import Model.Exception.NoPlaceAvailableOnShipException;
import Model.Game.*;
import View.DialogInfo;
import View.PopupAssignationView;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.event.ChangeEvent;

public class PopupAssignationController implements EventHandler<MouseEvent>, javafx.beans.value.ChangeListener<String> {
    public Equipe equipe;
    public PopupAssignationView view;
    public boolean assign = true;

    public PopupAssignationController(Stage stage,Equipe equipe){
        this.equipe= equipe;
        this.view=new PopupAssignationView(this.equipe);
        view.setController(this);
        view.popup(stage);
    }

    @Override
    public void handle(MouseEvent event) {

    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

        if (assign) {
            ObjectProperty comboProperty = (ObjectProperty) observable;
            ComboBox<String> combobox = (ComboBox) comboProperty.getBean();
            String shipName= combobox.getId().split("#")[0];
            String poste = combobox.getId().split("#")[1].substring(0,3);
            Bateau bateau = equipe.getBateauByName(shipName);
            Joueur matelot;
            int thePoste = (poste.equals("Att")) ? Amiral.ATT : Amiral.DEF;

            if (newValue.equals("Aucun")) {
                matelot = equipe.getMatelotByName(oldValue);
                equipe.getAmiral().removeAssignation((Matelot) matelot, bateau, thePoste);

            } else {
                matelot = equipe.getMatelotByName(newValue);
                if (oldValue == null || oldValue.equals("Aucun")) {
                    assign = false;
                    try {
                        equipe.getAmiral().addAssignation((Matelot)matelot, bateau, thePoste);
                    } catch (NoPlaceAvailableOnShipException e) {
                        DialogInfo.showText(Alert.AlertType.WARNING, "Assignation impossible", e.getMessage());
                        combobox.setValue("Aucun");
                    } catch (AlreadyAssignedToOtherMatelotException e) {
                        DialogInfo.showText(Alert.AlertType.WARNING, "Assignation impossible", e.getMessage());
                        combobox.setValue("Aucun");

                    } catch (AlreadyAssignedToAttPostException e) {
                        DialogInfo.showText(Alert.AlertType.WARNING, "Assignation impossible", e.getMessage());
                        combobox.setValue("Aucun");

                    } catch (AlreadyAssignedToDefPostException e) {
                        DialogInfo.showText(Alert.AlertType.WARNING, "Assignation impossible", e.getMessage());
                        combobox.setValue("Aucun");

                    }
                    assign = true;
                } else {
                    assign = false;
                    Joueur oldMatelot = equipe.getMatelotByName(oldValue);
                    try {
                        equipe.getAmiral().removeAssignation((Matelot)oldMatelot, bateau, thePoste);
                        equipe.getAmiral().addAssignation((Matelot)matelot, bateau, thePoste);
                    } catch (NoPlaceAvailableOnShipException e) {
                        DialogInfo.showText(Alert.AlertType.WARNING, "Assignation impossible", e.getMessage());
                        combobox.setValue(oldValue);
                        equipe.getAmiral().redoAssignation((Matelot)oldMatelot, bateau, thePoste);

                    } catch (AlreadyAssignedToDefPostException e) {
                        DialogInfo.showText(Alert.AlertType.WARNING, "Assignation impossible", e.getMessage());
                        combobox.setValue(oldValue);
                        equipe.getAmiral().redoAssignation((Matelot)oldMatelot, bateau, thePoste);

                    } catch (AlreadyAssignedToOtherMatelotException e) {
                        DialogInfo.showText(Alert.AlertType.WARNING, "Assignation impossible", e.getMessage());
                        combobox.setValue(oldValue);
                        equipe.getAmiral().redoAssignation((Matelot)oldMatelot, bateau, thePoste);

                    } catch (AlreadyAssignedToAttPostException e) {
                        DialogInfo.showText(Alert.AlertType.WARNING, "Assignation impossible", e.getMessage());
                        combobox.setValue(oldValue);
                        equipe.getAmiral().redoAssignation((Matelot)oldMatelot, bateau, thePoste);

                    }
                    assign = true;
                }
            }
        }
    }
}
