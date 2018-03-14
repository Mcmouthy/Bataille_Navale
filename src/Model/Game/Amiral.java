package Model.Game;

import java.util.ArrayList;
import java.util.List;

public class Amiral extends Joueur {
    private List<Bateau> lesBateaux;

    public Amiral(String pseudo, List<Bateau> lesBateaux) {
        super(pseudo);
        this.lesBateaux = lesBateaux;
    }

    public Amiral(String pseudo) {
        super(pseudo);
        this.lesBateaux = new ArrayList<>();
    }

    public List<Bateau> getLesBateaux() {
        return lesBateaux;
    }

    public void setLesBateaux(List<Bateau> lesBateaux) {
        this.lesBateaux = lesBateaux;
    }

    public boolean addAssignation(Matelot matelot, Bateau bateau) {
        return false;
    }

    public boolean removeAssignation(Matelot matelot, Bateau bateau) {
        return false;
    }

    public boolean changeAssignation(Matelot matelot, Bateau bateau) {
        return false;
    }
}
