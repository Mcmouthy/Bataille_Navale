package Model.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Amiral extends Joueur {
    private List<Bateau> lesBateaux;
    private HashMap<Matelot, Bateau> assignations;

    public Amiral(String pseudo, List<Bateau> lesBateaux, HashMap<Matelot, Bateau> assignations) {
        super(pseudo);
        this.lesBateaux = lesBateaux;
        this.assignations = assignations;
    }

    public Amiral(String pseudo) {
        super(pseudo);
        this.lesBateaux = new ArrayList<>();
        this.assignations = new HashMap<>();
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

    public boolean addAssignation(Matelot matelot, Bateau bateau) {
        if (!matelot.getBateauxAssignes().contains(bateau)){
            assignations.put(matelot,bateau);
            matelot.addBateau(bateau);
            return true;
        }
        return false;
    }

    public boolean removeAssignation(Matelot matelot, Bateau bateau) {
        return false;
    }

    public boolean changeAssignation(Matelot matelot, Bateau bateau) {
        return false;
    }
}
