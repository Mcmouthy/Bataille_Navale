package Model.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Amiral extends Joueur {
    private List<Bateau> lesBateaux;
    private HashMap<Bateau, Matelot[]> assignations;

    public Amiral(String pseudo, List<Bateau> lesBateaux, HashMap<Bateau, Matelot[]> assignations) {
        super(pseudo);
        this.lesBateaux = lesBateaux;
        this.assignations = assignations;
    }

    public Amiral(String pseudo) {
        super(pseudo);
        this.lesBateaux = new ArrayList<>();
        this.assignations = new HashMap<>();
    }

    public HashMap<Bateau, Matelot[]> getAssignations() {
        return assignations;
    }

    public void setAssignations(HashMap<Bateau, Matelot[]> assignations) {
        this.assignations = assignations;
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
