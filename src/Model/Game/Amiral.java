package Model.Game;

import java.util.HashMap;
import java.util.List;

public class Amiral extends Joueur {
    private List<Bateau> lesBateaux;
    private HashMap<Matelot, Bateau> assignations;

    public Amiral() {
    }

    public List<Bateau> getLesBateaux() {
        return lesBateaux;
    }

    public void setLesBateaux(List<Bateau> lesBateaux) {
        this.lesBateaux = lesBateaux;
    }

    public HashMap<Matelot, Bateau> getAssignations() {
        return assignations;
    }

    public void setAssignations(HashMap<Matelot, Bateau> assignations) {
        this.assignations = assignations;
    }
}
