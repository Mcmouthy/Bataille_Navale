package View;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;


public class DialogInfo {
    public static void showText(Alert.AlertType type,String intitule, String texte) {
        Alert dialog = new Alert(type);
        dialog.setTitle("Bataille Navale");
        dialog.setHeaderText(intitule);
        Label label = new Label(texte);
        label.setWrapText(true);
        dialog.getDialogPane().setContent(label);
        dialog.showAndWait();
    }
}
