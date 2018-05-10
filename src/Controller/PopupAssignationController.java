package Controller;

import Model.Game.Equipe;
import View.PopupAssignationView;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PopupAssignationController implements EventHandler<MouseEvent> {
    public Equipe equipe;
    public PopupAssignationView view;

    public PopupAssignationController(Stage stage,Equipe equipe){
        this.equipe= equipe;
        this.view=new PopupAssignationView(this.equipe);
        view.setController(this);
        view.popup(stage);
    }

    @Override
    public void handle(MouseEvent event) {

    }
}
